//
// Annee 2009_2010 - Module Java - Feuille_2
//
// Edition A : spécifications initiale de la classe Ville
//
//    + Version 1.0.0 : version initiale
//    + Version 1.1.0 : gestion d'exceptions dans l'accesseur setPopulation
//    + Version 1.2.0 : gestion d'exceptions dans le constructeur normal
//    + Version 1.3.0 : mise en place d'une dérivation depuis une classe Cite
//    + Version 1.3.1 : prise en compte de modifications dans la classe Cite
//
public class Ville extends Cite {

   // Constructeur par defaut
   //
   public Ville () {super("NICE", 345000);}

   // Constructeur normal avec gestion des exceptions
   //
   public Ville (String nom, int population) throws NegativeValueException {	     
      super (nom, population);
      if (population < 0) throw new NegativeValueException(population);
   }

   // Accesseurs de modification
   //
   public void setPopulation (int population) throws NegativeValueException {  
   	  
      if (population < 0) {
         super._setPopulation(0);
         throw new NegativeValueException(population);
      }
      else super._setPopulation(population);
   }
}
