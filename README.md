# Fubuki (Version 2.0)

Fubuki est un jeu de réflexion développé en Java Swing, inspiré des grilles logiques de nombres. L'objectif est de remplir les cases vides d'une grille de 3x3 avec les chiffres de 1 à 9 sans doublon, de manière à ce que la somme de chaque ligne et de chaque colonne corresponde exactement aux totaux indiqués.

Le projet suit une architecture de type MVC (Modèle-Vue-Contrôleur) :

  - **Metier** : contient les règles du jeu, les cases, la gestion du plateau, la solution complète et l'historique d'annulation (`Pile`).

  - **Controleur** : fait le lien entre l'interface utilisateur et le moteur métier.

  - **IHM** : gère l'interface graphique conçue avec Java Swing.


## Règles du jeu

  - Une grille de 3x3 est générée aléatoirement, accompagnée des sommes cibles pour chaque ligne et chaque colonne.

  - Quelques cases peuvent être pré-remplies et verrouillées selon la difficulté choisie pour servir d'indices.

  - Le joueur sélectionne un chiffre disponible et le place dans une case vide (ou note ses candidats en mode brouillon).

  - La partie est gagnée lorsque toutes les cases sont renseignées, chaque chiffre de 1 à 9 est utilisé une seule fois, et l'ensemble des totaux de lignes et colonnes est respecté (dans la limite de 3 essais de validation).


## Fonctionnalités

  - Interface graphique fluide sous Java Swing (accueil, choix de difficulté, plateau de jeu, menu d'aide et écran de résultat).

  - Trois niveaux de difficulté : Facile (2 indices initiaux et 2 aides), Moyen (1 indice initial et 1 aide) et Difficile (aucun indice initial ni aide).

  - Génération aléatoire d'une grille valide via mélange de Fisher-Yates.

  - Calcul automatique des totaux cibles par ligne et colonne avec coloration de validation.

  - Verrouillage des indices, sélection interactive des chiffres et permutation rapide de deux cases placées.

  - Mode brouillon pour annoter les chiffres candidats dans les cases vides.

  - Glisser-déposer depuis le pavé numérique, effacement par clic droit ou outil gomme, et annulation des coups (`Ctrl + Z` ou bouton Annuler).

  - Contrôle de validité des sommes, chronomètre de partie et détection de victoire ou de défaite.


## Prérequis

  - **Java JDK 17 ou supérieur** (utilisation des expressions `switch` et du filtrage de motif `instanceof`).

  - Un terminal ou un IDE (VS Code, IntelliJ, Eclipse) configuré pour Java.

  - *(Optionnel)* Docker avec accès au serveur graphique X11 pour l'exécution en conteneur.


## Lancement du jeu

Depuis la racine du projet dans un terminal :

```bash
# Exécution classique de l'application
./Fubuki.sh
```

```bash
# Construction et exécution avec Docker (affichage X11)
xhost +local:docker
docker build -t fubuki . && docker run --rm -e DISPLAY=$DISPLAY -e XCURSOR_THEME=Adwaita -e XCURSOR_SIZE=24 -v /tmp/.X11-unix:/tmp/.X11-unix fubuki
```

## Documentation et architecture

Pour plus de détails sur l'architecture MVC, la pile d'annulation et le mode brouillon, consultez la [fiche de conception](CONCEPTION.md).
  
  - Diagramme de classes UML : disponible dans `images/Fubuki_UML.png`.

  - Javadoc : générable via `javadoc -d doc -encoding UTF-8 -docencoding UTF-8 -charset UTF-8 -author -version @compile.list`.

## Rôle des classes principales

  - `controleur.Controleur` : point d'entrée du programme (`main`), initialise le métier et l'interface et sert de passerelle entre les deux couches.

  - `metier.Case` : modélise une case (coordonnées, valeur, état modifiable ou verrouillé).

  - `metier.Plateau` : stocke la matrice des cases, les totaux cibles et gère le mélange, la victoire et les échanges.

  - `metier.Fubuki` : moteur de règles (génération aléatoire, sauvegarde de la solution, distribution d'indices, vérification de victoire).

  - `metier.Pile` : gère l'historique des coups (placement, effacement, échange) et l'annulation des actions valides.

  - `ihm.FrameJeu` : fenêtre principale de l'application gérant la navigation entre panneaux.

  - `ihm.PanelAccueil` : écran de bienvenue et lancement de partie.

  - `ihm.PanelChoixDiff` : sélection du niveau de difficulté (Facile, Moyen, Difficile).

  - `ihm.PanelJeu` : affichage de la grille de jeu, des totaux, du pavé numérique, des outils, du menu d'aide et saisie du joueur.

  - `ihm.PanelResultat` : écran de fin de partie affichant la victoire ou la défaite, le temps écoulé et les validations restantes.

  - `ihm.ConstantesIHM` : centralise les couleurs, bordures et marges de l'interface graphique.