//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package SWING
//
// Classe CadreG - Visualisation d'un cadre entierement parametrable
//
// Edition A 		: cadre AWT avec dictionnaire de configuration
//    + Version 1.0.0	: parametrage de la partie statique
//    + Version 1.1.0   : parametrage de la partie dynamique
//    + Version 1.2.0   : externalisation du dictionnaire de configuration
//                        (sans la partie dynamique)
//    + Version 1.3.0   : introduction de la gestion de parametres par defaut
//
// Edition B        : cadre SWING avec dictionnaire de configuration
//    + Version 2.0.0   : version initiale
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class CadreG extends JFrame {

   public CadreG (HashMap config) {
   	
      // Description statique parametree
      //
      setSize(config);
      setTitle(config);
      setLocation(config);
      setBackground(config);
      setForeground(config);
      setFont(config);   
   }
   
   private void setSize(HashMap config) {
   Dimension tailleEcran;
   int largeur, hauteur;
   Object w;
   
      tailleEcran= Toolkit.getDefaultToolkit().getScreenSize();
      largeur = (int)tailleEcran.getWidth();
      hauteur = (int)tailleEcran.getHeight();
   
      if (config==null) {setSize(largeur, hauteur); return;}
      w= config.get("largeur");
      if (w!=null) largeur=((Integer)w).intValue();
      w= config.get("hauteur"); 
      if (w!=null) hauteur=((Integer)w).intValue();
      setSize(largeur, hauteur);
   }
   
   private void setTitle(HashMap config) {
   Object w;
   
      if (config==null) {setTitle("(SWING) - Bandeau par defaut"); return;}
      w= config.get("titre");
      if (w==null) {setTitle("(SWING) - Classe CadreG - V 2.0.0"); return;}
      setTitle((String)w);
   }
   
   private void setLocation(HashMap config) {
   Object w;
   
      if (config==null) {setLocation(0, 0); return;}
      w= config.get("position");
      if (w==null) {setLocation(0, 0); return;}
      Dimension position= (Dimension)w;
      int abscisse= (int)position.getWidth();
      int ordonnee= (int)position.getHeight();
      setLocation (abscisse, ordonnee);
   }
   
   private void setBackground(HashMap config) { 
   Object w;
   
      if (config==null) {setBackground(Color.white); return;}
      w= config.get("arrierePlan");
      if (w==null) {setBackground(Color.yellow); return;}
      getContentPane().setBackground((Color)w);
   }
   
   private void setForeground(HashMap config) {
   Object w;
   
      if (config==null) {setForeground(Color.red); return;}
      w= config.get("avantPlan");
      if (w==null) {setForeground(Color.black); return;}
      getContentPane().setForeground((Color)w);
   }
   
   private void setFont(HashMap config) {
   Object w;
   
      if (config==null) return;
      w= config.get("police");
      if (w==null) return;
      setFont((Font)w);
   }
 
   public static void main(String[] args) {
   	
      // Charger le dictionnaire de configuration
      //   	
      HashMap config= (HashMap) Config.load("ConfigCadreG", "2.0.0");
         
      // Creer et montrer le cadre
      //
      new CadreG(config).show();	
   }

}
