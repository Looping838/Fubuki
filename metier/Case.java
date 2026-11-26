package metier;

// Classe publique Case : représente une cellule individuelle de la grille de jeu
public class Case
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    private int     x;             // Indice de ligne de la case dans la grille
    private int     y;             // Indice de colonne de la case dans la grille
    private int     nombre;        // Chiffre placé dans la case (0 si la case est vide)
    private boolean estModifiable; // Indique si le joueur peut modifier ou effacer cette case

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    // Constructeur qui initialise une case vide et modifiable aux coordonnées données
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

    // Getter qui retourne l'indice de ligne de la case
    public int get_x()      { return this.x;      }

    // Getter qui retourne l'indice de colonne de la case
    public int get_y()      { return this.y;      }

    // Getter qui retourne le chiffre actuellement stocké dans la case
    public int get_nombre() { return this.nombre; }

    // Setter qui modifie le chiffre contenu dans la case
    public void set_nombre(int nombre)      { this.nombre        = nombre; }

    // Setter qui verrouille ou déverrouille la modification de la case
    public void setModifiable(boolean bool) { this.estModifiable = bool;   }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    // Méthode utilitaire qui indique si la case est modifiable par le joueur
    public boolean estModifiable() { return this.estModifiable; }

    // Méthode utilitaire qui vérifie si la case ne contient aucun chiffre
    public boolean estVide()       { return this.nombre == 0;   }
}