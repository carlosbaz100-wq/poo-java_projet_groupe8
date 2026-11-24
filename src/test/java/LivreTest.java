import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests complementaires : invariants et encapsulation de Livre
class LivreTest {

    private Livre livre;

    @BeforeEach
    void setUp() {
        livre = new Livre(101, "Java", "Sierra", 320);
    }

    @Test
    void nombrePagesNegatifOuNulRefuse() {
        assertThrows(IllegalArgumentException.class,
                () -> new Livre(102, "Titre", "Auteur", 0));
        assertThrows(IllegalArgumentException.class,
                () -> new Livre(102, "Titre", "Auteur", -1));
    }

    @Test
    void ajouterAuteurNullOuVideRefuse() {
        assertThrows(IllegalArgumentException.class, () -> livre.ajouterAuteur(null));
        assertThrows(IllegalArgumentException.class, () -> livre.ajouterAuteur("   "));

        assertEquals(0, livre.getNbAuteurs());
    }

    @Test
    void ajouterAuteurDoublonRefuse() {
        livre.ajouterAuteur("Bert Bates");

        assertThrows(IllegalArgumentException.class,
                () -> livre.ajouterAuteur("bert bates"));
        assertThrows(IllegalArgumentException.class,
                () -> livre.ajouterAuteur("Sierra"));

        assertEquals(1, livre.getNbAuteurs());
    }

    @Test
    void capaciteAuteursAtteinteRefuseEtGardeLEtat() {
        livre.ajouterAuteur("A");
        livre.ajouterAuteur("B");
        livre.ajouterAuteur("C");

        assertThrows(IllegalStateException.class, () -> livre.ajouterAuteur("D"));

        assertEquals(3, livre.getNbAuteurs());
        assertArrayEquals(new String[]{"A", "B", "C"}, livre.getAuteurs());
    }

    @Test
    void getAuteursRenvoieUneCopie() {
        livre.ajouterAuteur("Bert Bates");

        String[] copie = livre.getAuteurs();
        copie[0] = "Modifie";

        assertArrayEquals(new String[]{"Bert Bates"}, livre.getAuteurs());
    }

    @Test
    void getAuteursNeContientQueLesAuteursAjoutes() {
        assertArrayEquals(new String[]{}, livre.getAuteurs());

        livre.ajouterAuteur("Bert Bates");

        assertArrayEquals(new String[]{"Bert Bates"}, livre.getAuteurs());
    }
}
