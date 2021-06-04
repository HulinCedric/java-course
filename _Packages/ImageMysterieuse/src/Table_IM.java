/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Applications graphiques
 * 
 * @version 1.0.0
 * 
 *          version initiale fonctionnel
 */
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * IHM et protocole cote serveur du jeu l'image mysterieuse
 * 
 * @author C. Hulin
 * @version 1.0.0
 */
public class Table_IM {
    private Protocole_IM protocoleInterne;
    private ImageMysterieuse application;
    private int ll;
    private int cc;
    private String path;
    private String solution;
    private String prefixe;
    private int port;
    private int nbClients;
    
    /**
     * Constructeur normal
     * 
     * @param config
     */
    public Table_IM(HashMap config) {
        
        // Recuperer les donnees de configuration
        //
        setLine(config);
        setColumn(config);
        setPath(config);
        setPrefixe(config);
        setPort(config);
        setNbClients(config);
        
        // Creer la solution selon le fichier choisi
        //
        File file = new File(path);
        if (file == null || !file.exists()) return;
        solution = file.getName().substring(0, file.getName().indexOf(".jpg"));
        
        // Creer une instance du jeu l'image mysterieuse
        //
        application = new ImageMysterieuse(config);
        
        // Creer un noeud recevant tous les joueurs
        //
        NoeudG_TCP serveur = new NoeudG_TCP(prefixe, port, nbClients);
        
        // Lancer le protocole
        //
        protocoleInterne = new Protocole_IM(serveur, nbClients);
    }
    
