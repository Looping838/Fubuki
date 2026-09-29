package controleur;

import ihm.FrameJeu;
import metier.Fubuki;
import metier.Plateau;


public class Controleur
{
	private FrameJeu      frameJeu;
    private Fubuki        metier;
    private Plateau       plateau;

    public Controleur()
	{	
		initPlateau();
        
        this.plateau    = new Plateau(this);
        this.frameJeu   = new FrameJeu (this);
    }

    public void    initPlateau()
    {
        this.metier = new Fubuki(this);
        metier.initPlateau();
    }

    public int getNbLigne()     { return this.plateau.getNbLigne();}
    public int getNbColonne()   { return this.plateau.getNbColonne();}


    public static void main (String[] args) { new Controleur(); }
}