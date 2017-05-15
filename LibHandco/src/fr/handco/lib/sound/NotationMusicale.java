package fr.handco.lib.sound;

//
// Frédéric Boulanger
// Supélec - Département Informatique
// 3 rue Joliot-Curie
// 91192 Gif-sur-Yvette cedex, France
//
// frederic.boulanger@supelec.fr
//
/**
 * Une notation musicale est une façon textuelle de représenter les notes.
 */
interface NotationMusicale {
  public double frequence(String note);
}