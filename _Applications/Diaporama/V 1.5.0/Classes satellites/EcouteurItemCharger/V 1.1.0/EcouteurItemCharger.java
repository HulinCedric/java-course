//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Application Diaporama
//
// Classe EcouteurItemCharger - Designation par l'operateur d'un lot d'images 
//                              via un explorateur de fichiers
//                              
// Edition A    : TP_4 / Ex_10
//    + Version 1.0.0  : mise en oeuvre de la classe FileDialog
//    + Version 1.1.0  : mise en oeuvre de la classe JFileChooser                               
//
//
// L'attribut "lotImages" de la classe EcouteurItemCharger est un dictionnaire 
// dont les cles sont les chemins absolus d'acces aux fichiers images du 
// repertoire source, designe dynamiquement par l'operateur. Les associes sont
// les labels textuels affectes aux images. Ces labels sont construits 
// automatiquement par l'ecouteur, a partir du nom de chaque fichier image,
// debarrasse de son extension. 
//
// Auteur : A. Thuaire
//
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
      
public class EcouteurItemCharger implements ActionListener {
private JFrame        hamecon;
private String        filtreFichiersImages;
private File          repertoireSource;
private LinkedHashMap lotImages;
      
   public EcouteurItemCharger(JFrame f) {
      hamecon             = f; 
      filtreFichiersImages= ".jpg";
      repertoireSource    = null;
   }
   
   // ------                                                    *** Accesseurs
   
   public LinkedHashMap obtenirLotImages()   {return lotImages;}
   public File        obtenirCheminSource()  {return repertoireSource;}
   
   // ------                                                  *** Methode main
   
   public static void main(String[] args) {
   	
   	  // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("Classe EcouteurItemCharger - V 1.1.0");
      f.setSize(600, 400);
      f.setLocation(250, 250);
      
      // Creer et configurer une barre de menus pour ce cadre support
      //
      LinkedHashMap config= (LinkedHashMap)Config.load("ConfigMenusG", 
                                                         "1.0.0");
      MenusG barreMenus= new MenusG(config, f);
      
      // Creer un ecouteur pour l'item "Charger" du menu "Fichier"
      //
      EcouteurItemCharger ecouteur= new EcouteurItemCharger(f);
      
      // Ajouter l'ecouteur a l'item "Charger"
      //
      barreMenus.ajouterEcouteur("Fichier", "Charger", ecouteur);
      
      // Visualiser le cadre principal
      //
      f.show();
      
      // Visualiser le nombre d'images dans la console
      //
      while (ecouteur.obtenirCheminSource() == null);
      LinkedHashMap lotImages= ecouteur.obtenirLotImages();
      System.out.println ("Nombre d'images = " + lotImages.size());
   }
   
   // ------                                       *** Methode actionPerformed
   
   public void actionPerformed (ActionEvent e) {
         	
      // Creer et visualiser un explorateur de repertoires
      //
      JFileChooser explorateur = new JFileChooser(".");
      explorateur.showOpenDialog(hamecon);
			
      // Acquerir le repertoire cible
      //
      while (repertoireSource == null) {
         repertoireSource= explorateur.getCurrentDirectory();
      }
			
      // Charger le lot d'images a visualiser
      //
      chargerImages(repertoireSource.getPath());
   }
   
   // ------                                         *** Methode chargerImages                                              
   
   public void chargerImages(String cheminRepertoire) {
   File repertoireImages= null;
   
      // Creer le repertoire abstrait cible
      //
      if (cheminRepertoire== null) repertoireImages= new File(".");
      else repertoireImages= new File(cheminRepertoire);
      
      // Controler l'existence du repertoire cible
      //
      if (repertoireImages == null) return;
      
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
   }
}
