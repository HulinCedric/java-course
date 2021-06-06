//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package AWT
//
// Classe Fleurs - Description d'un diaporama de fleurs
//
// Edition A    : diaporama decrit par un dictionnaire
//
//    + Version 1.0.0  : version initiale (pour TP_5 / Ex_2)
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Fleurs {
	
   // Description des images
   //   	
   public static LinkedHashMap donnees() {
   LinkedHashMap data= new LinkedHashMap();
      	
      data.put("../_Images/Fleurs/Campanules.jpg",      "Les campanules");
      data.put("../_Images/Fleurs/Coquelicot.jpg",      "Le coquelicot");
      data.put("../_Images/Fleurs/Gaillardes.jpg",      "Trois gaillardes");
      data.put("../_Images/Fleurs/Hibiscus.jpg",        "L'hibiscus");
      data.put("../_Images/Fleurs/Liseron.jpg",         "Un liseron");
      data.put("../_Images/Fleurs/Rince bouteille.jpg", "Un rince bouteille");
      data.put("../_Images/Fleurs/Rose.jpg",            "Une rose");
      data.put("../_Images/Fleurs/Tournesol.jpg",       "Un champ de tournesols");
      data.put("../_Images/Fleurs/Zinia.jpg",           "Un zinia");
        
      return data;
   }
   
   public static void main(String[] args) {
   LinkedHashMap data= donnees();
   HashMap w= null;
   
      Data.store(data, "Fleurs", "1.0.0");
      w= (HashMap)Data.load("Fleurs", "1.0.0");
   }
}
