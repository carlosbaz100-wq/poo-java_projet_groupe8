import java.util.Objects;

public class Lecteur {

    private String nom;
    private String prenom;
    private int numero;
    private String adresse;

    public Lecteur(String nom, String prenom, int numero, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getNumero() {
        return numero;
    }

    public String getAdresse() {
        return adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecteur lecteur = (Lecteur) o;
        return numero == lecteur.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Lecteur n°" + numero + " : " + prenom + " " + nom + " (" + adresse + ")";
    }
}
