/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Composants reseaux generiques sous
 * TCP/IP
 * 
 * @edition A : reunion des classes externes ThreadEmetteur
 *          et ThreadRecepteur
 * 
 * @version 1.0.0
 * 
 *          preparation de la gestion multi clients du mode
 *          "serveur" de la classe NoeudG_TCP
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
 * Gestion d'une connexion TCP/IP client/serveur,
 * bidirectionnelle, flux duplex, mode "bytes", totalement
 * generique
 * 
 * @author C. Hulin
 * @version 1.0.0
 */
public class ConnexionG_TCP {
    final private ThreadRecepteur threadRecepteur;
    final private ThreadEmetteur threadEmetteur;
    
    /**
     * Premier Constructeur normal
     * 
     * @param prefixeThreads
     * @param socketSupport
     */
    public ConnexionG_TCP(String prefixeThreads, Socket socketSupport) {
        
        // Creer le thread emetteur
        //
        // ATTENTION : respecter l'ordre de creation des
        // deux threads
        //
        threadEmetteur = new ThreadEmetteur(prefixeThreads, socketSupport);
        
        // Creer le thread recepteur
        //
        threadRecepteur = new ThreadRecepteur(prefixeThreads, socketSupport);
    }
    
    // ------ *** Methode debuterReception
    
    public void debuterReception() {
        threadRecepteur.debuterReception();
    }
    
    // ------ *** Methode obtenirMessagesRecus
    
    public LinkedList obtenirMessagesRecus() {
        return threadRecepteur.obtenirMessagesRecus();
    }
    
    // ------ *** Methode presenceMessageRecu
    
    public boolean presenceMessageRecu() {
        LinkedList listeMessages = threadRecepteur.obtenirMessagesRecus();
        int nbMessages = listeMessages.size();
        
        if (nbMessages != 0) return true;
        
        return false;
    }
    
    // ------ *** Methode retirerMessage
    
    public HashMap retirerMessage() {
        
        return threadRecepteur.retirerMessage();
    }
    
    // ------ *** Methode stopperReception
    
    public void stopperReception() {
        threadRecepteur.stopperReception();
    }
    
    // ------ *** Methode debuterEmission
    
    public void debuterEmission() {
        threadEmetteur.debuterEmission();
    }
    
    // ------ *** Methode ajouterMessage
    
    public void ajouterMessage(HashMap msg) {
        threadEmetteur.ajouterMessage(msg);
    }
    
    // ------ *** Methode envoyerMessage
    
    public void envoyerMessage(HashMap msg) {
        
        // Ajouter le message a la liste d'emission
        //
        threadEmetteur.ajouterMessage(msg);
    }
    
    // ------ *** Methode stopperEmission
    
    public void stopperEmission() {
        threadEmetteur.stopperEmission();
    }
    
    // ------ *** Methode fermer
    
    public void fermer() {
        
        // Fermer le flux sortant du thread emetteur
        //
        threadEmetteur.fermer();
        
        // Fermer le flux entrant du thread recepteur
        //
        threadRecepteur.fermer();
    }
    
    // -------------------------------- *** Classe interne
    // privee ThreadEmetteur
    
    private class ThreadEmetteur extends Thread {
        
        /**
         * Buffer sortie en mode "bytes"
         */
        private ObjectOutputStream bOut;
        
        /**
         * Socket d'emission
         */
        private Socket socketSupport;
        
        /**
         * Messages a envoyer
         */
        private LinkedList listeEmission;
        
        /**
         * Status autorisation a emettre
         */
        private boolean statusEmission;
        
        // ------ *** Constructeur normal
        
        private ThreadEmetteur(String prefixeThreads, Socket socketSupport) {
            
            // Invoquer un constructeur normal de la classe
            // mere
            //
            super(prefixeThreads + " - Emetteur");
            
            // Renseigner les attributs transmis par
            // parametre
            //
            this.socketSupport = socketSupport;
            
            // Initialiser l'environnement d'emission
            //
            listeEmission = new LinkedList();
            statusEmission = false;
            
            // Initialiser le flux sortant de la connexion
            //
            initFlux(socketSupport);
            
            // Temporiser pour permettre l'initialisation du
            // flux entrant
            //
            Chrono.attendre(300);
            
            // Demarrer le thread d'emission
            //
            start();
        }
        
        // ------ *** Accesseurs
        
        private LinkedList obtenirMessagesEmis() {
            return listeEmission;
        }
        
        private boolean obtenirStatusEmission() {
            return statusEmission;
        }
        
        private void debuterEmission() {
            statusEmission = true;
        }
        
        private void stopperEmission() {
            statusEmission = false;
        }
        
        private void ajouterMessage(HashMap msg) {
            listeEmission.add(msg);
        }
        
        private String obtenirMessage() {
            
            if (listeEmission.size() == 0) return null;
            return (String) listeEmission.getFirst();
        }
        
        // ----- *** Methode run
        
