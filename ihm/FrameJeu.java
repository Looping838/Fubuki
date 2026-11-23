package ihm;

import controleur.Controleur;
import java.awt.*;
import javax.swing.*;

public class FrameJeu extends JFrame
{
    private Controleur ctrl;
    
    private JPanel   panelActif;
    private JPanel[] tabPanel;
    
    public FrameJeu (Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.setTitle("Fubuki");
		this.setSize(800, 800);
		this.setLayout(new BorderLayout());

        ImageIcon icone = new ImageIcon("./images/icones/fubuki.png");
        this.setIconImage(icone.getImage());

        this.tabPanel = new JPanel[4];
        this.tabPanel[0] = new PanelAccueil(ctrl, this, 0);
        
        this.panelActif = this.tabPanel[0];

        this.add(this.panelActif, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /*-------------------------------*/
    /*            Getters            */
    /*-------------------------------*/

    public JPanel getPnl(int indice) {return this.tabPanel[indice];}

    /*-------------------------------*/
    /*            Setters            */
    /*-------------------------------*/
    
    // Méthode permettant de changer de panel sur l'écran
	public void setPnl(JPanel pnl)
	{
		this.remove(this.panelActif);  // On enlève le panel de la Frame
		this.panelActif = pnl;         // Le panelActif devient le nouveau panel passé en paramètre
		this.add(this.panelActif);     // Le nouveau panelActif est ajouté à la Frame

        // On actualise
		this.repaint();
		this.revalidate();
	}

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    public void creerPanelChoixDiff()   { this.tabPanel[1] = new PanelChoixDiff     (ctrl , this, 1); }
    public void creerPanelJeu()         { this.tabPanel[2] = new PanelJeu           (ctrl , this, 2); }
    
    public void afficherPanelResultat(boolean estVictoire)
    {
        this.tabPanel[3] = new PanelResultat(ctrl, this, 3, estVictoire);
        this.setPnl(this.tabPanel[3]);
    }

    public void restartJeu() 
    { 
        PanelJeu pnlJeu = (PanelJeu) this.tabPanel[2];
        pnlJeu.restart(); 
    }

    // Permet de créer une image à partir d'un chemin et de la redimensionner
    public Image creerImages (String chemin, int longueur, int largeur)
    {
        ImageIcon   imgOriginale;
        Image       imgRedimensionnee;

        imgOriginale        = new ImageIcon(chemin);
        imgRedimensionnee   = imgOriginale.getImage().getScaledInstance(longueur, largeur, Image.SCALE_SMOOTH);
		
        return imgRedimensionnee;
    }

    // Permet de créer une image à partir d'un chemin et de la redimensionner
    public Image creerDessins (String chemin)
    {
        ImageIcon   imgOriginale;
        Image       imgRedimensionnee;

        imgOriginale        = new ImageIcon(chemin);
        imgRedimensionnee   = imgOriginale.getImage().getScaledInstance(imgOriginale.getIconWidth(), imgOriginale.getIconHeight(), Image.SCALE_SMOOTH);
		
        return imgRedimensionnee;
    }
}