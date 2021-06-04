//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants reseaux generiques sous TCP/IP
//
// Classe ClientG_TCP - Echanges parametrables de messages avec un serveur
// TCP/IP au moyen d'une socket unique dediee
//                              
// Edition "Draft" : emission de messages en mode caracteres
//                              
// + Version 0.0.0 : version initiale avec creation d'un thread autonome
//
// Auteur : A. Thuaire
//

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class ClientG_TCP extends Thread {
    private String nomServeur;
    private int portServeur;
    private Socket socketSupport;
    private ObjectInputStream bin; // Flux entrant en mode bytes
    private ObjectOutputStream bout; // Flux sortant en mode bytes
    private LinkedList listeMessages; // Liste des messages a envoyer
    private LinkedList listeMessagesReception; // Liste des messages recu
    
    // ------ *** Premier constructeur normal
    
    public ClientG_TCP(String nomThread) {
        
        super(nomThread);
        nomServeur = "localHost";
        portServeur = 8081;
        listeMessages = new LinkedList();
        listeMessagesReception = new LinkedList();
        
        start();
    }
    
    // ------ *** Second constructeur normal
    
    public ClientG_TCP(String nomThread, String host, int port) {
        
        super(nomThread);
        nomServeur = host;
        portServeur = port;
        listeMessages = new LinkedList();
        listeMessagesReception = new LinkedList();
        
        start();
    }
    
    // ------ *** Accesseurs
    
    public Socket obtenirSocket() {
        return socketSupport;
    }
    
    public void ajouterMessage(String msg) {
        listeMessages.add(msg);
    }
    
    public LinkedList obtenirMessagesReception() {
        return listeMessagesReception;
    }
    
    public LinkedList obtenirMessages() {
        return listeMessages;
    }
    
    public String obtenirMessage() {
        
        if (listeMessages.size() == 0) return null;
        return (String) listeMessages.getFirst();
    }
    
    // ------ *** Methode main
    
    public static void main(String[] args) {
        
        // Creer et demarrer un client TCP
        //
        ClientG_TCP clientTCP = new ClientG_TCP("Client_1");
        System.out.println("* Creation et demarrage du client TCP/IP");
        
        // Attendre la mise en service du serveur
        //
        while (clientTCP.obtenirSocket() == null)
            Chrono.attendre(200);
        System.out.println("* Client connecte");
        System.out.println();
        
        // Obtenir la socket support
        //
        Socket socketSupport = clientTCP.obtenirSocket();
        
        // Construire un premier message a envoyer au serveur
        //
        String adresseIPClient = socketSupport.getLocalAddress()
        .getHostAddress();
        String msg_1 = new String("DECLARER" + " - " + adresseIPClient);
        
        String msg_2 = new String("Coucou je suis le client!");
        
        // Ajouter le message courant a la liste des messages a envoyer
        //
        clientTCP.ajouterMessage(msg_1);
        clientTCP.ajouterMessage(msg_2);
        
        // Calculer la taille courante de la liste
        //
        int tailleCourante = clientTCP.obtenirMessages().size();
        
        // Visualiser le message transmis
        //
        System.out.println("--> Message envoye  : " + msg_1);
        System.out.println("    Rang du message : " + tailleCourante);
        System.out.println();
        
        // Attendre que la liste des messages soit vide
        //
        clientTCP.attendreMessage();
        
        // Lire recu
        //
        String msg = (String) clientTCP.retirerMessage();
        
        // Visualiser le message recu
        // 
        System.out.print("Client /");
        System.out.println("<-- Message recu : " + msg);
        
        // Fermer les flux d'echange avec le serveur
        //
        clientTCP.fermer();
        
        System.out.println("* Client deconnecte");
        System.out.println();
    }
    
    // ------ *** Methode run
    
    public void run() {
        
        // Etablir la connexion avec le serveur cible
        //
        connecter();
        
        // Attendre l'ajout d'un message
        //	
        while (listeMessages.size() == 0)
            Chrono.attendre(200);
        
        // Parcourir la liste courante des messages au serveur cible
        //
        boolean statusEmission = false;
        String msg = null;
        
        while (listeMessages.size() != 0) {
            
            // Executer une operation atomatique d'envoi d'un message
            //
            synchronized (listeMessages) {
                
                // Extraire le message suivant
                //
                msg = (String) listeMessages.getFirst();
                
                // Envoyer le message courant
                //
                statusEmission = envoyerMessage(msg);
                
                // Retirer ce message de la liste
                //
                listeMessages.removeFirst();
            }
            
            // Visualiser le message envoye
            //
            if (statusEmission) {
                System.out.println("Client /--> Message envoye : " + msg);
            }
        }
    }
    
    // ------ *** Methode connecter
    
    private boolean connecter() {
        
        // Creer une connexion avec le serveur cible
        //
        try {
            socketSupport = new Socket(nomServeur, portServeur);
        }
        catch (Exception e) {}
        
        // Initialiser les flux entrant et sortant de la connexion
        //
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
    
    // --- *** Methode attendreMessage
    //
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
            listeMessagesReception.add(msg);
        }
        catch (Exception e) {
            return;
        }
    }
    
    // --- *** Methode retireMessage
    //
    public Object retirerMessage() {
        Object msg = null;
        
        if (listeMessagesReception.size() == 0) return null;
        
        // Executer une operation atomatique de obtenir le premier
        // message courant recu et le retirer de la liste
        //
        synchronized (listeMessagesReception) {
            
            msg = (Object) listeMessagesReception.getFirst();
            
            listeMessagesReception.removeFirst();
        }
        // Restituer le resultat
        //
        return msg;
    }
    
    // ------ *** Methode fermer
    //
    public void fermer() {
        try {
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
