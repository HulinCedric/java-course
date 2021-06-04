//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Espion
//
// Classe ConfigEspion - Description de la configuration d'un espion
//
// Auteur : A. Thuaire
//
import java.util.LinkedHashMap;

public class ConfigEspion {

	// Description des parametres de configuration
	//   	
	public static LinkedHashMap configurer() {
	LinkedHashMap config= new LinkedHashMap();

		config.put("PriorityThread", new Integer(5));
		config.put("Loop", new Integer(2000));
		config.put("Activation", new Long(System.currentTimeMillis()));
		//config.put("Priority", null);
		config.put("ThreadGroup", null);
		//config.put("Daemon", null);
		config.put("Time", null);
		
		return config;
	}

	public static void main(String[] args) {
	LinkedHashMap config= configurer();

		Config.store(config, "ConfigEspion", "1.0.0");
		Config.load("ConfigEspion", "1.0.0");
	}
}
