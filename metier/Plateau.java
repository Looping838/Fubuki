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
    public void     setCase           (int x , int y , int nbr){ this.plateau[x][y].set_nombre(nbr) ; }

    public void     initPlateau()
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

    public void     plateauJeu (int difficulte)
    {
        if ( difficulte == 0 )
        {
            Random rand = new Random();
            int x = 4 ;
            int y = 4 ;

            int cpt = 0 ;

            while ( cpt < 2 )
            {
                x = rand.nextInt(this.getNbLigne());
                y = rand.nextInt(this.getNbColonne());
                
                if (this.getCasePlateau(x , y).estModifiable() == true )
                {
                    this.getCasePlateau(x , y).setModifiable(false) ;
                    cpt ++ ;
                }
            }
        }
        else 
            if ( difficulte == 1 )
            {
                Random rand = new Random();
                int x = 4 ;
                int y = 4 ;

                x = rand.nextInt(this.getNbLigne());
                y = rand.nextInt(this.getNbColonne());

                this.getCasePlateau(x , y).setModifiable(false) ; 
            }
    }

    public void     plateauVide ()
    {
        for ( int cpt = 0 ; cpt < this.getNbLigne() ; cpt ++ )
        {
            for ( int cpt2 = 0 ; cpt2 < this.getNbColonne() ; cpt2 ++ )
            {
                if (this.getCasePlateau(cpt , cpt2).estModifiable() == true )
                {
                    this.getCasePlateau(cpt , cpt2).set_nombre(0) ;
                }
            }
        }
    }

    public boolean  estPresent ( int nbr )
    {
        for ( int cpt = 0 ; cpt < this.getNbLigne() ; cpt ++ )
            for ( int cpt2 = 0 ; cpt2 < this.getNbColonne() ; cpt2 ++ )
                if ( this.getCasePlateau(cpt, cpt2).get_nombre() == nbr )
                    return true ;

        return false ;
    }

    public boolean  estGagne ()
    {

        for (int cpt = 0; cpt < this.getNbLigne(); cpt++)
        {
            int somme = 0;
            for (int cpt2 = 0; cpt2 < this.getNbColonne(); cpt2++)
            {
                somme += this.getCasePlateau(cpt, cpt2).get_nombre();
            }
            if (somme != this.getTotauxLignes(cpt))
                return false;
        }

        for (int cpt = 0; cpt < this.getNbColonne(); cpt++)
        {
            int somme = 0;
            for (int cpt2 = 0; cpt2 < this.getNbLigne(); cpt2++)
            {
                somme += this.getCasePlateau(cpt2, cpt).get_nombre();
            }
            if (somme != this.getTotauxColonnes(cpt))
                return false;
        }

        return true;
    }

    public void     echangerNbr(int lig1, int col1, int lig2, int col2) 
    {
        // On récupère les deux objets Case via le Plateau
        Case case1 = this.getCasePlateau(lig1, col1);
        Case case2 = this.getCasePlateau(lig2, col2);
        
        // On échange physiquement leurs nombres en mémoire
        int memoire = case1.get_nombre();
        case1.set_nombre(case2.get_nombre());
        case2.set_nombre(memoire);
    }

    private void    melangerNombres()
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