//
// Annee 2009_2010 - Module Java - Feuille_2
//
// Edition A : spécifications initiale de la classe Ville
//
//    + Version 1.0.0 : version initiale
//    + Version 1.1.0 : gestion d'exceptions dans l'accesseur setPopulation
//
public class Ville  {
private String nom;
private Integer laPopulation;

   // Constructeur par defaut
   //
   public Ville () {	
      this.nom     = "NICE";
      laPopulation = new Integer(345000);
   }

   // Constructeur normal
   //
   public Ville (String nom, int population) {	
      this.nom     = nom.toUpperCase();
      laPopulation = new Integer(population);
   }
  
   // Accesseurs de consultation
   //					
   public String  getNom() {return nom;} 
   public Integer getPopulation() {return laPopulation;}

   // Accesseurs de modification
   //
   public void setPopulation (int population) throws NegativeValueException {
   	   
      if (population < 0) {
         laPopulation= new Integer(0);		// Traitement local de l'exception
         throw new NegativeValueException(population);
      }
      else laPopulation=new Integer(population);
   }
}
