
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests 10 a 12 : retour via ServicePret
class ServicePretRetourTest {

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

    // Test 10 : Prouve : un retour reussi remet le document dans l'etat "libre".
    @Test
    void retourReussiRendDisponible() {
        service.emprunter(101, alice);

        service.retourner(101, alice);

        assertTrue(livre.estDisponible());
        assertNull(livre.getEmprunteur());
    }

    // Test 11 (REFUS) : Prouve : retour d'un document absent => exception, rien ne bouge.
    @Test
    void retourDocumentAbsentLeveException() {
        assertThrows(DocumentIntrouvableException.class,
                () -> service.retourner(999, alice));

        assertEquals(1, catalogue.getTousLesDocuments().size()); // catalogue inchange
        assertTrue(service.getHistorique().isEmpty());           // rien trace
    }

    // Test 12 (REFUS) : Prouve : retour sans emprunt => exception, document toujours libre.
    @Test
    void retourSansEmpruntLeveExceptionEtatInchange() {
        assertThrows(RetourImpossibleException.class,
                () -> service.retourner(101, alice));

        assertTrue(livre.estDisponible());                       // etat inchange
        assertNull(livre.getEmprunteur());                       // etat inchange
        assertTrue(service.getHistorique().isEmpty());           // rien trace
    }

    @Test
    void retourParMauvaisLecteurRefuseEtGardeLEmprunteur() {
        service.emprunter(101, alice);

        assertThrows(RetourImpossibleException.class,
                () -> service.retourner(101, bob));

        assertSame(alice, livre.getEmprunteur());
        assertFalse(livre.estDisponible());
        assertEquals(1, service.getHistorique().size());
    }

    @Test
    void retourDeuxFoisRefuseEtGardeLEtat() {
        service.emprunter(101, alice);
        service.retourner(101, alice);

        assertThrows(RetourImpossibleException.class,
                () -> service.retourner(101, alice));

        assertTrue(livre.estDisponible());
        assertNull(livre.getEmprunteur());
        assertEquals(2, service.getHistorique().size());
    }

    @Test
    void retourArgumentInvalideRefuse() {
        assertThrows(IllegalArgumentException.class, () -> service.retourner(0, alice));
        assertThrows(IllegalArgumentException.class, () -> service.retourner(101, null));

        assertTrue(livre.estDisponible());
        assertTrue(service.getHistorique().isEmpty());
    }
}
