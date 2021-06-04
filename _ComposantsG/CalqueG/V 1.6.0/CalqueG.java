//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants graphiques generiques
//
// Classe CalqueG - Calque de visualisation d'objets graphiques
//
// Edition A    : exigences du projet TravelDraw et IE_2 2008_2009
//
//    + Version 0.0.0	: mise en place des objets graphiques elementaires  
//                        "label", "polygone", "trame", "segment" et "image",
//                        avec exploitation du XOR mode
//
//    + Version 0.1.0	: introduction d'une methode "enregistrer" pour assurer
//                        la persistance dans un fichier de donnees des objets  
//                        graphiques poses sur le calque 
//
//    + Version 0.2.0	: introduction d'un second constructeur normal pour 
//                        restaurer un calque preexistant a partir d'un fichier 
//                        de donnees
// 
//    + Version 0.3.0	: introduction d'une methode "charger" pour charger un
//                        calque preexistant a partir d'un fichier de donnees 
//                        et completer ainsi un calque courant
//
//    + Version 0.4.0	: suppression de l'exploitation du XOR mode       
//
// Edition B    : exigences de la classe principale ChronogrammeG
//
//    + Version 1.0.0	: introduction d'une classe satellite ComposantG      
//
//    + Version 1.1.0   : introduction d'une nouvelle classe satellite TrameG 
//                        chargee de modeliser une trame de fond de calque, sous
//                        forme d'une matrice de cellules (type Excel)
//
//    + Version 1.2.0   : ajout d'un nouvel attribut et d'un accesseur associe
//                        "fixerCouleurTrait" dans la classe ComposantG pour 
//                        individualiser la couleur de trait de chaque objet
//                        graphique
// 
//    + Version 1.3.0   : ajout d'un nouvel attribut et d'accesseurs associes
//                        "masquer" et "demasquer" dans la classe ComposantG
//                        pour pouvoir masquer/demasquer dynamiquement chaque 
//                        objet graphique     
//     
//    + Version 1.4.0   : introduction de la designation d'une cellule courante  
//                        dans la trame sous jacente et des methodes de gestion 
//                        associees
//    
//    + Version 1.5.0   : introduction d'une gestion individualisee des bordures 
//                        "NORD", "SUD", "EST" et "OUEST" de chaque cellule
//    
//    + Version 1.6.0   : introduction de la possibilite de modifier de facon 
//                        dynamique une des bordures d'une cellule cible de la 
//                        trame sous jacente
//  
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class CalqueG extends PanneauG {
private LinkedList composants;
private TrameG     trame;

// ------                                       *** Premier constructeur normal

   public CalqueG (Object hamecon, HashMap config) {
   	
      // Invoquer le constructeur de la classe mere
      //
      super(hamecon, config);
      
      // Initialiser la liste des composants graphiques preexistants
      //
      composants= new LinkedList(); 
   }

// ------                                        *** Second constructeur normal

   public CalqueG (Object hamecon, HashMap config, 
                   String cheminFichier, String version) {
   	
      // Invoquer le constructeur de la classe mere
      //
      super(hamecon, config);
   	
      // Restaurer les composants graphiques d'un calque preexistant
      //
      composants= restaurer(cheminFichier, version);
   }
     
// ------                                                     *** Methode paint
   
   public void paint(Graphics g) {
   	
      super.paint(g);
      
      // Dessiner la trame du calque
      //
      if (trame != null) trame.dessiner(g);
      
      // Parcourir la liste des composants a dessiner
      //
      ComposantG composant= null;
            
      Iterator i= composants.iterator();
     
      while (i.hasNext()) {
      	
         // Dessiner le composant graphique courant
         //
         composant= (ComposantG) i.next();
         composant.dessiner(g);
      } 
   }
   
// ------                                        *** Accesseurs de modification
   
   public boolean ajouterComposant(ComposantG description) {
   	
      // Controler la validite du parametre
      //
      if (description == null) return false;
      
      // Ajouter la description du composant dans la liste courante
      //
      composants.addLast(description);
      
      // Provoquer le rafraichissement du calque
      //
      repaint();
      
      return true;
   }
      
   public boolean retirerComposant(HashMap description) {
   	
      // Supprimer la description cible de la liste des composants
      //
      boolean status= composants.remove(description);
      
      // Provoquer le rafraichissement du calque
      //
      repaint();
      
      return status;
   }
   
// ------                                                      *** Methode main     
   
   public static void main(String[] args) {
   Dimension coinNO;
   Dimension gabarit;
   
      // Mettre en place un cadre support
      //
      JFrame f1= new JFrame();
      
      f1.setTitle("(SWING) Classe CalqueG - V 1.6.0");
      f1.setSize(500, 450);
      f1.setLocation(250, 250);
      
      // Charger un dictionnaire de configuration
      //   	
      HashMap config_1= (HashMap) Config.load("Panneau_A", "1.0.0");
     
      // Creer un premier calque et l'ajouter au cadre support
      //
      CalqueG calque_1= new CalqueG(f1, config_1);
      f1.getContentPane().add(calque_1);
      
      // Montrer le cadre support et son calque interne
      //
      f1.show();
      
      // Ajouter et dessiner une trame de fond 
      //
      calque_1.ajouterTrame(6, 7, new Dimension(70, 70));
      
      // Retirer les cellules (1, 1), (1, 2) et (1, 3)
      //
      calque_1.trame.retirerCellule(1, 1);
      calque_1.trame.retirerCellule(1, 2);
      calque_1.trame.retirerCellule(1, 3);
      
      // -- Dessiner un label dans le calque
      //
      coinNO= new Dimension(75, 50);
      ComposantG label= new ComposantG(calque_1, coinNO, "Joueur_1");
      
      calque_1.ajouterComposant(label);
      
      // Modifier la bordure EST des cellules (2, 2) et (5, 2)
      //
      calque_1.trame.modifierBordure(2, 2, "EST", 10);
      calque_1.trame.modifierBordure(5, 2, "EST", 10);
   }
   
// ------                                               *** Methode enregistrer
   
   public boolean enregistrer(String cheminFichier, String version) {
   	
      // Controler la validite des parametres
      //
      if (cheminFichier == null) return false;
      if (version == null) return false;
   	
      // Assurer le persistance de tous les composants graphiques du calque
      //
      return Data.store(composants, cheminFichier, version);
   }
   
// ------                                                 *** Methode restaurer
   
   private LinkedList restaurer(String cheminFichier, String version) {
   	
      // Controler la validite des parametres
      //
      if (cheminFichier == null) return new LinkedList();
      if (version == null) return new LinkedList();
   	
      // Charger tous les composants graphiques d'un calque preexistant
      //
      Object data= Data.load(cheminFichier, version);
      if (data == null) return new LinkedList();
      
      // Restituer le resultat dans le cas nominal
      //
      return (LinkedList)data;
   }
   
// ------                                                   *** Methode charger
   
   public boolean charger(String cheminFichier, String version) {
   	
      // Controler la validite des parametres
      //
      if (cheminFichier == null) return false;
      if (version == null) return false;
   	
      // Charger tous les composants graphiques d'un calque preexistant
      //
      Object data= Data.load(cheminFichier, version);
      if (data == null) return false;
      
      // Ajouter ces composants dans le calque courant
      //
      ComposantG composant= null;
      
      Iterator i= ((LinkedList)data).iterator();
     
      while (i.hasNext()) {
      	
         // Ajouter le composant graphique courant
         //
         composant= (ComposantG) i.next();
         ajouterComposant(composant);
      }
      // Restituer le resultat dans le cas nominal
      //
      return true;
   }
   
// ------                                              *** Methode ajouterTrame
   
   public boolean ajouterTrame (int nbLignes, 
                                int nbColonnes, 
                                Dimension gabarit) {
      	
     // Controler la validite des parametres
     //
     if (nbLignes <= 0)   return false;
     if (nbColonnes <= 0) return false;
      	
     // Construire et memoriser la trame de cellules
     //
     trame= new TrameG(this, nbLignes, nbColonnes, gabarit);
      	
     // Repeindre le panneau
     //
     repaint();
     return true;
   }
      
// ------                                              *** Methode retirerTrame
   
   public void retirerTrame () {trame= null;repaint();}
      
// ------                                             *** Methode presenceTrame
   
   public boolean presenceTrame() {
      	
     if (trame == null) return false;
     return true;
   }
}
