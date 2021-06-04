/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annexe 2009_2010
 */
import java.util.LinkedHashMap;

/**
 * Description de la configuration d'un Espion
 * 
 * @version 2.0.0
 * @author C. Hulin
 */
public class ConfigEspionD_TCP {
    
    /**
     * Description des parametres de configuration
     * 
     * @return Dictionnaire des proprietes d'un espion
     */
    public static LinkedHashMap configurer() {
        LinkedHashMap config = new LinkedHashMap();
        LinkedHashMap conditionThread = new LinkedHashMap();
        LinkedHashMap conditionThread_2 = new LinkedHashMap();
        LinkedHashMap conditions = new LinkedHashMap();
        
        // Identifiant du client
        //
        config.put("Prefixe", "ClientEspion-1");
        
        // Donnees de connexion
        //
        config.put("Host", "localHost");
        config.put("PortCommunication", new Integer(8080));
        
        // Donnee relative a la priorite du thread du client
        //
        config.put("PriorityThread", new Integer(5));
        
        // Donnee relative au temps en millisecondes
        // d'intervale de visualisation
        //
        config.put("Loop", new Integer(2000));
        
        // Date a laquel le client se mettra en fonction
        //
        config.put("Activation", new Long(System.currentTimeMillis()));
        
        // Donnees que l'on veux filtrer
        //
        // config.put("Priority", null);
        // config.put("ThreadGroup", null);
        // config.put("Daemon", null);
        config.put("Time", null);
        
        // Remplissage du dictionnaire des conditions de
        // transmissions relative a un thread
        //
        conditionThread.put("Priority_1", new Integer(6));
        conditionThread.put("bool1", "OU");
        conditionThread.put("Priority_2", new Integer(5));
        conditionThread.put("bool2", "ET");
        conditionThread.put("Daemon", new Boolean(false));
        
        // Remplissage du dictionnaire des conditions de
        // transmissions relative a un deuxieme thread
        //
        conditionThread_2.put("Priority", new Integer(6));
        conditionThread_2.put("bool1", "ET");
        conditionThread_2.put("ThreadGroup", "main");
        
        // Remplissage du dictionnaire de toutes les
        // conditions relative a tous les threads
        //
        conditions.put("main", conditionThread);
        conditions.put("Client_1", conditionThread_2);
        
        // Ajout du dictionnaire de conditions au
        // dictionnaire de configuration
        //
        //config.put("conditions", conditions);
        
        return config;
    }
    
    /**
     * main
     * 
     * @param args
     */
    public static void main(String[] args) {
        LinkedHashMap config = configurer();
        
        Config.store(config, "ConfigEspionD_TCP", "2.0.0");
        Config.load("ConfigEspionD_TCP", "2.0.0");
    }
}
