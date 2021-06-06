/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Composants reseaux generiques sous
 * TCP/IP
 * 
 * @edition "Draft" : reunion des classes ServeurG_TCP et
 *          ClientG_TCP, qui deviennent des classes internes
 *          privees
 * 
 * @version 0.0.0
 * 
 *          version limitee a un seul client et un seul
 *          echange bidirectionnel (mode "bytes" / client
 *          maitre)
 * 
 * @version 0.1.0
 * 
 *          introduction de deux classes internes privees
 *          pour separer la partie "emission" et la partie
 *          "reception" sous forme de deux threads
 *          independants
 * 
 * @version 0.2.0
 * 
 *          modification de la methode "envoyerMessage" pour
 *          ajouter automatiquement l'adresse IP a chaque
 *          message a emettre par le noeud
 * 
 * @version 0.3.0
 * 
 *          transfert des attributs d'emission et de
 *          reception (listes de messages et status) dans
 *          les threads concernes et ajout des accesseurs
 *          induits
 * 
 * @version 0.4.0
 * 
 *          externalisation des classes internes sous forme
 *          de classes satellites
 * 
 * @edition A : mise en place de la gestion en parallele de
 *          N clients pour les noeuds en mode "serveur"
 * 
 * @version 1.0.0
 * 
 *          introduction des niveaux "presentation" et
 *          "session" dans les deux modes ("client" et
 *          "serveur") et modification de l'interface
 *          applicative (niveau 7)
 * 
 * @version 1.1.0
 * 
 *          reunion des classes ThreadEmetteur et
 *          ThreadRecepteur dans une classe satellite
 *          ConnexionG_TCP
 * 
 * @version 1.2.0
 * 
 *          extension des constructeurs du mode "serveur"
 *          pour introduire la gestion de N clients en
 *          parallele et modification en consequence des
 *          classes internes Session et Presentation
 */
import java.util.HashMap;

/**
 * Echanges parametrables de messages sous protocole TCP/IP
 * au moyen d'une socket unique dediee
 * 
 * @author C. Hulin
 * @version 1.2.0
 */
public class NoeudG_TCP {
    
    /**
     * Nom du serveur
     */
    final private String nomServeur;
    
    /**
     * Port d'ecoute
     */
    final private int portCommunication;
    
    /**
     * Mode serveur
     */
    final private ServeurG_TCP serveurInterne;
    
    /**
     * Mode client
     */
    final private ClientG_TCP clientInterne;
    
    /**
     * Premier constructeur normal pour serveur
     * 
     * @param prefixe
     * @param port
     */
    public NoeudG_TCP(String prefixe, int port) {
        
        // Renseigner les attributs du noeud recus par
        // parametre
        // 
        portCommunication = port;
        
        // Renseigner les autres attributs
        // 
        nomServeur = null;
        clientInterne = null;
        
        // Creer l'instance de la classe applicative
        // ServeurG_TCP
        //
        serveurInterne = new ServeurG_TCP(prefixe, port);
    }
    
    /**
     * Second Constructeur normal pour le cas d'un serveur
     * 
     * @param prefixe
     * @param port
     * @param nbClients
     */
    public NoeudG_TCP(String prefixe, int port, int nbClients) {
        
        // Renseigner les attributs du noeud recus par
        // parametre
        // 
        portCommunication = port;
        
        // Renseigner les autres attributs
        // 
        nomServeur = null;
        clientInterne = null;
        
        // Creer l'instance de la classe applicative
        // ServeurG_TCP
        //
        serveurInterne = new ServeurG_TCP(prefixe, port, nbClients);
    }
    
    /**
     * Troisieme constructeur normal pour le cas d'un client
     * 
     * @param prefixe
     * @param host
     * @param port
     */
    public NoeudG_TCP(String prefixe, String host, int port) {
        
        // Renseigner les attributs du noeud recus par
        // parametre
        //
        nomServeur = host;
        portCommunication = port;
        
        // Renseigner les autres attributs
        // 
        serveurInterne = null;
        
        // Creer l'instance de la classe applicative
        // ClientG_TCP
        //
        clientInterne = new ClientG_TCP(prefixe, host, port);
    }
    
    /**
     * Commence a receptionner des messages
     */
    public void debuterReception() {
        
        if (clientInterne != null) clientInterne.debuterReception();
        else serveurInterne.debuterReception();
    }
    
    /**
     * Permet savoir si un messages a ete receptionner
     * 
     * @return true si un message est present, false sinon.
     */
    public boolean presenceMessageRecu() {
        
        if (clientInterne != null) return clientInterne.presenceMessageRecu();
        else return serveurInterne.presenceMessageRecu();
        
    }
    
    /**
     * Permet de retirer le message receptionner
     * 
     * @return message recu
     */
    public HashMap retirerMessage() {
        
        if (clientInterne != null) {
            return clientInterne.retirerMessage();
        }
        else {
            return serveurInterne.retirerMessage();
        }
    }
    
    /**
     * Arrete de receptionner des messages
     */
    public void stopperReception() {
        
        if (clientInterne != null) clientInterne.stopperReception();
        else serveurInterne.stopperReception();
    }
    
    /**
     * Commence a emettre des messages
     */
    public void debuterEmission() {
        
        if (clientInterne != null) clientInterne.debuterEmission();
        else serveurInterne.debuterEmission();
    }
    
    /**
     * Permet d'envoyer un message en broadcast
     * 
     * @param commande
     * @param parametres
     */
    public void envoyerMessage(String commande, HashMap parametres) {
        
        if (clientInterne != null) {
            clientInterne.envoyerMessage(commande, parametres);
        }
        else {
            serveurInterne.envoyerMessage(commande, parametres);
        }
    }
    
    /**
     * Permet d'envoyer un message a un destinataire precis
     * 
     * @param destinataire
     * @param commande
     * @param parametres
     */
    public void envoyerMessage(String destinataire, String commande, HashMap parametres) {
        
        serveurInterne.envoyerMessage(destinataire, commande, parametres);
    }
    
    /**
     * Arrete d'emettre des messages
     */
    public void stopperEmission() {
        
        if (clientInterne != null) clientInterne.stopperEmission();
        else serveurInterne.stopperEmission();
    }
    
    /**
     * Ferme proprement le noeud
     */
    public void fermer() {
        
        if (clientInterne != null) clientInterne.fermer();
        else serveurInterne.fermer();
    }
}