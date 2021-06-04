//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe MosaiqueG - Matrice de cellules (panneaux) generiques
//
// Edition A    : Cours_10
//    + Version 1.0.0	: chaque cellule supporte les homotheties de taille du
//                        cadre support, avec une repartition (ligne, colonne)
//                        constante
//    + Version 1.1.0   : ajout de la methode permuterImages
//    + Version 1.2.0   : ajout des methodes suivantes :
//                           + obtenirPositionPCL, 
//                           + obtenirPositionCLS,
//                           + obtenirPositionPCO, 
//                           + obtenirPositionCOS
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.awt.*;
import javax.swing.*;
  
public class MosaiqueG extends PanneauG {
private JFrame        cadreSupport;
private HashMap       config;
private LayoutManager gestPlacement;
private int           nbLignes;
private int           nbColonnes;          
private CelluleG[][]  matriceCellules;
   
// ------                                      *** Premier constructeur normal
   
   public MosaiqueG (JFrame hamecon, HashMap config, int nbLignes, 
                                                     int nbColonnes) {
      super(hamecon, config);
                                   	
      // Controler la validite des parametres
      //
      if (hamecon    == null) return;
      if (nbLignes   <= 0)    return;
      if (nbColonnes <= 0)    return;
     
      // Memoriser les attributs transmis par parametre
      //
      cadreSupport   = hamecon;
      config         = config;
      this.nbLignes  = nbLignes;
      this.nbColonnes= nbColonnes;
      
      // Installer le gestionnaire de placement du panneau principal
      //
      GridLayout gestPlacement= new GridLayout(nbLignes, nbColonnes);
      setLayout(gestPlacement);
         
      // Creer la matrice des cellules internes
      //
      matriceCellules= new CelluleG[nbLignes][nbColonnes];
      
      // Remplir cette matrice et ajouter chaque panneau secondaire au
      // panneau principal
      //
      CelluleG celluleCible;
      for (int i= 0; i<nbLignes; i++) { 
         for (int j= 0; j< nbColonnes; j++) {
         	
            // Construire la cellule cible
            //
            celluleCible= new CelluleG(this, config, new Dimension(i+1, j+1));
         	
            // Affecter la nouvelle cellule a la matrice
            //
            matriceCellules[i][j]= celluleCible;
         }
      }      
   }
   
   // ------                                               *** Methode main
     
   public static void main(String[] args) {
   	
      // Creer un cadre support
      //   	
      JFrame f= new JFrame();
      f.setTitle("((SWING) Classe MosaiqueG - V 1.2.0");
      f.setSize(500, 300);
      f.setLocation(350, 350);
      
      // Charger le dictionnaire de configuration de la mosaique
      //   	
      HashMap config= (HashMap)Config.load("ConfigPanneauG", "1.0.0");
          
      // Creer une mosaique 2x2
      //
      MosaiqueG mosaique= new MosaiqueG(f, config, 2, 2);
      
      // Ajouter quatre images
      //
      mosaique.ajouterImage(1, 1, "../_Images/Fleurs/Rose.jpg");
      mosaique.ajouterImage(1, 2, "../_Images/Fleurs/Liseron.jpg");
      mosaique.ajouterImage(2, 1, "../_Images/Fleurs/Hibiscus.jpg");
      mosaique.ajouterImage(2, 2, "../_Images/Fleurs/Zinia.jpg");
      
      // Retirer une des premieres images 
      //
      mosaique.retirerImage(1, 1);
      //mosaique.retirerImage(1, 2);
      //mosaique.retirerImage(2, 1);
     
      // Obtenir la position de la Premiere Cellule Occupee (PCO) 
      //
      Position positionPCO= mosaique.obtenirPositionPCO();
      System.out.println("\nPosition PCO= " + positionPCO);
      
      // Obtenir la position de la Premiere Cellule Libre (PCL) 
      //
      Position positionPCL= mosaique.obtenirPositionPCL();
      System.out.println("Position PCL= " + positionPCL);
      
      // Obtenir la position de la Cellule Occupee Suivante (COS)  
      //
      positionPCO= mosaique.obtenirPositionCOS(positionPCL.suivante());
      System.out.println("Position COS= " + positionPCO);
      
      // Ajouter la mosaique au cadre support
      //
      f.getContentPane().add(mosaique);
      
      // Montrer le cadre support et sa mosaique interne
      //
      f.show();	
   }
   
// ------                                        *** Accesseurs de consultation
   
