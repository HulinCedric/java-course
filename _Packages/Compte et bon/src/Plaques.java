public class Plaques extends Pile {
    
    public Plaques() {}
    
    public Plaques(int d) {}
    
    public Plaques(Plaques d) {}
    
    // Pour ordonner et creer les plaques
    public void generer() {}
    
    public void ordonner() {}
    
    public String toString() {
        return null;
    }
    
    // Surcharge des m�thodes de la classe m�re
    public void depiler() {}
    
    public int empiler(int d) {
        return d;
    }
    
    // Quelques operateurs pour acc�der ou modifier le
    // contenu de la pile
    // Ces operateurs cassent quelque peu le principe de la
    // pile mais sont n�cessaires
    public boolean equals(Object d) {
        return true;
    }
}
