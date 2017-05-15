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
 * Forme d'onde carrée.
 */
class FormeCarre extends FormeOnde {
  /**
   * Crée une forme d'onde carrée de fréquence fondamentale
   * <code>f0</code> à la fréquence d'échantillonnage <code>fe</code>.
   */
  public FormeCarre(double f0, double fe) {
    super(f0, fe);
  }

  /**
   * Calcule un échantillon de la sinusoïde.
   */
  public double echantillon(int i) {
    double periode = 1 / f0_;
    // On se ramène dans la période [0, periode[
    double t = Math.IEEEremainder(i/fe_, periode);
    // IEEEremainder nous ramène dans [-periode/2, periode/2[,
    // on ajoute donc periode au résultat si t est négatif.
    if (t < 0) {
      t += periode;
    }
    // Si on est avant la demi-période, le signal vaut 1,
    // sinon, il vaut -1
    if (t < periode/2) {
      return 1.0;
    } else {
      return -1.0;
    }
  }
}
