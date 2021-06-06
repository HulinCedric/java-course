/*
 *      Automate qui reconnait les mots definis sur l'alphabet {a, b, c}
 *      qui commencent par a et qui finissent par c. 
 * 
 *           a       b        c
 *    ->0   {1}      -        -
 *      1   {1}     {1}     {1,2}
 *     *2    -       -        -
 */
public class T_AEF_G_3 {
    
    public static void main(String[] arg) {
        
        Tests.Begin("AEF_G", "1.0.0");
        
        Tests.Design("Test de l'automate 3", 3);
        
        Tests.Case("Methode accepter");
        
        AEF_G.Cellule[][] transition = new AEF_G.Cellule[3][128];
        
        transition[0]['a'] = new AEF_G.Cellule(1, null);
        transition[0]['b'] = null;
        transition[0]['c'] = null;
        
        transition[1]['a'] = new AEF_G.Cellule(1, null);
        transition[1]['b'] = new AEF_G.Cellule(1, null);
        transition[1]['c'] = new AEF_G.Cellule(1, new AEF_G.Cellule(2, null));
        
        transition[2]['a'] = null;
        transition[2]['b'] = null;
        transition[2]['c'] = null;
        
        AEF_G automate = new AEF_G(0, new AEF_G.Cellule(2, null), transition);
        
        Tests.Unit(new Boolean(true), new Boolean(automate.accepter("ac")));
        Tests.Unit(new Boolean(true), new Boolean(automate.accepter("ababcabcaabbbc")));
        Tests.Unit(new Boolean(false), new Boolean(automate.accepter("babc")));
        Tests.Unit(new Boolean(false), new Boolean(automate.accepter("abzbc")));
        
        Tests.End();
    }
}
