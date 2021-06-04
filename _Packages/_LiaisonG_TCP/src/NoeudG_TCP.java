/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * 
 * Annee 2009_2010 - Composants reseaux generiques sous TCP/IP Version initiale
 * 
 * @version 0.0.0
 */
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Echanges parametrables de messages avec un serveur ou un client TCP/IP au
 * moyen d'une socket unique dediee
 * 
 * @version 0.0.0
 * @author C. Hulin
 */
public class NoeudG_TCP {
    
    /**
     * Nom du serveur cible
     */
    private String nomServeur;
    
    /**
     * Port du serveur cible
     */
    private int port;
    
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
    private LinkedList listeMessagesE;
    
    /**
     * Liste des messages recus
     */
    private LinkedList listeMessagesR;
    
    /**
     * Status de l'autorisation d'emettre
     */
    private boolean statusEmission;
    
    /**
     * Statut de l'autorisation de recevoir
     */
    private boolean statusReception;
    
    /**
     * Premier constructeur normal
     * 
     * @param nomThread
     */
    public NoeudG_TCP(String nomThread) {
        
        port = 8080;
        listeMessagesR = new LinkedList();
        statusReception = false;
        new Serveur(nomThread);
    }
    
    /**
     * Second constructeur normal
     * 
     * @param nomThread
     * @param host
     */
    public NoeudG_TCP(String nomThread, String host) {
        
        port = 8080;
        nomServeur = host;
        listeMessagesE = new LinkedList();
        listeMessagesR = new LinkedList();
        statusEmission = false;
        new Client(nomThread);
    }
    
    /**
     * Troisieme constructeur normal
     * 
     * @param nomThread
     * @param portC
     */
    public NoeudG_TCP(String nomThread, int portC) {
        
        port = portC;
        listeMessagesR = new LinkedList();
        statusReception = false;
        new Serveur(nomThread);
    }
    
    /**
     * Quatrieme constructeur normal
     * 
     * @param nomThread
     * @param host
     * @param portC
     */
    public NoeudG_TCP(String nomThread, String host, int portC) {
        
        port = portC;
        nomServeur = host;
        listeMessagesE = new LinkedList();
        listeMessagesR = new LinkedList();
        statusEmission = false;
        new Client(nomThread);
    }
    
    /**
     * Obtenir le socket support
     * 
     * @return socketSupport
     */
    public Socket obtenirSocket() {
        return socketSupport;
    }
    
    /**
     * Ajoute un message a la liste des messages
     * 
     * @param msg
     */
    public void ajouterMessage(Object msg) {
        listeMessagesE.add(msg);
    }
    
    /**
     * Obtenir la liste des messages d'emmission
     * 
     * @return listeMessagesE
     */
    public LinkedList obtenirMessagesE() {
        return listeMessagesE;
    }
    
    /**
     * Obtenir la liste des messages de reception
     * 
     * @return listeMessagesR
     */
    public LinkedList obtenirMessagesR() {
        return listeMessagesR;
    }
    
    /**
     * Retire et renvoie le message de tete de la liste d'emmission
     * 
     * @return message de tete de liste d'emmission
     */
    public Object retirerMessageE() {
        Object msg = null;
        
        if (listeMessagesE.size() == 0) return null;
        
        // Executer une operation atomique pour obtenir le premier
        // message courant recu et le retirer de la liste
        //
        synchronized (listeMessagesE) {
            
            msg = (Object) listeMessagesE.getFirst();
            
            listeMessagesE.removeFirst();
        }
        
        // Restituer le resultat
        //
        return msg;
    }
    
    /**
     * Retire et renvoie le message de tete de la liste de reception
     * 
     * @return message de tete de liste de reception
     */
    public Object retirerMessageR() {
        Object msg = null;
        
        if (listeMessagesR.size() == 0) return null;
        
        // Executer une operation atomique pour obtenir le premier
        // message courant recu et le retirer de la liste
        //
        synchronized (listeMessagesR) {
            
            msg = (Object) listeMessagesR.getFirst();
            
            listeMessagesR.removeFirst();
        }
        
        // Restituer le resultat
        //
        return msg;
    }
    
    /**
     * Flag de debut d'emision
     */
    public void debuterEmission() {
        statusEmission = true;
    }
    
