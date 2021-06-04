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
// Auteur : A. Thuaire
//

import java.awt.*;
import javax.swing.*;
  
public class TrameG {
   
private JPanel      hamecon;
private Cellule[][] matriceCellules;
private int         nbLignes;
private int         nbColonnes;
private Dimension   gabarit;

private Color      couleurTrait= Color.gray;
private Color      couleurFond= Color.black;
   
// ------                                     *** Premier constructeur normal
   
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
      	
      // Retirer la maille cible
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
   
   
   // -------------------------------------------    *** Classe interne Cellule
   
   private class Cellule {
   private int positionLigne;
   private int positionColonne;
   private Dimension gabarit;
   
      public Cellule (int ligne, int colonne, Dimension gabarit) {
      	
      	 // Memoriser les attributs transmis par parametre
         //
         positionLigne  = ligne;
         positionColonne= colonne;
         this.gabarit= gabarit;
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
      	
         // Sauvegarder la couleur courante de trace
         //
      	 couleurInitiale= g.getColor();
      	 
      	 // Construire le polygone attache a la cellule support
         //
         Polygon polygone= obtenirRectangle();
      	
         // Traiter le cas d'un tramage colore
         //
         if (couleurFond != null) {
            g.setColor(couleurFond);
            g.fillPolygon(polygone.xpoints, polygone.ypoints, 4);
         }
         
         // Modifier la couleur courante du trait de dessin
         //
         g.setColor(couleurTrait);
         
         // Dessiner les bords de la cellule courante
         //
         g.drawPolygon(polygone);
         
         // Restaurer la couleur courante de trace
         // 
         g.setColor(couleurInitiale);
      }
      
   // ------                                  *** Methode obtenirPositionCible
   
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
   } 
} 
