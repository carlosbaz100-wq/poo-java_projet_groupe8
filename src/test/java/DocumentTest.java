
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests 1 a 4 : transitions d'etat de Document (via Livre)
class DocumentTest {

    private Livre livre;
    private Lecteur alice, bob;

    @BeforeEach
    void setUp() {
        livre = new Livre(101, "Java", "Sierra", 320);
        alice = new Lecteur("Diallo", "Alice", 1, "Ouagadougou");
        bob = new Lecteur("Traore", "Bob", 2, "Ouagadougou");
    }

    // Test 1 — Prouve : un document neuf est dans un etat valide de depart.
    @Test
    void nouveauDocumentEstDisponible() {
        assertTrue(livre.estDisponible());
        assertNull(livre.getEmprunteur());
    }

    // Test 2 — Prouve : emprunter change bien la disponibilite ET l'emprunteur.
    @Test
    void emprunterRendLeDocumentIndisponible() {
        livre.emprunter(alice);

        assertFalse(livre.estDisponible());
        assertSame(alice, livre.getEmprunteur());
    }

    // Test 3 (REFUS) — Prouve : le 2e emprunt leve l'exception et ne vole pas le document.
    @Test
    void emprunterDeuxFoisRefuseEtGardeLEmprunteur() {
        livre.emprunter(alice);

        assertThrows(DocumentIndisponibleException.class, () -> livre.emprunter(bob));

        assertSame(alice, livre.getEmprunteur());   // etat inchange
        assertFalse(livre.estDisponible());         // etat inchange
    }

    // Test 4 (REFUS) — Prouve : on ne peut pas retourner ce qui n'est pas emprunte.
    @Test
    void retournerSansEmpruntRefuseEtGardeLEtat() {
        assertThrows(RetourImpossibleException.class, () -> livre.retourner());

        assertTrue(livre.estDisponible());          // etat inchange
        assertNull(livre.getEmprunteur());          // etat inchange
    }
}
