import java.util.Objects;

public abstract class Document {

    private final int numero;
    private final String titre;
    private final String auteurPrincipal;
    private boolean disponible = true;
    private Lecteur emprunteur;

    protected Document(int numero, String titre, String auteurPrincipal) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Le numero doit etre > 0");
        }
        if (titre == null || titre.isBlank()) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }
        if (auteurPrincipal == null || auteurPrincipal.isBlank()) {
            throw new IllegalArgumentException("L'auteur est obligatoire");
        }
        this.numero = numero;
        this.titre = titre;
        this.auteurPrincipal = auteurPrincipal;
    }

    public int getNumero() {
        return numero;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteurPrincipal() {
        return auteurPrincipal;
    }

    public boolean estDisponible() {
        return disponible;
    }

    public Lecteur getEmprunteur() {
        return emprunteur;
    }

    public void emprunter(Lecteur l) {
        if (l == null) {
            throw new IllegalArgumentException("Le lecteur est obligatoire");
        }
        if (!disponible) { // verification AVANT mutation
            throw new DocumentIndisponibleException("Document deja emprunte : " + numero);
        }
        this.emprunteur = l; // mutation
        this.disponible = false;
    }

    public void retourner() {
        if (disponible) { // verification AVANT mutation
            throw new RetourImpossibleException("Aucun emprunt en cours pour : " + numero);
        }
        this.emprunteur = null; // mutation
        this.disponible = true;
    }

    public abstract int dureePret();

    @Override
    public String toString() {
        String etat;
        if (disponible) {
            etat = "disponible";
        } else {
            etat = "emprunte par " + emprunteur.getPrenom() + " " + emprunteur.getNom();
        }
        return "Document n°" + numero + " : \"" + titre + "\" : " + auteurPrincipal
                + " [" + etat + "] : duree de pret : " + dureePret() + " jours";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return numero == document.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
