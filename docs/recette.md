# Tableau de recette — Jalon 4 (Groupe 8, POO IIAA511)

## Scénarios métier

| Scénario | Résultat attendu | État observable après l'appel |
|---|---|---|
| Succès : emprunt | emprunt accepté | indisponible, emprunteur = Alice, historique +1 |
| Succès : retour | retour accepté | disponible, emprunteur = null, historique +1 |
| Argument invalide (lecteur null) | `IllegalArgumentException` | document inchangé, historique inchangé |
| Objet absent | `DocumentIntrouvableException` | catalogue inchangé, historique inchangé |
| Lecteur non inscrit | `AdherentNonInscritException` | document disponible, emprunteur = null |
| Règle métier violée (déjà prêté) | `DocumentIndisponibleException` | même emprunteur, toujours indisponible, historique inchangé |
| Retour sans emprunt | `RetourImpossibleException` | toujours disponible, historique inchangé |

## Correspondance avec les tests

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
| 15 | retraitDocumentExistant | CatalogueTest | succès | document retiré, taille − 1 |
| 16 | retraitDocumentAbsentRefuse | CatalogueTest | **refus** | catalogue inchangé |

Tests complémentaires (hors des 16) : `PeriodiqueTest` (2 refus de consultation), `LecteurTest` (3 : invariants et égalité par numéro).

## Limite connue

La suite ne couvre pas encore le cas d'un périodique en consultation sur place que l'on tente d'emprunter.
