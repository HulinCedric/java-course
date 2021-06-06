//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Outils - Description de la configuration de tous les items 
//                 du menu deroulant "Outils" de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Outils {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap outils= new LinkedHashMap();
   
      outils.put("Grammaire et orthographe...",   null);
      outils.put("Langue",   null);
      outils.put("Réparer le texte interrompu...",   null);
      outils.put("Statistiques...",   null);
      outils.put("Synthèse automatique...",   null);
      outils.put("___", null);
      outils.put("Suivi des modifications",   null);
      outils.put("Comparaison et fusion de documents...",   null);
      outils.put("Protéger le document...", null);
      outils.put("Collaboration en ligne", null);
      outils.put("____", null);
      outils.put("Lettres et publipostage", null);
      outils.put("_____", null);
      outils.put("Outils sur le Web...", null);
      outils.put("Macro", null);
      outils.put("Modèles et compléments...", null);
      outils.put("Options de correction automatique...",      null);
      outils.put("Personnaliser...", null);
      outils.put("Options...", null);
         
      return outils;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Outils", "1.0.0");
      w= (LinkedHashMap)Config.load("Outils", "1.0.0");
   }
}
