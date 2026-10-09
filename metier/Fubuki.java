package metier;

import controleur.Controleur;
import java.util.Random;

public class Fubuki 
{
    private Plateau plateau;
    private int     difficulter ;

    private Controleur ctrl;

    public Fubuki (Controleur ctrl , int difficulter )
    {
        this.difficulter        = difficulter ; 
        this.plateau            = new Plateau(ctrl);
        this.plateau.initPlateau();
        this.plateauJeu() ;
        this.plateauVide() ;
        this.ctrl = ctrl;
    }

    public Plateau getPlateau() 
    { 
        return this.plateau; 
    }

    public void setDifficulter(int difficulter)
    {
        this.difficulter = difficulter ;
    }

    public int getDifficulter () { return this.difficulter ;}
    
    public void plateauJeu ()
    {
        if ( this.difficulter == 0 )
        {
            Random rand = new Random();
            int x = 4 ;
            int y = 4 ;

            int cpt = 0 ;

            while ( cpt < 2 )
            {
                x = rand.nextInt(this.plateau.getNbLigne());
                y = rand.nextInt(this.plateau.getNbColonne());
                
                if (this.plateau.getCasePlateau(x , y).estModifiable() == true )
                {
                    this.plateau.getCasePlateau(x , y).setModifiable(false) ;
                    cpt ++ ;
                }
            }
        }
        else 
            if ( this.difficulter == 1 )
            {
                Random rand = new Random();
                int x = 4 ;
                int y = 4 ;

                x = rand.nextInt(this.plateau.getNbLigne());
                y = rand.nextInt(this.plateau.getNbColonne());

                this.plateau.getCasePlateau(x , y).setModifiable(false) ; 
            }
            else 
            {

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

    public void effacerChiffre(int ligne, int colonne)  
    { 
        this.plateau.setCase(ligne, colonne, 0);
    }

    public void echangerNbr(int lig1, int col1, int lig2, int col2) 
    {
        // On récupère les deux objets Case via le Plateau
        Case case1 = this.plateau.getCasePlateau(lig1, col1);
        Case case2 = this.plateau.getCasePlateau(lig2, col2);
        
        // On échange physiquement leurs nombres en mémoire
        int memoire = case1.get_nombre();
        case1.set_nombre(case2.get_nombre());
        case2.set_nombre(memoire);
    }

}