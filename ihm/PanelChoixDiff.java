package ihm;

import controleur.Controleur;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PanelChoixDiff extends JPanel implements ActionListener
{
    public static final Color   LUMIERE         = Color.WHITE;
    public static final Color   OMBRE           = Color.LIGHT_GRAY;
    public static final Border  RELIEF          = BorderFactory.createRaisedBevelBorder();          // Relief 3D
    public static final Border  MARGE           = BorderFactory.createEmptyBorder(10, 25, 10, 25);  // Marge invisible (H, G, B, D)

    public static final Color   ROUGE_DIFFICULTE           = new Color(255, 94, 77  );
    public static final Color   ORANGE_DIFFICULTE          = new Color(255, 215, 0  );
    public static final Color   VERT_DIFFICULTE            = new Color(135, 233, 144);
    
    private Controleur  ctrl;
    private FrameJeu    frame;
    private int         indice;

    private JButton     btnFacile;
    private JButton     btnMoyen;
    private JButton     btnDifficile;
    private JButton     btnRetour;

    public PanelChoixDiff(Controleur ctrl, FrameJeu frame, int indice)
    {
        this.setLayout(new GridBagLayout());
        this.setBackground(PanelJeu.COULEUR_FOND);

        this.ctrl   = ctrl;
        this.frame  = frame;
        this.indice = indice;

        /*-------------------------------*/
		/*   Création des composants     */
		/*-------------------------------*/

        JLabel lblTitre = new JLabel("Choisissez une difficulté :");
        lblTitre.setFont(new Font("Sans-Serif", Font.BOLD, 70));

        this.btnFacile      = new JButton("Facile"      );
        this.btnFacile      .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnFacile      .setBackground(VERT_DIFFICULTE);
        this.btnFacile      .setBorder(BorderFactory.createCompoundBorder(RELIEF, MARGE));
        this.btnFacile      .setFocusPainted(false);
        this.btnFacile      .setPreferredSize(new Dimension(250, 60));
        this.btnFacile      .setIcon(new ImageIcon(this.creerImages("./images/icones/facile.png", 30, 25))); 

        this.btnMoyen       = new JButton("Moyen"       );
        this.btnMoyen       .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnMoyen       .setBackground(ORANGE_DIFFICULTE);
        this.btnMoyen       .setBorder(BorderFactory.createCompoundBorder(RELIEF, MARGE));
        this.btnMoyen       .setFocusPainted(false);
        this.btnMoyen       .setPreferredSize(new Dimension(250, 60)); 
        this.btnMoyen       .setIcon(new ImageIcon(this.creerImages("./images/icones/moyen.png", 30, 25)));

        this.btnDifficile   = new JButton("Difficile"   );
        this.btnDifficile   .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnDifficile   .setBackground(ROUGE_DIFFICULTE);
        this.btnDifficile   .setBorder(BorderFactory.createCompoundBorder(RELIEF, MARGE));
        this.btnDifficile   .setFocusPainted(false);
        this.btnDifficile   .setPreferredSize(new Dimension(250, 60)); 
        this.btnDifficile   .setIcon(new ImageIcon(this.creerImages("./images/icones/difficile.png", 30, 25)));

        this.btnRetour      = new JButton("Retour à l'accueil"   );
        this.btnRetour      .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnRetour      .setBackground(Color.WHITE);
        this.btnRetour      .setBorder(BorderFactory.createCompoundBorder(RELIEF, MARGE));
        this.btnRetour      .setFocusPainted(false);
        this.btnRetour      .setPreferredSize(new Dimension(250, 60)); 
        this.btnRetour      .setIcon(new ImageIcon(this.creerImages("./images/icones/accueil.png", 30, 25)));


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
        this.add(lblTitre, gbc);

        // Le bouton Facile
        gbc.gridy = 1; // En dessous du titre
        gbc.insets = new Insets(50, 0, 20, 0); // Petit espace de 20px sous ce bouton
        this.add(this.btnFacile, gbc);

        // Le bouton Moyen
        gbc.gridy = 2; // Tout en bas
        gbc.insets = new Insets(0, 0, 20, 0); // Pas d'espace en dessous
        this.add(this.btnMoyen, gbc);

        // Le bouton Difficile
        gbc.gridy = 3; 
        gbc.insets = new Insets(0, 0, 40, 0); 
        this.add(this.btnDifficile, gbc);

        // Le bouton Difficile
        gbc.gridy = 4; 
        gbc.insets = new Insets(0, 0, 0, 0); 
        this.add(this.btnRetour, gbc);

        // LE RESSORT INVISIBLE
        gbc.gridy = 5; // On le place sur une 4ème ligne
        gbc.weighty = 1.0; // Il aspire tout l'espace vertical restant !
        this.add(new JLabel(""), gbc); // On ajoute un texte vide

        /* ----------------------------- */
		/* Activation des Composants     */
		/* ----------------------------- */

        this.btnFacile      .addActionListener(this);
        this.btnMoyen       .addActionListener(this);
        this.btnDifficile   .addActionListener(this);
        this.btnRetour      .addActionListener(this);

        this.setVisible(true);
    }

    public void actionPerformed ( ActionEvent e )
	{
        if (e.getSource() == this.btnFacile)
        {
            this.ctrl.initPlateau(0); // changer 0 par difficulter quand boutton placer 
            this.frame.creerPanelJeu();
            this.frame.setPnl(this.frame.getPnl(this.indice + 1));
        }

        if (e.getSource() == this.btnMoyen)
        {
            this.ctrl.initPlateau(1); // changer 0 par difficulter quand boutton placer 
            this.frame.creerPanelJeu();
            this.frame.setPnl(this.frame.getPnl(this.indice + 1));
        }

        if (e.getSource() == this.btnDifficile)
        {
            this.ctrl.initPlateau(2); // changer 0 par difficulter quand boutton placer 
            this.frame.creerPanelJeu();
            this.frame.setPnl(this.frame.getPnl(this.indice + 1));
        }

        if (e.getSource() == this.btnRetour)
        {
            this.frame.setPnl(this.frame.getPnl(this.indice - 1));
        }
    }

    public Image creerImages (String chemin, int longueur, int largeur)
    {
        ImageIcon   imgOriginale;
        Image       imgRedimensionnee;

        imgOriginale        = new ImageIcon(chemin);
        imgRedimensionnee   = imgOriginale.getImage().getScaledInstance(longueur, largeur, Image.SCALE_SMOOTH);
		
        return imgRedimensionnee;
    }
}