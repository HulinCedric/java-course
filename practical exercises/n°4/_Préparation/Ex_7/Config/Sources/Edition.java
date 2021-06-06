//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Edition - Description de la configuration de tous les items 
//                  du menu deroulant "Edition" de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Edition {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap edition= new LinkedHashMap();
   
      edition.put("Annuler frappe",   null);
      edition.put("Répéter frappe",   null);
      edition.put("___", null);
      edition.put("Couper", null);
      edition.put("Copier", null);
      edition.put("Presse-papiers Office...", null);
      edition.put("Coller",      null); 
      edition.put("Collage special...",   null);
      edition.put("Coller comme lien hyper texte", null);
      edition.put("____", null);
      edition.put("Effacer", null);
      edition.put("Sélectionner tout", null);
      edition.put("_____", null);
      edition.put("Rechercher...",      null);
      edition.put("Remplacer...", null);
      edition.put("Atteindre...", null);
      edition.put("______", null);
      edition.put("Liaisons...", null);
      edition.put("Objet",      null);
         
      return edition;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Edition", "1.0.0");
      w= (LinkedHashMap)Config.load("Edition", "1.0.0");
   }
}
