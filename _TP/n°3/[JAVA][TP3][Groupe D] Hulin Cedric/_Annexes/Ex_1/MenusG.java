//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe BarreMenusG - Creation d'une barre de menus parametree par des
//                      dictionnaires de configuration
//
// Edition A    : la barre ne s'accroche pas automatiquement au cadre support 
//    + Version 1.0.0 : version limitee aux noms des menus                   
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class MenusG extends MenuBar {
private LinkedHashMap menus;


//                                               *** Constructeur par defaut
   public MenusG () {                         
   	
      menus    = new LinkedHashMap();
   }

//                                               *** Constructeur normal
   public MenusG (LinkedHashMap config) {    
   	
      this();
   	  
      // Controler la validite de la configuration
      //
      if (config==null) return; 

      // Creer le dictionnaire des menus
      //
      menus= (LinkedHashMap) config.clone();

      // Creer et ajouter chaque menu a la barre de menus
      //
      Iterator i=config.keySet().iterator();
      String cle=null;
      boolean ok= true;
   	     	
      while(i.hasNext()) {

         // Acquerir la cle courante
         //
         cle=(String)i.next();
         
         // Creer et ajouter un nouveau menu a la barre de menus
         // 
         ok= ajouterMenu(cle);
         if(!ok) return;
      }
   }
   
   // Ajouter un nouveau menu (sans items !)        *** Methode ajouterMenu
   //
   public boolean ajouterMenu(String nomMenu) {
   	
      // Controler la validite du nom du menu
      //
      if (nomMenu==null) return false;
   	  
      // Controler l'absence du menu dans le dictionnaire des menus
      //
      if (menus.get(nomMenu) != null) return false;
   	  
      // Creer et ajouter le nouveau menu a la barre de menus
      // 
      Menu menu= new Menu(nomMenu);
      add(menu);
   	  
      // Ajouter le nouveau menu dans le dictionnaire des menus (attribut)
      //
      menus.put(nomMenu, menu);
   
      return true;
   }
   
   public static void main(String[] args) {
   LinkedHashMap config;
   
      // Creer un cadre par defaut
      //
      Frame f= new Frame();
      f.setSize(700, 400);
      f.setTitle ("(AWT) Test de la barre de menus generique - V 1.0.0");
      f.setLocation (400, 300);
      
      // Charger la configuration de test de la barre de menus
      //
      config= (LinkedHashMap)Config.load("ConfigMenusG", "1.0.0");
      
      // Creer et accrocher la barre de test a la fenetre support
      //
      MenusG b= new MenusG(config);
      f.setMenuBar(b);

      // Visualiser la fenetre de test
      //
      f.setVisible(true);
      
      // Ajouter dynamiquement un nouveau menu
      //
      b.ajouterMenu("Xxxxx");
   }
   
}

