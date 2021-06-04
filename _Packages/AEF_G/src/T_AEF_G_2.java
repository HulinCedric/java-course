//
//      Automate qui accepte toutes les chaines
//      qui se terminent par “01”. 
// 
//            0      1       
//    ->0   {0,1}   {0}       
//      1     -     {2}     
//     *2     -      -      
public class T_AEF_G_2 {
    
    public static void main(String[] arg) {
        
        Tests.Begin("AEF_G", "1.0.0");
        
        Tests.Design("Test de l'automate 2", 3);
        
        Tests.Case("Methode accepter");
        
        AEF_G.Cellule[][] transition = new AEF_G.Cellule[3][128];
        transition[0][(int) '0'] = new AEF_G.Cellule(0, new AEF_G.Cellule(1, null));
        transition[0][(int) '1'] = new AEF_G.Cellule(0, null);
        transition[1][(int) '0'] = null;
        transition[1][(int) '1'] = new AEF_G.Cellule(2, null);
        transition[2][(int) '0'] = null;
        transition[2][(int) '1'] = null;
        
        AEF_G automate = new AEF_G(0, new AEF_G.Cellule(2, null), transition);
        
        Tests.Unit(new Boolean(true), new Boolean(automate.accepter("01101001")));
        
        Tests.End();
    }
    
}
