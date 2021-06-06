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
   
      config.put("Nouveau",             new Boolean(true));
      config.put("Ouvrir...",           new Boolean(true));
      config.put("Enregistrer",         new Boolean(false));
      config.put("Enregistrer sous...", new Boolean(false)); 
      config.put("-",                   null);
      config.put("Mise en page...",     new Boolean(false));
      config.put("Imprimer...",         new Boolean(false));
      config.put("--",                  null);
      config.put("Quitter",             new Boolean(true));
         
      return config;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configurer();
   LinkedHashMap w= null;
   
      Config.store(config, "../MenuFichier", "1.0.0");
      w= (LinkedHashMap)Config.load("../MenuFichier", "1.0.0");
   }
}
