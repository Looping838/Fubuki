package ihm;

import controleur.Controleur;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelResultat extends JPanel implements ActionListener
{
    private Controleur  ctrl;
    private FrameJeu    frame;
    private int         indice;
    private boolean     estVictoire;
    private String      temps;

    private JButton     btnAccueil;
    private JButton     btnQuitter;

    private JButton     btnRecommencer;
    private JButton     btnChoisir;

    private JLabel      lblResultat;
    private JLabel      lblStats;

    public PanelResultat(Controleur ctrl, FrameJeu frame, int indice, boolean estVictoire, String temps)
    {
        this.setLayout(new GridBagLayout());
        this.setBackground(ConstantesIHM.COULEUR_FOND);

        this.ctrl           = ctrl;
        this.frame          = frame;
        this.indice         = indice;
        this.estVictoire    = estVictoire;
        this.temps          = temps;

        /*-------------------------------*/
		/*   Création des composants     */
		/*-------------------------------*/       

        this.creerComposants();

        /*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/        

        this.positionnnerComposants();

        /* ----------------------------- */
		/* Activation des Composants     */
		/* ----------------------------- */        

        this.activerComposants();

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAccueil)
        {
            this.frame.setPnl(this.frame.getPnl(0));
            this.ctrl.resetJeu();
        }

        else if (e.getSource() == this.btnRecommencer)
        {
            this.frame.cacherMenuBar(true);
            this.frame.restartJeu();
            this.frame.setPnl(this.frame.getPnl(this.indice - 1));
        }

        else if (e.getSource() == this.btnQuitter)
        {
            System.exit(0);
        }
    }

     // Méthode qui crée les composants de l'interface
    private void creerComposants()
    {
        this.btnAccueil     = new JButton("  Retourner à l'accueil");
        this.btnAccueil     .setFont(new Font("Sans-Serif", Font.PLAIN, 24));
        this.btnAccueil     .setBackground(Color.WHITE);
        this.btnAccueil     .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnAccueil     .setFocusPainted(false);
        this.btnAccueil     .setPreferredSize(new Dimension(400, 60));
        this.btnAccueil     .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/accueil.png", 30, 30)));

        this.btnRecommencer = new JButton("  Recommencer une partie");
        this.btnRecommencer .setFont(new Font("Sans-Serif", Font.PLAIN, 24));
        this.btnRecommencer .setBackground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
        this.btnRecommencer .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnRecommencer .setFocusPainted(false);
        this.btnRecommencer .setPreferredSize(new Dimension(400, 60));
        this.btnRecommencer .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/restart.png", 35, 35)));
        
        this.btnQuitter     = new JButton("  Quitter");
        this.btnQuitter     .setFont(new Font("Sans-Serif", Font.PLAIN, 24));
        this.btnQuitter     .setBackground(Color.WHITE);
        this.btnQuitter     .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnQuitter     .setFocusPainted(false);
        this.btnQuitter     .setPreferredSize(new Dimension(400, 60));

        this.lblResultat    = new JLabel("", SwingConstants.CENTER);

        this.lblStats       = new JLabel("", SwingConstants.CENTER);
        this.lblStats       .setFont(new Font("Noto Serif Display Black", Font.PLAIN, 28));
        
        // Si victoire, on affiche "VICTOIRE !" en vert, sinon on affiche "DEFAITE .." en rouge
        if (this.estVictoire)
        {
            this.lblResultat.setIcon(new ImageIcon("./images/logos/victoire.png"));
            this.lblStats.setText(temps + " | " + frame.getNbValidationsRestantes());
        }
        else
        {
            this.lblResultat.setIcon(new ImageIcon("./images/logos/defaite.png"));
        }
    }

    // Méthode qui positionne les composants de l'interface
    private void positionnnerComposants()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Tout sera empilé dans la même colonne
        gbc.anchor = GridBagConstraints.CENTER; // Centré horizontalement

        // Le Label Resultat
        gbc.gridy = 0; // Tout en haut de notre grille
        // Insets(Haut, Gauche, Bas, Droite) = grand espace sous le titre
        gbc.insets = new Insets(130, 0, 80, 0);
        this.add(this.lblResultat, gbc);

        gbc.gridy = 1; // Tout en bas
        gbc.insets = new Insets(0, 0, 40, 0); 
        this.add(this.lblStats, gbc);

        // Le bouton Recommencer
        gbc.gridy = 2; // Tout en bas
        gbc.insets = new Insets(50, 0, 20, 0); 
        this.add(this.btnRecommencer, gbc);
        
        // Le bouton Accueil
        gbc.gridy = 3; // En dessous du titre
        gbc.insets = new Insets(0, 0, 20, 0); // Petit espace de 50px au-dessus, et 20px sous ce bouton
        this.add(this.btnAccueil, gbc);

        // Le bouton Quitter 
        gbc.gridy = 4; // Tout en bas
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(this.btnQuitter, gbc);

        // LE RESSORT INVISIBLE
        gbc.gridy = 5; // On le place sur une 4ème ligne
        gbc.weighty = 1.0; // Il aspire tout l'espace vertical restant
        this.add(new JLabel(""), gbc); // On ajoute un texte vide
    }

    // Méthode qui active les composants de l'interface
    private void activerComposants()
    {
        this.btnAccueil     .addActionListener(this);
        this.btnAccueil      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnAccueil.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                btnAccueil.setForeground(Color.BLACK); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnAccueil.setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                btnAccueil.setBackground(Color.WHITE);
                btnAccueil.setForeground(Color.BLACK);
            }
        });

        this.btnRecommencer .addActionListener(this);
        this.btnRecommencer      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnRecommencer.setBackground(ConstantesIHM.COULEUR_ROSE_CERVEAU_FONCE);
                btnRecommencer.setForeground(Color.BLACK); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnRecommencer.setBackground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
                btnRecommencer.setForeground(Color.BLACK);
            }
        });

        this.btnQuitter     .addActionListener(this);
        this.btnQuitter      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnQuitter.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                btnQuitter.setForeground(Color.BLACK); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnQuitter.setBackground(Color.WHITE);
                btnQuitter.setForeground(Color.BLACK);
            }
        });
    }
}