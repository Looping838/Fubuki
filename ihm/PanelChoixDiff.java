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
        this.setBackground(ConstantesIHM.COULEUR_FOND);

        this.ctrl   = ctrl;
        this.frame  = frame;
        this.indice = indice;

        /*-------------------------------*/
		/*   Création des composants     */
		/*-------------------------------*/

        JLabel lblTitre = new JLabel("Choisissez une difficulté :");
        lblTitre.setFont(new Font("Sans-Serif", Font.BOLD, 70));

        // --- BOUTON FACILE ---
        this.btnFacile = new JButton("Facile");
        this.btnFacile.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnFacile.setBackground(Color.WHITE); // Le bouton devient blanc
        this.btnFacile.setFocusPainted(false);
        this.btnFacile.setPreferredSize(new Dimension(250, 60));
        this.btnFacile.setIcon(new ImageIcon(this.creerImages("./images/icones/facile.png", 30, 25))); 
        
        // Ombre rouge : 0px en haut et à gauche, 3px en bas et à droite 
        Border ombreF = BorderFactory.createMatteBorder(0, 0, 3, 3, ConstantesIHM.OMBRE_FACILE);
        this.btnFacile.setBorder(BorderFactory.createCompoundBorder(ombreF, ConstantesIHM.MARGE));

        // --- BOUTON MOYEN ---
        this.btnMoyen = new JButton("Moyen");
        this.btnMoyen.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnMoyen.setBackground(Color.WHITE); // Le bouton devient blanc
        this.btnMoyen.setFocusPainted(false);
        this.btnMoyen.setPreferredSize(new Dimension(250, 60)); 
        this.btnMoyen.setIcon(new ImageIcon(this.creerImages("./images/icones/moyen.png", 30, 25)));

        // Ombre jaune/orange
        Border ombreM = BorderFactory.createMatteBorder(0, 0, 3, 3, ConstantesIHM.OMBRE_MOYEN);
        this.btnMoyen.setBorder(BorderFactory.createCompoundBorder(ombreM, ConstantesIHM.MARGE));

        // --- BOUTON DIFFICILE ---
        this.btnDifficile = new JButton("Difficile");
        this.btnDifficile.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnDifficile.setBackground(Color.WHITE); // Le bouton devient blanc
        this.btnDifficile.setFocusPainted(false);
        this.btnDifficile.setPreferredSize(new Dimension(250, 60)); 
        this.btnDifficile.setIcon(new ImageIcon(this.creerImages("./images/icones/difficile.png", 30, 25)));

        // Ombre rouge
        Border ombreD = BorderFactory.createMatteBorder(0, 0, 3, 3, ConstantesIHM.OMBRE_DIFFICILE);
        this.btnDifficile.setBorder(BorderFactory.createCompoundBorder(ombreD, ConstantesIHM.MARGE));

        this.btnRetour      = new JButton("  Retour à l'accueil"   );
        this.btnRetour      .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnRetour      .setBackground(Color.WHITE);
        this.btnRetour      .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
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
        this.btnFacile      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnFacile.setBackground(ConstantesIHM.VERT_DIFFICULTE);
                btnFacile.setForeground(Color.WHITE); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnFacile.setBackground(Color.WHITE);
                btnFacile.setForeground(Color.BLACK);
            }
        });

        this.btnMoyen       .addActionListener(this);
        this.btnMoyen      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnMoyen.setBackground(ConstantesIHM.ORANGE_DIFFICULTE);
                btnMoyen.setForeground(Color.WHITE); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnMoyen.setBackground(Color.WHITE);
                btnMoyen.setForeground(Color.BLACK);
            }
        });

        this.btnDifficile   .addActionListener(this);
        this.btnDifficile      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnDifficile.setBackground(ConstantesIHM.ROUGE_DIFFICULTE);
                btnDifficile.setForeground(Color.WHITE); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnDifficile.setBackground(Color.WHITE);
                btnDifficile.setForeground(Color.BLACK);
            }
        });

        this.btnRetour      .addActionListener(this);
        this.btnRetour      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnRetour.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                btnRetour.setForeground(Color.WHITE); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnRetour.setBackground(Color.WHITE);
                btnRetour.setForeground(Color.BLACK);
            }
        });

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