   public int obtenirNbLignes()   {return nbLignes;}
   public int obtenirNbColonnes() {return nbColonnes;}
   public int obtenirNbCellules() {return nbLignes*nbColonnes;}  
   public int obtenirNbImages()   {
   PanneauG panneauCourant;
   
      // Parcourir l'ensemble des cellules
      //
      int      nbImages= 0;
      for (int i= 0; i<nbLignes; i++) {
         for (int j= 1; j<nbColonnes; j++) {
         	
            // Obtenir la cellule courante
            //
            panneauCourant= matriceCellules[i][j];
            
            // Determiner la presence ou pas d'une image
            //
            if (panneauCourant.presenceImage()) nbImages++;
         }
      }
      
      // Restituer le resultat
      //
      return nbImages;
   }  
      
// ------                                             *** Methode ajouterImage
   
   public boolean ajouterImage(int ligne, int colonne, String cheminImage) {
      	
      // Controler la validite des parametres
      //
      if (ligne < 1 || ligne > nbLignes)       return false;
      if (colonne < 1 || colonne > nbColonnes) return false;
      if (cheminImage == null)                 return false;
         
      // Ajouter l'image a la cellule cible
      //
      CelluleG celluleCible = matriceCellules[ligne-1][colonne-1];
      celluleCible.ajouterImage(cheminImage);
      
      return true;
   }
      
// ------                                             *** Methode retirerImage
   
   public boolean retirerImage(int ligne, int colonne) {
      	
      // Controler la validite des parametres
      //
      if (ligne < 1 || ligne > nbLignes) return false;
      if (colonne < 1 || colonne > nbColonnes) return false;
         
      // Ajouter l'image au panneau cible
      //
      CelluleG celluleCible = matriceCellules[ligne-1][colonne-1];
      celluleCible.retirerImage();
      
      return true;
   }
   
// ------                                            *** Methode permuterImages
   
    public void permuterImages(Dimension p1, Dimension p2) {
   int i1, i2, j1, j2;     
   
      // Detailler les coordonnees des deux cellules cibles
      //
      i1= (int)p1.getWidth()  - 1;
      j1= (int)p1.getHeight() - 1;
      i2= (int)p2.getWidth()  - 1;
      j2= (int)p2.getHeight() - 1;
      
      // Obtenir les cellules cibles
      //
      CelluleG cellule_1= matriceCellules[i1][j1];
      CelluleG cellule_2= matriceCellules[i2][j2];
      
      // Determiner la presence d'images dans les cellules cibles
      //
      boolean presenceImage_1= cellule_1.presenceImage();
      boolean presenceImage_2= cellule_2.presenceImage();
      
      // Recueillir les chemins d'acces aux images presentes
      //
      String cheminImage_1= null, cheminImage_2= null;
      if (presenceImage_1) cheminImage_1= cellule_1.obtenirCheminImage();
      if (presenceImage_2) cheminImage_2= cellule_2.obtenirCheminImage();
      
      // Retirer les anciennes images des panneaux sous jacents
      //
      if (presenceImage_1) matriceCellules[i1][j1].retirerImage();
      if (presenceImage_2) matriceCellules[i2][j2].retirerImage(); 
      
      // Ajouter les nouvelles images dans les panneaux sous jacents
      //
      if (presenceImage_2) matriceCellules[i1][j1].ajouterImage(cheminImage_2);
      if (presenceImage_1) matriceCellules[i2][j2].ajouterImage(cheminImage_1);
   }	
   
// ------                                      *** Methode obtenirPositionPCL
   
