import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bibliotheque implements CatalogueDocuments {

    private final String nom;
    private final Map<Integer, Document> catalogue;
    private final Set<Lecteur> adherents;

    public Bibliotheque(String nom) {
        this.nom = nom;
        this.catalogue = new HashMap<>();
        this.adherents = new HashSet<>();
    }

    // Catalogue 

    @Override
    public boolean ajouterDocument(Document d) {
        if (d == null) {
            throw new IllegalArgumentException("Le document est obligatoire");
        }
        if (catalogue.containsKey(d.getNumero())) {
            return false; // doublon refuse, catalogue inchange
        }
        catalogue.put(d.getNumero(), d);
        return true;
    }

    // Renvoie le vrai document (pas une copie) pour que ServicePret puisse le modifier
    @Override
    public Document chercherParNumero(int numero) {
        return catalogue.get(numero);
    }

    @Override
    public boolean retirerDocument(int numero) {
        return catalogue.remove(numero) != null;
    }

    @Override
    public List<Document> getTousLesDocuments() {
        return new ArrayList<>(catalogue.values());
    }

    public int getNbDocuments() {
        return catalogue.size();
    }

    public List<Document> chercherParTitre(String titre) {
        if (titre == null) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }
        List<Document> resultat = new ArrayList<>();
        for (Document d : catalogue.values()) {
            if (d.getTitre().toLowerCase().contains(titre.toLowerCase())) {
                resultat.add(d);
            }
        }
        return resultat;
    }

    public List<Document> getDocumentsTriesParTitre() {
        List<Document> copie = new ArrayList<>(catalogue.values());
        Collections.sort(copie, new Comparator<Document>() {
            @Override
            public int compare(Document d1, Document d2) {
                return d1.getTitre().compareTo(d2.getTitre());
            }
        });
        return copie;
    }

    public Map<String, List<Document>> regrouperParAuteur() {
        Map<String, List<Document>> groupes = new HashMap<>();
        for (Document d : catalogue.values()) {
            groupes.computeIfAbsent(d.getAuteurPrincipal(), k -> new ArrayList<>()).add(d);
        }
        return groupes;
    }

    //  Adhérents

    public boolean inscrireAdherent(Lecteur l) {
        if (l == null) {
            throw new IllegalArgumentException("Le lecteur est obligatoire");
        }
        return adherents.add(l); // false si deja inscrit
    }

    public boolean estAdherent(Lecteur l) {
        return adherents.contains(l);
    }

    public Set<Lecteur> getAdherents() {
        return new HashSet<>(adherents);
    }

    public int getNbAdherents() {
        return adherents.size();
    }

    //  Affichage (pas de logique metier)

    public void afficherCatalogue() {
        System.out.println("Catalogue de la bibliotheque " + nom);
        System.out.println("Nombre de documents : " + catalogue.size());
        for (Document d : catalogue.values()) {
            System.out.println(d);
        }
    }

    public void afficherDisponibles() {
        System.out.println("Documents disponibles dans la bibliotheque " + nom);
        int count = 0;
        for (Document d : catalogue.values()) {
            if (d.estDisponible()) {
                System.out.println(d);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Aucun document disponible");
        }
    }

    public void afficherEmpruntesParLecteur(Lecteur l) {
        System.out.println("Documents empruntes par " + l.getPrenom() + " " + l.getNom());
        int count = 0;
        for (Document d : catalogue.values()) {
            Lecteur emp = d.getEmprunteur();
            if (emp != null && emp.equals(l)) {
                System.out.println(d);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Aucun document emprunte par ce lecteur");
        }
    }

    public void afficherAdherents() {
        System.out.println("Adherents de la bibliotheque " + nom + " (" + adherents.size() + ") :");
        if (adherents.isEmpty()) {
            System.out.println("  Aucun adherent inscrit.");
            return;
        }
        for (Lecteur l : adherents) {
            System.out.println("  " + l);
        }
    }

    public String getNom() {
        return nom;
    }
}
