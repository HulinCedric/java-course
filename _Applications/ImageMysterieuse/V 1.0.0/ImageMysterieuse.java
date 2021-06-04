//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Applications graphiques
//
// Classe ImageMysterieuse - Jeu de plateau eponyme
//
// Edition A    : limitee aux tests fonctionnels de l'IHM
//    + Version 1.0.0	: limitee a une image locale
// 
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ImageMysterieuse {

// ------                                               *** Methode main
     
   public static void main(String[] args) {
   	
   	  // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("Jeu de l'image mystérieuse - V 0.0.0 (alpha)");
      f.setSize(500, 300);
      f.setLocation(350, 350);
      
      // Charger le dictionnaire de configuration du panneau principal
      //   	
      HashMap config= (HashMap)Config.load("ConfigPanneauG", "1.0.0");
      
      // Creer et configurer ce panneau
      //
      PanneauG panneau= new PanneauG(f, config);
      
      // Ajouter une image de fond
      //
      panneau.ajouterImage("../_Images/Fleurs/Rose.jpg");
      
      // Ajouter un maillage rectangulaire couvrant du panneau principal
      //
      panneau.ajouterMaillage(Color.yellow, Color.black, 10, 15);
      
      // Construire un ecouteur dedie et l'ajouter au panneau principal 
      //
      ImageMysterieuse w= new ImageMysterieuse();
      ImageMysterieuse.EcouteurIM ecouteur= w.new EcouteurIM(panneau);
      panneau.ajouterEcouteur(ecouteur);
      
      // Ajouter le panneau principal au cadre support
      //
      f.getContentPane().add(panneau);
      
      // Montrer le cadre support et son panneau interne
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
