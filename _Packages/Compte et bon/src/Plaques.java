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
    
    // Surcharge des méthodes de la classe mère
    public void depiler() {}
    
    public int empiler(int d) {
        return d;
    }
    
    // Quelques operateurs pour accéder ou modifier le
    // contenu de la pile
    // Ces operateurs cassent quelque peu le principe de la
    // pile mais sont nécessaires
    public boolean equals(Object d) {
        return true;
    }
}
