/**
 * IUT de Nice / Departement informatique / Module APO-Java Annee
 * 2009_2010 - Composants graphiques generiques
 * 
 * @edition A
 * 
 *          exigences du projet TravelDraw et IE_2 2008_2009
 * 
 * @version 0.0.0
 * 
 *          mise en place des objets graphiques elementaires
 *          "label", "polygone", "trame", "segment" et "image",
 *          avec exploitation du XOR mode
 * 
 * @version 0.1.0
 * 
 *          introduction d'une methode "enregistrer" pour assurer
 *          la persistance dans un fichier de donnees des objets
 *          graphiques poses sur le calque
 * 
 * @version 0.2.0
 * 
 *          introduction d'un second constructeur normal pour
 *          restaurer un calque preexistant a partir d'un fichier
 *          de donnees
 * 
 * @version 0.3.0
 * 
 *          introduction d'une methode "charger" pour charger un
 *          calque preexistant a partir d'un fichier de donnees
 *          et completer ainsi un calque courant
 * 
 * @version 0.4.0
 * 
 *          suppression de l'exploitation du XOR mode
 * 
 * @edition B
 * 
 *          exigences de la classe principale ChronogrammeG
 * 
 * @version 1.0.0
 * 
 *          introduction d'une classe satellite ComposantG
 * 
 * @version 1.1.0
 * 
 *          introduction d'une nouvelle classe satellite TrameG
 *          chargee de modeliser une trame de fond de calque,
 *          sous forme d'une matrice de cellules (type Excel)
 * 
 * @version 1.2.0
 * 
 *          ajout d'un nouvel attribut et d'un accesseur associe
 *          "fixerCouleurTrait" dans la classe ComposantG pour
 *          individualiser la couleur de trait de chaque objet
 *          graphique
 * 
 * @version 1.3.0
 * 
 *          ajout d'un nouvel attribut et d'accesseurs associes
 *          "masquer" et "demasquer" dans la classe ComposantG
 *          pour pouvoir masquer/demasquer dynamiquement chaque
 *          objet graphique
 * 
 * @version 1.4.0
 * 
 *          introduction de la designation d'une cellule courante
 *          dans la trame sous jacente et des methodes de gestion
 *          associees
 * 
 * @version 1.5.0
 * 
 *          introduction d'une gestion individualisee des
 *          bordures "NORD", "SUD", "EST" et "OUEST" de chaque
 *          cellule
 * 
 * @version 1.6.0
 * 
 *          introduction de la possibilite de modifier de facon
 *          dynamique une des bordures d'une cellule cible de la
 *          trame sous jacente
 * 
 * @version 1.7.0
 * 
 *          introduction de la possibilite de supprimer de facon
 *          dynamique une des bordures d'une cellule cible de la
 *          trame sous jacente
 * 
 * @edition C
 * 
 *          exigences de dessin dans le gabarit d'une cellule
 * 
 * @version 2.0.0
 * 
 *          possibilite de dessiner un label dans une cellule
 * 
 * @version 2.1.0
 * 
 *          extension de la gestion individualisee des bordures
 *          hexagonale "NORD-EST", "SUD-EST", "SUD-OUEST",
 *          "NORD-OUEST", "EST" et "OUEST" de chaque cellule
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;

/**
 * Calque de visualisation d'objets graphiques
 * 
 * @author C. Hulin, A. Thuaire
 * @version 2.0.0
 */
public class CalqueG extends PanneauG {
    private LinkedList composants;
    private TrameG     trame;
    
    /**
     * Premier constructeur normal
     * 
     * @param hamecon
     * @param config
     */
    public CalqueG(Object hamecon, HashMap config) {
        
        // Invoquer le constructeur de la classe mere
        //
        super(hamecon, config);
        
        // Initialiser la liste des composants graphiques
        // preexistants
        //
        composants = new LinkedList();
    }
    
