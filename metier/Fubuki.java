package metier;

import controleur.Controleur;

public class Fubuki 
{
    private Plateau plateau ;
    

    private Controleur ctrl;

    public Fubuki (Controleur ctrl)
    {
        this.plateau = new Plateau(ctrl) ;
        this.ctrl = ctrl;
    }


}
