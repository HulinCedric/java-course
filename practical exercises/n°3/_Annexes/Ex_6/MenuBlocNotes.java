//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuBlocNotes - Description de la configuration du menu ?
//
// Auteur : C. Hulin
//
import java.util.*;

public class MenuBlocNotes
{
	// Description des parametres de configuration
	//   	
	public static LinkedHashMap configuration()
	{
		LinkedHashMap config= new LinkedHashMap();
	   
		config.put("Rubrique d'aide",	null); 
		config.put("------",null);
		config.put("Ë propos du Bloc-notes",	null); 

	 	return config;
	}
	   
	public static void main(String[] args) 
	{
		LinkedHashMap config= configuration();
	   
		Config.store(config, "?", "1.0.0");
		Config.load("?", "1.0.0");
	}
}
