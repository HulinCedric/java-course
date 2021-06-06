//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Fenetre - Description de la configuration de tous les items 
//                  du menu deroulant "Fenetre" de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Fenetre {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap fenetre= new LinkedHashMap();
   
      fenetre.put("Nouvelle fenêtre",   null);
      fenetre.put("Réorganiser tout", null);
      fenetre.put("Fractionner",   null);
         
      return fenetre;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Fenetre", "1.0.0");
      w= (LinkedHashMap)Config.load("Fenetre", "1.0.0");
   }
}
