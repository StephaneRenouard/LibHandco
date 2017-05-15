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
 * Une forme d'onde est une mani�re de g�n�rer un signal d'une 
 * fr�quence donn�e �chantillonn� � une certaine fr�quence.
 */
abstract class FormeOnde {
  /** Fr�quence fondamentale du signal. */
  protected final double f0_;
  /** Fr�quence d'�chantillonnage. */
  protected final double fe_;

  /** 
   * Construit une forme d'onde de fr�quence <code>f0</code> 
   * avec une fr�quence d'�chantillonnage <code>fe</code>.
   * @param f0 est la fr�quence fondamentale du signal
   * @param fe est la fr�quence d'�chantillonnage
   */
  public FormeOnde(double f0, double fe) {
    f0_ = f0;
    fe_ = fe;
  }

  /**
   * Calcule un �chantillon du signal.
   * @param i est l'indice de l'�chantillon dont la date est i/fe_
   * @return la valeur de l'�chantillon, normalis�e entre -1.0 et 1.0
   */
  public abstract double echantillon(int i);

  /**
   * Rend la fr�quence du signal.
   */
  public double frequence() {
    return f0_;
  }

  /**
   * Rend la fr�quence d'�chantillonnage du signal.
   */
  public double frequenceEch() {
    return fe_;
  }
}
