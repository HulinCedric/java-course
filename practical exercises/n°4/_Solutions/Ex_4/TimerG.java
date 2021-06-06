//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2008_2009 - Package SWING
//
// Classe TimerG - Attente parametrable de N secondes
//
// Edition A 		: Attente par suspension du thread courant
//    + Version 1.0.0	: 
//
// Auteur : A. Thuaire
//

public class TimerG {

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
      TimerG.attendre(1);
      System.out.println("Prochain message dans deux secondes ..");
      TimerG.attendre(2);
      System.out.println("Prochain message dans trois secondes ...");
      TimerG.attendre(3);
      System.out.println("Prochain message dans quatre secondes ....");
      TimerG.attendre(4);
      System.out.println("La fin dans cinq secondes ....");
      TimerG.attendre(5);
      System.out.println("FIN !!!!!");
      	
   }

}
