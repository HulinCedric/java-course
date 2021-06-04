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
 *          Ajout d'une methode observer permettant de
 *          rentrer les donnees des Thread actif, hormis
 *          celui de l'espion, dans un dictionnaire
 * 
 * @version 1.2.0
 * 
 *          Ajout d'un dictionnaire secondaire regroupant
 *          toutes les proprietes des threads espionnes
 * 
 * @version 1.3.0
 * 
 *          Ajout de la date absolue du releve des
 *          proprietes
 * 
 * @version 1.4.0
 * 
 *          Ajout de la gestion de fichier configuration
 * 
 * @version 1.5.0
 * 
 *          Ajout de la methode filtrer qui permet de gerer
 *          la visualisation selon les parametre de
 *          configuration
 * 
 * @version 1.6.0 (not finish)
 * 
 *          Ajout de la methode transmettre qui determine si
 *          la transmission est en accord avec les
 *          conditions donnees afin de transmettre les
 *          informations recoltees
 * 
 * @version 2.0.0
 * 
 *          Introduction d'un ClientG_TCP permettant
 *          d'envoyer les informations recoltees a un
 *          serveur les receptionnant
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Classe permettant d'espionner des threads
 * 
 * @version 2.0.0
 * @author C. Hulin
 */
public class EspionD_TCP implements Runnable {
    private HashMap dico;
    private LinkedHashMap conditions;
    final private NoeudG_TCP clientInterne;
    private String host;
    private String prefixe;
    private int portCommunication;
    private int loop;
    private int priorityThread;
    private long activation;
    private boolean priority;
    private boolean threadGroup;
    private boolean daemon;
    private boolean time;
    
    /**
     * Constructeur par defaut
     */
    public EspionD_TCP() {
        
        dico = new HashMap();
        host = "localHost";
        portCommunication = 8080;
        prefixe = "ClientEspion";
        loop = 2000;
        priorityThread = 5;
        activation = System.currentTimeMillis();
        priority = true;
        threadGroup = false;
        daemon = false;
        time = false;
        
        clientInterne = new NoeudG_TCP(prefixe, host, portCommunication);
        clientInterne.debuterEmission();
        // clientInterne.setDaemon(true);
    }
    
