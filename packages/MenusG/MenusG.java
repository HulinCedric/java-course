//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenusG - Creation d'une barre de menus parametree par un
//                 dictionnaire de configuration
//
// Edition A    : la barre s'accroche automatiquement au cadre support 
//    + Version 1.0.0 : version limitee aux noms des menus
//    + Version 1.1.0 : mise en place d'un hamecon sur le frame englobant
//    + Version 1.2.0 : mise en place de la gestion des items a partir de
//                      fichiers de configuration (un fichier par menu)
//    + Version 1.3.0 : prise en compte de l'etat initial (actif / inactif)
//                      de chaque item de menu a sa creation
//    + Version 1.4.0 : introduction de l'accesseur obtenirItem
//    + Version 1.5.0 : introduction des methodes activerItem et desactiverItem
//    + Version 1.6.0 : introduction de la methode ajouterEcouteur
//    + Version 1.7.0 : introduction de la methode retirerEcouteur                   
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
import java.awt.event.*;

public class MenusG extends MenuBar {
private LinkedHashMap menus;
private LinkedHashMap items;
private Frame hameconAmont;

//                                                  *** Constructeur par defaut
//
   public MenusG (Frame parent) {                         
   	
      menus       = new LinkedHashMap();
      items       = new LinkedHashMap();
      hameconAmont= parent;
   }

//                                                  *** Constructeur normal
//
   public MenusG (LinkedHashMap config, Frame parent) {    
   	
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
      hameconAmont.setMenuBar(this);
   }
   
   // --- Ajouter un nouveau menu avec ses items      *** Methode ajouterMenu
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
      Menu menu= new Menu(nomMenu);
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
      String cle= "Menu"+nomMenu;
      config= (LinkedHashMap)Config.load(cle, associe);
      if (config==null) return false;
      
      // Memoriser la configuration chargee dans le dictionnaire global de
      // tous les items
      //
      items.put(nomMenu, config);
      
      // Creer et ajouter tous les items au menu courant
      //
      return ajouterItems(nomMenu);
   }
   
//  --- Module de tests unitaires                            *** Methode main
//
   public static void main(String[] args) {
   LinkedHashMap config;
   
      // Creer un cadre par defaut
      //
      Frame f= new Frame();
      f.setSize(700, 400);
      f.setTitle ("(AWT) Test de la barre de menus generique - V 1.7.0");
      f.setLocation (400, 300);
      
      // Charger la configuration de test de la barre de menus
      //
      config= (LinkedHashMap)Config.load("ConfigMenusG", "1.7.0");
      
      // Creer et accrocher la barre de test a la fenetre support
      //
      MenusG b= new MenusG(config, f);

      // Visualiser la fenetre de test
      //
      f.setVisible(true);
      
      // Ajouter dynamiquement un nouveau menu
      //
      b.ajouterMenu("Xxxxx", null);
      
      // Activer dynamiquement l'item "Enregister"
      //
      b.activerItem("Fichier", "Enregistrer");
      
      // Desactiver dynamiquement l'item "Nouveau"
      //
      b.desactiverItem("Fichier", "Nouveau");
      
      // Activer dynamiquement l'item "Enregister sous..."
      //
      b.activerItem("Fichier", "Enregistrer sous...");
      
      // Ajouter l'ecouteur de test a l'item "Enregistrer sous..."
      //
      b.ajouterEcouteur("Fichier", "Enregistrer sous...", 
                        new EcouteurItemMenu("Enregistrer sous... !"));
                        
      // Retirer l'ecouteur de test a l'item "Enregistrer sous..."
      //
      b.retirerEcouteur("Fichier", "Enregistrer sous...", 1);
   }
   
//  --- Creer et ajouter tous les items d'un menu     *** Methode ajouterItems
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
      Boolean associe= null;
   	     	
      while(i.hasNext()) {

         // Acquerir la cle courante (label de l'item)
         //
         cle=(String)i.next();
         
         // Acquerir l'associe (etat initial de l'item)
         //
         associe= (Boolean)config.get(cle);
         
         // Creer et ajouter un nouvel item au menu cible
         // 
         ok= ajouterItem(nomMenu, cle, associe);
         if(!ok) return false;
      }
         
      return true;
   }
   
