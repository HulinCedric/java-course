//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Applications graphiques
//
// Classe MenuFichier - Description de la configuration de tous les items du 
//                      menu deroulant "Fichier" de l'application "Diaporama"
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;

public class MenuFichier {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configurer() {
   LinkedHashMap config= new LinkedHashMap();
   
      config.put("Charger", new Boolean(true));
         
      return config;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configurer();
   LinkedHashMap w= null;
   
      Config.store(config, "../MenuFichier", "1.0.0");
      w= (LinkedHashMap)Config.load("../MenuFichier", "1.0.0");
   }
}
