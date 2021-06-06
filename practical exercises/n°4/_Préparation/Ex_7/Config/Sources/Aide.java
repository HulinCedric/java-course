//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Aide - Description de la configuration de tous les items 
//               du menu deroulant "?" de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Aide {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap aide= new LinkedHashMap();
   
      aide.put("Aide sur Microsoft Word",   null);
      aide.put("Afficher le Compagnon Office",   null);
      aide.put("___", null);
      aide.put("Qu'est ce que c'est ?",   null);
      aide.put("Microsoft Office sur le Web",   null);
      aide.put("Activer le produit...",   null);
      aide.put("____", null);
      aide.put("Détecter et réparer...", null);
      aide.put("_____", null);
      aide.put("A propos de Microsoft Word", null);
         
      return aide;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Aide", "1.0.0");
      w= (LinkedHashMap)Config.load("Aide", "1.0.0");
   }
}
