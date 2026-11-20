
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests 5 a 9 : emprunt via ServicePret
class ServicePretEmpruntTest {

    private Bibliotheque catalogue;
    private ServicePret service;
    private Lecteur alice, bob;
    private Livre livre;

    @BeforeEach
    void setUp() {
        catalogue = new Bibliotheque("Test");
        alice = new Lecteur("Diallo", "Alice", 1, "Ouagadougou");
        bob = new Lecteur("Traore", "Bob", 2, "Ouagadougou");
        livre = new Livre(101, "Java", "Sierra", 320);
        catalogue.ajouterDocument(livre);
        service = new ServicePret(catalogue, Set.of(alice, bob));
    }

    // Test 5 — Prouve : un emprunt reussi est trace dans l'historique (une ligne).
    @Test
    void empruntReussiEcritDansLHistorique() {
        service.emprunter(101, alice);

        assertEquals(1, service.getHistorique().size());
        assertSame(alice, livre.getEmprunteur());
    }

    // Test 6 (REFUS) — Prouve : document absent => exception, rien ne bouge.
    @Test
    void empruntDocumentAbsentLeveException() {
        assertThrows(DocumentIntrouvableException.class,
                () -> service.emprunter(999, alice));

        assertEquals(1, catalogue.getTousLesDocuments().size()); // catalogue inchange
        assertTrue(livre.estDisponible());                       // document inchange
        assertTrue(service.getHistorique().isEmpty());           // rien trace
    }

    // Test 7 (REFUS) — Prouve : document deja prete => exception, 1er emprunteur conserve.
    @Test
    void empruntDejaPreteLeveExceptionEtatInchange() {
        service.emprunter(101, alice);

        assertThrows(DocumentIndisponibleException.class,
                () -> service.emprunter(101, bob));

        assertSame(alice, livre.getEmprunteur());                // etat inchange
        assertFalse(livre.estDisponible());                      // etat inchange
        assertEquals(1, service.getHistorique().size());         // pas de ligne en plus
    }

    // Test 8 (REFUS) — Prouve : lecteur non inscrit => exception, document intact.
    @Test
    void empruntLecteurNonInscritLeveExceptionEtatInchange() {
        Lecteur intrus = new Lecteur("Ouedraogo", "Eve", 3, "Bobo-Dioulasso");

        assertThrows(AdherentNonInscritException.class,
                () -> service.emprunter(101, intrus));

        assertTrue(livre.estDisponible());                       // etat inchange
        assertNull(livre.getEmprunteur());                       // etat inchange
        assertTrue(service.getHistorique().isEmpty());           // rien trace
    }

    // Test 9 (REFUS) — Prouve : un argument invalide (lecteur null) leve IllegalArgumentException.
    @Test
    void empruntArgumentInvalideLeveIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.emprunter(101, null));

        assertTrue(livre.estDisponible());                       // etat inchange
        assertTrue(service.getHistorique().isEmpty());           // rien trace
    }
}
