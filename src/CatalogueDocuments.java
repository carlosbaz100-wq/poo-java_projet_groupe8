import java.util.List;
public interface CatalogueDocuments {
    boolean ajouterDocument(Document d);
    Document chercherParNumero(int numero);
    boolean retirerDocument(int numero);
    List<Document> getTousLesDocuments();
}