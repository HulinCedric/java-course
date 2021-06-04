//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Exemple d'applications interactives non parametrees
//
// Edition A    : fenetre de visualisation d'images et de dessins
//    + Version 1.0.0	: fenetre limitee a son cadre
//
// Auteur : A. Thuaire
//

import java.awt.*;
import javax.swing.*;

public class FenetreImage extends JFrame {

   public FenetreImage() {
   	
      // Description complete du cadre
      //
      setSize(1000, 700);
      setTitle ("(SWING) Fenetre image - V 1.0.0");
      setLocation (200, 50);
   }

   public static void main(String[] args) {
      new FenetreImage().show();	
   }

}
