//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe ConfigMenusG - Description de la configuration d'une barre de menus
//
// Auteur : A. Thuaire, C. Hulin
//

import java.util.*;

public class ConfigMenusG {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap config= new LinkedHashMap();
   
      config.put("Fichier",   	"1.0.0");
      config.put("Edition", 	"1.0.0");
      config.put("Format", 		"1.0.0");
      config.put("Affichage",	"1.0.0");
      config.put("?",      		"1.0.0"); 
         
      return config;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   
      Config.store(config, "ConfigMenusG", "1.2.0");
      Config.load("ConfigMenusG", "1.2.0");
   }
}
