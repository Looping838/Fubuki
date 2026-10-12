package metier ;

import controleur.Controleur;

public class Pile 
{
    private String[]    pile ;
    private int         numAction ;
    private Controleur  ctrl ;

    public Pile (Controleur ctrl)
    {
        this.pile      = new String[10] ;
        this.numAction = 1 ;
        this.ctrl      = ctrl ;
    }

    public void   empiler( String action ) 
    { 
        this.pile[numAction] = action   ;
        this.numAction ++ ;
    }
    
    public void depiler()                
    { 
        if ( this.numAction > 1)
        {
            this.numAction -- ;
            this.decomposeur( this.pile[numAction]) ;
        }

    }

    public void decomposeur(String res)
    {
        if (res == null || res.isBlank()) return;

        String[] parties = res.replaceAll("[\\[\\],]", " ").trim().split("\\s+");
        String action = parties[0];

        switch (action)
        {
            case "setNbrCase" -> {
                int lig = Integer.parseInt(parties[2]); 
                int col = Integer.parseInt(parties[3]); 
                this.ctrl.effacerChiffreAnnulation(lig, col);            
            }

            case "effacerChiffre" -> {
                int val = Integer.parseInt(parties[1]); 
                int lig = Integer.parseInt(parties[2]);
                int col = Integer.parseInt(parties[3]);
                this.ctrl.setNbrCaseAnnulation(lig, col, val);
            }

            case "echangerNbr" -> {
                int lig1 = Integer.parseInt(parties[1]);
                int col1 = Integer.parseInt(parties[2]);
                int lig2 = Integer.parseInt(parties[3]);
                int col2 = Integer.parseInt(parties[4]);
                this.ctrl.echangerNbrAnnulation(lig2, col2, lig1, col1);
            }

            default -> System.err.println("Action inconnue : " + action);
        }
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

    public boolean estVide() 
    {
        return this.numAction <= 1;
    }
}
