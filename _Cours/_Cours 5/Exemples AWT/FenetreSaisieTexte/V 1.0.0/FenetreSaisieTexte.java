//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package AWT
//
// Exemple d'applications interactives non parametrees
//
// Edition A    : fenetre de saisie de texte
//    + Version 1.0.0	: fenetre limitee a son cadre
//
// Auteurs : M. Collard et A. Thuaire
//

import java.awt.*;

public class FenetreSaisieTexte extends Frame {

   public FenetreSaisieTexte() {
   	
      // Description statique limitee au cadre
      //
      setSize(500, 200);
      setTitle ("(AWT) Fenetre de saisie de texte - V 1.0.0");
      setLocation (400, 300);
   }

   public static void main(String[] args) {
      new FenetreSaisieTexte().show();	
   }

}
