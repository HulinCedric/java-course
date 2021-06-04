/*
 * Automate qui accepte les nombres décimaux. Exemples :
 * +23,15 -12,456 + - , 0 1 ... 9 ->0 {1} {1} - - - ... - 1
 * - - - {4} {2} ... {2} 2 - - {3} {2} - ... - 3 - - - {3}
 * {3,4} ... {3,4} *4 - - - - - ... -
 */

public class T_AEF_G_1 {
    
    public static void main(String[] arg) {
        
        Tests.Begin("AEF_G", "1.0.0");
        
        Tests.Design("Test de l'automate 1", 3);
        
        Tests.Case("Methode accepter");
        
        AEF_G.Cellule[][] transition = new AEF_G.Cellule[5][128];
                
        transition[0]['+'] = new AEF_G.Cellule(1, null);
        transition[0]['-'] = new AEF_G.Cellule(1, null);
        transition[0][','] = null;
        transition[0]['0'] = null;
        transition[0]['1'] = null;
        transition[0]['2'] = null;
        transition[0]['3'] = null;
        transition[0]['4'] = null;
        transition[0]['5'] = null;
        transition[0]['6'] = null;
        transition[0]['7'] = null;
        transition[0]['8'] = null;
        transition[0]['9'] = null;
        
        transition[1]['+'] = null;
        transition[1]['-'] = null;
        transition[1][','] = null;
        transition[1]['0'] = new AEF_G.Cellule(4, null);
        transition[1]['1'] = new AEF_G.Cellule(2, null);
        transition[1]['2'] = new AEF_G.Cellule(2, null);
        transition[1]['3'] = new AEF_G.Cellule(2, null);
        transition[1]['4'] = new AEF_G.Cellule(2, null);
        transition[1]['5'] = new AEF_G.Cellule(2, null);
        transition[1]['6'] = new AEF_G.Cellule(2, null);
        transition[1]['7'] = new AEF_G.Cellule(2, null);
        transition[1]['8'] = new AEF_G.Cellule(2, null);
        transition[1]['9'] = new AEF_G.Cellule(2, null);
        
        transition[2]['+'] = null;
        transition[2]['-'] = null;
        transition[2][','] = new AEF_G.Cellule(3, null);
        transition[2]['0'] = new AEF_G.Cellule(2, null);
        transition[2]['1'] = new AEF_G.Cellule(2, null);
        transition[2]['2'] = new AEF_G.Cellule(2, null);
        transition[2]['3'] = new AEF_G.Cellule(2, null);
        transition[2]['4'] = new AEF_G.Cellule(2, null);
        transition[2]['5'] = new AEF_G.Cellule(2, null);
        transition[2]['6'] = new AEF_G.Cellule(2, null);
        transition[2]['7'] = new AEF_G.Cellule(2, null);
        transition[2]['8'] = new AEF_G.Cellule(2, null);
        transition[2]['9'] = new AEF_G.Cellule(2, null);
        
        transition[3]['+'] = null;
        transition[3]['-'] = null;
        transition[3][','] = null;
        transition[3]['0'] = new AEF_G.Cellule(3, null);
        transition[3]['1'] = new AEF_G.Cellule(3, new AEF_G.Cellule(4, null));
        transition[3]['2'] = new AEF_G.Cellule(3, new AEF_G.Cellule(4, null));
        transition[3]['3'] = new AEF_G.Cellule(3, new AEF_G.Cellule(4, null));
        transition[3]['4'] = new AEF_G.Cellule(3, new AEF_G.Cellule(4, null));
        transition[3]['5'] = new AEF_G.Cellule(3, new AEF_G.Cellule(4, null));
        transition[3]['6'] = new AEF_G.Cellule(3, new AEF_G.Cellule(4, null));
        transition[3]['7'] = new AEF_G.Cellule(3, new AEF_G.Cellule(4, null));
        transition[3]['8'] = new AEF_G.Cellule(3, new AEF_G.Cellule(4, null));
        transition[3]['9'] = new AEF_G.Cellule(3, new AEF_G.Cellule(4, null));
        
        transition[4]['+'] = null;
        transition[4]['-'] = null;
        transition[4][','] = null;
        transition[4]['0'] = null;
        transition[4]['1'] = null;
        transition[4]['2'] = null;
        transition[4]['3'] = null;
        transition[4]['4'] = null;
        transition[4]['5'] = null;
        transition[4]['6'] = null;
        transition[4]['7'] = null;
        transition[4]['8'] = null;
        transition[4]['9'] = null;
        
        AEF_G automate = new AEF_G(0, new AEF_G.Cellule(4, null), transition);
        
        Tests.Unit(new Boolean(true), new Boolean(automate.accepter("-2350,12")));
        Tests.Unit(new Boolean(true), new Boolean(automate.accepter("+123,369")));
        Tests.Unit(new Boolean(true), new Boolean(automate.accepter("-0")));
        Tests.Unit(new Boolean(false), new Boolean(automate.accepter("-201,0")));
        Tests.Unit(new Boolean(false), new Boolean(automate.accepter("-200")));
        Tests.Unit(new Boolean(false), new Boolean(automate.accepter("+200,")));
        
        Tests.End();
    }
}
