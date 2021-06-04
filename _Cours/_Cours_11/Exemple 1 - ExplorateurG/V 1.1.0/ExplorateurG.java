
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ExplorateurG extends JFileChooser {
    private JFrame hamecon;
    private File repertoireCible;
    private ActionListener ecouteur;
    private File[] fichiersCibles;
    private JButton charger;
    
    // ------ *** Constructeur normal
    
    public ExplorateurG(JFrame f, String origine) {
        
        super(origine);
        
        // Renseigner les attributs definis par parametre
        //
        hamecon = f;
        
        f.add(charger = new JButton("Charger"));
        
        // Creer un ecouteur d'item de menu
        //
        ecouteur = new EcouteurItemMenu();
    }
    
    // ------ *** Accesseurs
    
    public File obtenirRepertoireCible() {
        return repertoireCible;
    }
    
    public ActionListener obtenirEcouteurItemMenu() {
        return ecouteur;
    }
    
    public File[] obtenirFichiersCibles() {
        return fichiersCibles;
    }
    
    // ------ *** Methode main
    
    public static void main(String[] args) {
        
        // Creer un cadre support
        //
        JFrame f = new JFrame();
        f.setTitle("Classe ExplorateurG - V 1.1.0");
        f.setSize(600, 400);
        f.setLocation(250, 250);
        
        // Definir le comportement de l'application lors de la
        // fermeture du cadre
        //
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Creer un explorateur de repertoires
        //
        ExplorateurG explorateur = new ExplorateurG(f, ".");
        
        // Autoriser les selections multiples
        //
        explorateur.setMultiSelectionEnabled(true);
        
        // Ajouter l'ecouteur a l'item "Charger"
        //
        explorateur.bindEcouteurItemMenu(explorateur.obtenirEcouteurItemMenu());
        
        // Visualiser le cadre support
        //
        f.setVisible(true);
        
        // Attendre la selection operateur
        //
        while (explorateur.obtenirRepertoireCible() == null)
            System.out.println(true);
        
        // Visualiser le chemin absolu du repertoire cible
        //
        System.out.println();
        System.out.print("Repertoire cible = ");
        System.out.println(explorateur.repertoireCible.getPath());
        
        // Visualiser le nombre de fichiers cibles
        //
        System.out.println();
        System.out.print("Nombre de fichiers cibles = ");
        System.out.println(explorateur.obtenirFichiersCibles().length);
        
        // Detruire le cadre principal
        //
        f.dispose();
    }
    
    public void bindEcouteurItemMenu(ActionListener listener) {
        charger.addActionListener(listener);
    }
    
    // ----------------------------- *** Classe interne
    // EcouteurItemMenu
    
    private class EcouteurItemMenu implements ActionListener {
        
        // ------ *** Methode actionPerformed
        
        public void actionPerformed(ActionEvent e) {
            
            // Visualiser le panneau de dialogue
            //
            showOpenDialog(hamecon);
            
            // Acquerir le repertoire cible et les fichiers
            // selectionnes
            //
            repertoireCible = getCurrentDirectory();
            fichiersCibles = getSelectedFiles();
        }
    }
}
