/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Composants reseaux generiques sous
 * TCP/IP
 * 
 * @edition "Draft" : externalisation de la classe interne
 *          de meme nom depuis la classe principale
 *          NoeudG_TCP
 * 
 * @version 0.0.0
 * 
 *          gestion d'un client unique
 * 
 * @edition A : mise en place des niveaux "Session" et
 *          "Presentation"
 * 
 * @version 1.0.0
 * 
 *          introduction des classes internes privees
 *          Session et Presentation
 * 
 * @version 1.1.0
 * 
 *          modification des constructeurs des classes
 *          internes Session et Presentation pour prendre en
 *          compte la gestion de N clients en parallele
 * 
 * @version 1.2.0
 * 
 *          ajout d'une classe interne a Session faisant
 *          office de DNS + gestion de multi client
 */
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Serveur TCP/IP totalement generique
 * 
 * @author C. Hulin
 * @version 1.2.0
 */
public class ServeurG_TCP {
    final private Presentation niveauP;
    
    // ------ *** Premier constructeur normal
    
    public ServeurG_TCP(String prefixeThreads, int portServeur) {
        
        // Construire le niveau sous jacent de presentation
        //
        niveauP = new Presentation(prefixeThreads, portServeur);
    }
    
    // ------ *** Second constructeur normal
    
    public ServeurG_TCP(String prefixeThreads, int portServeur, int nbClients) {
        
        // Construire le niveau sous jacent de presentation
        //
        niveauP = new Presentation(prefixeThreads, portServeur, nbClients);
    }
    
    // ------ *** Methode debuterReception
    
    public void debuterReception() {
        niveauP.debuterReception();
    }
    
    // ------ *** Methode presenceMessageRecu
    
    public boolean presenceMessageRecu() {
        
        return niveauP.presenceMessageRecu();
    }
    
    // ------ *** Methodes retirerMessage
    
    public HashMap retirerMessage() {
        
        return niveauP.retirerMessage();
    }
    
    // ------ *** Methode stopperReception
    
    public void stopperReception() {
        niveauP.stopperReception();
    }
    
    // ------ *** Methode debuterEmission
    
    public void debuterEmission() {
        niveauP.debuterEmission();
    }
    
    // ------ *** Methodes envoyerMessage
    
    public void envoyerMessage(String commande, HashMap parametres) {
        
        niveauP.envoyerMessage(commande, parametres);
    }
    
    public void envoyerMessage(String destinataire, String commande, HashMap parametres) {
        
        niveauP.envoyerMessage(destinataire, commande, parametres);
    }
    
    // ------ *** Methode stopperEmission
    
    public void stopperEmission() {
        niveauP.stopperEmission();
    }
    
    // ------ *** Methode fermer
    
    public void fermer() {
        
        // Fermer le niveau presentation sous jacent
        //
        niveauP.fermer();
    }
    
    // ----------------------------------- *** Classe
    // interne privee Presentation
    private class Presentation {
        private Session niveauS;
        
        // ------ *** Premier constructeur normal
        
        private Presentation(String prefixeThreads, int portServeur) {
            
            // Construire le niveau session sous jacent
            //
            niveauS = new Session(prefixeThreads, portServeur);
        }
        
        // ------ *** Second constructeur normal
        
        private Presentation(String prefixeThreads, int portServeur, int nbClients) {
            
            // Construire le niveau session sous jacent
            //
            niveauS = new Session(prefixeThreads, portServeur, nbClients);
        }
        
        // ------ *** Methode debuterReception
        
        private void debuterReception() {
            niveauS.debuterReception();
        }
        
        // ------ *** Methode presenceMessageRecu
        
        private boolean presenceMessageRecu() {
            
            return niveauS.presenceMessageRecu();
        }
        
        // ------ *** Methode retirerMessage
        
        private HashMap retirerMessage() {
            
            HashMap tampon = niveauS.retirerMessage();
            if (tampon.containsKey("destinataire")) tampon.remove("destinataire");
            return tampon;
        }
        
        // ------ *** Methode stopperReception
        
        private void stopperReception() {
            niveauS.stopperReception();
        }
        
        // ------ *** Methode debuterEmission
        
