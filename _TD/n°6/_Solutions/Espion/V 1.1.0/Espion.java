//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010
//
// Classe Espion - classe permetant d'espionner des threads
//                                       
//  	+ Version 1.0.0	: Version initial
//		+ Version 1.1.0	: Ajout d'une methode observer permettant
//						  de rentrer les donnees des Thread actif,
//						  hormis celui de l'espion, dans un dictionnaire
//
//
// Auteur : C. Hulin
//
import java.util.HashMap;
import java.util.Iterator;

public class Espion implements Runnable {
	private HashMap dico;


	// ------                                     	*** Constructeur par defaut

	public Espion () {

		dico = new HashMap();
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
	Iterator it;

		observer();

		System.out.println();  
		System.out.println("=> [" + etape + "] Nombre de threads= " + nbThreads);

		it = dico.keySet().iterator();
		while (it.hasNext())
		{
			String cle= (String) it.next();
			System.out.println("["+ cle + "][" + dico.get(cle) + "]");
		}
		System.out.println();  
	}

	// ------                                		*** Methode observer

	private void observer() {
	Thread[] ta = new Thread[Thread.activeCount()];
	int nbThreads = Thread.enumerate(ta);

		// Vider le dictionnaire des donnees
		// des anciennes donnees
		//
		dico.clear();

		// Parcourir les Thread actif
		//
		for (int i=0; i<nbThreads; i++) {

			// Entrer les donnees des Thread hormis ceux de l'espion
			//
			if (!ta[i].getName().equals(Thread.currentThread().getName()))
				dico.put(ta[i].getName(), new Integer(ta[i].getPriority()));
		}
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
