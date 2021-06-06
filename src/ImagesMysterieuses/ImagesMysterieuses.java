//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Applications graphiques
//
// Classe ImagesMysterieuses - Jeu de plateau eponyme, avec plusieurs images en
//                             parallele, organisees de facon matricielle
//
// Edition A    : limitee aux tests fonctionnels de l'IHM
//    + Version 1.0.0	: limitee a plusieurs images locales
// 
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ImagesMysterieuses {

// ------                                               *** Methode main
     
   public static void main(String[] args) {
   	
   	  // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("Jeu des images mystérieuses - V 0.0.0 (alpha)");
      f.setSize(500, 300);
      f.setLocation(350, 350);
      
      // Charger le dictionnaire de configuration du panneau principal
      //   	
      HashMap config= (HashMap)Config.load("ConfigPanneauG", "1.0.0");
          
      // Creer une mosaique 2x2
      //
      MosaiqueG mosaique= new MosaiqueG(f, config, 2, 2);
      
      // Ajouter quatre images
      //
      mosaique.ajouterImage(1, 1, "../_Images/Fleurs/Rose.jpg");
      mosaique.ajouterImage(1, 2, "../_Images/Fleurs/Liseron.jpg");
      mosaique.ajouterImage(2, 1, "../_Images/Fleurs/Hibiscus.jpg");
      mosaique.ajouterImage(2, 2, "../_Images/Fleurs/Zinia.jpg");
      
      // Definir un maillage rectangulaire couvrant du panneau principal
      //
      mosaique.ajouterMaillage(Color.yellow, Color.black, 10, 15);
      
      // Construire un ecouteur dedie et l'ajouter au panneau principal 
      //
      ImagesMysterieuses w= new ImagesMysterieuses();
      ImagesMysterieuses.EcouteurIM ecouteur= w.new EcouteurIM(mosaique);
      mosaique.ajouterEcouteur(ecouteur);
      
      // Ajouter la mosaique au cadre support
      //
      f.getContentPane().add(mosaique);
      
      // Montrer le cadre support et sa mosaique interne
      //
      f.show();
   }
   
// -------------------------------------  *** Classe interne privee EcouteurIM 
  
   private class EcouteurIM extends EcouteurPanneauG {
   	
   	  private EcouteurIM (PanneauG hamecon) {panneauCible= hamecon;}
                                       	
      public void mouseClicked(MouseEvent e) {
         
         // Obtenir les coordonnees absolues du clic courant
         //
         int x= e.getX();
         int y= e.getY(); 
         
         // Obtenir la designation de la maille cible
         //
         Dimension cible= panneauCible.obtenirPositionCible(x,y );
         if (cible == null) return;
         
         // Extraire les coordonnees (ligne, colonne) de cette maille
         //
         int ligne  = (int)cible.getWidth();
         int colonne= (int)cible.getHeight();
         
         // Retirer la maille cible
         //
         panneauCible.obtenirMaillage().retirerMaille(ligne, colonne);
      }
   }
} 
