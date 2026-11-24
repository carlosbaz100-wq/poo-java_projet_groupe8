import java.util.List;
import java.util.Set;

/**
 * Demonstration du jalon 4 : le service de pret orchestre les operations,
 * les refus levent des exceptions et l'etat reste coherent.
 */
public class Main {

    public static void main(String[] args) {

        Bibliotheque bibliotheque = new Bibliotheque("Bibliotheque 2iE");

        Livre livreJava = new Livre(101, "Introduction a Java", "Kathy Sierra", 320);
        livreJava.ajouterAuteur("Bert Bates");
        Livre livreLettre = new Livre(102, "Une si longue lettre", "Mariama Ba", 168);
        Periodique revue = new Periodique(201, "Pour la Science", "Redaction", 548);

        Lecteur alice = new Lecteur("KOUSSE", "Souleymane", 1001, "Ouagadougou");
        Lecteur bob = new Lecteur("BAZONGO", "Carlos", 1002, "Ouagadougou");
        Lecteur eve = new Lecteur("OUEDRAOGO", "Eve", 1003, "Bobo-Dioulasso");

        bibliotheque.ajouterDocument(livreJava);
        bibliotheque.ajouterDocument(livreLettre);
        bibliotheque.ajouterDocument(revue);
        bibliotheque.inscrireAdherent(alice);
        bibliotheque.inscrireAdherent(bob);

        Set<Lecteur> adherents = bibliotheque.getAdherents();
        ServicePret service = new ServicePret(bibliotheque, adherents);

        System.out.println("Catalogue de " + bibliotheque.getNom());
        bibliotheque.afficherCatalogue();

        System.out.println();
        System.out.println("Etape 1 : emprunt accepte");
        service.emprunter(101, alice);
        System.out.println("Disponible : " + livreJava.estDisponible());
        System.out.println("Emprunteur : " + nomComplet(livreJava));

        System.out.println();
        System.out.println("Etape 2 : second emprunt du meme document");
        try {
            service.emprunter(101, bob);
            System.out.println("Anomalie : le second emprunt aurait du etre refuse");
        } catch (DocumentIndisponibleException e) {
            System.out.println("Refus attendu : " + e.getMessage());
        }
        System.out.println("Emprunteur inchange : " + nomComplet(livreJava));
        System.out.println("Historique : " + service.getHistorique().size() + " ligne(s)");

        System.out.println();
        System.out.println("Etape 3 : lecteur non inscrit");
        try {
            service.emprunter(102, eve);
            System.out.println("Anomalie : un lecteur non inscrit a pu emprunter");
        } catch (AdherentNonInscritException e) {
            System.out.println("Refus attendu : " + e.getMessage());
        }
        System.out.println("Document toujours disponible : " + livreLettre.estDisponible());

        System.out.println();
        System.out.println("Etape 4 : document absent du catalogue");
        try {
            service.emprunter(999, alice);
            System.out.println("Anomalie : un document absent a pu etre emprunte");
        } catch (DocumentIntrouvableException e) {
            System.out.println("Refus attendu : " + e.getMessage());
        }

        System.out.println();
        System.out.println("Etape 5 : retour du document");
        service.retourner(101, alice);
        System.out.println("Disponible : " + livreJava.estDisponible());
        System.out.println("Emprunteur : " + livreJava.getEmprunteur());

        System.out.println();
        System.out.println("Etape 6 : retour par un lecteur qui n'a rien emprunte");
        try {
            service.retourner(101, bob);
            System.out.println("Anomalie : un retour sans emprunt a ete accepte");
        } catch (RetourImpossibleException e) {
            System.out.println("Refus attendu : " + e.getMessage());
        }

        System.out.println();
        System.out.println("Etape 7 : historique des operations reussies");
        for (String ligne : service.getHistorique()) {
            System.out.println("   " + ligne);
        }

        System.out.println();
        System.out.println("Etape 8 : catalogue final");
        bibliotheque.afficherCatalogue();
        System.out.println("Nombre de documents : " + bibliotheque.getNbDocuments());
        System.out.println("Nombre d'adherents : " + bibliotheque.getNbAdherents());

        System.out.println();
        System.out.println("Etape 9 : consultation d'un periodique");
        revue.consulterSurPlace(alice);
        System.out.println(revue);
        try {
            service.emprunter(201, bob);
            System.out.println("Anomalie : un periodique en consultation a pu etre emprunte");
        } catch (DocumentIndisponibleException e) {
            System.out.println("Refus attendu : " + e.getMessage());
        }
        revue.terminerConsultation();
        System.out.println("Consultation terminee : " + !revue.estEnConsultation());

        System.out.println();
        System.out.println("Etape 10 : tri par titre");
        List<Document> tries = bibliotheque.getDocumentsTriesParTitre();
        for (Document document : tries) {
            System.out.println("   " + document.getTitre());
        }
    }

    private static String nomComplet(Document document) {
        if (document.getEmprunteur() == null) {
            return "aucun";
        }
        return document.getEmprunteur().getPrenom() + " " + document.getEmprunteur().getNom();
    }
}
