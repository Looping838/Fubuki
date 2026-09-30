package ihm;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class PanelAccueil extends JPanel implements ActionListener
{
    private Controleur ctrl;
    private FrameJeu   frame;

    private int        indice;

    private JButton    btnJouer;
    private JButton    btnQuitter;

    public PanelAccueil(Controleur ctrl, FrameJeu frame, int indice)
    {
        this.setLayout(new GridLayout(5, 1));

        /*-------------------------------*/
		/*   Création des composants     */
		/*-------------------------------*/

        this.ctrl = ctrl;
        this.frame = frame;
        this.indice = indice;
        
        JPanel pnlCentre        = new JPanel();
        pnlCentre               .setLayout(new GridLayout(2,1));
        pnlCentre               .setOpaque(false);
        
        JPanel pnlBtnJouer      = new JPanel();
        pnlBtnJouer             .setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnlBtnJouer             .setOpaque(false);

        JPanel pnlBtnQuitter    = new JPanel();
        pnlBtnQuitter           .setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnlBtnQuitter           .setOpaque(false);


        this.btnJouer           = new JButton("Jouer !");
        this.btnJouer           .setOpaque(false);

        this.btnQuitter         = new JButton("Quitter");
        this.btnQuitter         .setOpaque(false);

        /*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
        
        pnlBtnJouer     .add(this.btnJouer);
        pnlBtnQuitter   .add(this.btnQuitter);

        pnlCentre       .add(pnlBtnJouer);
        pnlCentre       .add(pnlBtnQuitter);

        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(pnlCentre, BorderLayout.CENTER);
        this.add(new JLabel(""));
        this.add(new JLabel(""));

        /* ----------------------------- */
		/* Activation des Composants     */
		/* ----------------------------- */

        this.btnJouer  .addActionListener(this);
		this.btnQuitter.addActionListener(this);

        this.setVisible(true);
    }

    public void actionPerformed ( ActionEvent e )
	{
        if (e.getSource() == this.btnJouer)
        {
            this.frame.creerPanelJeu();
            this.frame.setPnl(this.frame.getPnl(this.indice + 1));
        }

        if (e.getSource() == this.btnQuitter)
        {
            System.exit(0);
        }
    }
}