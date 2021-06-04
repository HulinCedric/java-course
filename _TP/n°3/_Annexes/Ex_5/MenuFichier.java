//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuFichier - Description de la configuration du menu Fichier
//
// Auteur : C. Hulin
//
import java.util.LinkedHashMap;

public class MenuFichier
{
	// Description des parametres de configuration
	//   	
	public static LinkedHashMap configuration()
	{
		LinkedHashMap config= new LinkedHashMap();
	  
		config.put("Nouveau...",			null);
		config.put("Ouvrir...", 			null);
		config.put("Enregistrer",   		null);
	 	config.put("Enregistrer sous...", 	null);
	 	config.put("-",						null);
		config.put("Mise en page...",		null);
		config.put("Imprimer...", 			null); 
		config.put("--",					null);
	 	config.put("Quitter",				null); 

	 	return config;
	}
	   
	public static void main(String[] args) 
	{
		LinkedHashMap config= configuration();
	   
		Config.store(config, "Fichier", "1.0.0");
		Config.load("Fichier", "1.0.0");
	}
}