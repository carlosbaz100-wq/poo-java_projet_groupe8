import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ServicePret {

    private final CatalogueDocuments catalogue;
    private final Collection<Lecteur> adherents;
    private final List<String> historique = new ArrayList<>();

    public ServicePret(CatalogueDocuments catalogue, Collection<Lecteur> adherents) {
        this.catalogue = Objects.requireNonNull(catalogue, "Le catalogue est obligatoire");
        this.adherents = Set.copyOf(Objects.requireNonNull(adherents, "Les adherents sont obligatoires"));
    }

    public void emprunter(int numeroDocument, Lecteur lecteur) {
         // Arguments
        verifierArguments(numeroDocument, lecteur);
        // Existence
        Document d = trouverDocument(numeroDocument);
        // Regles metier
        if (!adherents.contains(lecteur)) {
            throw new AdherentNonInscritException("Lecteur non inscrit : " + lecteur.getNumero());
        }
        // 4. Mutation 
        d.emprunter(lecteur);
        // 5. Historique 
        historique.add(lecteur.getNumero() + " a emprunte le document " + numeroDocument);
    }

    public void retourner(int numeroDocument, Lecteur lecteur) {
        // 1. Arguments
        verifierArguments(numeroDocument, lecteur);
        // 2. Existence
        Document d = trouverDocument(numeroDocument);
        // 3. Regles metier
        if (d.getEmprunteur() == null) {
            throw new RetourImpossibleException("Aucun emprunt en cours : " + numeroDocument);
        }
        if (!d.getEmprunteur().equals(lecteur)) {
            throw new RetourImpossibleException(
                    "Ce lecteur n'a pas emprunte ce document : " + numeroDocument);
        }
        // 4. Mutation
        d.retourner();
        // 5. Historique
        historique.add(lecteur.getNumero() + " a retourne le document " + numeroDocument);
    }

    public List<String> getHistorique() {
        return List.copyOf(historique);
    }

    private void verifierArguments(int numeroDocument, Lecteur lecteur) {
        if (numeroDocument <= 0) {
            throw new IllegalArgumentException("Le numero de document doit etre > 0");
        }
        if (lecteur == null) {
            throw new IllegalArgumentException("Le lecteur est obligatoire");
        }
    }

    private Document trouverDocument(int numeroDocument) {
        Document d = catalogue.chercherParNumero(numeroDocument);
        if (d == null) {
            throw new DocumentIntrouvableException("Document absent : " + numeroDocument);
        }
        return d;
    }
}
