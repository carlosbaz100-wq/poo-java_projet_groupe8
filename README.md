# Application de gestion de bibliotheque

Mini-projet de Programmation Oriente Objet (POO IIAA511), Institut 2iE.

| Element | Valeur |
|---|---|
| Groupe | G8 |
| Membres | DAMBA Aimee, KOUSSE Souleymane, BAZONGO Yessamba Carlos |
| Cours | POO IIAA511 |
| Encadrant | Dr Babacar LEYE |
| Annee academique | 2026 - 2027 |
| Version livree | jalon 4 et version 1.0 |

## 1. Sujet et perimetre

Le programme gere le catalogue et les prets d'une bibliotheque. Il permet d'enregistrer
des documents, d'inscrire des adherents, d'emprunter un document disponible, de le
retourner et de consulter l'historique des operations.

Fonctionnalites couvertes :

- catalogue de documents avec ajout, recherche par numero, recherche par titre, retrait,
  tri par titre et regroupement par auteur ;
- deux types de documents, le livre et le periodique, et une consultation sur place
  reservee aux periodiques ;
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

Resultat attendu : `BUILD SUCCESS` et 21 tests sans echec.

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

La demonstration enchaine neuf etapes : affichage du catalogue, emprunt accepte,
second emprunt refuse, lecteur non inscrit, document absent, retour, retour impossible,
historique des operations reussies, puis etat final du catalogue et tri par titre.

## 5. Architecture

Le projet separe trois responsabilites : les entites gardent leur etat, le catalogue
stocke et retrouve les documents, le service orchestre les prets.

| Classe | Role | Ne fait jamais |
|---|---|---|
| `Document` | etat du document (disponible, emprunteur), invariants, transitions d'etat | afficher un message, connaitre le catalogue |
| `Livre` | document avec nombre de pages et co-auteurs | gerer un pret |
| `Periodique` | document avec numero de parution et consultation sur place | gerer un pret |
| `Consultable` | contrat de la consultation sur place | - |
| `Empruntable` | contrat du pret d'un document | - |
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

**Document abstraite.** Chaque sous-classe impose sa duree de pret : 21 jours pour un
livre, 7 jours pour un periodique. Ajouter un type de document oblige le compilateur a
verifier l'implementation de `dureePret()`.

**Interfaces separees.** `Consultable` et `Empruntable` isolent deux comportements
differents. Un periodique est consultable sur place et empruntable, un livre est
seulement empruntable, ce qui evite une hierarchie rigide.

**Collections adaptees au besoin.** `HashMap` pour retrouver un document par numero en
temps constant, `HashSet` pour garantir l'unicite des adherents, `ArrayList` pour
conserver l'ordre de l'historique, `List.copyOf` et `Set.copyOf` pour renvoyer des
copies non modifiables.

**Service separe du modele.** `ServicePret` est le seul endroit qui combine catalogue,
adherents et historique. `Bibliotheque` ne connait plus la notion de pret, ce qui
supprime le doublon de logique qui existait aux jalons precedents.

## 7. Robustesse et atomicite

Chaque operation suit le meme ordre, et rien n'est modifie avant la derniere
verification :

| Etape | Question | Si l'etape echoue |
|---|---|---|
| 1. Arguments | le lecteur est-il non nul, le numero est-il valide | `IllegalArgumentException` |
| 2. Existence | le document est-il dans le catalogue | `DocumentIntrouvableException` |
| 3. Regle metier | le lecteur est-il inscrit, le document est-il libre | exception metier |
| 4. Mutation | changement de la disponibilite et de l'emprunteur | aucune |
| 5. Historique | ajout de la ligne d'historique | aucune |

Consequence : un refus ne laisse jamais d'etat partiel. Les tests de refus verifient
l'exception et l'etat observe juste apres l'appel.

## 8. Tests et recette

La suite compte 21 tests repartis en six classes :

| Classe de test | Nombre | Objet |
|---|---|---|
| `DocumentTest` | 4 | transitions d'etat, dont deux refus |
| `LecteurTest` | 3 | invariants et egalite par numero |
| `PeriodiqueTest` | 2 | consultation sur place, deux refus |
| `CatalogueTest` | 4 | ajout, doublon, retrait, recherche |
| `ServicePretEmpruntTest` | 5 | emprunt, dont trois refus |
| `ServicePretRetourTest` | 3 | retour, dont refus d'un retour impossible |

Les 16 tests demandes par la fiche TD 4 sont couverts, dont 6 refus distincts. Le
tableau de recette complet, avec l'etat observable apres chaque appel, se trouve dans
`docs/recette.md`.

## 9. Contributions

| Membre | Perimetre | Fichiers |
|---|---|---|
| KOUSSE Souleymane | modele, exceptions, atomicite | `Document`, `ServicePret`, les quatre exceptions, `CatalogueDocuments` |
| DAMBA Aimee | collections, interfaces, tests | `Lecteur`, `Periodique`, les six classes de test, `docs/recette.md` |
| BAZONGO Yessamba Carlos | build, demonstration, livrables | `pom.xml`, `Main`, `Livre`, `Bibliotheque`, `README`, diagrammes, rapport, presentation |

Chaque jalon a fait l'objet d'un tag : `jalon-1`, `jalon-2`, `jalon-3`, puis `jalon-4`
et `version-1.0` pour la version finale. L'historique des commits indique l'auteur de
chaque fichier.

## 10. Limites connues

- La suite ne couvre pas le cas d'un periodique en consultation sur place que l'on
  tente d'emprunter en meme temps.
- Les donnees sont en memoire : tout est perdu a la fermeture du programme.
- Aucune notion d'amende ni de duree de pret depassee n'est implementee.

## 11. Sources

- CM 1 a CM 5, POO IIAA511, Dr Babacar LEYE.
- Fiche TD 4, tests unitaires et matrice de tests.
- Documentation JUnit 5 et Maven.
- Claude (Anthropic) : relecture du code, du README et aide a la redaction.
