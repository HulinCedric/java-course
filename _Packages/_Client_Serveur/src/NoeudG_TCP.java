import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class NoeudG_TCP extends Thread {
    private String nom;
    private int port;
    private Socket socketSupport;
    private ObjectInputStream bin; // Flux entrant en mode bytes
    private ObjectOutputStream bout; // Flux sortant en mode bytes
    private LinkedList listeMessagesEnvoi; // Liste des messages a envoyer
    private LinkedList listeMessagesReception; // Liste des messages recu
    private boolean client = false; // Defini le Noeud comme client
    private boolean serveur = false; // Defini le Noeud comme serveur
    private boolean statusProtocole; // Statut d'execution du protocole de la
                                     // couche de niveau superieur
    private ServerSocket serveurSocket;
    private Object interneClass;
    
    NoeudG_TCP(String nomThread) {
        super(nomThread);
        nom = "localHost";
        port = 8081;
        listeMessagesEnvoi = new LinkedList();
        listeMessagesReception = new LinkedList();
        statusProtocole = true;
        
        start();
    }
    
    NoeudG_TCP(String nomThread, int portUse) {
        super(nomThread);
        nom = "localHost";
        port = portUse;
        listeMessagesEnvoi = new LinkedList();
        listeMessagesReception = new LinkedList();
        statusProtocole = true;
        
        start();
    }
    
    NoeudG_TCP(String nomThread, String host, int portUse) {
        super(nomThread);
        nom = host;
        port = portUse;
        listeMessagesEnvoi = new LinkedList();
        listeMessagesReception = new LinkedList();
        statusProtocole = true;
        
        start();
    }
    
    // ------ *** Accesseurs
    
    public Socket obtenirSocket() {
        
        return socketSupport;
    }
    
    public boolean obtenirStatusProtocole() {
        
        return statusProtocole;
    }
    
    public void ajouterMessageEnvoi(String msg) {
        
        listeMessagesEnvoi.add(msg);
    }
    
    public LinkedList obtenirMessagesEnvoi() {
        
        return listeMessagesEnvoi;
    }
    
    public LinkedList obtenirMessagesReception() {
        
        return listeMessagesReception;
    }
    
    public Object retirerMessageEnvoi() {
        Object msg = null;
        
        if (listeMessagesEnvoi.size() == 0) return null;
        
        // Executer une operation atomatique de obtenir le premier
        // message courant recu et le retirer de la liste
        //
        synchronized (listeMessagesEnvoi) {
            
            msg = (Object) listeMessagesEnvoi.getFirst();
            listeMessagesEnvoi.removeFirst();
        }
        
        // Restituer le resultat
        //
        return msg;
    }
    
    public Object retirerMessageReception() {
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
    
    public void client() {
        client = true;
        serveur = false;
    }
    
    public void serveur() {
        serveur = true;
        client = false;
    }
    
    // ------ *** Methode run
    
    public void run() {
        
        while (!serveur && !client)
            Chrono.attendre(200);
        
        if (client) interneClass = new Client();
        else interneClass = new Serveur();
        
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
        catch (IOException e) {
            fermer();
            e.printStackTrace();
            System.out.println("ERR1");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERR2");
        }
    }
    
    // ------ *** Methode connecter
    
    private boolean connecter() {
        
        // Creer une connexion avec le serveur cible
        //
        if (client) {
            try {
                socketSupport = new Socket(nom, port);
            }
            catch (Exception e) {}
        }
        else {
            // Creer la socket serveur
            //
            try {
                serveurSocket = new ServerSocket(port);
            }
            catch (Exception e) {
                System.err.println("Port deja utilisee");
                return false;
            }
        }
        
        // Initialiser les flux entrant et sortant de la connexion
        //
        return initFlux(socketSupport);
    }
    
    // ------ *** Methode accepter
    
    private boolean accepter() {
        
        // Attendre la connexion du client
        //
        try {
            socketSupport = serveurSocket.accept();
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
    
    // ------ *** Methode main
    
    public static void main(String[] args) {
        
        NoeudG_TCP serveurTCP = new NoeudG_TCP("Serveur");
        serveurTCP.serveur();
        
        NoeudG_TCP client1 = new NoeudG_TCP("Client1");
        client1.client();
        client1.ajouterMessageEnvoi("Client1 envoi1");
        client1.ajouterMessageEnvoi("Client1 envoi2");
        client1.ajouterMessageEnvoi("Client1 envoi3");
        
        client1.fermer();
    }
    
    private class Client extends Thread {
        
        public Client() {
            start();
        }
        
        public void run() {
            // Etablir la connexion avec le serveur cible
            //
            connecter();
            
            // Attendre l'ajout d'un message
            //	
            while (listeMessagesEnvoi.size() == 0)
                Chrono.attendre(200);
            
            // Parcourir la liste courante des messages au serveur cible
            //
            boolean statusEmission = false;
            String msg = null;
            
            while (listeMessagesEnvoi.size() != 0) {
                
                // Executer une operation atomatique d'envoi d'un message
                //
                synchronized (listeMessagesEnvoi) {
                    
                    // Extraire le message suivant
                    //
                    msg = (String) listeMessagesEnvoi.getFirst();
                    
                    // Envoyer le message courant
                    //
                    statusEmission = envoyerMessage(msg);
                    
                    // Retirer ce message de la liste
                    //
                    listeMessagesEnvoi.removeFirst();
                }
                
                // Visualiser le message envoye
                //
                if (statusEmission) {
                    System.out.println("Client / Message envoye : " + msg);
                }
            }
        }
    }
    
    private class Serveur extends Thread {
        
        public Serveur() {
            start();
        }
        
        public void run() {
            
            connecter();
            
            // Attendre la succession des messages du protocole de niveau
            // superieur
            //
            while (statusProtocole) {
                
                // Attendre le prochain message (appel bloquant)
                //
                attendreMessage();
                while (listeMessagesReception.size() != 0)
                    System.out.println("Serveur / Reception : "
                            + retirerMessageReception());
            }
        }
    }
    
    // ------------------------------------- *** Classe interne privee Chrono
    
    private static class Chrono {
        
        private static void attendre(int tms) {
            
            // Attendre tms millisecondes, en bloquant le thread courant
            //
            try {
                Thread.currentThread();
                Thread.sleep(tms);
            }
            catch (InterruptedException e) {}
        }
    }
}