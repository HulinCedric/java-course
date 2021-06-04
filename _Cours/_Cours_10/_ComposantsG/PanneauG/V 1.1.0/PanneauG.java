//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe PanneauG - Panneau generique de visualisation
//
// Edition A    : Cours_10
//    + Version 1.0.0	: classe externalisee de l'application FenetreImage,
//                        avec une taille d'image ajustee a celle du panneau
//    + Version 1.1.0   : mise en place d'un dictionnaire de configuration
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class PanneauG extends JPanel {
private JFrame    hamecon;
private Image     image;
   	
// ------                                               *** Constructeur normal

   public PanneauG (JFrame hamecon, HashMap config) {
   	
      // Memoriser l'hamecon vers le conteneur amont
      //
      this.hamecon= hamecon;
      
      // Configurer la partie statique
      //
      setBackground(config);
      setForeground(config);
      setFont(config);
   }
      
   // ------                                          *** Methode setBackground
    
      private void setBackground(HashMap config) { 
      Object w;
   
         if (config==null) {setBackground(Color.black); return;}
         w= config.get("arrierePlan");
         if (w==null) {setBackground(Color.black); return;}
         setBackground((Color)w);
      }
      
   // ------                                          *** Methode setForeground
   
      private void setForeground(HashMap config) {
      Object w;
   
         if (config==null) {setForeground(Color.yellow); return;}
         w= config.get("avantPlan");
         if (w==null) {setForeground(Color.yellow); return;}
         setForeground((Color)w);
      }
      
   // ------                                          *** Methode setFont
   
      private void setFont(HashMap config) {
      Object w;
   
         if (config==null) return;
         w= config.get("police");
         if (w==null) return;
         setFont((Font)w);
      }
      
   // ------                                          *** Methode ajouterImage
   
      public void ajouterImage(String nomImage) {
   	
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
         
         repaint();
      }    
      
   // ------                                         *** Methode dessinerImage
   
      private void dessinerImage(Graphics g) {
      	
         g.drawImage(image, 0, 0, getWidth(), getHeight(),null);	
      }
      
      // ------                                            *** Methode paint

   public void paint(Graphics g) {
      	
      // Transmettre le contexte courant
      //
      super.paint(g);
      
      // Dessiner l'image de fond
      //
      if (image != null) dessinerImage(g);                                                                              
   }
      
   // ------                                               *** Methode main
     
   public static void main(String[] args) {
   	
      // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("(SWING) Classe PanneauG - V 1.1.0");
      f.setSize(500, 300);
      f.setLocation(350, 350);
      
      // Charger le dictionnaire de configuration d'un panneau
      //   	
      HashMap config= (HashMap)Config.load("ConfigPanneauG", "1.0.0");
      
      // Creer et configurer un panneau
      //
      PanneauG panneau= new PanneauG(f, config);
      
      // Ajouter une image de fond
      //
      panneau.ajouterImage("../_Images/Fleurs/Rose.jpg");
      
      // Montrer le cadre support et son panneau interne
      //
      f.getContentPane().add(panneau);
      f.show();	
   } 
} 
