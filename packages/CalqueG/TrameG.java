/**
 * IUT de Nice / Departement informatique / Module APO-Java Annee
 * 2009_2010 - Composants graphiques generiques
 * 
 * @edition A
 * 
 *          exigences de la classe principale ChronogrammeG
 * 
 * @version 1.0.0
 * 
 *          la cellule elementaire n'a pas de bordure autre que
 *          le trait du trace
 * 
 * @version 1.1.0
 * 
 *          introduction de la possibilite de designer de facon
 *          dynamique une cellule courante existante
 * 
 * @version 1.2.0
 * 
 *          introduction d'une gestion individualisee des
 *          bordures "NORD", "SUD", "EST" et "OUEST" de chaque
 *          cellule, avec ajout induit d'une classe interne
 *          Bordure
 * 
 * @version 1.3.0
 * 
 *          introduction de la methode "modifierBordure" dans les
 *          classes internes Cellule et Bordure
 * 
 * @version 1.4.0
 * 
 *          introduction de la methode "supprimerBordure" dans
 *          les classes internes Cellule et Bordure
 * 
 * @version 1.5.0
 * 
 *          ajout des accesseurs nombreLignes et nombreColonnes
 * 
 * @version 1.6.0
 * 
 *          ajout de la methode publique "obtenirRectangle"
 * 
 * @edition B
 * 
 *          extension de la trame en cellule hexagonal
 * 
 * @version 2.0.0
 * 
 *          gestion complete de cellule hexagonal
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.HashMap;

import javax.swing.JPanel;

/**
 * Decoupage generique sous jacent d'un calque, independante de
 * toute homothetie dynamique de la taille de ce dernier
 * 
 * @author A. Thuaire, C. Hulin
 * @version 2.0.0
 */
public class TrameG {
    
    private JPanel      hamecon;
    private Cellule[][] matriceCellules;
    private int         nbLignes;
    private int         nbColonnes;
    private Dimension   gabarit;
    private Dimension   celluleCourante;
    
    private Color       couleurTrait = Color.gray;
    private Color       couleurFond  = Color.yellow;
    
    /**
     * Constructeur normal
     * 
     * @param hamecon
     * @param nbLignes
     * @param nbColonnes
     * @param gabarit
     */
    public TrameG(JPanel hamecon, int nbLignes, int nbColonnes, Dimension gabarit) {
        
        // Controler la validite des parametres
        //
        if (hamecon == null) return;
        if (couleurTrait == null) return;
        if (nbLignes <= 0) return;
        if (nbColonnes <= 0) return;
        
        // Memoriser les attributs transmis par parametre
        //
        this.hamecon = hamecon;
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.gabarit = gabarit;
        
        // Creer la matrice des cellules
        //
        matriceCellules = new Cellule[nbLignes][nbColonnes];
        
        // Remplir cette matrice en y ajoutant chacune des
        // cellules
        //
        for (int i = 1; i <= nbLignes; i++)
            for (int j = 1; j <= nbColonnes; j++)
                ajouterCellule(i, j);
    }
    
    /**
     * obtenirCelluleCourante
     * 
     * @return celluleCourante
     */
    public Dimension obtenirCelluleCourante() {
        return celluleCourante;
    }
    
    /**
     * nombreLignes
     * 
     * @return nbLignes
     */
    public int nombreLignes() {
        return nbLignes;
    }
    
    /**
     * nombreColonnes
     * 
     * @return nbColonnes
     */
    public int nombreColonnes() {
        return nbColonnes;
    }
    
    /**
     * ajouterCellule
     * 
     * @param ligne
     * @param colonne
     * @return flag de reussite
     */
    public boolean ajouterCellule(int ligne, int colonne) {
        
        // Controler la validite des parametres
        //
        if (ligne < 1 || ligne > nbLignes) return false;
        
        // Controler la validite des parametres
        //
        if (colonne < 1 || colonne > nbColonnes) return false;
        
        // Controler la validite (absence prealable) de
        // l'ajout
        //
        if (matriceCellules[ligne - 1][colonne - 1] != null) return false;
        
        // Creer et affecter la nouvelle maille
        //
        matriceCellules[ligne - 1][colonne - 1] = new Cellule(ligne, colonne, gabarit);
        
        // Repeindre le panneau support
        //
        hamecon.repaint();
        
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
        
        // Controler la validite des parametres
        //
        if (ligne < 1 || ligne > nbLignes) return false;
        
        // Controler la validite des parametres
        //
        if (colonne < 1 || colonne > nbColonnes) return false;
        
        // Controler la validite (absence prealable) de
        // l'ajout
        //
        if (matriceCellules[ligne - 1][colonne - 1] == null) return false;
        
        // Traiter le cas ou la cible cible est la cellule
        // courante
        //
        if (celluleCourante != null) {
            
            if ((int) celluleCourante.getWidth() == ligne &&
                (int) celluleCourante.getHeight() == colonne) celluleCourante = null;
        }
        
        // Retirer la cellule cible
        //
        matriceCellules[ligne - 1][colonne - 1] = null;
        
        // Repeindre le panneau support
        //
        hamecon.repaint();
        
        return true;
    }
    
