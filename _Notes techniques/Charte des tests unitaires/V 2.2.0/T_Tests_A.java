/**
*
* Annee 2009_2010 - Module Java - Charte des tests unitaires
*
* Classe Tests - Module de tests unitaires (cas d'anomalies)
*
* @author Alain Thuaire
*
**/

public class T_Tests_A {

   public static void main (String[] args) {
        
      Tests.Begin("Tests", "2.2.0");

      Tests.Design("Visualisation de niveau 0", 0);
         
         Tests.Case("C1 : 1 TU NOK");			
            Tests.Unit("x", "y");
            Tests.Unit(new Integer(0), new Integer(0));

         Tests.Case("C2 : 2 TU / Second TU NOK");			
            Tests.Unit("x", "x");
            Tests.Unit(new Integer(0), new Integer(1)); 
                			       
      Tests.Design("Visualisation de niveau 1", 1);		
  
         Tests.Case("C1 : 1 TU NOK");
            Tests.Unit("niveau 1", "niveau 2");

      Tests.Design("Visualisation de niveau 2", 2);		
  
         Tests.Case("C1 : 2 TU OK");
            Tests.Unit("xxx", "xxx");
            Tests.Unit(new Integer(2), new Integer(2));

         Tests.Case("C2 : 3 TU / Troisieme TU NOK");
            Tests.Unit("niveau 1", "niveau 1");
            Tests.Unit(new Integer(1), new Integer(1));
            Tests.Unit(new Integer(-1), new Integer(-2));  

         Tests.Case("C3 : 2 TU OK");
            Tests.Unit("yyy", "yyy");
             
      Tests.Design("Visualisation de niveau 3", 3);		
  
         Tests.Case("C1 : 3 TU OK");
            Tests.Unit("......", "......");
            Tests.Unit(new Integer(3), new Integer(3));
            Tests.Unit(new Float(3), new Float(3));

         Tests.Case("C2 : 2 TU OK");
            Tests.Unit("xxxxx", "xxxxx");
            Tests.Unit(new Integer(-3), new Integer(-3)); 
            
         Tests.Case("C3 : 1 TU NOK");
            Tests.Unit(new Float(-3), new Float(3));
            
      Tests.Design("Traitement des references nulles", 3);		
  
         Tests.Case("Un seul parametre est null");
            Tests.Unit("...", null);
            Tests.Unit(null, "...");
                     
      Tests.End();

   }
}
