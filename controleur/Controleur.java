package controleur;

import ihm.FrameJeu;
import java.awt.Dimension;
import metier.Case;
import metier.Fubuki;
import metier.Plateau;

public class Controleur
{
	private FrameJeu        frameJeu;
    private Fubuki          metier;
    private Plateau         plateau;

    private Dimension       tailleEcran;

    int hJeu, lJeu;

    public Controleur()
	{	
        this.frameJeu   = new FrameJeu (this);

        tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        hJeu = (int) tailleEcran.getHeight();
		lJeu = (int) tailleEcran.getWidth ();

        this.frameJeu.setSize(tailleEcran);
    }

    public void initPlateau( int difficulte )
    {
        this.metier  = new Fubuki(this , difficulte );
        this.plateau = this.metier.getPlateau();
    }

    public Case     getCasePlateau(int lig, int col)            { return this.plateau.getCasePlateau(lig, col); }
    
    public int      getDifficulte()                             { return this.metier.getDifficulte(); }
    public int      getNbLigne()                                { return this.plateau.getNbLigne();}
    public int      getNbColonne()                              { return this.plateau.getNbColonne();}
    public int      getTotauxLignes(int i)                      { return this.plateau.getTotauxLignes(i);}
    public int      getTotauxColonnes(int i)                    { return this.plateau.getTotauxColonnes(i);}
    public int      getNombreCasePlateau (int lig , int col )   { return this.plateau.getNombreCase(lig , col ) ; }
    public int[]    getNbrPossible()                            { return this.plateau.getNbrPossible(); }
    public int      getIndiceCase ()                            { return this.metier.getIndice() ; }

    public void     setNbrCase ( int lig , int col , int num )  
    {  
        this.plateau.getCasePlateau(lig , col ).set_nombre(num) ;
        this.metier.getPile().empiler("setNbrCase " + num + " [" + lig + "," + col + "]");
    }

    public boolean  estPresent(int nbr)                          { return this.metier.estPresent(nbr); }
    public boolean  estGagne()                                   { return this.metier.estGagne(); }
    
    public boolean  estLigneValide(int ligne) 
    {
        int somme = 0;
        for (int col = 0; col < getNbColonne(); col++) 
            somme += getNombreCasePlateau(ligne, col);

        return somme == getTotauxLignes(ligne);
    }

    public boolean  estColonneValide(int col) 
    {
        int somme = 0;
        for (int lig = 0; lig < getNbLigne(); lig++) 
            somme += getNombreCasePlateau(lig, col);

        return somme == getTotauxColonnes(col);
    }

    public void    resetJeu() { this.initPlateau( this.metier.getDifficulte() ) ; } 

    public void    effacerChiffre(int ligne, int colonne)               
    { 
        int num = this.metier.getPlateau().getNombreCase(ligne, colonne) ;
        this.metier.effacerChiffre(ligne, colonne);
        this.metier.getPile().empiler("effacerChiffre " + num + " [" + ligne + "," + colonne + "]");
    }

    public void    echangerNbr (int lig1, int col1, int lig2, int col2) 
    { 
        this.metier.echangerNbr(lig1, col1, lig2, col2);
        this.metier.getPile().empiler("echangerNbr " + " [" + lig1 + "," + col1 + "]" + " [" + lig2 + "," + col2 + "]");
    }

    public void    depilerCase() { this.metier.getPile().depiler(); }

    public static void main (String[] args) { new Controleur(); }

}