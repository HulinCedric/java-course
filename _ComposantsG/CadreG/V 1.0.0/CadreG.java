//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe CadreG - Visualisation d'un cadre entierement parametrable
//
// Edition A 		: cadre avec dictionnaire de configuration
//    + Version 1.0.0	: parametrage de la partie statique
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CadreG extends Frame {

   public CadreG (HashMap config) {
   	
      // Description statique parametree
      //
      int largeur= ((Integer)config.get("largeur")).intValue();
      int hauteur= ((Integer)config.get("hauteur")).intValue();
      setSize(largeur, hauteur);
      
      String titre= (String)config.get("titre");
      setTitle (titre);
      
      Dimension position= (Dimension)config.get("position");
      int abscisse= (int)position.getWidth();
      int ordonnee= (int)position.getHeight();
      setLocation (abscisse, ordonnee);
      
      Color arrierePlan= (Color)config.get("arrierePlan");
      Color avantPlan= (Color)config.get("avantPlan");
      
      setBackground(arrierePlan);
      setForeground(avantPlan);
      
      Font police= (Font)config.get("police");
      setFont(police);
      
      // Description dynamique
      //
      addWindowListener (new WindowAdapter() {	 
         public void windowClosing (WindowEvent e) {System.exit(0);}});  
   }
 
   public static void main(String[] args) {
   	
   	  // Descrire les parametres de configuration
      //   	
      HashMap config= new HashMap();
      	
      config.put("titre",       "(AWT) Classe CadreG - V 1.0.0");
      config.put("largeur",     new Integer(500));
      config.put("hauteur",     new Integer(300));
      config.put("position",    new Dimension(400, 300));
      config.put("arrierePlan", Color.yellow);
      config.put("avantPlan",   Color.red);
      config.put("police",      new Font("Times Roman", Font.BOLD, 16));
      
      // Creer et montrer le cadre
      //      
      new CadreG(config).show();	
   }

}
