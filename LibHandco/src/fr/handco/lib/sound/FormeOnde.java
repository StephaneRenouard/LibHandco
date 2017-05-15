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
 * Une forme d'onde est une manière de générer un signal d'une 
 * fréquence donnée échantillonné à une certaine fréquence.
 */
abstract class FormeOnde {
  /** Fréquence fondamentale du signal. */
  protected final double f0_;
  /** Fréquence d'échantillonnage. */
  protected final double fe_;

  /** 
   * Construit une forme d'onde de fréquence <code>f0</code> 
   * avec une fréquence d'échantillonnage <code>fe</code>.
   * @param f0 est la fréquence fondamentale du signal
   * @param fe est la fréquence d'échantillonnage
   */
  public FormeOnde(double f0, double fe) {
    f0_ = f0;
    fe_ = fe;
  }

  /**
   * Calcule un échantillon du signal.
   * @param i est l'indice de l'échantillon dont la date est i/fe_
   * @return la valeur de l'échantillon, normalisée entre -1.0 et 1.0
   */
  public abstract double echantillon(int i);

  /**
   * Rend la fréquence du signal.
   */
  public double frequence() {
    return f0_;
  }

  /**
   * Rend la fréquence d'échantillonnage du signal.
   */
  public double frequenceEch() {
    return fe_;
  }
}
