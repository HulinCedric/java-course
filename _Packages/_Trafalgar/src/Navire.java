import java.awt.Point;
import java.util.LinkedList;

public class Navire {
    
    private String nom; // Identification du bateau
    private String drapeau; // Designation de la flotte
    private Point position; // Position courante du navire
    private Vecteur vitesse; // Vitesse courante du navire
    private double vitesseMax; // Vitesse maximum du navire
    private int nbSoldats; // Effectif des troupes de debarquement
    
    private int typeNavire; // Type de navire
    private Coque coque; // Coque du navire
    private int poidsNavire; // Poids du navire
    private int etatMeteo; // Etat courant de la meteo autour du navire
    
    private LinkedList pArmes; // Liste des armes du navire
    private LinkedList pRadars; // Liste des radars du navire
    
    /**
     * deplacer
     */
    public void deplacer(Ile[] iles) {
        Vecteur V0 = new Vecteur(0, 0);
        
        // Controler la validite de l'objet support
        //
        if (nok()) return;
        
        // Controler la validite du parametre
        //
        if (iles == null) return;
        
        // Controler l'etat du navire
        //
        if (obtenirEtatCoque() == _Trafalgar.COULE) return;
        
        // Obtenir la position courante A du navire
        //
        Point A = obtenirPositionNavire();
        if (A == null) return;
        
        // Obtenir la vitesse courante du navire
        //
        Vecteur V = obtenirVitesseNavire();
        if (V == null) return;
        
        // Controler le cas particulier d'un navire echoue
        //
        if (V.equals(V0)) return;
        
        // Determiner la trajectoire du navire
        //
        DemiDroite trajectoire = new DemiDroite(A, V);
        
        // Calculer l'image B par une translation du vecteur
        // vitesse
        //
        Point B = A.translation(V);
        if (B == null) return;
        
        // Controler un echouage eventuel sur une ile de la
        // zone de deplacement, en analysant l'intersection
        // eventuelle de la trajectoire du navire avec
        // chacune d'elles
        //
        Point I;
        double AB, AI;
        
        for (int i = 0; i < iles.length; i++) {
            
            // Obtenir le triangle de l'ile courante
            // 
            Polygone T = iles[i].obtenirLieux();
            if (T == null) return;
            
            // Determiner le point d'intersection eventuel
            // de la trajectoire avec le triangle de l'ile
            // courante
            //
            I = T.intersection(trajectoire);
            
            // Traiter l'absence d'intersection de la
            // trajectoire avec le triangle de l'ile
            // courante
            //
            if (I == null) continue;
            
            // Traiter le cas d'une intersection, avec
            // l'image B dans le triangle de l'ile courante
            // (echouage)
            //
            if (T.appartient(B)) {
                position = I;
                vitesse = V0;
                return;
            }
            
            // Traiter le cas general d'une image B en deca
            // de l'intersection
            //
            AB = A.distance(B);
            AI = A.distance(I);
            if (AB < AI) continue;
            
            // Traiter le cas particulier d'une image B au
            // dela de l'intersection (echouage)
            //
            position = I;
            vitesse = V0;
            coque.etat(_Trafalgar.COULE);
            return;
        }
        
        // Traiter le cas general (absence d'echouage)
        //
        position = B;
    }
}