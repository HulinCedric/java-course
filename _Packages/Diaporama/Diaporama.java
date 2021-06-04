//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Applications graphiques
//
// Application Diaporama - Visualisation d'un diaporama parametrable
//
// Edition A    : TP_4
//    + Version 1.0.0	: visualisation d'un lot d'images defini par un fichier
//                        de donnees (Ex_3)                              
//    + Version 1.1.0   : choix du mode de projection (continu ou pas)
//                        + controle operateur de l'enchainement (Ex_5)
//    + Version 1.2.0   : introduction d'une nouvelle signature de la methode
//                        designerImages pour designer le repertoire d'origine 
//                        du lot d'images (Ex_7)
//    + Version 1.3.0   : filtrage des extensions des fichiers sources (Ex_8)
//    + Version 1.4.0   : construction du label textuel de l'image a partir du
//                        nom du fichier image, debarrasse de son extension 
//                        .jpg (Ex_9)
//    + Version 1.5.0   : mise en place d'un menu deroulant pour designer le
//                        lot d'images sources et externalisation de la
//                        seconde signature de la methode designerImages (Ex_10)
//    + Version 1.6.0   : mise en place de la partie audio (Ex_11)
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.applet.*;
import java.net.*;


public class Diaporama extends PanneauG {
private LinkedHashMap lotImages;
private boolean       controlFlag= true;
private boolean       execMode;
private String        extensionFichiersImages;
private String        extensionFichiersSons;
private PlatineSon    platineSon;

   public Diaporama (Object hamecon, HashMap config, boolean mode) {
   	
   	  super(hamecon, config);
      
      // Renseigner le mode de projection choisi (continu ou pas)
      //
      execMode= mode;
      
      // Ajouter un ecouteur souris au panneau principal
      //
      ajouterEcouteur(new EcouteurDiaporama());
      
      // Renseigner l'extension des fichiers images
      //
      extensionFichiersImages= ".jpg";
      
      // Renseigner l'extension des fichiers sons
      //
      extensionFichiersSons= ".wav";
      
      // Ajouter le moyen de diffusion audio
      //
      platineSon= new PlatineSon(); 
   }
   
   public static void main(String[] args) {
   	
   	  // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("Application Diaporama - V 1.6.0");
      f.setSize(600, 400);
      f.setLocation(250, 250);
      
      // Creer et configurer une barre de menus pour ce cadre support
      //
      LinkedHashMap config_1= (LinkedHashMap)Config.load("ConfigMenusG", 
                                                         "1.0.0");
      MenusG barreMenus= new MenusG(config_1, f);
      
      // Creer et configurer le diaporama
      //
      HashMap config_2= (HashMap)Config.load("ConfigPanneauG", "1.0.0");
      Diaporama diaporama= new Diaporama(f, config_2, true);
      
      // Integrer le diaporama au cadre support
      //
      f.getContentPane().add(diaporama);
      
      // Creer un ecouteur pour l'item "Charger" du menu "Fichier"
      //
      EcouteurItemCharger ecouteur= new EcouteurItemCharger(f);
      
      // Ajouter l'ecouteur a l'item "Charger"
      //
      barreMenus.ajouterEcouteur("Fichier", "Charger", ecouteur);
      
      // Visualiser le cadre support et attendre la selection operateur
      //
      f.show();
      while (ecouteur.obtenirCheminSource() == null);
      
      // Obtenir le lot d'images correspondant
      //
      diaporama.lotImages= ecouteur.obtenirLotImages();
      
      // Lancer la projection des images et l'audition des sons associes
      //
      diaporama.go(2);
   }
   
   // ------                                        *** Methode designerImages 
   
   public boolean designerImages(String nomFichier, String version) {
   	
      lotImages= (LinkedHashMap)Data.load(nomFichier, version);
      if (lotImages != null) return true;
      else return false;
   }
   
   // ------                                                    *** Methode go 
   
   public void go(int tempo) {
   	
   	  // Attendre le chargement du lot d'images courant
   	  //
   	  while (lotImages == null) Chrono.attendre(1);
   	
   	  // Retirer le titre general du diaporama
      //
      retirerTitre();
   	
      // Enchainer les diapositives suivant le mode choisi (continu ou pas)
      //
      do {
      	 
         // Parcourir le dictionnaire des images
         //
         String cle=null, associe= null;
         Iterator i=lotImages.keySet().iterator();
         String radicalFichierSon= null, cheminFichierSon= null;
         int w= 0;
         
         while(i.hasNext()) {
         	
         	// Controler l'etat du drapeau de suspension du diaporama
      	    //
      	    if (controlFlag) {

               cle=(String)i.next();                 // Acquerir image courante
               associe= (String)lotImages.get(cle);  // Acquerir sous titre
               
               // Projeter l'image courante et le titre associe
               //
               ajouterImage(cle);                  
               ajouterTitre(associe, Color.yellow, null);  
               
               // Diffuser le son reference par l'image courante
               //
               w= cle.indexOf(extensionFichiersImages);
               radicalFichierSon= cle.substring(0, w);
               cheminFichierSon= radicalFichierSon + extensionFichiersSons;
               platineSon.diffuser(cheminFichierSon);
               
               // Appliquer la temporisation entre chaque image
               //
               Chrono.attendre(tempo);
            }             
         }
         
      } while (execMode);
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
      
   // --------------------------    *** Classe interne privee EcouteurDiaporama
      
      private class EcouteurDiaporama extends EcouteurPanneauG {
   
         public void mouseClicked(MouseEvent e) {controlFlag= !controlFlag;}
      }
}