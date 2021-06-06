public class Pile {
    
    protected Object[] m_pT;
    protected int m_nSommet;
    protected int m_nTaille;
    
    public Pile() {}
    
    public Pile(int d) {}
    
    public Pile(Pile d) {}
    
    public void empiler(Object d) {}
    
    public void depiler() {}
    
    public int sommet() {
        if (nok()) return m_nSommet;
        return m_nSommet;
    }
    
    public int taille() {
        if (nok()) return m_nSommet;
        return m_nTaille;
    }
    
    public Object top() {
        if (nok()) return m_pT;
        return m_pT[m_nSommet];
    }
    
    public void operator(int d) {
        return;
    }
    
    public void vider() {}
    
    public boolean neutre() {
        return (m_nTaille == 0 && m_nSommet == 0);
    }
    
    public void veto() {
        m_nTaille = -1;
    }
    
    public boolean ok() {
        return (m_nTaille >= 0 && m_pT != null);
    }
    
    public boolean nok() {
        return !(ok());
    }
};