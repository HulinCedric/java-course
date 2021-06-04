/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Applications graphiques
 * 
 * @edition A : limitee aux tests fonctionnels de l'IHM
 * 
 * @version 1.0.0
 * 
 *          limitee a une image locale
 * 
 * @version 1.1.0
 * 
 *          gestion de construction table_IM et joueur_IM
 *          + modification de l'ecouteur
 *          + ajout d'accesseurs de consultation
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JFrame;

/**
 * ImageMysterieuse - Jeu de plateau eponyme
 * 
 * @author C. Hulin
 * @version 1.1.0
 */
public class ImageMysterieuse {
    private JFrame cadre;
    private PanneauG panneau;
    private HashMap config;
    private String titre;
    private int colonne;
    private int ligne;
    private String path;
    
    /**
     * Constructeur normal
     * 
     * @param config
     */
    public ImageMysterieuse(HashMap config) {
        
        if (config == null) return;
        this.config = (HashMap) config.clone();
        setLine(config);
        setColumn(config);
        setPath(config);
        setTitle(config);
        
        // Creer un cadre support
        //      
        cadre = new JFrame();
        cadre.setTitle(titre);
        cadre.setSize(500, 300);
        cadre.setLocation(350, 350);
        
        // Cas d'un serveur
        // 
        if (colonne != 0 && ligne != 0 && path != null) {
            
            // Creer et configurer ce panneau
            //
            panneau = new PanneauG(cadre, config);
            
            // Ajouter une image de fond
            //
            panneau.ajouterImage(path);
            
            // Ajouter un maillage rectangulaire couvrant du
            // panneau principal
            //
            panneau.ajouterMaillage(Color.yellow, Color.black, ligne, colonne);
            
            // Construire un ecouteur dedie et l'ajouter au
            // panneau principal
            //
            panneau.ajouterEcouteur(new EcouteurIM(panneau));
            
            // Ajouter le panneau principal au cadre support
            //
            cadre.getContentPane().add(panneau);
            
            // Montrer le cadre support et son panneau
            // interne
            //
            cadre.setVisible(true);
        }
    }
    
    /**
     * setLine
     * 
     * @param config
     */
    private void setLine(HashMap config) {
        int defaultValue = 0;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            ligne = defaultValue;
            return;
        }
        w = config.get("ll");
        if (w != null) value = ((Integer) w).intValue();
        ligne = value;
    }
    
    /**
     * setColumn
     * 
     * @param config
     */
    private void setColumn(HashMap config) {
        int defaultValue = 0;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            colonne = defaultValue;
            return;
        }
        w = config.get("cc");
        if (w != null) value = ((Integer) w).intValue();
        colonne = value;
    }
    
    /**
     * setPath
     * 
     * @param config
     */
    private void setPath(HashMap config) {
        String defaultValue = null;
        String value = defaultValue;
        Object w;
        
        if (config == null) {
            path = defaultValue;
            return;
        }
        w = config.get("path");
        if (w != null) value = (String) w;
        path = value;
    }
    
    /**
     * setTitle
     * 
     * @param config
     */
    private void setTitle(HashMap config) {
        String defaultValue = "Jeu de l'image mysterieuse";
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
     */
    public static void main(String[] argv) {
        
        // Recuperer la configuration du jeu
        //
        HashMap config = (HashMap) Config.load("ConfigImageMysterieuseJoueur", "1.0.0");
        
        // Creer une instance du jeu l'image mysterieuse
        //
        ImageMysterieuse test = new ImageMysterieuse(config);
        
        // Indiquer les donnees pour afficher correctement
        // le jeu
        //
        test.configurer("Fleurs/rose.jpg", 10, 15);
    }
    
    /**
     * Configurer l'ecran de l'image mysterieuse (Cas du
     * Joueur_IM)
     * 
     * @param chemin
     * @param ligne
     * @param colonne
     */
    public void configurer(String chemin, int ligne, int colonne) {
        
        // Creer et configurer ce panneau
        //
        panneau = new PanneauG(cadre, config);
        
        // Ajouter une image de fond
        //
        panneau.ajouterImage(chemin);
        
        // Ajouter un maillage rectangulaire couvrant du
        // panneau principal
        //
        panneau.ajouterMaillage(Color.yellow, Color.black, ligne, colonne);
        
        // Construire un ecouteur dedie et l'ajouter au
        // panneau principal
        //
        ImageMysterieuse.EcouteurIM ecouteur = new EcouteurIM(panneau);
        panneau.ajouterEcouteur(ecouteur);
        
        // Ajouter le panneau principal au cadre support
        //
        cadre.getContentPane().add(panneau);
        
        // Montrer le cadre support et son panneau interne
        //
        cadre.setVisible(true);
    }
    
    /**
     * Obtention des coordonnes de la ligne cliquer par
     * l'utilisateur
     * 
     * @return coordonne de la ligne cliquer
     */
    public int obtenirLigne() {
        int l = ligne;
        ligne = 0;
        return l;
    }
    
    /**
     * Obtention des coordonnes de la colonne cliquer par
     * l'utilisateur
     * 
     * @return coordonne de la colonne cliquer
     */
    public int obtenirColonne() {
        int c = colonne;
        colonne = 0;
        return c;
    }
    
    /**
     * Enlever une cellule de la maille
     * 
     * @param ligne
     * @param colonne
     */
    public void retirerCellule(int ligne, int colonne) {
        
        // Verifier les parametres
        //
        if (ligne < 0 || colonne < 0) return;
        
        // Retirer la cellule
        //
        panneau.obtenirMaillage().retirerMaille(ligne, colonne);
    }
    
    /**
     * retirerMaillage
     */
    public void retirerMaillage() {
        panneau.retirerMaillage();
    }
    
    /**
     * Classe interne privee EcouteurIM
     * 
     * @author C. Hulin
     * @version 1.1.0
     */
    private class EcouteurIM extends EcouteurPanneauG {
        
        /**
         * Constructeur normal
         * 
         * @param hamecon
         */
        private EcouteurIM(PanneauG hamecon) {
            panneauCible = hamecon;
        }
        
        /**
         * Clique de souris recuperer
         */
        public void mouseClicked(MouseEvent e) {
            
            // Obtenir les coordonnees absolues du clic
            // courant
            //
            int x = e.getX();
            int y = e.getY();
            
            // Obtenir la designation de la maille cible
            //
            Dimension cible = panneauCible.obtenirPositionCible(x, y);
            if (cible == null) return;
            
            // Extraire les coordonnees (ligne, colonne) de
            // cette cellule
            //
            ligne = (int) cible.getWidth();
            colonne = (int) cible.getHeight();
        }
    }
}
