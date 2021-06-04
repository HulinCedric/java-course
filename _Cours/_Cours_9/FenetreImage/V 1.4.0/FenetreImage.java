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
//
// Auteur : A. Thuaire
//

import java.awt.*;
import javax.swing.*;

public class FenetreImage extends JFrame {
private JPanel panneau;
private Image image;

   public FenetreImage() {
   	
      // Description complete du cadre
      //
      setSize(1000, 700);
      setTitle ("(SWING) Fenetre image - V 1.4.0");
      setLocation (200, 50);
      
      // Introduction d'un panneau interne
      //
      panneau= new JPanel();
      panneau.setLayout(new GridLayout(1,0));
      panneau.setBackground(Color.yellow);
      getContentPane().add(panneau);
      
      // Charger une image de fond depuis un fichier de type jpeg
      //
      String cheminImage= "../_Images/Paysages/Meige.jpg";
      image= Toolkit.getDefaultToolkit().getImage(cheminImage);
   	
      // Construire un media tracker pour controler le chargement de l'image
      //
      MediaTracker mt= new MediaTracker(this);
   	  
      // Attendre la fin du chargement effectif de l'image
      //
      mt.addImage(image,0);
      try{mt.waitForAll();}
      catch(Exception e){}  
   } 
   
// --- Surcharge de la methode paint du cadre               *** Methode paint

   public void paint(Graphics g) {
   	
   	  // Transmettre le contexte graphique courant au panneau interne
      //
      panneau.paint(g);
      
      // Modifier dynamiquement la couleur de fond du panneau
      //
      panneau.setBackground(Color.black);
      
      // Controle l'existence d'une image de fond
      //
      if (image == null) return;
      
      // Visualiser l'image de fond
      //
      panneau.getGraphics().drawImage(image, 0, 0, panneau.getWidth(), 
                                      panneau.getHeight(), null);
   }  
   
   public static void main(String[] args) {
   FenetreImage f1= new FenetreImage();
   
      f1.show();
      f1.repaint();
   }
}
