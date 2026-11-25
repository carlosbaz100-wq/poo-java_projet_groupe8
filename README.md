# Application de gestion de bibliotheque

Mini-projet de Programmation Orientee Objet (POO IIAA511), Institut 2iE.
Application console de gestion de catalogue et de prets d'une bibliotheque,
realisee en groupe de trois sur quatre jalons.

![Java 17](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.9-orange)
![JUnit 5](https://img.shields.io/badge/JUnit-5.11-green)
![Tests](https://img.shields.io/badge/tests-44%20passing-brightgreen)

| Element | Valeur |
|---|---|
| Groupe | G8 |
| Membres | DAMBA Aimee, KOUSSE Souleymane, BAZONGO Yessamba Carlos |
| Cours | POO IIAA511 |
| Encadrant | Dr Babacar LEYE |
| Annee academique | 2026 - 2027 |
| Version livree | version 1.0 |
| Depot | https://github.com/carlosbaz100-wq/poo-java_projet_groupe8 |
| Tag final | version-1.0 (commit de cloture) |

## 1. Sujet et perimetre

Le programme gere le catalogue et les prets d'une bibliotheque. Il permet d'enregistrer
des documents, d'inscrire des adherents, d'emprunter un document disponible, de le
retourner et de consulter l'historique des operations.

Fonctionnalites couvertes :

- catalogue de documents avec ajout, recherche par numero, recherche par titre, retrait,
  tri par titre et regroupement par auteur ;
- deux types de documents, le livre et le periodique ; un periodique peut etre
  consulte sur place ou emprunte, mais jamais les deux a la fois ;
- adherents identifies par un numero unique, avec recherche et affichage ;
- emprunt et retour orchestres par un service dedie, avec historique des operations ;
- refus explicites par exceptions pour tous les cas invalides.

Hors perimetre : les amendes, les reservations, la persistance en base de donnees et
l'interface graphique. Le programme est une application console.

## 2. Prerequis

- JDK 17 ou superieur. Verification : `java -version` doit afficher 17 ou plus.
- Maven 3.9 ou superieur. Verification : `mvn -version`.
- Aucune bibliotheque supplementaire n'est a installer, Maven telecharge JUnit 5.

## 3. Compilation et tests

Depuis la racine du depot :

```
mvn clean test
```

Resultat attendu : `BUILD SUCCESS` et 44 tests sans echec.

Les rapports detailles sont ecrits dans `target/surefire-reports/`.

Compilation seule, sans executer les tests :

```
mvn clean compile
```

## 4. Execution de la demonstration

```
mvn clean compile
java -cp target/classes Main
```

La demonstration enchaine dix etapes : affichage du catalogue, emprunt accepte,
second emprunt refuse, lecteur non inscrit, document absent, retour, retour impossible,
historique des operations reussies, etat final du catalogue, consultation d'un periodique
avec refus d'emprunt pendant la consultation, puis tri par titre.

## 5. Architecture

Le projet separe trois responsabilites : les entites gardent leur etat, le catalogue
stocke et retrouve les documents, le service orchestre les prets.

| Classe | Role | Ne fait jamais |
|---|---|---|
| `Document` | etat du document (disponible, emprunteur), invariants, transitions d'etat | afficher un message, connaitre le catalogue |
| `Livre` | document avec nombre de pages et co-auteurs | decider des regles de pret |
| `Periodique` | document avec numero de parution, consultation sur place et pret | decider des regles de pret |
| `Consultable` | contrat de la consultation sur place (consulter, terminer, etat) | - |
| `Empruntable` | contrat du pret d'un document (emprunter, retourner, etat) | - |
| `Lecteur` | identite de l'adherent, egalite par numero | connaitre ses emprunts |
| `CatalogueDocuments` | contrat du catalogue | decider si un pret est autorise |
| `Bibliotheque` | catalogue concret, adherents, recherches, tris | enregistrer un pret |
| `ServicePret` | orchestration emprunt et retour, historique, atomicite | modifier un champ d'un document |
| `Main` | demonstration console | porter des regles metier |

Les exceptions metier sont `DocumentIntrouvableException`,
`DocumentIndisponibleException`, `RetourImpossibleException` et
`AdherentNonInscritException`. Elles heritent de `RuntimeException` pour ne pas
alourdir les signatures ; les arguments invalides levent `IllegalArgumentException`.

## 6. Choix de conception

**Classe Document abstraite.** Chaque sous-classe impose sa duree de pret : 21 jours pour un
livre, 7 jours pour un periodique. Ajouter un type de document oblige le compilateur a
verifier l'implementation de `dureePret()`.

**Interfaces separees.** `Consultable` et `Empruntable` isolent deux comportements
differents. Un periodique est consultable sur place et empruntable, mais jamais les
deux a la fois ; un livre est seulement empruntable. Cette separation evite une
hierarchie rigide et rend explicites les contrats de chaque usage.

**Collections adaptees au besoin.** `HashMap` pour retrouver un document par numero en
temps constant, `HashSet` pour garantir l'unicite des adherents, `ArrayList` pour
conserver l'ordre de l'historique. `List.copyOf` protege l'historique, et
`Collections.unmodifiableSet` renvoie une vue non modifiable mais vivante des
adherents, afin que le service voie les inscriptions posterieures a sa creation.

**Service separe du modele.** `ServicePret` est le seul endroit qui combine catalogue,
adherents et historique. `Bibliotheque` ne connait plus la notion de pret, ce qui
supprime le doublon de logique qui existait aux jalons precedents.

## 7. Robustesse et atomicite

Chaque operation suit le meme ordre, et rien n'est modifie avant la derniere
verification :

| Etape | Question | Si l'etape echoue |
|---|---|---|
| 1. Arguments | le lecteur est-il non nul, le numero est-il valide ? | `IllegalArgumentException` |
| 2. Existence | le document est-il dans le catalogue | `DocumentIntrouvableException` |
| 3. Regle metier | le lecteur est-il inscrit, le document est-il libre | exception metier |
| 4. Mutation | changement de la disponibilite et de l'emprunteur | aucune |
| 5. Historique | ajout de la ligne d'historique | aucune |

Consequence : un refus ne laisse jamais d'etat partiel. Les tests de refus verifient
l'exception et l'etat observe juste apres l'appel.

## 8. Tests et validation

La suite compte 44 tests repartis en sept classes :

| Classe de test | Nombre | Objet |
|---|---|---|
| `DocumentTest` | 4 | transitions d'etat, dont deux refus |
| `LivreTest` | 6 | invariants, co-auteurs et encapsulation, dont quatre refus |
| `LecteurTest` | 4 | invariants et egalite par numero, dont trois refus |
| `PeriodiqueTest` | 5 | consultation sur place et exclusion avec le pret, dont quatre refus |
| `CatalogueTest` | 11 | ajout, doublon, retrait, recherche, tri et regroupement, dont quatre refus |
| `ServicePretEmpruntTest` | 8 | emprunt, dont six refus |
| `ServicePretRetourTest` | 6 | retour, dont cinq refus |

Les 16 tests demandes par la fiche TD 4 sont couverts. La suite complete compte
44 tests, dont 16 succes et 28 refus. Les tests se trouvent dans `src/test/java` ;
le rapport final presente la synthese de la recette, avec le resultat attendu et
l'etat observe apres chaque appel.

## 9. Contributions

| Membre | Perimetre | Fichiers | Lignes |
|---|---|---|---|
| KOUSSE Souleymane | modele, exceptions, atomicite | `Document`, `Bibliotheque`, `ServicePret`, `Empruntable`, les quatre exceptions | 373 |
| DAMBA Aimee | collections, interfaces, tests | `Lecteur`, `Periodique`, les sept classes de test | 758 |
| BAZONGO Yessamba Carlos | build, demonstration, livrables | `pom.xml`, `Main`, `Livre`, `CatalogueDocuments`, `Consultable`, `README`, diagramme, rapport, presentation | 217 |

Total : 1348 lignes Java suivies par Git (etat final). La matrice PME distingue les
taches historiques et la tache d'harmonisation finale ; les lignes ci-dessus sont
comptees par fichier au dernier commit, pas par auteur de chaque ligne.

Les tags `jalon-1`, `jalon-2` et `jalon-3` marquent les etapes intermediaires ; le tag
`version-1.0` marque la version finale. L'historique des commits indique l'auteur de
chaque fichier.

## 10. Limites connues

- La consultation sur place n'a pas de duree maximale ni de reservation.
- Les donnees sont en memoire : tout est perdu a la fermeture du programme.
- Aucune notion d'amende ni de duree de pret depassee n'est implementee.


## 11. Sources

- CM 1 a CM 5, POO IIAA511, Dr Babacar LEYE.
- Fiche TD 4, tests unitaires et matrice de tests.
- Documentation JUnit 5 et Maven.
- Claude (Anthropic) : relecture du code, du README et aide a la redaction.
- Capsule video de demonstration (8 minutes maximum), a remettre au delegue du groupe,
  qui la transmet a Babacar LEYE via WeTransfer.
