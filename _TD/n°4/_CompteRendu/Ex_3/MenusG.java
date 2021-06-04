//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
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
//                      des menus Word + Griser les items qui n'ont pas d'残outeur     
//    + Version 1.4.0 : ajout d'un getter priver sur items      
//    + Version 1.5.0 : ajout de methode de grisage ou de degrisage
//						d'item.
//    + Version 1.6.0 : ajout d'une methode d'ajout d'ecouteurs sur un item
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
// Auteur : Hulin C仕ric
//
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class MenusG extends MenuBar {

private LinkedHashMap menus;
private LinkedHashMap items;
private Frame hameconAmont;

   //                                             		*** Constructeur par defaut
   //
   public MenusG (Frame parent) {                         
   	
      menus       = new LinkedHashMap();
      items       = new LinkedHashMap();
      hameconAmont= parent;
   }

   //                                                  	*** Constructeur normal
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
   
   // Ajouter un nouveau menu avec ses items           	*** Methode ajouterMenu
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
      config= (LinkedHashMap)Config.load(nomMenu, associe);
      if (config==null) return false;
      
      // Memoriser la configuration chargee dans le dictionnaire global de
      // tous les items
      //
      items.put(nomMenu, config);
      
      // Creer et ajouter tous les items au menu courant
      //
      return AjouterItems(nomMenu);
   }
   
   //                                          	 		*** Methode main
   //
   public static void main(String[] args) {
   LinkedHashMap config;
   
      // Creer un cadre par defaut
      //
      Frame f= new Frame();
      f.setSize(900, 500);
      f.setTitle ("(AWT) Test de la barre de menus generique - V 1.6.0");
      f.setLocation (0, 0);
      
      // Charger la configuration de test de la barre de menus
      //
      config= (LinkedHashMap)Config.load("ConfigMenusG", "1.2.0");
      
      // Creer et accrocher la barre de test a la fenetre support
      //
      MenusG menu= new MenusG(config, f);
      
      // Activer et Desactiver
      //
      menu.activerItem("Fichier", "Enregistrer");

      // Visualiser la fenetre de test
      //
      f.setVisible(true);
   }
   
   //  Creer et ajouter tous les items d'un menu    	*** Methode AjouterItems
   //   
   private boolean AjouterItems(String nomMenu) {
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
   	     	
      while(i.hasNext()) 
      {
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
         
   // Ajouter un nouvel item dans un menu cible  		*** Methode ajouterItem
   //   
   private boolean ajouterItem(String nomMenu, String nomItem) {
   Menu menuCourant= null;
   MenuItem itemCourant= null;
   LinkedHashMap config= null;
   	
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
	   if (nomItem.substring(0,1).equals("-"))
	   { 
		   menuCourant.addSeparator();
		   itemCourant= null;
	   }
	   else 
	   {
		   // Creer et ajouter le nouvel item au menu cible
		   // 
		   itemCourant= new MenuItem(nomItem);
		   
		   if(((LinkedHashMap) items.get(nomMenu)).get(nomItem) == null)	itemCourant.setEnabled(false);
         
		   menuCourant.add(itemCourant);
	   }
      
	   // Ajouter la reference du nouvel item dans le dictionnaire de tous les
	   // items (attribut)
	   //
	   config= (LinkedHashMap)items.get(nomMenu);
	   if (config==null) return false;
      
	   config.put(nomItem, itemCourant);
	   items.put(nomMenu, config);
   
	   return true;
   }
   
   // Obtenir un item									*** Methode obtenirItem
   //
   private MenuItem obtenirItem(String nomMenu, String nomItem) {
   MenuItem itemCourant= null;
		   	
	   // Controler la validite des parametres
	   //
	   if (nomMenu==null) return null;
	   if (nomItem==null) return null;
		      
	   // Obtenir la reference du MenuItem cible
	   //
	   itemCourant= (MenuItem) ((LinkedHashMap) items.get(nomMenu)).get(nomItem);
	   if(itemCourant == null)	return null;
		     
	   // Retourner la reference sur le MenuItem
	   //
	   return itemCourant;
   }
   
   // Griser un item									*** Methode activerItem
   //
   public boolean activerItem(String nomMenu, String nomItem) {
   MenuItem itemCourant= null;
	   	
	   // Controler la validite des parametres
	   //
	   if (nomMenu==null) return false;
	   if (nomItem==null) return false;
		      
	   // Obtenir la reference du MenuItem cible
	   //
	   itemCourant= obtenirItem(nomMenu, nomItem);
	   if (itemCourant == null)	return false;
		     
	   // Degriser le MenuItem
	   //
	   itemCourant.setEnabled(true);
	   return true;
   }
   
   // Degriser un item									*** Methode desactiverItem
   //
   public boolean desactiverItem(String nomMenu, String nomItem) {
   MenuItem itemCourant= null;
	   	
	   // Controler la validite des parametres
	   //
	   if (nomMenu==null) return false;
	   if (nomItem==null) return false;
		      
	   // Obtenir la reference du MenuItem cible
	   //
	   itemCourant= obtenirItem(nomMenu, nomItem);
	   if (itemCourant == null)	return false;
		     
	   // Griser le MenuItem
	   //
	   itemCourant.setEnabled(false);
	   return true;
   }
   
   // Ajouter un ecouteur a un item						*** Methode ajouterEcouteur
   //
   public boolean ajouterEcouteur(String nomMenu , String nomItem, ActionListener ecouteur) {
   MenuItem itemCourant=null; 
   	
	   // Controler la validite des parametres
	   //
	   if (nomMenu==null) return false;
	   if (nomItem==null) return false;
	   
	   // Obtenir la reference du MenuItem cible
	   //
	   itemCourant= obtenirItem(nomMenu, nomItem);
	   if (itemCourant == null)	return false;
 
	   // D使riser l'item
	   //
	   if (!desactiverItem(nomMenu, nomItem)) return false;
      
	   // Ajouter l'残outeur sur l'item
	   //
	   itemCourant.addActionListener(ecouteur);
	   return true;
   	}
}