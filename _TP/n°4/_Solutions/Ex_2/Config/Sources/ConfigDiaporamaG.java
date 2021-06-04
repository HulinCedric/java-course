//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package SWING
//
// Classe ConfigDiaporamaG - Description de la configuration d'une fenetre
//                           de visualisation d'un diaporama
//
// Edition A    : TD_5
//
//    + Version 1.0.0  : version initiale (TD_5 / Ex_5)
//    + Version 1.1.0  : conformite avec TD_5 / Ex_8
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ConfigDiaporamaG {
	
   // Description des parametres de configuration
   //   	
   public static HashMap configuration() {
   HashMap config= new HashMap();
      	
      config.put("titre",       "(SWING) Classe DiaporamaG - V 1.5.0");
      config.put("arrierePlan",   Color.black);
      config.put("avantPlan",     Color.yellow);
      config.put("police",        new Font("Times Roman", Font.BOLD, 32));
      config.put("execMode",      new Boolean(true));
         
      return config;
   }
   
   public static void main(String[] args) {
   HashMap config= configuration();
   HashMap w= null;
   
      Config.store(config, "ConfigDiaporamaG", "1.1.0");
      w= (HashMap)Config.load("ConfigDiaporamaG", "1.1.0");
   }
}
