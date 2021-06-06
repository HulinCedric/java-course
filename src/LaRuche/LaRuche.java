//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Applications graphiques
//
// Classe LaRuche - Jeu de plateau eponyme
//
// Edition Alpha    : limitee aux tests fonctionnels de l'IHM
//    + Version 0.0.0	: limitee a une description statique du presentoir de 
//                        chaque joueur
//    + Version 0.1.0   : ajout au presentoir de l'ecouteur standard de la 
//                        mosaique (Classe EcouteurMosaiqueG - V 1.1.0)
// 
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LaRuche {

// ------                                               *** Methode main
     
   public static void main(String[] args) {
   	
   	  // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("Jeu de la ruche - V 0.1.0 (alpha)");
      f.setSize(500, 150);
      f.setLocation(550, 550);
      
      // Charger le dictionnaire de configuration du panneau principal
      //   	
      HashMap config= (HashMap)Config.load("ConfigPanneauG", "1.0.0");
          
      // Creer une mosaique 1x5
      //
      MosaiqueG presentoir= new MosaiqueG(f, config, 1, 5);
      
      // Ajouter cinq images d'alveoles au presentoir
      //
      presentoir.ajouterImage(1, 1, "../_Images/Ruche/Piece_01.jpg");
      presentoir.ajouterImage(1, 2, "../_Images/Ruche/Piece_02.jpg");
      presentoir.ajouterImage(1, 3, "../_Images/Ruche/Piece_03.jpg");
      presentoir.ajouterImage(1, 4, "../_Images/Ruche/Piece_04.jpg");
      presentoir.ajouterImage(1, 5, "../_Images/Ruche/Piece_05.jpg");
      // presentoir.ajouterImage(1, 6, "../_Images/Ruche/Piece_06.jpg");
      // presentoir.ajouterImage(1, 7, "../_Images/Ruche/Piece_07.jpg");
      // presentoir.ajouterImage(1, 8, "../_Images/Ruche/Piece_08.jpg");
      
      // Creer et ajouter un ecouteur dedie pour designer l'alveole cible
      // alveole d'un clic souris
      //
      presentoir.ajouterEcouteur(new EcouteurMosaiqueG(presentoir));
      
      // Ajouter le presentoir au cadre support
      //
      f.getContentPane().add(presentoir);
      
      // Montrer le cadre support et sa mosaique interne
      //
      f.show();
   }
} 
