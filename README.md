poo-java_projet_groupe8
Application de gestion de bibliothèque — Groupe 8
Cours : Programmation Orientée Objet — Java (POO IIAA511)
Institut : 2iE, Ouagadougou
Encadrant : Dr Babacar LEYE
Année académique : 2026 – 2027

Jalon 1 — Classes et encapsulation
Membres du groupe
Membre	Fichiers
DAMBA Aimée	Lecteur.java
KOUSSE Souleymane	Document.java
BAZONGO Yessamba Carlos	Main.java
Objectif du programme
Le programme enregistre des documents et des adhérents d'une bibliothèque. Il permet à un adhérent d'emprunter un document disponible et de le retourner, en mettant à jour la disponibilité à chaque opération.

Classes retenues
Entité	Justification
Document	A ses propres données (numéro, titre, auteur, disponibilité) et ses propres comportements (emprunter, retourner).
Lecteur	A ses propres données (numéro, nom, adresse) et interagit avec les documents.
Sources consultées
Cours CM1 : Objets, encapsulation et constructeurs (Dr Babacar LEYE)
Claude (Anthropic) : aide à la compréhension du travail, relecture du code et du README
Jalon 2 — Héritage et polymorphisme
Membres du groupe
Membre	Fichiers
DAMBA Aimée	Lecteur.java, Periodique.java
KOUSSE Souleymane	Document.java,diagramme de classe
BAZONGO Yessamba Carlos	Main.java , Livre.java
Ce qui change par rapport au jalon 1
Au jalon 1, Document était une classe concrète unique. À ce jalon, Document devient abstraite et impose à ses sous-classes de définir dureePret(). Livre et Periodique héritent de Document et spécialisent leur comportement. Lecteur reste inchangé.

Deux choix de conception
Document abstraite avec dureePret() abstraite

On aurait pu laisser Document concrète et écrire une méthode dureePret() avec une valeur par défaut. On a préféré la rendre abstraite : chaque sous-classe est forcée de définir sa propre durée. Si on ajoute un troisième type de document plus tard, le compilateur refusera de compiler tant que dureePret() n'est pas implémentée.

Tableau fixe String[] pour les auteurs de Livre

Une difficulté rencontrée
Dans Livre.toString(), on doit accéder à estDisponible() et getEmprunteur() qui sont définis dans Document. Ces attributs étant privés, il faut passer par les accesseurs hérités. Accéder directement à disponible provoque une erreur de compilation.

Sources consultées
Cours CM2 : Héritage, polymorphisme et interfaces (Dr Babacar LEYE)
Claude (Anthropic) : aide à la compréhension du travail, relecture du code et du README
