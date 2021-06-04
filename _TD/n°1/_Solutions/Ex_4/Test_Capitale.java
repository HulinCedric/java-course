//
// Annee 2009_2010 - Module S3 - Feuille_1 / Test_Capitale.java
//
// Module de tests unitaires - Classe Capitale 
//
public class Test_Capitale {

   public static void main (String[] args) {
   Capitale Rome = new Capitale ("Rome",5250000,"Italie");          
      
   Tests.Begin ("Capitale", "1.0.0");

      Tests.Design("Comportements herites de la classe Ville",2);
         Tests.Case ("Accesseurs de consultation");
            Tests.Unit("ROME", Rome.getNom());
            Tests.Unit(new Integer(5250000), Rome.getPopulation());

         Tests.Case ("Accesseurs de modification"); {
         Capitale Paris = new Capitale ("Paris",2000000,"France");
            Paris.setPopulation(2200000);
            Tests.Unit(new Integer(2200000), Paris.getPopulation());
         }
      
      Tests.Design("Comportements specifiques d'une capitale",3);
         Tests.Case ("Accesseurs de consultation");
            Tests.Unit("Italie", Rome.getPays());

    Tests.End();

   }

}
