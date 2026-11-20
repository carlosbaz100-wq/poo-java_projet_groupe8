
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests 13 a 16 : contrat du catalogue (ajout, recherche, retrait)
class CatalogueTest {

    private Bibliotheque catalogue;
    private Livre livre;
    private Periodique periodique;

    @BeforeEach
    void setUp() {
        catalogue = new Bibliotheque("Test");
        livre = new Livre(101, "Java", "Sierra", 320);
        periodique = new Periodique(201, "Pour la Science", "Redaction", 548);
        catalogue.ajouterDocument(livre);
        catalogue.ajouterDocument(periodique);
    }

    // Test 13 — Prouve : chercherParNumero renvoie le VRAI document (pas une copie).
    @Test
    void ajoutPuisRechercheParNumero() {
        assertSame(livre, catalogue.chercherParNumero(101));
        assertSame(periodique, catalogue.chercherParNumero(201));
    }

    // Test 14 (REFUS) — Prouve : deux documents de meme numero ne coexistent pas.
    @Test
    void ajoutDoublonRefuse() {
        Livre doublon = new Livre(101, "Autre titre", "Autre auteur", 100);

        assertFalse(catalogue.ajouterDocument(doublon));

        assertEquals(2, catalogue.getTousLesDocuments().size()); // taille inchangee
        assertSame(livre, catalogue.chercherParNumero(101));     // l'original est conserve
    }

    // Test 15 — Prouve : retirer un document existant le supprime et reduit la taille de 1.
    @Test
    void retraitDocumentExistant() {
        assertTrue(catalogue.retirerDocument(101));

        assertNull(catalogue.chercherParNumero(101));
        assertEquals(1, catalogue.getTousLesDocuments().size());
    }

    // Test 16 (REFUS) — Prouve : retirer un document absent ne change rien.
    @Test
    void retraitDocumentAbsentRefuse() {
        assertFalse(catalogue.retirerDocument(999));

        assertEquals(2, catalogue.getTousLesDocuments().size()); // catalogue inchange
    }
}
