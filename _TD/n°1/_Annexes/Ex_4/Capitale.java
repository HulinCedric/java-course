//
// Annee 2009_2010 - Module S3_Java - Feuille_1 / Capitale.java
//
// Version_A : sans surcharge des attributs
//
public class Capitale extends Ville {
private String pays;

   public Capitale (String nom, int population, String pays) {
      super(nom, population);
      this.pays    = pays;
   }

   public String getPays () {return pays;}

}
