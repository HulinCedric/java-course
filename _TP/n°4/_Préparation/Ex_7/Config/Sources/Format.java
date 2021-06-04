//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Format - Description de la configuration de tous les items 
//                 du menu deroulant "Format" de l'application Word
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Format {
	
   // Description des parametres de configuration
   //   	
   public static LinkedHashMap configuration() {
   LinkedHashMap format= new LinkedHashMap();
   
      format.put("Police...",   null);
      format.put("Paragraphe...",   null);
      format.put("Puces et numéros...",   null);
      format.put("Bordure et trame...",   null);
      format.put("___", null);
      format.put("Colonnes...",   null);
      format.put("Tabulations...",   null);
      format.put("Lettrine...",   null);
      format.put("Orientation du texte...", null);
      format.put("Modifier la casse...", null);
      format.put("____", null);
      format.put("Arrière plan", null);
      format.put("Thèmes...", null);
      format.put("Cadres", null);
      format.put("Mise en forme automatique...", null);
      format.put("Styles et mises en forme...",      null);
      format.put("Révéler la mise en forme...", null);
      format.put("_____", null);
      format.put("Objet...", null);
         
      return format;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config= configuration();
   LinkedHashMap w= null;
   
      Config.store(config, "Format", "1.0.0");
      w= (LinkedHashMap)Config.load("Format", "1.0.0");
   }
}