    /**
     * dessiner
     * 
     * @param g
     */
    public void dessiner(Graphics g) {
        
        // Dessiner chacune des cellules presentes
        //
        for (int i = 0; i < nbLignes; i++)
            for (int j = 0; j < nbColonnes; j++)
                if (matriceCellules[i][j] != null) matriceCellules[i][j].dessiner(g);
    }
    
    /**
     * obtenirPositionCible
     * 
     * @param x
     * @param y
     * @return cellule
     */
    public Dimension obtenirPositionCible(int x, int y) {
        
        // Parcourir la matrice des cellules
        //
        Cellule celluleCourante = null;
        Dimension designation;
        
        for (int i = 0; i < nbLignes; i++) {
            
            for (int j = 0; j < nbColonnes; j++) {
                
                // Obtenir la cellule courante
                //
                celluleCourante = matriceCellules[i][j];
                
                // Determiner si la maille courante a ete
                // neutralisee
                //
                if (celluleCourante == null) continue;
                
                // Determiner si le point cible (x, y)
                // appartient a la cellule courante
                //
                designation = celluleCourante.obtenirPositionCible(x, y);
                if (designation != null) return designation;
            }
        }
        
        return null;
    }
    
    /**
     * designerCellule
     * 
     * @param ligne
     * @param colonne
     * @return flag de reussite
     */
    public boolean designerCellule(int ligne, int colonne) {
        
        // Controler la validite des parametres
        //
        if (ligne < 1 || ligne > nbLignes) return false;
        if (colonne < 1 || colonne > nbColonnes) return false;
        
        // Controler la presence de la cellule cible dans la
        // trame
        //
        if (matriceCellules[ligne - 1][colonne - 1] == null) {
            celluleCourante = null;
            return false;
        }
        
        // Memoriser les coordonnees de la cellule cible
        //
        celluleCourante = new Dimension(ligne, colonne);
        return true;
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
        
        // Controler la validite des parametres
        //
        if (ligne < 1 || ligne > nbLignes) return false;
        if (colonne < 1 || colonne > nbColonnes) return false;
        if (!pointCardinal.equals("NORD-OUEST") && !pointCardinal.equals("EST") &&
            !pointCardinal.equals("NORD-EST") && !pointCardinal.equals("SUD-OUEST") &&
            !pointCardinal.equals("SUD-EST") && !pointCardinal.equals("OUEST")) return false;
        if (epaisseur < 0) return false;
        
        // Controler la presence de la cellule cible dans la
        // trame
        //
        Cellule celluleCourante = matriceCellules[ligne - 1][colonne - 1];
        if (celluleCourante == null) return false;
        
        // Modifier la bordure cible de la cellule cible
        //
        celluleCourante.modifierBordure(pointCardinal, epaisseur);
        
        // Repeindre le panneau support
        //
        hamecon.repaint();
        
        return true;
    }
    
    /**
     * supprimerBordure
     * 
     * @param ligne
     * @param colonne
     * @param pointCardinal
     * @return flag de reussite
     */
    public boolean supprimerBordure(int ligne, int colonne, String pointCardinal) {
        
        // Controler la validite des parametres
        //
        if (ligne < 1 || ligne > nbLignes) return false;
        if (colonne < 1 || colonne > nbColonnes) return false;
        if (!pointCardinal.equals("NORD-OUEST") && !pointCardinal.equals("EST") &&
            !pointCardinal.equals("NORD-EST") && !pointCardinal.equals("SUD-OUEST") &&
            !pointCardinal.equals("SUD-EST") && !pointCardinal.equals("OUEST")) return false;
        
        // Controler la presence de la cellule cible dans la
        // trame
        //
        Cellule celluleCourante = matriceCellules[ligne - 1][colonne - 1];
        if (celluleCourante == null) return false;
        
        // Supprimer la bordure cible de la cellule cible
        //
        celluleCourante.supprimerBordure(pointCardinal);
        
        // Repeindre le panneau support
        //
        hamecon.repaint();
        
        return true;
    }
    
