import java.util.Random;

public class Fubuki 
{
    private Plateau plateau ;
    private int[]   nbrPossible = { 1,2,3,4,5,6,7,8,9 };

    public Fubuki ()
    {
        this.plateau = new Plateau() ;
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
                this.plateau.getCasePlateau(x, y).set_nombre(nbrPossible[index]);
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
        for (int i = nbrPossible.length - 1; i > 0; i--)
        {
            int j = rand.nextInt(i + 1);
            int temp = nbrPossible[i];
            nbrPossible[i] = nbrPossible[j];
            nbrPossible[j] = temp;
        }
    }
}
