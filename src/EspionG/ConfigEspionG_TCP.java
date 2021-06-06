/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annexe 2009_2010
 */
import java.awt.Dimension;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Description de la configuration d'un EspionG_TCP
 * 
 * @version 2.0.0
 * @author C. Hulin
 */
public class ConfigEspionG_TCP {
    
    /**
     * Description des parametres de configuration
     * 
     * @return Dictionnaire des proprietes d'un espion
     */
    public static HashMap configurer() {
        HashMap config = new LinkedHashMap();
        
        config.put("Prefixe", "ServeurEspion");
        config.put("PortCommunication", new Integer(8080));
        config.put("NumberOfClient", new Integer(1));
        config.put("Loop", new Integer(100));
        config.put("Ligne", new Integer(100));
        config.put("Colonne", new Integer(2));
        config.put("Dimension", new Dimension(170, 20));
        
        return config;
    }
    
    /**
     * main
     * 
     * @param args
     */
    public static void main(String[] args) {
        HashMap config = configurer();
        
        Config.store(config, "ConfigEspionG_TCP", "1.0.0");
        Config.load("ConfigEspionG_TCP", "1.0.0");
    }
}