    /**
     * Second constructeur normal
     * 
     * @param hamecon
     * @param config
     * @param cheminFichier
     * @param version
     */
    public CalqueG(Object hamecon, HashMap config, String cheminFichier, String version) {
        
        // Invoquer le constructeur de la classe mere
        //
        super(hamecon, config);
        
        // Restaurer les composants graphiques d'un calque
        // preexistant
        //
        composants = restaurer(cheminFichier, version);
    }
    
    /**
     * paint
     */
    public void paint(Graphics g) {
        
        super.paint(g);
        
        // Dessiner la trame du calque
        //
        if (trame != null) trame.dessiner(g);
        
        // Parcourir la liste des composants a dessiner
        //
        ComposantG composant = null;
        
        Iterator i = composants.iterator();
        
        while (i.hasNext()) {
            
            // Dessiner le composant graphique courant
            //
            composant = (ComposantG) i.next();
            composant.dessiner(g);
        }
    }
    
    /**
     * ajouterComposant
     * 
     * @param description
     * @return flag de reussite
     */
    public boolean ajouterComposant(ComposantG description) {
        
        // Controler la validite du parametre
        //
        if (description == null) return false;
        
        // Ajouter la description du composant dans la liste
        // courante
        //
        composants.addLast(description);
        
        // Provoquer le rafraichissement du calque
        //
        repaint();
        
        return true;
    }
    
    /**
     * ajouterComposant
     * 
     * @param ligne
     * @param colonne
     * @param pointCardinal
     * @param texte
     * @return flag de reussite
     */
    public boolean ajouterComposant(int ligne, int colonne, String pointCardinal, String texte) {
        
        // Controler la validite des parametres
        //
        if (ligne < 1 || ligne > trame.nombreLignes()) return false;
        if (colonne < 1 || colonne > trame.nombreColonnes()) return false;
        if (!pointCardinal.equals("NORD") && !pointCardinal.equals("SUD")) return false;
        if (texte == null) return false;
        
        // Obtenir le rectangle de la cellule cible
        //
        Polygon rectangle = trame.obtenirHexagone(ligne, colonne);
        
        // Determiner le coinNO du label a ajouter
        //
        int x, y;
        Dimension coinNO = null;
        
        if (pointCardinal.equals("SUD")) {
            
            x = rectangle.xpoints[1] + 5;
            y = rectangle.ypoints[1] - 5;
            
            coinNO = new Dimension(x, y);
        }
        
        if (pointCardinal.equals("NORD")) {
            
            x = rectangle.xpoints[0] + 5;
            y = rectangle.ypoints[0] + 5;
            
            coinNO = new Dimension(x, y);
        }
        
        // Ajouter le nouveau composant au calque
        //
        ComposantG label = new ComposantG(this, coinNO, texte);
        
        ajouterComposant(label);
        
        // Provoquer le rafraichissement du calque
        //
        repaint();
        
        return true;
    }
    
    /**
     * retirerComposant
     * 
     * @param description
     * @return reussite
     */
    public boolean retirerComposant(HashMap description) {
        
        // Supprimer la description cible de la liste des
        // composants
        //
        boolean status = composants.remove(description);
        
        // Provoquer le rafraichissement du calque
        //
        repaint();
        
        return status;
    }
    
