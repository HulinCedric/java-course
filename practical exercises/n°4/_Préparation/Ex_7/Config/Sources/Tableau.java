//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Tableau - Description de la configuration de tous les items 
//                  du menu deroulant "Tableau" de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tableau {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap tableau= new LinkedHashMap();
   
      tableau.put("Dessiner un tableau",   null);
      tableau.put("___", null);
      tableau.put("Insérer",   null);
      tableau.put("Supprimer",   null);
      tableau.put("Sélectionner",   null);
      tableau.put("Fusionner les cellules",   null);
      tableau.put("Fractionner les cellules...",   null);
      tableau.put("Fractionner le tableau",   null);
      tableau.put("____", null);
      tableau.put("Tableau : Format automatique...", null);
      tableau.put("Ajustement automatique", null);
      tableau.put("Titres", null);
      tableau.put("_____", null);
      tableau.put("Convertir", null);
      tableau.put("Trier...", null);
      tableau.put("Formule...", null);
      tableau.put("Masquer le quadrillage",      null);
      tableau.put("______", null);
      tableau.put("Propriétés du tableau...", null);
         
      return tableau;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Tableau", "1.0.0");
      w= (LinkedHashMap)Config.load("Tableau", "1.0.0");
   }
}
