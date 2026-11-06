public class Livre extends Document {

    private int nombrePages;
    private String[] auteurs;
    private int nbAuteurs;
    private static final int CAPACITE_AUTEURS = 3; // TROIS AUTEURS MAXIMUM PAR LIVRE AVEC 1 PRINCIPAL

    public Livre(int numero, String titre, String auteurPrincipal, int nombrePages) {
        super(numero, titre, auteurPrincipal);
        this.nombrePages = nombrePages;
        this.auteurs = new String[CAPACITE_AUTEURS];
        this.nbAuteurs = 0;
    }

    public int getNombrePages() {

        return nombrePages;
    }
    // l'ajout d'un autre auteur que l'auteur principal
    public void ajouterAuteur(String auteur) {
        if (nbAuteurs < CAPACITE_AUTEURS) {
            auteurs[nbAuteurs] = auteur;
            nbAuteurs++;
        } else {
            System.out.println("Nombre maximum d'auteurs atteint pour : " + getTitre());
        }
    }

    public String[] getAuteurs() {

        return auteurs;
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

        String affichageAuteurs = "";
        for (int i = 0; i < nbAuteurs; i++) {
            affichageAuteurs = affichageAuteurs + ", " + auteurs[i];
        }

        return "Livre n°" + getNumero() + " : " + getTitre()
                + " - " + getAuteurPrincipal() + affichageAuteurs
                + " (" + nombrePages + " pages)"
                + " [" + etat + "]"
                + " - duree de pret : " + dureePret() + " jours";
    }
}
