/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Composants reseaux generiques sous
 * TCP/IP
 * 
 * @edition "Draft" : reception de messages en mode
 *          caracteres
 * 
 * @version 0.0.0
 * 
 *          version initiale avec un seul client possible et
 *          une capacite limitee a la reception d'un seul
 *          message
 * 
 * @version 0.2.0
 * 
 *          ajout attribut "statusReception" pour
 *          telecommander le debut et la fin de reception
 *          des messages + accesseurs debuterReception et
 *          stopperReception + modification induite de la
 *          methode run
 * 
 * @edition A : echanges bidirectionnels de messages en mode
 *          "bytes"
 * 
 * @version 1.0.0
 * 
 *          modification des attributs et de la methode
 *          "iniflux" pour gerer la reception par
 *          serialisation d'objets dans la socket sous
 *          jacente
 * 
 * @version 1.1.0
 * 
 *          mise en place d'une voie de retour limitee a
 *          l'envoi d'une reponse au premier message attendu
 */
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Echanges parametrables de messages avec des clients IP au
 * moyen d'une socket unique dediee
 * 
 * @author C. Hulin
 * @version 1.1.0
 */
public class ServeurG_TCP extends Thread {
    private int portReception;
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
    
    public ServeurG_TCP(String nomThread) {
        
        super(nomThread);
        portReception = 8080;
        
        listeEmission = new LinkedList();
        statusEmission = false;
        
        listeReception = new LinkedList();
        statusReception = false;
        
        start();
    }
    
    // ------ *** Second constructeur normal
    
    public ServeurG_TCP(String nomThread, int port) {
        
        super(nomThread);
        portReception = port;
        
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
    
    // --- Partie emission
    
    public void ajouterMessage(HashMap msg) {
        listeEmission.add(msg);
    }
    
    public LinkedList obtenirMessagesEmis() {
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
    
    // ------ *** Methode main
    
    public static void main(String[] args) {
        
        // Creer et demarrer un serveur IP
        //
        ServeurG_TCP serveurTCP = new ServeurG_TCP("Serveur_1");
        System.out.print("* Creation et demarrage du serveur TCP/IP ");
        System.out.println("(V 1.1.0)\n");
        
        // Autoriser la reception des messages
        //
        serveurTCP.debuterReception();
        Chrono.attendre(100);
        
        // Attendre les messages en provenance du client
        // unique
        //
        HashMap msg = null;
        HashMap reponse = null;
        
        while (serveurTCP.obtenirStatusReception()) {
            
            // Attendre la reception d'un nouveau message
            //
            while (serveurTCP.obtenirMessagesRecus().size() == 0)
                Chrono.attendre(100);
            
            // Retirer le message courant de la liste de
            // reception
            //
            msg = serveurTCP.retirerMessage();
            
            // Visualiser la commande recue
            // 
            System.out.print("<-- Commande recue : ");
            System.out.println((String) msg.get("commande"));
            
            // Preparer la reponse
            //
            reponse = new HashMap();
            reponse.put("commande", "DEMARRER");
            
            // Ajouter la reponse a la liste des messages a
            // envoyer
            //
            serveurTCP.ajouterMessage(reponse);
            
            // Autoriser l'emission de messages
            //
            serveurTCP.debuterEmission();
            
            // Temporiser pour permettre l'emission de la
            // reponse
            //
            Chrono.attendre(200);
            
            // Interdire l'emission de messages
            //
            serveurTCP.stopperEmission();
        }
        
        // Stopper l'emission/reception des messages
        //
        serveurTCP.stopperReception();
        
        // Fermer les flux d'echange avec le client unique
        //
        serveurTCP.fermer();
    }
    
    // ------ *** Methode run
    
    public void run() {
        
        // Etablir la connexion avec le serveur cible
        //
        accepter();
        
        // Attendre le message courant du client
        //
        while (statusReception) {
            
            // Attendre le prochain message (appel bloquant)
            //
            attendreMessage();
            
            // Attendre l'autorisation d'emettre la reponse
            //
            while (!statusEmission)
                Chrono.attendre(200);
            
            // Parcourir la liste courante des messages
            //
            HashMap msg = null;
            
            while (statusEmission) {
                
                if (listeEmission.size() != 0) {
                    
                    // Executer une operation atomique
                    // d'envoi d'un message
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
