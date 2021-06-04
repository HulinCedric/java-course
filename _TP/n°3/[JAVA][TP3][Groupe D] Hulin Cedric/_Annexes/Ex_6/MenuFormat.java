//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuFormat - Description de la configuration du menu Format
//
// Auteur : C. Hulin
//
import java.util.*;

public class MenuFormat
{
	// Description des parametres de configuration
	//   	
	public static LinkedHashMap configuration()
	{
		LinkedHashMap config= new LinkedHashMap();
	   
		config.put("Retour automatique ˆ la ligne",	null);
		config.put("Police...",						null);
	
	 	return config;
	}
	   
	public static void main(String[] args) 
	{
		LinkedHashMap config= configuration();
	   
		Config.store(config, "Format", "1.0.0");
		Config.load("Format", "1.0.0");
	}
}