    /**
     * obtenirRectangle
     * 
     * @param ligne
     * @param colonne
     * @return rectangle
     */
    public Polygon obtenirHexagone(int ligne, int colonne) {
        
        // Controler la validite des parametres
        //
        if (ligne < 1 || ligne > nbLignes) return null;
        if (colonne < 1 || colonne > nbColonnes) return null;
        
        // Controler la presence de la cellule cible dans la
        // trame
        //
        Cellule celluleCourante = matriceCellules[ligne - 1][colonne - 1];
        if (celluleCourante == null) return null;
        
        // Restituer le resultat
        //
        return celluleCourante.obtenirHexagone();
    }
    
    /**
     * Modelise une cellule
     * 
     * @author A. Thuaire, C. Hulin
     * @version 2.0.0
     */
    private class Cellule {
        private int       positionLigne;
        private int       positionColonne;
        private Dimension gabarit;
        private HashMap   bordures;
        
        /**
         * Constructeur normal
         * 
         * @param ligne
         * @param colonne
         * @param gabarit
         */
        public Cellule(int ligne, int colonne, Dimension gabarit) {
            
            // Memoriser les attributs transmis par
            // parametre
            //
            positionLigne = ligne;
            positionColonne = colonne;
            this.gabarit = gabarit;
            
            // Construire les quatre bordures de la cellule
            //
            bordures = new HashMap();
            
            bordures.put("NORD-EST", new Bordure(this, "NORD-EST", 0));
            bordures.put("NORD-OUEST", new Bordure(this, "NORD-OUEST", 0));
            bordures.put("EST", new Bordure(this, "EST", 0));
            bordures.put("SUD-EST", new Bordure(this, "SUD-EST", 0));
            bordures.put("SUD-OUEST", new Bordure(this, "SUD-OUEST", 0));
            bordures.put("OUEST", new Bordure(this, "OUEST", 0));
        }
        
        /**
         * obtenirRectangle
         * 
         * @return rectangle
         */
        private Polygon obtenirHexagone() {
            
            // Determiner les dimensions de la cellule
            // elementaire
            //
            int largeurCellule = (int) gabarit.getWidth();
            int hauteurCellule = (int) gabarit.getHeight();
            
            int[] abscisses = new int[6];
            int[] ordonnees = new int[6];
            
            if (positionLigne % 2 != 0) {
                
                // Determiner les abscisses des sommets de
                // la cellule cible
                //
                abscisses[0] = (positionColonne - 1) * largeurCellule;
                abscisses[1] = abscisses[0];
                abscisses[2] = abscisses[0] + largeurCellule / 2;
                abscisses[3] = abscisses[0] + largeurCellule;
                abscisses[4] = abscisses[0] + largeurCellule;
                abscisses[5] = abscisses[0] + largeurCellule / 2;
                
                // Determiner les ordonnees des sommets de
                // la cellule cible
                //                
                ordonnees[0] =
                               ((positionLigne - 1) * (hauteurCellule - hauteurCellule / 4)) +
                                       hauteurCellule / 4;
                ordonnees[1] = ordonnees[0] + (hauteurCellule / 2);
                ordonnees[2] = ordonnees[0] + (hauteurCellule / 2) + (hauteurCellule / 4);
                ordonnees[3] = ordonnees[0] + (hauteurCellule / 2);
                ordonnees[4] = ordonnees[0];
                ordonnees[5] = ordonnees[0] - (hauteurCellule / 4);
                
            }
            else {
                
                // Determiner les abscisses des sommets de
                // la cellule cible
                //                
                abscisses[0] = (positionColonne - 1) * largeurCellule + (largeurCellule / 2);
                abscisses[1] = abscisses[0];
                abscisses[2] = abscisses[0] + largeurCellule / 2;
                abscisses[3] = abscisses[0] + largeurCellule;
                abscisses[4] = abscisses[0] + largeurCellule;
                abscisses[5] = abscisses[0] + largeurCellule / 2;
                
                // Determiner les ordonnees des sommets de
                // la cellule cible
                //                
                ordonnees[0] =
                               ((positionLigne - 1) * (hauteurCellule - hauteurCellule / 4)) +
                                       hauteurCellule / 4;
                ordonnees[1] = ordonnees[0] + (hauteurCellule / 2);
                ordonnees[2] = ordonnees[0] + (hauteurCellule / 2) + (hauteurCellule / 4);
                ordonnees[3] = ordonnees[0] + (hauteurCellule / 2);
                ordonnees[4] = ordonnees[0];
                ordonnees[5] = ordonnees[0] - (hauteurCellule / 4);
            }
            
            // Construire le polygone associe a la cellule
            // cible
            //
            Polygon polygone = new Polygon(abscisses, ordonnees, 6);
            
            // Restituer le resultat
            //
            return polygone;
        }
        
