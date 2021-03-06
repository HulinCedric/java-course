/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Composants graphiques generiques
 * 
 * @edition A
 * 
 *          exigences de la classe principale ChronogrammeG
 * 
 * @version 1.0.0
 * 
 *          externalisation des objets graphiques de la
 *          classe CalqueG, a l'exception de la trame
 * 
 * @version 1.1.0
 * 
 *          introduction d'un nouvel attribut "couleurTrait"
 *          et d'un accesseur associe "fixerCouleurTrait"
 *          pour individualiser la couleur de trait des
 *          objets
 * 
 * @version 1.2.0
 * 
 *          introduction d'un nouvel attribut "masque" et de
 *          deux accesseurs associes "masquer" et
 *          "demasquer" pour pouvoir masquer/demasquer
 *          dynamiquement chaque objet graphique
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.io.Serializable;

import javax.swing.JPanel;

/**
 * Objets graphiques generiques
 * 
 * @author A. Thuaire
 * @version 1.2.0
 */
public class ComposantG implements Serializable {
    private JPanel hamecon;
    private Dimension coinNO;
    private Dimension gabarit;
    private Object source;
    private Color couleurTrait;
    private Boolean masque = new Boolean(false);
    
    /**
     * Cas d'un simple point
     * 
     * @param calqueSupport
     * @param position
     */
    public ComposantG(JPanel calqueSupport, Dimension position) {
        
        hamecon = calqueSupport;
        coinNO = position;
    }
    
    /**
     * Cas d'un segment
     * 
     * @param calqueSupport
     * @param origine
     * @param extremite
     */
    public ComposantG(JPanel calqueSupport, Dimension origine, Dimension extremite) {
        hamecon = calqueSupport;
        coinNO = origine;
        source = extremite;
    }
    
    /**
     * Cas d'un rectangle
     * 
     * @param calqueSupport
     * @param origine
     * @param longueur
     * @param largeur
     */
    public ComposantG(JPanel calqueSupport, Dimension origine, int longueur, int largeur) {
        
        // Controler la validite des deux derniers
        // parametres
        //
        if (longueur < 0) longueur = 0;
        if (largeur < 0) largeur = 0;
        
        hamecon = calqueSupport;
        coinNO = origine;
        this.gabarit = new Dimension(longueur, largeur);
    }
    
    /**
     * Cas d'un label textuel
     * 
     * @param calqueSupport
     * @param origine
     * @param texte
     */
    public ComposantG(JPanel calqueSupport, Dimension origine, String texte) {
        
        hamecon = calqueSupport;
        coinNO = origine;
        source = texte;
    }
    
    /**
     * Cas d'une image
     * 
     * @param calqueSupport
     * @param origine
     * @param gabarit
     * @param image
     */
    public ComposantG(JPanel calqueSupport, Dimension origine, Dimension gabarit, String image) {
        hamecon = calqueSupport;
        coinNO = origine;
        this.gabarit = gabarit;
        source = image;
    }
    
    /**
     * Cas d'un polygone
     * 
     * @param calqueSupport
     * @param polygone
     */
    public ComposantG(JPanel calqueSupport, Polygon polygone) {
        
        hamecon = calqueSupport;
        source = polygone;
    }
    
    /**
     * Cas d'une ellipse ou d'un cercle
     * 
     * @param calqueSupport
     * @param origine
     * @param rayon
     */
    public ComposantG(JPanel calqueSupport, Dimension origine, int rayon) {
        
        hamecon = calqueSupport;
        coinNO = origine;
        source = new Integer(rayon);
    }
    
    /**
     * fixerCouleurTrait
     * 
     * @param couleurTrait
     */
    public void fixerCouleurTrait(Color couleurTrait) {
        
        this.couleurTrait = couleurTrait;
    }
    
    /**
     * masquer
     */
    public void masquer() {
        masque = new Boolean(true);
    }
    
    /**
     * demasquer
     */
    public void demasquer() {
        masque = new Boolean(false);
    }
    
