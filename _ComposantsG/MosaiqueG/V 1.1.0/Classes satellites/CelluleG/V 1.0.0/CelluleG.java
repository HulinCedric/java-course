//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe CelluleG - Element de la partition d'une mosaique generique
//
// Edition A    : Cours_10
//    + Version 1.0.0	: version initiale
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import javax.swing.*;
   
public class CelluleG extends PanneauG {
private Object    mosaique;
private Dimension position;
private boolean   etatCellule;
private Object[]  demons; 
   
   public CelluleG (Object hamecon, HashMap config, Dimension position) {
      	
      super(hamecon, config);
      
      // Controler la validite des parametres
      //
      if (hamecon == null)  return;
      if (config  == null)  return;
      if (position == null) return;
      
      // Memoriser les attributs transmis par parametre
      //
      mosaique     = hamecon;
      this.position= position;
      
      // Fixer l'etat par defaut de la cellule
      //
      etatCellule= false;
      
      // Ajouter le panneau sous jacent au panneau principal
      //
      ((PanneauG)mosaique).add(this);
   }
      
// ------                                    *** Accesseurs de consultation
   
   public Dimension obtenirPosition    () {return position;}
   public String    obtenirCheminImage () {return super.obtenirCheminImage();}
   public String    obtenirTexteTitre  () {return super.obtenirTexteTitre();}
   public Color     obtenirCouleurTitre() {return super.obtenirCouleurTitre();}
   public Font      obtenirPoliceTitre () {return super.obtenirPoliceTitre();}
   public boolean   obtenirEtat        () {return etatCellule;} 
   public Object[]  obtenirDemons      () {return demons;}
      
// ------                                    *** Accesseurs de modification
   
   public void fixerEtat (boolean etat) {etatCellule= etat;}
      
// ------                                        *** Methodes ajouterImage
   
   public void ajouterImage (String cheminImage) {
      	
     // Controler la validite du parametre
     //
     if (cheminImage == null) return;
      	 
     // Ajouter l'image au panneau sous jacent
     //
     super.ajouterImage(cheminImage);
   } 
      
// ------                                         *** Methode retirerImage
   
   public void retirerImage () {super.retirerImage();}
      
// ------                                        *** Methode ajouterDemon
   
   public boolean _ajouterDemon () {return true;}
      
// ------                                        *** Methode retirerDemon
   
   public boolean _retirerDemon () {return true;}
      
// ------                                        *** Methode presenceDemon
   
   public boolean _presenceDemon () {
      	 
     if (demons.length != 0) return true;
     return false;
   }
}
   
