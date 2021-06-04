//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe BlocNotes - Application similaire au Bloc-notes windows
//
// Auteur : C. Hulin
//

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BlocNotes 
{
	public static void main(String[] args) {
	CadreG cadre= null;
	MenusG menus= null;
	Panel  panneauSupport= null;
	TextArea zoneAffichage= null;
   	
	// Charger la configuration du cadre principal de la fenetre Word
	//   	
    HashMap configCadre= (HashMap) Config.load("ConfigCadreG", "1.3.0");
	if(configCadre == null) return;
      
	// Creer le cadre principal
	//
 	cadre= new CadreG(configCadre);
      
 	// Ajouter au cadre un panneau central
	//
	panneauSupport= new Panel();
    panneauSupport.setLayout(new GridLayout(1,0));
	panneauSupport.setForeground(Color.red);
	cadre.add(panneauSupport);
      
	// Ajouter au panneau la zone de saisie des textes 
  	//
	zoneAffichage= new TextArea(150, 100);
	zoneAffichage.setEditable(false);
  	panneauSupport.add(zoneAffichage);
      
	// Charger la configuration de la barre de menus
 	//
	LinkedHashMap configMenus= (LinkedHashMap)Config.load("ConfigMenusG", "1.2.0");
 	if(configMenus == null) return;
      
	// Creer et accrocher la barre des menus au cadre support
 	//
  	menus= new MenusG(configMenus, cadre);
           
 	// Visualiser l'ensemble de la fenetre principale Word
 	//
	cadre.setVisible(true);
   }
}