    /**
     * Constructeur normal
     * 
     * @param config
     */
    public EspionD_TCP(LinkedHashMap config) {
        
        dico = new HashMap();
        conditions = (LinkedHashMap) config.get("conditions");
        
        setHost(config);
        setPortCommunication(config);
        setPrefixe(config);
        setLoop(config);
        setPriorityThread(config);
        setActivation(config);
        setPriority(config);
        setThreadGroup(config);
        setDaemon(config);
        setTime(config);
        
        clientInterne = new NoeudG_TCP(prefixe, host, portCommunication);
        clientInterne.debuterEmission();
        // clientInterne.setDaemon(true);
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
     * setPriorityThread
     * 
     * @param config
     */
    private void setPriorityThread(HashMap config) {
        int defaultValue = 5;
        int value = defaultValue;
        Object w;
        
        if (config == null) {
            priorityThread = defaultValue;
            return;
        }
        w = config.get("PriorityThread");
        if (w != null) value = ((Integer) w).intValue();
        priorityThread = value;
    }
    
    /**
     * setActivation
     * 
     * @param config
     */
    private void setActivation(HashMap config) {
        long defaultValue = System.currentTimeMillis();
        long value = defaultValue;
        Object w;
        
        if (config == null) {
            activation = defaultValue;
            return;
        }
        w = config.get("Activation");
        if (w != null) value = ((Long) w).longValue();
        activation = value;
    }
    
    /**
     * setPriority
     * 
     * @param config
     */
    private void setPriority(HashMap config) {
        boolean defaultValue = true;
        
        if (config == null) {
            priority = defaultValue;
            return;
        }
        priority = !config.containsKey("Priority");
    }
    
    /**
     * setThreadGroup
     * 
     * @param config
     */
    private void setThreadGroup(HashMap config) {
        boolean defaultValue = false;
        
        if (config == null) {
            threadGroup = defaultValue;
            return;
        }
        threadGroup = !config.containsKey("ThreadGroup");
    }
    
    /**
     * setDaemon
     * 
     * @param config
     */
    private void setDaemon(HashMap config) {
        boolean defaultValue = false;
        
        if (config == null) {
            daemon = defaultValue;
            return;
        }
        daemon = !config.containsKey("Daemon");
    }
    
    /**
     * setTime
     * 
     * @param config
     */
    private void setTime(HashMap config) {
        boolean defaultValue = false;
        
        if (config == null) {
            time = defaultValue;
            return;
        }
        time = !config.containsKey("Time");
    }
    
    /**
     * Main
     */
    public static void main(String[] args) {
        LinkedHashMap config;
        
        config = (LinkedHashMap) Config.load("ConfigEspionD_TCP", "2.0.0");
        
        Thread t1 = new Thread(new EspionD_TCP(config));
        
        t1.setName("TestClientEspion-1");
        
        // t1.setDaemon(true);
        t1.start();
        
        Chrono.attendre(3000);
    }
    
    /**
     * run
     */
    public void run() {
        int i = 1;
        
        Thread.currentThread().setPriority(priorityThread);
        
        while (activation > System.currentTimeMillis())
            Chrono.attendre((int) (activation - System.currentTimeMillis()));
        
        while (true) {
            
            voirThreads(i++);
            Chrono.attendre(loop);
        }
    }
    
    /**
     * voirThreads
     * 
     * @param etape
     */
    private void voirThreads(int etape) {
        Thread[] ta = new Thread[Thread.activeCount()];
        int nbThreads = Thread.enumerate(ta);
        Iterator it1, it2;
        String cle1, cle2;
        
        System.out.println();
        System.out.println("=> [" + etape + "] Nombre de threads= " + nbThreads);
        
        observer();
        transmettre((HashMap) null);
        
        it1 = dico.keySet().iterator();
        while (it1.hasNext()) {
            cle1 = (String) it1.next();
            System.out.print("[" + cle1 + "]");
            it2 = ((LinkedHashMap) dico.get(cle1)).keySet().iterator();
            while (it2.hasNext()) {
                cle2 = (String) it2.next();
                System.out.print("[" + ((LinkedHashMap) dico.get(cle1)).get(cle2) + "]");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * observer
     */
    private void observer() {
        Thread[] ta = new Thread[Thread.activeCount()];
        int nbThreads = Thread.enumerate(ta);
        LinkedHashMap proprietes;
        
        // Vider le dictionnaire des donnees des anciennes
        // donnees
        //
        dico.clear();
        
        // Parcourir les Thread actif
        //
        for (int i = 0; i < nbThreads; i++) {
            
            // Entrer les donnees des Thread hormis ceux de
            // l'espion
            //
            if (!ta[i].getName().equals(Thread.currentThread().getName())
                    && !ta[i].getName().equals(prefixe + " - Emetteur")
                    && !ta[i].getName().equals(prefixe + " - Recepteur")) {
                proprietes = new LinkedHashMap();
                
                proprietes.put("Priority", new Integer(ta[i].getPriority()));
                proprietes.put("ThreadGroup", ta[i].getThreadGroup().getName());
                proprietes.put("Daemon", new Boolean(ta[i].isDaemon()));
                proprietes.put("Time", new Long(System.currentTimeMillis()));
                
                dico.put(ta[i].getName(), proprietes);
            }
        }
        filtrer();
    }
    
    /**
     * Permet a l'espion de filtrer les parametres demandees
     * dans le fichier de configuration
     */
    private void filtrer() {
        Iterator it;
        
        /*
         * Initialise l'iterateur sur le dictionnaire de
         * threads
         */
        it = dico.keySet().iterator();
        
        /*
         * Parcourir le dictionnaire de threads et exclure
         * les parametres non attendus
         */
        while (it.hasNext()) {
            String cle = (String) it.next();
            if (!priority) ((LinkedHashMap) dico.get(cle)).remove("Priority");
            if (!threadGroup) ((LinkedHashMap) dico.get(cle)).remove("ThreadGroup");
            if (!daemon) ((LinkedHashMap) dico.get(cle)).remove("Daemon");
            if (!time) ((LinkedHashMap) dico.get(cle)).remove("Time");
        }
    }
    
    /**
     * Permet a l'espion de transmettre les donnees
     * recoltees a un serveur si les conditions requises
     * sont remplies
     * 
     * @param conditions
     *            les conditions pour emmetre
     */
    private void transmettre(HashMap conditions) {
        
        // si les conditions son remplies on transmet
        //
        if (verifier_condition())

        // Transmettre les information au serveur
        //
        clientInterne.envoyerMessage(prefixe, dico);
    }
    
    /**
     * Traiter les conditions de transmission
     */
    private boolean verifier_condition() {
        String[] strTMP = null;
        boolean res = false;
        
        if (conditions == null) return true;
        
        Iterator i = conditions.keySet().iterator();
        
        String cle = null;
        if (conditions.isEmpty()) return true;
        else {
            while (i.hasNext()) {
                cle = (String) i.next();
                LinkedHashMap conditionsTmp = (LinkedHashMap) conditions.get(cle);
                LinkedHashMap dicoTmp = (LinkedHashMap) dico.get(cle);
                if (dicoTmp != null) {
                    
                    Iterator j = conditionsTmp.keySet().iterator();
                    Iterator k = null;
                    String cle_2 = null;
                    String cle_3 = null;
                    String prec = null;
                    while (j.hasNext()) {
                        
                        cle_2 = (String) j.next();
                        if (j.hasNext()) cle_3 = (String) j.next();
                        
                        strTMP = cle_2.split("_");
                        Object conditionProp = conditionsTmp.get(cle_2);
                        Object threadProp = dicoTmp.get(strTMP[0]);
                        String bool = (String) conditionsTmp.get(cle_3);
                        
                        if (bool.equals("ET")) {
                            if (conditionProp.equals(threadProp)) {
                                res = true;
                            }
                            else {
                                res = false;
                                break;
                            }
                        }
                        else {
                            if (bool.equals("OU")) {
                                if (!conditionProp.equals(threadProp)) {
                                    res = false;
                                }
                                else {
                                    res = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return res;
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