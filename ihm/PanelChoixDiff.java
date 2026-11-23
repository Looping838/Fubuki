package ihm;

import controleur.Controleur;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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

    private JPanel      pnlDifficulte;

    public JLabel       lblTitre;

    public Border       lumiere;

    public Border       ombreF;
    public Border       ombreM;
    public Border       ombreD;

    public Border       bordureNormaleF;
    public Border       bordureNormaleM;
    public Border       bordureNormaleD;

    public Border       bordureSurvolF;
    public Border       bordureSurvolM;
    public Border       bordureSurvolD;

    public Border       bordure3DF;
    public Border       bordure3DM;
    public Border       bordure3DD;

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

        this.creerComposants();

        /*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/

        this.positionnerComposants();

        /* ----------------------------- */
		/* Activation des Composants     */
		/* ----------------------------- */

        this.activerComposants();

        this.setVisible(true);
    }

    @Override
    public void actionPerformed ( ActionEvent e )
	{
        // Difficulté : 0 = Facile, 1 = Moyen, 2 = Difficile

        // Difficulté Facile
        if (e.getSource() == this.btnFacile)
        {
            this.ctrl.initPlateau(0);  
            this.frame.creerPanelJeu();
            this.frame.setPnl(this.frame.getPnl(this.indice + 1));
        }

        // Difficulté Moyen
        if (e.getSource() == this.btnMoyen)
        {
            this.ctrl.initPlateau(1);
            this.frame.creerPanelJeu();
            this.frame.setPnl(this.frame.getPnl(this.indice + 1));
        }

        // Difficulté Difficile
        if (e.getSource() == this.btnDifficile)
        {
            this.ctrl.initPlateau(2);
            this.frame.creerPanelJeu();
            this.frame.setPnl(this.frame.getPnl(this.indice + 1));
        }

        // Retour à l'accueil
        if (e.getSource() == this.btnRetour)
        {
            this.frame.setPnl(this.frame.getPnl(this.indice - 1));
        }
    }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

     // Méthode qui crée les composants de l'interface
    private void creerComposants()
    {
        // Bordure lumiere + ombre de couleur (haut, gauche, bas, droite)
        this.lumiere = BorderFactory.createMatteBorder(2, 2, 0, 0, Color.WHITE);
        
        this.lblTitre = new JLabel("Choisissez une difficulté :");
        lblTitre.setFont(new Font("Sans-Serif", Font.BOLD, 70));

        // --- BOUTON FACILE ---
        this.btnFacile = new JButton("Facile");
        this.btnFacile.setHorizontalTextPosition(SwingConstants.CENTER);
        this.btnFacile.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.btnFacile.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnFacile.setBackground(ConstantesIHM.COULEUR_FOND_BOUTON_FACILE); // Le bouton devient blanc
        this.btnFacile.setFocusPainted(false);
        this.btnFacile.setPreferredSize(new Dimension(250, 250));
        this.btnFacile.setIcon(new ImageIcon(this.frame.creerDessins("./images/logos/etoile-pleine.png"))); 
        this.btnFacile.setIconTextGap(15);
        
        this.ombreF  = BorderFactory.createMatteBorder(0, 0, 3, 3, ConstantesIHM.OMBRE_FACILE);
        
        // Bordure normale (juste l'ombre + la marge interne)
        this.bordureNormaleF = BorderFactory.createCompoundBorder(this.ombreF, ConstantesIHM.MARGE);
        
        // Bordure survol (l'ombre + la lumière + la marge interne)
        this.bordure3DF = BorderFactory.createCompoundBorder(this.ombreF, this.lumiere);
        this.bordureSurvolF = BorderFactory.createCompoundBorder(this.bordure3DF, ConstantesIHM.MARGE);

        // Par défaut, on applique la bordure normale
        this.btnFacile.setBorder(this.bordureNormaleF);


        // --- BOUTON MOYEN ---
        this.btnMoyen = new JButton("Moyen");
        this.btnMoyen.setHorizontalTextPosition(SwingConstants.CENTER);
        this.btnMoyen.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.btnMoyen.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnMoyen.setBackground(ConstantesIHM.COULEUR_FOND_BOUTON_MOYEN); // Le bouton devient blanc
        this.btnMoyen.setFocusPainted(false);
        this.btnMoyen.setPreferredSize(new Dimension(250, 250)); 
        this.btnMoyen.setIcon(new ImageIcon(this.frame.creerDessins("./images/logos/etoile-pleine2.png")));
        this.btnMoyen.setIconTextGap(15);

        this.ombreM  = BorderFactory.createMatteBorder(0, 0, 3, 3, ConstantesIHM.OMBRE_MOYEN);
        
        // Bordure normale (juste l'ombre + la marge interne)
        this.bordureNormaleM = BorderFactory.createCompoundBorder(this.ombreM, ConstantesIHM.MARGE);
        
        // Bordure survol (l'ombre + la lumière + la marge interne)
        this.bordure3DM = BorderFactory.createCompoundBorder(this.ombreM, this.lumiere);
        this.bordureSurvolM = BorderFactory.createCompoundBorder(this.bordure3DM, ConstantesIHM.MARGE);

        this.btnMoyen.setBorder(this.bordureNormaleM);


        // --- BOUTON DIFFICILE ---
        this.btnDifficile = new JButton("Difficile");
        this.btnDifficile.setHorizontalTextPosition(SwingConstants.CENTER);
        this.btnDifficile.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.btnDifficile.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnDifficile.setBackground(ConstantesIHM.COULEUR_FOND_BOUTON_DIFF); // Le bouton devient blanc
        this.btnDifficile.setFocusPainted(false);
        this.btnDifficile.setPreferredSize(new Dimension(250, 250)); 
        this.btnDifficile.setIcon(new ImageIcon(this.frame.creerDessins("./images/logos/etoile-pleine3.png")));
        this.btnDifficile.setIconTextGap(15);

        this.ombreD  = BorderFactory.createMatteBorder(0, 0, 3, 3, ConstantesIHM.OMBRE_DIFFICILE);
        
        // Bordure normale (juste l'ombre + la marge interne)
        this.bordureNormaleD = BorderFactory.createCompoundBorder(this.ombreD, ConstantesIHM.MARGE);
        
        // Bordure survol (l'ombre + la lumière + la marge interne)
        this.bordure3DD = BorderFactory.createCompoundBorder(this.ombreD, this.lumiere);
        this.bordureSurvolD = BorderFactory.createCompoundBorder(this.bordure3DD, ConstantesIHM.MARGE);

        this.btnDifficile.setBorder(this.bordureNormaleD);


        // --- BOUTON RETOUR ---
        this.btnRetour      = new JButton("  Retour à l'accueil"   );
        this.btnRetour      .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnRetour      .setBackground(Color.WHITE);
        this.btnRetour      .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnRetour      .setFocusPainted(false);
        this.btnRetour      .setPreferredSize(new Dimension(250, 60)); 
        this.btnRetour      .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/accueil.png", 30, 25)));

        this.pnlDifficulte  = new JPanel(new GridLayout(1, 3, 25, 0));
        this.pnlDifficulte  .setOpaque(false);
    }

    // Méthode qui positionne les composants de l'interface
    private void positionnerComposants()
    {
        this.pnlDifficulte.add(this.btnFacile);
        this.pnlDifficulte.add(this.btnMoyen);
        this.pnlDifficulte.add(this.btnDifficile);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Tout sera empilé dans la même colonne
        gbc.anchor = GridBagConstraints.CENTER; // Centré horizontalement

        // Le Titre
        gbc.gridy = 0; // Tout en haut de notre grille
        // Insets(Haut, Gauche, Bas, Droite) = grand espace sous le titre
        gbc.insets = new Insets(180, 0, 80, 0);
        this.add(lblTitre, gbc);

        // Le panel Difficulté
        gbc.gridy = 1; // En dessous du titre
        gbc.insets = new Insets(0, 0, 20, 0); // Petit espace de 20px sous ce bouton
        this.add(this.pnlDifficulte, gbc);

        // Le bouton Retour
        gbc.gridy = 2; 
        gbc.insets = new Insets(50, 0, 0, 0); 
        this.add(this.btnRetour, gbc);

        // LE RESSORT INVISIBLE
        gbc.gridy = 3; // On le place sur une 5ème ligne
        gbc.weighty = 1.0; // Il aspire tout l'espace vertical restant !
        this.add(new JLabel(""), gbc); // On ajoute un texte vide
    }

    // Méthode qui active les composants de l'interface
    private void activerComposants()
    {
        this.btnFacile      .addActionListener(this);
        this.btnFacile      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnFacile.setBackground(ConstantesIHM.VERT_DIFFICULTE);
                btnFacile.setForeground(Color.BLACK); // Pour que le texte reste lisible

                btnFacile.setBorder(bordure3DF);
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnFacile.setBackground(ConstantesIHM.COULEUR_FOND_BOUTON_FACILE);
                btnFacile.setForeground(Color.BLACK);

                btnFacile.setBorder(bordureNormaleF);
            }
        });

        this.btnMoyen       .addActionListener(this);
        this.btnMoyen      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnMoyen.setBackground(ConstantesIHM.ORANGE_DIFFICULTE);
                btnMoyen.setForeground(Color.BLACK); // Pour que le texte reste lisible

                btnMoyen.setBorder(bordure3DM);
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnMoyen.setBackground(ConstantesIHM.COULEUR_FOND_BOUTON_MOYEN);
                btnMoyen.setForeground(Color.BLACK);

                btnMoyen.setBorder(bordureNormaleM);
            }
        });

        this.btnDifficile   .addActionListener(this);
        this.btnDifficile      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnDifficile.setBackground(ConstantesIHM.ROUGE_DIFFICULTE);
                btnDifficile.setForeground(Color.BLACK); // Pour que le texte reste lisible

                btnDifficile.setBorder(bordure3DD);
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnDifficile.setBackground(ConstantesIHM.COULEUR_FOND_BOUTON_DIFF);
                btnDifficile.setForeground(Color.BLACK);

                btnDifficile.setBorder(bordureNormaleD);
            }
        });

        this.btnRetour      .addActionListener(this);
        this.btnRetour      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnRetour.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                btnRetour.setForeground(Color.BLACK); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnRetour.setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                btnRetour.setBackground(Color.WHITE);
                btnRetour.setForeground(Color.BLACK);
            }
        });
    }
}