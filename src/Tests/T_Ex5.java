//
// Annee 2009_2010 - Module S3 - IE_1
//
// Ex5 - Tests unitaires d'une nouvelle forme de la methode Unit 
//

import java.util.*;

public class T_Ex5 {

    public static void main (String[] args) {
    	
       Tests.Begin("Tests", "2.7.0");
       
          Tests.Design("Glossaires",3);
          
             Tests.Case ("Glossaires vides"); {
             HashMap G1= new HashMap();
             HashMap G2= new HashMap();
                           
            	Tests.Unit(G1, G2);     
             }
          
             Tests.Case ("Glossaires de deux associations"); {
             HashMap G1= new HashMap();
             HashMap G2= new HashMap();
             
                G1.put("cle A", "associe 1");
                G1.put("cle B", "associe 2");
                
                G2.put("cle B", "associe 2");
                G2.put("cle A", "associe 1");
                           
            	Tests.Unit(G1, G2);     
             }
             
             Tests.Case ("Glossaires de deux associations avec clonage"); {
             HashMap G1= new HashMap();
             HashMap G2= new HashMap();
             
                G1.put("cle A", "associe 1");
                G1.put("cle B", "associe 2");
                           
            	G2= (HashMap) G1.clone();
            	Tests.Unit(G1, G2);     
             }
             
             Tests.Case ("Glossaires de fleurs !"); {
             HashMap G1= new HashMap();
             HashMap G2= new HashMap();
             
                G1.put("cle A", "rose");
                G1.put("cle B", "coquelicot");
                G1.put("cle C", "pivoine");
                
                G2.put("cle C", "pivoine");
                G2.put("cle A", "rose");
                G2.put("cle B", "coquelicot");
                           
            	Tests.Unit(G1, G2);     
             } 
             
             Tests.Case ("Deux references globales nulles !"); {
                          
            	Tests.Unit((HashMap)null, (HashMap)null);     
             }   
 
       Tests.End();
   }
} 
