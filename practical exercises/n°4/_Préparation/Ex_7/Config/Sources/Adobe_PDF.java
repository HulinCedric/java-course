//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Adobe_PDF - Description de la configuration de tous les items 
//                    du menu deroulant "Adobe PDF" de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Adobe_PDF {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap adobe= new LinkedHashMap();
   
      adobe.put("Convertir en Adobe PDF",   null);
      adobe.put("Convertir en Adobe PDF et envoyer par messagerie",   null);
      adobe.put("Convertir en Adobe PDF et envoyer pour révision",   null);
      adobe.put("___", null);
      adobe.put("Modifier les options de conversion",   null);
         
      return adobe;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Adobe_PDF", "1.0.0");
      w= (LinkedHashMap)Config.load("Adobe_PDF", "1.0.0");
   }
}