        /**
         * dessiner
         * 
         * @param g
         */
        public void dessiner(Graphics g) {
            Color couleurInitiale = null;
            Bordure bordure = null;
            Polygon hexagone = null;
            
            // Sauvegarder la couleur courante de trace
            //
            couleurInitiale = g.getColor();
            
            // Traiter la bordure NORD-EST
            //
            bordure = (Bordure) bordures.get("NORD-EST");
            if (bordure != null) {
                
                hexagone = ((Bordure) bordure).obtenirHexagone();
                g.drawPolygon(hexagone);
                
                if (couleurFond != null) {
                    g.setColor(couleurFond);
                    g.fillPolygon(hexagone.xpoints, hexagone.ypoints, 4);
                }
            }
            
            // Traiter la bordure NORD-OUEST
            //
            bordure = (Bordure) bordures.get("NORD-OUEST");
            if (bordure != null) {
                
                hexagone = ((Bordure) bordure).obtenirHexagone();
                g.drawPolygon(hexagone);
                
                if (couleurFond != null) {
                    g.setColor(couleurFond);
                    g.fillPolygon(hexagone.xpoints, hexagone.ypoints, 4);
                }
            }
            
            // Traiter la bordure EST
            //
            bordure = (Bordure) bordures.get("EST");
            if (bordure != null) {
                
                hexagone = ((Bordure) bordure).obtenirHexagone();
                g.drawPolygon(hexagone);
                
                if (couleurFond != null) {
                    g.setColor(couleurFond);
                    g.fillPolygon(hexagone.xpoints, hexagone.ypoints, 4);
                }
            }
            
            // Traiter la bordure SUD-OUEST
            //
            bordure = (Bordure) bordures.get("SUD-OUEST");
            if (bordure != null) {
                
                hexagone = ((Bordure) bordure).obtenirHexagone();
                g.drawPolygon(hexagone);
                
                if (couleurFond != null) {
                    g.setColor(couleurFond);
                    g.fillPolygon(hexagone.xpoints, hexagone.ypoints, 4);
                }
            }
            
            // Traiter la bordure SUD-EST
            //
            bordure = (Bordure) bordures.get("SUD-EST");
            if (bordure != null) {
                
                hexagone = ((Bordure) bordure).obtenirHexagone();
                g.drawPolygon(hexagone);
                
                if (couleurFond != null) {
                    g.setColor(couleurFond);
                    g.fillPolygon(hexagone.xpoints, hexagone.ypoints, 4);
                }
            }
            
            // Traiter la bordure OUEST
            //
            bordure = (Bordure) bordures.get("OUEST");
            if (bordure != null) {
                
                hexagone = ((Bordure) bordure).obtenirHexagone();
                g.drawPolygon(hexagone);
                
                if (couleurFond != null) {
                    g.setColor(couleurFond);
                    g.fillPolygon(hexagone.xpoints, hexagone.ypoints, 4);
                }
            }
            
            // Restaurer la couleur courante de trace
            // 
            g.setColor(couleurInitiale);
        }
        
        /**
         * obtenirPositionCible
         * 
         * @param x
         * @param y
         * @return cellule
         */
        public Dimension obtenirPositionCible(int x, int y) {
            
            // Construire le polygone attache a la cellule
            // cible
            //
            Polygon polygone = obtenirHexagone();
            
            // Determiner si le point cible (x, y)
            // appartient a cette cellule
            //	
            if (polygone.contains(x, y)) return new Dimension(positionLigne, positionColonne);
            
            return null;
        }
        
        /**
         * modifierBordure
         * 
         * @param pointCardinal
         * @param epaisseur
         */
        public void modifierBordure(String pointCardinal, int epaisseur) {
            
            bordures.put(pointCardinal, new Bordure(this, pointCardinal, epaisseur));
        }
        
        /**
         * supprimerBordure
         * 
         * @param pointCardinal
         */
        public void supprimerBordure(String pointCardinal) {
            
            bordures.remove(pointCardinal);
        }
    }
    
    /**
     * Modelise les bordure d'une cellule
     * 
     * @author C. Hulin, A. Thuaire
     * @version 2.0.0
     */
    private class Bordure {
        private Polygon hexagone;
        
