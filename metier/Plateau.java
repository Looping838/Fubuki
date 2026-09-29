package metier;

public class Plateau 
{
    private Case[][] plateau ;
    private int []   totauxLignes;
    private int []   totauxColonnes;


    public Plateau ()
    {
        this.plateau = new Case[3][3] ;

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

    public Case     getCasePlateau     (int x , int y )  { return this.plateau        [x][y] ;}
    public Case[][] getPlateau         ()                { return this.plateau               ;}
    public int      getTotauxLignes    (int x )          { return this.totauxLignes   [x]    ;}
    public int      getTotauxColonnes  (int y )          { return this.totauxColonnes [y]    ;}

    public void setTotauxLignes   (int x   , int nbr)     { this.totauxLignes  [x] = nbr  ;}
    public void setTotauxColonnes (int y   , int nbr)     { this.totauxColonnes[y] = nbr  ;}

}