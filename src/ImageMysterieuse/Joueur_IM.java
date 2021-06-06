/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Applications graphiques
 * 
 * @version 1.0.0
 * 
 *          version initiale fonctionnel
 */
import java.util.HashMap;

import javax.swing.JOptionPane;

/**
 * IHM et protocole cote joueur du jeu l'image mysterieuse
 * 
 * @author C. Hulin
 * @version 1.0.0
 */
public class Joueur_IM {
    private Protocole_IM protocoleInterne;
    private ImageMysterieuse application;
    private String host;
    private String prefixe;
    private int port;
    
    /**
     * Constructeur normal
     * 
     * @param config
     */
    public Joueur_IM(HashMap config) {
        String pseudo = null;
        
        // Recuperer le pseudo convoiter par l'utilisateur
        //
        while (pseudo == null) {
            pseudo = JOptionPane.showInputDialog(null, "Declarer", "Entrer pseudo",
                    JOptionPane.QUESTION_MESSAGE);
            if (pseudo != null) {
                if (pseudo.length() == 0) pseudo = null;
            }
        }
        
        // Specifier a l'utilisateur le commencement de la
        // partie
        //
        JOptionPane.showMessageDialog(null, "Bienvenue " + pseudo
                + " dans le jeu de l'image mysterieuse", "Demarrer",
                JOptionPane.INFORMATION_MESSAGE);
        
        // Creer une instance du jeu, l'image mysterieuse
        //
        application = new ImageMysterieuse(config);
        
        // Recuperer les donnees de configuration
        //
        setHost(config);
        setPrefixe(config);
        setPort(config);
        
        // Creer un noeud en guise de joueur
        //
        NoeudG_TCP joueur = new NoeudG_TCP(prefixe, host, port);
        joueur.debuterEmission();
        joueur.debuterReception();
        
        // Lancer le protocole
        //
        protocoleInterne = new Protocole_IM(joueur, pseudo);
    }
    
    /**
     * setHost
     * 
     * @param config
     */
    private void setHost(HashMap config) {
        String defaultValue = "localHost";
        String value = defaultValue;
        Object w;
        
        if (config == null) {
            host = defaultValue;
            return;
        }
        w = config.get("Host");
        if (w != null) value = (String) w;
        host = value;
    }
    
    /**
     * setPrefixe
     * 
     * @param config
     */
    private void setPrefixe(HashMap config) {
        String defaultValue = "Joueur_1";
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
     * Main
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        // Charger le dictionnaire de configuration du jeu,
        // l'image mysterieuse
        //      
        HashMap config = (HashMap) Config.load("ConfigImageMysterieuseJoueur", "1.0.0");
        
        // Creer une instance d'un joueur
        //
        Joueur_IM joueur = new Joueur_IM(config);
        
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
        private NoeudG_TCP clientInterne;
        boolean gagnant;
        
        /**
         * Constructeur normal
         * 
         * @param joueur
         * @param pseudo
         */
        public Protocole_IM(NoeudG_TCP joueur, String pseudo) {
            gagnant = false;
            clientInterne = joueur;
            
            // Creer le message de declaration du pseudo
            //
            HashMap parametres = new HashMap();
            parametres.put("pseudo", pseudo);
            
            // Envoyer le message de declaration du pseudo
            //
            clientInterne.envoyerMessage("DECLARER", parametres);
            System.out.println("---> DECLARER : " + pseudo);
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
            if (clientInterne.presenceMessageRecu()) {
                
                // Retirer le message de la liste de
                // reception
                //
                message = clientInterne.retirerMessage();
                
                // Envoyer une reponse adequate a la table
                //
                envoyer((HashMap) message.clone());
            }
            
            // Restituer s'il y a eu un gagnant
            //
            return !gagnant;
        }
        
        /**
         * Traite et envoie une reponse adequate au message
         * recu de la table
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
            if (commande.equals("DEMARRER")) {
                System.out.println("<--- DEMARRER");
            }
            else {
                if (commande.equals("CONFIGURER")) {
                    Integer ll = (Integer) message.get("ll");
                    Integer cc = (Integer) message.get("cc");
                    String path = (String) message.get("path");
                    
                    // Configurer l'application avec les
                    // parametres donnes par la table
                    //
                    application.configurer(path, ll.intValue(), cc.intValue());
                    System.out.println("<--- CONFIGURER");
                    
                }
                else {
                    if (commande.equals("JOUER")) {
                        int ll, cc;
                        
                        // Recuperer la solution du joueur
                        //
                        System.out.println("<--- JOUER");
                        String solution = JOptionPane.showInputDialog(null, "votre solution");
                        if (solution == null) solution = "";
                        
                        // Recuperer les coordonnes de la
                        // cellule jouer
                        //
                        do {
                            ll = application.obtenirLigne();
                            cc = application.obtenirColonne();
                            Chrono.attendre(200);
                        }
                        while (ll == 0 && cc == 0);
                        
                        // Creer le message stipulant la
                        // solution proposer et la cellule
                        // retirer
                        //
                        parametres.put("sol", solution);
                        parametres.put("ll", new Integer(ll));
                        parametres.put("cc", new Integer(cc));
                        
                        // Envoyer le message stipulant la
                        // solution proposer et la cellule
                        // retirer
                        //
                        clientInterne.envoyerMessage("PROPOSER", parametres);
                        System.out.println("---> PROPOSER : " + solution);
                    }
                    else {
                        if (commande.equals("RETIRER")) {
                            
                            // Retirer la cellule stipuler
                            // par la table
                            //
                            Integer ll = (Integer) message.get("ll");
                            Integer cc = (Integer) message.get("cc");
                            application.retirerCellule(ll.intValue(), cc.intValue());
                            System.out.println("<--- RETIRER");
                        }
                        else {
                            if (commande.equals("TERMINER")) {
                                
                                // Retirer le maillage de la
                                // table
                                //
                                application.retirerMaillage();
                                
                                // Indiquer la solution et
                                // qui la trouver
                                //
                                String pseudo = (String) message.get("pseudo");
                                String solution = (String) message.get("sol");
                                JOptionPane.showMessageDialog(null, "La solution ete " + solution
                                        + ", le vainqueur est " + pseudo);
                                
                                // Indiquer qu'il y a eu un
                                // gagnant
                                //
                                gagnant = true;
                                
                                // Arreter le noeud
                                // proprement
                                //
                                clientInterne.stopperReception();
                                Chrono.attendre(100);
                                clientInterne.stopperEmission();
                            }
                        }
                    }
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