// --- Ajouter un nouvel item dans un menu cible      *** Methode ajouterItem
//   
   private boolean ajouterItem (String  nomMenu, 
                                String  nomItem, 
                                Boolean etatInitial) {
   Menu menuCourant= null;
   MenuItem itemCourant= null;
   LinkedHashMap w= null;
   	
      // Controler la validite des parametres
      //
      if (nomMenu==null) return false;
      if (nomItem==null) return false;
      
      // Obtenir la reference du menu cible
      //
      menuCourant= (Menu)menus.get(nomMenu);
      if (menuCourant==null) return false;
      
      // Traiter le cas particulier d'une barre de separation
      //
      if (nomItem.substring(0,1).equals("-")){ 
         menuCourant.addSeparator();
         itemCourant= null;
      }
      else {
   	  
         // Creer et ajouter le nouvel item au menu cible
         // 
         itemCourant= new MenuItem(nomItem);
         menuCourant.add(itemCourant);
         
         // Traiter l'etat initial de l'item
         //
         if (etatInitial != null && 
             ! etatInitial.booleanValue())itemCourant.setEnabled(false);
      }
      
      // Ajouter la reference du nouvel item dans le dictionnaire de tous les
      // items (attribut)
      //
      w= (LinkedHashMap)items.get(nomMenu);
      if (w==null) return false;
      
      w.put(nomItem, itemCourant);
   
      return true;
   }
   
// --- Obtenir la reference d'un item de menu         *** Methode obtenirItem
//   
   public MenuItem obtenirItem (String nomMenu, String  nomItem) {
   	
      // Controler la validite des parametres
      //
      if (nomMenu==null) return null;
      if (nomItem==null) return null;
      
      // Obtenir le dictionnaire des items du menu cible
      //
      LinkedHashMap w= (LinkedHashMap)items.get(nomMenu);
      if (w==null) return null;
      
      // Obtenir la reference de l'item cible
      //
      return (MenuItem)w.get(nomItem);
   }
   
// --- Activer un item d'un menu cible               *** Methode activerItem
//   
   public boolean activerItem (String  nomMenu, String  nomItem) {
        
      // Obtenir la reference de l'item cible
      //
      MenuItem itemCible= obtenirItem(nomMenu, nomItem);
      if (itemCible==null) return false;
      
      // Activer l'item cible
      //
      itemCible.setEnabled(true);
      return true;
   }
   
// --- Desactiver un item d'un menu cible            *** Methode desactiverItem
//   
   public boolean desactiverItem (String  nomMenu, String  nomItem) {
   	
      // Obtenir la reference de l'item cible
      //
      MenuItem itemCible= obtenirItem(nomMenu, nomItem);
      if (itemCible==null) return false;
      
      // Desactiver l'item cible
      //
      itemCible.setEnabled(false);
      return true;
   }
   
// --- Attacher un ecouteur a un item cible        *** Methode ajouterEcouteur
//   
   public boolean ajouterEcouteur (String         nomMenu, 
                                   String         nomItem,
                                   ActionListener ecouteur) {
        
      // Obtenir la reference de l'item cible
      //
      MenuItem itemCible= obtenirItem(nomMenu, nomItem);
      if (itemCible==null) return false;
      
      // Controler la validite du troisieme parametre
      //
      if(ecouteur == null) return false;
      
      // Ajouter l'ecouteur fourni en parametre
      //
      itemCible.addActionListener(ecouteur);
      return true;
   }
   
// --- Retirer un ecouteur a un item cible         *** Methode retirerEcouteur
//   
   public boolean retirerEcouteur (String nomMenu, String nomItem,int rang) {
   	
   	  // Obtenir la reference de l'item cible
      //
      MenuItem itemCible= obtenirItem(nomMenu, nomItem);
      if (itemCible==null) return false;
      
      // Obtenir le tableau courant des ecouteurs attaches a l'item
      //
      ActionListener[] ecouteurs= itemCible.getActionListeners();
      if (ecouteurs.length == 0) return false;
  
      // Controler la validite du premier parametre
      //
      if(rang <= 0 || rang > ecouteurs.length) return false;
      
      // Retirer l'ecouteur designe par son rang
      //
      itemCible.removeActionListener(ecouteurs[rang-1]);
      
      // Desactiver l'item cible si opportun 
      //
      if (ecouteurs.length - 1 == 0) itemCible.setEnabled(false);
      return true;
   }
   
   // --- Ecouteur de tests des items de menus
   //
   private static class EcouteurItemMenu implements ActionListener {
   private String labelItem;
   	
      public EcouteurItemMenu(String label) {
         labelItem= label;
      }
   	
      public void actionPerformed (ActionEvent e) {
         System.out.println(labelItem);
      }
   }         
}

