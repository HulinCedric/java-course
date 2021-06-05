/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2008_2009 - Fichier de configuration
 */
import java.util.HashMap;

/**
 * Description de la configuration du jeu Sudoku
 * 
 * @version 1.0.0
 * @author C. Hulin
 */
public class ConfigSudoku {
    
    // Description des parametres de configuration
    //   	
    public static HashMap configuration() {
        HashMap config = new HashMap();
        
        config.put("titre", "Sudoku - 0.0.0");
        
        return config;
    }
    
    public static void main(String[] args) {
        
        Config.store(configuration(), "ConfigSudoku", "1.0.0");
    }
}