    /**
     * dessiner
     * 
     * @param g
     */
    public void dessiner(Graphics g) {
        String typeSource = null;
        
        // - Traiter particulier le cas particulier d'un
        // simple point
        //
        if (source == null && gabarit == null) {
            dessinerPoint(g);
            return;
        }
        
        // - Traiter particulier le cas particulier d'un
        // rectangle
        //
        if (source == null && gabarit != null) {
            dessinerRectangle(g);
            return;
        }
        
        // Obtenir le type de la source du composant a
        // dessiner
        //
        if (source != null) typeSource = (String) source.getClass().getName();
        
        // - Traiter le cas d'un segment
        //
        if (gabarit == null && typeSource.equals("java.awt.Dimension")) {
            dessinerSegment(g);
            return;
        }
        
        // - Traiter le cas d'un label textuel
        //
        if (gabarit == null && typeSource.equals("java.lang.String")) {
            dessinerLabel(g);
            return;
        }
        
        // - Traiter le cas d'une image
        //
        if (gabarit != null && typeSource.equals("java.lang.String")) {
            dessinerImage(g);
            return;
        }
        
        // - Traiter le cas d'un polygone
        //
        if (typeSource.equals("java.awt.Polygon")) {
            dessinerPolygone(g);
            return;
        }
    }
    
    /**
     * dessinerPoint
     * 
     * @param g
     */
    private void dessinerPoint(Graphics g) {
        Color couleurInitiale = null;
        
        // Controler l'etat courant du masque
        //
        if (masque.booleanValue()) return;
        
        // Sauvegarder la couleur initiale de trait
        //
        couleurInitiale = g.getColor();
        
        // Obtenir la couleur du segment a dessiner
        //
        Color couleurCourante = couleurTrait;
        
        // Modifier la couleur courante de dessin dans le
        // calque
        //
        if (couleurCourante != null) g.setColor(couleurCourante);
        
        // Obtenir les coordonnees du point cible
        //
        int x1 = (int) coinNO.getWidth();
        int y1 = (int) coinNO.getHeight();
        
        // Dessiner le point cible
        //
        g.drawLine(x1, y1, x1, y1);
        
        // Restaurer la couleur initiale de dessin dans le
        // calque
        //
        if (couleurCourante != null) g.setColor(couleurInitiale);
    }
    
    /**
     * dessinerSegment
     * 
     * @param g
     */
    private void dessinerSegment(Graphics g) {
        Color couleurInitiale = null;
        
        // Controler l'etat courant du masque
        //
        if (masque.booleanValue()) return;
        
        // Sauvegarder la couleur initiale de trait
        //
        couleurInitiale = g.getColor();
        
        // Obtenir la couleur du segment a dessiner
        //
        Color couleurCourante = couleurTrait;
        
        // Modifier la couleur courante de dessin dans le
        // calque
        //
        if (couleurCourante != null) g.setColor(couleurCourante);
        
        // Obtenir les coordonnees du coin Nord Ouest de la
        // zone de dessin
        //
        int x1 = (int) coinNO.getWidth();
        int y1 = (int) coinNO.getHeight();
        
        // Obtenir les coordonnees du point extremite
        //
        int x2 = (int) ((Dimension) source).getWidth();
        int y2 = (int) ((Dimension) source).getHeight();
        
        // Dessiner le segment cible
        //
        g.drawLine(x1, y1, x2, y2);
        
        // Restaurer la couleur initiale de dessin dans le
        // calque
        //
        if (couleurCourante != null) g.setColor(couleurInitiale);
    }
    
    /**
     * dessinerRectangle
     * 
     * @param g
     */
    private void dessinerRectangle(Graphics g) {
        Color couleurInitiale = null;
        
        // Controler l'etat courant du masque
        //
        if (masque.booleanValue()) return;
        
        // Sauvegarder la couleur initiale de trait
        //
        couleurInitiale = g.getColor();
        
        // Obtenir la couleur du segment a dessiner
        //
        Color couleurCourante = couleurTrait;
        
        // Modifier la couleur courante de dessin dans le
        // calque
        //
        if (couleurCourante != null) g.setColor(couleurCourante);
        
        // Obtenir les coordonnees du coin Nord Ouest de la
        // zone de dessin
        //
        int x1 = (int) coinNO.getWidth();
        int y1 = (int) coinNO.getHeight();
        
        // Obtenir la longueur et la largeur
        //
        int longueur = (int) gabarit.getWidth();
        int largeur = (int) gabarit.getHeight();
        
        // Dessiner le rectangle cible
        //
        g.drawLine(x1, y1, x1 + longueur, y1);
        g.drawLine(x1 + longueur, y1, x1 + longueur, y1 + largeur);
        g.drawLine(x1 + longueur, y1 + largeur, x1, y1 + largeur);
        g.drawLine(x1, y1 + largeur, x1, y1);
        
        // Restaurer la couleur initiale de dessin dans le
        // calque
        //
        if (couleurCourante != null) g.setColor(couleurInitiale);
    }
    
