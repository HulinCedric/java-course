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
//    + Version 0.4.0	: suppression du XOR mode      
//     
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class CalqueG extends PanneauG {
private LinkedList composants;

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
      
      // Parcourir la liste des composants a dessiner
      //
      HashMap composant= null;
            
      Iterator i= composants.iterator();
     
      while (i.hasNext()) {
      	
         // Dessiner le composant graphique courant
         //
         composant= (HashMap) i.next();
         dessinerComposant(g, composant);
      }
   }
   
// ------                                        *** Accesseurs de modification
   
   public boolean ajouterComposant(HashMap description) {
   	
      // Controler la validite du parametre
      //
      if (description == null) return false;
      
      // Controler la presence de la cle "type" dans la description
      //
      String type= (String)description.get("type");
      if (type == null) return false;
      
      // Ajouter la description du composant dans la liste courante
      //
      if (composants == null) composants= new LinkedList();
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
   PanneauG panneau= null;
   
      // Mettre en place un cadre support
      //
      JFrame f1= new JFrame();
      
      f1.setTitle("(SWING) Classe CalqueG - V 0.3.0 (C1)");
      f1.setSize(500, 300);
      f1.setLocation(50, 50);
      
      // Charger un dictionnaire de configuration
      //   	
      HashMap config_1= (HashMap) Config.load("Panneau_A", "0.0.0");
      
      // Creer un calque avec le second constructeur normal
      //
      CalqueG calque_1= new CalqueG(f1, config_1, "Calque_A", "0.1.0");
      
      // Charger dans ce calque les objets graphiques d'un autre calque
      //
      calque_1.charger("Calque_B", "0.1.0");
      
      // Ajouter le calque au cadre support
      //
      f1.getContentPane().add(calque_1);
      
      // Montrer le premier cadre support et son calque interne
      //
      f1.show();
   } 
   
// ------                                         *** Methode dessinerComposant
   
   private void dessinerComposant(Graphics g, HashMap description) {
   	
      // Obtenir le type du composant
      //    
      String type= (String)description.get("type");
      
      // Traiter le composant en fonction de son type
      //
      if (type.equals("Trame"))    dessinerTrame   (g, description);
      if (type.equals("Segment"))  dessinerSegment  (g, description);
      if (type.equals("Label"))    dessinerLabel    (g, description);
      if (type.equals("Polygone")) dessinerPolygone (g, description);
      if (type.equals("Image"))    dessinerImage    (g, description);
   }
   
// ------                                           *** Methode dessinerSegment
 
   private void dessinerSegment(Graphics g, HashMap description) {
   Color couleurInitiale= null;
   
      // Sauvegarder la couleur initiale de trait
      //
      couleurInitiale= g.getColor();
      
      // Obtenir la couleur du segment a dessiner
      //
      Color couleurCourante= (Color)description.get("couleur");
      
      // Modifier la couleur courante de dessin dans le calque
      //
      if (couleurCourante != null) g.setColor(couleurCourante);
      	
      // Obtenir les coordonnees du point origine
      //
      int x1= ((Integer)description.get("abscisse")).intValue();
      int y1= ((Integer)description.get("ordonnee")).intValue();
      
      // Obtenir les coordonnees du point extremite
      //
      Dimension gabarit= (Dimension)description.get("gabarit");
      int largeur= (int)gabarit.getWidth();
      int hauteur= (int)gabarit.getHeight();
      
      // Dessiner le segment cible
      //
      g.drawLine(x1, y1, x1+largeur, y1+hauteur);
      
      // Restaurer la couleur initiale de dessin dans le calque
      //
      if (couleurCourante != null) g.setColor(couleurInitiale);
   }
   
// ------                                             *** Methode dessinerLabel

   private void dessinerLabel(Graphics g, HashMap description) {
   Color couleurInitiale= null;
   
      // Sauvegarder la couleur initiale de trait
      //
      couleurInitiale= g.getColor();
      
      // Obtenir la couleur du label a dessiner
      //
      Color couleurCourante= (Color)description.get("couleur");
      
      // Modifier la couleur courante de dessin dans le calque
      //
      if (couleurCourante != null) g.setColor(couleurCourante);
         
      // Obtenir le texte du label
      //
      String label = (String)description.get("label");
      if (label == null) return;
      
      // Obtenir les coordonnees du coin Nord Ouest de la zone de dessin
      //
      int x= ((Integer)description.get("abscisse")).intValue();
      int y= ((Integer)description.get("ordonnee")).intValue();
         
      // Dessiner le label depuis l'origine de la zone de dessin
      //   
      g.drawString(label, x, y);
      
      // Restaurer la couleur initiale de dessin dans le calque
      //
      if (couleurCourante != null) g.setColor(couleurInitiale);
   }