        private void debuterEmission() {
            niveauS.debuterEmission();
        }
        
        // ------ *** Methodes envoyerMessage
        
        private void envoyerMessage(String commande, HashMap parametres) {
            HashMap msg;
            
            // Dupliquer le dictionnaire recu
            //
            msg = (HashMap) parametres.clone();
            
            // Renseigner le champ "commande"
            //
            msg.put("commande", commande);
            
            // Transmettre le message
            //
            niveauS.envoyerMessage(msg);
        }
        
        private void envoyerMessage(String destinataire, String commande, HashMap parametres) {
            HashMap msg;
            
            // Dupliquer le dictionnaire recu
            //
            msg = (HashMap) parametres.clone();
            
            // Renseigner les champs "destinataire" et
            // "commande"
            //
            msg.put("destinataire", destinataire);
            msg.put("commande", commande);
            
            // Transmettre le message
            //
            niveauS.envoyerMessage(msg);
        }
        
        // ------ *** Methode stopperEmission
        
        private void stopperEmission() {
            niveauS.stopperEmission();
        }
        
        // ------ *** Methode fermer
        
        private void fermer() {
            
            // Fermer le niveau session sous jacent
            //
            niveauS.fermer();
        }
    }
    
    // ----------------------------------- *** Classe
    // interne privee Session
    
    private class Session {
        final private int portCommunication;
        final private String prefixeThreads;
        
        private DNS DNSServeur;
        
        // ------ *** Premier constructeur normal
        
        private Session(String prefixe, int portServeur) {
            
            // Renseigner les attributs transmis par
            // parametre
            //
            this.prefixeThreads = prefixe;
            portCommunication = portServeur;
            
            // Initialiser le DNS
            //
            DNSServeur = new DNS();
            
            // Etablir la connexion TCP/IP avec le serveur
            // cible
            //
            accepter();
        }
        
        // ------ *** Second constructeur normal
        
        private Session(String prefixe, int portServeur, int nbClients) {
            
            // Renseigner les attributs transmis par
            // parametre
            //
            this.prefixeThreads = prefixe;
            portCommunication = portServeur;
            
            // Initialiser le DNS
            //
            DNSServeur = new DNS();
            
            // Etablir la connexion TCP/IP avec les clients
            // cibles
            //
            accepter(nbClients);
        }
        
        // ------ *** Methodes accepter
        
        private boolean accepter() {
            
            // Creer la socket serveur
            //
            ServerSocket serveur;
            try {
                serveur = new ServerSocket(portCommunication);
            }
            catch (Exception e) {
                return false;
            }
            
            // Attendre la connexion du client
            //
            Socket client;
            try {
                client = serveur.accept();
            }
            catch (Exception e) {
                return false;
            }
            
            // Creer la connexion pour le client
            //
            ConnexionG_TCP connexion = new ConnexionG_TCP(prefixeThreads, client);
            System.out.println("=== Connexion realisee");
            
            // Ajout les informations dans le DNS
            //
            String IP = client.getLocalAddress().getHostAddress();
            ajouterIP(IP, connexion);
            
            return true;
        }
        
        private boolean accepter(int nbClients) {
            
            // Creer la socket serveur
            //
            ServerSocket serveur;
            try {
                serveur = new ServerSocket(portCommunication, nbClients);
            }
            catch (Exception e) {
                return false;
            }
            
            // Attendre la sollicitation de tous les clients
            //
            Socket client;
            for (int i = 1; i <= nbClients; i++) {
                
                // Attendre la sollicitation d'un client
                //
                try {
                    client = serveur.accept();
                }
                catch (Exception e) {
                    return false;
                }
                
                // Creer la connexion pour le client courant
                //
                ConnexionG_TCP connexion = new ConnexionG_TCP(prefixeThreads, client);
                
                String IP = client.getInetAddress().getHostAddress();
                
                if (ajouterIP(IP, connexion))

                System.out.println("=== Connexion " + i + " : realisee");
            }
            
            return true;
        }
        
        // ------ *** Methode ajouterIP
        
        private boolean ajouterIP(String IP, ConnexionG_TCP connexion) {
            return DNSServeur.ajouterConnexion(IP, connexion);
        }
        
