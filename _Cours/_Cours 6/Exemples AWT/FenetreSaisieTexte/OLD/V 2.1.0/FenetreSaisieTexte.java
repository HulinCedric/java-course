//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2007_2008 - Package AWT
//
// Exemple d'applications interactives non parametrees
//
// Edition A 		    : fenetre de saisie de texte
//    + Version 1.0.0	: description statique uniquement
//    + Version 1.1.0   : ajout gestion bouton X
//    + Version 1.2.0   : ajout gestion activation fenetre
//
// Edition B            : fenetre de saisie avec bouton de commande
//    + Version 2.0.0   : amenagement du centre du cadre
//
// Auteurs : M. Collard et A. Thuaire
//

import java.awt.*;
import java.awt.event.*;

public class FenetreSaisieTexte extends Frame {
private TextArea zoneAffichage;

   public FenetreSaisieTexte() {
   	
      // Description statique
      //
      setSize(500, 200);
      setTitle ("(AWT) Fenetre de saisie de texte - V 2.0.0");
      setLocation (400, 300);
      
      setBackground(Color.yellow);
      setForeground(Color.red);
      setFont(new Font("Times Roman", Font.BOLD, 16));

      zoneAffichage= new TextArea(4, 20);
      add(zoneAffichage, "North");
      
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
