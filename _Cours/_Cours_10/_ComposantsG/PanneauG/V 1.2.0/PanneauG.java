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
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanneauG extends JPanel {
private JFrame    hamecon;
private Image     image;
private String    texteTitre;
private Color     couleurTitre;
private Font      policeTitre;
   	
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
      	
      	// Controler la presence d'une image
      	 //
      	 if (texteTitre == null) return;
      	
         g.drawImage(image, 0, 0, getWidth(), getHeight(),null);	
      }
      
   // ------                                          *** Methode ajouterTitre
   
      public boolean ajouterTitre(String titre, Color couleur, Font police) {
      	
      	// Controler la validite des parametres
      	//
      	if (titre == null) return false;
      	
      	// Renseigner les attributs associes au titre
        //
      	texteTitre  = titre;
      	couleurTitre= couleur;
      	policeTitre = police;
      	
      	// Repeindre le panneau
      	//
      	repaint();
        return true;
      }
      
   // ------                                          *** Methode retirerTitre
   
      public void retirerTitre () {
      	
      	texteTitre= null; 
      	couleurTitre= null;
      	policeTitre= null;
      	
      	repaint();
  	
      }
      
      
   // ------                                          *** Methode dessinerTitre
   
      private void dessinerTitre(Graphics g) {
      	
      	 // Controler la presence d'un titre
      	 //
      	 if (texteTitre == null) return;
         
         // Calculer les dimensions du titre
         //
         FontMetrics fm= g.getFontMetrics();
        
         int largeurTitre= fm.stringWidth(texteTitre);
         int hauteurTitre= fm.getHeight();
         
         // Modifier la couleur courante de dessin dans le panneau
         //
         if (couleurTitre != null) g.setColor(couleurTitre);
         
         // Modifier la police courante du panneau
         //
         if (policeTitre != null) g.setFont(policeTitre);
         
         // Traiter le cas de l'absence d'image de fond
         //
         if (image == null) {
   	  	
            // Calculer les coordonnees du centre du panneau
            //
   	        int abscisseCentre= getWidth()/2;
   	        int ordonneeCentre= getHeight()/2;
         
            // Dessiner le texte
            //
            g.drawString(texteTitre, (getWidth()-largeurTitre)/2, 
                                     (getHeight()-hauteurTitre)/2);
   	 }
   	     
   	 else {
   	
            // Traiter le cas de la presence d'une image de fond
            //
            g.drawString(texteTitre, (getWidth()-largeurTitre)/2, 
                                      getHeight()-10 );
         }
      }
      
   // ------                                              *** Methode paint

      public void paint(Graphics g) {
      	
         // Transmettre le contexte courant
         //
         super.paint(g);
      
         // Dessiner l'image de fond
         //
         if (image != null) dessinerImage(g);
      
         // Dessiner le titre du panneau
         //
         if (texteTitre != null) dessinerTitre(g);                                                                                 
      }
      
   // ------                                               *** Methode main
     
   public static void main(String[] args) {
   	
      // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("(SWING) Classe PanneauG - V 1.2.0");
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
      panneau.ajouterImage("../_Images/Paysages/Rochilles.jpg");
      
      // Ajouter un titre au panneau
      //
      panneau.ajouterTitre("Les Rochilles (Valloire)", Color.yellow, null);
      // panneau.retirerTitre();
      
      // Montrer le cadre support et son panneau interne
      //
      f.getContentPane().add(panneau);
      f.show();	
   } 
} 
