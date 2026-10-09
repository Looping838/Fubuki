package metier ;

public class Pile 
{
    private String[] pile ;
    private int      numAction ;

    public Pile ()
    {
        this.pile      = new String[50] ;
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

}
