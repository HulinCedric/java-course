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
//    + Version 0.1.0   : introduction et activations d'une evolution de la
//                        methode de classe voirThreads
//    + Version 0.2.0   : creation de chaque instance sous forme d'un thread et
//                        suppression des methodes diffuser et stop                                                 
//
// Auteur : A. Thuaire
//

import java.applet.*;
import java.net.*;

public class PlatineSonG extends Thread implements AudioClip {
private String cheminFichierSon;
      
// ------                                          *** Constructeur par defaut

   public PlatineSonG() {super("PlatineSonG");}
   
// ------                                              *** Constructeur normal

   public PlatineSonG(String cheminFichierSon) {
   	
   	  super("PlatineSonG");
   	  this.cheminFichierSon= cheminFichierSon;
   }
   
// ------                                                     *** Methode play
  
   public void play() {
   	
voirThreads(3.1, "   --- Entree methode play de la platine son");
   	
   	  try {

      	 URL source= new java.net.URL("file:" + cheminFichierSon);	 
      	 AudioClip support= java.applet.Applet.newAudioClip(source);
      	 
voirThreads(3.2, "   --- Apres creation de l'audio clip sous jacent");      	 
      	 
         support.play();
      }
      catch (Exception e){
         System.out.println("Chemin fichier son incorrect");
      }
      
voirThreads(3.3, "   --- Sortie methode play de l'audio clip");   
      
   }
   
// ------                                                     *** Methode main
   
   public static void main(String[] args) {
 
voirThreads(1, "Demarrage de l'application");
   	
   	  // Creer une platine support
   	  //
   	  PlatineSonG platineSupport= new PlatineSonG("../_Sons/bird.wav");
 
voirThreads(2, "Avant entree dans methode start");
   	  
   	  // Diffuser a partir du fichier defini a la construction
   	  //
   	  platineSupport.start();
   	  
voirThreads(4, "Apres sortie de la methode start");

      // Visualiser la fin d'execution du thread principal
      //
      System.out.println("****** Fin d'execution du thread principal");
   }
   
// ------                                                     *** Methode loop
   
   public void loop() {}
   
// ------                                                      *** Methode run
  
   public void run () {if (cheminFichierSon != null) play();}
   
// ------                                              *** Methode voirThreads  
   
   private static void voirThreads(double etape, String intituleEtape) {
   Thread[] ta = new Thread[Thread.activeCount()];
   int nbThreads = Thread.enumerate(ta);
   
      System.out.println("");  
      System.out.println("=> [" + etape + "] " + intituleEtape + "\n"); 
      System.out.println("Nombre de threads= "+nbThreads);
      
      for (int i=0; i<nbThreads; i++) {
         System.out.print("Thread "+ (i+1));
         System.out.print(" (P= " + ta[i].getPriority() + ")");
         System.out.println(" : " + ta[i].getName());
      }
      
      System.out.println("");  
   }
   
// -------------------------------------      *** Classe interne privee Chrono
   
   private static class Chrono {

      private static void attendre (int tms) {
         	
         // Attendre tms millisecondes, en bloquant le thread courant 
         //
         try {Thread.currentThread().sleep(tms);} 
         catch(InterruptedException e){ }
      }
   }
}
