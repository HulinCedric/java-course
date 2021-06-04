//
// Annee 2009_2010 - Module Java - Charte tests unitaires / Tests.java
//
// Utilitaires de tests - Classe Tests - Version 2.1.0
//
// Version 2.0.0    : version initiale complète
// Version 2.0.1    : formattage des commentaires pour impression
// Version 2.1.0    : correction du bug verboseLevel (ligne 111 ) +
//                    correction du bug statusDesign (ligne 100 ) +
//                    la classe devient abstraite
//
/**
 *
 * La classe Tests fournit un ensemble complet de services destinés à 
 * simplifier et à uniformiser la mise en oeuvre de la charte de 
 * réalisation des modules de tests unitaires exposée en cours. La 
 * classe est également utilisée en TD comme un exemple très concret 
 * de codes sources "écrits par d'autres".
 *
 * Les services fournis sont :
 *
 * Begin    : début d'exécution des tests unitaires de la classe cible,
 * Design   : exécuter un "test design",
 * Case     : exécuter un "test case",
 * Unit     : exécuter un "test unit",
 * End      : fin d'exécution des tests unitaires de la classe cible.
 *
 * @author Alain Thuaire
**/
abstract public class Tests {
private static int levelDesign;		// Numero d'ordre du test_design 
private static int levelCase;		// Numero d'ordre du test_case
private static int levelUnit;		// Numero d'ordre du test_unit
private static int verboseLevel;	// Niveau courant de visualisation
private static int statusDesign;	// CR du test_design courant
private static int statusCase;		// CR de test_case courant
private static boolean testBegin;	// Indicateur execution Begin
private static String  designName;	// Nom du test design courant;
private static String  caseName;	// Nom du test case courant;

/**
 *
 * La méthode Begin visualise la ligne d'entête du module de tests 
 * unitaires.
 * 
**/
   public static void Begin (String name, String version) {
      visuEntete(name, version);	// Visualiser entete des tests
      testBegin=true;			// Service Begin execute
   }

/**
 *
 * La méthode End visualise d'abord le compte rendu (CR) du dernier 
 * "test case" et/ou du dernier "test design" du module, en fonction 
 * du niveau de visualisation courant (attribut verboseLevel).
 *
 * Seuls les CR non nuls sont visualisés.
 *
 * La méthode End visualise ensuite la ligne de clôture du module de 
 * tests unitaires, si et seulement si ce dernier s'est terminé 
 * globalement avec succès. Dans la charte, un "test design" (y compris
 * le dernier) n'est exécuté que si le précédent se termine avec succès 
 * (CR=0).
 * 
**/
   public static void End () {
   String lastLine= "\n------------------------------------------------------------\n";
      
      // Visualiser le CR dernier test case et dernier test design
      //
      visuCR("Case"); visuCR("Design"); 
      
      // Visualiser la ligne de cloture des tests unitaires
      //
      if (statusDesign == 0) System.out.println(lastLine);
      else System.out.println();
      
      // Gerer le cas du dernier test design en anomalie
      //
      if (statusDesign != 0) System.exit(statusDesign);
   }

/**
 *
 * La méthode Design visualise d'abord le compte rendu (CR) du dernier 
 * "test case" et/ou du dernier "test design" exécutés, en fonction du 
 * niveau de visualisation courant (attribut verboseLevel).
 *
 * La méthode renseigne ensuite les attributs du "test design" et le 
 * niveau requis de visualisation (second paramètre).
 *
 * La méthode visualise pour terminer la ligne d'entête des "test 
 * design".
 * 
**/
   public static void Design (String name, int level) {
   	
      // Controler les conditions initiales requises par la charte
      //
      if (!valid("Begin")) System.exit(1);
      
      // Visualiser le CR du test case et du test design precedents
      //
      visuCR("Case"); visuCR("Design"); 
      
      // Controler que le status OK du test design precedent
      //
      if (statusDesign != 0) System.exit(1);

      // Initialiser les attributs du test design
      //
      levelCase   =0;
      statusCase  =0;
      statusDesign=0;
      designName  =name;
      levelDesign++;
      
      // Controler et affecter le nouveau niveau de visualisation
      //
      verboseLevel=level;
      if (level > 3) verboseLevel=3;
      if (level < 0) verboseLevel=0;
      
 
      // Visualiser la ligne d'entete du nouveau test design
      //   
      visuEntete(name);
   }
/**
 *
 * La méthode Case visualise d'abord le compte rendu (CR) du dernier
 * "test case" exécuté, en fonction du niveau de visualisation courant 
 * (attribut verboseLevel).
 *
 * La méthode renseigne ensuite les attributs du "test case".
 *
 * La méthode visualise pour terminer la ligne d'entête des "test cases".
 * 
**/
   public static void Case (String name) {
      
      // Controler les conditions initiales requises par la charte
      //
      if (!valid("Begin") || !valid("Design"))  System.exit(1);

      // Visualiser le CR du dernier test case execute
      //  
      visuCR("Case");
      
      // Initialiser les attributs du nouveau test case
      // 
      caseName=name;
      levelCase++;
      
      // Visualiser la ligne d'entete du nouveau test case
      //  
      visuEntete();
   }
 
