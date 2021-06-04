//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants graphiques generiques sous SWING
//
// Classe ExplorateurG - Explorateur de fichiers et de repertoires
//                              
// Edition "Draft" :  solution technique basee sur la classe FileDialog
//                              
//    + Version 0.0.0	: limitee au mode LOAD                             
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
   	
   	  // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("Classe ExplorateurG - V 0.0.0");
      f.setSize(600, 400);
      f.setLocation(250, 250);
      
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
      
      // Visualiser le cadre support
      //
      f.show();
      
      // Attendre la selection operateur
      //
      while (explorateur.obtenirRepertoireCible() == null) {
      	 Chrono.attendre(100);
      }
      
      // Visualiser le chemin absolu du repertoire cible
      //
      System.out.println ();
      System.out.print("Repertoire cible = ");
      System.out.println(explorateur.repertoireCible);
   }
   
// ------                                          *** Methode actionPerformed
   
   public void actionPerformed (ActionEvent e) {
         	
      // Creer et visualiser le panneau de dialogue
      //
      FileDialog dialogue = new FileDialog(hamecon);
      dialogue.setModal(true);
      dialogue.show();
			
      // Acquerir le repertoire cible
      //
      repertoireCible = dialogue.getDirectory();
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
