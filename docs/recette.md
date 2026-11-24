# Tableau de recette : validation des tests - Jalon 4 (Groupe 8, POO IIAA511)

Ce document décrit les scénarios métier vérifiés par la suite de tests et donne, pour
chacun, le résultat attendu et l'état observable après l'appel. Il sert de preuve pour
la recette du jalon 4 et pour le rapport final.

## Scénarios métier

| Scénario | Résultat attendu | État observable après l'appel |
|---|---|---|
| Succès : emprunt | emprunt accepté | indisponible, emprunteur = Alice, historique +1 |
| Succès : retour | retour accepté | disponible, emprunteur = null, historique +1 |
| Argument invalide (numéro <= 0 ou lecteur null) | `IllegalArgumentException` | document et historique inchangés |
| Objet absent | `DocumentIntrouvableException` | catalogue et historique inchangés |
| Lecteur non inscrit | `AdherentNonInscritException` | document disponible, emprunteur = null, historique inchangé |
| Règle métier violée (déjà prêté) | `DocumentIndisponibleException` | même emprunteur, toujours indisponible, historique inchangé |
| Retour sans emprunt | `RetourImpossibleException` | toujours disponible, historique inchangé |
| Retour par un autre lecteur | `RetourImpossibleException` | emprunteur initial conservé, historique inchangé |
| Document non empruntable | `DocumentIndisponibleException` | document disponible, aucun emprunteur, historique inchangé |
| Retrait d'un document emprunté | `DocumentIndisponibleException` | document toujours dans le catalogue |
| Succès : consultation sur place | consultation acceptée | en consultation, consultant = Alice |
| Double consultation | `DocumentIndisponibleException` | consultant initial conservé |
| Consultation d'un document emprunté | `DocumentIndisponibleException` | aucune consultation, emprunteur conservé |
| Emprunt d'un document en consultation | `DocumentIndisponibleException` | consultation conservée, aucun emprunteur |
| Retrait d'un périodique en consultation | `DocumentIndisponibleException` | document toujours dans le catalogue |
| Adhérent inscrit après la création du service | emprunt accepté | le service voit la nouvelle inscription |

## Correspondance avec les tests

### Tests 1 à 16

| # | Test | Fichier | Type | Vérifié |
|---|---|---|---|---|
| 1 | nouveauDocumentEstDisponible | DocumentTest | succès | disponible = true, emprunteur = null |
| 2 | emprunterRendLeDocumentIndisponible | DocumentTest | succès | disponible = false, emprunteur = Alice |
| 3 | emprunterDeuxFoisRefuseEtGardeLEmprunteur | DocumentTest | **refus** | exception + emprunteur inchangé |
| 4 | retournerSansEmpruntRefuseEtGardeLEtat | DocumentTest | **refus** | exception + disponible = true |
| 5 | empruntReussiEcritDansLHistorique | ServicePretEmpruntTest | succès | historique = 1 ligne |
| 6 | empruntDocumentAbsentLeveException | ServicePretEmpruntTest | **refus** | exception + catalogue inchangé |
| 7 | empruntDejaPreteLeveExceptionEtatInchange | ServicePretEmpruntTest | **refus** | exception + même emprunteur + historique inchangé |
| 8 | empruntLecteurNonInscritLeveExceptionEtatInchange | ServicePretEmpruntTest | **refus** | exception + disponible = true |
| 9 | empruntArgumentInvalideLeveIllegalArgument | ServicePretEmpruntTest | **refus** | exception + état inchangé |
| 10 | retourReussiRendDisponible | ServicePretRetourTest | succès | disponible = true, emprunteur = null |
| 11 | retourDocumentAbsentLeveException | ServicePretRetourTest | **refus** | exception + catalogue inchangé |
| 12 | retourSansEmpruntLeveExceptionEtatInchange | ServicePretRetourTest | **refus** | exception + disponible = true |
| 13 | ajoutPuisRechercheParNumero | CatalogueTest | succès | le document retrouvé est le même objet |
| 14 | ajoutDoublonRefuse | CatalogueTest | **refus** | taille inchangée |
| 15 | retraitDocumentExistant | CatalogueTest | succès | document retiré, taille - 1 |
| 16 | retraitDocumentAbsentRefuse | CatalogueTest | **refus** | catalogue inchangé |

### Tests complémentaires (17 à 44)

