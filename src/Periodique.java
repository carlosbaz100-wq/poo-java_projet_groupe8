public class Periodique extends Document implements Consultable {

    private int numeroParution;

    public Periodique(int numero, String titre, String auteurPrincipal, int numeroParution) {
        super(numero, titre, auteurPrincipal);
        this.numeroParution = numeroParution;
    }

    public int getNumeroParution() {

        return numeroParution;
    }

    @Override
    public int dureePret() {

        return 7;
    }

    @Override
    public String toString() {
        String etat;
        if (estDisponible()) {
            etat = "disponible";
        } else {
            etat = "emprunte par " + getEmprunteur().getPrenom() + " " + getEmprunteur().getNom();
        }
        return "Periodique n°" + getNumero() + " : \"" + getTitre() + "\" — " + getAuteurPrincipal()
                + " (parution n°" + numeroParution + ")"
                + " [" + etat + "] — duree de pret : " + dureePret() + " jours";
    }
}
