//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuFichier - Description de la configuration du menu Fichier
//
// Auteur : C. Hulin
//
import java.awt.MenuItem;
import java.util.LinkedHashMap;

public class MenuFichier
{
	// Description des parametres de configuration
	//   	
	public static LinkedHashMap configuration()
	{
		LinkedHashMap config= new LinkedHashMap();
	  
		config.put("Nouveau...", 			new MenuItem("Nouveau..."));
		config.put("Ouvrir...", 			new MenuItem("Ouvrir.."));
		config.put("Enregistrer",   		new MenuItem("Enregistrer"));
	 	config.put("Enregistrer sous...", 	new MenuItem("Enregistrer sous..."));
	 	config.put("-",						null);
		config.put("Mise en page...",		new MenuItem("Mise en page..."));
		config.put("Imprimer...", 			new MenuItem("Imprimer...")); 
		config.put("--",					null);
	 	config.put("Quitter",				new MenuItem("Quitter")); 

	 	return config;
	}
	   
	public static void main(String[] args) 
	{
		LinkedHashMap config= configuration();
	   
		Config.store(config, "Fichier", "1.0.0");
		Config.load("Fichier", "1.0.0");
	}
}