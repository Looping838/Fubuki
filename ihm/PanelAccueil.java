package ihm;

import controleur.Controleur;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PanelAccueil extends JPanel implements ActionListener
{
    private Controleur ctrl;
    private FrameJeu   frame;

    private int        indice;

    private JButton    btnJouer;
    private JButton    btnQuitter;

    public static final Color   LUMIERE         = Color.WHITE;
    public static final Color   OMBRE           = Color.LIGHT_GRAY;
    public static final Border  RELIEF          = BorderFactory.createRaisedBevelBorder();          // Relief 3D
    public static final Border  MARGE           = BorderFactory.createEmptyBorder(10, 25, 10, 25);  // Marge invisible (H, G, B, D)

    public PanelAccueil(Controleur ctrl, FrameJeu frame, int indice)
    {
        this.setLayout(new GridLayout(7, 1));
        this.setBackground(PanelJeu.COULEUR_FOND);

        /*-------------------------------*/
		/*   Création des composants     */
		/*-------------------------------*/

        this.ctrl   = ctrl;
        this.frame  = frame;
        this.indice = indice;
                
        JPanel pnlCentre        = new JPanel();
        pnlCentre               .setLayout(new GridLayout(3,1));
        pnlCentre               .setOpaque(false);
        
        JPanel pnlTitre         = new JPanel();
        pnlTitre                .setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        pnlTitre                .setOpaque(false);
        
        JPanel pnlBtnJouer      = new JPanel();
        pnlBtnJouer             .setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pnlBtnJouer             .setOpaque(false);

        JPanel pnlBtnQuitter    = new JPanel();
        pnlBtnQuitter           .setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pnlBtnQuitter           .setOpaque(false);


        JLabel lblTitre         = new JLabel("Fubuki");
        lblTitre                .setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        lblTitre                .setFont(new Font("Sans-Serif", Font.BOLD, 60));

        JLabel lblTitreOmbre    = new JLabel("Fubuki");
        lblTitreOmbre           .setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        lblTitreOmbre           .setFont(new Font("Sans-Serif", Font.BOLD, 60));
        lblTitreOmbre           .setForeground(OMBRE);

        this.btnJouer           = new JButton("Jouer !");
        this.btnJouer           .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnJouer           .setBackground(Color.WHITE);
        this.btnJouer           .setBorder(BorderFactory.createCompoundBorder(RELIEF, MARGE));
        this.btnJouer           .setFocusPainted(false);

        this.btnQuitter         = new JButton("Quitter");
        this.btnQuitter         .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnQuitter         .setBackground(Color.WHITE);
        this.btnQuitter         .setBorder(BorderFactory.createCompoundBorder(RELIEF, MARGE));
        this.btnQuitter         .setFocusPainted(false);

        /*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
        
        pnlTitre        .add(lblTitre);
        pnlBtnJouer     .add(this.btnJouer);
        pnlBtnQuitter   .add(this.btnQuitter);

        this.add(new JLabel(""));
        this.add(pnlTitre);
        this.add(new JLabel(""));
        this.add(pnlBtnJouer);
        this.add(pnlBtnQuitter);
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