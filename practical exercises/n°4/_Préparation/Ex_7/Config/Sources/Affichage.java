//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Affichage - Description de la configuration de tous les items 
//                    du menu deroulant "Affichage" de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Affichage {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap affichage= new LinkedHashMap();
   
      affichage.put("Normal",   null);
      affichage.put("Web",   null);
      affichage.put("Page",   null);
      affichage.put("Plan",   null);
      affichage.put("___", null);
      affichage.put("Volet Office", null);
      affichage.put("Barre d'outils", null);
      affichage.put("R�gle", null);
      affichage.put("Explorateur de documents",      null); 
      affichage.put("____", null);
      affichage.put("En t�te et pied de page", null);
      affichage.put("Notes de bas de page", null);
      affichage.put("Balise", null);
      affichage.put("_____", null);
      affichage.put("Plein �cran",      null);
      affichage.put("Zoom...", null);
         
      return affichage;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Affichage", "1.0.0");
      w= (LinkedHashMap)Config.load("Affichage", "1.0.0");
   }
}
