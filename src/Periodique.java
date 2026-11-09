public class Periodique extends Document implements Consultable {

    private int numeroParution;
// Etat de consultation sur place
    private boolean enConsultation;
    private Lecteur consultant;

    public Periodique(int numero, String titre, String auteurPrincipal, int numeroParution) {
        super(numero, titre, auteurPrincipal);
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
 // Consultation sur place (on sait QUI consulte) 
    @Override
    public void consulterSurPlace(Lecteur l) {
        if (enConsultation) {
            System.out.println("Refus : \"" + getTitre() + "\" est deja en consultation par "
                    + consultant.getPrenom() + " " + consultant.getNom());
            return;
        }
        
        if (!estDisponible()) {
            System.out.println("Refus : \"" + getTitre() + "\" est emprunte, consultation impossible");
            return;
        }
        
        this.enConsultation = true;
        this.consultant = l;
        System.out.println(l.getPrenom() + " " + l.getNom()
                + " consulte sur place : \"" + getTitre() + "\"");
    }

    @Override
    public void terminerConsultation() {
        if (!enConsultation) {
            System.out.println("Refus : \"" + getTitre() + "\" n'est pas en consultation");
            return;
        }
        System.out.println(consultant.getPrenom() + " " + consultant.getNom()
                + " a termine la consultation de : \"" + getTitre() + "\"");
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
