//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuEdition - Description de la configuration du menu Edition
//
// Auteur : C. Hulin
//
import java.util.*;
 
public class MenuEdition
{
	// Description des parametres de configuration
	//   	
	public static LinkedHashMap configuration()
	{
		LinkedHashMap config= new LinkedHashMap();
	   
		config.put("Annuler",				null);
	 	config.put("---",					null);
		config.put("Couper",				null);
		config.put("Copier",				null);
	 	config.put("Coller",				null);
	 	config.put("Supprimer",				null);
	 	config.put("----",					null);
		config.put("Rechercher...",			null);
		config.put("Rechercher le suivant",	null); 
		config.put("Remplacer...", 			null); 
		config.put("Atteindre...", 			null); 
		config.put("-----",					null);
		config.put("Selectionner tout",		null); 
		config.put("Heure/Date", 			null); 

	 	return config;
	}
	   
	public static void main(String[] args) 
	{
		LinkedHashMap config= configuration();
	   
		Config.store(config, "Edition", "1.0.0");
		Config.load("Edition", "1.0.0");
	}
}
