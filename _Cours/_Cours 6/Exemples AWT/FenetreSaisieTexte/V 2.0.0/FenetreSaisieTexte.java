//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Exemple d'applications interactives non parametrees
//
// Edition A 	: fenetre de saisie de texte
//    + Version 1.0.0   : fenetre limitee au cadre externe
//    + Version 1.1.0   : ajout gestion evenement bouton X
//    + Version 1.2.0   : ajout d'une zone interne de saisie texte
//    + Version 1.3.0   : signalement console de chaque activation du cadre
//    + Version 1.4.0   : signalement des activations du cadre dans la zone
//                        interne de saisie texte
//    + Version 1.5.0   : modification des caracteristiques externes de la 
//                        zone de saisie texte
//    + Version 1.6.0   : introduction d'un panneau support de la zone de
//                        saisie texte
//    + Version 1.7.0   : l'ecouteur du bouton X est defini par une classe
//                        interne anonyme
//    + Version 1.8.0   : ajustement automatiquement de la taille du panneau
//                        a celle du cadre
//
// Edition B    : integration d'une barre de menus deroulants
//    + Version 2.0.0   : ajout de la barre de menus et d'un menu "Fichier",
//                        sans les items associes et sans gestion d'evenements
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;

public class FenetreSaisieTexte extends Frame {
private Panel    panneauSupport;
private TextArea zoneAffichage;
private MenuBar  barreMenus;

   public FenetreSaisieTexte() {
   	
      // --- Description statique
      //
      setSize(500, 200);
      setTitle ("(AWT) Fenetre de saisie de texte - V 2.0.0");
      setLocation (400, 300);
      
      panneauSupport= new Panel();                    // Panneau support
      panneauSupport.setLayout(new GridLayout(1,0));
      add(panneauSupport);

      zoneAffichage= new TextArea();                  // Zone de saisie texte
      panneauSupport.add(zoneAffichage);

      zoneAffichage.setBackground(Color.yellow);
      zoneAffichage.setForeground(Color.red);
      zoneAffichage.setFont(new Font("Times Roman", Font.BOLD, 16));
      
      barreMenus= new MenuBar();                      // Barre de menus
      setMenuBar(barreMenus);
      barreMenus.add(new Menu("Fichier"));            // Menu "Fichier"
      
      // --- Description dynamique
      //
      addWindowListener (new WindowAdapter() 
         {	 
            public void windowClosing (WindowEvent e) {System.exit(0);}
         });
   }

   public static void main(String[] args) { new FenetreSaisieTexte().show();}

}