        public void run() {
            HashMap msg = null;
            
            // Attendre l'autorisation d'emettre
            //
            while (!statusEmission)
                Chrono.attendre(200);
            
            // Envoyer un ensemble des messages
            //
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
                        envoyerMessage(msg);
                        
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
            
            return true;
        }
        
        // ------ *** Methode envoyerMessage
        
        private boolean envoyerMessage(Object message) {
            
            // Controler la validite du flux de sortie
            //
            if (bOut == null) return false;
            
            // Ajouter systematiquement l'adresse IP du
            // noeud au message
            //
            HashMap msg = (HashMap) message;
            msg.put("adresseIP", socketSupport.getLocalAddress().getHostAddress());
            
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
        
        private void fermer() {
            
            // Fermer le flux sortant
            //
            try {
                bOut.close();
            }
            catch (Exception e) {}
            
            // Fermer la socket support
            //
            try {
                socketSupport.close();
            }
            catch (Exception e) {}
        }
    }
    
    // ------------------------------ *** Classe interne
    // privee ThreadRecepteur
    private class ThreadRecepteur extends Thread {
        /**
         * Buffer entree en mode "bytes"
         */
        private ObjectInputStream bIn;
        
        /**
         * Socket d'emission
         */
        private Socket socketSupport;
        
        /**
         * Messages recus
         */
        private LinkedList listeReception;
        
        /**
         * Status autorisation a recevoir
         */
        private boolean statusReception;
        
        // ------ *** Constructeur normal
        
        private ThreadRecepteur(String prefixeThreads, Socket socketSupport) {
            
            // Invoquer un constructeur normal de la classe
            // mere
            //	
            super(prefixeThreads + " - Recepteur");
            
            // Renseigner les attributs transmis par
            // parametre
            //
            this.socketSupport = socketSupport;
            
            // Initialiser l'environnement de reception
            //
            listeReception = new LinkedList();
            statusReception = false;
            
            // Initialiser le flux entrant de la connexion
            //
            initFlux(socketSupport);
            
            // Temporiser pour permettre l'initialisation du
            // flux sortant
            //
            Chrono.attendre(300);
            
            // Demarrer le thread de reception
            //
            start();
        }
        
        // ------ *** Accesseurs
        
        public LinkedList obtenirMessagesRecus() {
            return listeReception;
        }
        
        public boolean obtenirStatusReception() {
            return statusReception;
        }
        
        public void debuterReception() {
            statusReception = true;
        }
        
        public void stopperReception() {
            statusReception = false;
        }
        
        public HashMap retirerMessage() {
            HashMap msg = null;
            
            if (listeReception.size() == 0) return null;
            
            // Executer une operation atomique pour obtenir
            // le premier
            // message courant recu et le retirer de la
            // liste
            //
            synchronized (listeReception) {
                
                msg = (HashMap) listeReception.getFirst();
                
                listeReception.removeFirst();
            }
            
            // Restituer le resultat
            //
            return msg;
        }
        
        // ------ *** Methode run
        
        public void run() {
            
            // Attendre l'autorisation de recevoir
            //
            while (!statusReception)
                Chrono.attendre(200);
            
            // Attendre un ensemble de messages
            //
            while (statusReception) {
                
                // Attendre le message suivant (appel
                // bloquant)
                //
                attendreMessage();
            }
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
            if (streamIn == null) return false;
            
            // Creer le buffer d'entree
            //
            // ATTENTION : le constructeur est bloquant
            // jusqu'a la reception du
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
        
        // ------ *** Methode attendreMessage
        
        private void attendreMessage() {
            Object msg = null;
            
            // Recueillir l'ensemble des messages
            //
            while (statusReception) {
                
                // ATTENTION : la methode readObject leve
                // plusieurs types (classes)
                // d'exceptions suivant la nature du
                // probleme rencontre
                //
                try {
                    msg = bIn.readObject();
                    if (msg != null) break;
                }
                
                // Traiter le cas ou l'autre extremite de la
                // socket disparait sans
                // coordination prealable au niveau
                // applicatif (OSI - 7).
                //
                // Ce cas se produit quand l'objet "socket"
                // distant est detruit
                // (mort du thread distant par exemple)
                //                     
                catch (SocketException e) {}
                
                // Traiter le cas ou l'autre extremite ferme
                // la socket sans
                // coordination prealable au niveau
                // applicatif (OSI - 7)
                //
                catch (EOFException e) {}
                
                // Traiter le cas d'autres exceptions
                // relatives aux IO
                //
                catch (IOException e) {}
                
                // Traiter les autres cas d'exceptions
                //
                catch (Exception e) {}
                
                // Temporiser pour attendre le message
                // suivant
                //
                Chrono.attendre(100);
            }
            
            // Enregistrer le message courant dans la liste
            // des messages
            //
            listeReception.add(msg);
        }
        
        // ------ *** Methode fermer
        
        public void fermer() {
            
            // Fermer le flux entrant
            //
            try {
                bIn.close();
            }
            catch (Exception e) {}
            
            // Fermer la socket support
            //
            try {
                socketSupport.close();
            }
            catch (Exception e) {}
        }
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
