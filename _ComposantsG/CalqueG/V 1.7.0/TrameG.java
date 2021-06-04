//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants graphiques generiques
//
// Classe TrameG - Decoupage generique sous jacent d'un calque, independante de 
//                 toute homothetie dynamique de la taille de ce dernier
//
// Edition A    : exigences de la classe principale ChronogrammeG
//
//    + Version 1.0.0	: la cellule elementaire n'a pas de bordure autre que
//                        le trait du trace
//    
//    + Version 1.1.0   : introduction de la possibilite de designer de facon 
//                        dynamique une cellule courante existante
//    
//    + Version 1.2.0   : introduction d'une gestion individualisee des bordures 
//                        "NORD", "SUD", "EST" et "OUEST" de chaque cellule,
//                        avec ajout induit d'une classe interne Bordure
//    
//    + Version 1.3.0   : introduction de la methode "modifierBordure" dans les
//                        classes internes Cellule et Bordure 
//    
//    + Version 1.4.0   : introduction de la methode "supprimerBordure" dans les
//                        classes internes Cellule et Bordure 
//    
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import javax.swing.*;
  
public class TrameG {
   
private JPanel      hamecon;
private Cellule[][] matriceCellules;
private int         nbLignes;
private int         nbColonnes;
private Dimension   gabarit;
private Dimension   celluleCourante;

private Color       couleurTrait= Color.gray;
private Color       couleurFond= Color.gray;
   
// ------                                               *** Constructeur normal
   
   public TrameG (JPanel hamecon, int nbLignes, 
                                  int nbColonnes,
                                  Dimension gabarit) {
                                   	
      // Controler la validite des parametres
      //
      if (hamecon == null) return;
      if (couleurTrait == null) return;
      if (nbLignes <= 0)   return;
      if (nbColonnes <= 0) return;
                                     	
      // Memoriser les attributs transmis par parametre
      //
      this.hamecon     = hamecon;
      this.nbLignes    = nbLignes;
      this.nbColonnes  = nbColonnes;
      this.gabarit     = gabarit;
         
      // Creer la matrice des cellules
      //
      matriceCellules= new Cellule[nbLignes][nbColonnes];
      
      // Remplir cette matrice en y ajoutant chacune des cellules
      //
      for (int i= 1; i<=nbLignes; i++) 
         for (int j= 1; j<=nbColonnes; j++) ajouterCellule(i, j); 
   }
   
// ------                                       *** Accesseurs de consultation
   
   public Dimension obtenirCelluleCourante () {return celluleCourante;}
      
// ------                                           *** Methode ajouterCellule
   
   public boolean ajouterCellule(int ligne, int colonne) {
      	
      // Controler la validite des parametres
      //
      if (ligne < 1 || ligne > nbLignes) return false;
      
      // Controler la validite des parametres
      //
      if (colonne < 1 || colonne > nbColonnes) return false;
      
      // Controler la validite (absence prealable) de l'ajout 
      //
      if (matriceCellules[ligne-1][colonne-1] != null) return false;
         
      // Creer et affecter la nouvelle maille
      //
      matriceCellules[ligne-1][colonne-1]= new Cellule(ligne, colonne, gabarit);
      
      // Repeindre le panneau support
      //
      hamecon.repaint();
      
      return true;
   }
      
// ------                                           *** Methode retirerCellule
   
   public boolean retirerCellule(int ligne, int colonne) {
      	
      // Controler la validite des parametres
      //
      if (ligne < 1 || ligne > nbLignes) return false;
      
      // Controler la validite des parametres
      //
      if (colonne < 1 || colonne > nbColonnes) return false;
      
      // Controler la validite (absence prealable) de l'ajout 
      //
      if (matriceCellules[ligne-1][colonne-1] == null) return false;
      
      // Traiter le cas ou la cible cible est la cellule courante
      //
      if (celluleCourante != null) {
 
         if ((int)celluleCourante.getWidth() == ligne && 
             (int)celluleCourante.getHeight() == colonne) celluleCourante= null;
      }
      	
      // Retirer la cellule cible
      //
      matriceCellules[ligne-1][colonne-1]= null;
      
      // Repeindre le panneau support
      //
      hamecon.repaint();
      
      return true;
   }
   
// ------                                                  *** Methode dessiner
   
