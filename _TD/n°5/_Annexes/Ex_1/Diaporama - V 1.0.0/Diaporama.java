//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Applications graphiques
//
// Application Diaporama - Visualisation d'un diaporama parametrable
//
// Edition A    : TP_4
//    + Version 1.0.0	: Ex_3
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Diaporama extends PanneauG {
private LinkedHashMap lotImages;

   public Diaporama (Object hamecon, HashMap config) {super(hamecon, config);}
   
   public static void main(String[] args) {
   	
   	  // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("Application diaporama - V 1.0.0");
      f.setSize(600, 400);
      f.setLocation(250, 250);
      
      // Creer et configurer le panneau principal
      //
      HashMap config= (HashMap)Config.load("ConfigPanneauG", "1.0.0");
      Diaporama diaporama= new Diaporama(f, config);
      
      // Ajouter le titre du diaporama
      //
      diaporama.ajouterTitre("Paysages des Alpes", Color.yellow, null);
      
      // Designer le lot courant d'images (donnees)
      // 
      boolean ok= diaporama.designerImages("Paysages", "1.0.0");
      if (!ok) System.exit(1);   	
      
      // Integrer le diaporama au cadre support
      //
      f.getContentPane().add(diaporama);
      
      // Visualiser le titre du diaporama pendant 2 secondes
      //
      f.show(); Chrono.attendre(2);
      
      // Lancer la projection de tout le lot d'images
      //
      diaporama.go(2);
   }
   
   // ------                                        *** Methode designerImages 
   
   public boolean designerImages(String nomFichier, String version) {
   	
      lotImages= (LinkedHashMap)Data.load(nomFichier, version);
      if (lotImages != null) return true;
      else return false;
   } 
   
   // ------                                               *** Methode go 
   
   public void go(int tempo) {
   
      // Parcourir le dictionnaire des donnees et visualiser chaque image
      //
      String cle=null, associe= null;
      Iterator i=lotImages.keySet().iterator();
     
      while(i.hasNext()) {

         cle=(String)i.next();                     // Acquerir l'image courante
         associe= (String)lotImages.get(cle);      // Acquerir son sous titre
         ajouterImage(cle);                        // Visualiser image courante
         ajouterTitre(associe, Color.yellow, null);  
         Chrono.attendre(tempo);             
      }
   }
   
   // -------------------------------------    *** Classe interne privee Chrono
   
      private static class Chrono {

         private static void attendre (int N) {
         	
         	// Attendre N secondes en bloquant le thread courant 
            //
            try {Thread.currentThread().sleep(N*1000);} 
            catch(InterruptedException e){ }
         }
      }
}
