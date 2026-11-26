package metier;

/**
 * Représente une cellule individuelle de la grille de jeu.
 *
 * @author Alexis COFFARD
 * @author Timothé LARBI
 * @version 2.0
 */
public class Case
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    /** Indice de ligne de la case dans la grille */
    private int     x;

    /** Indice de colonne de la case dans la grille */
    private int     y;

    /** Chiffre placé dans la case (0 si la case est vide) */
    private int     nombre;

    /** Indique si le joueur peut modifier ou effacer cette case */
    private boolean estModifiable;

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    /**
     * Constructeur qui initialise une case vide et modifiable aux coordonnées données.
     *
     * @param x Indice de ligne de la case
     * @param y Indice de colonne de la case
     */
    public Case(int x, int y)
    {
        this.x             = x;
        this.y             = y;
        this.nombre        = 0;
        this.estModifiable = true;
    }

    /*-------------------------------*/
    /*      Getters & Setters        */
    /*-------------------------------*/

    /**
     * Getter qui retourne l'indice de ligne de la case.
     *
     * @return Indice de ligne
     */
    public int get_x()      { return this.x;      }

    /**
     * Getter qui retourne l'indice de colonne de la case.
     *
     * @return Indice de colonne
     */
    public int get_y()      { return this.y;      }

    /**
     * Getter qui retourne le chiffre actuellement stocké dans la case.
     *
     * @return Chiffre contenu dans la case (0 si vide)
     */
    public int get_nombre() { return this.nombre; }

    /**
     * Setter qui modifie le chiffre contenu dans la case.
     *
     * @param nombre Nouveau chiffre à inscrire dans la case
     */
    public void set_nombre(int nombre)      { this.nombre        = nombre; }

    /**
     * Setter qui verrouille ou déverrouille la modification de la case.
     *
     * @param bool {@code true} si la case est modifiable, {@code false} si elle est verrouillée
     */
    public void setModifiable(boolean bool) { this.estModifiable = bool;   }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    /**
     * Méthode utilitaire qui indique si la case est modifiable par le joueur.
     *
     * @return {@code true} si la case peut être modifiée, {@code false} sinon
     */
    public boolean estModifiable() { return this.estModifiable; }

    /**
     * Méthode utilitaire qui vérifie si la case ne contient aucun chiffre.
     *
     * @return {@code true} si la valeur vaut 0, {@code false} sinon
     */
    public boolean estVide()       { return this.nombre == 0;   }
}