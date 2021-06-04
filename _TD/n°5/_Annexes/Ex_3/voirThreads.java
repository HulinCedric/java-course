
   
   // ------                                            *** Methode voirThreads  
   // 
   private static void voirThreads(int etape) {
   Thread[] ta = new Thread[Thread.activeCount()];
   int nbThreads = Thread.enumerate(ta);
   
      System.out.println("");  
      System.out.println("=> [" + etape + "] Nombre de threads= "+nbThreads);
      
      for (int i=0; i<nbThreads; i++) {
         System.out.println("Le thread "+ (i+1) + " est : " + ta[i].getName());
         System.out.println("Priorite= " + ta[i].getPriority());
      }
      
      System.out.println("");  
   }
 
   
