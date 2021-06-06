//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Applications graphiques
//
// Classe TimerPanneauGG - Attente parametrable de N secondes
//
// Edition A 		: Attente par suspension du thread courant
//    + Version 1.0.0	: version initiale
//
// Auteur : A. Thuaire
//

public class TimerPanneauG {

   // Attendre N secondes en bloquant le thread courant 
   //
   public static void attendre (int duree) {
      try {Thread.currentThread().sleep(duree*1000);} 
      catch(InterruptedException e){ }
   }
 
   public static void main(String[] args) {
   	
      // Visualiser un premier message
      //
      System.out.println("Prochain message dans une seconde .");
      TimerPanneauG.attendre(1);
      System.out.println("Prochain message dans deux secondes ..");
      TimerPanneauG.attendre(2);
      System.out.println("Prochain message dans trois secondes ...");
      TimerPanneauG.attendre(3);
      System.out.println("Prochain message dans quatre secondes ....");
      TimerPanneauG.attendre(4);
      System.out.println("La fin dans cinq secondes ....");
      TimerPanneauG.attendre(5);
      System.out.println("FIN !!!!!");
   }
}
