
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
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

    // Test 13 : Prouve : chercherParNumero renvoie le VRAI document (pas une copie).
    @Test
    void ajoutPuisRechercheParNumero() {
        assertSame(livre, catalogue.chercherParNumero(101));
        assertSame(periodique, catalogue.chercherParNumero(201));
    }

    // Test 14 (REFUS) : Prouve : deux documents de meme numero ne coexistent pas.
    @Test
    void ajoutDoublonRefuse() {
        Livre doublon = new Livre(101, "Autre titre", "Autre auteur", 100);

        assertFalse(catalogue.ajouterDocument(doublon));

        assertEquals(2, catalogue.getTousLesDocuments().size()); // taille inchangee
        assertSame(livre, catalogue.chercherParNumero(101));     // l'original est conserve
    }

    // Test 15 : Prouve : retirer un document existant le supprime et reduit la taille de 1.
    @Test
    void retraitDocumentExistant() {
        assertTrue(catalogue.retirerDocument(101));

        assertNull(catalogue.chercherParNumero(101));
        assertEquals(1, catalogue.getTousLesDocuments().size());
    }

    // Test 16 (REFUS) : Prouve : retirer un document absent ne change rien.
    @Test
    void retraitDocumentAbsentRefuse() {
        assertFalse(catalogue.retirerDocument(999));

        assertEquals(2, catalogue.getTousLesDocuments().size()); // catalogue inchange
    }

    @Test
    void retraitDocumentEmprunteRefuseEtGardeLEtat() {
        Lecteur alice = new Lecteur("Diallo", "Alice", 1, "Ouagadougou");
        livre.emprunter(alice);

        assertThrows(DocumentIndisponibleException.class,
                () -> catalogue.retirerDocument(101));

        assertSame(livre, catalogue.chercherParNumero(101));
        assertEquals(2, catalogue.getTousLesDocuments().size());
    }

    @Test
    void retraitPeriodiqueEnConsultationRefuseEtGardeLEtat() {
        Lecteur alice = new Lecteur("Diallo", "Alice", 1, "Ouagadougou");
        periodique.consulterSurPlace(alice);

        assertThrows(DocumentIndisponibleException.class,
                () -> catalogue.retirerDocument(201));

        assertSame(periodique, catalogue.chercherParNumero(201));
        assertEquals(2, catalogue.getTousLesDocuments().size());
    }

    @Test
    void rechercheParTitreIgnoreLaCasse() {
        List<Document> resultat = catalogue.chercherParTitre("java");

        assertEquals(1, resultat.size());
        assertSame(livre, resultat.get(0));
    }

    @Test
    void triParTitre() {
        List<Document> tries = catalogue.getDocumentsTriesParTitre();

        assertEquals("Java", tries.get(0).getTitre());
        assertEquals("Pour la Science", tries.get(1).getTitre());
    }

    @Test
    void regroupementParAuteur() {
        Map<String, List<Document>> groupes = catalogue.regrouperParAuteur();

        assertEquals(2, groupes.size());
        assertSame(livre, groupes.get("Sierra").get(0));
        assertSame(periodique, groupes.get("Redaction").get(0));
    }

    @Test
    void getTousLesDocumentsRenvoieUneCopie() {
        List<Document> liste = catalogue.getTousLesDocuments();
        liste.clear();

        assertEquals(2, catalogue.getTousLesDocuments().size());
    }

    @Test
    void getAdherentsEstUneVueNonModifiableEtVivante() {
        Lecteur alice = new Lecteur("Diallo", "Alice", 1, "Ouagadougou");
        catalogue.inscrireAdherent(alice);
        Set<Lecteur> vue = catalogue.getAdherents();

        assertEquals(1, vue.size());
        assertThrows(UnsupportedOperationException.class,
                () -> vue.add(new Lecteur("Traore", "Bob", 2, "Ouagadougou")));

        Lecteur bob = new Lecteur("Traore", "Bob", 2, "Ouagadougou");
        catalogue.inscrireAdherent(bob);

        assertEquals(2, vue.size());
    }
}
