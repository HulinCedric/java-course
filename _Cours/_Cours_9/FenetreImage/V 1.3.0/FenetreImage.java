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
      setTitle ("(SWING) Fenetre image - V 1.3.0");
      setLocation (200, 50);
      
      // Introduction d'un panneau interne
      //
      panneau= new JPanel();
      panneau.setLayout(new GridLayout(1,0));
      panneau.setBackground(Color.yellow);
      getContentPane().add(panneau);
      
      // Charger une image de fond depuis un fichier de type jpeg
      //
      image= Toolkit.getDefaultToolkit().getImage("../../_Images/Meige.jpg");
      if (image == null) {
         System.out.println("Image absente ?"); 
         return;
      } 
   	
      // Construire un media tracker pour controler le chargement de l'image
      //
      MediaTracker mt= new MediaTracker(this);
   	  
      // Attendre la fin du chargement effectif de l'image
      //
      mt.addImage(image,0);
      try{mt.waitForAll();}
      catch(Exception e){}
      
      // Visualiser un controle de fin de chargement
      //
      System.out.println(image.toString());   
   } 
   
// --- Surcharge de la methode paint du cadre                     *** paint

   public void paint(Graphics g) {
   	
       panneau.setBackground(Color.black);
       panneau.repaint();
   }  
   
   public static void main(String[] args) {
      new FenetreImage().show();	
   }

}
