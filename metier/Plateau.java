package metier;

import controleur.Controleur;
import java.util.Random;

public class Plateau 
{
    private int      nbLigne;
    private int      nbColonne;
    
    private Case[][] plateau ;
    private int []   totauxLignes;
    private int []   totauxColonnes;

    private int[]   nbrPossible = { 1,2,3,4,5,6,7,8,9 };

    private Controleur ctrl;
    
    public Plateau (Controleur ctrl)
    {
        this.nbLigne = this.nbColonne = 3;
        
        this.plateau = new Case[this.nbLigne][this.nbColonne] ;

        for (int i = 0 ; i < 3 ; i++)
        {
            for (int j = 0 ; j < 3 ; j++)
            {
                this.plateau[i][j] = new Case(i,j) ;
            }
        }

        this.totauxColonnes = new int [3];
        this.totauxLignes   = new int [3];

    }

    public int      getNombreCase        (int x , int y )  { return getCasePlateau(x,y).get_nombre()  ;}

    public Case     getCasePlateau       (int x , int y )  { return this.plateau        [x][y] ;}
    public Case[][] getPlateau           ()                { return this.plateau               ;}
    public int[]    getEnsTotauxLignes   ()                { return this.totauxLignes          ;}
    public int      getNbLigne           ()                { return this.plateau.length        ;}
    public int      getTotauxLignes      (int x )          { return this.totauxLignes   [x]    ;}
    public int[]    getEnsTotauxColonnes ()                { return this.totauxColonnes        ;}
    public int      getNbColonne         ()                { return this.plateau[0].length     ;}
    public int      getTotauxColonnes    (int y )          { return this.totauxColonnes [y]    ;}
    public int[]    getNbrPossible       ()                { return this.nbrPossible           ;}

    public void     setTotauxLignes   (int x   , int nbr)      { this.totauxLignes  [x] = nbr  ;}
    public void     setTotauxColonnes (int y   , int nbr)      { this.totauxColonnes[y] = nbr  ;}
    public void     setCase           (int x , int y , int nbr)   { this.plateau[x][y].set_nombre(nbr) ; }

    public void initPlateau()
    {
        melangerNombres();

        int index = 0;
        int taille = this.getPlateau().length;

        for (int x = 0; x < taille; x++)
        {
            for (int y = 0; y < taille; y++)
            {
                this.getCasePlateau(x, y).set_nombre(this.nbrPossible[index]);
                index++;
            }
        }

        for (int i = 0; i < taille; i++)
        {
            int sommeLigne = 0;
            int sommeColonne = 0;

            for (int j = 0; j < taille; j++)
            {
                sommeLigne   += this.getCasePlateau(i, j).get_nombre();
                sommeColonne += this.getCasePlateau(j, i).get_nombre();
            }

            this.setTotauxLignes(i, sommeLigne);
            this.setTotauxColonnes(i, sommeColonne);
        }
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
}