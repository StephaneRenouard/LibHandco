package fr.handco.lib.sound;

//
// Frédéric Boulanger
// Supélec - Département Informatique
// 3 rue Joliot-Curie
// 91192 Gif-sur-Yvette cedex, France
//
// frederic.boulanger@supelec.fr
//
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;

/**
 * Exemple de production de son échantillonné en Java
 */
public class ExempleSon {
  public static void main(String[] args) {
    // Fréquence d'échantillonnage
    float sampleRate = 20500;
    // Taille des échantillons en bits (8 ou 16)
    int sampleSize = 16;
    // Ordre des octets pour les échantillons sur deux octets :
    //  - big endian : octet de poids fort en premier 
    //                 (on prend le mot par le "gros bout" = big end)
    //  - little endian : octet de poids faible en premier
    //                 (on prend le mot par le "petit bout" = little end)
    final boolean bigEndian = true;
    // Les échantillons seront signés (-128/+127 sur 8 bits)
    final boolean signed = true;
    // Ces données définissent un format audio
    AudioFormat audiofmt = new AudioFormat(sampleRate, sampleSize, 1, signed, bigEndian);
    // On va chercher à obtenir une ligne audio qui supporte ce format
    // Une SourceDataLine est une ligne qui permet de produire du son.
    // C'est donc une source du point de vue du système audio, mais c'est
    // un puits pour notre programme puisque nous allons lui envoyer des données.
    SourceDataLine line = null;
    try {
      line = AudioSystem.getSourceDataLine(audiofmt);
      line.open(audiofmt);  // on ouvre la ligne pour pouvoir écrire dedans
    } catch (LineUnavailableException lue) {
      System.out.println("# Erreur : impossible de trouver une ligne de sortie audio au format :");
      System.out.println("#          " + audiofmt);
      System.exit(1);
    }

    line.start();  // on démarre la production de son par la ligne 

    Note n = new Note("Eb4", 300);     // mi bémol de durée 0,3s
    n.jouer(line, 0.3);                //   joué sur la ligne avec une intensité de 30%
    new Note(" ", 50).jouer(line, 0.0);// silence de 0,05s
    n.jouer(line, 0.3);                // de nouveau le mi bémol à 30%
    n = new Note("C4", 350);           // un do de durée 0,35s
    n.jouer(line, 0.95);               //  joué à 95% d'intensité sonore
    n = new Note("G4", 2000);          // un sol de durée 2s
    n.jouer(line, 0.95);               //  joué à 95% d'intensité sonore

    // On attend que la ligne ait fini de jouer les notes avant de terminer le programme
    line.drain();
    
    // Il faut faire un exit car le système audio crée  
    // un thread qui ne termine pas tout seul.
    System.exit(0);
  }
}






