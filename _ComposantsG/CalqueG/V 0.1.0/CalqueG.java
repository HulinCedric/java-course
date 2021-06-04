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
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class CalqueG extends PanneauG {
private LinkedList composants;

// ------                                               *** Constructeur normal

   public CalqueG (Object hamecon, HashMap config) {
   	
      // Invoquer le constructeur de la classe mere
      //
      super(hamecon, config);
      
      // Initialiser la liste des composants graphiques preexistants
      //
      composants= new LinkedList(); 
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
      
      // Mettre en place un premier cadre support
      //
      JFrame f1= new JFrame();
      
      f1.setTitle("(SWING) Classe CalqueG - V 0.1.0 (C1)");
      f1.setSize(500, 300);
      f1.setLocation(50, 50);
      
      // Charger un premier dictionnaire de configuration
      //   	
      HashMap config_1= (HashMap) Config.load("Panneau_A", "0.0.0");
     
      // Creer un premier calque et l'ajouter au cadre support
      //
      CalqueG calque_1= new CalqueG(f1, config_1);
      f1.getContentPane().add(calque_1);
      
      // Montrer le premier cadre support et son calque interne
      //
      f1.show();
      
      // Dessiner un label dans le premier calque
      //
      HashMap description_1= new HashMap();
      
      description_1.put("type",     "Label");
      description_1.put("couleur",  Color.yellow);
      description_1.put("abscisse", new Integer(50));
      description_1.put("ordonnee", new Integer(50));
      description_1.put("label",    "xxx");
      
      calque_1.ajouterComposant(description_1);
      
      // Ajouter une grille dans le premier calque
      //
      HashMap description_2= new HashMap();
      
      description_2.put("type",     "Trame");
      description_2.put("couleur",  Color.red);
      description_2.put("source",   new Integer(20));
      
      calque_1.ajouterComposant(description_2);	
      
      // Dessiner un polygone dans le premier calque
      //
      HashMap description_3= new HashMap();
      
      Polygon source= new Polygon();
      source.addPoint(100, 100);
      source.addPoint(200, 200);
      source.addPoint(40, 400);
      
      description_3.put("type",     "Polygone");
      description_3.put("couleur",  Color.cyan);
      description_3.put("source",   source);
      
      calque_1.ajouterComposant(description_3);
      
      // Enregistrer les trois composants dans un fichier de donnees
      // du repertoire courant
      //
      calque_1.enregistrer("Calque_A", "0.1.0");
      
      // Mettre en place un second cadre support
      //
      JFrame f2= new JFrame();
      
      f2.setTitle("(SWING) Classe CalqueG - V 0.0.0 (C2)");
      f2.setSize(500, 300);
      f2.setLocation(400, 100);
      
      // Charger un second dictionnaire de configuration
      //   	
      HashMap config_2= (HashMap) Config.load("Panneau_B", "0.0.0");
     
      // Creer un second calque et l'ajouter au cadre support
      //
      CalqueG calque_2= new CalqueG(f2, config_2);
      
      f2.getContentPane().add(calque_2);
      
      // Montrer le second cadre support et son calque interne
      //
      f2.show();
      
      // Dessiner un segment dans le second calque
      //
      HashMap description_4= new HashMap();
      
      description_4.put("type",      "Segment");
      description_4.put("abscisse",  new Integer(200));
      description_4.put("ordonnee",  new Integer(150));
      description_4.put("gabarit",   new Dimension(100, 100));
      description_4.put("couleur",  Color.green);
      
      calque_2.ajouterComposant(description_4);
      
      // Dessiner une image dans le second calque
      //
      HashMap description_5= new HashMap();
      
      description_5.put("type",      "Image");
      description_5.put("abscisse",  new Integer(20));
      description_5.put("ordonnee",  new Integer(20));
      description_5.put("gabarit",   new Dimension(100, 100));
      description_5.put("source", "../_Images/Fleurs/Rose.jpg");
      
      calque_2.ajouterComposant(description_5);
      
      // Enregistrer les deux composants dans un fichier de donnees
      // du repertoire courant
      //
      calque_2.enregistrer("Calque_B", "0.1.0");
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
   
      // Obtenir la couleur du segment a dessiner
      //
      Color couleur= (Color)description.get("couleur");
      
      // Modifier la couleur courante de dessin dans le calque
      //
      if (couleur != null) {
         couleurInitiale= getForeground();
         g.setXORMode(couleur);
      } 
      	
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
      if (couleurInitiale != null) g.setXORMode(couleurInitiale);
   }
   
// ------                                             *** Methode dessinerLabel

   private void dessinerLabel(Graphics g, HashMap description) {
         
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
   }

// ------                                             *** Methode dessinerTrame
 
   private void dessinerTrame(Graphics g, HashMap description) {
   Color couleurInitiale= null;
   
      // Obtenir la couleur de la trame a dessiner
      //
      Color couleur= ((Color)description.get("couleur"));
      
      // Modifier la couleur courante de dessin dans le calque
      //
      if (couleur != null) {
         couleurInitiale= getForeground();
         g.setXORMode(couleur);
      }
      
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
      if (couleurInitiale != null) g.setXORMode(couleurInitiale);
   }
         
// ------                                          *** Methode dessinerPolygone

   private void dessinerPolygone(Graphics g, HashMap description) {
   Color couleurInitiale= null;
   
      // Obtenir le polygone a dessiner
      //
      Polygon source= ((Polygon)description.get("source"));
      if (source == null) return;
   
      // Obtenir la couleur des aretes
      //
      Color couleur= ((Color)description.get("couleur"));
      
      // Modifier la couleur courante de dessin dans le calque
      //
      if (couleur != null) {
         couleurInitiale= getForeground();
         g.setXORMode(couleur);
      } 
      	
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
   
   public boolean enregistrer(String chemin, String version) {
   	
      // Controler la validite des parametres
      //
      if (chemin == null) return false;
      if (version == null) return false;
   	
      // Assurer le persistance de tous les composants graphiques du calque
      //
      return Data.store(composants, chemin, version);
   }
}
