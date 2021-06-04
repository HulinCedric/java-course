//
// Annee 2009_2010 - Module Java - Feuille_2
//
// Module de tests unitaires - Classe Cite (Edition A / Version 1.1.0)
//
// Controles limités aux cas nominaux
//
public class Test_Cite_N {

   public static void main (String[] args) {
         
      Tests.Begin("Cite", "A - 1.1.0");

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
            
               try {nantes.setPopulation(260000);}
               catch(NegativeValueException e){}
               Tests.Unit(new Integer(260000), nantes.getPopulation());
            }
                   
      Tests.End();
   }

}
