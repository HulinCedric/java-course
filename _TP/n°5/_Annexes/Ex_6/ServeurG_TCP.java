//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants reseaux generiques sous TCP/IP
//
// Classe ServeurG_TCP - Echanges parametrables de messages avec des clients IP
//                       au moyen d'une socket unique dediee
//                              
// Edition "Draft" :  emission de messages en mode caracteres
//                              
//    + Version 0.0.0	: version initiale avec un seul client possible 
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.io.*;
import java.net.*;
      
public class ServeurG_TCP extends Thread {
private int            portReception;
private Socket         socketSupport;	
private BufferedReader cin;             // Buffer entree en mode caracteres
private PrintWriter    cout;            // Buffer sortie en mode caracteres
private LinkedList     listeMessages;   // Liste courante des messages recus
private boolean        statusProtocole; // Statut d'execution du protocole de
                                        // la couche de niveau superieur
  
// ------                                      *** Premier constructeur normal  
   
   public ServeurG_TCP (String nomThread) {
      
      super(nomThread);
      
      portReception  = 8080;
      listeMessages  = new LinkedList();
      statusProtocole= true;
      
      start();
   }
   
// ------                                       *** Second constructeur normal  
   
   public ServeurG_TCP (String nomThread, int port) {
   	
   	 super(nomThread);
   	 
   	 portReception  = port;
   	 listeMessages  = new LinkedList();
   	 statusProtocole= true;
   	 
   	 start();
   }

// ------                                                      *** Accesseurs  
   
   public Socket     obtenirSocket  ()         {return socketSupport;}
   public LinkedList obtenirMessages()         {return listeMessages;}
   public boolean    obtenirStatusProtocole () {return statusProtocole;} 
   public String     retirerMessage ()  {
   String msg= null;
   	
   	  if (listeMessages.size() == 0) return null;
   	  
   	  // Executer une operation atomatique de obtenir le premier
   	  // message courant recu et le retirer de la liste
   	  //
   	  synchronized (listeMessages) {
   	  	
   	     msg= (String)listeMessages.getFirst();
   	  
         listeMessages.removeFirst();
      }
      
      // Restituer le resultat
      //
      return msg;
   }
   
// ------                                                      *** Methode main  

   public static void main(String[] args) {
   	
   	  // Creer et demarrer un serveur IP
   	  //
   	  ServeurG_TCP serveurTCP= new ServeurG_TCP("Serveur_1");
   	  System.out.println("* Creation et demarrage du serveur TCP/IP\n");
      
      // Attendre les messages en provenance du client unique
      //
      String msg= null;
      
      while (serveurTCP.obtenirStatusProtocole()) {
      	
      	 // Attendre la reception d'un nouveau message
      	 //
      	 while (serveurTCP.obtenirMessages().size() == 0) Chrono.attendre(100);
      	 
      	 // Retirer le message courant de la liste de reception
      	 //
      	 msg= serveurTCP.retirerMessage();
      	 
      	 // Visualiser le message recu
         // 
         System.out.print("Serveur TCP/IP /");
         System.out.println("<-- Message recu : " + msg);
      }
      
      // Fermer les flux d'echange avec le client unique
      //
      serveurTCP.fermer();		
   }  
   
// ------                                                      *** Methode run  

   public void run() {
   
      // Etablir la connexion avec le serveur cible
      //
      accepter();
      
      // Attendre la succession des messages du protocole de niveau superieur
      //
      while (statusProtocole) {
      	
      	 // Attendre le prochain message (appel bloquant)
      	 //
      	 attendreMessage();
      }	
   }
   
// ------                                                *** Methode accepter  

   private boolean accepter() {
   
      // Creer la socket serveur
      //
      ServerSocket serveur;
      try {serveur= new ServerSocket(portReception);}
      catch (Exception e){return false;}
      
      // Attendre la connexion du client
      //
      try{socketSupport= serveur.accept();}
      catch (Exception e){return false;}
      
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
   
// ---                                             *** Methode attendreMessage  

   public void attendreMessage () {
   String msg=null;
   
      try {
         while (true) {
      	    msg= cin.readLine();
      	    if (msg != null) break;
      	    Chrono.attendre(100);
      	 }
      }
      catch (Exception e){}
      
      // Enregistrer le message courant dans la liste des messages
      //
      listeMessages.add(msg);
   }
   
// ------                                                   *** Methode fermer  

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
