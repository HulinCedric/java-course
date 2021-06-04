//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Composants graphiques generiques
//
// Classe Panneau_B - Description de la configuration initiale d'un panneau
//
// Edition A    : sans image de fond ni label textuel associe
//    + Version 1.0.0  : IE_2 2008_2009 / Ex_4
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;

public class Panneau_B {
	
   // Description des parametres de configuration
   //   	
   public static HashMap configuration() {
   HashMap config= new HashMap();
      	
      config.put("arrierePlan",   Color.black);
      config.put("avantPlan",     Color.yellow);
      config.put("police",        new Font("Times Roman", Font.BOLD, 32));
         
      return config;
   }
   
   public static void main(String[] args) {
   HashMap config= configuration();
   HashMap w= null;
   
      Config.store(config, "../Panneau_B", "0.0.0");
      w= (HashMap)Config.load("../Panneau_B", "0.0.0");
   }
}
