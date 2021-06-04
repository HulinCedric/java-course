//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Insertion - Description de la configuration de tous les items 
//                    du menu deroulant "Insertion" de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Insertion {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap insertion= new LinkedHashMap();
   
      insertion.put("Saut...",   null);
      insertion.put("Numéros de page...",   null);
      insertion.put("Date et heure...",   null);
      insertion.put("Insertion automatique",   null);
      insertion.put("Champ...",   null);
      insertion.put("Caractères spéciaux...",   null);
      insertion.put("Commentaire",   null);
      insertion.put("___", null);
      insertion.put("Référence", null);
      insertion.put("Composant Web...", null);
      insertion.put("____", null);
      insertion.put("Image", null);
      insertion.put("Diagramme...", null);
      insertion.put("Zone de texte", null);
      insertion.put("Fichier...", null);
      insertion.put("Objet...",      null);
      insertion.put("Signet...", null);
      insertion.put("Lien hypertexte", null);
         
      return insertion;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Insertion", "1.0.0");
      w= (LinkedHashMap)Config.load("Insertion", "1.0.0");
   }
}
