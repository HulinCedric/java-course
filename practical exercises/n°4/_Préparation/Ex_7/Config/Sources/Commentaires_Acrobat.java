//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Commentaires_Acrobat - Description de la configuration de tous les 
//                               items du menu deroulant "Commentaires_Acrobat" 
//                               de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Commentaires_Acrobat {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap commentaires= new LinkedHashMap();
   
      commentaires.put("Importer les commentaires d'Acrobat...",   null);
      commentaires.put("Poursuivre la procédure d'intégration",   null);
      commentaires.put("___", null);
      commentaires.put("Accepter toutes les modifications dans le document",   null);
      commentaires.put("Supprimer tous les commentaires du document",   null);
      commentaires.put("____", null);
      commentaires.put("Barre d'outils de révision",   null);
      commentaires.put("_____", null);
      commentaires.put("Afficher les instructions...",   null);
         
      return commentaires;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Commentaires_Acrobat", "1.0.0");
      w= (LinkedHashMap)Config.load("Commentaires_Acrobat", "1.0.0");
   }
}
