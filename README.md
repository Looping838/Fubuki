# Fubuki

Fubuki est un jeu de réflexion développé en Java Swing, inspiré des grilles logiques de nombres. L'objectif est de remplir les cases vides d'une grille de 3x3 avec les chiffres de 1 à 9 sans doublon, de manière à ce que la somme de chaque ligne et de chaque colonne corresponde exactement aux totaux indiqués.

Le projet suit une architecture de type MVC (Modèle-Vue-Contrôleur) :

  - Metier : contient les règles du jeu, les cases et la gestion du plateau.

  - Controleur : fait le lien entre l'interface utilisateur et le moteur métier.

  - IHM : gère l'interface graphique conçue avec Java Swing.

## Règles du jeu

  Une grille de 3x3 est générée aléatoirement, accompagnée des sommes cibles pour chaque ligne et chaque colonne.

  Quelques cases sont pré-remplies et verrouillées pour servir d'indices.

  Le joueur sélectionne un chiffre disponible et le place dans une case vide.

  La partie est gagnée lorsque toutes les cases sont renseignées, chaque chiffre de 1 à 9 est utilisé une seule fois, et l'ensemble des totaux de lignes et colonnes est respecté.


## Fonctionnalités

  - Interface graphique fluide sous Java Swing (menu d'accueil et plateau de jeu).

  - Génération aléatoire d'une grille valide via mélange de Fisher-Yates.

  - Calcul automatique des totaux cibles par ligne et colonne.

  - Verrouillage des indices et sélection interactive des chiffres sur la grille.

  - Contrôle de validité des sommes et détection de victoire.

## Prérequis

  - Java JDK 8 ou supérieur.

  - Un terminal ou un IDE (VS Code, IntelliJ, Eclipse) configuré pour Java.

## Lancement du jeu

Depuis la racine du projet dans un terminal :
Bash

## Compilation de l'ensemble du projet
javac @compile.list

## Exécution de l'application
java controleur.Controleur

## Rôle des classes principales

  - controleur.Controleur : point d'entrée du programme (main), initialise le métier et l'interface.

  - metier.Case : modélise une case (coordonnées, valeur, état modifiable ou verrouillé).

  - metier.Plateau : stocke la matrice des cases et les totaux cibles.

  - metier.Fubuki : moteur de règles (génération aléatoire, masquage des cases, vérification de victoire).

  - ihm.FrameJeu : fenêtre principale de l'application gérant la navigation entre panneaux.

  - ihm.PanelAccueil : écran de bienvenue et lancement de partie.

  - ihm.PanelJeu : affichage de la grille de jeu, des totaux et saisie du joueur.
