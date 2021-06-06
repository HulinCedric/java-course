/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Applications graphiques
 * 
 * @edition A : Version initiale
 * 
 * @version 0.0.0
 * 
 *          Mise en place de fichier de configuration
 */
import java.awt.Color;
import java.util.HashMap;

import javax.swing.JFrame;

/**
 * Version informatique du celebre jeu du sudoku
 * 
 * @version 0.0.0
 * @author C. Hulin
 */
public class Sudoku {
    private JFrame   cadre;
    private PanneauG panneau;
    private String   titre;
    
    /**
     * Constructeur normal
     * 
     * @param config
     * @since 0.0.0
     * @author C. Hulin
     */
    public Sudoku(HashMap config) {
        
        if (config == null) return;
        setTitle(config);
        
        // Creer un cadre support
        //      
        cadre = new JFrame();
        cadre.setTitle(titre);
        cadre.setSize(500, 500);
        cadre.setLocation(350, 200);
        cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Creer et configurer ce panneau
        //
        panneau = new PanneauG(cadre, config);
        
        // Ajouter une image de fond
        //
        // panneau.ajouterImage(path);
        
        // Ajouter un maillage rectangulaire couvrant du
        // panneau principal
        //
        panneau.ajouterMaillage(Color.BLACK, Color.WHITE, 3, 3);
        
        // Construire un ecouteur dedie et l'ajouter au
        // panneau principal
        //
        // panneau.ajouterEcouteur(new EcouteurIM(panneau));
        
        // Ajouter le panneau principal au cadre support
        //
        cadre.getContentPane().add(panneau);
        
        // Montrer le cadre support et son panneau
        // interne
        //
        cadre.setVisible(true);
    }
    
    /**
     * SetTitle
     * 
     * @param config
     * @since 0.0.0
     * @author C. Hulin
     */
    private void setTitle(HashMap config) {
        String defaultValue = "Jeu du Sudoku";
        String value = defaultValue;
        Object w;
        
        if (config == null) {
            titre = defaultValue;
            return;
        }
        w = config.get("titre");
        if (w != null) value = (String) w;
        titre = value;
    }
    
    /**
     * Main
     * 
     * @param argv
     * @since 0.0.0
     * @author C. Hulin
     */
    public static void main(String[] argv) {
        
        // Recuperer la configuration du jeu
        //
        HashMap config = (HashMap) Config.load("ConfigSudoku", "1.0.0");
        
        // Creer une instance du jeu sudoku
        //
        new Sudoku(config);
    }
}
