//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe BlocNotes - Application similaire au Bloc-notes windows
//
//    + Version 1.0.0 : version initiale
//    + Version 1.1.0 : Ajout de l'ecouteur nouveau
//    + Version 1.2.0 : Ajout de l'ecouteur enregistrer sous
//
// Auteur : C. Hulin
//
import java.awt.Color;
import java.awt.FileDialog;
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
	  	
	  	// Ajouteur les ecouteurs
	  	//
	  	menus.ajouterEcouteur("Fichier", "Nouveau...", new EcouteurItemNouveau());
	  	
	  	menus.ajouterEcouteur("Fichier", "Enregistrer sous...", new EcouteurItemEnregistrerSous());
	  	menus.desactiverItem("Fichier", "Enregistrer sous...");
	  	
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

	//  Classe interne Nouveau							*** Class EcouteurItemNouveau
	// 
	private class EcouteurItemNouveau implements ActionListener {
			   	
		public void actionPerformed (ActionEvent e) {
			
			// Rendre la TextArea editable, visible et vide
			//
			zoneAffichage.setText("");
			zoneAffichage.setEditable(true);
			zoneAffichage.setVisible(true);
			
			// Activer et desactiver les item
			//
			menus.desactiverItem("Fichier", "Nouveau...");
			menus.activerItem("Fichier", "Enregistrer sous...");
			
			// Changer le titre du cadre
			//
			cadre.setTitle("...");
		}
	} 
	
	//  Classe interne EnregistrerSous					*** Class EcouteurItemEnregistrerSous
	// 
	private class EcouteurItemEnregistrerSous implements ActionListener {
			   	
		public void actionPerformed (ActionEvent e) {

			// Instancier une boite de dialogue pour sauver un fichier
			//
			FileDialog dialog= new FileDialog(cadre, "Enregistrer sous...", FileDialog.SAVE);
			dialog.setVisible(true);
			
			// Gerer le cas de l'annulation
			//
			if (dialog.getFile() == null)	return;

			// Sauver le nom du fichier actuel
			//
			fichierActuel= dialog.getFile();
			
			// Sauver le texte de la TextArea dans un fichier.data
			//
			Data.store(zoneAffichage.getText(), fichierActuel, "1.0.0");
			
			// Changer le titre du cadre
			//
			cadre.setTitle(fichierActuel + " - 1.0.0");
		}
	} 
}
