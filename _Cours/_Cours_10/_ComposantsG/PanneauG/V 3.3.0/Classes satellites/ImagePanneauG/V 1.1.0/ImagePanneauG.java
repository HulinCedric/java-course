//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe ImagePanneauG - Image de fond de toute instance de PanneauG
//
// Edition A    : Cours_10
//    + Version 1.0.0	: version initiale
//    + Version 1.1.0   : ajout de l'accesseur obtenirCheminImage
//
// Auteur : A. Thuaire
//

import java.awt.*;

public class ImagePanneauG {
private PanneauG panneau;
private String   cheminFichier;
private Image    image;

// ------                                               *** Constructeur normal

   public ImagePanneauG (PanneauG hamecon, String chemin) {
      
      // Renseigner les attributs transmis par parametre
      //
      this.panneau = hamecon;
      cheminFichier= chemin;
      
      // Charger l'image de fond depuis le fichier support
      //
      image= Toolkit.getDefaultToolkit().getImage(chemin);
   	
      // Construire un media tracker pour controler le chargement de l'image
      //
      MediaTracker mt= new MediaTracker(panneau);
   	  
      // Attendre la fin du chargement effectif de l'image
      //
      mt.addImage(image,0);
      try{mt.waitForAll();}
      catch(Exception e){}
   }
   
// ------                                       *** Accesseurs de consultation
   
   public String obtenirCheminImage() {return cheminFichier;}    
      
// ------                                              *** Methode dessiner
   
   public void dessiner(Graphics g) {
      	
      if (image != null) {     	
         g.drawImage(image, 0, 0, panneau.getWidth(), 
                                  panneau.getHeight(),null);
      }	
   }
} 