| # | Test | Fichier | Type | Vérifié |
|---|---|---|---|---|
| 17 | nombrePagesNegatifOuNulRefuse | LivreTest | **refus** | `IllegalArgumentException` |
| 18 | ajouterAuteurNullOuVideRefuse | LivreTest | **refus** | exception + nombre d'auteurs inchangé |
| 19 | ajouterAuteurDoublonRefuse | LivreTest | **refus** | exception + doublon et auteur principal refusés |
| 20 | capaciteAuteursAtteinteRefuseEtGardeLEtat | LivreTest | **refus** | exception + trois co-auteurs conservés |
| 21 | getAuteursRenvoieUneCopie | LivreTest | succès | modifier le tableau renvoyé ne change pas le livre |
| 22 | getAuteursNeContientQueLesAuteursAjoutes | LivreTest | succès | tableau de taille exacte, sans case nulle |
| 23 | nomVideRefuse | LecteurTest | **refus** | `IllegalArgumentException` |
| 24 | prenomVideRefuse | LecteurTest | **refus** | `IllegalArgumentException` |
| 25 | numeroNegatifOuNulRefuse | LecteurTest | **refus** | `IllegalArgumentException` |
| 26 | deuxLecteursDeMemeNumeroSontLeMemeAdherent | LecteurTest | succès | `equals`, `hashCode` et unicité dans un `Set` |
| 27 | consulterDocumentEmprunteRefuseEtGardeLEtat | PeriodiqueTest | **refus** | exception + aucune consultation |
| 28 | terminerConsultationNonCommenceeRefuseEtGardeLEtat | PeriodiqueTest | **refus** | exception + état inchangé |
| 29 | consulterReussiPuisTerminer | PeriodiqueTest | succès | consultant = Alice, puis état remis à zéro |
| 30 | consulterPuisEmprunterRefuseEtGardeLEtat | PeriodiqueTest | **refus** | exception + consultation et consultant conservés |
| 31 | doubleConsultationRefuseEtGardeLEtat | PeriodiqueTest | **refus** | exception + consultant initial conservé |
| 32 | retraitDocumentEmprunteRefuseEtGardeLEtat | CatalogueTest | **refus** | exception + document toujours présent |
| 33 | retraitPeriodiqueEnConsultationRefuseEtGardeLEtat | CatalogueTest | **refus** | exception + document toujours présent |
| 34 | rechercheParTitreIgnoreLaCasse | CatalogueTest | succès | recherche insensible à la casse |
| 35 | triParTitre | CatalogueTest | succès | ordre alphabétique par titre |
| 36 | regroupementParAuteur | CatalogueTest | succès | deux groupes, documents attendus |
| 37 | getTousLesDocumentsRenvoieUneCopie | CatalogueTest | succès | vider la liste renvoyée ne change pas le catalogue |
| 38 | getAdherentsEstUneVueNonModifiableEtVivante | CatalogueTest | succès | ajout refusé, inscription ultérieure visible |
| 39 | empruntNumeroInvalideRefuse | ServicePretEmpruntTest | **refus** | exception + état inchangé |
| 40 | adherentInscritApresCreationDuServiceEstReconnu | ServicePretEmpruntTest | succès | le service voit l'inscription tardive |
| 41 | empruntDocumentNonEmpruntableRefuse | ServicePretEmpruntTest | **refus** | exception + document et historique inchangés |
| 42 | retourParMauvaisLecteurRefuseEtGardeLEmprunteur | ServicePretRetourTest | **refus** | exception + emprunteur initial conservé |
| 43 | retourDeuxFoisRefuseEtGardeLEtat | ServicePretRetourTest | **refus** | exception + état libre conservé |
| 44 | retourArgumentInvalideRefuse | ServicePretRetourTest | **refus** | exception + état et historique inchangés |

## Bilan

La suite compte 44 tests : 16 succès et 28 refus. Les 16 tests demandés par la fiche
TD 4 sont couverts, et les tests complémentaires renforcent les cas limites, les
constructeurs, l'encapsulation, les collections et l'exclusion entre consultation et
prêt.

## Limites connues

- La consultation sur place n'a pas de durée maximale ni de réservation.
- Les données sont en mémoire : tout est perdu à la fermeture du programme.
- Aucune notion d'amende ni de durée de prêt dépassée n'est implémentée.
