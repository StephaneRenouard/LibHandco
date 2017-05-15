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
 * Forme d'onde sinusoïdale.
 */
class FormeSinus extends FormeOnde {
  /**
   * Crée une forme d'onde sinusoïdale de fréquence <code>f0</code>
   * à la fréquence d'échantillonnage <code>fe</code>.
   */
  public FormeSinus(double f0, double fe) {
    super(f0, fe);
  }

  /**
   * Calcule un échantillon de la sinusoïde.
   */
  public double echantillon(int i) {
    double periode = 1 / f0_;  // période du signal
    // On se ramène dans la période [0, periode[
    double t = Math.IEEEremainder(i/fe_, periode);
    // IEEEremainder nous ramène dans [-periode/2, periode/2[,
    // on ajoute donc periode au résultat si t est négatif.
    if (t < 0) {
      t += periode;
    }
    // On calcule f(t) = sin(2*pi*f*t)
    return Math.sin(2 * Math.PI * f0_ * t);
  }
}
