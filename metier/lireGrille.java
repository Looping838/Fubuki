package metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class lireGrille 
{
    private static final String FICHIER = "./grilleJour/grille_jour.txt";

    public static boolean chargerGrille(Plateau plateau, Plateau complet)
    {
        File f = new File(FICHIER);
        if (!f.exists())
        {
            System.err.println("Erreur : Le fichier " + FICHIER + " est introuvable.");
            return false;
        }

        try (Scanner sc = new Scanner(f))
        {
            // Lecture de la solution (3x3)
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    int val = sc.nextInt();
                    complet.getCasePlateau(i, j).set_nombre(val);
                    plateau.getCasePlateau(i, j).set_nombre(0);
                    plateau.getCasePlateau(i, j).setModifiable(true);
                }
            }

            // Totaux des lignes
            for (int i = 0; i < 3; i++)
            {
                int totL = sc.nextInt();
                plateau.setTotauxLignes(i, totL);
                complet.setTotauxLignes(i, totL);
            }

            // Totaux des colonnes
            for (int j = 0; j < 3; j++)
            {
                int totC = sc.nextInt();
                plateau.setTotauxColonnes(j, totC);
                complet.setTotauxColonnes(j, totC);
            }

            // Cases indices (coordonnées x et y)
            while (sc.hasNextInt())
            {
                int lig = sc.nextInt();
                int col = sc.nextInt();
                int val = complet.getCasePlateau(lig, col).get_nombre();

                plateau.getCasePlateau(lig, col).set_nombre(val);
                plateau.getCasePlateau(lig, col).setModifiable(false);
            }

            return true;
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Fichier non trouvé : " + e.getMessage());
            return false;
        }
    }
}