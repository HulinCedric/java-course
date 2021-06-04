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
//    + Version 2.0.0   : amenagement du centre du cadre
//    + Version 2.1.0   : ajout d'un bouton de commande (D)
//    + Version 2.2.0   : ajout d'un panneau Sud
//    + Version 2.3.0   : le bouton D devient un attribut boutonDialogue
//    + Version 2.4.0   : ajout gestion evenements sur bouton de dialogue
//    + Version 2.5.0   : les classes internes deviennent privees
//    + Version 2.6.0   : l'ecouteur de fenetre devient une classe anonyme
//    + Version 2.7.0   : l'ecouteur de bouton devient une classe anonyme
//
// Auteurs : M. Collard et A. Thuaire
//

import java.awt.*;
import java.awt.event.*;

public class FenetreSaisieTexte extends Frame {
private TextArea zoneAffichage;
private Panel panneauSud;
private Button boutonDialogue;

   public FenetreSaisieTexte() {
   	
      // Description statique
      //
      setSize(500, 200);
      setTitle ("(AWT) Fenetre de saisie de texte - V 2.7.0");
      setLocation (400, 300);
      
      setBackground(Color.yellow);
      setForeground(Color.red);
      setFont(new Font("Times Roman", Font.BOLD, 16));

      zoneAffichage= new TextArea(4, 20);
      add(zoneAffichage, "North");
      
      panneauSud= new Panel();
      add(panneauSud, "South");
      panneauSud.setForeground(Color.black);
      
      boutonDialogue= new Button("Ouvrir dialogue");
      panneauSud.add(boutonDialogue);
      
      // Description dynamique
      //
      addWindowListener (new WindowAdapter() 
         {	 
            public void windowClosing (WindowEvent e) {System.exit(0);}
         }); 

      boutonDialogue.addActionListener (new ActionListener()
         {	 
            public void actionPerformed (ActionEvent e) {System.exit(0);}
         });
   }
   
   public static void main(String[] args) {
      new FenetreSaisieTexte().show();	
   }

}
