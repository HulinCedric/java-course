/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annexe 2009_2010
 * 
 * @version 1.0.0
 * 
 *          version initiale
 * 
 * @version 1.1.0
 * 
 *          ajout de multiple methode pour transcoder les
 *          informations d'un espion en chronogramme
 * 
 * @version 1.2.0
 * 
 *          extension du chronogramme a plusieurs espion
 */
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.JFrame;

/**
 * Classe permettant de collecter des informations
 * transmises par des espions afin de dessiner un
 * chronogramme
 * 
 * @version 1.2.0
 * @author C. Hulin
 */
public class EspionG_TCP implements Runnable {
    private HashMap dico;
    final private NoeudG_TCP serveurInterne;
    private String prefixe;
    private int portCommunication;
    private int numberOfClient;
    private int loop;
    private int ligne;
    private int colonne;
    private Dimension dimension;
    private HashMap knowEspions;
    private HashMap configuration;
    
    /**
     * Constructeur normal
     * 
     * @param config
     */
    public EspionG_TCP(HashMap config) {
        
        dico = new HashMap();
        knowEspions = new HashMap();
        configuration = config;
        
        setPrefixe(config);
        setPortCommunication(config);
        setNumberOfClient(config);
        setLoop(config);
        setLigne(config);
        setColonne(config);
        setDimension(config);
        
        serveurInterne = new NoeudG_TCP(prefixe, portCommunication, numberOfClient);
        serveurInterne.debuterReception();
    }
    
    /**
     * setPrefixe
     * 
     * @param config
     */
    private void setPrefixe(HashMap config) {
        String defaultValue = "ClientEspion";
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
     * setPortCommunication
     * 
     * @param config
     */
    private void setPortCommunication(HashMap config) {
        int defaultValue = 8080;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            portCommunication = defaultValue;
            return;
        }
        w = config.get("PortCommunication");
        if (w != null) value = ((Integer) w).intValue();
        portCommunication = value;
    }
    
    /**
     * setLigne
     * 
     * @param config
     */
    private void setLigne(HashMap config) {
        int defaultValue = 20;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            ligne = defaultValue;
            return;
        }
        w = config.get("Ligne");
        if (w != null) value = ((Integer) w).intValue();
        ligne = value;
    }
    
    /**
     * setColonne
     * 
     * @param config
     */
    private void setColonne(HashMap config) {
        int defaultValue = 5;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            colonne = defaultValue;
            return;
        }
        w = config.get("Colonne");
        if (w != null) value = ((Integer) w).intValue();
        colonne = value;
    }
    
    /**
     * setDimension
     * 
     * @param config
     */
    private void setDimension(HashMap config) {
        Dimension defaultValue = new Dimension(170, 20);
        Dimension value = defaultValue;
        Object w;
        
        if (config == null) {
            dimension = defaultValue;
            return;
        }
        w = config.get("Dimension");
        if (w != null) value = ((Dimension) w);
        dimension = value;
    }
    
    /**
     * setNumberOfClient
     * 
     * @param config
     */
    private void setNumberOfClient(HashMap config) {
        int defaultValue = 1;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            numberOfClient = defaultValue;
            return;
        }
        w = config.get("NumberOfClient");
        if (w != null) value = ((Integer) w).intValue();
        numberOfClient = value;
    }
    
    /**
     * setLoop
     * 
     * @param config
     */
    private void setLoop(HashMap config) {
        int defaultValue = 2000;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            loop = defaultValue;
            return;
        }
        w = config.get("Loop");
        if (w != null) value = ((Integer) w).intValue();
        loop = value;
    }
    
    /**
     * Main
     */
    public static void main(String[] args) {
        LinkedHashMap config;
        
        config = (LinkedHashMap) Config.load("ConfigEspionG_TCP", "1.0.0");
        
        Thread t1 = new Thread(new EspionG_TCP(config));
        
        t1.start();
    }
    
    /**
     * run
     */
    public void run() {
        
        while (true) {
            if (serveurInterne.presenceMessageRecu()) receptionnerInformation();
            
            Chrono.attendre(loop);
        }
    }
    
    /**
     * voirInformations
     * 
     * @param etape
     */
    private void voirInformations(HashMap dico) {
        Iterator it1, it2;
        String cle1, cle2;
        
        System.out.println("[" + dico.get("commande") + "]");
        dico.remove("commande");
        
        it1 = dico.keySet().iterator();
        while (it1.hasNext()) {
            cle1 = (String) it1.next();
            System.out.println("    [" + cle1 + "]");
            it2 = ((LinkedHashMap) dico.get(cle1)).keySet().iterator();
            while (it2.hasNext()) {
                cle2 = (String) it2.next();
                System.out.print("\t[" + cle2 + "]\t");
                System.out.print("[" + ((LinkedHashMap) dico.get(cle1)).get(cle2) + "]");
                System.out.println();
            }
            System.out.println();
        }
        
    }
    
    /**
     * Receptionner les information envoyees par les divers
     * espions rattacher a espionG
     */
    private void receptionnerInformation() {
        
        // Reseter le dictionnaire des anciennes donnees
        //
        dico.clear();
        
        // Recuperer et retirer le message receptionner par
        // le serveur interne
        //
        dico = serveurInterne.retirerMessage();
        
        // Visualiser les informations sous forme de
        // chronogramme
        //
        dessinerChronogramme((HashMap) dico.clone());
        
        // Visualiser les informations sous forme textuel
        //
        voirInformations((HashMap) dico.clone());
    }
    
    public void dessinerChronogramme(HashMap dico) {
        String nomEspion;
        
        // Supprimer la commande du noeudG
        //
        nomEspion = (String) dico.get("commande");
        dico.remove("commande");
        
        // Verifier existence du tread courant
        //
        espionPresent(nomEspion);
        
        ((ChronogrammeG) (knowEspions.get(nomEspion))).dessinerChronogramme(dico);
        
    }
    
    /**
     * Verification de la connaissance d'un thread par
     * l'application
     * 
     * @param nomThread
     * @return flag de connaissance
     */
    public boolean espionPresent(String nomEspion) {
        
        // Verifier la presence du thread dans le
        // registre
        //
        if (knowEspions.containsKey(nomEspion)) return true;
        
        // Cree la fenetre de visualisation
        //
        JFrame frame = new JFrame();
        frame.setTitle(nomEspion);
        frame.setSize(500, 600);
        frame.setLocation(250, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Ajouter le thread citer dans le registre
        //
        knowEspions.put(nomEspion, new ChronogrammeG(frame, configuration, ligne, colonne,
                dimension));
        return false;
    }
    
    /**
     * Classe interne privee Chrono
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