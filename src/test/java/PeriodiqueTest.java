
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests complementaires (au-dela des 16) : consultation sur place
class PeriodiqueTest {

    private Periodique periodique;
    private Lecteur alice, bob;

    @BeforeEach
    void setUp() {
        periodique = new Periodique(201, "Pour la Science", "Redaction", 548);
        alice = new Lecteur("Diallo", "Alice", 1, "Ouagadougou");
        bob = new Lecteur("Traore", "Bob", 2, "Ouagadougou");
    }

    @Test
    void consulterDocumentEmprunteRefuseEtGardeLEtat() {
        periodique.emprunter(alice);

        assertThrows(DocumentIndisponibleException.class,
                () -> periodique.consulterSurPlace(bob));

        assertFalse(periodique.estEnConsultation());
        assertNull(periodique.getConsultant());
        assertSame(alice, periodique.getEmprunteur());
    }

    @Test
    void terminerConsultationNonCommenceeRefuseEtGardeLEtat() {
        assertThrows(IllegalStateException.class,
                () -> periodique.terminerConsultation());

        assertFalse(periodique.estEnConsultation());
        assertNull(periodique.getConsultant());
    }

    @Test
    void consulterReussiPuisTerminer() {
        periodique.consulterSurPlace(alice);

        assertTrue(periodique.estEnConsultation());
        assertSame(alice, periodique.getConsultant());

        periodique.terminerConsultation();

        assertFalse(periodique.estEnConsultation());
        assertNull(periodique.getConsultant());
    }

    @Test
    void consulterPuisEmprunterRefuseEtGardeLEtat() {
        periodique.consulterSurPlace(alice);

        assertThrows(DocumentIndisponibleException.class,
                () -> periodique.emprunter(bob));

        assertTrue(periodique.estEnConsultation());
        assertSame(alice, periodique.getConsultant());
        assertTrue(periodique.estDisponible());
        assertNull(periodique.getEmprunteur());
    }

    @Test
    void doubleConsultationRefuseEtGardeLEtat() {
        periodique.consulterSurPlace(alice);

        assertThrows(DocumentIndisponibleException.class,
                () -> periodique.consulterSurPlace(bob));

        assertTrue(periodique.estEnConsultation());
        assertSame(alice, periodique.getConsultant());
    }
}
