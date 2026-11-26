package metier;

import java.util.Random;

// Classe publique Plateau : modélise la grille 3x3 de Fubuki, ses cases et ses totaux cibles
public class Plateau
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    private int      nbLigne;                                       // Nb de lignes de la grille (3)
    private int      nbColonne;                                     // Nb de colonnes de la grille (3)
    private Case[][] plateau;                                       // Matrice des cases composant la grille
    private int[]    totauxLignes;                                  // Tab. des sommes cibles de chaque ligne
    private int[]    totauxColonnes;                                // Tab. des sommes cibles de chaque colonne
    private int[]    nbrPossible = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };   // Ensemble des 9 chiffres à répartir
    private Random   rand        = new Random();                    // Générateur aléatoire pour le mélange et la difficulté

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    // Constructeur qui instancie une grille 3x3 de cases vides et ses tableaux de totaux
    public Plateau()
    {
        this.nbLigne   = 3;
        this.nbColonne = 3;
        this.plateau   = new Case[this.nbLigne][this.nbColonne];

        // Initialise chaque cellule de la matrice avec ses coordonnées
        for (int lig = 0; lig < this.nbLigne; lig++)
            for (int col = 0; col < this.nbColonne; col++)
                this.plateau[lig][col] = new Case(lig, col);

        this.totauxLignes   = new int[this.nbLigne];
        this.totauxColonnes = new int[this.nbColonne];
    }

    /*-------------------------------*/
    /*      Getters & Setters        */
    /*-------------------------------*/

    // Getter qui retourne le nombre de lignes du plateau
    public int  getNbLigne()                    { return this.nbLigne;                    }

    // Getter qui retourne le nombre de colonnes du plateau
    public int  getNbColonne()                  { return this.nbColonne;                  }

    // Getter qui retourne l'objet Case aux coordonnées (x, y)
    public Case getCasePlateau(int x, int y)    { return this.plateau[x][y];              }

    // Getter qui retourne le chiffre contenu dans la case (x, y)
    public int  getNombreCase(int x, int y)     { return this.plateau[x][y].get_nombre(); }

    // Getter qui retourne la somme cible de la ligne x
    public int  getTotauxLignes(int x)          { return this.totauxLignes[x];            }

    // Getter qui retourne la somme cible de la colonne y
    public int  getTotauxColonnes(int y)        { return this.totauxColonnes[y];          }

    // Setter qui définit la somme cible de la ligne x
    public void setTotauxLignes(int x, int nbr)   { this.totauxLignes[x]   = nbr; }

    // Setter qui définit la somme cible de la colonne y
    public void setTotauxColonnes(int y, int nbr) { this.totauxColonnes[y] = nbr; }

    // Setter qui affecte un chiffre à la case (x, y) uniquement si elle est modifiable
    public void setCase(int x, int y, int nbr)
    {
        if (!this.plateau[x][y].estModifiable()) return;
        this.plateau[x][y].set_nombre(nbr);
    }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    // Méthode utilitaire qui remplit aléatoirement la grille avec les chiffres 1 à 9 et calcule les totaux
    public void initPlateau()
    {
        this.melangerNombres();

        // Répartit les 9 chiffres mélangés dans l'ensemble des cases de la grille
        int index = 0;
        for (int lig = 0; lig < this.nbLigne; lig++)
        {
            for (int col = 0; col < this.nbColonne; col++)
            {
                this.plateau[lig][col].set_nombre(this.nbrPossible[index]);
                index++;
            }
        }

        // Calcule simultanément les totaux cibles de chaque ligne et de chaque colonne
        for (int i = 0; i < this.nbLigne; i++)
        {
            int sommeLigne   = 0;
            int sommeColonne = 0;

            for (int j = 0; j < this.nbColonne; j++)
            {
                sommeLigne   += this.plateau[i][j].get_nombre();
                sommeColonne += this.plateau[j][i].get_nombre();
            }

            this.setTotauxLignes(i, sommeLigne);
            this.setTotauxColonnes(i, sommeColonne);
        }
    }

    // Méthode utilitaire qui verrouille un nombre de cases initiales selon la difficulté choisie
    public void plateauJeu(int difficulte)
    {
        int nbCasesRevelees = (difficulte == 0) ? 2 : (difficulte == 1 ? 1 : 0);
        int cpt = 0;

        // Sélectionne aléatoirement des cases distinctes qui resteront révélées au joueur
        while (cpt < nbCasesRevelees)
        {
            int x = this.rand.nextInt(this.nbLigne);
            int y = this.rand.nextInt(this.nbColonne);

            if (this.plateau[x][y].estModifiable())
            {
                this.plateau[x][y].setModifiable(false);
                cpt++;
            }
        }
    }

    // Méthode utilitaire qui remet à zéro toutes les cases non verrouillées avant le début de la partie
    public void plateauVide()
    {
        // Parcourt la grille et masque la valeur des cases modifiables
        for (int lig = 0; lig < this.nbLigne; lig++)
            for (int col = 0; col < this.nbColonne; col++)
                if (this.plateau[lig][col].estModifiable())
                    this.plateau[lig][col].set_nombre(0);
    }

    // Méthode utilitaire qui vérifie si un chiffre donné apparaît déjà dans la grille
    public boolean estPresent(int nbr)
    {
        // Parcourt toutes les cases à la recherche d'une occurrence du chiffre
        for (int lig = 0; lig < this.nbLigne; lig++)
            for (int col = 0; col < this.nbColonne; col++)
                if (this.plateau[lig][col].get_nombre() == nbr)
                    return true;

        return false;
    }

    // Méthode utilitaire qui contrôle si toutes les cases sont remplies et si tous les totaux sont exacts
    public boolean estGagne()
    {
        // Vérifie qu'aucune case n'est vide et que la somme de chaque ligne correspond au total cible
        for (int lig = 0; lig < this.nbLigne; lig++)
        {
            int somme = 0;
            for (int col = 0; col < this.nbColonne; col++)
            {
                if (this.plateau[lig][col].estVide()) return false;
                somme += this.plateau[lig][col].get_nombre();
            }
            if (somme != this.totauxLignes[lig]) return false;
        }

        // Vérifie que la somme de chaque colonne correspond au total cible
        for (int col = 0; col < this.nbColonne; col++)
        {
            int somme = 0;
            for (int lig = 0; lig < this.nbLigne; lig++)
                somme += this.plateau[lig][col].get_nombre();

            if (somme != this.totauxColonnes[col]) return false;
        }

        return true;
    }

    // Méthode utilitaire qui échange les chiffres de deux cases si elles sont toutes deux modifiables
    public void echangerNbr(int lig1, int col1, int lig2, int col2)
    {
        Case case1 = this.plateau[lig1][col1];
        Case case2 = this.plateau[lig2][col2];

        if (!case1.estModifiable() || !case2.estModifiable()) return;

        int memoire = case1.get_nombre();
        case1.set_nombre(case2.get_nombre());
        case2.set_nombre(memoire);
    }

    // Méthode utilitaire qui mélange aléatoirement le tableau des 9 chiffres (algorithme de Fisher-Yates)
    private void melangerNombres()
    {
        // Parcourt le tableau à rebours en permutant chaque élément avec un indice aléatoire précédent
        for (int i = this.nbrPossible.length - 1; i > 0; i--)
        {
            int j    = this.rand.nextInt(i + 1);
            int temp = this.nbrPossible[i];
            this.nbrPossible[i] = this.nbrPossible[j];
            this.nbrPossible[j] = temp;
        }
    }
}