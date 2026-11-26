package controleur;

import ihm.FrameJeu;
import java.awt.Toolkit;
import metier.Case;
import metier.Fubuki;
import metier.Plateau;

// Classe publique Controleur : fait le lien entre l'interface graphique (IHM) et le moteur métier
public class Controleur
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    private FrameJeu frameJeu; // Réf. vers la fenêtre principale de l'application
    private Fubuki   metier;   // Réf. vers le moteur logique gérant la partie en cours
    private Plateau  plateau;  // Réf. vers la grille de jeu active

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    // Constructeur qui lance la fenêtre principale en plein écran
    public Controleur()
    {
        this.frameJeu = new FrameJeu(this);
        this.frameJeu.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    }

    /*-------------------------------*/
    /*            Getters            */
    /*-------------------------------*/

    // Getter qui retourne l'objet Case situé aux coordonnées demandées
    public Case getCasePlateau(int lig, int col)       { return this.plateau.getCasePlateau(lig, col); }

    // Getter qui retourne le chiffre contenu dans la case demandée (0 si vide)
    public int  getNombreCasePlateau(int lig, int col) { return this.plateau.getNombreCase(lig, col);  }

    // Getter qui retourne le niveau de difficulté de la partie en cours (0, 1 ou 2)
    public int  getDifficulte()                        { return this.metier.getDifficulte();           }

    // Getter qui retourne le nombre de lignes du plateau
    public int  getNbLigne()                           { return this.plateau.getNbLigne();             }

    // Getter qui retourne le nombre de colonnes du plateau
    public int  getNbColonne()                         { return this.plateau.getNbColonne();           }

    // Getter qui retourne la somme cible à atteindre pour la ligne indiquée
    public int  getTotauxLignes(int i)                 { return this.plateau.getTotauxLignes(i);       }

    // Getter qui retourne la somme cible à atteindre pour la colonne indiquée
    public int  getTotauxColonnes(int i)               { return this.plateau.getTotauxColonnes(i);     }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    // --- Initialisation & Réinitialisation ---

    // Méthode utilitaire qui initialise une nouvelle partie selon la difficulté choisie
    public void initPlateau(int difficulte)
    {
        this.metier  = new Fubuki(this, difficulte);
        this.plateau = this.metier.getPlateau();
    }

    // Méthode utilitaire qui régénère une nouvelle grille en conservant la difficulté actuelle
    public void resetJeu()
    {
        this.initPlateau(this.metier.getDifficulte());
    }

    // --- Vérifications ---

    // Méthode utilitaire qui vérifie si un chiffre est déjà placé sur le plateau
    public boolean estPresent(int nbr) { return this.metier.estPresent(nbr); }

    // Méthode utilitaire qui vérifie si l'ensemble de la grille respecte les totaux cibles
    public boolean estGagne()          { return this.metier.estGagne();      }

    // Méthode utilitaire qui vérifie si la somme des chiffres d'une ligne égale son total cible
    public boolean estLigneValide(int ligne)
    {
        int somme = 0;

        // Additionne les valeurs de toutes les colonnes de la ligne
        for (int col = 0; col < this.getNbColonne(); col++)
            somme += this.getNombreCasePlateau(ligne, col);

        return somme == this.getTotauxLignes(ligne);
    }

    // Méthode utilitaire qui vérifie si la somme des chiffres d'une colonne égale son total cible
    public boolean estColonneValide(int col)
    {
        int somme = 0;

        // Additionne les valeurs de toutes les lignes de la colonne
        for (int lig = 0; lig < this.getNbLigne(); lig++)
            somme += this.getNombreCasePlateau(lig, col);

        return somme == this.getTotauxColonnes(col);
    }

    // --- Actions de jeu (avec historisation) ---

    // Méthode utilitaire qui place un chiffre sur le plateau et empile l'action pour annulation
    public void setNbrCase(int lig, int col, int num)
    {
        this.plateau.setCase(lig, col, num);
        this.metier.getPile().empiler("setNbrCase " + num + " [" + lig + "," + col + "]");
    }

    // Méthode utilitaire qui efface le chiffre d'une case et empile l'action pour annulation
    public void effacerChiffre(int ligne, int colonne)
    {
        int num = this.plateau.getNombreCase(ligne, colonne);
        this.metier.effacerChiffre(ligne, colonne);
        this.metier.getPile().empiler("effacerChiffre " + num + " [" + ligne + "," + colonne + "]");
    }

    // Méthode utilitaire qui permute les chiffres de deux cases et empile l'action pour annulation
    public void echangerNbr(int lig1, int col1, int lig2, int col2)
    {
        this.metier.echangerNbr(lig1, col1, lig2, col2);
        this.metier.getPile().empiler("echangerNbr [" + lig1 + "," + col1 + "] [" + lig2 + "," + col2 + "]");
    }

    // Méthode utilitaire qui demande au moteur métier de révéler une case correcte au hasard
    public void donnerIndice()
    {
        this.metier.getIndice();
    }

    // --- Gestion de l'annulation (Pile) ---

    // Méthode utilitaire qui indique si au moins une action valide peut être annulée
    public boolean peutAnnuler() { return !this.metier.getPile().estVide(); }

    // Méthode utilitaire qui déclenche l'annulation de la dernière action valide
    public void    depilerCase() { this.metier.getPile().depiler();         }

    // Méthode utilitaire qui vide complètement l'historique d'annulation
    public void    viderPile()   { this.metier.getPile().vider();           }

    // Méthode utilitaire qui rétablit un chiffre sans l'empiler lors d'une annulation
    public void setNbrCaseAnnulation(int lig, int col, int num)
    {
        this.plateau.setCase(lig, col, num);
    }

    // Méthode utilitaire qui vide une case sans l'empiler lors d'une annulation
    public void effacerChiffreAnnulation(int ligne, int colonne)
    {
        this.metier.effacerChiffre(ligne, colonne);
    }

    // Méthode utilitaire qui permute deux cases en sens inverse sans l'empiler lors d'une annulation
    public void echangerNbrAnnulation(int lig1, int col1, int lig2, int col2)
    {
        this.metier.echangerNbr(lig1, col1, lig2, col2);
    }

    /*-------------------------------*/
    /*        Point d'entrée         */
    /*-------------------------------*/

    // Méthode principale qui démarre l'application Fubuki
    public static void main(String[] args)
    {
        new Controleur();
    }
}