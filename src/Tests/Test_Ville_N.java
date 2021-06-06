//
// Annee 2009_2010 - Module Java - Feuille_2
//
// Module de tests unitaires - Classe Ville (Edition A / Version 2.0.0)
//
// Controles limités aux cas nominaux
//
public class Test_Ville_N {

   public static void main (String[] args) {
         
      Tests.Begin("Ville", "A - 2.0.0");

         Tests.Design("Construction et clonage",3);
         
            Tests.Case ("Constructeur par defaut"); {
            	
               Ville nice= new Ville();
               Tests.Unit("NICE - 345000", nice.toString());
            }
            
            Tests.Case ("Constructeur normal"); {
            
               Ville nantes = null;
               try {nantes= new Ville ("Nantes",250000);}
               catch (NegativeValueException e){}
               Tests.Unit("NANTES - 250000", nantes.toString());
            }
            
            Tests.Case ("Clonage"); {
            Ville nice = null;
            
               try {nice= new Ville ("Nice",375000);}
               catch (NegativeValueException e){}
            
               Cite copie= (Cite)nice.clone();  // cast (Ville) impossible ! 
               Tests.Unit(new Boolean(true), new Boolean (copie.equals(nice)));
               Tests.Unit(new Boolean(false), new Boolean(copie==nice));
               Tests.Unit(nice.toString(), copie.toString());
            }
         
         Tests.Design("Accesseurs",3);
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
