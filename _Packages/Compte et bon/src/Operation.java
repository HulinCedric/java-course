public class Operation {
    
    private int m_Nb1;
    private String m_signe;
    private int m_Nb2;
    private int m_resultat;
    
    public Operation() {

    }
    
    public Operation(int d, String c, int e) {

    }
    
    public Operation(Operation d) {

    }
    
    public boolean neutre() {
        return m_resultat == 0;
    }
    
    public void veto() {
        m_signe = "0";
    }
    
    public boolean ok() {
        return !m_signe.equals("0");
    }
    
    public boolean nok() {
        return !(ok());
    }
    
    public String toString() {
        return m_signe;

    }
    
    public int resultat() {
        if (nok()) return 0;
        return m_resultat;
    }
    
    public int nb1() {
        if (nok()) return 0;
        return m_Nb1;
    }
    
    public int nb2() {
        if (nok()) return 0;
        return m_Nb2;
    }
    
}
