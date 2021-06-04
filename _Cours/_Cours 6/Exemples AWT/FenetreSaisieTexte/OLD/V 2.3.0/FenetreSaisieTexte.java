//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Exemple d'applications interactives non parametrees
//
// Edition A 		    : fenetre de saisie de texte
//    + Version 1.0.0	: description statique uniquement
//    + Version 1.1.0   : ajout gestion bouton X
//    + Version 1.2.0   : ajout gestion activation fenetre
//
// Edition B            : fenetre de saisie avec bouton de commande
//    + Version 2.0.0   : amenagement du panneau central
//    + Version 2.1.0   : mise en place du bouton de commande (D)
//    + Version 2.2.0   : ajout d'un panneau Sud
//
// Auteurs : M. Collard et A. Thuaire
//

import java.awt.*;
import java.awt.event.*;

public class FenetreSaisieTexte extends Frame {
private TextArea zoneAffichage;
private Panel panneauSud;

   public FenetreSaisieTexte() {
   	
      // Description statique
      //
      setSize(500, 200);
      setTitle ("(AWT) Fenetre de saisie de texte - V 2.2.0");
      setLocation (400, 300);
      
      setBackground(Color.yellow);
      setForeground(Color.red);
      setFont(new Font("Times Roman", Font.BOLD, 16));

      zoneAffichage= new TextArea(4, 20);
      add(zoneAffichage, "North");
      
      // add(new Button("Ouvrir dialogue"));
      
      panneauSud= new Panel();
      panneauSud.setForeground(Color.black);
      panneauSud.add(new Button("Ouvrir dialogue"));
      add(panneauSud, "South");
      
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
