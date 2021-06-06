//
// Annee 2009_2010 - Module Java - Feuille_2
//
//
public class NegativeValueException extends Exception {
private int theOrigin;

   public  NegativeValueException (int value) {				// Constructeur normal
      theOrigin = value;
   }
						
   public int getOrigin () {return theOrigin;}		// Accesseur

}