   public Position obtenirPositionPCL() {
      return obtenirPositionCLS(new Position(1, 1));
   }
   
// ------                                      *** Methode obtenirPositionCLS
   
   public Position obtenirPositionCLS(Dimension positionDepart) {
   CelluleG celluleCourante;
   
      // Controler la validite du parametre
      //
      if (positionDepart == null) return null;
       
      // Controler la validite de la ligne de depart
      //
      int ligneDepart  = (int)positionDepart.getWidth();
      if (ligneDepart < 1 || ligneDepart > nbLignes) return null;
       
      // Controler la validite de la colonne de depart
      //
      int colonneDepart= (int)positionDepart.getHeight();
      if (colonneDepart < 1 || colonneDepart > nbColonnes) return null;
       
      // Parcourir la matrice depuis la position de depart
      //
      int xDepart= ligneDepart -1;
      int yDepart= colonneDepart -1;
      
      for (int i= xDepart; i<nbLignes; i++) {
         for (int j= yDepart; j<nbColonnes; j++) {
         	
         	// Obtenir la cellule courante
         	//
            celluleCourante= matriceCellules[i][j];
            
            // Determiner presence ou pas d'une image dans la cellule courante
            //
            if (!celluleCourante.presenceImage()) return new Position(i+1, j+1);
         }
      }
      
      // Restituer le resultat en cas de saturation
      //
      return new Position(0,0);
   }
   
// ------                                       *** Methode obtenirPositionPCO
   
   public Position obtenirPositionPCO() {
      return obtenirPositionCOS(new Position(1, 1));
   }
   
// ------                                       *** Methode obtenirPositionCOS
   
   public Position obtenirPositionCOS(Position positionDepart) {
   CelluleG celluleCourante;
   
      // Controler la validite du parametre
      //
      if (positionDepart == null) return null;
      
      // Controler la validite de la ligne de depart
      //
      int ligneDepart  = (int)positionDepart.getWidth();
      if (ligneDepart < 1 || ligneDepart > nbLignes) return null;
       
      // Controler la validite de la colonne de depart
      //
      int colonneDepart= (int)positionDepart.getHeight();
      if (colonneDepart < 1 || colonneDepart > nbColonnes) return null;
       
      // Parcourir la matrice depuis la position de depart
      //
      int xDepart= ligneDepart -1;
      int yDepart= colonneDepart -1;
         
      for (int i= xDepart; i<nbLignes; i++) {
         for (int j= yDepart; j<nbColonnes; j++) {
         	         	
         	// Obtenir la cellule courante
         	//
            celluleCourante= matriceCellules[i][j];
            
            // Determiner presence ou pas d'une image dans la cellule courante
            //
            if (celluleCourante.presenceImage()) return new Position(i+1, j+1);
         }
      }
      
      // Restituer le resultat d'un solde vide
      //
      return new Position(0,0);
   }
   
   // -------------------------------------------  *** Classe interne Position
   
      public class Position extends Dimension {
      private boolean status;
   
         public Position (int ligne, int colonne) {
      	
            super(ligne, colonne);
      	
            // Controler la validite des parametres
            //
            if (ligne <= 0 || ligne > nbLignes)       {status= false; return;}
            if (colonne <= 0 || colonne > nbColonnes) {status= false; return;}
         
            // Valider le status
            //
            status= true;
         }
      
     // ------                                         *** Methode valide
         
        public boolean valide() {return status;}
        
     // ------                                         *** Methode suivante
      
        public Position suivante() {
      	
      	   // Obtenir les coordonnees de la position support
      	   //
      	   int x= (int)getWidth();
      	   int y= (int)getHeight();
      	
      	   // Restituer le resultat
      	   //
      	   if (x == nbLignes && y == nbColonnes) return new Position(0,0);
      	   if (y < nbColonnes) return new Position(x, y+1);
      	   return new Position(x+1, 1);
        }
     } 
} 
