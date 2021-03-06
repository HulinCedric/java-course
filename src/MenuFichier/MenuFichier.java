//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuFichier : menu "Fichier" de l'application FenetreSaisieTexte
//
// Edition A 	: description statique uniquement
//    + Version 1.0.0   : avec accesseur de lecture des items de menus
//
// Edition B    : introduction des ecouteurs d'items du menu "Fichier"
//    + Version 2.0.0   : ajout d'un ecouteur par defaut sur chaque item
//                        de menu
//    + Version 2.1.0   : chaque ecouteur visualise son label textuel dans la
//                        console  
//
// Auteur : A. Thuaire
//
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFichier extends Menu {
private LinkedHashMap itemsMenu;

   public MenuFichier() {

      super("Fichier");
   	
      // --- Description des items
      //
      itemsMenu= new LinkedHashMap();
     
      itemsMenu.put("Nouveau",         new MenuItem("Nouveau..."));
      itemsMenu.put("Ouvrir",          new MenuItem("Ouvrir..."));
      itemsMenu.put("Enregistrer",     new MenuItem("Enregistrer"));
      itemsMenu.put("EnregistrerSous", new MenuItem("Enregistrer sous..."));
      
      itemsMenu.put("-",null);
      itemsMenu.put("MiseEnPage",      new MenuItem("Mise en page..."));
      itemsMenu.put("Imprimer",        new MenuItem("Imprimer...")); 
      
      itemsMenu.put("--",null);
      itemsMenu.put("Quitter",         new MenuItem("Quitter")); 

      // --- Installation des items
      //     
      Iterator i= itemsMenu.keySet().iterator();
      MenuItem itemMenu;
      
      while (i.hasNext()) {
      	
         itemMenu= (MenuItem)itemsMenu.get(i.next());
         if (itemMenu == null) addSeparator();
         else add(itemMenu);
      } 
      
      // --- Installation d'un ecouteur par defaut sur chaque item de menu
      //     
      Iterator j= itemsMenu.keySet().iterator();
      String cle= null;
      
      while (j.hasNext()) {
      	
      	 cle= (String)(j.next());
         itemMenu= (MenuItem)itemsMenu.get(cle);
         if (itemMenu != null) {    
            itemMenu.addActionListener(new EcouteurItemMenu(cle));
         }
      } 
   } 
   
   // --- Accesseur de consultation du dictionnaire des items de menu
   //
   public LinkedHashMap getItemsMenu() {return itemsMenu;} 
   
   // --- Ecouteur standard d'items de menus
   //
   private class EcouteurItemMenu implements ActionListener {
   private String labelItem;
   	
      public EcouteurItemMenu(String label) {
         labelItem= label;
      }
   	
      public void actionPerformed (ActionEvent e) {
         System.out.println(labelItem);
      }
   }      
}
