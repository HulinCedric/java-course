//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Exemple d'applications interactives non parametrees
//
// Edition A    : fenetre de visualisation d'images et de dessins
//    + Version 1.0.0	: fenetre limitee a son cadre
//    + Version 1.1.0   : introduction d'un panneau interne
//    + Version 1.2.0   : modification dynamique de la couleur du panneau
//                        interne
//    + Version 1.3.0   : chargement d'une image
//    + Version 1.4.0   : visualisation de l'image dans le panneau interne
//    + Version 1.5.0   : introduction d'une classe interne PanneauG
//
// Auteur : A. Thuaire
//

import java.awt.*;
import javax.swing.*;

public class FenetreImage extends JFrame {
private PanneauG panneau;

   public FenetreImage(String cheminImage) {
   	
      // Description complete du cadre
      //
      setSize(1000, 700);
      setTitle ("(SWING) Fenetre image - V 1.5.0");
      setLocation (200, 50);
      
      // Introduction d'un panneau interne
      //
      panneau= new PanneauG(this);
      getContentPane().add(panneau);
      
      // Charger une image de fond depuis un fichier de type jpeg
      //
      panneau.loadImage(cheminImage);
   }
   
   public static void main(String[] args) {
      new FenetreImage("../_Images/Paysages/Meige.jpg").show();	
   }
   
// *** Classe interne PanneauG
   
   private class PanneauG extends JPanel {
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
      
   // --- Chargement d'une image de fond                *** Methode loadImage
   
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
         
         repaint();
      }    
   	
   // --- Surcharge de la methode paint du panneau      *** Methode paint

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
         g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
      }
   }  
}
