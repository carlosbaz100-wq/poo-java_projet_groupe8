import java.util.Arrays;

public class Livre extends Document implements Empruntable {

    private static final int CAPACITE_AUTEURS = 3; // trois co-auteurs maximum, en plus de l'auteur principal

    private final int nombrePages;
    private final String[] auteurs;
    private int nbAuteurs;

    public Livre(int numero, String titre, String auteurPrincipal, int nombrePages) {
        super(numero, titre, auteurPrincipal);
        if (nombrePages <= 0) {
            throw new IllegalArgumentException("Le nombre de pages doit etre > 0");
        }
        this.nombrePages = nombrePages;
        this.auteurs = new String[CAPACITE_AUTEURS];
        this.nbAuteurs = 0;
    }

    public int getNombrePages() {
        return nombrePages;
    }

    // Ajoute un co-auteur different de l'auteur principal et des co-auteurs deja enregistres.
    public void ajouterAuteur(String auteur) {
        if (auteur == null || auteur.isBlank()) {
            throw new IllegalArgumentException("L'auteur est obligatoire");
        }
        if (getAuteurPrincipal().equalsIgnoreCase(auteur)) {
            throw new IllegalArgumentException("L'auteur principal est deja enregistre : " + auteur);
        }
        if (nbAuteurs >= CAPACITE_AUTEURS) {
            throw new IllegalStateException("Nombre maximum de co-auteurs atteint pour : " + getTitre());
        }
        for (int i = 0; i < nbAuteurs; i++) {
            if (auteurs[i].equalsIgnoreCase(auteur)) {
                throw new IllegalArgumentException("Cet auteur est deja enregistre : " + auteur);
            }
        }
        auteurs[nbAuteurs] = auteur;
        nbAuteurs++;
    }

    // Copie defensive : modifier le tableau renvoye ne change pas l'etat du livre.
    public String[] getAuteurs() {
        return Arrays.copyOf(auteurs, nbAuteurs);
    }

    public int getNbAuteurs() {
        return nbAuteurs;
    }

    @Override
    public int dureePret() {
        return 21;
    }

    @Override
    public String toString() {
        String etat;
        if (estDisponible()) {
            etat = "disponible";
        } else {
            etat = "emprunte par " + getEmprunteur().getPrenom() + " " + getEmprunteur().getNom();
        }

        StringBuilder affichageAuteurs = new StringBuilder();
        for (int i = 0; i < nbAuteurs; i++) {
            affichageAuteurs.append(", ").append(auteurs[i]);
        }

        return "Livre n°" + getNumero() + " : " + getTitre()
                + " - " + getAuteurPrincipal() + affichageAuteurs
                + " (" + nombrePages + " pages)"
                + " [" + etat + "]"
                + " - duree de pret : " + dureePret() + " jours";
    }
}
