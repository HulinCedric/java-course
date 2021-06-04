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
      f.setTitle("((SWING) Classe MosaiqueG - V 1.1.0");
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
      
      // Permuter les deux images de la diagonale principale
      //     
      mosaique.permuterImages(new Dimension(1, 1), new Dimension(2, 2));
      
      // Retirer une image de la diagonale secondaire
      //
      mosaique.retirerImage(1, 2);
      
      // Permuter les deux images de la diagonale secondaire
      //     
      mosaique.permuterImages(new Dimension(1, 2), new Dimension(2, 1));
      
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
   
// ------                                         *** Methode permuterImages
   
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
   
   // -------------------------------------------  *** Classe interne Position
   
   public class Position extends Dimension {
   private boolean status;
   
      public Position (int ligne, int colonne) {
      	
         super(ligne, colonne);
      	
         // Controler la validite des parametres
         //
         if (nbLignes <= 0 || nbColonnes <= 0 )    {status= false; return;}
         if (ligne <= 0 || ligne > nbLignes)       {status= false; return;}
         if (colonne <= 0 || colonne > nbColonnes) {status= false; return;}
         
         // Valider le status
         //
         status= true;
      }
      
      private boolean defaut() {return status;}
   } 
} 
