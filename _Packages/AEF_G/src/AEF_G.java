/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010
 * 
 * @version 0.0.0
 * 
 *          version initiale
 * 
 * @version 1.0.0
 * 
 *          classe interne Cellule
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Automate a etat fini generique
 * 
 * @author C. Fouco, C. Hulin
 * @version 1.0.0
 */
public class AEF_G {
    /**
     * etat initial
     */
    private int e0;
    /**
     * fonction de transition
     */
    private Cellule[][] transition;
    /**
     * etats finals
     */
    private Cellule finaux;
    
    /**
     * Constructeur normal
     * 
     * @param etat_initial
     * @param etat_finaux
     * @param fonction_transition
     */
    public AEF_G(int etat_initial, Cellule etat_finaux, Cellule[][] fonction_transition) {
        e0 = etat_initial;
        finaux = etat_finaux;
        transition = fonction_transition;
    }
    
    /**
     * appelMethode
     * 
     * @param nomClass
     * @param nomMethod
     * @param parametre
     * @return flag de reussite
     */
    private boolean appelMethode(String nomClass, String nomMethod, Object[] parametre) {
        boolean flagReussite = false;
        
        try {
            
            // Creer un objet Class du nom de la classe
            // donner en parametre
            //
            Class cl = Class.forName(nomClass);
            
            // Creer une nouvelle instance de la classe
            // donner en parametre
            //
            Object o = cl.newInstance();
            
            // Creer la signature de la methode cible
            //
            Class[] types;
            if (parametre != null) {
                
                types = new Class[parametre.length];
                
                for (int i = 0; i < parametre.length; i++)
                    types[i] = parametre[i].getClass();
            }
            else types = null;
            
            // Chercher la methode cible selon la signature
            // cible
            //
            Method m = cl.getMethod(nomMethod, types);
            
            // Executer la methode sur l'objet passe en
            // parametre
            //
            m.invoke(o, parametre);
            
            // Mettre a jour le flag de reussite
            //
            flagReussite = true;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        // Restituer le resultat
        //
        return flagReussite;
    }
    
    /**
     * accepter
     * 
     * @param mot
     * @return flag de reussite
     */
    public boolean accepter(String mot) {
        return accepter(mot, 0, e0);
    }
    
    /**
     * accepter
     * 
     * @param mot
     * @param position
     * @param etat_courant
     * @return falg de reussite
     */
    private boolean accepter(String mot, int position, int etat_courant) {
        
        if (position == mot.length()) {
            return Cellule.estDans(finaux, etat_courant);
        }
        else {
            boolean resultat = false;
            
            // Recuperer le code ASCII du caractere courant
            //
            int caractere_courant = mot.charAt(position);
            
            for (Cellule nv_q = transition[etat_courant][caractere_courant]; nv_q != null; nv_q = nv_q.suivant) {
                
                resultat = resultat || accepter(mot, position + 1, nv_q.val);
                System.out.println("*********************************" + nv_q.val);
            }
            return resultat;
        }
    }
    
    /**
     * Classe interne Cellule
     * 
     * @author C. Fouco, C. Hulin
     * @Version 1.0.0
     */
    public static class Cellule {
        private int val;
        private Cellule suivant;
        
        /**
         * Constructeur normal
         * 
         * @param v
         * @param x
         */
        public Cellule(int v, Cellule x) {
            val = v;
            suivant = x;
        }
        
        /**
         * estDans
         * 
         * @param x
         * @param v
         * @return flag de presence
         */
        private static boolean estDans(Cellule x, int v) {
            
            if (x == null) {
                return false;
            }
            else {
                return x.val == v || estDans(x.suivant, v);
            }
        }
    }
}
