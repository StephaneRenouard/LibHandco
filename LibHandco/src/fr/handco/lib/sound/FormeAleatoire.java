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
 * Forme d'onde al�atoire (non p�riodique).
 */
class FormeAleatoire extends FormeOnde {
  public FormeAleatoire() {
    super(0, 0);
  }

  public double echantillon(int i) {
    // L'�chantillon est choisi au hasard dans [0.0, 1.0[
    // puis �tendu � l'intervalle [-1, 1[
    return 2 * Math.random() - 1;
  }
}
