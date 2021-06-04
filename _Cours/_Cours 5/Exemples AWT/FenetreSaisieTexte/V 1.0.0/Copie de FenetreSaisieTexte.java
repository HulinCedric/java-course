//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Exemple d'applications interactives non parametrees
//
// Edition A 		: fenetre de saisie de texte
//    + Version 1.0.0	: description statique uniquement
//
// Auteurs : M. Collard et A. Thuaire
//

import java.awt.*;

public class FenetreSaisieTexte extends Frame {
private TextArea zoneAffichage;

   public FenetreSaisieTexte() {
   	
      // Description statique
      //
      setSize(500, 200);
      setTitle ("(AWT) Fenetre de saisie de texte - V 1.0.0");
      setLocation (400, 300);

      // zoneAffichage= new TextArea(5, 40);
      // add(zoneAffichage, "Center");
   }

   public static void main(String[] args) {
      new FenetreSaisieTexte().show();	
   }

}
