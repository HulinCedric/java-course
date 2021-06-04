/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Composants reseaux generiques sous TCP/IP
 * 
 * Edition "Draft" :  emission de messages en mode caracteres
 * 
 * Version initiale avec creation d'un thread autonome
 * @version 0.0.0
 * 
 * introduction temporisation avant fermeture socket
 * + attente d'au moins un message au demarrage
 * @version 0.1.0
 * 
 * modification de la methode connecter pour autoriser
 * un demarrage du client TCP/IP avant celui du serveur
 * @version 0.2.0
 * 
 * ajout attribut "statusEmission" pour telecommander le
 * debut et la fin d'emission des messages
 * + accesseurs debuterEmission et stopperEmission
 * + modification induite de la methode run
 * @version 0.3.0
 * 
 * modification des flux en mode bytes
 * @version 0.4.0
 */
import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Echanges parametrables de messages avec un serveur 
 * TCP/IP au moyen d'une socket unique dediee
 * @version 0.4.0                      
 * @author C. Hulin
 */
public class ClientG_TCP extends Thread {

	/**
	 * Nom du serveur cible
	 */
	private String nomServeur;

	/**
	 * Port du serveur cible
	 */
	private int portServeur;

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
	 * Liste des messages a envoyer
	 */
	private LinkedList listeMessages;

	/**
	 * Status de l'autorisation d'emettre
	 */
	private boolean statusEmission;

	/**
	 * Premier constructeur normal 
	 * @param nomThread
	 */
	public ClientG_TCP (String nomThread) {

		super(nomThread);
		nomServeur    = "localHost"; 
		portServeur   =8080;
		listeMessages = new LinkedList();
		statusEmission= false;

		start();
	}

	/**
	 * Second constructeur normal
	 * @param nomThread
	 * @param host
	 * @param port
	 */
	public ClientG_TCP (String nomThread, String host, int port) {

		super(nomThread);
		nomServeur    = host; 
		portServeur   = port;
		listeMessages = new LinkedList();
		statusEmission= false;

		start();
	}

	/**
	 * Obtenir le socket support
	 * @return socketSupport
	 */
	public Socket     obtenirSocket()            {return socketSupport;}

	/**
	 * Ajoute un message a la liste des messages
	 * @param msg
	 */
	public void       ajouterMessage(Object msg) {listeMessages.add(msg);}

	/**
	 * Obtenir la liste des messages
	 * @return listeMessages
	 */
	public LinkedList obtenirMessages()          {return listeMessages;}

	/**
	 * Obtenir le message en t&ecirc;te de liste
	 * @return
	 */
	public Object     obtenirMessage() {

		if (listeMessages.size() == 0) return null;
		return (Object)listeMessages.getFirst();
	}

	/**
	 * Flag de debut d'emision
	 */
	public void       debuterEmission() {statusEmission= true; }

	/**
	 * Flag de fin d'emision
	 */
	public void       stopperEmission() {statusEmission= false;} 

	/**
	 * Main de test
	 * @param args
	 */
	public static void main(String[] args) {

		// Creer et demarrer un client TCP
		//
		ClientG_TCP clientTCP= new ClientG_TCP("Client_1");
		System.out.println("* Creation et demarrage du client TCP/IP");

		// Attendre la mise en service du serveur
		//
		while (clientTCP.obtenirSocket() == null) Chrono.attendre(200);
		System.out.println("* Client connecte");
		System.out.println();

		// Obtenir la socket support
		//
		Socket socketSupport= clientTCP.obtenirSocket();

		// Construire un premier message a envoyer au serveur
		//
		String adresseIPClient= socketSupport.getLocalAddress().getHostAddress();
		String msg_1= new String("DECLARER" + " - " + adresseIPClient);

		// Ajouter le message courant a la liste des messages a envoyer
		//
		clientTCP.ajouterMessage(msg_1);

		// Calculer la taille courante de la liste
		//
		int tailleCourante= clientTCP.obtenirMessages().size();

		// Visualiser le message transmis
		//
		System.out.println("--> Message envoye  : " + msg_1);
		System.out.println("    Rang du message : " + tailleCourante);
		System.out.println();

		// Debuter l'emission des messages
		//
		clientTCP.debuterEmission();
		Chrono.attendre(1000);

		// Construire un second message a envoyer au serveur
		//
		String msg_2= new String("CONTINUER");

		// Ajouter le message courant a la liste des messages a envoyer
		//
		clientTCP.ajouterMessage(msg_2);

		// Visualiser le message transmis
		//
		System.out.println("--> Message envoye  : " + msg_2);
		System.out.println("    Rang du message : 2");
		System.out.println();

		// Stopper l'emission des messages
		//
		Chrono.attendre(1000);
		clientTCP.stopperEmission();

		// Fermer les flux d'echange avec le serveur
		//
		clientTCP.fermer();

		System.out.println("* Client deconnecte");
		System.out.println();
	}  

	/**
	 * Implementation de la methode run
	 */
	public void run() {

		// Etablir la connexion avec le serveur cible
		//
		connecter(); 

		// Attendre l'autorisation d'emettre
		//
		while (!statusEmission) Chrono.attendre(200);

		// Parcourir la liste courante des messages
		//
		Object msg= null;

		while (statusEmission) {

			if (listeMessages.size() != 0) {

				// Executer une operation atomatique d'envoi d'un message 
				//
				synchronized (listeMessages) {

					// Extraire le message suivant
					//
					msg= (String)listeMessages.getFirst();

					// Envoyer le message courant
					//
					statusEmission= envoyerMessage(msg);

					// Retirer ce message de la liste
					//
					listeMessages.removeFirst();
				}
			}
			else {

				// Attendre le message suivant
				//
				Chrono.attendre(100);
			}
		}	
	}

	/**
	 * Connecte un client au serveur
	 * @return si bien passer true, sinon false
	 */
	private boolean connecter() {

		// Creer une connexion avec le serveur cible
		//
		while (true) {

			// Creer la socket support
			//
			try{socketSupport= new Socket(nomServeur, portServeur);}
			catch (Exception e){}

			// Controler la validite de cette socket
			//
			if (socketSupport != null) break;
		}

		// Initialiser les flux entrant et sortant de la connexion
		//
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

		// Creer le flux d'entree
		//
		InputStream streamIn= null;
		try{streamIn= s.getInputStream();}
		catch (Exception e) {return false;}

		// Creer le flux d'objets entrant en mode byte
		//
		try{bin= new ObjectInputStream(streamIn);}
		catch (Exception e) {return false;}

		// Creer le flux de sortie
		//
		OutputStream streamOut= null;
		try{streamOut= s.getOutputStream();}
		catch(Exception e){return false;}

		// Creer le flux d'objets sortant en mode byte
		//
		try{bout= new ObjectOutputStream(streamOut);}
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