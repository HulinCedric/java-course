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

import java.util.HashMap;

public class T_NoeudG_TCP_Client_2 {
    
    public static void main(String[] args) {
        
        // Creer et demarrer un noeud TCP en mode client
        //
        System.out.print("* Creation et demarrage d'un noeud TCP/IP ");
        System.out.println("(V 1.2.0) - Joueur\n");
        NoeudG_TCP clientTCP2 = new NoeudG_TCP("Joueur2", "localHost", 8080);
        
        // Attendre l'acceptation du serveur
        //
        Chrono.attendre(200);
        System.out.println("* Joueur connecte");
        System.out.println();
        
        // Renseigner les parametres de la commande
        // "DECLARER"
        //
        HashMap parametres2 = new HashMap();
        
        parametres2.put("pseudo", "Java_2");
        
        // Envoyer la commande "DECLARER" au serveur
        //
        clientTCP2.envoyerMessage("DECLARER", parametres2);
        
        // Autoriser l'emission des messages
        //
        clientTCP2.debuterEmission();
        clientTCP2.debuterReception();
        
        // Visualiser la commande transmise
        //
        System.out.println("--> (Joueur2) Commande envoyee  : " + "DECLARER");
                        
        Chrono.attendre(1000);

        
        // Stopper l'emission/reception des messages
        //
        clientTCP2.stopperEmission();
        clientTCP2.stopperReception();
        
        // Fermer les flux d'echange avec le serveur
        //
        clientTCP2.fermer();

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
                Thread.currentThread().sleep(tms);
            }
            catch (InterruptedException e) {}
        }
    }
}
