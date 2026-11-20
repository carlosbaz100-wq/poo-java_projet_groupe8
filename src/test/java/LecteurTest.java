
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

// Tests complementaires (au-dela des 16) : invariants et egalite de Lecteur
class LecteurTest {

    @Test
    void nomVideRefuse() {
        assertThrows(IllegalArgumentException.class,
                () -> new Lecteur(" ", "Alice", 1, "Ouagadougou"));
    }

    @Test
    void numeroNegatifOuNulRefuse() {
        assertThrows(IllegalArgumentException.class,
                () -> new Lecteur("Diallo", "Alice", 0, "Ouagadougou"));
    }

    @Test
    void deuxLecteursDeMemeNumeroSontLeMemeAdherent() {
        Lecteur a1 = new Lecteur("Diallo", "Alice", 1, "Ouagadougou");
        Lecteur a2 = new Lecteur("Autre", "Nom", 1, "Bobo-Dioulasso");

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());

        Set<Lecteur> ensemble = new HashSet<>();
        ensemble.add(a1);
        ensemble.add(a2);
        assertEquals(1, ensemble.size());
    }
}