        // ------ *** Methode ajouterPseudo
        
        private boolean ajouterPseudo(String pseudo) {
            return DNSServeur.ajouterPseudo(pseudo);
        }
        
        // ------ *** Methode debuterReception
        
        private void debuterReception() {
            LinkedList connexions = DNSServeur.obtenirConnexions();
            Iterator i = connexions.iterator();
            
            while (i.hasNext()) {
                ((ConnexionG_TCP) i.next()).debuterReception();
            }
        }
        
        // ------ *** Methode presenceMessageRecu
        
        private boolean presenceMessageRecu() {
            LinkedList connexions = DNSServeur.obtenirConnexions();
            Iterator i = connexions.iterator();
            LinkedList listeMessages;
            int nbMessages;
            
            while (i.hasNext()) {
                
                listeMessages = ((ConnexionG_TCP) i.next()).obtenirMessagesRecus();
                nbMessages = listeMessages.size();
                
                if (nbMessages != 0) return true;
            }
            
            return false;
        }
        
        // ------ *** Methode retirerMessage
        
        private HashMap retirerMessage() {
            LinkedList connexions;
            Iterator i;
            HashMap tampon = null;
            String adresseIP;
            
            connexions = DNSServeur.obtenirConnexions();
            i = connexions.iterator();
            
            if (!presenceMessageRecu()) return null;
            
            while (i.hasNext()) {
                
                tampon = ((ConnexionG_TCP) i.next()).retirerMessage();
                if (tampon != null) {
                    
                    adresseIP = (String) tampon.remove("adresseIP");
                    if (DNSServeur.obtenirPseudo(adresseIP) != null) {
                        tampon.put("pseudo", DNSServeur.obtenirPseudo(adresseIP));
                    }
                    break;
                }
            }
            
            return tampon;
        }
        
        // ------ *** Methode stopperReception
        
        private void stopperReception() {
            LinkedList connexions = DNSServeur.obtenirConnexions();
            Iterator i = connexions.iterator();
            
            while (i.hasNext()) {
                ((ConnexionG_TCP) i.next()).stopperReception();
            }
        }
        
        // ------ *** Methode debuterEmission
        
        private void debuterEmission() {
            LinkedList connexions = DNSServeur.obtenirConnexions();
            Iterator i = connexions.iterator();
            
            while (i.hasNext()) {
                ((ConnexionG_TCP) i.next()).debuterEmission();
            }
        }
        
        // ------ *** Methode envoyerMessage
        
        private void envoyerMessage(HashMap msg) {
            
            // Verifier le cas d'un message
            //
            if (msg == null) return;
            
            // Ajout du message au destinataire
            //
            if (msg.containsKey("destinataire")) {
                
                // Verifier que le destinataire est deja
                // ficher dans le DNS
                //
                String ipDestinataire = DNSServeur.obtenirIP_Pseudo((String) msg
                        .get("destinataire"));
                
                // Cas d'un destinataire non enregistrer
                //
                if (ipDestinataire == null) {
                    ajouterPseudo((String) msg.get("destinataire"));
                    ipDestinataire = DNSServeur.obtenirIP_Pseudo((String) msg.get("destinataire"));
                }
                
                // Ajouter l'ip du client en entete du
                // message
                //
                msg.put("adresseIP", ipDestinataire);
                
                // Recuperer la connexion du destinataire
                //
                ConnexionG_TCP connexion = DNSServeur.obtenirConnexion(ipDestinataire);
                
                // Ajouter le message a la liste d'emission
                //
                connexion.ajouterMessage(msg);
            }
            
            // Ajout du message en broadcast a tous les
            // destinataires
            //
            else {
                
                LinkedList msgs, connexions, IPs;
                HashMap tampon = new HashMap();
                Iterator i, j;
                
                // Obtenir la liste des ip des clients
                //
                IPs = DNSServeur.obtenirIPs();
                
                // Initialiser le parcours de la liste des
                // ip
                //
                i = IPs.iterator();
                msgs = new LinkedList();
                
                while (i.hasNext()) {
                    
                    // Ajouter en entete l'ip de chaque
                    // destinataire a chaque message
                    //
                    tampon.clear();
                    tampon = (HashMap) msg.clone();
                    tampon.put("adresseIP", i.next());
                    msgs.add(tampon.clone());
                }
                
                // Initialiser le parcours de la liste des
                // connexions
                //
                connexions = DNSServeur.obtenirConnexions();
                i = connexions.iterator();
                j = msgs.iterator();
                
                while (i.hasNext()) {
                    
                    // Ajouter le message propres a chaque
                    // client a sa liste d'emission
                    //
                    ((ConnexionG_TCP) i.next()).envoyerMessage((HashMap) j.next());
                }
            }
        }
        
