//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuAffichage -  Description de la configuration de tous les items 
//                         du menu deroulant "Affichage" de l'application 
//                         "Bloc notes"
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class MenuAffichage {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configurer() {
   LinkedHashMap config= new LinkedHashMap();
   
      config.put("Barre d'etat", new Boolean(true));
         
      return config;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configurer();
   LinkedHashMap w= null;
   
      Config.store(config, "MenuAffichage", "1.0.0");
      w= (LinkedHashMap)Config.load("MenuAffichage", "1.0.0");
   }
}
