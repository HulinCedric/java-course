//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Application Diaporama
//
// Classe PlatineSon - Diffusion sonore d'un fichier source designe par son
//                     chemin absolu 
//                              
// Edition A    : TP_4 / Ex_11
//    + Version 1.0.0	: mise en oeuvre de l'interface AudioClip                              
//
// Auteur : A. Thuaire
//

import java.applet.*;
import java.net.*;

public class PlatineSon implements AudioClip {
private String cheminFichierSon;
      
// ------                                          *** Constructeur par defaut

   public PlatineSon() {}
   
// ------                                                 *** Methode diffuser
   
   public void diffuser(String cheminFichierSon) {
   	  this.cheminFichierSon= cheminFichierSon;
   	  if (cheminFichierSon != null) play();
   }
 
// ------                                                     *** Methode play
  
   public void play() {
   	
   	  try {
      	 URL source= new java.net.URL("file:" + cheminFichierSon);
         java.applet.Applet.newAudioClip(source).play();
      }
      catch (Exception e){
         System.out.println("Chemin fichier son incorrect");
      }
   }
   
// ------                                                     *** Methode main
   
   public static void main(String[] args) {
   	
   	  // Creer une platine support
   	  //
   	  PlatineSon platineSupport= new PlatineSon();
   	  
   	  // Diffuser a partir d'un fichier predefini
   	  //
   	  platineSupport.diffuser("../../../../_Images/Fleurs/Coquelicot.wav");
   }
   
// ------                                            *** Methodes stop et loop
   
   public void stop() {}  
   public void loop() {}
}
