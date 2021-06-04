//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants graphiques generiques
//
// Classe Panneau_A - Description de la configuration d'un panneau
//
// Edition A    : sans image de fond ni label textuel
//    + Version 0.0.0  : IE_2 2008_2009 / Ex_4
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;

public class Panneau_A {
	
   // Description des parametres de configuration
   //   	
   public static HashMap configuration() {
   HashMap config= new HashMap();
      	
      config.put("arrierePlan",   Color.black);
      config.put("avantPlan",     Color.yellow);
      config.put("police",        new Font("Times Roman", Font.BOLD, 12));
         
      return config;
   }
   
   public static void main(String[] args) {
   HashMap config= configuration();
   HashMap w= null;
   
      Config.store(config, "../Panneau_A", "0.0.0");
      w= (HashMap)Config.load("../Panneau_A", "0.0.0");
   }
}
