//
// Annee 2009_2010 - Module Java - Feuille_2
//
// Module de tests unitaires - Classe Ville (Edition A / Version 1.3.0)
//
// Controles limités aux cas nominaux
//
// Tests unitaires inchanges par rapport à ceux de la version 1.2.0 de la 
// classe Ville
//
public class Test_Ville_N {

   public static void main (String[] args) {
         
      Tests.Begin("Ville", "A - 1.3.0");

         Tests.Design("Construction et accesseurs",3);
            Tests.Case ("Accesseurs de consultation"); {
            
               Ville nice  = new Ville();
               Tests.Unit("NICE", nice.getNom());
               Tests.Unit(new Integer(345000), nice.getPopulation());

               Ville nantes= null;
               try {nantes= new Ville ("Nantes",250000);}
               catch (NegativeValueException e){}
               Tests.Unit("NANTES", nantes.getNom());   
               Tests.Unit(new Integer(250000), nantes.getPopulation());
            }
            
            Tests.Case ("Accesseurs de modification"); {
            Ville nice   = new Ville();
            
               try {nice.setPopulation(375000);}
               catch(NegativeValueException e){}
               Tests.Unit(new Integer(375000), nice.getPopulation());
            }
                   
      Tests.End();
   }

}
