
public class Periodique extends Document implements Consultable {

    private final int numeroParution;
    // Etat de consultation sur place
    private boolean enConsultation;
    private Lecteur consultant;

    public Periodique(int numero, String titre, String auteurPrincipal, int numeroParution) {
        super(numero, titre, auteurPrincipal);
        if (numeroParution <= 0) {
            throw new IllegalArgumentException("Le numero de parution doit etre > 0");
        }
        this.numeroParution = numeroParution;
        this.enConsultation = false;
        this.consultant = null;
    }

    public int getNumeroParution() {
        return numeroParution;
    }

    public boolean estEnConsultation() {
        return enConsultation;
    }

    public Lecteur getConsultant() {
        return consultant;
    }

    @Override
    public int dureePret() {
        return 7;
    }

    // Consultation sur place : on verifie tout AVANT de modifier l'etat.
    @Override
    public void consulterSurPlace(Lecteur l) {
        if (l == null) {
            throw new IllegalArgumentException("Le lecteur est obligatoire");
        }
        if (enConsultation) {
            throw new DocumentIndisponibleException(
                    "Document deja en consultation : " + getNumero());
        }
        if (!estDisponible()) {
            throw new DocumentIndisponibleException(
                    "Document emprunte, consultation impossible : " + getNumero());
        }
        this.enConsultation = true;
        this.consultant = l;
    }

    @Override
    public void terminerConsultation() {
        if (!enConsultation) {
            throw new IllegalStateException(
                    "Aucune consultation en cours : " + getNumero());
        }
        this.enConsultation = false;
        this.consultant = null;
    }

    @Override
    public String toString() {
        String etat;
        if (estDisponible()) {
            etat = "disponible";
        } else {
            etat = "emprunte par " + getEmprunteur().getPrenom() + " " + getEmprunteur().getNom();
        }
        String consult = enConsultation
                ? "  en consultation sur place par " + consultant.getPrenom() + " " + consultant.getNom()
                : "";

        return "Periodique n°" + getNumero() + " : \"" + getTitre() + "\" — " + getAuteurPrincipal()
                + " (parution n°" + numeroParution + ")"
                + " [" + etat + consult + "] : duree de pret : " + dureePret() + " jours";
    }
}
