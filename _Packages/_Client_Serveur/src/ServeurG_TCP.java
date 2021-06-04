//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants reseaux generiques sous TCP/IP
//
// Classe ServeurG_TCP - Echanges parametrables de messages avec des clients IP
// au moyen d'une socket unique dediee
//                              
// Edition "Draft" : emission de messages en mode caracteres
//                              
// + Version 0.0.0 : version initiale avec un seul client possible
//
// Auteur : A. Thuaire
//

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServeurG_TCP extends Thread {
    private int portReception;
    private Socket socketSupport;
    private ObjectInputStream bin; // Flux entrant en mode bytes
    private ObjectOutputStream bout; // Flux sortant en mode bytes
    private LinkedList listeMessages; // Liste courante des messages recus
    private boolean statusProtocole; // Statut d'execution du protocole de
    
    // la couche de niveau superieur
    
    // ------ *** Premier constructeur normal
    
    public ServeurG_TCP(String nomThread) {
        
        super(nomThread);
        
        portReception = 8080;
        listeMessages = new LinkedList();
        statusProtocole = true;
        
        start();
    }
    
    // ------ *** Second constructeur normal
    
    public ServeurG_TCP(String nomThread, int port) {
        
        super(nomThread);
        
        portReception = port;
        listeMessages = new LinkedList();
        statusProtocole = true;
        
        start();
    }
    
    // ------ *** Accesseurs
    
    public Socket obtenirSocket() {
        return socketSupport;
    }
    
    public LinkedList obtenirMessages() {
        return listeMessages;
    }
    
    public boolean obtenirStatusProtocole() {
        return statusProtocole;
    }
    
    public Object retirerMessage() {
        Object msg = null;
        
        if (listeMessages.size() == 0) return null;
        
        // Executer une operation atomatique de obtenir le premier
        // message courant recu et le retirer de la liste
        //
        synchronized (listeMessages) {
            
            msg = (Object) listeMessages.getFirst();
            
            listeMessages.removeFirst();
        }
        
        // Restituer le resultat
        //
        return msg;
    }
    
    // ------ *** Methode main
    
    public static void main(String[] args) {
        
        // Creer et demarrer un serveur IP
        //
        ServeurG_TCP serveurTCP = new ServeurG_TCP("Serveur_1");
        System.out.println("* Creation et demarrage du serveur TCP/IP\n");
        
        // Attendre les messages en provenance du client unique
        //
        Object msg = null;
        
        while (serveurTCP.obtenirStatusProtocole()) {
            
            // Attendre la reception d'un nouveau message
            //
            while (serveurTCP.obtenirMessages().size() == 0)
                Chrono.attendre(200);
            
            // Retirer le message courant de la liste de reception
            //
            msg = serveurTCP.retirerMessage();
            
            // Visualiser le message recu
            // 
            System.out.print("Serveur /");
            System.out.println("<-- Message recu : " + msg);
            
            // Renvoi d'un message acknowledgment
            //
            serveurTCP.envoyerMessage("Message bien Recu cote serveur");
        }
        
        // Fermer les flux d'echange avec le client unique
        //
        serveurTCP.fermer();
    }
    
    // ------ *** Methode run
    
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
    
    // ------ *** Methode accepter
    
    private boolean accepter() {
        
        // Creer la socket serveur
        //
        ServerSocket serveur;
        try {
            serveur = new ServerSocket(portReception);
        }
        catch (Exception e) {
            System.err.println("Port deja utilisee");
            return false;
        }
        
        // Attendre la connexion du client
        //
        try {
            socketSupport = serveur.accept();
        }
        catch (Exception e) {
            return false;
        }
        
        return initFlux(socketSupport);
    }
    
    // ------ *** Methode initFlux
    
    private boolean initFlux(Socket s) {
        
        // Controler l'existence de la socket support
        //
        if (s == null) return false;
        
        // Creer le flux d'entree
        //
        InputStream streamIn = null;
        try {
            streamIn = s.getInputStream();
        }
        catch (Exception e) {
            return false;
        }
        
        // Creer le flux de sortie
        //
        OutputStream streamOut = null;
        try {
            streamOut = s.getOutputStream();
        }
        catch (Exception e) {
            return false;
        }
        
        // Creer le flux d'objets sortant en mode byte
        //
        try {
            bout = new ObjectOutputStream(streamOut);
        }
        catch (Exception e) {
            return false;
        }
        
        // Creer le flux d'objets entrant en mode byte
        //
        try {
            bin = new ObjectInputStream(streamIn);
        }
        catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    // --- *** Methode attendreMessage
    
    public void attendreMessage() {
        Object msg = null;
        
        try {
            while (true) {
                msg = bin.readObject();
                if (msg != null) break;
                Chrono.attendre(200);
            }
            // Enregistrer le message courant dans la liste des messages
            //
            listeMessages.add(msg);
        }
        catch (IOException e) {
            
            // Il y a une exception qui se leve ici
            // a chaque fois que le bin va lire un objet inexistant
            // le test du message a null est donc inutile.
            // Une solution serai de fermer le socket, car le client
            // n'a plus rien a envoye, mais comme il se peut que le client envoi
            // a des intervalle irregulier des messages nous ne pouvons pas
            // fermer son socket.
            //
            // fermer();
            // e.printStackTrace();
            // System.err.println("Err1");
            Chrono.attendre(200);
            
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("ERR2");
        }
    }
    
    // --- *** Methode envoyerMessage
    //
    public boolean envoyerMessage(String msg) {
        
        try {
            bout.writeObject(msg);
        }
        catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    // ------ *** Methode fermer
    
    public void fermer() {
        
        try {
            // statusProtocole= false;
            bin.close();
            bout.close();
            socketSupport.close();
        }
        catch (Exception e) {}
    }
    
    // ------------------------------------- *** Classe interne privee Chrono
    
    private static class Chrono {
        
        private static void attendre(int tms) {
            
            // Attendre tms millisecondes, en bloquant le thread courant
            //
            try {
                Thread.currentThread().sleep(tms);
            }
            catch (InterruptedException e) {}
        }
    }
}
