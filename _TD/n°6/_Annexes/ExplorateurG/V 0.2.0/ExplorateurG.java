//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants graphiques generiques sous SWING
//
// Classe ExplorateurG - Explorateur de fichiers et de repertoires
//                              
// Edition "Draft" :  solution technique basee sur la classe FileDialog
//                              
//    + Version 0.0.0	: limitee au mode LOAD
//    + Version 0.1.0   : introduction et activations d'une evolution de la
//                        methode de classe voirThreads 
//    + Version 0.2.0   : arret automatique de l'application sur fermeture du  
//                        cadre principal                             
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
      
public class ExplorateurG implements ActionListener {
private JFrame  hamecon;
private String  repertoireCible;
      
// ------                                              *** Constructeur normal

   public ExplorateurG (JFrame f) {hamecon  = f;}
   
// ------                                                     *** Accesseurs

   public String obtenirRepertoireCible() {return repertoireCible;}   
   
// ------                                                     *** Methode main
   
   public static void main(String[] args) {
   	
voirThreads(1, "Point d'entree du programme");
   	
   	  // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("Classe ExplorateurG - V 0.2.0");
      f.setSize(600, 400);
      f.setLocation(250, 250);
      
      // Definir le comportement de l'application lors de la fermeture du cadre
      //
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
voirThreads(2, "Apres creation du cadre support");
      
      // Creer et configurer une barre de menus pour ce cadre support
      //
      LinkedHashMap config= (LinkedHashMap)Config.load("ConfigMenusG", "1.0.0");
      MenusG barreMenus= new MenusG(config, f);
      
      // Creer un explorateur de repertoires
      //
      ExplorateurG explorateur= new ExplorateurG(f);
      
      // Ajouter l'ecouteur a l'item "Charger"
      //
      barreMenus.ajouterEcouteur("Fichier", "Charger", explorateur);
      
voirThreads(3, "Avant visualisation du cadre support");

      // Visualiser le cadre support
      //
      f.show();
      
voirThreads(4, "Apres visualisation du cadre support");
      
      // Attendre la selection operateur
      //
      while (explorateur.obtenirRepertoireCible() == null) {
      	 Chrono.attendre(100);
      }
      
voirThreads(6, "Apres la boucle d'attente");
      
      // Visualiser le chemin absolu du repertoire cible
      //
      System.out.println ();
      System.out.print("Repertoire cible = ");
      System.out.println(explorateur.repertoireCible);
   }
   
// ------                                          *** Methode actionPerformed
   
   public void actionPerformed (ActionEvent e) {
   	
voirThreads(5.1, "Entree dans la methode actionPerformed"); 
         	
      // Creer et visualiser le panneau de dialogue
      //
      FileDialog dialogue = new FileDialog(hamecon);
      dialogue.setModal(true);
      dialogue.show();
      
voirThreads(5.2, "Avant l'acquisition du repertoire cible"); 
			
      // Acquerir le repertoire cible
      //
      repertoireCible = dialogue.getDirectory();
      
voirThreads(5.3, "Apres l'acquisition du repertoire cible");

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
}
