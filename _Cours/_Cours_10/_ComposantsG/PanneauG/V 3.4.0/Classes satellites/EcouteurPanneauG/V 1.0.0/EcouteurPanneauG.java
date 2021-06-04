//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe EcouteurPanneauG - Ecouteur souris de toute instance de PanneauG
//
// Edition A    : Cours_10
//    + Version 1.0.0	: limite a des "call back" independantes de la cible
// 
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
  
public class EcouteurPanneauG implements MouseListener, MouseMotionListener {
private PanneauG panneauCible; 
                                      	
   public EcouteurPanneauG (PanneauG hamecon) {
   	  panneauCible= hamecon; 	
   }
   
   public void mouseClicked(MouseEvent e) {
      System.out.println("("+e.getX()+", "+e.getY()+ ")");
   }

   public void mousePressed  (MouseEvent e) {}
   public void mouseReleased (MouseEvent e) {}
   public void mouseEntered  (MouseEvent e) {}
   public void mouseExited   (MouseEvent e) {}
  
   public void mouseMoved    (MouseEvent e) {
   	  System.out.println("["+e.getX()+", "+e.getY()+ "]");
   }
   
   public void mouseDragged  (MouseEvent e) {
      System.out.println("{"+e.getX()+", "+e.getY()+ "}");
   }  
} 
