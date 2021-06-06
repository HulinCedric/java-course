//
// Annee 2009_2010 - Module Java - Feuille_2
//
// Module de tests unitaires - Classe Ville (Edition A / Version 1.0.0)
//
// Controles limités aux cas nominaux
//
public class Test_Ville_N {

   public static void main (String[] args) {
         
      Tests.Begin("Ville", "A - 1.0.0");

         Tests.Design("Construction et accesseurs",3);
            Tests.Case ("Accesseurs de consultation"); {
            Ville nice   = new Ville();
            Ville nantes = new Ville ("Nantes",250000);

               Tests.Unit("NICE", nice.getNom());
               Tests.Unit(new Integer(345000), nice.getPopulation());

               Tests.Unit("NANTES", nantes.getNom());   
               Tests.Unit(new Integer(250000), nantes.getPopulation());
            }
            
            Tests.Case ("Accesseurs de modification"); {
            Ville nice   = new Ville();
            Ville nantes = new Ville ("Nantes",250000);
            
               nice.setPopulation(375000);
               Tests.Unit(new Integer(375000), nice.getPopulation());
            }
                   
      Tests.End();
   }

}
