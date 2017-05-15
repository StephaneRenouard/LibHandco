package fr.handco.lib.sound;

//
// Fr�d�ric Boulanger
// Sup�lec - D�partement Informatique
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
 * Exemple de production de son �chantillonn� en Java
 */
public class ExempleSon {
  public static void main(String[] args) {
    // Fr�quence d'�chantillonnage
    float sampleRate = 20500;
    // Taille des �chantillons en bits (8 ou 16)
    int sampleSize = 16;
    // Ordre des octets pour les �chantillons sur deux octets :
    //  - big endian : octet de poids fort en premier 
    //                 (on prend le mot par le "gros bout" = big end)
    //  - little endian : octet de poids faible en premier
    //                 (on prend le mot par le "petit bout" = little end)
    final boolean bigEndian = true;
    // Les �chantillons seront sign�s (-128/+127 sur 8 bits)
    final boolean signed = true;
    // Ces donn�es d�finissent un format audio
    AudioFormat audiofmt = new AudioFormat(sampleRate, sampleSize, 1, signed, bigEndian);
    // On va chercher � obtenir une ligne audio qui supporte ce format
    // Une SourceDataLine est une ligne qui permet de produire du son.
    // C'est donc une source du point de vue du syst�me audio, mais c'est
    // un puits pour notre programme puisque nous allons lui envoyer des donn�es.
    SourceDataLine line = null;
    try {
      line = AudioSystem.getSourceDataLine(audiofmt);
      line.open(audiofmt);  // on ouvre la ligne pour pouvoir �crire dedans
    } catch (LineUnavailableException lue) {
      System.out.println("# Erreur : impossible de trouver une ligne de sortie audio au format :");
      System.out.println("#          " + audiofmt);
      System.exit(1);
    }

    line.start();  // on d�marre la production de son par la ligne 

    Note n = new Note("Eb4", 300);     // mi b�mol de dur�e 0,3s
    n.jouer(line, 0.3);                //   jou� sur la ligne avec une intensit� de 30%
    new Note(" ", 50).jouer(line, 0.0);// silence de 0,05s
    n.jouer(line, 0.3);                // de nouveau le mi b�mol � 30%
    n = new Note("C4", 350);           // un do de dur�e 0,35s
    n.jouer(line, 0.95);               //  jou� � 95% d'intensit� sonore
    n = new Note("G4", 2000);          // un sol de dur�e 2s
    n.jouer(line, 0.95);               //  jou� � 95% d'intensit� sonore

    // On attend que la ligne ait fini de jouer les notes avant de terminer le programme
    line.drain();
    
    // Il faut faire un exit car le syst�me audio cr�e  
    // un thread qui ne termine pas tout seul.
    System.exit(0);
  }
}