// ------                                             *** Methode dessinerTrame
 
   private void dessinerTrame(Graphics g, HashMap description) {
   Color couleurInitiale= null;
   
      // Sauvegarder la couleur initiale de trait
      //
      couleurInitiale= g.getColor();
      
      // Obtenir la couleur de la trame a dessiner
      //
      Color couleurCourante= (Color)description.get("couleur");
      
      // Modifier la couleur courante de dessin dans le calque
      //
      if (couleurCourante != null) g.setColor(couleurCourante);
      
      // Obtenir le pas de la trame 
      //
      Integer source= (Integer)description.get("source");
      int pas= 15;
      if (source != null) pas= source.intValue();
      
      // Obtenir le gabarit du calque
      //
      int largeur= getWidth();
      int hauteur= getHeight();
      
      // Dessiner les lignes horizontales 
      //
      for (int i= pas; i <= hauteur; i+=pas) {
         g.drawLine(0, i, largeur, i);
      }
      
      // Dessiner les lignes verticales 
      //
      for (int j= pas; j <= largeur; j+=pas) {
         g.drawLine(j, 0, j, hauteur);
      }
      
      // Restaurer la couleur initiale de dessin dans le calque
      //
      if (couleurCourante != null) g.setColor(couleurInitiale);
   }
         
// ------                                          *** Methode dessinerPolygone

   private void dessinerPolygone(Graphics g, HashMap description) {
   Color couleurInitiale= null;
   
      // Sauvegarder la couleur initiale de trait
      //
      couleurInitiale= g.getColor();
      
      // Obtenir la couleur du polygone a dessiner
      //
      Color couleurCourante= (Color)description.get("couleur");
      
      // Modifier la couleur courante de dessin dans le calque
      //
      if (couleurCourante != null) g.setColor(couleurCourante);
   
      // Obtenir le polygone a dessiner
      //
      Polygon source= ((Polygon)description.get("source"));
      if (source == null) return;
      	
      // Obtenir le sommet origine du polygone
      //
      int x0= source.xpoints[0];
      int y0= source.ypoints[0];
      
      // Parcourir tous les sommets du polygone
      //
      int x1, y1, x2, y2;
      
      x1= x0; y1= y0;
      for (int i= 1; i < source.npoints; i++) {
      	
      	// Obtenir le sommet courant
      	//
      	x2= source.xpoints[i];
      	y2= source.ypoints[i];
      	
      	// Dessiner l'arete courante
      	//
      	g.drawLine(x1, y1, x2, y2);
      	
      	// Modifier le sommet origine pour l'arete suivante
      	//
      	x1= x2;
      	y1= y2;	
      }
      
      // Dessiner la derniere arete
      //
      g.drawLine(x1, y1, x0, y0);
      
      // Restaurer la couleur initiale de dessin dans le calque
      //
      if (couleurInitiale != null) g.setXORMode(couleurInitiale);
   }
   
// ------                                             *** Methode dessinerImage

   private void dessinerImage(Graphics g, HashMap description) {
   
      // Obtenir la designation de l'image a visualiser
      //
      String chemin= (String)description.get("source");
      if (chemin == null) return;
      
      // Obtenir les coordonnees du coint Nord Ouest du rectangle de dessin
      //
      int x0= 0, y0= 0;
      Integer abscisse= (Integer)description.get("abscisse");
      if (abscisse != null) x0= abscisse.intValue();
      
      Integer ordonnee= (Integer)description.get("ordonnee");
      if (ordonnee != null) y0= ordonnee.intValue();
      
      // Obtenir les coordonnees du point extremite du rectangle de dessin
      //
      int largeur= getWidth();
      int hauteur= getHeight();
      Dimension gabarit= (Dimension)description.get("gabarit");
      if (gabarit != null) {
      	 largeur= (int)gabarit.getWidth();
         hauteur= (int)gabarit.getHeight();
      }
      
      // Charger l'image cible
      //
      Image image= chargerImage(chemin);
      if (image == null) return;
         
      // Dessiner l'image sur le calque
      //
      g.drawImage(image, x0, y0, largeur, hauteur, null);  
   }
   
// ------                                              *** Methode chargerImage
    
   private Image chargerImage(String nomImage) {
   Image resultat;
      	
      // Charger l'image cible (format jpeg)
      //
      resultat= Toolkit.getDefaultToolkit().getImage(nomImage); 
   	
      // Construire un media tracker pour controler le chargement de l'image
      //
      MediaTracker mt= new MediaTracker(this);
   	  
      // Attendre la fin du chargement effectif de l'image
      //
      mt.addImage(resultat,0);
      try{mt.waitForAll();}
      catch(Exception e){}
      
     // Restituer le resultat
     //
     return resultat;
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
      if (cheminFichier == null) {return new LinkedList();}
      if (version == null) {return new LinkedList();}
   	
      // Charger tous les composants graphiques d'un calque preexistant
      //
      Object data= Data.load(cheminFichier, version);
      if (data == null) {return new LinkedList();}
      
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
      HashMap composant= null;
      
      Iterator i= ((LinkedList)data).iterator();
     
      while (i.hasNext()) {
      	
         //  Ajouter le composant graphique courant
         //
         composant= (HashMap) i.next();
         ajouterComposant(composant);
      }
      
      // Restituer le resultat dans le cas nominal
      //
      return true;
   }
}
