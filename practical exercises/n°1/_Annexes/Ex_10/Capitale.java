//
// Annee 2009_2010 - Module Java - Feuille 2
//
// Edition A : exigences fonctionnelles initiales
//
//    + Version 1.0.0 : version initiale
//
public class Capitale extends Ville {
private String pays;

   public Capitale (String nom, int population, String region, String pays) {
      super(nom, population, region);
      this.pays   = pays;
   }

   public String getPays () {return pays;}

   public static void main (String[] args) {          
      
   Tests.Begin ("Capitale", "A - 1.0.0");
      
      Tests.Design("Comportements specifiques d'une capitale",3);
         
         Tests.Case ("Accesseurs de consultation"); {
         Capitale rome = new Capitale ("Rome",5250000,"Latium", "Italie");
            Tests.Unit("Italie", rome.getPays());
         }
            
    Tests.End();

   }
}
