public class Main {

    public static void main(String[] args) {

        //  Creation des objets
        Livre l1 = new Livre(101, "Introduction a Java", "Kathy Sierra", 320);
        l1.ajouterAuteur("Bert Bates");

        Livre l2 = new Livre(102, "Une si longue lettre", "Mariama Ba", 168);

        Periodique p1 = new Periodique(201, "Science et technique", "Redaction", 12);
        Periodique p2 = new Periodique(202, "Pour la Science", "Redaction", 548);

        Lecteur lec1 = new Lecteur("KOUSSE", "Souleymane", 1001, "Ouagadougou");
        Lecteur lec2 = new Lecteur("BAZONGO", "Carlos", 1002, "Ouagadougou");

        // TEST 1 : affichage initial via type parent
        System.out.println("TEST 1 : affichage via type parent ");
        Document d1 = l1;
        Document d2 = p1;
        System.out.println(d1);
        System.out.println(d2);

        // TEST 2 : dureePret() polymorphe
        System.out.println("TEST 2 : dureePret() polymorphe");
        System.out.println("Duree de pret du livre     : " + d1.dureePret() + " jours (attendu : 21)");
        System.out.println("Duree de pret du periodique: " + d2.dureePret() + " jours (attendu : 7)");

        // TEST 3 : disponibilite initiale
        System.out.println("TEST 3 : disponibilite initiale ");
        System.out.println("l1 disponible : " + l1.estDisponible() + " (attendu : true)");
        System.out.println("p1 disponible : " + p1.estDisponible() + " (attendu : true)");

        // TEST 4 : emprunt normal
        System.out.println("TEST 4 : emprunt normal ");
        l1.emprunter(lec1);
        System.out.println("l1 disponible apres emprunt : " + l1.estDisponible() + " (attendu : false)");
        System.out.println(l1);

        // TEST 5 : refus du double emprunt
        System.out.println();
        System.out.println("TEST 5 : refus du double emprunt ");
        l1.emprunter(lec2);

        // TEST 6 : retour
        System.out.println("TEST6 : retour normal ");
        l1.retourner();
        System.out.println("l1 disponible apres retour : " + l1.estDisponible() + " (attendu : true)");
        System.out.println(l1);

        // TEST 7 : refus du retour sans emprunt
        System.out.println(" TEST 7 : refus du retour sans emprunt ");
        l1.retourner();

        // TEST 8 : tableau d'auteurs
        System.out.println(" TEST 8 : auteurs du livre ");
        System.out.println("Auteur principal : " + l1.getAuteurPrincipal());
        String[] auteurs = l1.getAuteurs();
        System.out.println("Nombre de co-auteurs : " l1.getNbAuteurs();
        for (int i = 0; i <l1.getNbAuteurs(); i++) {
            System.out.println("  Co-auteur " + (i + 1) + " : " + auteurs[i]);
        }

        // TEST 9 : deuxieme livre et periodique
        System.out.println("TEST 9 : emprunt sur d'autres documents ");
        l2.emprunter(lec2);
        p1.emprunter(lec1);
        System.out.println(l2);
        System.out.println(p1);

        // TEST 10 : dureePret() via type parent sur tableau
        System.out.println(" TEST 10 : dureePret() polymorphe sur tableau de documents ");
        Document[] documents = { l1, l2, p1, p2 };
        for (int i = 0; i < documents.length; i++) {
            System.out.println(documents[i].getTitre() + " : " + documents[i].dureePret() + " jours");
        }
    }
}
