package metier;

import controleur.Controleur;

public class Fubuki 
{
    private Plateau plateau       ;
    
    private Controleur ctrl;

    public Fubuki (Controleur ctrl)
    {
        this.plateau       = new Plateau(ctrl);
        this.plateau.initPlateau();
        this.ctrl = ctrl;
    }

    public Plateau getPlateau() 
    { 
        return this.plateau; 
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


}
