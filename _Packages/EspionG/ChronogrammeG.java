import java.awt.Dimension;
import java.awt.Window;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;

/**
 * 
 * @author C. Hulin
 * @version 1.0.0
 */
public class ChronogrammeG extends CalqueG {
    
    private int curentLine;
    private int curentColumn;
    private HashMap knowThreads;
    private Object hamecon;
    
    /**
     * Constructeur normal
     * 
     * @param nomEspion
     * @param config
     */
    public ChronogrammeG(Object hamecon, HashMap config, int ligne, int colonne, Dimension dimension) {
        
        // Invoquer le constructeur de la classe mere
        //
        super(hamecon, config);
        
        // Initialiser les attributs
        //
        this.hamecon = hamecon;
        curentLine = 2;
        curentColumn = 0;
        knowThreads = new HashMap();
        
        // Ajouter une trame de dimension defini
        //        
        ajouterTrame(ligne, colonne, dimension);
        
        // Ajouter le calque a la fenetre
        //
        ((JFrame) hamecon).getContentPane().add(this);
        
        // Rendre visible la fenetre
        //
        ((JFrame) hamecon).setVisible(true);
    }
    
    /**
     * Transcodage des informations d'un espion en
     * chronogramme
     * 
     * @param dico
     */
    public void dessinerChronogramme(HashMap dico) {
        String cle1;
        Iterator it1 = dico.keySet().iterator();
        
        while (it1.hasNext()) {
            
            // Recuperer le nom du thread courant
            //
            cle1 = (String) it1.next();
            
            // Verifier existence du tread courant
            //
            if (!threadPresent(cle1)) {
                
                // Ajouter le du thread courant en tete
                // de
                // colonne
                //
                ajouterComposant(1, curentColumn, "SUD", cle1);
                
                // Retirer la cellule de la colonne
                // courante
                //
                retirerCellule(1, curentColumn);
            }
            
            // Dessiner l'etat du thread courant
            //
            dessinerEtat(cle1);
        }
        
        // Incrementer Ligne courante
        //
        curentLine++;
    }
    
    /**
     * Verification de la connaissance d'un thread par
     * l'application
     * 
     * @param nomThread
     * @return flag de connaissance
     */
    private boolean threadPresent(String nomThread) {
        
        // Verifier la presence du thread dans le
        // registre
        //
        if (knowThreads.containsKey(nomThread)) return true;
        
        // Ajouter le thread citer dans le registre
        //
        knowThreads.put(nomThread, new Integer(++curentColumn));
        return false;
    }
    
    /**
     * Specifie sur le chronogramme l'activite du thread
     * cible
     * 
     * @param cle
     */
    private void dessinerEtat(String cle) {
        
        // Recuperer la colonne du thread cible
        //
        Integer colonneCle = (Integer) knowThreads.get(cle);
        
        // Modifier la bordure afin de la demarquer du
        // quadrillage
        //
        modifierBordure(curentLine, colonneCle.intValue(), "EST", 10);
    }
}
