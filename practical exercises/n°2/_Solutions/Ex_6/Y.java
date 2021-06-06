//
// Annee 2009_2010 - Module Java - Feuille_4
//
// Edition A : spécifications initiale de la classe Y
//
//    + Version 1.0.0 : version initiale
//
import java.util.*;

public class Y {
private HashMap config;

   public Y(HashMap config) {this.config= config;}

   public int compter () {

   Iterator i= config.keySet().iterator();
   String cle, type;
   Object associe;
   int resultat= 0;

      while (i.hasNext()) {
         cle    = (String)i.next();
         associe= config.get(cle);
         // System.out.println(associe.getClass().getName());
         type   = associe.getClass().getName();
         if (associe instanceof LinkedHashMap)/*type.equals("java.util.LinkedHashMap")*/ resultat++;
      }
      
      return resultat;
   }
   
   public static void main(String[] args) {
   	
      // Construire un dictionnaire de test
      //
      HashMap test= new HashMap();
     
      test.put("longueur", new Double(200));
      test.put("largeur", new Double(100));
      test.put("panneaux", new HashMap());
      test.put("menus", new LinkedHashMap());

      // Test de la methode compter
      //
      int compteur;
      Y cible= new Y(test);
      compteur= cible.compter();
      System.out.println("Cardinal= "+compteur);
   }
}