 /**
 *
 * La méthode Unit visualise d'abord les deux messages standard d'un 
 * test elementaire, en fonction du niveau de visualisation courant 
 * (attribut verboseLevel).
 *
 * La méthode elabore pour terminer les CR du "test case" et du "test
 * design" courant. 
 *
**/ 
   public static void Unit (Object attendu, Object obtenu) {

      // Controler les conditions initiales requises par la charte
      //
      if (!valid("Begin") || !valid("Design") || !valid("Case")) System.exit(1);	  
  
      // Visualiser les messages du test de plus niveau
      // 
      visuEntete(attendu, obtenu); 	  

      // Elaborer le CR du test case courant
      //  
      if (statusCase == 0 && !obtenu.equals(attendu)) statusCase=levelCase;
      else statusCase=0;
      
      // Elaborer le CR du test design courant
      //  
      if (statusDesign == 0 && statusCase != 0) statusDesign=statusCase;
   }
/**
 *
 * La méthode privée valid vérifie les conditions d'exécution du niveau 
 * de tests passé en paramètre.
 * 
**/
   private static boolean valid (String level) {
   String headText   = "\n*** Erreur fatale (Tests) : ";
   String errorBegin = "Begin requis au prealable\n";
   String errorDesign= "Design requis au prealable\n";
   String errorCase  = "Case requis au prealable\n";
   
      // Controler l'execution prealable du service Begin
      //
      if (level.equals("Begin") && !testBegin) {
         System.out.println( headText + errorBegin);
         return false;
      }
      
      // Controler l'execution prealable du service Design
      //   
      if (level.equals("Design") && levelDesign == 0) {
         System.out.println(headText + errorDesign);
         return false;
      }
      
      // Controler l'execution prealable du service Case
      // 
      if (level.equals("Case") && levelCase == 0) {
         System.out.println(headText + errorCase);
         return false;
      }
      
      return true;  
   }

/**
 *
 * La méthode privée visuEntete visualise la ligne d'entete du niveau
 * de description fixé par sa signature, en fonction du niveau de 
 * visualisation courant (attribut verboseLevel).
 * 
**/
   private static void visuEntete (String name, String version) {
   String beginPart  = "\n--- Tests unitaires de la classe ";
   String versionPart= " - Version ";
   String endPart    = " ---\n\n";
      
      System.out.print(beginPart);
      System.out.print(name + versionPart + version);
      System.out.print(endPart);
   }
  
   private static void visuEntete (String name) {
   String beginPart = "\n** [";
   String endPart   = "]\n";
   
      if (verboseLevel > 0) System.out.print(beginPart + name + endPart);
   }
   
   private static void visuEntete () {
   String beginPart = "\n   + (";
   String endPart   = ")\n";
   
      if (verboseLevel > 1) System.out.print(beginPart + caseName + endPart);
   }
   
   private static void visuEntete (Object attendu, Object obtenu) {
   String beginPart = "\n     Valeur attendue : ";
   String middlePart= "\n     Valeur obtenue  : ";
   String LF        = "\n";
   
      if (verboseLevel > 2) {
         System.out.print(beginPart + attendu);
         System.out.print(middlePart + obtenu + LF);
      }
   }
   
 /**
 *
 * La méthode privée visuCR visualise le compte rendu courant du niveau
 * de tests fourni en paramètre.
 * 
**/
   private static void visuCR (String level) {
   String beginPartCase ="     => CR= ";
   String beginPartDesign = "=> CR= ";
   String LF   = "\n";
   
      // Visualiser le CR du dernier test case execute
      //
      if (level.equals("Case") && levelCase > 0 && verboseLevel > 1 && statusCase != 0) {
         if (verboseLevel == 2) System.out.print(beginPartCase + statusCase + LF);
         else System.out.print(LF + beginPartCase + statusCase + LF);
      }  	
      
      // Visualiser le CR du dernier test design execute
      //
      if (level.equals("Design")&&verboseLevel >0 &&statusDesign != 0) {
      	 if (verboseLevel == 1 && statusDesign != 0) System.out.print(beginPartDesign + statusDesign + LF);
         else System.out.print(LF + beginPartDesign + statusDesign + LF);
      }
   }
   
/**
 *
 * Module de tests unitaires de la classe Tests.
 * 
**/

   public static void main (String[] args) {
        
      Begin("Tests", "2.0.0");

      Design("Visualisation de niveau 0", 0);
         
         Case("C1 : 2 TU OK");			
            Unit("x", "x");
            Unit(new Integer(0), new Integer(0));
      			       
      Design("Visualisation de niveau 1", 1);		
  
         Case("C1 : 1 TU OK");
            Unit("niveau 1", "niveau 1");
            Unit(new Integer(1), new Integer(1));

      Design("Visualisation de niveau 2", 2);		
  
         Case("C1 : 2 TU OK");
            Unit("xxx", "xxx");
            Unit(new Integer(2), new Integer(2));

         Case("C2 : 3 TU OK");
            Unit("niveau 1", "niveau 1");
            Unit(new Integer(1), new Integer(1));
            Unit(new Integer(-1), new Integer(-1));  

      Design("Visualisation de niveau 3", 3);		
  
         Case("C1 : 3 TU OK");
            Unit("......", "......");
            Unit(new Integer(3), new Integer(3));
            Unit(new Float(3), new Float(3));

         Case("C2 : 2 TU OK");
            Unit("xxxxx", "xxxxx");
            Unit(new Integer(-3), new Integer(-3)); 
            
         Case("C3 : 1 TU OK");
            Unit(new Float(-3), new Float(-3));  
      
      Design("Visualisation de niveau 3 avec TU NOK", 3);		
  
         Case("C1 : 2 TU OK");
            Unit("......", "......");
            Unit(new Integer(4), new Integer(4));

         Case("C2 : 1 TU NOK");
            Unit(new Float(-4.0), new Float(-4.01)); 
            
         Case("C3 : 1 TU OK");
            Unit(new Double(-4.25), new Double(-4.25));
             
      End();

   }
}
