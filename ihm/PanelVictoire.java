package ihm;

import controleur.Controleur;
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
        this.frame = frame;
        this.setLayout(new GridLayout(6, 1));
        this.setBackground(new Color(200, 219, 250));

        JLabel lblVictoire = new JLabel("VICTOIRE !", SwingConstants.CENTER);
        lblVictoire.setFont(new Font("Sans-Serif", Font.BOLD, 48));
        lblVictoire.setForeground(new Color(0, 150, 0));

        this.btnAccueil = new JButton("Retour à l'accueil");
        this.btnAccueil.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnAccueil.setOpaque(false);
        this.btnAccueil.addActionListener(this);

        this.btnQuitter = new JButton("Quitter");
        this.btnQuitter.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnQuitter.setOpaque(false);
        this.btnQuitter.addActionListener(this);

        JPanel pnlBtnAccueil = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtnAccueil.setOpaque(false);
        pnlBtnAccueil.add(this.btnAccueil);

        JPanel pnlBtnQuitter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtnQuitter.setOpaque(false);
        pnlBtnQuitter.add(this.btnQuitter);

        this.add(new JLabel(""));
        this.add(lblVictoire);
        this.add(new JLabel(""));
        this.add(pnlBtnAccueil);
        this.add(pnlBtnQuitter);
        this.add(new JLabel(""));
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