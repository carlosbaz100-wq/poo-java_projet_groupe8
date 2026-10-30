public class Main {

    public static void main(String[] args) {

        // La creation des documents
        Document d1 = new Document(1, "Une si longue lettre", "Mariama Ba");
        Document d2 = new Document(2, "Introduction a Java", "Kathy Sierra");

        // --- Creation des lecteurs ---
        Lecteur l1 = new Lecteur("KOUSSE", "Souleymane", 1001, "Ouagadougou");
        Lecteur l2 = new Lecteur("BAZONGO", "Carlos", 1002, "Ouagadougou");

        // Affichage du debut
        System.out.println("LES DOCUMENTS");
        System.out.println(d1);
        System.out.println(d2);

       
        System.out.println("LES LECTEURS CREES");
        System.out.println(l1);
        System.out.println(l2);

        // Emprunt 
        System.out.println("EMPRUMT");
        System.out.println("Disponibilite avant l'emprunt : " + d1.estDisponible());
        d1.emprunter(l1);
        System.out.println("Disponibilite apres l'emprunt : " + d1.estDisponible());
        System.out.println(d1);

        // Tentative d'un autre emprunt

        System.out.println("=== Tentative de double emprunt ===");
        d1.emprunter(l2);

        // Retour du document
        System.out.println("RETOUR DU DOCUMENT");
        d1.retourner();
        System.out.println("Disponibilite apres retour : " + d1.estDisponible());
        System.out.println(d1);

        // Tentative de retour sans emprunt 
        System.out.println();
        System.out.println("=== Tentative de retour sans emprunt ===");
        d1.retourner();
    }
}
