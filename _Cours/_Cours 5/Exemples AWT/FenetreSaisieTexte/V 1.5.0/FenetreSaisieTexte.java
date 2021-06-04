//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Exemple d'applications interactives non parametrees
//
// Edition A 	: fenetre de saisie de texte
//    + Version 1.0.0	: fenetre limitee au cadre externe
//    + Version 1.1.0   : ajout gestion evenement bouton X
//    + Version 1.2.0   : ajout d'une zone interne de saisie texte
//    + Version 1.3.0   : signalement console de chaque activation du cadre
//    + Version 1.4.0   : signalement des activations du cadre dans la zone
//                        interne de saisie texte
//    + Version 1.5.0   : modification des caracteristiques externes de la 
//                        zone de saisie texte
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
      setTitle ("(AWT) Fenetre de saisie de texte - V 1.5.0");
      setLocation (400, 300);

      zoneAffichage= new TextArea();
      add(zoneAffichage, "Center");

      zoneAffichage.setBackground(Color.yellow);
      zoneAffichage.setForeground(Color.red);
      zoneAffichage.setFont(new Font("Times Roman", Font.BOLD, 16));
      
      // Description dynamique
      //
      addWindowListener (new EcouteurFenetre());
   }
   
   public class EcouteurFenetre extends WindowAdapter {	
   
      public void windowClosing (WindowEvent e) {System.exit(0);}
      
      public void windowActivated (WindowEvent e) {
         zoneAffichage.append("Vu !!!\n");
      }
   }

   public static void main(String[] args) {
      new FenetreSaisieTexte().show();	
   }

}