//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Fichier - Description de la configuration de tous les items 
//                  du menu deroulant "Fichier" de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Fichier {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap fichier= new LinkedHashMap();
   
      fichier.put("Nouveau...",   null);
      fichier.put("Ouvrir...",   null);
      fichier.put("Fermer", null);
      fichier.put("_____", null);
      fichier.put("Enregistrer", null);
      fichier.put("Enregistrer sous...",      null); 
      fichier.put("Enregistrer en tant que page Web...",   null);
      fichier.put("Rechercher...", null);
      fichier.put("______", null);
      fichier.put("Versions...", null);
      fichier.put("_______", null);
      fichier.put("Apercu de la page Web",      null);
      fichier.put("________", null);
      fichier.put("Mise en page...", null);
      fichier.put("Apercu avant impression", null);
      fichier.put("Imprimer...",      null);
      fichier.put("_________", null);
      fichier.put("Envoyer vers",      null);
      fichier.put("Propriétes", null);
      fichier.put("__________", null);
      fichier.put("Quitter", null);
         
      return fichier;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Fichier", "1.0.0");
      w= (LinkedHashMap)Config.load("Fichier", "1.0.0");
      //System.out.println(w.toString());
   }
}
