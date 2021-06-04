/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Composants reseaux generiques sous
 * TCP/IP
 * 
 * @edition "Draft" : emission de messages en mode
 *          caracteres
 * 
 * @version 0.0.0
 * 
 *          version initiale avec creation d'un thread
 *          autonome
 * 
 * @version 0.1.0
 * 
 *          introduction temporisation avant fermeture
 *          socket + attente d'au moins un message au
 *          demarrage
 * 
 * @version 0.2.0
 * 
 *          modification de la methode connecter pour
 *          autoriser un demarrage du client TCP/IP avant
 *          celui du serveur
 * 
 * @version 0.3.0
 * 
 *          ajout attribut "statusEmission" pour
 *          telecommander le debut et la fin d'emission des
 *          messages + accesseurs debuterEmission et
 *          stopperEmission + modification induite de la
 *          methode run
 * 
 * @edition A : echanges bidirectionnels de messages en mode
 *          "bytes"
 * 
 * @version 1.0.0
 * 
 *          modification des attributs et de la methode
 *          "iniflux" pour gerer l'emission par
 *          serialisation d'objets dans la socket sous
 *          jacente
 * 
 * @version 1.1.0
 * 
 *          reunion des classes ThreadEmetteur et
 *          ThreadRecepteur dans une classe satellite
 *          ConnexionG_TCP
 * 
 * @version 1.2.0
 * 
 *          ajout des attributs et des methodes necessaires
 *          a la reception d'un message de retour
 */
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Echanges parametrables de messages avec un serveur TCP/IP
 * au moyen d'une socket unique dediee
 * 
 * @author C. Hulin
 * @version 1.2.0
 */
public class ClientG_TCP extends Thread {
    private String nomServeur;
    private int portServeur;
    private Socket socketSupport;
    private ObjectInputStream bIn; // Buffer entree en mode
    // "bytes"
    private ObjectOutputStream bOut; // Buffer sortie en
    // mode "bytes"
    
    private LinkedList listeEmission; // messages a envoyer
    private boolean statusEmission; // Status autorisation a
    // emettre
    
    private LinkedList listeReception; // messages recus
    private boolean statusReception; // Status autorisation
    
    // a recevoir
    
    // ------ *** Premier constructeur normal
    
    public ClientG_TCP(String nomThread) {
        
        super(nomThread);
        nomServeur = "localHost";
        portServeur = 8080;
        
        listeEmission = new LinkedList();
        statusEmission = false;
        
        listeReception = new LinkedList();
        statusReception = false;
        
        start();
    }
    
    // ------ *** Second constructeur normal
    
    public ClientG_TCP(String nomThread, String host, int port) {
        
        super(nomThread);
        nomServeur = host;
        portServeur = port;
        
        listeEmission = new LinkedList();
        statusEmission = false;
        
        listeReception = new LinkedList();
        statusReception = false;
        
        start();
    }
    
    // ------ *** Accesseurs
    
    public Socket obtenirSocket() {
        return socketSupport;
    }
    
    // --- Partie emission
    
    public void ajouterMessage(HashMap msg) {
        listeEmission.add(msg);
    }
    
    public LinkedList obtenirMessages() {
        return listeEmission;
    }
    
    public String obtenirMessage() {
        
        if (listeEmission.size() == 0) return null;
        return (String) listeEmission.getFirst();
    }
    
    public void debuterEmission() {
        statusEmission = true;
    }
    
    public void stopperEmission() {
        statusEmission = false;
    }
    
    // --- Partie reception
    
    public LinkedList obtenirMessagesRecus() {
        return listeReception;
    }
    
    public boolean obtenirStatusReception() {
        return statusReception;
    }
    
    public HashMap retirerMessage() {
        HashMap msg = null;
        
        if (listeReception.size() == 0) return null;
        
        // Executer une operation atomique pour obtenir le
        // premier
        // message courant recu et le retirer de la liste
        //
        synchronized (listeReception) {
            
            msg = (HashMap) listeReception.getFirst();
            
            listeReception.removeFirst();
        }
        
        // Restituer le resultat
        //
        return msg;
    }
    
    public void debuterReception() {
        statusReception = true;
    }
    
    public void stopperReception() {
        statusReception = false;
    }
    
    // ------ *** Methode main
    
