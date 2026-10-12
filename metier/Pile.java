package metier ;

import controleur.Controleur;

public class Pile 
{
    private String[] pile ;
    private int      numAction ;

    public Pile (Controleur ctrl)
    {
        this.pile      = new String[10] ;
        this.numAction = 1 ;
    }

    public void   empiler( String action ) 
    { 
        this.pile[numAction] = action   ;
        this.numAction ++ ;
    }
    
    public String depiler()                
    { 
        if ( this.numAction <= 1)
        {
            this.numAction -- ;
            return this.pile[numAction + 1] ;
        }
        else 
            return "pas possible" ;
    }


    public String afficherPile ()
    {
        String res = " [ " ;

        for ( int cpt = 0 ; cpt < this.pile.length ; cpt ++ )
        {
            res += this.pile[cpt] + " , " ;
        }

        res += " ] " ;


        return res ;
    }
}
