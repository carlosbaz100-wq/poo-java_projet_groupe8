
import java.util.Objects;

public class Lecteur {

    private final String nom;
    private final String prenom;
    private final int numero;
    private final String adresse;

    public Lecteur(String nom, String prenom, int numero, String adresse) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        if (prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException("Le prenom est obligatoire");
        }
        if (numero <= 0) {
            throw new IllegalArgumentException("Le numero doit etre > 0");
        }
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

    // Deux lecteurs avec le meme numero sont le meme adherent.
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
