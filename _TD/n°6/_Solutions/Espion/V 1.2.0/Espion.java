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
//		+ Version 1.2.0	: Ajout d'un dictionnaire secondaire regrouppant
//						  toutes les proprietes des threads espiones
//
//
// Auteur : C. Hulin
//
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

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
	Iterator it1;
	Iterator it2;

		observer();

		System.out.println();  
		System.out.println("=> [" + etape + "] Nombre de threads= " + nbThreads);

		it1 = dico.keySet().iterator();
		while (it1.hasNext())
		{
			String cle1= (String) it1.next();
			System.out.print("["+ cle1 + "]");
			it2= ((LinkedHashMap) dico.get(cle1)).keySet().iterator();
			while (it2.hasNext())
			{
				String cle2= (String) it2.next();
				System.out.print("["+ ((LinkedHashMap) dico.get(cle1)).get(cle2) + "]");
			}
			System.out.println();
		}
		System.out.println();  
	}

	// ------                                		*** Methode observer

	private void observer() {
	Thread[] ta = new Thread[Thread.activeCount()];
	int nbThreads = Thread.enumerate(ta);
	LinkedHashMap proprietes;

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
			{
				proprietes= new LinkedHashMap();
				
				proprietes.put("Priority", new Integer(ta[i].getPriority()));
				proprietes.put("ThreadGroup", ta[i].getThreadGroup().getName());
				proprietes.put("Daemon", new Boolean(ta[i].isDaemon()));
			
				dico.put(ta[i].getName(), proprietes);
			}
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
