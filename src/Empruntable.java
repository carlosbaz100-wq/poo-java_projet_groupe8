public interface Empruntable {
    void emprunter(Lecteur l);
    void retourner();
    boolean estDisponible();
    Lecteur getEmprunteur();
}
