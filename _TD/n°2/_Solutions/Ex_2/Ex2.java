//
// Annee 2009_2010 - Module S3 - Feuille_3
//
// Tests unitaires des tableaux 
//
import java.util.Arrays;

public class Ex2 {

    public static void main (String[] args) {
    	
       Tests.Begin("Tableaux en Java", "A - 1.0.0");
       
          Tests.Design("Constructeurs et accesseurs",3);
          
             Tests.Case ("Tableaux a une dimension"); {
             int[] T1= {0, -1, 7, 4, 3};
             Integer[] T2= { new Integer(0), 
                             new Integer(-1), 
                             new Integer(2),  
                             new Integer(3)
                           };
             String[]  T3= { "abc",
                             new String("def")
                           };
            	
                Tests.Unit(new Integer(-1), new Integer(T1[1]));
                Tests.Unit(new Integer(5), new Integer(T1.length)); 
            		
                Tests.Unit(new Integer(2), T2[2]);
                Tests.Unit(new Integer(4), new Integer(T2.length));
            	
                Tests.Unit("def", T3[1]);
                Tests.Unit(new Integer(2), new Integer(T3.length));
             }
            
             Tests.Case ("Tableaux a deux dimensions"); {
             int[][] T1 = { {7, 3},
                            {2, 4} 
                          };
                          
             Integer[][] T2= { { new Integer(0),  new Integer(6), new Integer(2) }, 
                               { new Integer(-4), new Integer(3), new Integer(4) }, 
                               { new Integer(11), new Integer(7) } 
                             };
                              
             String[][] T3=  { { new String("bleu"),  new String("jaune")},
                               { new String("rouge"), new String("blanc")},
                               { "mauve" }
                             };
            	
            	Tests.Unit(new Integer(4), new Integer(T1[1][1]));
            	Tests.Unit(new Integer(2), new Integer(T1.length));
            	
            	Tests.Unit(new Integer(7), T2[2][1]);
            	Tests.Unit(new Integer(3), new Integer(T2.length));
            	Tests.Unit(new Integer(2), new Integer(T2[2].length));
            	
            	Tests.Unit("blanc", T3[1][1]);
            	Tests.Unit("mauve", T3[2][0]);
            	Tests.Unit(new Integer(3), new Integer(T3.length));
            	Tests.Unit(new Integer(1), new Integer(T3[2].length));
             }
           
             Tests.Case ("Tableaux numeriques a trois dimensions"); {
             int[][][] T1= { 
            	             { 
            	                {7,  3, 12}, 
            	                {2,  4}
            	             },
            	             
            	             {
            	                {-1, 0},
            	                {0,  1} 
            	             } 
            	           };
            	           
                Tests.Unit(new Integer(7),  new Integer(T1[0][0][0]));
                Tests.Unit(new Integer(3),  new Integer(T1[0][0][1]));
                Tests.Unit(new Integer(4),  new Integer(T1[0][1][1]));
            	
                Tests.Unit(new Integer(-1), new Integer(T1[1][0][0]));
                Tests.Unit(new Integer(1),  new Integer(T1[1][1][1]));
                
                Tests.Unit(new Integer(2), new Integer(T1.length));
                Tests.Unit(new Integer(2), new Integer(T1[0].length));
                Tests.Unit(new Integer(3), new Integer(T1[0][0].length));
              }
            	      
              Tests.Case ("Tableaux de messages a trois dimensions"); {     
              String[][][] T2= { 
                                 { 
                                   {"chat", "chien", "biche"},
                                   {"lion", "antilope", "buffle"}
                                 },
                                 
                                 {
                                   {"poule", "pie", "canard"},
                                   {"perroquet", "mouette", "aigle"}
                                 }
                               };
            	           
                 Tests.Unit("chat",  T2[0][0][0]);
                 Tests.Unit("buffle",T2[0][1][2]);
            	
                 Tests.Unit("pie",   T2[1][0][1]);
                 Tests.Unit("aigle", T2[1][1][2]);
                
                 Tests.Unit(new Integer(2), new Integer(T2.length));
                 Tests.Unit(new Integer(2), new Integer(T2[0].length));
                 Tests.Unit(new Integer(3), new Integer(T2[1][0].length));
              }
            
         Tests.Design("Methodes heritees de Object",3);
         	
            Tests.Case ("Methode clone"); {
            int[] T1 = {0, 6, 2, 4, 3};
            int[] T2 = (int[])T1.clone();
         		
               Tests.Unit(new Integer(T1[3]), new Integer(T2[3]));
         		
               T1[3]= -4;
               Tests.Unit(new Integer(4), new Integer(T2[3]));
            }
         
            Tests.Case ("Methode equals pour []"); {
            int[] T1 = {0, 6, 2, 4, 3};
            int[] T2 = {0, 6, 2, 4, 3};
         		
               Tests.Unit(new Boolean(false), new Boolean(T1.equals(T2)));
            }
         	
            Tests.Case ("Methode equals de la classe Arrays"); {
            int[] T1 = {0, 6, 2, 4, 3};
            int[] T2 = {0, 6, 2, 4, 3};
         		
               Tests.Unit(new Boolean(true), new Boolean(Arrays.equals(T1, T2)));
            }
         		
         Tests.Design("Compatibilite avec les services de la classe Arrays",3);
         	
            Tests.Case ("Service fill"); {
            int[] T1 = new int[10];
            String[] T2 = new String[7];
         		
               Arrays.fill(T1,5);
               Tests.Unit(new Integer(5), new Integer(T1[8]));
         		
               Arrays.fill(T2, 1, 3, "bleu");
               Tests.Unit("bleu", T2[2]);
               Tests.Unit(null, T2[0]);
               Tests.Unit(null, T2[6]);
            }
          	
            Tests.Case ("Methode sort"); {
            int[]    T1= {0, 6, 2, -4, 3, 8, -11, 0, 1};
            String[] T2= { "bleu", "rouge", "blanc", "vert", "mauve", "indigo"};
    
               Arrays.sort(T1);
               Tests.Unit(new Integer(-11), new Integer(T1[0]));
               Tests.Unit(new Integer(0), new Integer(T1[2]));
         		
               Arrays.sort(T2);
               Tests.Unit("blanc", T2[0]);
               Tests.Unit("vert",  T2[5]);
            }
 
       Tests.End();
    }
}

