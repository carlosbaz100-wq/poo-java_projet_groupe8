public class Document {

    private int numero;
    private String titre;
    private String auteur;
    private boolean disponible;
    private Lecteur emprunteur;

    public Document(int numero, String titre, String auteur) {
        this.numero = numero;
        this.titre = titre;
        this.auteur = auteur;
        this.disponible = true;
        this.emprunteur = null;
    }

    public int getNumero() {
        return numero;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public boolean estDisponible() {
        return disponible;
    }

    public Lecteur getEmprunteur() {
        return emprunteur;
    }

    public void emprunter(Lecteur l) {
        if (!disponible) {
            System.out.println("Refus : \"" + titre + "\" est deja emprunte par "
                    + emprunteur.getPrenom() + " " + emprunteur.getNom());
            return;
        }
        this.emprunteur = l;
        this.disponible = false;
        System.out.println(l.getPrenom() + " " + l.getNom()
                + " a emprunte : \"" + titre + "\"");
    }

    public void retourner() {
        if (disponible) {
            System.out.println("Refus : \"" + titre + "\" est deja disponible, pas d'emprunt en cours");
            return;
        }
        System.out.println(emprunteur.getPrenom() + " " + emprunteur.getNom()
                + " a retourne : \"" + titre + "\"");
        this.emprunteur = null;
        this.disponible = true;
    }

    @Override
    public String toString() {
        String etat;
        if (disponible) {
            etat = "disponible";
        } else {
            etat = "emprunte par " + emprunteur.getPrenom() + " " + emprunteur.getNom();
        }
        return "Document n°" + numero + " : \"" + titre + "\" — " + auteur + " [" + etat + "]";
    }
}
