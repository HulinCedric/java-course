//
// Annee 2006_2007 - Module Java
//
// Tests unitaires de la classe Data - Version 1.0.0
//
//

import java.util.*;
import java.io.*;
import java.awt.*;

public class TestData {
	
public static void main (String [] args) {     
   Tests.Begin("Data", "A - 1.0.0");
      Tests.Design("Enregistrer une donnee simple", 3);
         Tests.Case("Cas nominal avec des classes enveloppes"); {
         Integer w1= new Integer(-123);
         String  w2= "xxx";
         
            Tests.Unit(new Boolean (true), 
                       new Boolean(Data.store(w1,"unEntier", "1.0.0")));
                    
            Tests.Unit(new Boolean (true), 
                       new Boolean(Data.store(w2,"uneChaine", "1.0.0"))); 
         }
         
         Tests.Case("Cas nominal avec des classes graphiques"); {
         Color w1= Color.black;
         Font  w2= new Font("Times Roman", Font.BOLD, 36);
         
            Tests.Unit(new Boolean (true), 
                       new Boolean(Data.store(w1,"uneCouleur", "1.0.0")));
                    
            Tests.Unit(new Boolean (true), 
                       new Boolean(Data.store(w2,"unePolice", "1.0.0"))); 
         }                  	
            
         Tests.Case("Cas d'anomalie : reference nulle");{
         HashMap erreur= null;
               
            Tests.Unit(new Boolean (false), 
                       new Boolean(Data.store(erreur,"erreur","1.0.0")));                     	
         }
         
         Tests.Case("Cas d'anomalie : objet non serialisable"); {
         Enumeration env= System.getProperties().propertyNames();
         
            Tests.Unit(new Boolean (false), 
                       new Boolean(Data.store(env,"env","1.0.0")));   
         }
         
      Tests.Design("Enregistrer une donnee composee", 3);
         Tests.Case("Cas nominal d'un tableau"); {
         Integer[] tab= {new Integer(0), new Integer(1), new Integer(-1)};
            Tests.Unit(new Boolean (true), 
                       new Boolean(Data.store(tab,"tab","1.0.0"))); 
         }
          
         Tests.Case("Cas nominal d'un dictionnaire simple"); {
         LinkedHashMap dico= new LinkedHashMap();
         
            dico.put("cle_1", "associe_1");
            dico.put("cle_2", "associe_2");
            dico.put("cle_3", "associe_3");
            
            Tests.Unit(new Boolean (true), 
                       new Boolean(Data.store(dico,"dico","1.0.0"))); 
         }
         
         Tests.Case("Cas nominal d'un dictionnaire complexe"); {
         HashMap ecrivains= new HashMap();
            ecrivains.put("Victor Hugo"           , null);
            ecrivains.put("Emile Zola"            , "Au bonheur des dames");
            ecrivains.put("Charles Baudelaire"    , "Les fleurs du mal");
            ecrivains.put("Guillaume Apollinaire" , "Alcools");
            ecrivains.put("Alain Fournier"        , "Le Grand Meaulnes");
            
         LinkedHashMap hugo= new LinkedHashMap();
            hugo.put("Les miserables"            , "1862");
            hugo.put("La legende des siecles"    , "1859");
            hugo.put("Napoleon le Petit"         , "1852");
            hugo.put("Les travailleurs de la mer", "1866");
            
            ecrivains.put("Victor Hugo", hugo);
            Tests.Unit(new Boolean (true), 
                       new Boolean(Data.store(ecrivains,"ecrivains","1.0.0")));   
         }
         
      Tests.Design("Charger une donnee simple", 3);
         Tests.Case("Cas nominal avec des classes enveloppes"); {
         
            Tests.Unit(new Integer(-123), 
                       (Integer)Data.load("unEntier", "1.0.0"));
                    
            Tests.Unit("xxx", 
                       (String)Data.load("uneChaine", "1.0.0")); 
         }
         
      Tests.Design("Charger une donnee composee", 3);
         Tests.Case("Cas nominal d'un tableau"); {
         Integer[] tab= (Integer[])Data.load("tab","1.0.0");
            
            Tests.Unit(new Integer(-1), tab[2]); 
         }
         
         Tests.Case("Cas nominal d'un dictionnaire simple"); {
         LinkedHashMap dico=(LinkedHashMap)Data.load("dico","1.0.0");
            
            Tests.Unit("associe_2", (String)dico.get("cle_2"));   
         }
         
         Tests.Case("Cas nominal d'un dictionnaire complexe"); {
         HashMap auteurs=(HashMap)Data.load("ecrivains","1.0.0");
         LinkedHashMap oeuvres= (LinkedHashMap)auteurs.get("Victor Hugo");
            
            Tests.Unit("1862", (String)oeuvres.get("Les miserables"));   
         }
          
      Tests.End();
    }       

}
