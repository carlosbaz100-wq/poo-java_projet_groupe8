import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Livre l1 = new Livre(101, "Introduction a Java", "Kathy Sierra", 320);
        l1.ajouterAuteur("Bert Bates");

        Livre l2 = new Livre(102, "Une si longue lettre", "Mariama Ba", 168);

        Periodique p1 = new Periodique(201, "Science et technique", "Redaction", 12);
        Periodique p2 = new Periodique(202, "Pour la Science", "Redaction", 548);

        Lecteur lec1 = new Lecteur("KOUSSE", "Souleymane", 1001, "Ouagadougou");
        Lecteur lec2 = new Lecteur("BAZONGO", "Carlos", 1002, "Ouagadougou");

        Bibliotheque bib = new Bibliotheque("Bibliotheque 2iE");

        System.out.println("TEST 1 : affichage via type parent");
        Document d1 = l1;
        Document d2 = p1;
        System.out.println(d1);
        System.out.println(d2);

        System.out.println("\nTEST 2 : dureePret");
        System.out.println("Duree de pret du livre     : " + d1.dureePret() + " jours");
        System.out.println("Duree de pret du periodique: " + d2.dureePret() + " jours");

        System.out.println("\nTEST 3 : disponibilite initiale");
        System.out.println("l1 disponible : " + l1.estDisponible());
        System.out.println("p1 disponible : " + p1.estDisponible());

        System.out.println("\nTEST 4 : emprunt normal");
        l1.emprunter(lec1);
        bib.enregistrerPret(lec1, l1);
        System.out.println("l1 disponible apres emprunt : " + l1.estDisponible());
        System.out.println(l1);

        System.out.println("\nTEST 5 : refus du double emprunt");
        l1.emprunter(lec2);

        System.out.println("\nTEST 6 : retour normal");
        l1.retourner();
        bib.enregistrerRetour(lec1, l1);
        System.out.println("l1 disponible apres retour : " + l1.estDisponible());
        System.out.println(l1);

        System.out.println("\nTEST 7 : refus du retour sans emprunt");
        l1.retourner();

        System.out.println("\nTEST 8 : auteurs du livre");
        System.out.println("Auteur principal : " + l1.getAuteurPrincipal());
        String[] auteurs = l1.getAuteurs();
        System.out.println("Nombre de co-auteurs : " + l1.getNbAuteurs());
        for (int i = 0; i < l1.getNbAuteurs(); i++) {
            System.out.println("  Co-auteur " + (i + 1) + " : " + auteurs[i]);
        }

        System.out.println("\nTEST 9 : emprunt sur d'autres documents");
        l2.emprunter(lec2);
        bib.enregistrerPret(lec2, l2);
        p1.emprunter(lec1);
        bib.enregistrerPret(lec1, p1);
        System.out.println(l2);
        System.out.println(p1);

        System.out.println("\nTEST 10 : dureePret() ");
        Document[] documents = { l1, l2, p1, p2 };
        for (int i = 0; i < documents.length; i++) {
            System.out.println(documents[i].getTitre() + " : " + documents[i].dureePret() + " jours");
        }

        System.out.println("\nTEST 11 : catalogue (ajout, doublon, recherche, retrait)");

        System.out.println("Nb documents : " + bib.getNbDocuments());

        bib.ajouterDocument(l1);
        bib.ajouterDocument(l2);
        bib.ajouterDocument(p1);
        bib.ajouterDocument(p2);
        System.out.println("Nb documents : " + bib.getNbDocuments());

        bib.ajouterDocument(l1);
        System.out.println("Nb documents apres doublon : " + bib.getNbDocuments());

        Document trouve = bib.chercherParNumero(101);
        System.out.println("Recherche n°101 : " + (trouve != null ? trouve.getTitre() : "null"));

        Document absent = bib.chercherParNumero(999);
        System.out.println("Recherche n°999 : " + absent);

        bib.retirerDocument(102);
        System.out.println("Nb documents apres retrait : " + bib.getNbDocuments());

        bib.retirerDocument(102);

        System.out.println("\nTEST 12 : adherents uniques");

        System.out.println("Nb adherents : " + bib.getNbAdherents());

        bib.inscrireAdherent(lec1);
        bib.inscrireAdherent(lec2);
        System.out.println("Nb adherents apres 2 inscriptions : " + bib.getNbAdherents());

        Lecteur lec1bis = new Lecteur("KOUSSE", "Souleymane", 1001, "Koudougou");
        bib.inscrireAdherent(lec1bis);
        System.out.println("Nb adherents apres doublon : " + bib.getNbAdherents());

        System.out.println("lec1.equals(lec1bis) : " + lec1.equals(lec1bis));
        System.out.println("lec1.hashCode() == lec1bis.hashCode() : "
                + (lec1.hashCode() == lec1bis.hashCode()));

        System.out.println("lec1 est adherent : " + bib.estAdherent(lec1));

        Set<Lecteur> copieAdherents = bib.getAdherents();
        copieAdherents.clear();
        System.out.println("Nb adherents actualise : " + bib.getNbAdherents());

        bib.afficherAdherents();

        System.out.println("\nTEST 13 : historique des prets");
        bib.afficherHistorique();

        List<String> copieHistorique = bib.getHistoriquePrets();
        int tailleAvant = bib.getHistoriquePrets().size();
        copieHistorique.clear();
        int tailleApres = bib.getHistoriquePrets().size();
        System.out.println("Historique protege : " + (tailleAvant == tailleApres));

        System.out.println("\nTEST 14 : recherche par titre ");
        List<Document> res1 = bib.chercherParTitre("java");
        List<Document> res2 = bib.chercherParTitre("JAVA");
        System.out.println("Recherche 'java'  : " + res1.size() + " resultat(s)");
        System.out.println("Recherche 'JAVA'  : " + res2.size() + " resultat(s)");
        System.out.println("Catalogue non modifie apres recherche : " + bib.getNbDocuments());

        System.out.println("\nTEST 15 : tri par titre");
        List<Document> tries = bib.getDocumentsTriesParTitre();
        for (Document doc : tries) {
            System.out.println("  " + doc.getTitre());
        }

        System.out.println("\nTEST 16 : regroupement par auteur");
        Map<String, List<Document>> parAuteur = bib.regrouperParAuteur();
        for (Map.Entry<String, List<Document>> entry : parAuteur.entrySet()) {
            System.out.println("  Auteur : " + entry.getKey()
                    + " — " + entry.getValue().size() + " document(s)");
        }
    }
}
