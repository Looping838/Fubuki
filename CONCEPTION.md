# Fiche de conception — Fubuki (v2.0)

**Auteurs :** Alexis COFFARD (Interface Graphique & Ergonomie) — Timothé LARBI (Moteur logique & Métier)

---

## 1. Architecture générale (MVC)

L'application est structurée selon une architecture **Modèle–Vue–Contrôleur (MVC)** en trois packages afin de séparer la logique métier de l'affichage graphique :

- **`controleur` (`Controleur`) :** point d'entrée de l'application (`main`). Il instancie `FrameJeu` et `Fubuki`, expose les accesseurs et actions nécessaires à l'IHM et empile les modifications de grille dans l'historique.
- **`metier` (`Case`, `Plateau`, `Fubuki`, `Pile`) :** contient l'état du jeu, la génération des grilles, les règles de victoire, la révélation d'indices et la gestion de l'annulation, sans dépendance Swing.
- **`ihm` (`FrameJeu`, `PanelAccueil`, `PanelChoixDiff`, `PanelJeu`, `PanelResultat`, `ConstantesIHM`) :** gère les écrans, les interactions utilisateur (souris, glisser-déposer, raccourci `Ctrl + Z`, menu d'aide) et l'actualisation visuelle.

---

## 2. Choix techniques notables

### Génération de grille et niveaux de difficulté (`Plateau` & `Fubuki`)
- Une grille de $3 \times 3$ est remplie avec les chiffres de $1$ à $9$ mélangés selon l'algorithme de **Fisher-Yates**, puis les totaux de chaque ligne et de chaque colonne sont calculés.
- `Fubuki` conserve une copie complète (`plateauComplet`) afin de pouvoir révéler une valeur exacte lors de l'utilisation d'un indice tout en verrouillant la case (`estModifiable = false`) et en supprimant tout doublon posé ailleurs par le joueur.
- La difficulté ajuste le nombre de cases révélées au départ et le nombre d'indices disponibles en cours de partie :
  - **Facile (0) :** 2 cases verrouillées au départ, 2 indices disponibles.
  - **Moyen (1) :** 1 case verrouillée au départ, 1 indice disponible.
  - **Difficile (2) :** 0 case verrouillée au départ, 0 indice disponible.

### Historique et annulation des coups (`Pile`)
- Chaque action modifiable (`setNbrCase`, `effacerChiffre`, `echangerNbr`) est enregistrée sous forme de chaîne descriptive dans un tableau circulaire de capacité fixe (`TAILLE_MAX = 50`).
- Lors d'une annulation (`depiler()` / `Ctrl + Z`), la pile vérifie si l'action est toujours cohérente avec l'état courant du plateau (par exemple si la case n'a pas été verrouillée entre-temps par un indice ou si le chiffre effacé n'a pas été replacé ailleurs) avant d'exécuter l'opération inverse via le contrôleur sans ré-empiler l'annulation.

### Affichage des cases et mode brouillon (`PanelJeu`)
- Chaque cellule jouable utilise un `CardLayout` comportant deux vues : `"VALEUR"` (affichage du chiffre placé) et `"BROUILLON"` (grille $3 \times 3$ permettant d'afficher ou masquer les notes candidates de $1$ à $9$ tant que la case est vide).
- Les totaux de lignes et de colonnes ne sont colorés en vert (valide) ou rouge (invalide) qu'après un clic sur **Valider**, dans la limite de **3 validations** par partie.

### Déploiement et portabilité (`Fubuki.sh` & `Dockerfile`)
- Le script `Fubuki.sh` automatise la compilation via `@compile.list` vers `bin/` puis le lancement de `controleur.Controleur`.
- Le `Dockerfile` s'appuie sur `eclipse-temurin:21-jdk` et installe les bibliothèques X11 (`libxext6`, `libxrender1`, `libxtst6`, `libxi6`, `libxcursor1`), les polices (`fontconfig`, `fonts-liberation`, `fonts-noto-core`) et `adwaita-icon-theme` pour garantir un rendu graphique et un curseur fidèles lors du transfert X11.