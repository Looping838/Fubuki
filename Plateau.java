public class Plateau 
{
    private Case[][] plateau ;
    private int []   totaux_lignes;
    private int []   totaux_colonnes;


    public Plateau ()
    {
        this.plateau         = new Case[3][3] ;
        this.totaux_colonnes = new int [3]    ;
        this.totaux_lignes   = new int [3]    ;
    }

    public Case getCasePlateau     (int x , int y ) { return this.plateau[x][y]      ;}
    public int  getTotaux_lignes   (int x )         { return this.totaux_lignes[x]   ;}
    public int  getTotaux_colonnes (int y )         { return this.totaux_colonnes[y] ;}

    public int  setTotaux_lignes   (int x , int nbr) { return this.totaux_lignes[x]   = nbr ;}
    public int  setTotaux_colonnes (int y , int nbr) { return this.totaux_colonnes[y] = nbr ;}

}