import java.awt.Point;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Ile {
    
    /**
     * Identification de l'ile
     */
    private String m_nom;
    
    /**
     * Description topologique
     */
    private Polygon m_lieux;
    
    /**
     * Etat de la meteo dans la zone qui entoure l'ile
     */
    private int m_etatMeteo;
    
    /**
     * Effectifs des troupes debarquees
     */
    private HashMap m_effectifs; // <string, list <int> >
    // m_effectifs;
    
    /**
     * Localisations sur l'ile des troupes debarquees
     */
    private HashMap m_positions;// <string, list <Point> >
    
    // m_positions;
    
    /**
     * Constructeur par defaut
     */
    public Ile() {
        
        // Valuer le nom
        //   
        m_nom = "-";
        
        // Initialiser la meteo
        //
        m_etatMeteo = -1; // INF_ETATS_METEO;
        
    }
    
    /**
     * Premier constructeur normal
     * 
     * @param nom
     * @param lieux
     */
    public Ile(String nom, Polygon lieux) {
        
        // Valuer le nom
        //
        m_nom = nom;
        
        // Affecter la description topologique
        //
        m_lieux = lieux;
        
        // Initialiser la meteo
        //
        m_etatMeteo = -1; // INF_ETATS_METEO;
    }
    
    // --- Methode toString pour les tests unitaires
    //
    public String toString() {
        String resultat;
        
        // Controler le cas particulier de l'element neutre
        //
        if (neutre()) resultat = "---";
        else resultat = "[ " + m_nom + " / " + m_lieux + " ]";
        
        return resultat;
    }
    
    public boolean neutre() {
        return m_nom.equals("-");
    }
    
    public boolean ok() {
        return m_lieux != null;
    }
    
    public boolean nok() {
        return !ok();
    }
    
    // ------ Accesseurs de consultation
    //
    public String obtenirNom() {
        return m_nom;
    }
    
    public Polygon obtenirLieux() {
        return m_lieux;
    }
    
    public int obtenirMeteo() {
        return m_etatMeteo;
    }
    
    public LinkedList obtenirPositions(String flotte) {
        return (LinkedList) m_positions.get(flotte);
    }
    
    /**
     * Obtenir le total des effectifs d'une flotte
     * 
     * @param flotte
     * @return
     */
    public int effectifs(String flotte) {
        
        // Traiter le cas d'absence eventuelle de la flotte
        // sur l'ile
        //
        if (!m_effectifs.containsKey(flotte)) return 0;
        
        // Parcourir la liste des effectifs de la flotte
        // cible
        //
        int somme = 0;
        Iterator i = ((LinkedList) (m_effectifs.get(flotte))).iterator();
        
        while (i.hasNext()) {
            
            // Cumuler tous les effectifs de la flotte cible
            //
            somme += ((Integer) i.next()).intValue();
        }
        
        // Restituer le resultat
        //
        return somme;
    }
    
    /**
     * Obtenir les effectifs d'une flotte stationnee sur une
     * position cible
     * 
     * @param flotte
     * @param pointCible
     * @return
     */
    public int effectifs(String flotte, Point pointCible) {
        
        // Traiter le cas d'absence eventuelle de la flotte
        // sur l'ile
        //
        if (!m_positions.containsKey(flotte)) return 0;
        if (!m_effectifs.containsKey(flotte)) return 0;
        
        // Parcourir en parallele la liste des positions de
        // la flotte cible et la liste des effectifs
        //
        Iterator i = ((LinkedList) (m_positions.get(flotte))).iterator();
        Iterator j = ((LinkedList) (m_effectifs.get(flotte))).iterator();
        
        int somme = 0;
        double ecartPosition;
        
        while (i.hasNext()) {
            
            // Obtenir la position courante de la flotte
            // cible
            //
            Point position = (Point) i.next();
            
            // Controler la distance entre la position cible
            // et la position courante
            //
            ecartPosition = position.distance(pointCible);
            if (ecartPosition < 0.1) somme += ((Integer) j.next()).intValue();
        }
        
        // Restituer le resultat
        //
        return somme;
    }
}