    /**
     * Flag de fin d'emision
     */
    public void stopperEmission() {
        statusEmission = false;
    }
    
    /**
     * Flag de debut de reception
     */
    public void debuterReception() {
        statusReception = true;
    }
    
    /**
     * Flag de fin de reception
     */
    public void stopperReception() {
        statusReception = false;
    }
    
    /**
     * Main de test
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        // Creer et demarrer un Noeud TCP serveur
        //
        NoeudG_TCP serveurTCP = new NoeudG_TCP("Serveur_1");
        System.out.println("* Creation et demarrage du serveur TCP/IP");
        System.out.println("* Serveur_1 demarer");
        Chrono.attendre(1000);
        
        // Debuter la reception des messages
        //
        serveurTCP.debuterReception();
        
        // Creer et demarrer un Noeud TCP client
        //
        NoeudG_TCP clientTCP = new NoeudG_TCP("Client_1", "localHost");
        System.out.println("* Creation et demarrage du client TCP/IP");
        
        // Attendre la mise en service du serveur
        //
        while (clientTCP.obtenirSocket() == null)
            Chrono.attendre(200);
        System.out.println("* Client_1 connecte");
        System.out.println();
        Chrono.attendre(1000);
        
        // Obtenir la socket support
        //
        Socket socketSupport = clientTCP.obtenirSocket();
        
        // Construire un premier message a envoyer au serveur
        //
        String adresseIPClient = socketSupport.getLocalAddress()
                .getHostAddress();
        String msg_1 = new String("DECLARER" + " - " + adresseIPClient);
        
        // Ajouter le message courant a la liste des messages a envoyer
        //
        clientTCP.ajouterMessage(msg_1);
        
        // Calculer la taille courante de la liste
        //
        int tailleCourante = clientTCP.obtenirMessagesE().size();
        
        // Visualiser le message transmis
        //
        System.out.println("Client_1\t--> Message envoye  : " + msg_1);
        System.out.println("\t\t    Rang du message : " + tailleCourante);
        System.out.println();
        
        // Debuter l'emission des messages
        //
        clientTCP.debuterEmission();
        Chrono.attendre(1000);
        
        // Attendre les messages en provenance du client unique
        //
        while (serveurTCP.obtenirMessagesR().size() == 0)
            Chrono.attendre(100);
        
        // Retirer le message courant de la liste de reception
        //
        Object msg_2 = serveurTCP.retirerMessageR();
        
        // Visualiser le message recu
        // 
        System.out.println("Serveur_1\t<-- Message recu : " + msg_2);
        System.out.println();
        
        // Renvoi d'un message acknowledgment
        //
        serveurTCP.envoyerMessage("Message bien recu rang 1");
        
        // Visualiser le message transmis
        //
        Chrono.attendre(1000);
        System.out
                .println("Serveur_1\t--> Message envoye  : Message bien recu rang 1");
        System.out.println();
        
        // Attendre le premier message d'acknoledgement
        //
        Chrono.attendre(1000);
        clientTCP.attendreMessage();
        
        // Recuperer le premier message d'acknoledgement
        //
        String msg_3 = (String) clientTCP.retirerMessageR();
        
        // Visualiser le message recu
        // 
        System.out.println("Client_1\t<-- Message recu : " + msg_3);
        System.out.println();
        
        // Stopper l'emission des messages
        //
        Chrono.attendre(1000);
        clientTCP.stopperEmission();
        
        // Fermer les flux d'echange avec le serveur
        //
        clientTCP.fermer();
        
        System.out.println("* Client_1  deconnecte");
        
        // Stopper la reception des messages
        //
        serveurTCP.stopperReception();
        
        // Fermer les flux d'echange avec le client unique
        //
        serveurTCP.fermer();
        
        System.out.println("* Serveur_1 arreter");
    }
    
    /**
     * Initialise les flux d'entree et de sortie
     * 
     * @param s
     *            socket permettant le parametrage des flux d'entrer et de
     *            sortie
     * @return si bien passer true, sinon false
     */
    private boolean initFlux(Socket s) {
        
        // Controler l'existence de la socket support
        //
        if (s == null) return false;
        
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
        
        // Creer le flux d'entree
        //
        InputStream streamIn = null;
        try {
            streamIn = s.getInputStream();
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
    
    /**
     * Envoi les Messages au serveur en liaison
     * 
     * @param msg
     * @return si bien passer true, sinon false
     */
    public boolean envoyerMessage(Object msg) {
        
        // Controler la validite du flux de sortie
        //
        if (bout == null) return false;
        
        // Transferer le message dans le flux de sortie
        //
        try {
            bout.writeObject(msg);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * Attend la reception de message
     */
    public void attendreMessage() {
        Object msg = null;
        
        try {
            while (true) {
                // Recuperer le message dans le flux d'entrer
                //
                msg = bin.readObject();
                
                // Enregistrer le message courant dans la liste des messages
                //
                if (msg != null) break;
            }
            listeMessagesR.add(msg);
        }
        catch (Exception e) {}
    }
    
    /**
     * Ferme la connection
     */
    public void fermer() {
        
        try {
            bin.close();
            bout.close();
            socketSupport.close();
        }
        catch (Exception e) {}
    }
    
    /**
     * Echanges parametrables de messages avec un serveur TCP/IP au moyen d'une
     * socket unique dediee
     * 
     * @version 0.5.0
     * @author C. Hulin
     */
    private class Client extends Thread {
        
        /**
         * Premier constructeur normal
         * 
         * @param nomThread
         */
        public Client(String nomThread) {
            
            super(nomThread);
            start();
        }
        
        /**
         * Connecte un client au serveur
         * 
         * @return si bien passer true, sinon false
         */
        private boolean connecter() {
            
            // Creer une connexion avec le serveur cible
            //
            while (true) {
                
                // Creer la socket support
                //
                try {
                    socketSupport = new Socket(nomServeur, port);
                }
                catch (Exception e) {}
                
                // Controler la validite de cette socket
                //
                if (socketSupport != null) break;
            }
            
            // Initialiser les flux entrant et sortant de la connexion
            //
            return initFlux(socketSupport);
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
            while (!statusEmission)
                Chrono.attendre(200);
            
            // Parcourir la liste courante des messages
            //
            Object msg = null;
            
            while (statusEmission) {
                
                if (listeMessagesE.size() != 0) {
                    
                    // Executer une operation atomatique d'envoi d'un message
                    //
                    synchronized (listeMessagesE) {
                        
                        // Extraire le message suivant
                        //
                        msg = (String) listeMessagesE.getFirst();
                        
                        // Envoyer le message courant
                        //
                        statusEmission = envoyerMessage(msg);
                        
                        // Retirer ce message de la liste
                        //
                        listeMessagesE.removeFirst();
                    }
                }
                else {
                    
                    // Attendre le message suivant
                    //
                    Chrono.attendre(100);
                }
            }
        }
    }
    
    /**
     * Echanges parametrables de messages avec des clients IP au moyen d'une
     * socket unique dediee
     * 
     * @version 0.3.0
     * @author C. Hulin
     */
    private class Serveur extends Thread {
        
        /**
         * Premier constructeur normal
         * 
         * @param nomThread
         */
        public Serveur(String nomThread) {
            
            super(nomThread);
            start();
        }
        
        /**
         * Attend la connexion d'un client
         */
        private boolean accepter() {
            
            // Creer la socket serveur
            //
            ServerSocket serveur;
            try {
                serveur = new ServerSocket(port);
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
        
        /**
         * Implementation de la methode run
         */
        public void run() {
            
            // Etablir la connexion avec le serveur cible
            //
            accepter();
            
            // Attendre la succession des messages du protocole de niveau
            // superieur
            //
            while (statusReception) {
                
                // Attendre le prochain message (appel bloquant)
                //
                attendreMessage();
            }
        }
    }
    
    /**
     * Classe interne privee Chrono
     * 
     * @version 0.0.0
     * @author C. Hulin
     */
    private static class Chrono {
        
        /**
         * Attendre n millisecondes, en bloquant le thread courant
         * 
         * @param n
         */
        private static void attendre(int n) {
            
            try {
                Thread.currentThread();
                Thread.sleep(n);
            }
            catch (InterruptedException e) {}
        }
    }
}