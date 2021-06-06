//
// Annee 2009_2010 - Module Java - Feuille_2
//
// Module de tests unitaires - Classe Ville (Edition A / Version 1.3.0)
//
// Controles limités aux cas d'anomalies
//
// Tests unitaires inchanges par rapport à ceux de la version 1.2.0 de la 
// classe Ville
//
public class Test_Ville_A {

   public static void main (String[] args) {
        
      Tests.Begin("Ville", "A - 1.3.0");

         Tests.Design("Anomalies sur construction et/ou accesseurs",3);
            
            Tests.Case ("Anomalie sur construction"); {

               Ville nantes= null;
               try {nantes= new Ville ("Nantes",-500);}
               catch (NegativeValueException e){   
                  Tests.Unit(new Integer(-500), new Integer(e.getOrigin()));
               }
            }
            
            Tests.Case ("Anomalie sur modification"); {
            Ville nice   = new Ville();
            
               try {nice.setPopulation(-100);}
               catch(NegativeValueException e1){
               	  try {nice.setPopulation(5000);}
                  catch(NegativeValueException e2){}
                  Tests.Unit(new Integer(5000), nice.getPopulation());
               }
            }
                   
      Tests.End();
   }

}
