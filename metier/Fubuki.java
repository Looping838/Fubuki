package metier;

import java.util.Random;

import controleur.Controleur;

public class Fubuki 
{
    private Plateau plateau ;
    private int[]   nbrPossible = { 1,2,3,4,5,6,7,8,9 };

    private Controleur ctrl;

    public Fubuki (Controleur ctrl)
    {
        this.plateau = new Plateau(ctrl) ;
        this.ctrl = ctrl;
    }

    public Plateau initPlateau()
    {
        melangerNombres();

        int index = 0;
        int taille = this.plateau.getPlateau().length;

        for (int x = 0; x < taille; x++)
        {
            for (int y = 0; y < taille; y++)
            {
                this.plateau.getCasePlateau(x, y).set_nombre(this.nbrPossible[index]);
                index++;
            }
        }

        for (int i = 0; i < taille; i++)
        {
            int sommeLigne = 0;
            int sommeColonne = 0;

            for (int j = 0; j < taille; j++)
            {
                sommeLigne   += this.plateau.getCasePlateau(i, j).get_nombre();
                sommeColonne += this.plateau.getCasePlateau(j, i).get_nombre();
            }

            this.plateau.setTotauxLignes(i, sommeLigne);
            this.plateau.setTotauxColonnes(i, sommeColonne);
        }

        return this.plateau;
    }

    private void melangerNombres()
    {
        Random rand = new Random();
        for (int i = this.nbrPossible.length - 1; i > 0; i--)
        {
            int j = rand.nextInt(i + 1);
            int temp = this.nbrPossible[i];
            this.nbrPossible[i] = nbrPossible[j];
            this.nbrPossible[j] = temp;
        }
    }

    public void afficherPlateau() 
    {
        System.out.println("\n--- PLATEAU FUBUKI ---");

        int taille = this.plateau.getPlateau().length;

        // Affichage des lignes avec leur résultat cible à droite
        for (int i = 0; i < taille; i++) 
        {
            System.out.print("| ");
            for (int j = 0; j < taille; j++) 
            {
                int val = this.plateau.getCasePlateau(i, j).get_nombre();
                // Affiche un espace ou point si la case vaut 0 (vide), sinon le chiffre
                if (val == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(val + " ");
                }
            }
            // Total cible de la ligne
            System.out.println("| = " + this.plateau.getTotauxLignes(i));
        }

        System.out.println("----------------------");

        // Affichage des totaux cibles des colonnes en bas
        System.out.print("  ");
        for (int j = 0; j < taille; j++) 
        {
            // Alignement sur 2 caractères pour que les nombres >= 10 restent alignés
            System.out.printf("%-2d", this.plateau.getTotauxColonnes(j));
        }
        System.out.println("\n");
    }

    /*public static void main(String[] args) 
    {
        Fubuki jeu = new Fubuki();
        jeu.initPlateau();
        jeu.afficherPlateau();
    }*/
}
