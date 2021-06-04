//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants reseaux generiques sous
// TCP/IP
//
// Classe T_NoeudG_TCP - Module de test du mode client de la
// classe NoeudG_TCP
// (Controle limite a trois connexions simultanees)
//
// Auteur : A. Thuaire
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class T_NoeudG_TCP_Client {
    private static String solution = "rose";
    private static String solutionJoueur = "";
    
    public static void main(String[] args) {
        // Creer et demarrer un noeud TCP en mode client
        //
        System.out.print("* Creation et demarrage d'un noeud TCP/IP ");
        System.out.println("(V 1.2.0) - Joueur\n");
        NoeudG_TCP clientTCP = new NoeudG_TCP("Joueur", "localHost", 8080);
        
        // Affichage de la checkbox qui demande le login
        //
        String nom = JOptionPane.showInputDialog(null, "Pseudo", "Entrer pseudo",
                JOptionPane.QUESTION_MESSAGE);
        
        if (nom != null && nom != "") {
            JOptionPane.showMessageDialog(null, "Bienvenue " + nom
                    + " dans le jeu de l'image mystérieuse", "", JOptionPane.INFORMATION_MESSAGE);
        }
        
        // Attendre l'acceptation du serveur
        //
        Chrono.attendre(200);
        System.out.println("* Joueur connecte");
        System.out.println();
        
        // Renseigner les parametres de la commande
        // "DECLARER"
        //
        HashMap parametres = new HashMap();
        parametres.put("pseudo", nom);
        
        // Envoyer la commande "DECLARER" au serveur
        //
        clientTCP.envoyerMessage("DECLARER", parametres);
        
        // Autoriser l'emission des messages
        //
        clientTCP.debuterEmission();
        
        // Visualiser la commande transmise
        //
        System.out.println("--> (Joueur) Commande envoyee  : " + "DECLARER");
        
        // Creer un cadre support
        //
        JFrame f = new JFrame();
        f.setTitle("Jeu de l'image mystérieuse - V 0.0.0 (alpha)");
        f.setSize(500, 300);
        f.setLocation(350, 350);
        
        // Charger le dictionnaire de configuration du
        // panneau principal
        //
        HashMap config = (HashMap) Config.load("ConfigPanneauG", "1.0.0");
        
        // Creer et configurer ce panneau
        //
        PanneauG panneau = new PanneauG(f, config);
        
        // Ajouter une image de fond
        //
        panneau.ajouterImage("Fleurs/Rose.jpg");
        
        // Ajouter un maillage rectangulaire couvrant le
        // panneau principal
        //
        panneau.ajouterMaillage(Color.yellow, Color.black, 10, 15);
        
        // Ajouter l'ecouteur au panneau
        //
        T_NoeudG_TCP_Client w = new T_NoeudG_TCP_Client();
        T_NoeudG_TCP_Client.EcouteurIM ecouteur = w.new EcouteurIM(panneau);
        panneau.ajouterEcouteur(ecouteur);
        
        // Ajouter le panneau principal au cadre support
        //
        f.getContentPane().add(panneau);
        
        // Montrer le cadre support et son panneau interne
        //
        f.setVisible(true);
        
        while (!solutionJoueur.equals(solution)) {
            Chrono.attendre(100);
        }
        
        // Stopper l'emission/reception des messages
        //
        clientTCP.stopperEmission();
        clientTCP.stopperReception();
        
        // Fermer les flux d'echange avec le serveur
        //
        clientTCP.fermer();
        
        System.out.println();
        System.out.println("* Joueur deconnecte");
        System.out.println();
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
    
    // ------------------------------------- *** Classe
    // interne privee EcouteurIM
    
    private class EcouteurIM extends EcouteurPanneauG {
        private EcouteurIM(PanneauG hamecon) {
            panneauCible = hamecon;
        }
        
        public void mouseClicked(MouseEvent e) {
            // Obtenir les coordonnees absolues du clic
            // courant
            //
            int x = e.getX();
            int y = e.getY();
            
            // Obtenir la designation de la maille cible
            //
            Dimension cible = panneauCible.obtenirPositionCible(x, y);
            if (cible == null) return;
            
            // Extraire les coordonnees (ligne, colonne) de
            // cette maille
            //
            int ligne = (int) cible.getWidth();
            int colonne = (int) cible.getHeight();
            
            // Retirer la maille cible
            //
            JOptionPane jop = new JOptionPane();
            solutionJoueur = JOptionPane.showInputDialog(null, "Solution", "Entrer solution",
                    JOptionPane.QUESTION_MESSAGE);
            panneauCible.obtenirMaillage().retirerMaille(ligne, colonne);
            
            if (solutionJoueur.equals(solution)) {
                for (ligne = 0; ligne < 11; ligne++) {
                    for (colonne = 0; colonne < 16; colonne++) {
                        panneauCible.obtenirMaillage().retirerMaille(ligne, colonne);
                    }
                }
            }
        }
    }
}
