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
//    + Version 1.2.0   : introduction d'un titre pour le panneau (option)
//    + Version 1.3.0   ; gestion du titre deplacee dans une classe externe
//                        + ajout methode publique presenceImage
//    + Version 1.4.0   : gestion de l'image deplacee dans une classe externe
//
// Edition B    : introduction d'un ecouteur souris du panneau (option)
//    + Version 2.0.0   : implementation des deux interfaces standards par la
//                        classe PanneauG
//    + Version 2.1.0   ; gestion de la souris deplacee dans une classe interne
//                        + ajout des methodes publiques ajouterEcouteur et 
//                        retirerEcouteur
// 
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanneauG extends JPanel {
private JFrame        hamecon;
private TitrePanneauG titre;
private ImagePanneauG image;
private EcouteurSouris ecouteur;

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

// ------                                              *** Methode paint

   public void paint(Graphics g) {
      	
      // Transmettre le contexte courant
      //
      super.paint(g);
      
      // Dessiner l'image de fond
      //
      if (image != null) image.dessiner(g);
      
      // Dessiner le titre du panneau
      //
      if (titre != null) titre.dessiner(g);
   }
      
// ------                                               *** Methode main
     
   public static void main(String[] args) {
   	
      // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("(SWING) Classe PanneauG - V 2.1.0");
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
      panneau.ajouterImage("../_Images/Paysages/Aiguille.jpg");
      
      // Ajouter un ecouteur de souris
      //
      panneau.ajouterEcouteur();
      
      // Montrer le cadre support et son panneau interne
      //
      f.getContentPane().add(panneau);
      f.show();	
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
      
   // ------                                          *** Methode ajouterTitre
   
      public boolean ajouterTitre(String texte, Color couleur, Font police) {
      	
      	// Controler la validite des parametres
      	//
      	if (texte == null)   return false;
      	if (couleur == null) return false;
      	
      	// Creer une instance de la classe externe 
        //
      	titre= new TitrePanneauG(this, texte, couleur, police);
      	
      	// Repeindre le panneau
      	//
      	repaint();
        return true;
      }
      
   // ------                                          *** Methode retirerTitre
   
      public void retirerTitre () {titre= null; repaint();}
      
   // ------                                          *** Methode ajouterImage
   
      public void ajouterImage(String chemin) {
   	
         image= new ImagePanneauG(this, chemin);
         repaint();
      } 
      
   // ------                                          *** Methode retirerImage
   
      public void retirerImage() {image= null; repaint();}
   
   // ------                                          *** Methode presenceImage
   
      public boolean presenceImage() {
      	
      	 if (image == null) return false;
      	 return true;
      }
      
   // ---                                           *** Methode ajouterEcouteur
   
      public void ajouterEcouteur () {
      
         // Creer un ecouteur de souris
         //
         ecouteur= new EcouteurSouris();
         
         // Ajouter l'ecouteur au panneau
      	 //	
         addMouseListener((MouseListener)ecouteur);
         addMouseMotionListener((MouseMotionListener)ecouteur);  
      }
      
   // ---                                           *** Methode retirerEcouteur
   
      public void retirerEcouteur () {
      	
      	 removeMouseListener((MouseListener)ecouteur);
         removeMouseMotionListener((MouseMotionListener)ecouteur); 
      } 
      
   // ---------------------------------------       *** Classe EcouteurSouris 
  
   public class EcouteurSouris implements MouseListener, MouseMotionListener {
                                       	
      public EcouteurSouris() {}
   
      public void mouseClicked(MouseEvent e) {
         System.out.println("("+e.getX()+", "+e.getY()+ ")");
      }

      public void mousePressed  (MouseEvent e) {}
      public void mouseReleased (MouseEvent e) {}
      public void mouseEntered  (MouseEvent e) {}
      public void mouseExited   (MouseEvent e) {}
  
      public void mouseMoved    (MouseEvent e) {
   	     System.out.println("["+e.getX()+", "+e.getY()+ "]");
      }
   
      public void mouseDragged  (MouseEvent e) {
         System.out.println("{"+e.getX()+", "+e.getY()+ "}");
      }  
   }
} 
