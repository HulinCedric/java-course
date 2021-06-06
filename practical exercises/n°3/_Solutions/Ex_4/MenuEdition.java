//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuEdition - Description de la configuration de tous les items du 
//                      menu deroulant "Edition" de l'application "Bloc notes"
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class MenuEdition {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configurer() {
   LinkedHashMap config= new LinkedHashMap();
   
      config.put("Annuler",               new Boolean(false));
      config.put("-",                     null);
      config.put("Couper",                new Boolean(false));
      config.put("Copier",                new Boolean(false));
      config.put("Coller",                new Boolean(false));
      config.put("Supprimer",             new Boolean(false)); 
      config.put("--",                    null);
      config.put("Rechercher...",         new Boolean(true));
      config.put("Rechercher le suivant", new Boolean(true));
      config.put("Remplacer...",          new Boolean(true));
      config.put("Atteindre...",          new Boolean(true));
      config.put("---",                   null);
      config.put("Selectionner tout",     new Boolean(true));
      config.put("Heure/Date",            new Boolean(true));
         
      return config;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configurer();
   LinkedHashMap w= null;
   
      Config.store(config, "MenuEdition", "1.0.0");
      w= (LinkedHashMap)Config.load("MenuEdition", "1.0.0");
   }
}
