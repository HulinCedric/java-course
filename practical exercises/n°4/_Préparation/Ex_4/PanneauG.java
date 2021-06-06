//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package SWING
//
// Classe PanneauG - Visualisation d'un panneau entierement parametrable
//
// Edition A 		: panneau avec dictionnaire de configuration
//    + Version 1.0.0	: parametrage de la partie statique
//    + Version 1.1.0   : introduction de la capture des evenements souris
//    + Version 1.2.0   : chargement optionnel d'une image de fond
//    + Version 1.3.0   : liberation d'une zone en bas d'image
//    + Version 1.4.0   : inscription d'un label centre en bas d'image
//    + Version 1.5.0   : introduction des attributs font et labelImage
//                        + parametrage de l'image et du label associe
//    + Version 1.6.0   : suppression de l'attribut font et exploitation
//                        de la police du contexte graphique (classe Graphics)
//
// Edition B        : introduction d'extensions fonctionnelles pour TD_5
//
//    + Version 2.0.0   : label textuel centre si par d'image de fond
//    + Version 2.1.0   : possibilité de modifier dynamiquement l'image de
//                        fond et/ou son label
//    + Version 2.2.0   : creation d'une classe interne EcouteurSouris
//    + Version 2.3.0   : externalisation des call back dans la classe 
//                        exploitante, designee par l'attribut "hamecon"
//    + Version 2.4.0   : suppression de l'ecouteur de souris
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PanneauG extends JPanel {
private Object hamecon;
private Image image;
private String labelImage;

//                                              *** Premier constructeur normal
//
   public PanneauG (Object hamecon, HashMap config) {
   	
   	  // Memoriser l'hamecon vers le conteneur amont
   	  //
   	  this.hamecon= hamecon;
   	
      // Configurer la partie statique
      //
      setBackground(config);
      setForeground(config);
      setFont(config);
      setImage(config);
   }
//                                                   *** Methode setBackground
//    
   private void setBackground(HashMap config) { 
   Object w;
   
      if (config==null) {setBackground(Color.white); return;}
      w= config.get("arrierePlan");
      if (w==null) {setBackground(Color.yellow); return;}
      setBackground((Color)w);
   }
//                                                   *** Methode setForeground
//    
   private void setForeground(HashMap config) {
   Object w;
   
      if (config==null) {setForeground(Color.red); return;}
      w= config.get("avantPlan");
      if (w==null) {setForeground(Color.black); return;}
      setForeground((Color)w);
   }
//                                                      *** Methode setFont
//  
   private void setFont(HashMap config) {
   Object w;
   
      if (config==null) return;
      w= config.get("police");
      if (w==null) return;
      setFont((Font)w);
   }
//                                                      *** Methode setImage
//  
   private void setImage(HashMap config) {
   Object w;
   
      if (config==null) return;
      w= config.get("fichierImage");
      if (w!=null) loadImage((String)w);
      w= config.get("labelImage");
      if (w!=null) labelImage= (String)w;
   }
   
//                                                      *** Methode setImage
//  
   public void setImage(String fichierImage, String labelImage) {
   	
   	  if (fichierImage!=null) loadImage(fichierImage);
   	  if (labelImage!=null) this.labelImage= labelImage;
   	  repaint();
   }
   
//                                                     *** Methode setLabel
//  
   public void setLabel(String labelImage) {
   	
   	  if (labelImage!=null) this.labelImage= labelImage;
   	  repaint();
   }
   

//                                                      *** Methode loadImage
//    
   public void loadImage(String nomImage) {
   	
   	  // Charger l'image de fond depuis un fichier de type jpeg
      //
      image= Toolkit.getDefaultToolkit().getImage(nomImage); 
   	
      // Construire un media tracker pour controler le chargement de l'image
      //
      MediaTracker mt= new MediaTracker(this);
   	  
      // Attendre la fin du chargement effectif de l'image
      //
      mt.addImage(image,0);
      try{mt.waitForAll();}
      catch(Exception e){}
   }   
   
   //                                                   *** Methode paint
   //   
   public void paint(Graphics g) {
   	
   	  super.paint(g);
   	  
   	  // Traiter le cas de l'absence d'image et de label
   	  //
   	  if (image == null && labelImage==null) return;
   	  
   	  // Visualiser un label sans image
   	  //
   	  if (image == null) {
   	  	
   	     // Calculer les coordonnees du centre du panneau
   	     //
   	     int abscisseCentre= getWidth()/2;
   	     int ordonneeCentre= getHeight()/2;
   	     
   	     // Calculer les dimensions du label textuel
   	     //
   	     FontMetrics fm= g.getFontMetrics();
        
         int largeur= fm.stringWidth(labelImage);
         int hauteur = fm.getHeight();
         
         g.drawString(labelImage, (getWidth()-largeur)/2, 
                                  (getHeight()-hauteur)/2);
 
         return;
   	  }
   	  
   	  // Visualiser l'image sans label textuel associe
   	  //
   	  if (image != null && labelImage == null) {
   	  	 g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
   	  	 return;
   	  }
   	     	  
      // Calculer les dimensions du label textuel
      //
      FontMetrics fm= g.getFontMetrics();
        
      int largeur= fm.stringWidth(labelImage);
      int hauteur = fm.getHeight();
      
      // Visualiser l'image avec son label textuel
      //
      g.drawImage(image, 0, 0, getWidth(), getHeight()-hauteur-10, null);
      g.drawString(labelImage, (getWidth()-largeur)/2, getHeight()-10 );
   }
 
   public static void main(String[] args) {
   PanneauG panneau= null;
      
      // Charger un premier dictionnaire de configuration
      //   	
      HashMap config_1= (HashMap) Config.load("Vercors", "1.0.0");
      
      // Creer un premier cadre support
      //
      JFrame f1= new JFrame();
         
      // Creer le premier panneau cible
      //
      panneau= new PanneauG(f1, config_1);
      
      // Ajouter le panneau cible
      //
      f1.getContentPane().add(panneau);
      
      // Mettre en place le premier cadre support
      //
      f1.setTitle("(SWING) Classe PanneauG - V 2.3.0 (P1)");
      f1.setSize(500, 300);
      f1.setLocation(50, 50);
      
      // Montrer le premier cadre support et son panneau interne
      //
      f1.show();	
      
      // Charger un second dictionnaire de configuration
      //   	
      HashMap config_2= (HashMap) Config.load("Aiguille", "1.0.0");
      
      // Creer un second cadre support
      //
      JFrame f2= new JFrame();
         
      // Creer le second panneau
      //
      panneau= new PanneauG(f2, config_2);
      
      // Ajouter le nouveau panneau cible
      //
      f2.getContentPane().add(panneau);
      
      // Mettre en place le second cadre support
      //
      f2.setTitle("(SWING) Classe PanneauG - V 2.3.0 (P2)");
      f2.setSize(500, 300);
      f2.setLocation(400, 100);
      
      // Montrer le second cadre support et son panneau interne
      //
      f2.show();
      
      // Modifier l'image du second panneau
      //
      panneau.setImage("../_Images/Fleurs/Liseron.jpg", "Liseron");	
   }
}
