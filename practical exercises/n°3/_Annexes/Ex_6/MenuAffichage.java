//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Classe MenuAffichage - Description de la configuration du menu Affichage
//
// Auteur : C. Hulin
//
import java.util.*;

public class MenuAffichage
{
	// Description des parametres de configuration
	//   	
	public static LinkedHashMap configuration()
	{
		LinkedHashMap config= new LinkedHashMap();
	   
		config.put("Barre d'Žtat",	null);

	 	return config;
	}
	   
	public static void main(String[] args) 
	{
		LinkedHashMap config= configuration();
	   
		Config.store(config, "Affichage", "1.0.0");
		Config.load("Affichage", "1.0.0");
	}
}