   public void dessiner (Graphics g) {
   	
      // Dessiner chacune des cellules presentes
      //
      for (int i= 0; i<nbLignes; i++) 
         for (int j= 0; j<nbColonnes; j++) 
            if (matriceCellules[i][j] != null) 
               matriceCellules[i][j].dessiner(g);
   }
   
// ------                                     *** Methode obtenirPositionCible
   
   public Dimension obtenirPositionCible (int x, int y) {
   	
      // Parcourir la matrice des cellules
      //
      Cellule celluleCourante= null;
      Dimension designation;
      
      for (int i= 0; i<nbLignes; i++) {
   
         for (int j= 0; j<nbColonnes; j++) {
         	
            // Obtenir la cellule courante
            //
            celluleCourante= matriceCellules[i][j];
            
            // Determiner si la maille courante a ete neutralisee
            //
            if (celluleCourante == null) continue;
            
            // Determiner si le point cible (x, y) appartient 
            // a la cellule courante
            //
            designation= celluleCourante.obtenirPositionCible(x, y);
            if (designation != null) return designation;
         } 
      }
      
      return null;     
   }
   
// ------                                          *** Methode designerCellule
   
   public boolean designerCellule (int ligne, int colonne) {
   	
   	  // Controler la validite des parametres
      //
      if (ligne < 1 || ligne > nbLignes) return false;
      if (colonne < 1 || colonne > nbColonnes) return false;
   	
   	  // Controler la presence de la cellule cible dans la trame
      //
      if (matriceCellules[ligne-1][colonne-1] == null) {
         celluleCourante= null;
         return false;
      }
      
      // Memoriser les coordonnees de la cellule cible
      //
      celluleCourante= new Dimension(ligne, colonne);
      return true;
   }
   
// ------                                           *** Methode modifierBordure
   
   public boolean modifierBordure (int ligne, 
                                   int colonne,
                                   String pointCardinal,
                                   int epaisseur) {
   	
   	  // Controler la validite des parametres
      //
      if (ligne < 1 || ligne > nbLignes) return false;
      if (colonne < 1 || colonne > nbColonnes) return false;
      if (!pointCardinal.equals("NORD") &&
          !pointCardinal.equals("EST")  &&
          !pointCardinal.equals("SUD")  &&
          !pointCardinal.equals("OUEST") ) return false;
      if (epaisseur < 0) return false;
   	
   	  // Controler la presence de la cellule cible dans la trame
      //
      Cellule celluleCourante= matriceCellules[ligne-1][colonne-1];
      if (celluleCourante == null) return false;
            
      // Modifier la bordure cible de la cellule cible
      //
      celluleCourante.modifierBordure(pointCardinal, epaisseur);
      
      // Repeindre le panneau support
      //
      hamecon.repaint();

      return true;
   }
   
// ------                                          *** Methode supprimerBordure
   
   public boolean supprimerBordure (int ligne, 
                                   int colonne,
                                   String pointCardinal) {
   	
   	  // Controler la validite des parametres
      //
      if (ligne < 1 || ligne > nbLignes) return false;
      if (colonne < 1 || colonne > nbColonnes) return false;
      if (!pointCardinal.equals("NORD") &&
          !pointCardinal.equals("EST")  &&
          !pointCardinal.equals("SUD")  &&
          !pointCardinal.equals("OUEST") ) return false;
   	
   	  // Controler la presence de la cellule cible dans la trame
      //
      Cellule celluleCourante= matriceCellules[ligne-1][colonne-1];
      if (celluleCourante == null) return false;
            
      // Supprimer la bordure cible de la cellule cible
      //
      celluleCourante.supprimerBordure(pointCardinal);
      
      // Repeindre le panneau support
      //
      hamecon.repaint();

      return true;

   }
   
   
// ------------------------------------       *** Classe interne privee Cellule
   
   private class Cellule {
   private int       positionLigne;
   private int       positionColonne;
   private Dimension gabarit;
   private HashMap   bordures;
   
