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
 *          preparation solution TP_5
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
 *          modification des retirer messages avec
 *          depouillement des informations basees sur les
 *          couches
 */
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Client TCP/IP totalement generique
 * 
 * @author C. Hulin
 * @version 1.1.0
 */
public class ClientG_TCP {
    final private Presentation niveauP;
    
    // ------ *** Constructeur normal
    
    public ClientG_TCP(String prefixeThreads, String nomServeur, int portServeur) {
        
        // Construire le niveau sous jacent de presentation
        //
        niveauP = new Presentation(prefixeThreads, nomServeur, portServeur);
    }
    
    // ------ *** Methode debuterReception
    
    public void debuterReception() {
        niveauP.debuterReception();
    }
    
    // ------ *** Methode presenceMessageRecu
    
    public boolean presenceMessageRecu() {
        
        return niveauP.presenceMessageRecu();
    }
    
    // ------ *** Methode retirerMessage
    
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
        
        // ------ *** Constructeur normal
        
        private Presentation(String prefixeThreads, String nomServeur, int portServeur) {
            
            // Construire le niveau session sous jacent
            //
            niveauS = new Session(prefixeThreads, nomServeur, portServeur);
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
        final private String nomServeur;
        final private int portCommunication;
        
        private Socket socketSupport;
        
        final private ConnexionG_TCP connexionTCP;
        
        // ------ *** Constructeur normal
        
        private Session(String prefixeThreads, String nomServeur, int portServeur) {
            
            // Renseigner les attributs transmis par
            // parametre
            //
            this.nomServeur = nomServeur;
            portCommunication = portServeur;
            
            // Etablir la connexion TCP/IP avec le serveur
            // cible
            //
            connecter();
            
            // Installer la connexion TCP/IP
            //
            connexionTCP = new ConnexionG_TCP(prefixeThreads, socketSupport);
        }
        
        // ------ *** Methode connecter
        
        private boolean connecter() {
            
            // Creer une connexion avec le serveur cible
            //
            while (true) {
                
                // Creer la socket support
                //
                try {
                    socketSupport = new Socket(nomServeur, portCommunication);
                }
                catch (Exception e) {}
                
                // Controler la validite de cette socket
                //
                if (socketSupport != null) break;
            }
            
            return true;
        }
        
        // ------ *** Methode debuterReception
        
        private void debuterReception() {
            connexionTCP.debuterReception();
        }
        
        // ------ *** Methode presenceMessageRecu
        
        private boolean presenceMessageRecu() {
            LinkedList listeMessages = connexionTCP.obtenirMessagesRecus();
            int nbMessages = listeMessages.size();
            
            if (nbMessages != 0) return true;
            
            return false;
        }
        
        // ------ *** Methode retirerMessage
        
        private HashMap retirerMessage() {
            
            HashMap tampon = connexionTCP.retirerMessage();
            tampon.remove("adresseIP");
            return tampon;
        }
        
        // ------ *** Methode stopperReception
        
        private void stopperReception() {
            connexionTCP.stopperReception();
        }
        
        // ------ *** Methode debuterEmission
        
        private void debuterEmission() {
            connexionTCP.debuterEmission();
        }
        
        // ------ *** Methode envoyerMessage
        
        private void envoyerMessage(HashMap msg) {
            HashMap msgclone;
            
            // Dupliquer le dictionnaire recu
            //
            msgclone = (HashMap) msg.clone();
            
            // Renseigner le champ "adresseIP"
            //
            msgclone.put("adresseIP", socketSupport.getInetAddress().getHostAddress());
            
            // Ajouter le message a la liste d'emission
            //
            connexionTCP.ajouterMessage(msgclone);
        }
        
        // ------ *** Methode stopperEmission
        
        private void stopperEmission() {
            connexionTCP.stopperEmission();
        }
        
        // ------ *** Methode fermer
        
        private void fermer() {
            
            // Fermer la connexion TCP sous jacente
            //
            connexionTCP.fermer();
        }
    }
}