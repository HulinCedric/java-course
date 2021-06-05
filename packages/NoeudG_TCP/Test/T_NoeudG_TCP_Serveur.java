import java.util.HashMap;

//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Composants reseaux generiques sous
// TCP/IP
//
// Classe T_NoeudG_TCP - Module de test du mode serveur de
// la classe NoeudG_TCP
// (Controle limite a trois connexions simultanees)
//     
// Auteur : A. Thuaire
//
public class T_NoeudG_TCP_Serveur {
    
    public static void main(String[] args) {
        
        // Creer et demarrer un noeud TCP/IP en mode serveur
        // avec trois clients
        //
        System.out.print("* Creation et demarrage d'un noeud TCP/IP ");
        System.out.println("(V 1.2.0) - Table\n");
        NoeudG_TCP serveurTCP = new NoeudG_TCP("Table", 8080, 1);
        
        // Debuter l'emission/reception des messages
        //
        serveurTCP.debuterEmission();
        serveurTCP.debuterReception();
               
        Chrono.attendre(2000);
        
        HashMap msg;
        String pseudo;
        String commande;
        
        while (serveurTCP.presenceMessageRecu()) {
            
            msg= serveurTCP.retirerMessage();
            pseudo= (String) msg.get("pseudo");
            commande= (String) msg.get("commande");
            serveurTCP.envoyerMessage(pseudo, commande, msg);
        }
        
        // Stopper l'emission/reception des messages
        //
        serveurTCP.stopperEmission();
        serveurTCP.stopperReception();
        
        Chrono.attendre(2000);
        
        // Fermer brutalement la liaison sans coordination
        // avec les clients
        //
        System.out.println();
        System.out.println("* Table deconnectee");
        System.out.println();
        
        serveurTCP.fermer();
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
}
