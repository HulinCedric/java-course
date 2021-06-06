/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Applications graphiques
 * 
 * @edition A : limitee aux tests fonctionnels de l'IHM
 * 
 * @version 1.0.0
 * 
 *          version initiale
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;

/**
 * Description de la configuration du jeu l'image
 * mysterieuse
 * 
 * @author C. Hulin
 * @version 1.0.0
 */
public class ConfigImageMysterieuse {
    
    // Description des parametres de configuration d'une
    // table
    //   	
    public static HashMap configurerTable() {
        HashMap config = new HashMap();
        
        // Titre de la page du jeu
        //
        config.put("titre", "Table_IM");
        
        // Donnees de configuration
        //
        config.put("path", "Fleurs/Gaillardes.jpg");
        config.put("ll", new Integer(10));
        config.put("cc", new Integer(15));
        
        // Donnees de connexion
        //
        config.put("Prefixe", "Table");
        config.put("Port", new Integer(8080));
        config.put("nbClients", new Integer(1));
        
        // Donnees de panneauG
        //
        config.put("arrierePlan", Color.black);
        config.put("avantPlan", Color.gray);
        config.put("police", new Font("Times Roman", Font.BOLD, 16));
        config.put("grille", new Dimension(10, 10));
        
        return config;
    }
    
    // Description des parametres de configuration d'un
    // joueur
    //      
    public static HashMap configurerJoueur() {
        HashMap config = new HashMap();
        
        // Titre de la page du jeu
        //
        config.put("titre", "Joueur_IM");
        
        // Identifiant du joueur
        //
        config.put("Prefixe", "Joueur_1");
        
        // Donnees de connexion
        //
        config.put("Host", "localHost");
        
        // Donnees de panneauG
        //
        config.put("arrierePlan", Color.black);
        config.put("avantPlan", Color.gray);
        config.put("police", new Font("Times Roman", Font.BOLD, 16));
        config.put("grille", new Dimension(10, 10));
        
        return config;
    }
    
    /**
     * Main
     * 
     * @param args
     */
    public static void main(String[] args) {
        HashMap configTable = configurerTable();
        HashMap configJoueur = configurerJoueur();
        
        Config.store(configTable, "ConfigImageMysterieuseTable", "1.0.0");
        Config.load("ConfigImageMysterieuseTable", "1.0.0");
        Config.store(configJoueur, "ConfigImageMysterieuseJoueur", "1.0.0");
        Config.load("ConfigImageMysterieuseJoueur", "1.0.0");
    }
}
