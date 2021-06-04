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
//		+ Version 1.3.0	: Ajout de la date absolue du releve des proprietes
//		+ Version 1.4.0	: Ajout de la gestion de fichier configuration
//		+ Version 1.5.0	: Ajout de la methode filtrer qui permet de gerer
//						  la visualisation selon les parametre de configuration
//
// Auteur : C. Hulin
//
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Espion implements Runnable {
private HashMap dico;
private int loop;
private int priorityThread;
private long activation;
private boolean priority;
private boolean threadGroup;
private boolean daemon;
private boolean time;

	// ------                                     	*** Constructeur par defaut

	public Espion () {

		dico = new HashMap();
		loop= 2000;
		priorityThread= 5;
		activation= System.currentTimeMillis();
		priority= true;
		threadGroup= false;
		daemon= false;
		time= false;
	}

	// ------                                     	*** Constructeur normal
	
	public Espion (LinkedHashMap config) {

		dico = new HashMap();
		
		setLoop(config);
		setPriorityThread(config);
		setActivation(config);
		setPriority(config);
		setThreadGroup(config);
		setDaemon(config);
		setTime(config);
	}

	// ------                                     	*** Methode setLoop
	
	private void setLoop(HashMap config) {
	int defaultValue= 2000;
	int value= defaultValue;
	Object w;

		if (config==null) {loop= defaultValue; return;}
		w= config.get("Loop");
		if (w!=null) value =((Integer)w).intValue();
		loop= value;
	}

	// ------                                     	*** Methode setPriorityThread
	
	private void setPriorityThread(HashMap config) {
	int defaultValue= 5;
	int value= defaultValue;
	Object w;

		if (config==null) {priorityThread= defaultValue; return;}
		w= config.get("PriorityThread");
		if (w!=null) value =((Integer)w).intValue();
		priorityThread= value;
	}

	// ------                                     	*** Methode setActivation
	
	private void setActivation(HashMap config) {
	long defaultValue= System.currentTimeMillis();
	long value= defaultValue;
	Object w;

		if (config==null) {activation= defaultValue; return;}
		w= config.get("Activation");
		if (w!=null) value =((Long)w).longValue();
		activation= value;
	}
	
	// ------                                     	*** Methode setPriority

	private void setPriority(HashMap config) {
	boolean defaultValue= true;

		if (config==null) {priority= defaultValue; return;}
		priority= !config.containsKey("Priority");
	}

	// ------                                     	*** Methode setThreadGroup

	private void setThreadGroup(HashMap config) {
	boolean defaultValue= false;

		if (config==null) {threadGroup= defaultValue; return;}
		threadGroup= !config.containsKey("ThreadGroup");
	}

	// ------                                     	*** Methode setDaemon

	private void setDaemon(HashMap config) {
	boolean defaultValue= false;

		if (config==null) {daemon= defaultValue; return;}
		daemon= !config.containsKey("Daemon");
	}
	
	// ------                                     	*** Methode setTime
	
	private void setTime(HashMap config) {
	boolean defaultValue= false;

		if (config==null) {time= defaultValue; return;}
		time= !config.containsKey("Time");
	}

	// ------                                    	*** Main

	public static void main(String[] args) {
	LinkedHashMap config;

		config= (LinkedHashMap) Config.load("ConfigEspion", "1.0.0");

		Thread t1 = new Thread(new Espion(config));
		t1.start();
		Chrono.attendre(3000);
	}  

	// ------                                 		*** Methode run  

	public void run() { 	 
	int i=1;
		
		Thread.currentThread().setPriority(priorityThread);
	
		while (activation > System.currentTimeMillis());

		while (true) {

			voirThreads(i++);
			Chrono.attendre(loop);
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
				proprietes.put("Time", new Long(System.currentTimeMillis()));	

				dico.put(ta[i].getName(), proprietes);
			}
		}
		
		filtrer();
	}
	
	// ------                                		*** Methode filtrer

	private void filtrer() {
	Iterator it1;

		it1 = dico.keySet().iterator();
		while (it1.hasNext())
		{
			String cle= (String) it1.next();
			if (!priority)
				((LinkedHashMap) dico.get(cle)).remove("Priority");
			if (!threadGroup)
				((LinkedHashMap) dico.get(cle)).remove("ThreadGroup");
			if (!daemon)
				((LinkedHashMap) dico.get(cle)).remove("Daemon");
			if (!time)
				((LinkedHashMap) dico.get(cle)).remove("Time");	
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
