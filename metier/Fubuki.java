package metier;

import controleur.Controleur;
import java.util.Random;

public class Fubuki 
{
    private Plateau plateau;

    private Controleur ctrl;

    public Fubuki (Controleur ctrl)
    {
        this.plateau = new Plateau(ctrl);
        this.plateau.initPlateau();
        this.plateauJeu() ;
        this.plateauVide() ;
        this.ctrl = ctrl;
    }

    public Plateau getPlateau() 
    { 
        return this.plateau; 
    }

    public void plateauJeu ()
    {
        Random rand = new Random();
        int x = 4 ;
        int y = 4 ;

        for ( int cpt = 0 ; cpt < 2 ; cpt ++ )
        {
            x = rand.nextInt(this.plateau.getNbLigne());
            y = rand.nextInt(this.plateau.getNbColonne());
             
            if (this.plateau.getCasePlateau(x , y).estModifiable() == true )
            {
                this.plateau.getCasePlateau(x , y).setModifiable(false) ;
            }
        }
    }

    public void plateauVide ()
    {
        for ( int cpt = 0 ; cpt < this.plateau.getNbLigne() ; cpt ++ )
        {
            for ( int cpt2 = 0 ; cpt2 < this.plateau.getNbColonne() ; cpt2 ++ )
            {
                    
                if (this.plateau.getCasePlateau(cpt , cpt2).estModifiable() == true )
                {
                    this.plateau.getCasePlateau(cpt , cpt2).set_nombre(0) ;
                }
            }
        }
    }

    public boolean estPresent ( int nbr )
    {
        for ( int cpt = 0 ; cpt < this.plateau.getNbLigne() ; cpt ++ )
            for ( int cpt2 = 0 ; cpt2 < this.plateau.getNbColonne() ; cpt2 ++ )
                if ( this.plateau.getCasePlateau(cpt, cpt2).get_nombre() == nbr )
                    return true ;

        return false ;
    }

    public boolean estGagne ()
    {

        for (int cpt = 0; cpt < this.plateau.getNbLigne(); cpt++)
        {
            int somme = 0;
            for (int cpt2 = 0; cpt2 < this.plateau.getNbColonne(); cpt2++)
            {
                somme += this.plateau.getCasePlateau(cpt, cpt2).get_nombre();
            }
            if (somme != this.plateau.getTotauxLignes(cpt))
                return false;
        }

        for (int cpt = 0; cpt < this.plateau.getNbColonne(); cpt++)
        {
            int somme = 0;
            for (int cpt2 = 0; cpt2 < this.plateau.getNbLigne(); cpt2++)
            {
                somme += this.plateau.getCasePlateau(cpt2, cpt).get_nombre();
            }
            if (somme != this.plateau.getTotauxColonnes(cpt))
                return false;
        }

        return true;
    }

    public void effacerChiffre(int ligne, int colonne)  { this.plateau.setCase(ligne, colonne, 0);}

    public void echangerNbr   ( int lig1 , int col1 ,
                                int lig2 , int col2 )
    {
        int tmp = 0 ;
        tmp = this.plateau.getNombreCase(lig1, col1) ;

        this.plateau.getCasePlateau(lig1, col1).set_nombre( this.plateau.getNombreCase(lig2, col2) );

        this.plateau.getCasePlateau(lig1, col1).set_nombre(tmp) ;
    }

}