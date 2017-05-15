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
 * Forme d'onde triangulaire.
 */
class FormeTriangle extends FormeOnde {
  /**
   * Cr�e une forme d'onde triangulaire de fr�quence fondamentale
   * <code>f0</code> � la fr�quence d'�chantillonnage <code>fe</code>.
   */
  public FormeTriangle(double f0, double fe) {
    super(f0, fe);
  }

  public double echantillon(int i) {
    double periode = 1 / f0_;
    // On se ram�ne dans la p�riode [0, periode[
    double t = Math.IEEEremainder(i/fe_, periode);
    // IEEEremainder nous ram�ne dans [-periode/2, periode/2[,
    // on ajoute donc periode au r�sultat si t est n�gatif.
    if (t < 0) {
      t += periode;
    }
    double value = 0;
    if (t <= (periode/4)) {
      // 1er quart, on monte vers 1
      value = t * 4 / periode;
    } else if (t <= (3 * periode / 4)) {
      // 2e et 3e quarts, on descend de 1 vers -1
      t -= (periode / 4);
      value = 1 - (t * 4 / periode);
    } else {
      // dernier quart, on remonte vers 0
      t -= (3 * periode / 4);
      value = -1 + (t * 4 / periode);
    }
    return value;
  }
}
