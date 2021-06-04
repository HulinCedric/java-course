//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuFichier : menu "Fichier" de l'application FenetreSaisieTexte
//
// Edition A 	: description statique uniquement
//    + Version 1.0.0   : avec accesseur de lecture des items de menus
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
   } 

   public LinkedHashMap getItemsMenu() {return itemsMenu;}      
}
