//
// Annee 2006_2007 - Module Java
//
// Tests unitaires de la classe Config - Version 1.1.0
//

import java.util.*;
import java.io.*;
import java.awt.*;

public class TestConfig {
	
public static void main (String [] args) {
HashMap cadre= new HashMap();
   cadre.put("abscisse", new Integer(0));
   cadre.put("ordonnee", new Integer(0));
   cadre.put("hauteur",  new Integer(730));
   cadre.put("largeur",  new Integer(1020));     
   cadre.put("fond",     Color.blue);
   cadre.put("texte",    Color.yellow);
   cadre.put("titre",   "Fenetre test");
            
LinkedHashMap pan= new LinkedHashMap();
   pan.put("Boutons radio" , Color.black);
   pan.put("Cases a cocher", Color.white);
   pan.put("Canvas"        , Color.red);   
      
   Tests.Begin("Config", "A - 1.1.0");
      Tests.Design("Service store", 3);
         Tests.Case("Cas nominal avec un HashMap");
            Tests.Unit(new Boolean (true), 
                       new Boolean(Config.store(cadre,"cadre", "1.0.0")));               	
         
         Tests.Case("Cas nominal avec un LinkedHashMap");
            Tests.Unit(new Boolean (true), 
                       new Boolean(Config.store(pan,"panneaux", "1.0.0")));
         
         Tests.Case("Cas nominal compose");     
            cadre.put("panneaux", pan);
            Tests.Unit(new Boolean (true), 
                       new Boolean(Config.store(cadre,"cadre", "2.0.0")));                   	
            
         Tests.Case("Cas d'anomalie : reference nulle");
            Tests.Unit(new Boolean (false), 
                       new Boolean(Config.store(null,"erreur","1.0.0")));                     	
            
         Tests.Case("Cas d'anomalie : configuration invalide");{
               
            Tests.Unit(new Boolean (false), 
                       new Boolean(Config.store("xxx","erreur","2.0.0")));                     	
         }
                   
      Tests.Design("Service load", 3);
         
         Tests.Case("Cas nominal avec un HashMap"); {
         HashMap config= (HashMap) Config.load("cadre", "1.0.0");
            
            Tests.Unit("Fenetre test", (String)config.get("titre")); 
         }
         
         Tests.Case("Cas nominal avec un LinkedHashMap"); {
         LinkedHashMap config= (LinkedHashMap)Config.load("panneaux", "1.0.0");
            
            Tests.Unit(Color.white, (Color)config.get("Cases a cocher")); 
         }
            
         Tests.Case("Cas nominal compose"); {
         HashMap config= (HashMap)Config.load("cadre", "2.0.0");
         LinkedHashMap panneaux= (LinkedHashMap)config.get("panneaux");
            
            Tests.Unit(Color.red, (Color)panneaux.get("Canvas")); 
         }
      
      Tests.End();
    }       

}
