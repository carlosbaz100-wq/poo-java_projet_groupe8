import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ServicePret {

    private final CatalogueDocuments catalogue;
    private final Collection<Lecteur> adherents;
    private final List<String> historique = new ArrayList<>();

    public ServicePret(CatalogueDocuments catalogue, Collection<Lecteur> adherents) {
        this.catalogue = Objects.requireNonNull(catalogue, "Le catalogue est obligatoire");
        this.adherents = Collections.unmodifiableCollection(
                Objects.requireNonNull(adherents, "Les adherents sont obligatoires"));
    }

    public void emprunter(int numeroDocument, Lecteur lecteur) {
        // 1. Arguments
        verifierArguments(numeroDocument, lecteur);
        // 2. Existence
        Document d = trouverDocument(numeroDocument);
        // 3. Regles metier
        if (!adherents.contains(lecteur)) {
            throw new AdherentNonInscritException("Lecteur non inscrit : " + lecteur.getNumero());
        }
        if (!(d instanceof Empruntable empruntable)) {
            throw new DocumentIndisponibleException("Document non empruntable : " + numeroDocument);
        }
        // 4. Mutation
        empruntable.emprunter(lecteur);
        // 5. Historique
        historique.add(lecteur.getNumero() + " a emprunte le document " + numeroDocument);
    }

    public void retourner(int numeroDocument, Lecteur lecteur) {
        // 1. Arguments
        verifierArguments(numeroDocument, lecteur);
        // 2. Existence
        Document d = trouverDocument(numeroDocument);
        // 3. Regles metier
        if (!(d instanceof Empruntable empruntable)) {
            throw new DocumentIndisponibleException("Document non empruntable : " + numeroDocument);
        }
        if (empruntable.getEmprunteur() == null) {
            throw new RetourImpossibleException("Aucun emprunt en cours : " + numeroDocument);
        }
        if (!empruntable.getEmprunteur().equals(lecteur)) {
            throw new RetourImpossibleException(
                    "Ce lecteur n'a pas emprunte ce document : " + numeroDocument);
        }
        // 4. Mutation
        empruntable.retourner();
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
