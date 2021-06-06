//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe EcouteurMosaiqueG - Ecouteur souris par defaut de toute instance de 
//                            la classe MosaiqueG
//
// Edition A    : Cours_10
//    + Version 1.0.0	: introduction d'une "call back" pour visualiser les
//                        coordonnees (ligne, colonne) de la cellule designee 
//                        par un clic souris
//    + Version 1.1.0   : modification de la "call back" pour visualiser les
//                        coordonnees relatives (ligne, colonne) dans la
//                        cellule designee par un clic souris 
// 
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
  
public class EcouteurMosaiqueG implements MouseListener, MouseMotionListener {
private MosaiqueG mosaique; 
                                 
   public EcouteurMosaiqueG () {}
        	
   public EcouteurMosaiqueG (MosaiqueG hamecon) {mosaique= hamecon;}
   
   public void mouseClicked(MouseEvent e) {
         
      // Obtenir les coordonnees absolues du clic courant
      //
      int x= e.getX();
      int y= e.getY();
         
      // Obtenir la designation de la cellule cible
      //
      Dimension positionCellule= mosaique.obtenirPositionCelluleCible(x,y);
      if (positionCellule == null) return;
         
      // Extraire les coordonnees (ligne, colonne) de cette cellule
      //
      int ligneCellule  = (int)positionCellule.getWidth();
      int colonneCellule= (int)positionCellule.getHeight();
      
      // Obtenir la position relative du clic dans la cellule cible
      //
      Dimension cible= mosaique.obtenirPositionRelativeCible(x,y);
      if (cible == null) return; 
      
      // Extraire les coordonnees (ligne, colonne) de cette position
      //
      int ligneClic  = (int)cible.getWidth();
      int colonneClic= (int)cible.getHeight();
      
      // Visualiser les coordonnees de cette cellule
      //
      System.out.print("[" + ligneCellule + ", "+ colonneCellule + "]");
      System.out.println(" : (" + ligneClic + ", "+ colonneClic + ")"); 
   }
   
   public void mouseMoved    (MouseEvent e) {}
   public void mouseDragged  (MouseEvent e) {}  

   public void mousePressed  (MouseEvent e) {}
   public void mouseReleased (MouseEvent e) {}
   public void mouseEntered  (MouseEvent e) {}
   public void mouseExited   (MouseEvent e) {}
} 
