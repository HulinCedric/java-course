//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Applications graphiques
//
// Classe Paysages - Description d'un diaporama de paysages
//
// Edition A    : diaporama decrit par un dictionnaire
//
//    + Version 1.0.0  : version initiale pour TP_4 / Ex_3
//
// Auteur : A. Thuaire
//

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Paysages {
	
   // Description des images
   //   	
   public static LinkedHashMap donnees() {
   LinkedHashMap data= new LinkedHashMap();
      	
      data.put("../_Images/Paysages/Aiguille.jpg",  "Le mont Aiguille (Vercors)");
      data.put("../_Images/Paysages/Meige.jpg",     "Le pic de la Meige (Oisans)");
      data.put("../_Images/Paysages/Thabor.jpg",    "Le mont Thabor (Briançonnais)");
      data.put("../_Images/Paysages/Noir.jpg",      "Le lac Noir");
      data.put("../_Images/Paysages/Neuvache.jpg",  "La vallée de neuvache (Briançonnais)");
      data.put("../_Images/Paysages/Rochilles.jpg", "La barre des rochilles (Galibier)");
         
      return data;
   }
   
   public static void main(String[] args) {
   LinkedHashMap data= donnees();
   HashMap w= null;
   
      Data.store(data, "../Paysages", "1.0.0");
      w= (HashMap)Data.load("../Paysages", "1.0.0");
   }
}
