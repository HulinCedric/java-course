//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Applications graphiques
//
// Classe Fleurs - Description d'un diaporama de fleurs
//
// Edition A    : diaporama decrit par un dictionnaire
//
//    + Version 1.0.0  : version initiale pour TP_4 / Ex_4
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.util.*;

public class Fleurs {
	
   // Description des fleurs
   //   	
   public static LinkedHashMap donnees() {
   LinkedHashMap data= new LinkedHashMap();
      	
      data.put("../_Images/Fleurs/Rose.jpg",       "Rose");
      data.put("../_Images/Fleurs/Campanules.jpg", "Campanule");
      data.put("../_Images/Fleurs/Tournesol.jpg",  "Tournesol");
      data.put("../_Images/Fleurs/Zinia.jpg",      "Zinia");
      data.put("../_Images/Fleurs/Hibiscus.jpg",   "Hibiscus");
      data.put("../_Images/Fleurs/Gaillardes.jpg", "Gaillarde");
         
      return data;
   }
   
   public static void main(String[] args) {
   LinkedHashMap data= donnees();
   HashMap w= null;
   
      Data.store(data, "../Fleurs", "1.0.0");
      w= (HashMap)Data.load("../Fleurs", "1.0.0");
   }
}
