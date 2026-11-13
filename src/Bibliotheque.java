import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bibliotheque implements CatalogueDocuments {

    private String nom;
    private Map<Integer, Document> catalogue;
    private Set<Lecteur> adherents;
    private List<String> historiquePrets;

    public Bibliotheque(String nom) {
        this.nom = nom;
        this.catalogue = new HashMap<>();
        this.adherents = new HashSet<>();
        this.historiquePrets = new ArrayList<>();
    }

    public boolean ajouterDocument(Document d) {
        if (d == null) {
            System.out.println("Erreur : impossible d'ajouter un document null.");
            return false;
        }
        if (catalogue.containsKey(d.getNumero())) {
            System.out.println("Refus : le document n°" + d.getNumero() + " existe deja.");
            return false;
        }
        catalogue.put(d.getNumero(), d);
        return true;
    }

    public Document chercherParNumero(int numero) {
        return catalogue.get(numero);
    }

    public List<Document> getTousLesDocuments() {
        return new ArrayList<>(catalogue.values());
    }

    public boolean retirerDocument(int numero) {
        Document document = catalogue.remove(numero);
        if (document == null) {
            System.out.println("Aucun document trouve avec le numero : " + numero);
            return false;
        }
        System.out.println("Document retire avec succes : " + document.getTitre());
        return true;
    }

    public void afficherCatalogue() {
        System.out.println("Catalogue de la bibliotheque " + nom);
        System.out.println("Nombre de documents : " + catalogue.size());
        for (Document d : catalogue.values()) {
            System.out.println(d.toString());
        }
    }

    public void afficherDisponibles() {
        System.out.println("Documents disponibles dans la bibliotheque " + nom);
        int count = 0;
        for (Document d : catalogue.values()) {
            if (d.estDisponible()) {
                System.out.println(d.toString());
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
            if (emp != null && emp.getNumero() == l.getNumero()) {
                System.out.println(d.toString());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Aucun document emprunte par ce lecteur");
        }
    }

    public List<Document> chercherParTitre(String titre) {
        List<Document> resultat = new ArrayList<>();
        for (Document d : catalogue.values()) {
            if (d.getTitre().toLowerCase().contains(titre.toLowerCase())) {
                resultat.add(d);
            }
        }
        if (resultat.isEmpty()) {
            System.out.println("Aucun document trouve avec le titre : " + titre);
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
            String auteur = d.getAuteurPrincipal();
            if (!groupes.containsKey(auteur)) {
                groupes.put(auteur, new ArrayList<>());
            }
            groupes.get(auteur).add(d);
        }
        return groupes;
    }

    public boolean inscrireAdherent(Lecteur l) {
        if (l == null) {
            System.out.println("Erreur : lecteur null.");
            return false;
        }
        boolean ajout = adherents.add(l);
        if (!ajout) {
            System.out.println("Refus : le lecteur n°" + l.getNumero() + " est deja inscrit.");
        } else {
            System.out.println("Adherent inscrit : " + l.getPrenom() + " " + l.getNom());
        }
        return ajout;
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

    public void enregistrerPret(Lecteur l, Document d) {
        String entree = l.getPrenom() + " " + l.getNom()
                + " a emprunte \"" + d.getTitre()
                + "\" (doc n°" + d.getNumero() + ")";
        historiquePrets.add(entree);
    }

    public void enregistrerRetour(Lecteur l, Document d) {
        String entree = l.getPrenom() + " " + l.getNom()
                + " a retourne \"" + d.getTitre()
                + "\" (doc n°" + d.getNumero() + ")";
        historiquePrets.add(entree);
    }

    public List<String> getHistoriquePrets() {
        return new ArrayList<>(historiquePrets);
    }

    public void afficherHistorique() {
        System.out.println("Historique des prets de la bibliotheque " + nom + " :");
        if (historiquePrets.isEmpty()) {
            System.out.println("  Aucun pret enregistre.");
            return;
        }
        for (int i = 0; i < historiquePrets.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + historiquePrets.get(i));
        }
    }

    public int getNbDocuments() {
        return catalogue.size();
    }

    public String getNom() {
        return nom;
    }
}