    public static void main(String[] args) {
        
        // Creer et demarrer un client TCP
        //
        ClientG_TCP clientTCP = new ClientG_TCP("Client_1");
        System.out.print("* Creation et demarrage du client TCP/IP ");
        System.out.println("V 1.1.0\n");
        
        // Attendre la mise en service du serveur
        //
        while (clientTCP.obtenirSocket() == null)
            Chrono.attendre(200);
        System.out.println("* Client connecte");
        System.out.println();
        
        // Obtenir la socket support
        //
        Socket socketSupport = clientTCP.obtenirSocket();
        
        // Construire le message a envoyer au serveur
        //
        HashMap msg = new HashMap();
        
        msg.put("commande", "DECLARER");
        msg.put("pseudo", "Java_2010");
        msg.put("adresseIP", socketSupport.getLocalAddress().getHostAddress());
        
        // Ajouter le message courant a la liste des
        // messages a envoyer
        //
        clientTCP.ajouterMessage(msg);
        
        // Autoriser l'emission des messages
        //
        clientTCP.debuterEmission();
        Chrono.attendre(300);
        
        // Temporiser pour l'envoi du message par le thread
        // secondaire
        //
        Chrono.attendre(100);
        
        // Visualiser la commande transmise
        //
        System.out.println("--> Commande envoyee  : " + "DECLARER");
        
        // Stopper l'emission de messages
        //
        clientTCP.stopperEmission();
        
        // Autoriser la reception de messages
        //
        clientTCP.debuterReception();
        Chrono.attendre(50);
        
        // Attendre la reponse du serveur
        //
        while (clientTCP.obtenirMessagesRecus().size() == 0) {
            Chrono.attendre(100);
        }
        
        // Retirer la reponse du serveur
        //
        HashMap reponse = clientTCP.retirerMessage();
        
        // Visualiser la reponse du serveur
        // 
        System.out.print("<-- Commande recue    : ");
        System.out.println((String) reponse.get("commande"));
        System.out.println();
        
        // Stopper la reception des messages
        //
        clientTCP.stopperReception();
        Chrono.attendre(100);
        
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
        
        // Attendre l'autorisation d'emettre
        //
        while (!statusEmission)
            Chrono.attendre(200);
        
        // Parcourir la liste courante des messages
        //
        HashMap msg = null;
        
        while (statusEmission) {
            
            if (listeEmission.size() != 0) {
                
                // Executer une operation atomique d'envoi
                // d'un message
                //
                synchronized (listeEmission) {
                    
                    // Extraire le message suivant
                    //
                    msg = (HashMap) listeEmission.getFirst();
                    
                    // Envoyer le message courant
                    //
                    statusEmission = envoyerMessage(msg);
                    
                    // Retirer ce message de la liste
                    //
                    listeEmission.removeFirst();
                }
            }
            
            else {
                
                // Temporiser pour attendre le message
                // suivant
                //
                Chrono.attendre(100);
            }
        }
        
        // Attendre la reponse du serveur
        //
        while (statusReception) {
            
            // Attendre le prochain message (appel bloquant)
            //
            attendreMessage();
        }
    }
    
    // ------ *** Methode connecter
    
    private boolean connecter() {
        
        // Creer une connexion avec le serveur cible
        //
        while (true) {
            
            // Creer la socket support
            //
            try {
                socketSupport = new Socket(nomServeur, portServeur);
            }
            catch (Exception e) {}
            
            // Controler la validite de cette socket
            //
            if (socketSupport != null) break;
        }
        
        // Initialiser les flux entrant et sortant de la
        // connexion
        //
        return initFlux(socketSupport);
    }
    
    // ------ *** Methode initFlux
    
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
        if (streamOut == null) return false;
        
        // Creer le buffer de sortie
        //
        try {
            bOut = new ObjectOutputStream(streamOut);
        }
        catch (Exception e) {
            return false;
        }
        if (bOut == null) return false;
        
        // Creer le flux d'entree
        //
        InputStream streamIn = null;
        try {
            streamIn = s.getInputStream();
        }
        catch (Exception e) {
            return false;
        }
        if (streamIn == null) return false;
        
        // Creer le buffer d'entree
        //
        // ATTENTION : le constructeur est bloquant jusqu'a
        // la reception du
        // premier objet (message)
        //
        try {
            bIn = new ObjectInputStream(streamIn);
        }
        catch (Exception e) {
            return false;
        }
        if (bIn == null) return false;
        
        return true;
    }
    
    // --- *** Methode envoyerMessage
    
    public boolean envoyerMessage(Object msg) {
        
        // Controler la validite du flux de sortie
        //
        if (bOut == null) return false;
        
        // Transferer le message dans le flux de sortie
        //
        try {
            bOut.writeObject(msg);
        }
        catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    // --- *** Methode attendreMessage
    
    public void attendreMessage() {
        Object msg = null;
        
        // ATTENTION : la methode readObject leve plusieurs
        // types (classes)
        // d'exceptions suivant la nature du probleme
        // rencontre
        //
        
        while (true) {
            try {
                msg = bIn.readObject();
                if (msg != null) break;
            }
            
            // Traiter le cas ou l'autre extremite de la
            // socket disparait sans
            // coordination prealable au niveau applicatif
            // (OSI - 7).
            //
            // Ce cas se produit quand l'objet "socket"
            // distant est detruit
            // (mort du thread distant par exemple)
            //                     
            catch (SocketException e) {}
            
            // Traiter le cas ou l'autre extremite ferme la
            // socket sans
            // coordination prealable au niveau applicatif
            // (OSI - 7)
            //
            catch (EOFException e) {}
            
            // Traiter le cas d'autres exceptions relatives
            // aux IO
            //
            catch (IOException e) {}
            
            // Traiter les autres cas d'exceptions
            //
            catch (Exception e) {}
            
            // Temporiser pour attendre le message suivant
            
            Chrono.attendre(100);
        }
        
        // Enregistrer le message courant dans la liste des
        // messages
        //
        listeReception.add(msg);
    }
    
    // ------ *** Methode fermer
    
    public void fermer() {
        
        try {
            bIn.close();
            bOut.close();
            socketSupport.close();
        }
        catch (Exception e) {}
    }
    
    // ------------------------------------- *** Classe
    // interne privee Chrono
    
    private static class Chrono {
        
        private static void attendre(int tms) {
            
            // Attendre tms millisecondes, en bloquant le
            // thread courant
            //
            try {
                Thread.currentThread();
                Thread.sleep(tms);
            }
            catch (InterruptedException e) {}
        }
    }
}
