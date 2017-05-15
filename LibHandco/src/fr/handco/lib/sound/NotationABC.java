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
 * La notation ABC représente les notes par une lettre :
 *   A = la
 *   B = si
 *   C = do
 *   D = ré
 *   E = mi
 *   F = fa
 *   G = sol
 * Un bémol est indiqué par un '_' placé avant la note.
 * Un dièse est indiqué par un '^' placé avant la note.
 * L'octave dans laquelle se trouve la note est indiquée
 * par la casse du nom et par les caractères ''' et ',' situés
 * après le nom de la note. Les changements d'octave se font 
 * sur la note do.
 * "C" désigne le do situé une ligne en-dessous de la clef de sol, 
 * et "B" désigne le si situé sur la 3e ligne de la clef de sol.
 * Le do qui se situe juste au dessus est noté "c".
 * Chaque apostrophe ''' placée après le nom de la note la monte
 * d'une octave. Chaque virgule ',' placée après le nom de la note
 * la descend d'une octave. Ainsi, "C,," désigne le do grave du 
 * violoncelle, situé sur la deuxième ligne en-dessous de la
 * clef de fa, alors que "_e'" désigne le mi bémol aigu situé
 * sur la 3e ligne au-dessus de la clef de sol.
 */
class NotationABC implements NotationMusicale {
  // Valeur d'un demi-ton = 1/12 d'octave
  private static final double demiton_ = Math.pow(2, 1.0/12); // racine 12e de 2
  // Fréquence du la4
  private final double diapason_;
  // Fréquence du do "du milieu"
  private final double C4_;

  /**
   * Crée une nouvelle notation ABC avec <code>diapason</code>
   * comme fréquence du la noté "A" (2e interligne de la clef de sol).
   * @param diapason est la fréquence en Hertz du la du 2e interligne 
   *        de la clef de sol.
   */
  public NotationABC(double diapason) {
    diapason_ = diapason;
    // On détermine la fréquence du do qui est à 
    // une tierce (3 demi-tons) moins une octave du la
    C4_ = diapason_ * Math.pow(demiton_, 3) / 2;
  }

  /**
   * Détermine la fréquence d'une note notée en ABC.
   */
  public double frequence(String note) {
    // On commence par déterminer à combien de demi-tons
    // du do C4 se trouve la note.
    int offsetC = demiTonsDo(note);
    double freq = C4_;
    if (offsetC > 0) {
      // Si la note est au-dessus
      while (offsetC >= 12) {
        // on multiplie sa fréquence par 2 pour chaque octave
        freq *= 2;
        offsetC -= 12;
      }
      while (offsetC > 0) {
        // puis on la multiplie par 1/12 d'octave pour chaque demi-ton
        freq *= demiton_;
        offsetC --;
      }
    } else if (offsetC < 0) {
      // Si la note est en-dessous du do, on fait l'inverse
      while (offsetC <= -12) {
        freq /= 2;
        offsetC += 12;
      }
      while (offsetC < 0) {
        freq /= demiton_;
        offsetC ++;
      }
    }
    return freq;
  }

  /**
   * Détermine à combien de demi-tons du do C4 se trouve
   * une note notée en ABC. 
   */
  private int demiTonsDo(String note) {
    int offsetC = 0;
    int idx = 0;
    while (idx < note.length()) {
      switch (Character.toUpperCase(note.charAt(idx))) {
        case '_' :     // bemol
          offsetC --;
          break;
        case '^' :     // dièze
          offsetC ++;
          break;
        case '=' :     // bécarre
          break;
        case '\'' :    // octave supérieure
          offsetC += 12;
          break;
        case ',' :     // octave inférieure
          offsetC -= 12;
          break;
        case 'C' :
          break;
        case 'D' :
          offsetC += 2;
          break;
        case 'E' :
          offsetC += 4;
          break;
        case 'F' :
          offsetC += 5;
          break;
        case 'G' :
          offsetC += 7;
          break;
        case 'A' :
          offsetC += 9;
          break;
        case 'B' :
          offsetC += 11;
          break;
      }
      // Les minuscules indiquent l'octave supérieure
      if (Character.isLowerCase(note.charAt(idx))) {
        offsetC += 12;
      }
      idx++;
    }
    return offsetC;
  }
}
