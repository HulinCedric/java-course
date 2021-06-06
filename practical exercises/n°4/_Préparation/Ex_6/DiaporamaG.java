//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package SWING
//
// Classe DiaporamaG - Visualisation d'un diaporama entierement parametrable
//
// Edition A 		: Cours 10
//    + Version 1.0.0	: ecouteur et call back dans la classe PanneauG
//    + Version 1.1.0   : transfert de la call back "clicSouris", avec 
//                        son comportement primitif issu de "PanneauG"
//    + Version 1.2.0   : transfert de l'ecouteur de souris au cote de la 
//                        "call back"
//    + Version 1.3.0   : introduction de l'ecouteur de souris dans le cadre
//    + Version 1.4.0   : introduction de l'attribut "controlFlag" avec
//                        possibilite de suspendre le diaporama au gre de 
//                        l'operateur
//    + Version 1.5.0   : mise en conformite avec les exigences de TD5/Ex_8
//    + Version 1.6.0   : introduction d'une signature supplementaire de la 
//                        methode "charger" (TP5/Ex_4)
//    + Version 1.7.0   : filtrage des noms des fichiers d'images
//    + Version 1.8.0   : les labels des images deviennent par defaut le nom
//                        du fichier image (sans extension)     
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class DiaporamaG extends CadreG {
private PanneauG panneau;
private LinkedHashMap diaporama;
private boolean controlFlag;
private boolean execMode= false;

   public DiaporamaG (HashMap config) {
   	
      super(config);
   	
      // Creation du panneau interne
      //
      panneau= new PanneauG(this, config);
      
      // Ajout du panneau dans le cadre
      //
      getContentPane().add(panneau);
      
      // Creer un ecouteur souris
      //
      new EcouteurSouris();
      
      // Initialiser le drapeau de suspension du diaporama
      //  
      controlFlag= true;
      
      // Initialiser le mode de projection (continu ou pas)
      //
      getMode(config);
      
      // Montrer la fenetre de projection
      //
      show();
   }
   
   private void getMode(HashMap config){
   Boolean w= null;
   
      if (config== null) return;
      w= (Boolean)config.get("execMode");
      if (w==null) return;
      execMode= w.booleanValue();
   }
   
// ---------------------------------------   *** Classe interne EcouteurSouris 
//  
public class EcouteurSouris implements MouseListener,
                                       MouseMotionListener {
                                       	
   public EcouteurSouris() {
   	
      addMouseListener(this);
      addMouseMotionListener(this);  	
   }
   
   public void mouseClicked(MouseEvent e) {clicSouris(e);}

   public void mousePressed  (MouseEvent e) {}
   public void mouseReleased (MouseEvent e) {}
   public void mouseEntered  (MouseEvent e) {}
   public void mouseExited   (MouseEvent e) {}
   public void mouseMoved    (MouseEvent e) {}
   public void mouseDragged  (MouseEvent e) {}
 }
 
 // ---------------------------------------
 
 //                             *** Call back pour clic souris dans le cadre
 //
   public void clicSouris(MouseEvent e) {controlFlag= !controlFlag;}
 
 //                                                      *** Methodes charger
 //  
   public void charger(String nomFichier, String version) {
      diaporama= (LinkedHashMap)Data.load(nomFichier, version);
   }
   
   public void charger(String cheminRepertoire) {
   File repertoireImages= null;
   
      // Creer le repertoire abstrait cible
      //
      if (cheminRepertoire== null) repertoireImages= new File(".");
      else repertoireImages= new File(cheminRepertoire);
      
      // Obtenir la liste de tous les fichiers du repertoire cible
      //
      String[] nomsFichierImage= repertoireImages.list();
      
      // Construire le dictionnaire des donnees
      //
      diaporama= new LinkedHashMap();
      int position=0;
      
      for (int k=0; k < nomsFichierImage.length; k++) {
      	 position= nomsFichierImage[k].indexOf(".jpg");
      	 if (position > 0) {
            diaporama.put (cheminRepertoire + "/" + nomsFichierImage[k], 
                           nomsFichierImage[k].substring(0, position));
         }
      }
   }
   
   public void voirTitre(String titreDiaporama) {
      panneau.setLabel(titreDiaporama);
   }
   
   public void go(int tempo) {
   	
      // Parcourir le dictionnaire des donnees et visualiser chaque image
      //
      Iterator i=null;
      String cle=null;
      String associe= null;
      
      // Enchainer les diapositives suivant le mode choisi
      //
      do {
      	
      	 // Parcourir le dictionnaire des donnees et visualiser chaque image
         //
         if (diaporama==null) diaporama= new LinkedHashMap();
         i=diaporama.keySet().iterator();
     
         while(i.hasNext()) {
      	
      	    // Controler l'etat du drapeau de suspension du diaporama
      	    //
      	    if (!controlFlag) {

               // Acquerir la cle courante
               //
               cle=(String)i.next();
         
               // Acquerir l'associe courant
               //
               associe= (String)diaporama.get(cle);
  
               // Visualiser l'image courante
               //
               panneau.setImage(cle, associe);
         
               // Attendre le temps de latence
               //
               TimerG.attendre(tempo);
            }
         }
         
      } while (execMode);
      
      hide();    	
   }
    
   public static void main(String[] args) {
   	
      // Charger le dictionnaire de configuration du diaporama
      //   	
      HashMap config= (HashMap) Config.load("ConfigDiaporamaG", "1.2.0");
      
      // Creer le diaporama courant
      //
      DiaporamaG diaporama= new DiaporamaG(config);
      
      // Charger le diaporama
      //
      diaporama.charger("../_Images/Fleurs");
      
      // Visualiser le titre du diaporama
      //
      diaporama.voirTitre("Les fleurs du jardin");
      
      // Lancer la projection des images
      //
      diaporama.go(2);	
   }
}
