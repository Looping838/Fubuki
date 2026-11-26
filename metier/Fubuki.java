package metier;

import controleur.Controleur;
import java.util.Random;

// Classe publique Fubuki : orchestre la logique d'une partie, la solution et la pile d'annulation
public class Fubuki
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    private Plateau plateau;          // Grille de jeu manipulée par le joueur
    private Plateau plateauComplet;   // Grille contenant la solution complète générée au départ
    private int     difficulte;       // Niveau de difficulté (0:Facile, 1:Moyen, 2:Difficile)
    private Pile    pile;             // Historique des actions permettant d'annuler les coups
    private Random  rand = new Random(); // Générateur aléatoire pour le tirage des indices

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    // Constructeur qui génère le plateau de jeu et initialise la pile d'historique
    public Fubuki(Controleur ctrl, int difficulte)
    {
        this.initPlateau(difficulte);
        this.pile = new Pile(ctrl, this.plateau);
    }

    /*-------------------------------*/
    /*            Getters            */
    /*-------------------------------*/

    // Getter qui retourne le plateau de jeu en cours
    public Plateau getPlateau()    { return this.plateau;    }

    // Getter qui retourne le niveau de difficulté de la partie
    public int     getDifficulte() { return this.difficulte; }

    // Getter qui retourne la pile d'actions après avoir actualisé sa référence vers le plateau
    public Pile getPile()
    {
        if (this.pile != null)
            this.pile.setPlateau(this.plateau);
        return this.pile;
    }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    // Méthode utilitaire qui génère une grille résolue, sauvegarde la solution et masque les cases jouables
    public void initPlateau(int difficulte)
    {
        this.difficulte     = difficulte;
        this.plateau        = new Plateau();
        this.plateauComplet = new Plateau();

        this.plateau.initPlateau();
        this.plateau.plateauJeu(this.difficulte);

        // Sauvegarde l'intégralité de la grille solution avant de masquer les cases modifiables
        for (int lig = 0; lig < this.plateau.getNbLigne(); lig++)
        {
            for (int col = 0; col < this.plateau.getNbColonne(); col++)
            {
                int valeur = this.plateau.getCasePlateau(lig, col).get_nombre();
                this.plateauComplet.getCasePlateau(lig, col).set_nombre(valeur);
            }
        }

        this.plateau.plateauVide();
    }

    // Méthode utilitaire qui révèle et verrouille aléatoirement une case encore modifiable
    public void getIndice()
    {
        // Vérifie qu'il reste au moins une case modifiable pour éviter une boucle infinie
        boolean existeCaseModifiable = false;
        for (int lig = 0; lig < this.plateau.getNbLigne() && !existeCaseModifiable; lig++)
            for (int col = 0; col < this.plateau.getNbColonne(); col++)
                if (this.plateau.getCasePlateau(lig, col).estModifiable())
                    existeCaseModifiable = true;

        if (!existeCaseModifiable) return;

        // Tire des coordonnées au hasard jusqu'à tomber sur une case modifiable
        while (true)
        {
            int x = this.rand.nextInt(this.plateau.getNbLigne());
            int y = this.rand.nextInt(this.plateau.getNbColonne());

            // Révèle la valeur exacte et retire tout doublon éventuel placé ailleurs par le joueur
            if (this.plateau.getCasePlateau(x, y).estModifiable())
            {
                int indice = this.plateauComplet.getCasePlateau(x, y).get_nombre();

                // Efface le chiffre de l'indice s'il avait déjà été placé sur une autre case
                for (int lig = 0; lig < this.plateau.getNbLigne(); lig++)
                    for (int col = 0; col < this.plateau.getNbColonne(); col++)
                        if (this.plateau.getCasePlateau(lig, col).get_nombre() == indice)
                            this.effacerChiffre(lig, col);

                this.plateau.getCasePlateau(x, y).set_nombre(indice);
                this.plateau.getCasePlateau(x, y).setModifiable(false);
                break;
            }
        }
    }

    // Méthode utilitaire qui vérifie si un chiffre est déjà présent sur le plateau
    public boolean estPresent(int nbr) { return this.plateau.estPresent(nbr); }

    // Méthode utilitaire qui vérifie si le joueur a résolu la grille
    public boolean estGagne()          { return this.plateau.estGagne();      }

    // Méthode utilitaire qui remet à zéro la valeur d'une case du plateau
    public void effacerChiffre(int ligne, int colonne)
    {
        this.plateau.setCase(ligne, colonne, 0);
    }

    // Méthode utilitaire qui échange le contenu de deux cases du plateau
    public void echangerNbr(int lig1, int col1, int lig2, int col2)
    {
        this.plateau.echangerNbr(lig1, col1, lig2, col2);
    }
}