//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package SWING
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
   
      config.put("Fichier",   "1.0.0");
      config.put("Edition",   "1.0.0");
      config.put("Affichage", "1.0.0");
      config.put("Insertion", "1.0.0");
      config.put("Format",    "1.0.0");
      config.put("Outils",    "1.0.0");
      config.put("Tableau",   "1.0.0");
      config.put("Fenetre",   "1.0.0");
      config.put("Aide",      "1.0.0");
      config.put("Adobe_PDF", "1.0.0");
      config.put("Commentaires_Acrobat", "1.0.0");
         
      return config;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "ConfigMenusG", "1.5.0");
      w= (LinkedHashMap)Config.load("ConfigMenusG", "1.5.0");
   }
}
