package controleur;

import ihm.FrameJeu;
import metier.Case;
import metier.Fubuki;
import metier.Plateau;

public class Controleur
{
	private FrameJeu      frameJeu;
    private Fubuki        metier;
    private Plateau       plateau;

    public Controleur()
	{	
		this.initPlateau();
        this.frameJeu   = new FrameJeu (this);
    }

    public void initPlateau()
    {
        this.metier  = new Fubuki(this);
        this.plateau = this.metier.getPlateau();
    }

    public Case     getCasePlateau(int lig, int col)            { return this.plateau.getCasePlateau(lig, col); }
    
    public int      getNbLigne()                                { return this.plateau.getNbLigne();}
    public int      getNbColonne()                              { return this.plateau.getNbColonne();}
    public int      getTotauxLignes(int i)                      { return this.plateau.getTotauxLignes(i);}
    public int      getTotauxColonnes(int i)                    { return this.plateau.getTotauxColonnes(i);}
    public int      getNombreCasePlateau (int lig , int col )   { return this.plateau.getNombreCase(lig , col ) ; }
    public int[]    getNbrPossible()                            { return this.plateau.getNbrPossible(); }

    public boolean estPresent(int nbr)                          { return this.metier.estPresent(nbr); }
    public boolean estGagne()                                   { return this.metier.estGagne(); }

    public void    effacerChiffre(int ligne, int colonne)       { this.metier.effacerChiffre(ligne, colonne);}



    public static void main (String[] args) { new Controleur(); }
}