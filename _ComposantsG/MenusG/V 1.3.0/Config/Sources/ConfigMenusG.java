//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe ConfigMenusG - Description de la configuration d'une barre de menus
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ConfigMenusG {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configurer() {
   LinkedHashMap config= new LinkedHashMap();
   
      config.put("Fichier",   "1.0.0");
      config.put("Edition",   null);
      config.put("Format",    null);
      config.put("Affichage", null);
      config.put("?",         null); 
         
      return config;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configurer();
   LinkedHashMap w= null;
   
      Config.store(config, "../ConfigMenusG", "1.3.0");
      w= (LinkedHashMap)Config.load("../ConfigMenusG", "1.3.0");
   }
}
