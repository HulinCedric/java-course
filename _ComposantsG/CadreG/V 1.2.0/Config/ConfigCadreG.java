//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe ConfigCadreG - Description de la configuration d'un cadre
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ConfigCadreG {
	
   // Description des parametres de configuration
   //   	
   public static HashMap configuration() {
   HashMap config= new HashMap();
      	
      config.put("titre",       "(AWT) Classe CadreG - V 1.2.0");
      config.put("largeur",     new Integer(500));
      config.put("hauteur",     new Integer(300));
      config.put("position",    new Dimension(400, 300));
      config.put("arrierePlan", Color.yellow);
      config.put("avantPlan",   Color.red);
      config.put("police",      new Font("Times Roman", Font.BOLD, 16));
         
      return config;
   }
   
   public static void main(String[] args) {
   HashMap config= configuration();
   HashMap w= null;
   
      Config.store(config, "ConfigCadreG", "1.2.0");
      w= (HashMap)Config.load("ConfigCadreG", "1.2.0");
   }
}
