//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
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
   public static LinkedHashMap configuration() {
   LinkedHashMap config= new LinkedHashMap();
   
      config.put("Fichier",   null);
      config.put("Edition",   null);
      config.put("Affichage", null);
      config.put("Insertion", null);
      config.put("Aide",      null); 
         
      return config;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "ConfigMenusG", "1.0.0");
      w= (LinkedHashMap)Config.load("ConfigMenusG", "1.0.0");
   }
}
