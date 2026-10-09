package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelVictoire extends JPanel implements ActionListener
{
    private FrameJeu frame;
    private JButton  btnAccueil;
    private JButton  btnQuitter;

    public PanelVictoire(FrameJeu frame)
    {
        this.setLayout(new GridLayout(6, 1));
        this.setBackground(PanelJeu.COULEUR_FOND);

        /*-------------------------------*/
		/*   Création des composants     */
		/*-------------------------------*/       
        
        this.frame = frame;

        JLabel lblVictoire = new JLabel("VICTOIRE !", SwingConstants.CENTER);
        lblVictoire.setFont(new Font("Sans-Serif", Font.BOLD, 48));
        lblVictoire.setForeground(new Color(0, 150, 0));

        this.btnAccueil = new JButton("Retourner à l'accueil");
        this.btnAccueil.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnAccueil.setBackground(Color.WHITE);
        this.btnAccueil.setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, PanelAccueil.MARGE));
        this.btnAccueil.setFocusPainted(false);

        this.btnQuitter = new JButton("Quitter");
        this.btnQuitter.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnQuitter.setBackground(Color.WHITE);
        this.btnQuitter.setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, PanelAccueil.MARGE));
        this.btnQuitter.setFocusPainted(false);

        JPanel pnlBtnAccueil = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtnAccueil.setOpaque(false);

        JPanel pnlBtnQuitter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtnQuitter.setOpaque(false);

        /*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/        

        pnlBtnAccueil.add(this.btnAccueil);
        pnlBtnQuitter.add(this.btnQuitter);

        this.add(new JLabel(""));
        this.add(lblVictoire);
        this.add(new JLabel(""));
        this.add(pnlBtnAccueil);
        this.add(pnlBtnQuitter);
        this.add(new JLabel(""));

        /* ----------------------------- */
		/* Activation des Composants     */
		/* ----------------------------- */        

        this.btnAccueil.addActionListener(this);
        this.btnQuitter.addActionListener(this);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAccueil)
        {
            // Revient sur le PanelAccueil (indice 0)[cite: 1]
            this.frame.setPnl(this.frame.getPnl(0));
            this.frame.getCtrl().resetJeu();
        }

        else if (e.getSource() == this.btnQuitter)
        {
            System.exit(0);
        }
    }
}