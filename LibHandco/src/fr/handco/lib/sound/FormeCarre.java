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
 * Forme d'onde carr�e.
 */
class FormeCarre extends FormeOnde {
  /**
   * Cr�e une forme d'onde carr�e de fr�quence fondamentale
   * <code>f0</code> � la fr�quence d'�chantillonnage <code>fe</code>.
   */
  public FormeCarre(double f0, double fe) {
    super(f0, fe);
  }

  /**
   * Calcule un �chantillon de la sinuso�de.
   */
  public double echantillon(int i) {
    double periode = 1 / f0_;
    // On se ram�ne dans la p�riode [0, periode[
    double t = Math.IEEEremainder(i/fe_, periode);
    // IEEEremainder nous ram�ne dans [-periode/2, periode/2[,
    // on ajoute donc periode au r�sultat si t est n�gatif.
    if (t < 0) {
      t += periode;
    }
    // Si on est avant la demi-p�riode, le signal vaut 1,
    // sinon, il vaut -1
    if (t < periode/2) {
      return 1.0;
    } else {
      return -1.0;
    }
  }
}
