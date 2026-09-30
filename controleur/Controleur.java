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
		this.initPlateau();
        this.frameJeu   = new FrameJeu (this);
    }

    public void initPlateau()
    {
        this.metier  = new Fubuki(this);
        this.plateau = this.metier.getPlateau();
    }

    public int getNbLigne()                 { return this.plateau.getNbLigne();}
    public int getNbColonne()               { return this.plateau.getNbColonne();}
    public int getTotauxLignes(int i)       { return this.plateau.getTotauxLignes(i);}
    public int getTotauxColonnes(int i)     { return this.plateau.getTotauxColonnes(i);}



    public static void main (String[] args) { new Controleur(); }
}