    /**
     * dessinerLabel
     * 
     * @param g
     */
    private void dessinerLabel(Graphics g) {
        Color couleurInitiale = null;
        
        // Controler l'etat courant du masque
        //
        if (masque.booleanValue()) return;
        
        // Sauvegarder la couleur initiale de trait
        //
        couleurInitiale = g.getColor();
        
        // Obtenir la couleur du segment a dessiner
        //
        Color couleurCourante = couleurTrait;
        
        // Modifier la couleur courante de dessin dans le
        // calque
        //
        if (couleurCourante != null) g.setColor(couleurCourante);
        
        // Obtenir le texte du label
        //
        String label = (String) source;
        
        // Obtenir les coordonnees du coin Nord Ouest de la
        // zone de dessin
        //
        int x = (int) coinNO.getWidth();
        int y = (int) coinNO.getHeight();
        
        // Dessiner le label depuis l'origine de la zone de
        // dessin
        //   
        g.drawString(label, x, y);
        
        // Restaurer la couleur initiale de dessin dans le
        // calque
        //
        if (couleurCourante != null) g.setColor(couleurInitiale);
    }
    
    /**
     * dessinerPolygone
     * 
     * @param g
     */
    private void dessinerPolygone(Graphics g) {
        Color couleurInitiale = null;
        
        // Controler l'etat courant du masque
        //
        if (masque.booleanValue()) return;
        
        // Sauvegarder la couleur initiale de trait
        //
        couleurInitiale = g.getColor();
        
        // Obtenir la couleur du segment a dessiner
        //
        Color couleurCourante = couleurTrait;
        
        // Modifier la couleur courante de dessin dans le
        // calque
        //
        if (couleurCourante != null) g.setColor(couleurCourante);
        
        // Obtenir le polygone a dessiner
        //
        Polygon polygone = (Polygon) source;
        
        // Obtenir le sommet origine du polygone
        //
        int x0 = polygone.xpoints[0];
        int y0 = polygone.ypoints[0];
        
        // Parcourir tous les sommets du polygone
        //
        int x1, y1, x2, y2;
        
        x1 = x0;
        y1 = y0;
        for (int i = 1; i < polygone.npoints; i++) {
            
            // Obtenir le sommet courant
            //
            x2 = polygone.xpoints[i];
            y2 = polygone.ypoints[i];
            
            // Dessiner l'arete courante
            //
            g.drawLine(x1, y1, x2, y2);
            
            // Modifier le sommet origine pour l'arete
            // suivante
            //
            x1 = x2;
            y1 = y2;
        }
        
        // Dessiner la derniere arete
        //
        g.drawLine(x1, y1, x0, y0);
        
        // Restaurer la couleur initiale de dessin dans le
        // calque
        //
        if (couleurCourante != null) g.setColor(couleurInitiale);
    }
    
    /**
     * dessinerImage
     * 
     * @param g
     */
    private void dessinerImage(Graphics g) {
        
        // Controler l'etat courant du masque
        //
        if (masque.booleanValue()) return;
        
        // Obtenir la designation de l'image a visualiser
        //
        String chemin = (String) source;
        
        // Obtenir les coordonnees du coin Nord Ouest de la
        // zone de dessin
        //
        int x0 = (int) coinNO.getWidth();
        int y0 = (int) coinNO.getHeight();
        
        // Obtenir les coordonnees du point extremite du
        // rectangle de dessin
        //
        int largeur = (int) gabarit.getWidth();
        int hauteur = (int) gabarit.getHeight();
        
        // Charger l'image cible
        //
        Image image = chargerImage(chemin);
        if (image == null) return;
        
        // Dessiner l'image sur le calque
        //
        g.drawImage(image, x0, y0, largeur, hauteur, null);
    }
    
    /**
     * chargerImage
     * 
     * @param nomImage
     * @return image charger
     */
    private Image chargerImage(String nomImage) {
        Image resultat;
        
        // Charger l'image cible (format jpeg)
        //
        resultat = Toolkit.getDefaultToolkit().getImage(nomImage);
        
        // Construire un media tracker pour controler le
        // chargement de l'image
        //
        MediaTracker mt = new MediaTracker(hamecon);
        
        // Attendre la fin du chargement effectif de l'image
        //
        mt.addImage(resultat, 0);
        try {
            mt.waitForAll();
        }
        catch (Exception e) {}
        
        // Restituer le resultat
        //
        return resultat;
    }
}
