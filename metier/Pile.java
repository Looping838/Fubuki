package metier;

import controleur.Controleur;
import java.util.Arrays;

// Classe publique Pile : gère l'historique des coups joués et leur annulation (LIFO)
public class Pile
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    private static final int TAILLE_MAX = 50; // Capacité maximale de la pile d'historique

    private String[]   pile;      // Tab. circulaire mémorisant les commandes textuelles jouées
    private int        numAction; // Indice du prochain emplacement libre au sommet de la pile
    private Controleur ctrl;      // Réf. vers le contrôleur pour exécuter les actions inverses
    private Plateau    plateau;   // Réf. vers le plateau actif pour valider la cohérence d'un coup

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    // Constructeur qui initialise une pile d'historique vide liée au contrôleur et au plateau
    public Pile(Controleur ctrl, Plateau plateau)
    {
        this.pile      = new String[TAILLE_MAX];
        this.numAction = 1;
        this.ctrl      = ctrl;
        this.plateau   = plateau;
    }

    /*-------------------------------*/
    /*            Setter             */
    /*-------------------------------*/

    // Setter qui met à jour la référence vers le plateau de jeu actif
    public void setPlateau(Plateau plateau)
    {
        this.plateau = plateau;
    }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    // Méthode utilitaire qui enregistre une nouvelle action au sommet de la pile
    public void empiler(String action)
    {
        if (this.numAction == TAILLE_MAX) this.numAction = 1;
        this.pile[this.numAction] = action;
        this.numAction++;
    }

    // Méthode utilitaire qui dépile et annule la dernière action encore valide
    public void depiler()
    {
        // Remonte la pile jusqu'à trouver une action toujours applicable sur l'état actuel de la grille
        while (this.numAction > 1)
        {
            this.numAction--;
            String action = this.pile[this.numAction];
            this.pile[this.numAction] = null;

            // Ignore les actions rendues obsolètes (ex. case verrouillée entre-temps par un indice)
            if (this.estActionValide(action))
            {
                this.decomposeur(action);
                break;
            }
        }
    }

    // Méthode utilitaire qui efface tout l'historique et réinitialise le pointeur de sommet
    public void vider()
    {
        Arrays.fill(this.pile, null);
        this.numAction = 1;
    }

    // Méthode utilitaire qui vérifie s'il existe au moins une action annulable dans la pile
    public boolean estVide()
    {
        // Parcourt la pile du sommet vers la base à la recherche d'au moins une action valide
        for (int i = this.numAction - 1; i >= 1; i--)
            if (this.estActionValide(this.pile[i]))
                return false;

        return true;
    }

    // Méthode utilitaire qui contrôle si une action enregistrée peut encore être annulée sans conflit
    private boolean estActionValide(String res)
    {
        if (res == null || res.isBlank()) return false;
        if (this.plateau == null) return true;

        String[] parties = res.replaceAll("[\\[\\],]", " ").trim().split("\\s+");

        // Vérifie la faisabilité de l'annulation selon le type d'action mémorisée
        return switch (parties[0])
        {
            // Un placement est annulable si la case est toujours modifiable et non vide
            case "setNbrCase" -> {
                int lig = Integer.parseInt(parties[2]);
                int col = Integer.parseInt(parties[3]);
                yield this.plateau.getCasePlateau(lig, col).estModifiable()
                   && this.plateau.getNombreCase(lig, col) != 0;
            }
            // Un effacement est annulable si la case est modifiable et que le chiffre n'a pas été reposé ailleurs
            case "effacerChiffre" -> {
                int val = Integer.parseInt(parties[1]);
                int lig = Integer.parseInt(parties[2]);
                int col = Integer.parseInt(parties[3]);
                yield this.plateau.getCasePlateau(lig, col).estModifiable()
                   && !this.plateau.estPresent(val);
            }
            // Un échange est annulable si les deux cases concernées sont toujours modifiables
            case "echangerNbr" -> {
                int lig1 = Integer.parseInt(parties[1]);
                int col1 = Integer.parseInt(parties[2]);
                int lig2 = Integer.parseInt(parties[3]);
                int col2 = Integer.parseInt(parties[4]);
                yield this.plateau.getCasePlateau(lig1, col1).estModifiable()
                   && this.plateau.getCasePlateau(lig2, col2).estModifiable();
            }
            default -> false;
        };
    }

    // Méthode utilitaire qui analyse la chaîne d'action et exécute l'opération inverse via le contrôleur
    private void decomposeur(String res)
    {
        if (res == null || res.isBlank()) return;

        String[] parties = res.replaceAll("[\\[\\],]", " ").trim().split("\\s+");

        // Exécute l'action symétrique correspondant à l'opération dépilée
        switch (parties[0])
        {
            case "setNbrCase" -> {
                int lig = Integer.parseInt(parties[2]);
                int col = Integer.parseInt(parties[3]);
                this.ctrl.effacerChiffreAnnulation(lig, col);
            }
            case "effacerChiffre" -> {
                int val = Integer.parseInt(parties[1]);
                int lig = Integer.parseInt(parties[2]);
                int col = Integer.parseInt(parties[3]);
                this.ctrl.setNbrCaseAnnulation(lig, col, val);
            }
            case "echangerNbr" -> {
                int lig1 = Integer.parseInt(parties[1]);
                int col1 = Integer.parseInt(parties[2]);
                int lig2 = Integer.parseInt(parties[3]);
                int col2 = Integer.parseInt(parties[4]);
                this.ctrl.echangerNbrAnnulation(lig2, col2, lig1, col1);
            }
            default -> System.err.println("Action inconnue : " + parties[0]);
        }
    }
}