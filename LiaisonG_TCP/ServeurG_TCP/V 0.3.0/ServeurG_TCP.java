/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Composants reseaux generiques sous TCP/IP
 * 
 * Edition "Draft" :  emission de messages en mode caracteres
 * 
 * version initiale avec un seul client possible et 
 * capacite limitee a la reception d'un seul message
 * @version 0.0.0
 * 
 * ajout attribut "statusReception" pour telecommander  
 * le debut et la fin de reception des messages
 * + accesseurs debuterReception et stopperReception
 * + modification induite de la methode run
 * @version 0.1.0
 * 
 * modification des flux en mode bytes
 * @version 0.2.0
 * 
 * mise en place d'une voie de retour
 * @version 0.3.0
 */
import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Echanges parametrables de messages avec des clients IP
 * au moyen d'une socket unique dediee
 * @version 0.3.0
 * @author C. Hulin
 */
public class ServeurG_TCP extends Thread {

	/**
	 * Port d'ecoute du serveur
	 */
	private int portReception;

	/**
	 * Socket support
	 */
	private Socket socketSupport;

	/**
	 * Buffer entree en mode bytes
	 */
	private ObjectInputStream bin;

	/**
	 * Buffer sortie en mode bytes
	 */
	private ObjectOutputStream bout;

	/**
	 * Liste courante des messages recus
	 */
	private LinkedList listeMessages;

	/**
	 * Statut de l'autorisation de recevoir
	 */
	private boolean statusReception;

	/**
	 * Premier constructeur normal
	 * @param nomThread
	 */
	public ServeurG_TCP (String nomThread) {

		super(nomThread);

		portReception  = 8080;
		listeMessages  = new LinkedList();
		statusReception= false;

		start();
	}

	/**
	 * Second constructeur normal
	 * @param nomThread
	 * @param port
	 */
	public ServeurG_TCP (String nomThread, int port) {

		super(nomThread);

		portReception  = port;
		listeMessages  = new LinkedList();
		statusReception= false;

		start();
	}

	/**
	 * Obtenir le socket support
	 * @return socketSupport
	 */
	public Socket obtenirSocket  ()         {return socketSupport;}

	/**
	 * Obtenir la liste des messages
	 * @return listeMessages
	 */
	public LinkedList obtenirMessages()         {return listeMessages;}

	/**
	 * Obtenir le status de la reception
	 * @return statusReception
	 */
	public boolean obtenirStatusReception() {return statusReception;}

	/**
	 * Retire et renvoie le message de tete de liste
	 * @return message de tete de liste
	 */
	public Object retirerMessage ()  {
		Object msg= null;

		if (listeMessages.size() == 0) return null;

		// Executer une operation atomique pour obtenir le premier
		// message courant recu et le retirer de la liste
		//
		synchronized (listeMessages) {

			msg= (Object)listeMessages.getFirst();

			listeMessages.removeFirst();
		}

		// Restituer le resultat
		//
		return msg;
	}

	/** 
	 * Flag de debut de reception
	 */
	public void debuterReception() {statusReception= true;}

	/**
	 * Flag de fin de reception
	 */
	public void stopperReception() {statusReception= false;} 

	/**
	 * Main de test
	 */
	public static void main(String[] args) {

		// Creer et demarrer un serveur IP
		//
		ServeurG_TCP serveurTCP= new ServeurG_TCP("Serveur_1");
		System.out.println("* Creation et demarrage du serveur TCP/IP\n");

		// Debuter la reception des messages
		//
		serveurTCP.debuterReception();

		// Attendre les messages en provenance du client unique
		//
		Object msg= null;
		int i=1;

		while (serveurTCP.obtenirStatusReception()) {

			// Attendre la reception d'un nouveau message
			//
			while (serveurTCP.obtenirMessages().size() == 0) Chrono.attendre(100);

			// Retirer le message courant de la liste de reception
			//
			msg= serveurTCP.retirerMessage();

			// Visualiser le message recu
			// 
			System.out.println("<-- Message recu : " + msg);
			
			// Renvoi d'un message acknowledgment
			//
			serveurTCP.envoyerMessage("Message bien recu rang " + i);
			
			// Visualiser le message transmis
			//
			System.out.println("--> Message envoye  : Message bien recu rang " + i);
			System.out.println();
			i++;
		}

		// Stopper la reception des messages
		//
		serveurTCP.stopperReception();

		// Fermer les flux d'echange avec le client unique
		//
		serveurTCP.fermer();		
	}  

	/**
	 * Implementation de la methode run
	 */
	public void run() {

		// Etablir la connexion avec le serveur cible
		//
		accepter();

		// Attendre la succession des messages du protocole de niveau superieur
		//
		while (statusReception) {

			// Attendre le prochain message (appel bloquant)
			//
			attendreMessage();
		}	
	}

	/**
	 * Attend la connexion d'un client
	 */
	private boolean accepter() {

		// Creer la socket serveur
		//
		ServerSocket serveur;
		try {serveur= new ServerSocket(portReception);}
		catch (Exception e){System.err.println("Port deja utilisee");return false;}

		// Attendre la connexion du client
		//
		try{socketSupport= serveur.accept();}
		catch (Exception e){return false;}

		return initFlux(socketSupport);
	}

	/**
	 * Initialise les flux d'entree et de sortie
	 * @param s
	 * @return si bien passer true, sinon false
	 */
	private boolean initFlux(Socket s) {

		// Controler l'existence de la socket support
		//
		if (s==null) return false;

		// Creer le flux de sortie
		//
		OutputStream streamOut= null;
		try{streamOut= s.getOutputStream();}
		catch(Exception e){return false;}

		// Creer le flux d'objets sortant en mode byte
		//
		try{bout= new ObjectOutputStream(streamOut);}
		catch (Exception e) {return false;}
		
		// Creer le flux d'entree
		//
		InputStream streamIn= null;
		try{streamIn= s.getInputStream();}
		catch (Exception e) {return false;}

		// Creer le flux d'objets entrant en mode byte
		//
		try{bin= new ObjectInputStream(streamIn);}
		catch (Exception e) {return false;}

		return true;
	}

	/**
	 * Envoi les Messages au serveur en liaison
	 * @param msg
	 * @return si bien passer true, sinon false
	 */
	public boolean envoyerMessage (Object msg) {

		// Controler la validite du flux de sortie
		//
		if (bout==null) return false;

		// Transferer le message dans le flux de sortie
		//
		try {bout.writeObject(msg);}
		catch (Exception e) {return false;}
		return true;
	}
	
	/**
	 * Attend la reception de message
	 */
	public void attendreMessage () {
		Object msg=null;

		try 
		{
			while (true) {

				// Recuperer le message dans le flux d'entrer
				//
				msg= bin.readObject();

				// Enregistrer le message courant dans la liste des messages
				//
				if (msg != null) break;
			}
			
			listeMessages.add(msg);
		}
		catch (Exception e){}
	}

	/**
	 * Ferme la connection
	 */
	public void fermer () {

		try 
		{ 
			bin.close();
			bout.close();
			socketSupport.close();
		}
		catch(Exception e){}
	}

	/**
	 * Classe interne privee Chrono
	 * @version 0.0.0
	 * @author C. Hulin
	 */
	private static class Chrono {

		/**
		 * Attendre n millisecondes, en bloquant le thread courant
		 * @param n
		 */
		private static void attendre (int n) {

			try 
			{
				Thread.currentThread();
				Thread.sleep(n);
			} 
			catch(InterruptedException e){ }
		}
	}
}