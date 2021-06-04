//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Applications graphiques
//
// Classe ConfigPanneauG - Description de la configuration du panneau principal
//                         (classe PanneauG) pour un diaporama
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ConfigPanneauG {
	
   // Description des parametres de configuration
   //   	
   public static HashMap configurer() {
   HashMap config= new HashMap();
      	
      config.put("arrierePlan", Color.black);
      config.put("avantPlan",   Color.gray);
      config.put("police",      new Font("Times Roman", Font.BOLD, 32));
         
      return config;
   }
   
   public static void main(String[] args) {
   HashMap config= configurer();
   HashMap w= null;
   
      Config.store(config, "../ConfigPanneauG", "1.0.0");
      w= (HashMap)Config.load("../ConfigPanneauG", "1.0.0");
   }
}
