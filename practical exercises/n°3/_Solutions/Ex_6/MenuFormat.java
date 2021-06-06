//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuFormat -  Description de la configuration de tous les items du 
//                      menu deroulant "Format" de l'application "Bloc notes"
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFormat {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configurer() {
   LinkedHashMap config= new LinkedHashMap();
   
      config.put("Retour automatique a la ligne", new Boolean(true));
      config.put("Police...",                     new Boolean(true));
         
      return config;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configurer();
   LinkedHashMap w= null;
   
      Config.store(config, "MenuFormat", "1.0.0");
      w= (LinkedHashMap)Config.load("MenuFormat", "1.0.0");
   }
}