    /**
     * setLine
     * 
     * @param config
     */
    private void setLine(HashMap config) {
        int defaultValue = 10;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            ll = defaultValue;
            return;
        }
        w = config.get("ll");
        if (w != null) value = ((Integer) w).intValue();
        ll = value;
    }
    
    /**
     * setColumn
     * 
     * @param config
     */
    private void setColumn(HashMap config) {
        int defaultValue = 15;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            cc = defaultValue;
            return;
        }
        w = config.get("cc");
        if (w != null) value = ((Integer) w).intValue();
        cc = value;
    }
    
    /**
     * setPath
     * 
     * @param config
     */
    private void setPath(HashMap config) {
        String defaultValue = "Fleurs/rose.jpg";
        String value = defaultValue;
        Object w;
        
        if (config == null) {
            path = defaultValue;
            return;
        }
        w = config.get("path");
        if (w != null) value = (String) w;
        path = value;
    }
    
    /**
     * setPrefixe
     * 
     * @param config
     */
    private void setPrefixe(HashMap config) {
        String defaultValue = "Table";
        String value = defaultValue;
        Object w;
        
        if (config == null) {
            prefixe = defaultValue;
            return;
        }
        w = config.get("Prefixe");
        if (w != null) value = (String) w;
        prefixe = value;
    }
    
    /**
     * setPort
     * 
     * @param config
     */
    private void setPort(HashMap config) {
        int defaultValue = 8080;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            port = defaultValue;
            return;
        }
        w = config.get("Port");
        if (w != null) value = ((Integer) w).intValue();
        port = value;
    }
    
    /**
     * setNbClients
     * 
     * @param config
     */
    private void setNbClients(HashMap config) {
        int defaultValue = 1;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            nbClients = defaultValue;
            return;
        }
        w = config.get("nbClients");
        if (w != null) value = ((Integer) w).intValue();
        nbClients = value;
    }
    
    /**
     * Main
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        // Charger le dictionnaire de configuration du jeu
        // l'image mysterieuse
        //      
        HashMap config = (HashMap) Config.load("ConfigImageMysterieuseTable", "1.0.0");
        
        // Creer une instance de table
        //
        Table_IM joueur = new Table_IM(config);
        
        // Executer le protocole
        //
        joueur.executer();
    }
    
    /**
     * Execute les regles de l'image mysterieuse
     */
    public void executer() {
        
        while (protocoleInterne.attendre())
            Chrono.attendre(100);
    }
    
    /**
     * Protocole du jeu l'image mysterieuse
     * 
     * @author C. Hulin
     * @version 1.0.0
     */
    private class Protocole_IM {
        private NoeudG_TCP serveurInterne;
        private LinkedHashMap clients;
        private int nbClientDeclarer;
        private int pseudoCourant;
        private boolean gagnant;
        
        /**
         * Constructeur normal
         * 
         * @param serveur
         * @param nbClients
         */
        public Protocole_IM(NoeudG_TCP serveur, int nbClients) {
            clients = new LinkedHashMap();
            nbClientDeclarer = 0;
            pseudoCourant = 1;
            gagnant = false;
            serveurInterne = serveur;
            serveurInterne.debuterEmission();
            serveurInterne.debuterReception();
            
            // Receptionner les declarations de pseudo des
            // joueurs
            //
            String pseudo;
            String commande;
            HashMap message = new HashMap();
            HashMap reponse = new HashMap();
            
            while (nbClientDeclarer != nbClients) {
                
                // Controler la reception d'un message
                //
                message.clear();
                while (!serveurInterne.presenceMessageRecu())
                    Chrono.attendre(100);
                
                // Retirer le message de la liste de
                // reception
                //
                message = serveurInterne.retirerMessage();
                
                // Recuperer la commande receptionner
                //
                commande = (String) message.get("commande");
                
                if (commande.equals("DECLARER")) {
                    
                    // Recuperer le pseudo envoyer par le
                    // joueur
                    //
                    reponse.clear();
                    pseudo = (String) message.get("pseudo");
                    reponse.put("pseudo", pseudo);
                    
                    // Visualiser la commande et le pseudo
                    // receptionner
                    //
                    System.out.println("<--- " + commande + " " + pseudo);
                    
                    // Envoyer la reponse au joueur
                    //
                    serveurInterne.envoyerMessage(pseudo, "DEMARRER", reponse);
                    
                    // Ajouter le pseudo du joueur dans la
                    // liste des joueurs
                    //
                    clients.put(new Integer(++nbClientDeclarer), pseudo);
                }
            }
            
            // Creer le message sur la configuration de la
            // table
            //
            HashMap parametres = new HashMap();
            parametres.put("path", path);
            parametres.put("ll", new Integer(ll));
            parametres.put("cc", new Integer(cc));
            
            // Envoyer la configuration de la table aux
            // joueurs
            //
            serveurInterne.envoyerMessage("CONFIGURER", parametres);
            System.out.println("---> CONFIGURER");
            
            // Creer le message de d'autorisation de jouer a
            // un joueur
            //
            parametres.clear();
            pseudo = (String) clients.get(new Integer(pseudoCourant++));
            parametres.put("pseudo", pseudo);
            
            // Envoyer l'autorisation de jouer a un joueur
            //
            serveurInterne.envoyerMessage(pseudo, "JOUER", parametres);
            System.out.println("---> JOUER " + pseudo);
        }
        
        /**
         * Attend l'action d'un joueur
         * 
         * @return s'il y a eu un gagnant
         */
        private boolean attendre() {
            HashMap message = new HashMap();
            
            // Verifie la reception d'un message
            //
            if (serveurInterne.presenceMessageRecu()) {
                
                // Retirer le message de la liste de
                // reception
                //
                message = serveurInterne.retirerMessage();
                
                // Envoyer une reponse adequate au joueur
                //
                envoyer((HashMap) message.clone());
            }
            
            // Restituer s'il y a eu un gagnant
            //
            return !gagnant;
        }
        
        /**
         * Recupere le pseudo du prochain joueur
         * 
         * @return pseudo du prochain joueur
         */
        private String prochainJoueur() {
            if (pseudoCourant > clients.size()) pseudoCourant = 1;
            String client = (String) clients.get(new Integer(pseudoCourant++));
            return client;
        }
        
        /**
         * Traite et envoie une reponse adequate au message
         * recu d'un joueur
         * 
         * @param message
         */
        private void envoyer(HashMap message) {
            HashMap parametres = new HashMap();
            
            // Recuperer la commande
            //
            String commande = (String) message.get("commande");
            
            // Verifier le type de la commande
            //
            if (commande.equals("PROPOSER")) {
                System.out.println("<--- PROPOSER");
                Integer ll = (Integer) message.get("ll");
                Integer cc = (Integer) message.get("cc");
                String sol = (String) message.get("sol");
                String pseudo = (String) message.get("pseudo");
                
                // Controler que le joueur ai proposer la
                // solution
                //
                if (sol.equals(solution)) {
                    
                    // Creer le message de fin de partie
                    //
                    parametres.put("sol", solution);
                    parametres.put("pseudo", pseudo);
                    
                    // Indiquer qu'il y a eu un gagnant
                    //
                    gagnant = true;
                    
                    // Retirer la grille de jeu
                    //
                    application.retirerMaillage();
                    
                    // Envoyer le message de fin de partie
                    //
                    serveurInterne.envoyerMessage("TERMINER", parametres);
                    System.out.println("<--- TERMINER");
                    
                    // Arreter le noeud proprement
                    //
                    serveurInterne.stopperReception();
                    Chrono.attendre(200);
                    serveurInterne.stopperEmission();
                    
                }
                else {
                    
                    // Creer le message stipulant quelle
                    // cellule a ete retirer
                    //
                    parametres.put("ll", ll);
                    parametres.put("cc", cc);
                    
                    // Envoyer le message stipulant quelle
                    // cellule a ete retirer
                    //
                    serveurInterne.envoyerMessage("RETIRER", parametres);
                    System.out.println("---> RETIRER");
                    
                    // Retirer la cellule jouer
                    //
                    application.retirerCellule(ll.intValue(), cc.intValue());
                    
                    // Creer le message stipulant quel
                    // joueur doit continuer la partie
                    //
                    parametres.clear();
                    pseudo = prochainJoueur();
                    parametres.put("pseudo", pseudo);
                    
                    // Envoyer le message stipulant quel
                    // joueur doit continuer la partie
                    //
                    serveurInterne.envoyerMessage(pseudo, "JOUER", parametres);
                    System.out.println("---> JOUER : " + pseudo);
                }
            }
        }
    }
    
    /**
     * Classe interne privee Chrono
     * 
     * @author A. Thuaire
     * @version 1.0.0
     */
    private static class Chrono {
        
        /**
         * Attendre n millisecondes, en bloquant le thread
         * courant
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
