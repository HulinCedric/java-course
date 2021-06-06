//
// Annee 2009_2010 - Module Java - Feuille_2
//
// Module de tests unitaires - Classe Ville (Edition A / Version 1.1.0)
//
// Controles limités aux cas d'anomalies
//
public class Test_Ville_A {

   public static void main (String[] args) {
        
      Tests.Begin("Ville", "A - 1.1.0");

         Tests.Design("Anomalies sur construction et/ou accesseurs",3);
            
            Tests.Case ("Accesseurs de modification"); {
            Ville nice   = new Ville();
            
               try {nice.setPopulation(-100);}
               catch(NegativeValueException e){
                  Tests.Unit(new Integer(-100), new Integer(e.getOrigin()));
               }
            }
                   
      Tests.End();
   }

}