        // ------ *** Methode stopperEmission
        
        private void stopperEmission() {
            LinkedList connexions = DNSServeur.obtenirConnexions();
            Iterator i = connexions.iterator();
            
            while (i.hasNext()) {
                ((ConnexionG_TCP) i.next()).stopperEmission();
            }
        }
        
        // ------ *** Methode fermer
        
        private void fermer() {
            LinkedList connexions = DNSServeur.obtenirConnexions();
            Iterator i = connexions.iterator();
            
            while (i.hasNext()) {
                
                // Fermer la connexion
                //
                ((ConnexionG_TCP) i.next()).fermer();
            }
        }
        
        /**
         * 
         * @author C. Hulin
         * @version 1.0.0
         */
        private class DNS {
            private HashMap dictionnaireConnexion;
            private LinkedHashMap dictionnairePseudo;
            
            private DNS() {
                dictionnaireConnexion = new HashMap();
                dictionnairePseudo = new LinkedHashMap();
            }
            
            private boolean ajouterPseudo(String pseudo) {
                
                if (pseudo == null) return false;
                
                if (dictionnairePseudo.containsValue(pseudo)) return false;
                
                Iterator i = dictionnairePseudo.keySet().iterator();
                String cle;
                String associe;
                
                while (i.hasNext()) {
                    cle = (String) i.next();
                    associe = (String) dictionnairePseudo.get(cle);
                    if (associe == null) {
                        dictionnairePseudo.put(cle, pseudo);
                        break;
                    }
                }
                
                return true;
            }
            
            private boolean ajouterConnexion(String IP, ConnexionG_TCP connexion) {
                
                if (IP == null) return false;
                if (connexion == null) return false;
                
                if (dictionnaireConnexion.containsKey(IP)) return false;
                
                dictionnaireConnexion.put(IP, connexion);
                dictionnairePseudo.put(IP, null);
                
                return true;
            }
            
            private ConnexionG_TCP obtenirConnexion(String IP) {
                if (IP == null) return null;
                if (!dictionnaireConnexion.containsKey(IP)) return null;
                return (ConnexionG_TCP) dictionnaireConnexion.get(IP);
            }
            
            private LinkedList obtenirConnexions() {
                LinkedList listBroadcast = new LinkedList();
                Iterator i = dictionnaireConnexion.keySet().iterator();
                String cle;
                
                while (i.hasNext()) {
                    cle = (String) i.next();
                    listBroadcast.add(dictionnaireConnexion.get(cle));
                }
                
                return listBroadcast;
            }
            
            private LinkedList obtenirIPs() {
                LinkedList listBroadcast = new LinkedList();
                Iterator i = dictionnaireConnexion.keySet().iterator();
                
                while (i.hasNext()) {
                    listBroadcast.add((String) i.next());
                }
                
                return listBroadcast;
            }
            
            private String obtenirIP_Pseudo(String pseudo) {
                
                if (pseudo == null) return null;
                
                if (!dictionnairePseudo.containsValue(pseudo)) return null;
                
                Iterator i = dictionnairePseudo.keySet().iterator();
                String cle = null;
                
                while (i.hasNext()) {
                    cle = (String) i.next();
                    if (dictionnairePseudo.get(cle) != null) {
                        if (dictionnairePseudo.get(cle).equals(pseudo)) return cle;
                    }
                }
                return null;
            }
            
            private String obtenirPseudo(String IP) {
                if (IP == null) return null;
                if (!dictionnairePseudo.containsKey(IP)) return null;
                return (String) dictionnairePseudo.get(IP);
            }
            
        }
    }
}