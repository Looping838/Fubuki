public class Case 
{
    private int x;
    private int y ;
    private int nombre ; 
    private boolean estModifiable ;

    public Case ( int x , int y)
    {
        this.x      = x ;
        this.y      = y ;
        this.nombre = 0 ;
        this.estModifiable = true ;
    }

    public int get_x      () { return this.x ;}
    public int get_y      () { return this.y ;}
    public int get_nombre () {return this.nombre ;}

    public void set_x       (int x)      { this.x = x ;}
    public void set_y       (int y)      { this.y = y ;}
    public void set_nombre  (int nombre) { this.nombre = nombre ;}

    public boolean estModifiable () { return this.estModifiable ;}
    public boolean estVide       () { return this.nombre == 0   ;}
}
