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
 * Forme d'onde sinuso�dale.
 */
class FormeSinus extends FormeOnde {
  /**
   * Cr�e une forme d'onde sinuso�dale de fr�quence <code>f0</code>
   * � la fr�quence d'�chantillonnage <code>fe</code>.
   */
  public FormeSinus(double f0, double fe) {
    super(f0, fe);
  }

  /**
   * Calcule un �chantillon de la sinuso�de.
   */
  public double echantillon(int i) {
    double periode = 1 / f0_;  // p�riode du signal
    // On se ram�ne dans la p�riode [0, periode[
    double t = Math.IEEEremainder(i/fe_, periode);
    // IEEEremainder nous ram�ne dans [-periode/2, periode/2[,
    // on ajoute donc periode au r�sultat si t est n�gatif.
    if (t < 0) {
      t += periode;
    }
    // On calcule f(t) = sin(2*pi*f*t)
    return Math.sin(2 * Math.PI * f0_ * t);
  }
}
