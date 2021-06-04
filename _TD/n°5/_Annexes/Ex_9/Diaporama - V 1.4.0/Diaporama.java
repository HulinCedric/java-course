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
private boolean       controlFlag= true;
private boolean       execMode;
private String        filtreFichiersImages;

   public Diaporama (Object hamecon, HashMap config, boolean mode) {
   	
   	  super(hamecon, config);
      
      // Renseigner le mode de projection choisi (continu ou pas)
      //
      execMode= mode;
      
      // Ajouter un ecouteur souris au panneau principal
      //
      ajouterEcouteur(new EcouteurDiaporama());
      
      // Renseigner le filtre des fichiers images
      //
      filtreFichiersImages= ".jpg"; 
   }
   
   public static void main(String[] args) {
   	
   	  // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("Application Diaporama - V 1.4.0");
      f.setSize(600, 400);
      f.setLocation(250, 250);
      
      // Creer et configurer le diaporama
      //
      HashMap config= (HashMap)Config.load("ConfigPanneauG", "1.0.0");
      Diaporama diaporama= new Diaporama(f, config, true);
      
      // Ajouter le titre du diaporama
      //
      diaporama.ajouterTitre("Les fleurs du jardin", Color.yellow, null);
      
      // Designer le lot d'images par un repertoire transmis par parametre
      // 
      boolean ok= diaporama.designerImages("../_Images/Fleurs");
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
   
   // ------                                       *** Methodes designerImages 
   
   public boolean designerImages(String nomFichier, String version) {
   	
      lotImages= (LinkedHashMap)Data.load(nomFichier, version);
      if (lotImages != null) return true;
      else return false;
   }
   
   // ------                                               
   
   public boolean designerImages(String cheminRepertoire) {
   File repertoireImages= null;
   
      // Creer le repertoire abstrait cible
      //
      if (cheminRepertoire== null) repertoireImages= new File(".");
      else repertoireImages= new File(cheminRepertoire);
      
      // Controler l'existence du repertoire cible
      //
      if (repertoireImages == null) return false;
      
      // Obtenir la liste de tous les fichiers du repertoire cible
      //
      String[] nomsFichierImage= repertoireImages.list();
      
      // Construire le dictionnaire des donnees
      //
      lotImages= new LinkedHashMap();
      int position=0;
      
      String cle= null, associe= null;
      
      for (int k=0; k < nomsFichierImage.length; k++) {
      	 position= nomsFichierImage[k].indexOf(filtreFichiersImages);
      	 if (position > 0) {
            cle    = cheminRepertoire + "/" + nomsFichierImage[k];
            associe= nomsFichierImage[k].substring(0, position);
            lotImages.put (cle, associe);
         }
      }
      
      return true;
   }
   
   // ------                                                    *** Methode go 
   
   public void go(int tempo) {
   	
   	  // Retirer le titre general du diaporama
      //
      retirerTitre();
   	
      // Enchainer les diapositives suivant le mode choisi (continu ou pas)
      //
      do {
      	 
         // Parcourir le dictionnaire des donnees et visualiser chaque image
         //
         String cle=null, associe= null;
         Iterator i=lotImages.keySet().iterator();
     
         while(i.hasNext()) {
         	
         	// Controler l'etat du drapeau de suspension du diaporama
      	    //
      	    if (controlFlag) {

               cle=(String)i.next();                 // Acquerir image courante
               associe= (String)lotImages.get(cle);  // Acquerir sous titre
               ajouterImage(cle);                  
               ajouterTitre(associe, Color.yellow, null);  
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
