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
 * Forme d'onde aléatoire (non périodique).
 */
class FormeAleatoire extends FormeOnde {
  public FormeAleatoire() {
    super(0, 0);
  }

  public double echantillon(int i) {
    // L'échantillon est choisi au hasard dans [0.0, 1.0[
    // puis étendu à l'intervalle [-1, 1[
    return 2 * Math.random() - 1;
  }
}
