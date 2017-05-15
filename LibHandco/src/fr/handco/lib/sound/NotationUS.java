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
 * La notation US repr�sente les notes par une lettre :
 *   A = la
 *   B = si
 *   C = do
 *   D = r�
 *   E = mi
 *   F = fa
 *   G = sol
 * Un b�mol est indiqu� par un 'b' plac� apr�s la note.
 * Un di�se est indiqu� par un '#' plac� apr�s la note.
 * L'octave dans laquelle se trouve la note est indiqu�e
 * par un chiffre situ� apr�s le nom de la note. 
 * Les changements d'octave se font sur la note do.
 * Le do situ� une ligne en-dessous de la clef de sol est not� "C4" 
 * et "B4" d�signe le si situ� sur la 3e ligne de la clef de sol.
 * Le do qui se situe juste au dessus est not� "C5".
 * Ainsi, "C2" d�signe le do grave du violoncelle, situ� sur 
 * la deuxi�me ligne en-dessous de la clef de fa, alors 
 * que "Eb6" d�signe le mi b�mol aigu situ� sur la 3e ligne 
 * au-dessus de la clef de sol.
 * Ces num�ros d'octave d�butent � 0, "C0" �tant la note
 * la plus grave pouvant �tre repr�sent�e. Ils sont sup�rieurs
 * d'une unit� aux num�ros d'octave de la notation fran�aise, qui
 * utilise le num�ro -1 pour l'octave la plus grave.
 */
class NotationUS implements NotationMusicale {
  // Valeur d'un demi-ton = 1/12 d'octave
  private static final double demiton_ = Math.pow(2, 1.0/12); // racine 12e de 2
  // Fr�quence du la4
  private final double diapason_;
  // Fr�quence du do "du milieu"
  private final double C4_;

  /**
   * Cr�e une nouvelle notation US avec <code>diapason</code>
   * comme fr�quence du la not� "A4" (2e interligne de la clef de sol).
   * @param diapason est la fr�quence en Hertz du la du 2e interligne 
   *        de la clef de sol.
   */
  public NotationUS(double diapason) {
    diapason_ = diapason;
    // On d�termine la fr�quence du do qui est � 
    // une tierce (3 demi-tons) moins une octave du la
    C4_ = diapason_ * Math.pow(demiton_, 3) / 2;
  }

  /**
   * D�termine la fr�quence d'une note not�e en ABC.
   */
  public double frequence(String note) {
    // On commence par d�terminer � combien de demi-tons
    // du do C4 se trouve la note.
    int offsetC = demiTonsDo(note);
    double freq = C4_;
    if (offsetC > 0) {
      // Si la note est au-dessus
      while (offsetC >= 12) {
        // on multiplie sa fr�quence par 2 pour chaque octave
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
   * D�termine � combien de demi-tons du do C4 se trouve
   * une note not�e en ABC. 
   */
  private int demiTonsDo(String note) {
    int offsetC = 0;
    int idx = 0;
    while (idx < note.length()) {
      switch (note.charAt(idx)) {
        case 'b' :     // b�mol
          offsetC --;
          break;
        case '#' :     // di�ze
          offsetC ++;
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
        default:
          if (!Character.isDigit(note.charAt(idx))) {
            throw new Error("Symbole invalide dans un nom de note");
          }
          // on trouve le num�ro d'octave
          int i = Integer.parseInt("" + note.charAt(idx));
          // notre octave de r�f�rence est l'octave 4, 
          // et il y a 12 demi-tons par octave.
          offsetC += (i - 4) * 12;
      }
      idx++;
    }
    return offsetC;
  }
}
