package fr.handco.lib.sound;

//
// Fr�d�ric Boulanger
// Sup�lec - D�partement Informatique
// 3 rue Joliot-Curie
// 91192 Gif-sur-Yvette cedex, France
//
// frederic.boulanger@supelec.fr
//
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

/**
 * Une note est d�finie par son nom :
 *   A pour la
 *   B pour si
 *   C pour do
 *   D pour re
 *   E pour mi
 *   F pour fa
 *   G pour sol
 * suivi du num�ro de l'octave : l'octave qui va du do
 * juste en dessous de la clef de sol au si situ� une septi�me
 * au dessus est num�rot�e 4. Notamment, la fr�quence de la note
 * A4 est g�n�ralement fix�e � 440Hz.
 * 
 * Le nom d'une note peut-�tre suivi de 'b' pour indiquer un b�mol
 * ou de '#' pour indiquer un di�se.
 */
class Note {
  private double frequence_;  // fr�quence en Hertz
  private double duree_;      // dur�e en millisecondes
  private String nom_;        // nom tel que fourni au constructeur
  private static final double A4_ = 440.0;  // diapason standard

  /**
   * Cr�e un nouvelle note
   * @param nom est le nom de la note en notation standard US
   * @param ms est la sur�e de la note en millisecondes
   */
  public Note(String nom, double ms) {
    nom_ = nom;
    duree_ = ms;
    if (nom.charAt(0) == ' ') {  // un espace d�signe un silence
      frequence_ = 0;
    } else {
      // On obtient la fr�quence en interpr�tant le nom selon la notation US
      frequence_ = new NotationUS(A4_).frequence(nom_);
    }
  }

  /**
   * Joue une note sur une ligne audio � un cetain volume sonore.
   * @param line est la ligne sur laquelle la note doit �tre jou�e
   * @param niveau est le niveau sonore : 0.0 = silence, 1.0 = volume maximal
   */
  public void jouer(SourceDataLine line, double niveau) {
    if ((niveau < 0) || (niveau > 1)) {
      throw new Error("Le niveau sonore doit �tre entre 0.0 et 1.0");
    }
    // On r�cup�re le format audio de la ligne
    AudioFormat fmt = line.getFormat();
    // On ne sait g�n�rer les �chantillons qu'au format PCM_SIGNED.
    // Dans ce format, chaque �chantillon code la valeur du signal � un instant,
    // et les �chantillons sont sign�s. PCM = "Pulse Code Modulation"
    if (!fmt.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
      throw new Error("Les notes ne peuvent �tre jou�es qu'en format PCM sign�");
    }
    // On r�cup�re le nombre de bits par �chantillon
    int bits = fmt.getSampleSizeInBits();
    // On ne sait traiter que les �chantillons cod�s sur 1 ou 2 octets
    if ((bits != 8) && (bits != 16)) {
      throw new Error("Les notes ne peuvent �tre jou�es qu'en 8 ou 16 bits");
    }
    // On r�cup�re l'ordre dans lequel les octets sont plac�s :
    //  - big endian : l'octet de poids fort est en t�te
    //  - little endian : l'octet de poids faible est en t�te
    boolean bigEndian = fmt.isBigEndian();
    // On r�cup�re la fr�quence d'�chantillonnage
    double fe = fmt.getSampleRate();
    // Le nombre d'�chantillons est le produit de la dur�e en secondes 
    // par la fr�quence en Hertz
    int nbSamples = (int)(duree_ / 1000 * fe);
    int taille = nbSamples;
    // Si les �chantillons sont sur 2 octets, il faut doubler la taille
    // du tableau d'octets dans lequel seront rang�s les �chantillons
    if (bits == 16) {
      taille*= 2;
    }
    byte samples[] = new byte[taille];
    if (frequence_ == 0) {
      // Si la fr�quence est nulle, on g�n�re du silence
      for (int i = 0; i < taille; i++) {
        samples[i] = 0;
      }
    } else {
      // On va construire un son � partir d'une forme sinuso�dale,
      // d'une forme triangulaire et d'une forme carr�e plus un peu de bruit
      FormeOnde forme1 = new FormeSinus(frequence_, fe);
      FormeOnde forme2 = new FormeTriangle(frequence_, fe);
      FormeOnde forme3 = new FormeCarre(frequence_, fe);
      FormeOnde forme4 = new FormeAleatoire();
      for (int i = 0; i < nbSamples; i++) {
        // calcul de la valeur de l'�chantillon dans [-1.0, 1.0]
        double sample = niveau * (0.40 * forme1.echantillon(i) 
                                + 0.40 * forme2.echantillon(i)
                                + 0.20 * forme3.echantillon(i)
                                + 0.02 * forme4.echantillon(i)
                                 );
        if (bits == 8) {
          // Sur un octet, il suffit de multiplier par 127
          samples[i] = (byte)(0x7F * sample);
        } else {
          // Sur deux octets, on multiplie par 32767
          int value = (int)(0x7FFF * sample);
          if (bigEndian) {
            // En big endian, on place l'octet de poids fort en premier,
            // que l'on obtient en ne gardant que les 8 bits de poids fort
            // et en d�calant de 8 bits vers la gauche
            samples[2*i]   = (byte)((value & 0xFF00) >> 8);
            samples[2*i+1] = (byte)((value & 0xFF));
          } else {
            // En little endian, on place l'octet de poids faible en premier
            samples[2*i]   = (byte)((value & 0xFF));
            samples[2*i+1] = (byte)((value & 0xFF00) >> 8);
          }
        }
      }
    }
    // Il ne reste qu'� envoyer ces donn�es sur la ligne
    line.write(samples, 0, taille);
  }
}
