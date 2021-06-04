//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Classes graphiques generiques sous SWING
//
// Classe PlatineSonG - Diffusion sonore d'un fichier source designe par son
//                      chemin absolu 
//                              
// Edition "Draft" :  solution technique basee sur l'interface AudioClip
//                              
//    + Version 0.0.0	: TP_4 / Ex_11                             
//
// Auteur : A. Thuaire
//

import java.applet.*;
import java.net.*;

public class PlatineSonG implements AudioClip {
private String cheminFichierSon;
      
// ------                                          *** Constructeur par defaut

   public PlatineSonG() {}
   
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
   	  PlatineSonG platineSupport= new PlatineSonG();
   	  
   	  // Diffuser a partir d'un fichier predefini
   	  //
   	  platineSupport.diffuser("../_Sons/bird.wav");
   	  
   	  // Visualiser la fin d'execution du thread principal
   	  //
   	  System.out.println("\n****** Fin d'execution du thread principal");
   }
   
// ------                                            *** Methodes stop et loop
   
   public void stop() {}  
   public void loop() {}
}
