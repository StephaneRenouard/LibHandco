package fr.handco.lib.sound;

//
// Frédéric Boulanger
// Supélec - Département Informatique
// 3 rue Joliot-Curie
// 91192 Gif-sur-Yvette cedex, France
//
// frederic.boulanger@supelec.fr
//

/*! \mainpage Un exemple simple de production de son en Java
 *
 * Cet exemple ne contient qu'une seule classe et montre comment :
 * \li obtenir une ligne audio pour produire un son
 * \li générer les échantillons sonores en respectant le format attendu par la ligne
 * \li transmettre les échantillons à la ligne et attendre qu'elle ait fini de les jouer.
 *
 *
 */
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Cette classe permet de jouer un son sinusoïdal simple de fréquence et de
 * durée donnée. La création des échantillons sonores fonctionne pour des
 * échantillons codés sur un ou sur deux octets.
 */
public class SonSimple {
	/**
	 * Joue un son sinusoïdal de fréquence et de durée donnés sur une ligne
	 * audio.
	 * 
	 * @param freq
	 *            est la fréquence du son en Hertz
	 * @param duree
	 *            est la durée du son en millisecondes
	 * @param line
	 *            est la ligne audio sur laquelle le son doit être joué
	 */
	public static void jouer(double freq, long duree, SourceDataLine line) {
		// Format de son utilisé par la ligne
		AudioFormat fmt = line.getFormat();
		// On récupère la fréquence d'échantillonnage de la ligne
		float sampleRate = fmt.getSampleRate();
		// ainsi que la taille du tampon utilisé par cette ligne
		int bufsize = line.getBufferSize();
		// la taille des échantillons
		int sampleSize = fmt.getSampleSizeInBits();
		int nbOctets = 0; // nombre d'octets par échantillon
		int maxVal = 0; // valeur maximal d'un échantillon
		if (sampleSize == 8) {
			nbOctets = 1; // 8 bits => 1 octet, max = 127
			maxVal = 0x7F;
		} else if (sampleSize == 16) {
			nbOctets = 2; // 16 bits => 2 octets, max = 32767
			maxVal = 0x7FFF;
		} else {
			throw new Error("# Nombre de bits par échantillon non supporté : "
					+ sampleSize);
		}
		// Ordre des octets dans un échantillon
		boolean bigEndian = fmt.isBigEndian();
		// Le nombre d'échantillons à générer est le produit de la durée
		// en secondes par la fréquence d'échantillonnage en Hertz
		int nbEchantillons = (int) Math
				.ceil((double) duree / 1000 * sampleRate);
		// On alloue un tableau d'octets de la taille du tampon de la ligne
		byte audiosamples[] = new byte[bufsize];
		int i = 0;
		while (i < nbEchantillons) {
			// on génère les échantillons et on les transmet à la ligne tampon
			// par tampon, ce qui est plus efficace.
			int j = 0;
			while ((j < bufsize) && (i < nbEchantillons)) {
				// sample donne la valeur de l'échantillon en double avec
				// amplitude
				// -1,0/1.0, on la multiplie par maxVal pour obtenir la valeur
				// pour la ligne
				int val = (int) (maxVal * sample(i++, freq, sampleRate));
				if (nbOctets == 1) {
					// Un seul octet : il suffit de le mettre dans le tableau
					audiosamples[j++] = (byte) val;
				} else if (bigEndian) {
					// deux octets en big endian : on met l'octet de poids fort
					// en premier
					audiosamples[j++] = (byte) ((val & 0xFF00) >> 8);
					audiosamples[j++] = (byte) (val & 0x00FF);
				} else {
					// deux octets en little endian : on met l'octet de poids
					// faible en premier
					audiosamples[j++] = (byte) (val & 0x00FF);
					audiosamples[j++] = (byte) ((val & 0xFF00) >> 8);
				}
			}
			// on transmet le tampon à la ligne
			line.write(audiosamples, 0, j);
		}
	}

	/**
	 * Calcule un échantillon d'un signal sinusoïdal.
	 * 
	 * @param i
	 *            est l'indice de l'échantillon (débute à 0)
	 * @param freq
	 *            est la fréquence du signal en Hertz
	 * @param rate
	 *            est la fréquence d'échantillonnage en Hertz
	 * @return la valeur de l'échantillon, comprise en -1.0 et 1.0
	 */
	public static double sample(int i, double freq, double rate) {
		// On ramène d'abord le temps dans la période [0, 1/freq[
		double t = Math.IEEEremainder(i / rate, 1 / freq);
		// IEEEremainder nous ramène dans [-1/2freq, 1/2freq[, donc
		// on ajoute 1/freq pour être dans [0, 1/freq[
		if (t < 0) {
			t += 1 / freq;
		}
		// f(t) = sin(wt) = sin(2*pi*freq*t)
		return Math.sin(2 * Math.PI * freq * t);
	}

	public static void main(String args[]) {
		final float sampleRate = 20500; // fréquence d'échantillonnage
		final int sampleSize = 8; // nombre de bits par échantillon : ici un
									// octet
		final boolean bigEndian = true; // ordre des octets dans l'échantillon
										// (si sampleSize = 16)
		final boolean signed = true; // les échantillons sont signés (valeurs de
										// -128 à 127)
		// Ces paramètres définissent un format audio échantillonné
		AudioFormat audiofmt = new AudioFormat(sampleRate, sampleSize, 1,
				signed, bigEndian);
		// On va maintenant chercher une ligne audio qui supporte ce format
		// Une SourceDataLine est une ligne audio qui produit du son. C'est une
		// source pour
		// le système audio, même si du point de vue de notre programme, c'est
		// plutôt un puits.
		SourceDataLine line = null;
		try {
			line = AudioSystem.getSourceDataLine(audiofmt);
			line.open(audiofmt); // on ouvre la ligne pour pouvoir y écrire des
									// échantillons
		} catch (LineUnavailableException lue) {
			System.out
					.println("# Erreur : impossible de trouver une ligne de sortie audio au format :");
			System.out.println("#          " + audiofmt);
			System.exit(1);
		}

		line.start(); // on active la ligne afin qu'elle transforme les
						// échantillons en son
		jouer(440.0, 800, line); // jouer un la pendant 2 secondes

		// On attend que la ligne ait fini de jouer les échantillons
		line.drain();
		// Il faut faire un exit car le système audio crée un thread qui ne
		// termine pas tout seul.
		System.exit(0);
	}
}