        /**
         * Constructeur normal
         * 
         * @param celluleSupport
         * @param pointCardinal
         * @param epaisseur
         */
        public Bordure(Cellule celluleSupport, String pointCardinal, int epaisseur) {
            
            int[] abscisses = new int[4];
            int[] ordonnees = new int[4];
            
            // Obtenir l'enveloppe de la cellule support
            //
            Polygon enveloppe = celluleSupport.obtenirHexagone();
            
            // Traiter le cas de la bordure NORD
            //
            if (pointCardinal.equals("SUD-OUEST") && epaisseur >= 0) {
                
                abscisses[0] = enveloppe.xpoints[1];
                abscisses[1] = enveloppe.xpoints[2];
                abscisses[2] = abscisses[1];
                abscisses[3] = abscisses[0];
                
                ordonnees[0] = enveloppe.ypoints[1];
                ordonnees[1] = enveloppe.ypoints[2];
                ordonnees[2] = ordonnees[1] - epaisseur * 3 / 2;
                ordonnees[3] = ordonnees[0] - epaisseur * 3 / 2;
                
                hexagone = new Polygon(abscisses, ordonnees, 4);
            }
            
            // Traiter le cas de la bordure NORD-EST
            //
            if (pointCardinal.equals("SUD-EST") && epaisseur >= 0) {
                
                abscisses[0] = enveloppe.xpoints[2];
                abscisses[1] = enveloppe.xpoints[3];
                abscisses[2] = abscisses[1];
                abscisses[3] = abscisses[0];
                
                ordonnees[0] = enveloppe.ypoints[2];
                ordonnees[1] = enveloppe.ypoints[3];
                ordonnees[2] = ordonnees[1] - epaisseur * 3 / 2;
                ordonnees[3] = ordonnees[0] - epaisseur * 3 / 2;
                
                hexagone = new Polygon(abscisses, ordonnees, 4);
            }
            
            // Traiter le cas de la bordure EST
            //
            if (pointCardinal.equals("EST") && epaisseur >= 0) {
                
                abscisses[0] = enveloppe.xpoints[3];
                abscisses[1] = enveloppe.xpoints[4];
                abscisses[2] = abscisses[1] - epaisseur;
                abscisses[3] = abscisses[0] - epaisseur;
                
                ordonnees[0] = enveloppe.ypoints[3];
                ordonnees[1] = enveloppe.ypoints[4];
                ordonnees[2] = ordonnees[1];
                ordonnees[3] = ordonnees[0];
                
                hexagone = new Polygon(abscisses, ordonnees, 4);
            }
            
            // Traiter le cas de la bordure SUD-EST
            //
            if (pointCardinal.equals("NORD-EST") && epaisseur >= 0) {
                
                abscisses[0] = enveloppe.xpoints[4];
                abscisses[1] = enveloppe.xpoints[5];
                abscisses[2] = abscisses[1];
                abscisses[3] = abscisses[0];
                
                ordonnees[0] = enveloppe.ypoints[4];
                ordonnees[1] = enveloppe.ypoints[5];
                ordonnees[2] = ordonnees[1] + epaisseur * 3 / 2;
                ordonnees[3] = ordonnees[0] + epaisseur * 3 / 2;
                
                hexagone = new Polygon(abscisses, ordonnees, 4);
            }
            
            // Traiter le cas de la bordure SUD-OUEST
            //
            if (pointCardinal.equals("NORD-OUEST") && epaisseur >= 0) {
                
                abscisses[0] = enveloppe.xpoints[5];
                abscisses[1] = enveloppe.xpoints[0];
                abscisses[2] = abscisses[1];
                abscisses[3] = abscisses[0];
                
                ordonnees[0] = enveloppe.ypoints[5];
                ordonnees[1] = enveloppe.ypoints[0];
                ordonnees[2] = ordonnees[1] + epaisseur * 3 / 2;
                ordonnees[3] = ordonnees[0] + epaisseur * 3 / 2;
                
                hexagone = new Polygon(abscisses, ordonnees, 4);
            }
            
            // Traiter le cas de la bordure OUEST
            //
            if (pointCardinal.equals("OUEST") && epaisseur >= 0) {
                
                abscisses[0] = enveloppe.xpoints[0];
                abscisses[1] = enveloppe.xpoints[1];
                abscisses[2] = abscisses[1] + epaisseur;
                abscisses[3] = abscisses[0] + epaisseur;
                
                ordonnees[0] = enveloppe.ypoints[0];
                ordonnees[1] = enveloppe.ypoints[1];
                ordonnees[2] = ordonnees[1];
                ordonnees[3] = ordonnees[0];
                
                hexagone = new Polygon(abscisses, ordonnees, 4);
            }
        }
        
        /**
         * obtenirRectangle
         * 
         * @return rectangle
         */
        private Polygon obtenirHexagone() {
            return hexagone;
        }
    }
}