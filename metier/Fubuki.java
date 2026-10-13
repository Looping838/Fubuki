package metier;

import controleur.Controleur;
import java.util.Random;

public class Fubuki 
{
    private Plateau plateau;
    private Plateau plateauComplet ;
    private int     difficulte ;
    private Pile    pile ;

    public Fubuki (Controleur ctrl , int difficulte )
    {
        this.difficulte        = difficulte ; 
        this.plateau           = new Plateau(ctrl);
        this.plateauComplet    = new Plateau(ctrl);
        this.plateau.initPlateau();
        this.plateauJeu() ;

        this.plateauComplet.initPlateau();
        
        for (int lig = 0; lig < this.plateau.getNbLigne(); lig++) 
        {
            for (int col = 0; col < this.plateau.getNbColonne(); col++) 
            {
                int valeur = this.plateau.getCasePlateau(lig, col).get_nombre();
                this.plateauComplet.getCasePlateau(lig, col).set_nombre(valeur);
            }
        }
        
        this.plateauVide() ;

        this.pile = new Pile(ctrl);
    }

    public Plateau getPlateau() 
    { 
        return this.plateau; 
    }

    public Plateau getPlateauComplet () { return this.plateauComplet ;}

    public Pile    getPile() { return this.pile ;}

    public void    setDifficulte(int difficulte)
    {
        this.difficulte = difficulte ;
    }

    public int     getDifficulte () { return this.difficulte ;}
    
    public void    plateauJeu ()
    {
        this.plateau.plateauJeu(this.difficulte);
    }

    public void     plateauVide ()
    {
        this.plateau.plateauVide();
    }

    public boolean estPresent ( int nbr )
    {
        return this.estPresent(nbr);
    }

    public boolean estGagne ()
    {
        return this.estGagne() ;
    }

    public void effacerChiffre(int ligne, int colonne)  
    { 
        this.plateau.setCase(ligne, colonne, 0);
    }

    public void echangerNbr(int lig1, int col1, int lig2, int col2) 
    {
        this.plateau.echangerNbr(lig1, col1, lig2, col2);
    }

    public void getIndice () 
    {
        Random rand = new Random();

        int indice = 0 ;

        int x = 4 ;
        int y = 4 ;

        int cpt = 0 ;

        while ( cpt < 1 )
        {
            x = rand.nextInt(this.plateau.getNbLigne());
            y = rand.nextInt(this.plateau.getNbColonne());
                
            if (this.plateau.getCasePlateau(x , y).estModifiable() == true )
            {
                indice = this.plateauComplet.getCasePlateau(x , y).get_nombre() ;

                for ( int lig = 0 ; lig < this.plateau.getNbColonne() ; lig ++ )
                    for ( int col = 0 ; col < this.plateau.getNbLigne() ; col ++ )
                        if ( this.plateau.getCasePlateau(lig,col).get_nombre() == indice )
                            this.effacerChiffre(lig, col);

                
                this.plateau.getCasePlateau(x, y).set_nombre(indice);
                this.plateau.getCasePlateau(x, y).setModifiable(false) ;
                cpt ++ ;
            }
        }

    }
}   

