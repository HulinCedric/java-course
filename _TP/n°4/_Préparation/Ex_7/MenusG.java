//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package SWING
//
// Classe MenusG - Creation d'une barre de menus parametree par des
//                 dictionnaires de configuration
//
// Edition A    : la barre s'accroche automatiquement au cadre support 
//    + Version 1.0.0 : version limitee aux noms des menus
//    + Version 1.1.0 : mise en place d'un hamecon sur le frame englobant
//    + Version 1.2.0 : mise en place de la gestion des items a partir de
//                      fichiers de configuration (un fichier par menu)
//    + Version 1.3.0 : introduction de tous les fichiers de configuration
//                      des menus Word dans le point d'entree
//    + Version 1.4.0 : portage sous SWING
//    + Version 1.5.0 : introduction d'une gestion generique des evenements 
//                      attaches aux items de menus                                        
//
// L'attribut "menus" de la classe MenusG est un dictionnaire dont les cles 
// sont les noms des menus et dont les associes sont des references (au sens 
// Java du terme) sur les instances de la classe Menu, ajoutees a la barre 
// principale AWT.
//
// L'attribut "items" est un dictionnaire dont les cles sont aussi les noms des 
// menus et dont les associes sont eux memes des dictionnaires. Chaque sous-
// dictionnaire a pour cles les noms des items du menu cible et pour associes 
// des references (au sens Java du terme) sur les instances de la classe 
// MenuItem, ajoutees au menu cible.
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class MenusG extends JMenuBar {
private LinkedHashMap menus;
private LinkedHashMap items;
private JFrame hameconAmont;

//                                                  *** Constructeur par defaut
//
   public MenusG (JFrame parent) {                         
   	
      menus       = new LinkedHashMap();
      items       = new LinkedHashMap();
      hameconAmont= parent;
   }

//                                                  *** Constructeur normal
//
   public MenusG (LinkedHashMap config, JFrame parent) {    
   	
      this(parent);
   	  
      // Controler la validite de la configuration
      //
      if (config==null) return; 

      // Creer et ajouter chaque menu a la barre de menus
      //
      Iterator i=config.keySet().iterator();
      String cle=null;
      String associe= null;
      boolean ok= true;
   	     	
      while(i.hasNext()) {

         // Acquerir la cle courante
         //
         cle=(String)i.next();
         
         // Acquerir l'associe courant
         //
         associe= (String)config.get(cle);
         
         // Creer et ajouter le nouveau menu a la barre de menus
         // 
         ok= ajouterMenu(cle, associe);
         if(!ok) return;
      }
      
      // Accrocher la barre au frame englobant
      //
      hameconAmont.setJMenuBar(this);
   }
   
   // Ajouter un nouveau menu avec ses items           *** Methode ajouterMenu
   //
   public boolean ajouterMenu(String nomMenu, String associe) {
   LinkedHashMap config= null;
   	
      // Controler la validite du nom du menu
      //
      if (nomMenu==null) return false;
   	  
      // Controler l'absence du menu dans le dictionnaire des menus
      //
      if (menus.containsKey(nomMenu)) return false;
   	  
      // Creer et ajouter le nouveau menu a la barre de menus
      // 
      JMenu menu= new JMenu(nomMenu);
      add(menu);
   	  
      // Ajouter le nouveau menu dans le dictionnaire de tous les menus 
      // (attribut)
      //
      menus.put(nomMenu, menu);
      
      // Traiter le cas particulier de l'absence d'items
      //
      if (associe==null) {
         items.put(nomMenu, null);
         return true;
      }
      
      // Charger le dictionnaire de configuration des items
      //
      config= (LinkedHashMap)Config.load(nomMenu, associe);
      if (config==null) return false;
      
      // Memoriser la configuration chargee dans le dictionnaire global de
      // tous les items
      //
      items.put(nomMenu, config);
      
      // Creer et ajouter tous les items au menu courant
      //
      return ajouterItems(nomMenu);
   }
   
//  Creer et ajouter tous les items d'un menu          *** Methode AjouterItems
//   
   private boolean ajouterItems(String nomMenu) {
   LinkedHashMap config= null;
   	
      // Controler la validite du parametre
      //
      if (nomMenu==null) return false;
      
      // Obtenir le dictionnaire de configuration de tous les items
      //
      config= (LinkedHashMap)items.get(nomMenu);
      if (config==null) return false;
      
      // Creer et ajouter chaque item au menu cible
      //
      Iterator i=config.keySet().iterator();
      String cle=null;
      boolean ok= true;
   	     	
      while(i.hasNext()) {

         // Acquerir la cle courante
         //
         cle=(String)i.next();
         
         // Creer et ajouter un nouvel item au menu cible
         // 
         ok= ajouterItem(nomMenu, cle);
         if(!ok) return false;
      }
         
      return true;
   }
         
   
// Ajouter un nouvel item dans un menu cible           *** Methode ajouterItem
//   
   private boolean ajouterItem(String nomMenu, String nomItem) {
   JMenu menuCourant= null;
   JMenuItem itemCourant= null;
   LinkedHashMap w= null;
   	
      // Controler la validite des parametres
      //
      if (nomMenu==null) return false;
      if (nomItem==null) return false;
      
      // Obtenir la reference du menu cible
      //
      menuCourant= (JMenu)menus.get(nomMenu);
      if (menuCourant==null) return false;
      
      // Traiter le cas particulier d'une barre de separation
      //
      if (nomItem.substring(0,1).equals("_")){ 
         menuCourant.addSeparator();
         itemCourant= null;
      }
      else {
   	  
         // Creer et ajouter le nouvel item au menu cible
         // 
         itemCourant= new JMenuItem(nomItem);
         menuCourant.add(itemCourant);
      }
      
      // Ajouter la reference du nouvel item dans le dictionnaire de tous les
      // items (attribut)
      //
      w= (LinkedHashMap)items.get(nomMenu);
      if (w==null) return false;
      
      w.put(nomItem, itemCourant);
   
      return true;
   }
   
//                                                           *** Methode main
//
   public static void main(String[] args) {
   LinkedHashMap config;
   
      // Creer un cadre par defaut
      //
      JFrame f= new JFrame();
      f.setSize(900, 500);
      f.setTitle ("(SWING) Test de la barre de menus generique - V 1.5.0");
      f.setLocation (0, 0);
      
      // Charger la configuration de test de la barre de menus
      //
      config= (LinkedHashMap)Config.load("ConfigMenusG", "1.5.0");
      
      // Creer et accrocher la barre de test a la fenetre support
      //
      MenusG b= new MenusG(config, f);

      // Visualiser la fenetre de test
      //
      f.setVisible(true);
   }
}

