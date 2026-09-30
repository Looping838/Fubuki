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
		this.setSize(700, 700);
		this.setLayout(new BorderLayout());

        this.tabPanel = new JPanel[3];
        this.tabPanel[0] = new PanelAccueil(ctrl, this, 0);
        
        this.panelActif = this.tabPanel[0];

        this.add(this.panelActif, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public JPanel getPnl(int indice) {return this.tabPanel[indice];}
    
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

    public void creerPanelJeu()   { this.tabPanel[1] = new PanelJeu   (ctrl); }
}