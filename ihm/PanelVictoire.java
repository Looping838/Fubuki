package ihm;

import controleur.Controleur;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelVictoire extends JPanel implements ActionListener
{
    private Controleur  ctrl;
    private FrameJeu    frame;
    private int         indice;

    private JButton     btnAccueil;
    private JButton     btnQuitter;

    private JButton     btnRecommencer;
    private JButton     btnChoisir;

    public PanelVictoire(Controleur ctrl, FrameJeu frame, int indice)
    {
        this.setLayout(new GridBagLayout());
        this.setBackground(PanelJeu.COULEUR_FOND);

        this.ctrl   = ctrl;
        this.frame  = frame;
        this.indice = indice;

        /*-------------------------------*/
		/*   Création des composants     */
		/*-------------------------------*/       

        JLabel lblVictoire = new JLabel("VICTOIRE !", SwingConstants.CENTER);
        lblVictoire.setFont(new Font("Sans-Serif", Font.BOLD, 70));
        lblVictoire.setForeground(new Color(0, 150, 0));

        this.btnAccueil     = new JButton("Retourner à l'accueil");
        this.btnAccueil     .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnAccueil     .setBackground(Color.WHITE);
        this.btnAccueil     .setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, PanelAccueil.MARGE));
        this.btnAccueil     .setFocusPainted(false);
        this.btnAccueil     .setPreferredSize(new Dimension(300, 60));

        this.btnRecommencer = new JButton("Recommencer une partie");
        this.btnRecommencer .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnRecommencer .setBackground(Color.WHITE);
        this.btnRecommencer .setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, PanelAccueil.MARGE));
        this.btnRecommencer .setFocusPainted(false);
        this.btnRecommencer .setPreferredSize(new Dimension(300, 60));
        
        this.btnQuitter     = new JButton("Quitter");
        this.btnQuitter     .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnQuitter     .setBackground(Color.WHITE);
        this.btnQuitter     .setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, PanelAccueil.MARGE));
        this.btnQuitter     .setFocusPainted(false);
        this.btnQuitter     .setPreferredSize(new Dimension(300, 60));

        /*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Tout sera empilé dans la même colonne
        gbc.anchor = GridBagConstraints.CENTER; // Centré horizontalement

        // Le Titre
        gbc.gridy = 0; // Tout en haut de notre grille
        // Insets(Haut, Gauche, Bas, Droite) = grand espace sous le titre
        gbc.insets = new Insets(130, 0, 150, 0);
        this.add(lblVictoire, gbc);

        // Le bouton Facile
        gbc.gridy = 1; // En dessous du titre
        gbc.insets = new Insets(50, 0, 20, 0); // Petit espace de 20px sous ce bouton
        this.add(this.btnAccueil, gbc);

        // Le bouton Moyen
        gbc.gridy = 2; // Tout en bas
        gbc.insets = new Insets(0, 0, 20, 0); // Pas d'espace en dessous
        this.add(this.btnRecommencer, gbc);

        // Le bouton Moyen
        gbc.gridy = 3; // Tout en bas
        gbc.insets = new Insets(0, 0, 20, 0); // Pas d'espace en dessous
        this.add(this.btnQuitter, gbc);

        // LE RESSORT INVISIBLE
        gbc.gridy = 4; // On le place sur une 4ème ligne
        gbc.weighty = 1.0; // Il aspire tout l'espace vertical restant !
        this.add(new JLabel(""), gbc); // On ajoute un texte vide

        /* ----------------------------- */
		/* Activation des Composants     */
		/* ----------------------------- */        

        this.btnAccueil     .addActionListener(this);
        this.btnRecommencer .addActionListener(this);
        this.btnQuitter     .addActionListener(this);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAccueil)
        {
            this.frame.setPnl(this.frame.getPnl(0));
            this.ctrl.resetJeu();
        }

        else if (e.getSource() == this.btnRecommencer)
        {
            this.ctrl.resetJeu();
            this.ctrl.initPlateau(this.ctrl.getDifficulte());
            this.frame.setPnl(this.frame.getPnl(this.indice - 1));
        }

        else if (e.getSource() == this.btnQuitter)
        {
            System.exit(0);
        }
    }
}