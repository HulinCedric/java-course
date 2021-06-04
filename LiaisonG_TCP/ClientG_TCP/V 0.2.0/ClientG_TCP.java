//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants reseaux generiques sous TCP/IP
//
// Classe ClientG_TCP - Echanges parametrables de messages avec un serveur 
//                      TCP/IP au moyen d'une socket unique dediee
//                              
// Edition "Draft" :  emission de messages en mode caracteres
//                              
//    + Version 0.0.0	: version initiale avec creation d'un thread autonome
//    + Version 0.1.0   : introduction temporisation avant fermeture socket
//                        + attente d'au moins un message au demarrage
//    + Version 0.2.0   : modification de la methode connecter pour autoriser
//                        un demarrage du client TCP/IP avant celui du serveur
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.io.*;
import java.net.*;
      
public class ClientG_TCP extends Thread {
private String         nomServeur;
private int            portServeur;
private Socket         socketSupport;	
private BufferedReader cin;            // Buffer entree en mode caracteres
private PrintWriter    cout;           // Buffer sortie en mode caracteres
private LinkedList     listeMessages;  // Liste des messages a envoyer
  
// ------                                      *** Premier constructeur normal  
   
   public ClientG_TCP (String nomThread) {
      
      super(nomThread);
      nomServeur    = "localHost"; 
      portServeur=8080;
      listeMessages= new LinkedList();
      
      start();
   }
   
// ------                                       *** Second constructeur normal  
   
   public ClientG_TCP (String nomThread, String host, int port) {
   	
   	 super(nomThread);
   	 nomServeur = host; 
   	 portServeur= port;
   	 listeMessages= new LinkedList();
   	 
   	 start();
   }

// ------                                                      *** Accesseurs  
   
   public Socket     obtenirSocket()            {return socketSupport;}
   public void       ajouterMessage(String msg) {listeMessages.add(msg);}
   public LinkedList obtenirMessages()          {return listeMessages;}
   public String     obtenirMessage() {
   	
      if (listeMessages.size() == 0) return null;
   	  return (String)listeMessages.getFirst();
   } 
   
// ------                                                      *** Methode main  

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
      
      // Fermer les flux d'echange avec le serveur
      //
      Chrono.attendre(1000);
      clientTCP.fermer();
      
      System.out.println("* Client deconnecte");
      System.out.println();	
   }  
   
// ------                                                      *** Methode run  

   public void run() {
   
      // Etablir la connexion avec le serveur cible
      //
      connecter(); 
      
      // Attendre la presence d'au moins un message dans la file d'attente
      //
      while (listeMessages.size() == 0) Chrono.attendre(200);
       	
      // Parcourir la liste courante des messages
      //
      boolean statusEmission= false;
      String msg= null;
      
      while (listeMessages.size() != 0) {
      	
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
   }
   
// ------                                                *** Methode connecter  

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
   
// ------                                                 *** Methode initFlux  

   private boolean initFlux(Socket s) {
   
      // Controler l'existence de la socket support
      //
      if (s==null) return false;
   
      // Creer le flux d'entree
      //
      InputStream streamIn= null;
      try{streamIn= s.getInputStream();}
      catch (Exception e) {return false;}

      // Creer le buffer d'entree
      //
      InputStreamReader bufferIn;
      bufferIn= new InputStreamReader(streamIn);
      cin= new BufferedReader(bufferIn);
      if (cin == null) return false;
      
      // Creer le flux de sortie
      //
      OutputStream streamOut= null;
      try{streamOut= s.getOutputStream();}
      catch(Exception e){return false;}
      
      // Creer le buffer de sortie
      //
      OutputStreamWriter bufferOut;
      bufferOut= new OutputStreamWriter(streamOut);
      cout= new PrintWriter(new BufferedWriter(bufferOut), true);
      if (cout == null) return false;
      
      return true;
   }
   
// ---                                              *** Methode envoyerMessage  

   public boolean envoyerMessage (String msg) {
   	
      // Controler la validite du flux de sortie
      //
      if (cout==null) return false;
      
      // Transferer le message dans le flux de sortie
      //
   	  cout.println(msg);
   	  return true;
   }
   
// ------                                                   *** Methode fermer  
   //
   public void fermer () {
   	try { 
   	   cin.close();
   	   cout.close();
   	   socketSupport.close();
   	}
   	catch(Exception e){}
   }
   
// -------------------------------------      *** Classe interne privee Chrono
   
   private static class Chrono {

      private static void attendre (int tms) {
         	
         // Attendre tms millisecondes, en bloquant le thread courant 
         //
         try {Thread.currentThread().sleep(tms);} 
         catch(InterruptedException e){ }
      }
   }
}
