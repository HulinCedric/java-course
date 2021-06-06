//
// Annee 2009_2010 - Module S3_Java - Feuille_1 / Ville.java
//
// Version A : tous les attributs sont en mode "private"
//
public class Ville {
private String nom;
private Integer laPopulation;

   public Ville () {				// Constructeur par defaut
      this.nom     = "NICE";
      laPopulation = new Integer(345000);
   }

   public Ville (String nom, int population) {	// Constructeur normal
      this.nom     = nom.toUpperCase();
      laPopulation = new Integer(population);
   }
						// Accesseurs
   public String  getNom        () {return nom;}
   public Integer getPopulation () {return laPopulation;}
   public void    setPopulation (int population) laPopulation=new Integer(population);
}
