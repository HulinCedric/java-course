//
// Annee 2008_2009 - Module S3 - Feuille_3
//
// Visualisation des parametres de la ligne de commande 
//
public class Ex1 {

   public static void main (String[] args) {          
      
      for (int i=0; i<args.length; i++) 
         System.out.println("P" + (i+1) +"= " + args[i]);
   }

}
