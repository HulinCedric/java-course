//
// Annee 2009_2010 - Module Java - Feuille_2
//
// Edition A : spécifications initiale de la classe Cite
//
//    + Version 1.0.0 : version initiale
//
class Cite  {
private String nom;
private Integer laPopulation;
   
   // Constructeur normal
   //
   public Cite (String nom, int population) {	
      this.nom     = nom.toUpperCase();
   	  laPopulation= new Integer(population);
   }
  
   // Accesseurs de consultation
   //					
   public String  getNom() {return nom;} 
   public Integer getPopulation() {return laPopulation;}

   // Accesseurs de modification
   //
   public void setPopulation (int population) throws NegativeValueException {
      laPopulation=new Integer(population);
   }
}