   // ------                                            *** Constructeur normal
   
      public Cellule (int ligne, int colonne, Dimension gabarit) {
      	
      	 // Memoriser les attributs transmis par parametre
         //
         positionLigne  = ligne;
         positionColonne= colonne;
         this.gabarit= gabarit;
         
         // Construire les quatre bordures de la cellule
         //
         bordures= new HashMap();
         
         bordures.put("NORD",  new Bordure(this, "NORD",  0));
         bordures.put("EST",   new Bordure(this, "EST",   0));
         bordures.put("SUD",   new Bordure(this, "SUD",   0));
         bordures.put("OUEST", new Bordure(this, "OUEST", 0));
      }
      
   // ------                                       *** Methode obtenirRectangle
   
      private Polygon obtenirRectangle () {
         
         // Determiner les dimensions de la cellule elementaire
         //
         int largeurCellule= (int)gabarit.getWidth();
         int hauteurCellule= (int)gabarit.getHeight();
         
         // Determiner les abscisses des sommets de la cellule cible
         //
         int[] abscisses= new int[4];
         
         abscisses[0]= (positionColonne-1)*largeurCellule;
         abscisses[1]= abscisses[0];
         abscisses[2]= abscisses[0] + largeurCellule;
         abscisses[3]= abscisses[2];
      
         // Determiner les ordonnees des sommets de la cellule cible
         //
         int[] ordonnees= new int[4];
         
         ordonnees[0]= (positionLigne-1)*hauteurCellule;
         ordonnees[1]= ordonnees[0] + hauteurCellule;
         ordonnees[2]= ordonnees[1];
         ordonnees[3]= ordonnees[0];
         
         // Construire le polygone associe a la cellule cible
         //
         Polygon polygone= new Polygon(abscisses, ordonnees, 4);
      	
         // Restituer le resultat
         //
         return polygone;
      }
      
   // ------                                               *** Methode dessiner
   
      public void dessiner (Graphics g) {
      Color couleurInitiale= null;
      Bordure bordure= null;
      Polygon rectangle= null;
      	
         // Sauvegarder la couleur courante de trace
         //
      	 couleurInitiale= g.getColor();
      	 
      	 // Traiter la bordure NORD
         //
         bordure= (Bordure)bordures.get("NORD");
         if (bordure != null) {
         
            rectangle = ((Bordure)bordure).obtenirRectangle();
            g.drawPolygon(rectangle);
      	
            if (couleurFond != null) {
               g.setColor(couleurFond);
               g.fillPolygon(rectangle.xpoints, rectangle.ypoints, 4);
            }
         }
         
         // Traiter la bordure EST
         //
         bordure= (Bordure)bordures.get("EST");
         if (bordure != null) {
         
            rectangle = ((Bordure)bordure).obtenirRectangle();
            g.drawPolygon(rectangle);
            if (couleurFond != null) {
               g.setColor(couleurFond);
               g.fillPolygon(rectangle.xpoints, rectangle.ypoints, 4);
            }
         }
         
         // Traiter la bordure SUD
         //
         bordure= (Bordure)bordures.get("SUD");
         if (bordure != null) {
         
            rectangle = ((Bordure)bordure).obtenirRectangle();
            g.drawPolygon(rectangle);
      	
            if (couleurFond != null) {
               g.setColor(couleurFond);
               g.fillPolygon(rectangle.xpoints, rectangle.ypoints, 4);
            }
         }
         
         // Traiter la bordure OUEST
         //
         bordure= (Bordure)bordures.get("OUEST");
         if (bordure != null) {
         
            rectangle = ((Bordure)bordure).obtenirRectangle();
            g.drawPolygon(rectangle);
      	
            if (couleurFond != null) {
               g.setColor(couleurFond);
               g.fillPolygon(rectangle.xpoints, rectangle.ypoints, 4);
            }
         }
         
         // Restaurer la couleur courante de trace
         // 
         g.setColor(couleurInitiale);
      }
      
   // ------                                   *** Methode obtenirPositionCible
   
