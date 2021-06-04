//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuFichier - Description de la configuration de tous les items du 
//                      menu deroulant "Fichier" de l'application "Bloc notes"
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFichier {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configurer() {
   LinkedHashMap config= new LinkedHashMap();
   
      config.put("Nouveau",             null);
      config.put("Ouvrir...",           null);
      config.put("Enregistrer",         null);
      config.put("Enregistrer sous...", null); 
      config.put("-",                   null);
      config.put("Mise en page...",     null);
      config.put("Imprimer...",         null);
      config.put("--",                  null);
      config.put("Quitter",             null);
         
      return config;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configurer();
   LinkedHashMap w= null;
   
      Config.store(config, "MenuFichier", "1.0.0");
      w= (LinkedHashMap)Config.load("MenuFichier", "1.0.0");
   }
}
