//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe CadreG - Visualisation d'un cadre entierement parametrable
//
// Edition A 		: cadre avec dictionnaire de configuration
//    + Version 1.0.0	: parametrage de la partie statique
//    + Version 1.1.0   : parametrage de la partie dynamique
//    + Version 1.2.0   : externalisation du dictionnaire de configuration
//                        (sans la partie dynamique)
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
      
      // Description dynamique non parametree
      //
      addWindowListener (new WindowAdapter() {	 
         public void windowClosing (WindowEvent e) {System.exit(0);}});   
   }
 
   public static void main(String[] args) {
   String prefixe= "*** Erreur : ";
   String erreur_1= "dictionnaire de configuration absent ou invalide";
   	
      // Charger le dictionnaire de configuration
      //   	
      HashMap config= (HashMap) Config.load("ConfigCadreG", "1.2.0");
   
      if(config == null) {
      	 System.out.println(prefixe+erreur_1);
      	 System.exit(1);
      }
         
      // Creer et montrer le cadre
      //
      new CadreG(config).show();	
   }

}
