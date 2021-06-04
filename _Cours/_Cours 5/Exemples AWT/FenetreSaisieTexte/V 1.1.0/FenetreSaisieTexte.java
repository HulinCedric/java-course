//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Exemple d'applications interactives non parametrees
//
// Edition A 	: fenetre de saisie de texte
//    + Version 1.0.0	: fenetre limitee au cadre externe
//    + Version 1.1.0   : ajout gestion evenement bouton X
//
// Auteurs : M. Collard et A. Thuaire
//

import java.awt.*;
import java.awt.event.*;

public class FenetreSaisieTexte extends Frame {

   public FenetreSaisieTexte() {
   	
      // Description statique
      //
      setSize(500, 200);
      setTitle ("(AWT) Fenetre de saisie de texte - V 1.1.0");
      setLocation (400, 300);
      
      // Description dynamique
      //
      addWindowListener (new EcouteurFenetre());
   }
   
   public class EcouteurFenetre extends WindowAdapter {	
      public void windowClosing (WindowEvent e) {System.exit(0);}
   }

   public static void main(String[] args) {
      new FenetreSaisieTexte().show();	
   }

}