      public Dimension obtenirPositionCible (int x, int y) {
      	
         // Construire le polygone attache a la cellule cible
         //
         Polygon polygone= obtenirRectangle();
      	
         // Determiner si le point cible (x, y) appartient a cette cellule
         //	
         if(polygone.contains(x,y)) return new Dimension(positionLigne,
                                                         positionColonne);
         
         return null; 
      }
      
   // ------                                        *** Methode modifierBordure
   
      public void modifierBordure (String pointCardinal, int epaisseur) {
      	
      	 bordures.put(pointCardinal,  
      	              new Bordure(this, pointCardinal,  epaisseur));
      }
      
   // ------                                       *** Methode supprimerBordure
   
      public void supprimerBordure (String pointCardinal) {
      	
      	 bordures.put(pointCardinal, null);
      }    
   } 
   
// ------------------------------------       *** Classe interne privee Bordure
   
   private class Bordure {
   private Polygon rectangle;
   
   // ------                                            *** Constructeur normal
   
      public Bordure (Cellule celluleSupport, 
                      String pointCardinal, 
                      int epaisseur) {
                      	
      int[] abscisses= new int[4];
      int[] ordonnees= new int[4];
      	
         // Obtenir l'enveloppe de la cellule support
         //
         Polygon enveloppe= celluleSupport.obtenirRectangle();
         
         // Traiter le cas de la bordure NORD
         //
         if (pointCardinal.equals("NORD") && epaisseur >= 0) {
         	
            abscisses[0]= enveloppe.xpoints[0];
            abscisses[1]= enveloppe.xpoints[3];
            abscisses[2]= abscisses[1];
            abscisses[3]= abscisses[0];
      
            ordonnees[0]= enveloppe.ypoints[0];
            ordonnees[1]= enveloppe.ypoints[3];
            ordonnees[2]= ordonnees[1] + epaisseur;
            ordonnees[3]= ordonnees[0] + epaisseur;
         
            rectangle= new Polygon(abscisses, ordonnees, 4);
         }
         
         // Traiter le cas de la bordure EST
         //
         if (pointCardinal.equals("EST") && epaisseur >= 0) {
         	
            abscisses[0]= enveloppe.xpoints[3];
            abscisses[1]= enveloppe.xpoints[2];
            abscisses[2]= abscisses[1] - epaisseur;
            abscisses[3]= abscisses[0] - epaisseur;
      
            ordonnees[0]= enveloppe.ypoints[3];
            ordonnees[1]= enveloppe.ypoints[2];
            ordonnees[2]= ordonnees[1];
            ordonnees[3]= ordonnees[0];
         
            rectangle= new Polygon(abscisses, ordonnees, 4);
         }
         
         // Traiter le cas de la bordure SUD
         //
         if (pointCardinal.equals("SUD") && epaisseur >= 0) {
         	
            abscisses[0]= enveloppe.xpoints[2];
            abscisses[1]= enveloppe.xpoints[1];
            abscisses[2]= abscisses[1];
            abscisses[3]= abscisses[0];
      
            ordonnees[0]= enveloppe.ypoints[2];
            ordonnees[1]= enveloppe.ypoints[1];
            ordonnees[2]= ordonnees[1] - epaisseur;
            ordonnees[3]= ordonnees[0] - epaisseur;
         
            rectangle= new Polygon(abscisses, ordonnees, 4);
         }
         
         // Traiter le cas de la bordure OUEST
         //
         if (pointCardinal.equals("OUEST") && epaisseur >= 0) {
         	
            abscisses[0]= enveloppe.xpoints[1];
            abscisses[1]= enveloppe.xpoints[0];
            abscisses[2]= abscisses[1] + epaisseur;
            abscisses[3]= abscisses[0] + epaisseur;
      
            ordonnees[0]= enveloppe.ypoints[1];
            ordonnees[1]= enveloppe.ypoints[0];
            ordonnees[2]= ordonnees[1];
            ordonnees[3]= ordonnees[0];
         
            rectangle= new Polygon(abscisses, ordonnees, 4);
         }
      }
      
   // ------                                     *** Accesseurs de consultation
   
      private Polygon obtenirRectangle () {return rectangle;}
   
   }
} 
