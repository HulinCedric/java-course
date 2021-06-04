//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe PanneauG - Panneau generique de visualisation
//
// Edition A    : Cours_10
//    + Version 1.0.0	: classe externalisee de l'application FenetreImage,
//                        avec une taille d'image ajustee a celle du panneau
//
// Auteur : A. Thuaire
//

import java.awt.*;
import javax.swing.*;
  
public class PanneauG extends JPanel {
private JFrame hamecon;
private Image image;
   	
   // --- Premier constructeur normal                   *** Constructeur normal

   public PanneauG (JFrame hamecon) {
   	
     // Memoriser l'hamecon vers le conteneur amont
     //
     this.hamecon= hamecon;
   	     
     // Installer le gestionnaire de presentation
     //
     setLayout(new GridLayout(1,0));
   }
      
   // --- Chargement d'une image de fond               *** Methode chargerImage
   
   public void chargerImage(String nomImage) {
   	
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
   	
   // --- Surcharge de la methode paint du panneau            *** Methode paint

   public void paint(Graphics g) {
      	
      // Transmettre le contexte courant
      //
      super.paint(g);
      
      // Modifier dynamiquement la couleur de fond du panneau
      //
      setBackground(Color.black);
      
      // Controle l'existence d'une image de fond
      //
      if (image == null) return;
      
      // Visualiser l'image de fond
      //
      g.drawImage(image, 0, 0, getWidth(), getHeight(), null); //0, 0, 2000, 2000, null);
   }
    
   // ------                                                  *** Methode main
     
   public static void main(String[] args) {
   	
      // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("(SWING) Classe PanneauG - V 1.0.0");
      f.setSize(500, 300);
      f.setLocation(350, 350);
      
      // Creer un panneau et charger une image de fond
      //
      PanneauG panneau= new PanneauG(f);
      panneau.chargerImage("../_Images/Paysages/Meige.jpg");
      
      // Montrer le cadre support et son panneau interne
      //
      f.getContentPane().add(panneau);
      f.show();	
   }
} 
