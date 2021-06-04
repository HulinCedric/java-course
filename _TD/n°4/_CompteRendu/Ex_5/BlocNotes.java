//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe BlocNotes - Application similaire au Bloc-notes windows
//
//    + Version 1.0.0 : version initiale
//    + Version 1.1.0 : Ajout d'ecouteur nouveau
//
// Auteur : C. Hulin
//
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BlocNotes {
	
private CadreG cadre= null;
private MenusG menus= null;
private Panel  panneauSupport= null;
private TextArea zoneAffichage= null;

	//                                             		*** Constructeur normal
	//
	public BlocNotes(HashMap configCadre, LinkedHashMap configMenus) {
		
		// Creer le cadre principal
		//
		cadre= new CadreG(configCadre);
		
		// Ajouter au cadre un panneau central
		//
		panneauSupport= new Panel();
	    panneauSupport.setLayout(new GridLayout(1,0));
		panneauSupport.setForeground(Color.black);
		cadre.add(panneauSupport);
		
		// Ajouter au panneau la zone de saisie des textes 
	  	//
		zoneAffichage= new TextArea(150, 100);
		zoneAffichage.setEditable(false);
		zoneAffichage.setVisible(false);
	  	panneauSupport.add(zoneAffichage);
	  	
	  	// Creer et accrocher la barre des menus au cadre support
	 	//
	  	menus= new MenusG(configMenus, cadre);
	  	
	  	// Ajouteur un ecouteur
	  	//
	  	menus.ajouterEcouteur("Fichier", "Nouveau...", new EcouteurItemNouveau());
	  	
	 	// Visualiser l'ensemble de la fenetre principale BlocNotes
	 	//
	  	cadre.setVisible(true);
	}
	
   //                                          	 		*** Methode main
   //
	public static void main(String[] args) {
	
		// Charger la configuration du cadre principal de la fenetre Word
		//   	
	    HashMap configCadre= (HashMap) Config.load("ConfigCadreG", "1.3.0");
		if(configCadre == null) return;

		// Charger la configuration de la barre de menus
	 	//
		LinkedHashMap configMenus= (LinkedHashMap)Config.load("ConfigMenusG", "1.2.0");
	 	if(configMenus == null) return;
	  		  	
	 	// Instancier BlocNotes
	 	//
	  	new BlocNotes(configCadre, configMenus);
	}

	//  Classe interne 									*** Class EcouteurItemNouveau
	// 
	private class EcouteurItemNouveau implements ActionListener {
			   	
		public void actionPerformed (ActionEvent e) {
			zoneAffichage.setEditable(true);
			zoneAffichage.setVisible(true);
			menus.desactiverItem("Fichier", "Nouveau...");
			menus.activerItem("Fichier", "Enregistrer sous...");
		}
	} 
}
