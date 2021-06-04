//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010
//
// Classe Espion - classe permetant d'espionner des threads
//                                       
//    + Version 1.0.0	: version initial
//
// Auteur : C. Hulin
//
public class Espion implements Runnable {

	// ------                                     	*** Constructeur par defaut

	public Espion () {

	}

	// ------                                    	*** Main

	public static void main(String[] args) {

		Thread t1 = new Thread(new Espion());
		t1.start();
		Chrono.attendre(3000);
	}  

	// ------                                 		*** Methode run  

	public void run() { 	 
	int i=1;
		while (true) {
			
			voirThreads(i++);
			Chrono.attendre(2000);
		}
	}


	// ------                                		*** Methode voirThreads  

	private void voirThreads(int etape) {
	Thread[] ta = new Thread[Thread.activeCount()];
	int nbThreads = Thread.enumerate(ta);

		System.out.println();  
		System.out.println("=> [" + etape + "] Nombre de threads= " + nbThreads);

		for (int i=0 ; i < nbThreads ; i++) {

			System.out.println("[" + ta[i].getName() + "][" + ta[i].getPriority() + "]");
		}
		System.out.println();  
	}

	// -------------------------------------      *** Classe interne privee Chrono

	private static class Chrono {

		private static void attendre (int tms) {

			// Attendre tms millisecondes, en bloquant le thread courant 
			//
			try 
			{
				Thread.currentThread();
				Thread.sleep(tms);
			} 
			catch(InterruptedException e){ }
		}
	}

}
