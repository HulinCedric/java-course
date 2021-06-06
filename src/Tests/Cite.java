//
// Annee 2009_2010 - Module Java - Feuille_2
//
// Edition A : spécifications initiale de la classe Cite
//
//    + Version 1.0.0 : version initiale
//    + Version 1.1.0 : surcharge des méthodes equals et clone
//    + Version 1.1.1 : integration du module de tests unitaires des cas
//                      nominaux au sein de la classe
//    + Version 1.1.2 : modification de la signature formelle de la méthode 
//                      setPopulation (plus de gestion d'exceptions), avec
//                      changement obligatoire du nom de la méthode
//                      + introduction de la spécification protected pour toutes
//                      les methodes et constructeurs (exception obligatoire
//                      pour la méthode toString)
//
class Cite  {
private String nom;
private Integer laPopulation;
   
   // Constructeur normal
   //
   protected Cite (String nom, int population) {	
      this.nom    = nom.toUpperCase();
   	  laPopulation= new Integer(population);
   }
  
   // Accesseurs de consultation
   //					
   protected String  getNom() {return nom;} 
   protected Integer getPopulation() {return laPopulation;}

   // Accesseurs de modification
   //
   protected void _setPopulation (int population) {
   	  laPopulation=new Integer(population);
   }
   	  
   // Redefinition des methodes herites de la classe Object
   //
   protected boolean equals (Cite x) {
   	  if (getNom().equals(x.getNom()))
   	     if (getPopulation().equals(x.getPopulation())) return true;
   	     else return false;
   	  else return false; 
   }
   
   protected Object clone() {return new Cite(nom, laPopulation.intValue());} 
   
   public String toString() {return nom + " - " + laPopulation.toString();}
   
   public static void main (String[] args) {
         
      Tests.Begin("Cite", "A - 1.1.2");

         Tests.Design("Construction et clonage",3);
            Tests.Case ("Constructeur normal"); {
            
               Cite nantes = new Cite ("Nantes",250000);
               Tests.Unit("NANTES - 250000", nantes.toString());
            }
            
            Tests.Case ("Clonage"); {
            Cite nantes = new Cite ("Nantes",250000);
            
               Cite copie= (Cite)nantes.clone();
               Tests.Unit(new Boolean(true), new Boolean (copie.equals(nantes)));
               Tests.Unit(new Boolean(false), new Boolean(copie==nantes));
               Tests.Unit(nantes.toString(), copie.toString());
            }
            
         Tests.Design("Accesseurs",3);
                  	
            Tests.Case ("Accesseurs de consultation"); {
            Cite nantes = new Cite ("Nantes",250000);

               Tests.Unit("NANTES", nantes.getNom());   
               Tests.Unit(new Integer(250000), nantes.getPopulation());
            }
            
            Tests.Case ("Accesseurs de modification"); {
            Cite nantes = new Cite ("Nantes",250000);
            
               nantes._setPopulation(260000);
               Tests.Unit(new Integer(260000), nantes.getPopulation());
            }
                   
      Tests.End();
   }
}