    /**
     * Main
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Dimension coinNO;
        // Dimension gabarit;
        
        // Mettre en place un cadre support
        //
        JFrame f1 = new JFrame();
        
        f1.setTitle("(SWING) Classe CalqueG - V 2.0.0");
        f1.setSize(500, 450);
        f1.setLocation(250, 250);
        
        // Charger un dictionnaire de configuration
        //   	
        HashMap config_1 = (HashMap) Config.load("Panneau_A", "1.0.0");
        
        // Creer un premier calque et l'ajouter au cadre
        // support
        //
        CalqueG calque_1 = new CalqueG(f1, config_1);
        f1.getContentPane().add(calque_1);
        
        // Montrer le cadre support et son calque interne
        //
        f1.setVisible(true);
        
        // Ajouter et dessiner une trame de fond
        //
        calque_1.ajouterTrame(6, 7,
                              new Dimension((int) (Math.cos((30 * Math.PI) / 180) * 100), 100));
        
        // Modifier les bordures de la cellule (3, 3)
        // 
        calque_1.trame.modifierBordure(3, 3, "SUD-EST", 2);
        calque_1.trame.modifierBordure(3, 3, "NORD-EST", 2);
        calque_1.trame.modifierBordure(3, 3, "NORD-OUEST", 2);
        calque_1.trame.modifierBordure(3, 3, "EST", 2);
        calque_1.trame.modifierBordure(3, 3, "OUEST", 2);
        calque_1.trame.modifierBordure(3, 3, "SUD-OUEST", 2);
        
    }
    
    /**
     * enregistrer
     * 
     * @param cheminFichier
     * @param version
     * @return flag de reussite
     */
    public boolean enregistrer(String cheminFichier, String version) {
        
        // Controler la validite des parametres
        //
        if (cheminFichier == null) return false;
        if (version == null) return false;
        
        // Assurer le persistance de tous les composants
        // graphiques du calque
        //
        return Data.store(composants, cheminFichier, version);
    }
    
    /**
     * restaurer
     * 
     * @param cheminFichier
     * @param version
     * @return configuration
     */
    private LinkedList restaurer(String cheminFichier, String version) {
        
        // Controler la validite des parametres
        //
        if (cheminFichier == null) return new LinkedList();
        if (version == null) return new LinkedList();
        
        // Charger tous les composants graphiques d'un
        // calque preexistant
        //
        Object data = Data.load(cheminFichier, version);
        if (data == null) return new LinkedList();
        
        // Restituer le resultat dans le cas nominal
        //
        return (LinkedList) data;
    }
    
    /**
     * charger
     * 
     * @param cheminFichier
     * @param version
     * @return flag de reussite
     */
    public boolean charger(String cheminFichier, String version) {
        
        // Controler la validite des parametres
        //
        if (cheminFichier == null) return false;
        if (version == null) return false;
        
        // Charger tous les composants graphiques d'un
        // calque preexistant
        //
        Object data = Data.load(cheminFichier, version);
        if (data == null) return false;
        
        // Ajouter ces composants dans le calque courant
        //
        ComposantG composant = null;
        
        Iterator i = ((LinkedList) data).iterator();
        
        while (i.hasNext()) {
            
            // Ajouter le composant graphique courant
            //
            composant = (ComposantG) i.next();
            ajouterComposant(composant);
        }
        
        // Restituer le resultat dans le cas nominal
        //
        return true;
    }
    
    /**
     * retirerCellule
     * 
     * @param ligne
     * @param colonne
     * @return flag de reussite
     */
    public boolean retirerCellule(int ligne, int colonne) {
        return trame.retirerCellule(ligne, colonne);
    }
    
    /**
     * modifierBordure
     * 
     * @param ligne
     * @param colonne
     * @param pointCardinal
     * @param epaisseur
     * @return flag de reussite
     */
    public boolean modifierBordure(int ligne, int colonne, String pointCardinal, int epaisseur) {
        return trame.modifierBordure(ligne, colonne, pointCardinal, epaisseur);
    }
    
    /**
     * ajouterTrame
     * 
     * @param nbLignes
     * @param nbColonnes
     * @param gabarit
     * @return flag de reussite
     */
    public boolean ajouterTrame(int nbLignes, int nbColonnes, Dimension gabarit) {
        
        // Controler la validite des parametres
        //
        if (nbLignes <= 0) return false;
        if (nbColonnes <= 0) return false;
        
        // Construire et memoriser la trame de cellules
        //
        trame = new TrameG(this, nbLignes, nbColonnes, gabarit);
        
        // Repeindre le panneau
        //
        repaint();
        return true;
    }
    
    /**
     * retirerTrame
     */
    public void retirerTrame() {
        trame = null;
        repaint();
    }
    
    /**
     * presenceTrame
     * 
     * @return flag de presence
     */
    public boolean presenceTrame() {
        
        if (trame == null) return false;
        return true;
    }
}