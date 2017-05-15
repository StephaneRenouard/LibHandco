package fr.handco.lib.sound;

//
// Fr�d�ric Boulanger
// Sup�lec - D�partement Informatique
// 3 rue Joliot-Curie
// 91192 Gif-sur-Yvette cedex, France
//
// frederic.boulanger@supelec.fr
//
/**
 * Une notation musicale est une fa�on textuelle de repr�senter les notes.
 */
interface NotationMusicale {
  public double frequence(String note);
}