package ihm;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;

public class PanelJeu extends JPanel implements ActionListener
{   
    private JPanel[][]          tabPnlCases;

    private Controleur          ctrl;
    private FrameJeu            frame;
    private int                 indice;

    private Dimension           tailleEcran;
    private int                 taillePlateau;
    private int                 tailleBoutonPave;
    private int                 tailleBoutonOutil;

    private int                 nbLigne;          // Permet de stocker le nombre de lignes   du plateau
    private int                 nbColonne;        // Permet de stocker le nombre de colonnes du plateau

    private int                 ligSelectionne;   // Permet de stocker la ligne   sélectionnée lors du jeu
    private int                 colSelectionne;   // Permet de stocker la colonne sélectionnée lors du jeu

    private JPanel              pnlHaut;
    private JPanel              pnlCentre;
    private JPanel              pnlBas;
    
    private JPanel              pnlBandeau;
    private JPanel              pnlPlateau;
    private JPanel              pnlNbres;
    private JPanel              pnlBoutons;
    private JPanel              pnlOutils;
    private JPanel              pnlColonneDroite;

    private JSeparator          separateurHaut;
    private JSeparator          separateurBas;

    private JLabel[]            tabLblTotauxLignes;
    private JLabel[]            tabLblTotauxColonnes;
    private JLabel              lblValidation;
    private JLabel              lblValidationCoul;

    private JButton             btnValide;
private JButton             btnReset;
    private JButton             btnRestart;
    private JButton             btnAccueil;
    private JButton             btnAnnuler;
    private JButton             btnGomme;
    private JButton             btnIndice;
    private JButton             btnBrouillon;

    private ButtonGroup         grpBtn;

    private JToggleButton       btnActif;

    private boolean             modeValidation  = false;
    private boolean             modeBrouillon   = false;

    private int                 nbIndicesRestants;
    private int                 nbValidationsRestantes;
    private int                 nbValidationsMax;

    public PanelJeu(Controleur ctrl, FrameJeu frame, int indice)
    {
        // Forcer la couleur grise quand on sélectionne un bouton de pnlNbres
        UIManager.put("ToggleButton.select", ConstantesIHM.COULEUR_SELECTION);
        // Forcer la couleur noire du lbl quand le bouton est désactivé pour être plus lisible
        UIManager.put("ToggleButton.disabledText", Color.BLACK);
        
        this.setLayout(new BorderLayout());
        this.setBackground(ConstantesIHM.COULEUR_FOND);

        this.ctrl           = ctrl;
        this.frame          = frame;
        this.indice         = indice;
        
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
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() instanceof JToggleButton)
        {
            JToggleButton btnClique = (JToggleButton) e.getSource();

            if (this.btnActif == btnClique) 
            {
                this.grpBtn.clearSelection();
                this.btnActif = null;
            }
            else 
            {
                this.btnActif = btnClique;
            }

            if (this.grpBtn != null) 
            {
                java.util.Enumeration<AbstractButton> elements = this.grpBtn.getElements();
                while (elements.hasMoreElements()) 
                {
                    AbstractButton btn = elements.nextElement();
                    
                    // On remet en blanc UNIQUEMENT si le bouton n'est pas placé ET n'est pas sélectionné
                    if (btn.getBackground() != ConstantesIHM.COULEUR_GRIS_PLACE && !btn.isSelected()) 
                    {
                        btn.setBackground(Color.WHITE);
                    }
                }
            }

            this.ligSelectionne = -1;
            this.colSelectionne = -1;

            this.majPlateau();
        }

        if (e.getSource() == this.btnGomme)
        {
           if (this.ligSelectionne != -1 && this.colSelectionne != -1)
            {
                JLabel lblContenu = (JLabel) tabPnlCases[this.ligSelectionne][this.colSelectionne].getComponent(0);
                String chiffreEfface = lblContenu.getText();
                    
                if (this.ctrl.getCasePlateau(this.ligSelectionne, this.colSelectionne).estModifiable())
                {
                    this.ctrl.effacerChiffre(this.ligSelectionne, this.colSelectionne);
                    
                    lblContenu.setText(""); 
                    lblContenu.setForeground(null);

                    if (!chiffreEfface.equals("") && !chiffreEfface.equals("0") && this.grpBtn != null) 
                    {
                        java.util.Enumeration<AbstractButton> elements = this.grpBtn.getElements();
                        while (elements.hasMoreElements()) 
                        {
                            AbstractButton btn = elements.nextElement();
                            if (btn.getText().equals(chiffreEfface)) 
                            {
                                btn.setBackground(Color.WHITE);
                                break; 
                            }
                        }
                    }
                }

                this.modeValidation = false; 
                this.ligSelectionne = -1;
                this.colSelectionne = -1;

                this.majPlateau();
            }
        }

        if (e.getSource() == this.btnIndice)
        {
            if (this.nbIndicesRestants > 0)
            {
                this.nbIndicesRestants--;
                
                // On met à jour l'affichage du texte
                this.btnIndice.setText("  " + this.nbIndicesRestants);

                // Si on n'a plus d'indices, on désactive le bouton
                if (this.nbIndicesRestants == 0)
                {
                    this.btnIndice.setBackground(Color.WHITE);
                    this.btnIndice.setEnabled(false);
                }

                this.ctrl.donnerIndice(); // On demande au métier de placer l'indice
                this.majPlateau();        // On rafraîchit la grille pour voir le chiffre
                this.majBoutons();        // On grise le bouton correspondant dans le pavé numérique
            }
        }

        if (e.getSource() == this.btnBrouillon)
        {
            // On inverse la valeur (Si c'était true ça passe à false, et inversement)
            this.modeBrouillon = !this.modeBrouillon;

            this.ligSelectionne = -1;  
            this.colSelectionne = -1;

            if(this.modeBrouillon)
                this.btnBrouillon.setBackground(ConstantesIHM.COULEUR_BROUILLON_ACTIVE);
            else 
                this.btnBrouillon.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL); // gris survol car la souris est encore dessus

            this.majPlateau();
        }

        if (e.getSource() == this.btnValide)
    {
            this.modeValidation = true; 
            this.ligSelectionne = -1;   
            this.colSelectionne = -1;

            this.majPlateau();

            // Si le joueur n'a pas gagné, on consomme une tentative de validation
            if (!this.ctrl.estGagne())
            {
                this.nbValidationsRestantes--;
                this.majAffichageValidation();

                if (this.nbValidationsRestantes <= 0)
                {
                    this.frame.afficherPanelResultat(false);
                    this.frame.setPnl(this.frame.getPnl(this.indice + 1));
                    this.ctrl.resetJeu();
                    return;
                }
            }
            else
            {
                this.btnGomme.setEnabled(false);
                java.util.Enumeration<AbstractButton> elements = this.grpBtn.getElements();
                while (elements.hasMoreElements()) 
                {
                    elements.nextElement().setEnabled(false);
                }

                this.frame.afficherPanelResultat(true);
            }
        }

        if (e.getSource() == this.btnReset)
        {
            this.reset();
        }

        if (e.getSource() == this.btnRestart)
        {
            this.restart();
        }

        if (e.getSource() == this.btnAnnuler)
        {
            this.ctrl.depilerCase();
            this.majPlateau();
            this.majBoutons();
        }

        if (e.getSource() == this.btnAccueil)
        {
            this.frame.setPnl(this.frame.getPnl(0));
        }
    }

    /*-------------------------------*/
    /*            Getters            */
    /*-------------------------------*/

    public int getNbValidationsRestantes()
    {
        return this.nbValidationsRestantes;
    }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    private void creerComposants()
    {
        // Récupère la taille de l'écran de l'utilisateur
        this.tailleEcran            = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        // Le plateau prendra 55% de la hauteur de l'écran
        this.taillePlateau          = (int) (tailleEcran.height * 0.55);

        // On calcule la taille des boutons du pavé numérique proportionnellement
        this.tailleBoutonPave       = (int) (taillePlateau * 0.15);

        this.tailleBoutonOutil      = (int) (this.tailleBoutonPave * 3) + 20;

        this.nbLigne                = this.ctrl.getNbLigne();
        this.nbColonne              = this.ctrl.getNbColonne();

        this.tabPnlCases            = new JPanel[this.nbLigne][this.nbColonne];

        this.tabLblTotauxLignes     = new JLabel[this.nbLigne];
        this.tabLblTotauxColonnes   = new JLabel[this.nbColonne];
        
        this.ligSelectionne         = -1;
        this.colSelectionne         = -1;

        // Panel qui contiendra pnlBandeau
        this.pnlHaut                = new JPanel();
        this.pnlHaut                .setLayout(new BorderLayout());
        this.pnlHaut                .setOpaque(false);

        // Panel qui contiendra le JLabel du bandeau
        this.pnlBandeau             = new JPanel();
        this.pnlBandeau             .setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        this.pnlBandeau             .setOpaque(false);

        this.lblValidation          = new JLabel();
        this.lblValidation          .setOpaque(false);
        this.lblValidation          .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.lblValidation          .setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        this.initNbValidations();

        // Panel qui contiendra pnlPlateau
        this.pnlCentre              = new JPanel(new GridBagLayout());
        this.pnlCentre              .setOpaque(false);
        
        // Panel qui affichera le Plateau
        this.pnlPlateau             = new JPanel();
        this.pnlPlateau             .setLayout(new GridLayout(this.nbLigne + 1, this.nbColonne + 1, 2, 2));
        this.pnlPlateau             .setPreferredSize(new Dimension(600, 600));
        this.pnlPlateau             .setBackground(ConstantesIHM.COULEUR_FOND);

        // Panel qui contiendra pnlNbres
        this.pnlBas                 = new JPanel();
        this.pnlBas                 .setLayout(new BorderLayout());
        this.pnlBas                 .setOpaque(false);

        this.pnlBoutons             = new JPanel();
        this.pnlBoutons             .setLayout(new FlowLayout(FlowLayout.CENTER , 20, 20));
        this.pnlBoutons             .setOpaque(false);

        // Panel qui affichera les nombres à placer
        this.pnlNbres               = new JPanel();
        this.pnlNbres               .setLayout(new GridLayout(3, 3, 10, 10));
        this.pnlNbres               .setBackground(ConstantesIHM.COULEUR_FOND);
        
        this.pnlOutils              = new JPanel();
        this.pnlOutils              .setLayout(new GridBagLayout());
        this.pnlOutils              .setOpaque(false);

        this.pnlColonneDroite       = new JPanel();
        this.pnlColonneDroite       .setLayout(new GridBagLayout());
        this.pnlColonneDroite       .setOpaque(false);

        // Crée une ligne séparatrice à l'aide de JSeparator
        this.separateurHaut         = new JSeparator(SwingConstants.HORIZONTAL);
        this.separateurHaut         .setForeground(ConstantesIHM.COULEUR_SEPARATEUR);
        this.separateurHaut         .setBackground(ConstantesIHM.COULEUR_FOND_SEPARATEUR);

        this.separateurBas          = new JSeparator(SwingConstants.HORIZONTAL);
        this.separateurBas          .setForeground(ConstantesIHM.COULEUR_SEPARATEUR);
        this.separateurBas          .setBackground(ConstantesIHM.COULEUR_FOND_SEPARATEUR);

        /*-----------------------------*/

        // Parcours pour dessiner le plateau
        this.dessinerPlateau();   

        this.btnValide              = new JButton("  Valider");
        this.btnValide              .setBackground(Color.WHITE);
        this.btnValide              .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnValide              .setPreferredSize(new Dimension(tailleEcran.width/6, 50));
        this.btnValide              .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/valide.png", 30, 30)));
        this.btnValide              .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnValide              .setFocusPainted(false);

        this.btnReset               = new JButton("  Réintialiser le plateau");
        this.btnReset               .setBackground(Color.WHITE);
        this.btnReset               .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnReset               .setPreferredSize(new Dimension(tailleEcran.width/6, 50));
        this.btnReset               .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/reset.png", 30, 25)));
        this.btnReset               .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnReset               .setFocusPainted(false);

        this.btnRestart             = new JButton("  Recommencer une partie");
        this.btnRestart             .setBackground(Color.WHITE);
        this.btnRestart             .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnRestart             .setPreferredSize(new Dimension(tailleEcran.width/6, 50));
        this.btnRestart             .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/restart.png", 35, 35)));
        this.btnRestart             .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnRestart             .setFocusPainted(false);

        this.btnAnnuler             = new JButton("  Annuler");
        this.btnAnnuler             .setBackground(Color.WHITE);
        this.btnAnnuler             .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnAnnuler             .setPreferredSize(new Dimension(tailleEcran.width/6, 50));
        this.btnAnnuler             .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/annuler.png", 30, 25)));
        this.btnAnnuler             .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnAnnuler             .setFocusPainted(false);
        this.btnAnnuler             .setEnabled(false);

        this.btnAccueil             = new JButton("  Retourner à l'accueil");
        this.btnAccueil             .setBackground(Color.WHITE);
        this.btnAccueil             .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnAccueil             .setPreferredSize(new Dimension(tailleEcran.width/6, 50));
        this.btnAccueil             .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/accueil.png", 30, 25)));
        this.btnAccueil             .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnAccueil             .setFocusPainted(false);

        this.dessinerBoutons();    

        this.btnGomme               = new JButton("");
        this.btnGomme               .setBackground(Color.WHITE);
        this.btnGomme               .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE_GOMME));
        this.btnGomme               .setPreferredSize(new Dimension((this.tailleBoutonOutil - 10) / 2, 50));
        this.btnGomme               .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/gomme.png", 30, 30)));
        this.btnGomme               .setFocusPainted(false);

        this.btnIndice              = new JButton("");
        this.btnIndice              .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnIndice              .setPreferredSize(new Dimension((this.tailleBoutonOutil - 10) / 2, 50));
        this.btnIndice              .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/indice.png", 40, 35)));
        this.btnIndice              .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnIndice              .setFocusPainted(false);

        this.initBoutonIndice();

        this.btnBrouillon           = new JButton("  Mode Brouillon");
        this.btnBrouillon           .setBackground(Color.WHITE);
        this.btnBrouillon           .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnBrouillon           .setPreferredSize(new Dimension(this.tailleBoutonOutil, 50));
        this.btnBrouillon           .setIcon(new ImageIcon(this.frame.creerImages("./images/icones/brouillon.png", 30, 25)));
        this.btnBrouillon           .setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnBrouillon           .setFocusPainted(false);
    }

    private void positionnerComposants()
    {
        this.pnlBoutons.add(this.btnValide );
        this.pnlBoutons.add(this.btnReset);
        this.pnlBoutons.add(this.btnRestart);
        this.pnlBoutons.add(this.btnAnnuler);
        this.pnlBoutons.add(this.btnAccueil);

        this.pnlBandeau.add(lblValidation);
        
        this.pnlHaut   .add(pnlBandeau     , BorderLayout.CENTER  );
        this.pnlHaut   .add(separateurHaut , BorderLayout.SOUTH );

        GridBagConstraints gbcOutils    = new GridBagConstraints();
        gbcOutils.fill                  = GridBagConstraints.BOTH; // Pour que les boutons s'étirent bien
        gbcOutils.insets                = new Insets(5, 5, 5, 5); // Un peu d'espace entre eux

        // 1. La Gomme (colonne 0, ligne 0)
        gbcOutils.gridx     = 0; 
        gbcOutils.gridy     = 0;
        gbcOutils.gridwidth = 1; // Prend 1 case
        gbcOutils.insets    = new Insets(15, 0, 0, 5);
        pnlOutils           .add(this.btnGomme, gbcOutils);

        // 2. L'Indice (colonne 1, ligne 0)
        gbcOutils.gridx     = 1; 
        gbcOutils.gridy     = 0;
        gbcOutils.insets    = new Insets(15, 5, 0, 0);
        pnlOutils           .add(this.btnIndice, gbcOutils);

        // 3. Le Mode Brouillon (colonne 0, ligne 1 sur 2 colonnes)
        gbcOutils.gridx     = 0; 
        gbcOutils.gridy     = 1;
        gbcOutils.gridwidth = 2; // Ici que le bouton devient large de deux boutons
        gbcOutils.insets    = new Insets(10, 0, 0, 0);
        pnlOutils           .add(this.btnBrouillon, gbcOutils);

        GridBagConstraints gbcDroite = new GridBagConstraints();
        gbcDroite.gridx             = 0; 
        
        // On ajoute d'abord le pavé numérique en haut
        gbcDroite.gridy             = 0;
        this.pnlColonneDroite       .add(this.pnlNbres, gbcDroite);
        
        // Puis les outils juste en dessous
        gbcDroite.gridy         = 1;
        gbcDroite.insets        = new Insets(38, 0, 0, 0); // Espace entre le pavé et les outils
        this.pnlColonneDroite   .add(this.pnlOutils, gbcDroite);

        // Création des règles de placement
        GridBagConstraints gbc  = new GridBagConstraints();

        gbc.anchor              = GridBagConstraints.NORTH;
        
        // 1ère règle : Le plateau (Colonne 0)
        gbc.gridx       = 0;
        gbc.gridy       = 0;
        gbc.insets      = new Insets(0, 0, 0, 100); // Ajoute une PanelAccueil.MARGE de 60 pixels à droite du plateau
        this.pnlCentre  .add(this.pnlPlateau, gbc);

        // 2ème règle : Le pavé numérique (Colonne 1)
        gbc.gridx       = 1;
        gbc.gridy       = 0;
        gbc.insets      = new Insets(0, 0, 0, 0); // Pas de PanelAccueil.MARGE supplémentaire
        this.pnlCentre  .add(this.pnlColonneDroite, gbc);

        pnlBas          .add(separateurBas  , BorderLayout.NORTH );
        pnlBas          .add(pnlBoutons     , BorderLayout.CENTER);

        this.add(pnlHaut        , BorderLayout.NORTH );
        this.add(this.pnlCentre , BorderLayout.CENTER);
        this.add(pnlBas         , BorderLayout.SOUTH );
    }

    private void activerComposants()
    {
        this.btnGomme.addActionListener(this);
        this.btnGomme.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnGomme.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                btnGomme.setForeground(Color.BLACK); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnGomme.setBackground(Color.WHITE);
                btnGomme.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                btnGomme.setBackground(Color.WHITE);
                btnGomme.setForeground(Color.BLACK);
            }
        });

        this.btnIndice.addActionListener(this);
        this.btnIndice.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                // Si le bouton est encore utilisable, on met la couleur de survol
                if (btnIndice.isEnabled()) {
                    btnIndice.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                // Si le bouton est encore utilisable, on le remet en orange.
                // Sinon (s'il a été utilisé), on le laisse tranquille (il sera blanc) !
                if (btnIndice.isEnabled()) {
                    btnIndice.setBackground(ConstantesIHM.COULEUR_INDICE_ACTIVE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                btnIndice.setBackground(Color.WHITE);
                btnIndice.setForeground(Color.BLACK);
            }
        });

        this.btnBrouillon.addActionListener(this);
        this.btnBrouillon.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                // On lit directement le booléen de la classe
                if (!PanelJeu.this.modeBrouillon) 
                    btnBrouillon.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                if (PanelJeu.this.modeBrouillon) 
                    btnBrouillon.setBackground(ConstantesIHM.COULEUR_BROUILLON_ACTIVE);
                else 
                    btnBrouillon.setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                btnBrouillon.setBackground(Color.WHITE);
                btnBrouillon.setForeground(Color.BLACK);
            }
        });
        
        this.btnValide.addActionListener(this);
        this.btnValide.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnValide.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                btnValide.setForeground(Color.BLACK); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnValide.setBackground(Color.WHITE);
                btnValide.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                btnValide.setBackground(Color.WHITE);
                btnValide.setForeground(Color.BLACK);
            }
        });
        
        this.btnReset   .addActionListener(this);
        this.btnReset      .addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnReset.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                btnReset.setForeground(Color.BLACK); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnReset.setBackground(Color.WHITE);
                btnReset.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                btnReset.setBackground(Color.WHITE);
                btnReset.setForeground(Color.BLACK);
            }
        });
        
        this.btnRestart.addActionListener(this);
        this.btnRestart.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnRestart.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                btnRestart.setForeground(Color.BLACK); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnRestart.setBackground(Color.WHITE);
                btnRestart.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                btnRestart.setBackground(Color.WHITE);
                btnRestart.setForeground(Color.BLACK);
            }
        });
        
        this.btnAnnuler.addActionListener(this);
        this.btnAnnuler.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                btnAnnuler.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                btnAnnuler.setForeground(Color.BLACK); // Pour que le texte reste lisible
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                btnAnnuler.setBackground(Color.WHITE);
                btnAnnuler.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                btnAnnuler.setBackground(Color.WHITE);
                btnAnnuler.setForeground(Color.BLACK);
            }
        });
        
        this.btnAccueil.addActionListener(this);
        this.btnAccueil.addMouseListener(new MouseAdapter() 
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
                btnAccueil.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                btnAccueil.setBackground(Color.WHITE);
                btnAccueil.setForeground(Color.BLACK);
            }
        });
        

        // Pour le clic dans le vide (désélectionne la case en cours)
        MouseAdapter clicDansLeVide = new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                PanelJeu.this.ligSelectionne = -1;
                PanelJeu.this.colSelectionne = -1;
                PanelJeu.this.majPlateau();
            }
        };

        // On l'applique au panneau principal et au panneau central
        this            .addMouseListener(clicDansLeVide);
        this.pnlCentre  .addMouseListener(clicDansLeVide);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put
        (KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "actionAnnuler");
        
        // On définit ce que fait l'action "actionAnnuler"
        this.getActionMap().put("actionAnnuler", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                btnAnnuler.doClick();
            }
        });
    }

    public void dessinerPlateau()
    {
        // 1. Vider le composant graphique de ses anciennes cases
        this.pnlPlateau.removeAll();

        // 2. Réinitialiser les tableaux en mémoire pour le nouveau plateau
        this.tabPnlCases          = new JPanel[this.nbLigne][this.nbColonne];
        this.tabLblTotauxLignes   = new JLabel[this.nbLigne];
        this.tabLblTotauxColonnes = new JLabel[this.nbColonne];

        // 3. Boucle de création (votre code actuel)
        for (int lig = 0; lig <= this.nbLigne; lig++)
        {
            for (int col = 0; col <= this.nbColonne; col++)
            {
                JPanel pnlCellule = new JPanel(new CardLayout());
                pnlCellule.setBorder(BorderFactory.createLineBorder(Color.BLACK));        
                pnlCellule.setBackground(Color.WHITE);

                JPanel pnlBrouillon = new JPanel();
                pnlBrouillon.setLayout(new GridLayout(3, 3));
                pnlBrouillon.setOpaque(false);

                for (int valeurLabel = 1; valeurLabel <= 9; valeurLabel++)
                {
                    // Ajout de SwingConstants.CENTER ici :
                    JLabel lblNombre = new JLabel("", SwingConstants.CENTER);
                    lblNombre.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
                    lblNombre.setForeground(ConstantesIHM.COULEUR_GRIS_BROUILLON);

                    pnlBrouillon.add(lblNombre);
                }

                if (lig < this.nbLigne && col < this.nbColonne)
                {    
                    JLabel lblNombre;
                    
                    if (this.ctrl.getNombreCasePlateau(lig , col ) != 0)
                    {    
                        // Ajout de SwingConstants.CENTER ici :
                        lblNombre = new JLabel("" + this.ctrl.getNombreCasePlateau(lig , col ), SwingConstants.CENTER);
                        lblNombre.setFont(new Font("Sans-Serif", Font.BOLD, 24));
                    }
                    else
                    {
                        // Ajout de SwingConstants.CENTER ici :
                        lblNombre = new JLabel("", SwingConstants.CENTER);
                        lblNombre.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
                    }

                    lblNombre.setOpaque(false); 
                    lblNombre        .setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));

                    pnlCellule       .add(lblNombre, "VALEUR");
                    pnlCellule       .add(pnlBrouillon, "BROUILLON");

                    this.tabPnlCases[lig][col] = pnlCellule;    
                    pnlCellule       .addMouseListener(new GereSouris(lig, col));

                    // L'ASTUCE : On donne le droit de recevoir au panneau ET au texte
                    GereDepot depot = new GereDepot(lig, col);
                    pnlCellule.setTransferHandler(depot);
                    lblNombre.setTransferHandler(depot);
                }
                else if (lig < this.nbLigne && col == this.nbColonne)
                {
                    JLabel lblTtlLigne  = new JLabel(String.valueOf(this.ctrl.getTotauxLignes(lig)), SwingConstants.CENTER);
                    
                    lblTtlLigne         .setFont(new Font("Sans-Serif", Font.BOLD, 24));
                    lblTtlLigne         .setBackground(ConstantesIHM.COULEUR_FOND);
                    
                    // On sauvegarde le label dans le tableau 
                    this.tabLblTotauxLignes[lig] = lblTtlLigne; 

                    pnlCellule          .setBackground(ConstantesIHM.COULEUR_FOND);
                    pnlCellule          .add(lblTtlLigne); // Plus besoin du nom "VALEUR" car il n'y a qu'une carte
                    pnlCellule          .setBorder(null);
                }
                else if (lig == this.nbLigne && col < this.nbColonne)
                {
                    JLabel lblTtlColonne = new JLabel(String.valueOf(this.ctrl.getTotauxColonnes(col)), SwingConstants.CENTER);
                    
                    lblTtlColonne        .setFont(new Font("Sans-Serif", Font.BOLD, 24));
                    lblTtlColonne        .setBackground(ConstantesIHM.COULEUR_FOND);

                    // NOUVEAU : On sauvegarde ce label
                    this.tabLblTotauxColonnes[col] = lblTtlColonne;

                    pnlCellule.setBackground(ConstantesIHM.COULEUR_FOND);
                    pnlCellule.add(lblTtlColonne); // Pareil, on l'ajoute simplement
                    pnlCellule.setBorder(null);
                }
                else
                {
                    pnlCellule.setBorder(null);
                    pnlCellule.setBackground(this.getBackground());
                }
                
                this.pnlPlateau.add(pnlCellule);         // Le panel qui affichera le plateau ajoute le pnlCellule
            }
        }
    }

    public void dessinerBoutons()
    {
        this.pnlNbres.removeAll(); // On efface les anciens boutons
        
        this.grpBtn = new ButtonGroup(); // On recrée un groupe vierge
        
        // On boucle de 1 à 9
        for (int valeurBouton = 1; valeurBouton <= 9; valeurBouton++)
        {   
            JToggleButton btnTmp = new JToggleButton(String.valueOf(valeurBouton));
            btnTmp.setFont(new Font("Sans-Serif", Font.BOLD, 18));
            btnTmp.setPreferredSize(new Dimension(tailleBoutonPave, tailleBoutonPave));
            btnTmp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            btnTmp.setFocusPainted(false);

            btnTmp.setTransferHandler(new TransferHandler() 
            {
                @Override
                public int getSourceActions(JComponent c) {
                    return TransferHandler.COPY; // On autorise la copie
                }
                @Override
                protected java.awt.datatransfer.Transferable createTransferable(JComponent c) {
                    // On fabrique un colis de type texte pur contenant le chiffre du bouton
                    return new java.awt.datatransfer.StringSelection(((JToggleButton) c).getText());
                }
            });

            btnTmp.addMouseMotionListener(new MouseMotionAdapter() 
            {
                public void mouseDragged(MouseEvent e)
                {
                    JComponent composant = (JComponent) e.getSource();
                    composant.getTransferHandler().exportAsDrag(composant, e, TransferHandler.COPY);
                }
            });

            btnTmp.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseEntered(MouseEvent e) 
                {
                    // On n'applique le survol que si le bouton n'est pas grisé (déjà placé) et n'est pas sélectionné
                    if (btnTmp.getBackground() != ConstantesIHM.COULEUR_GRIS_PLACE && !btnTmp.isSelected()) 
                    {
                        btnTmp.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) 
                {
                    // Pareil pour le retour au blanc
                    if (btnTmp.getBackground() != ConstantesIHM.COULEUR_GRIS_PLACE && !btnTmp.isSelected()) 
                    {
                        btnTmp.setBackground(Color.WHITE);
                    }
                }
            });

            // Si le nombre n'est pas déjà placé sur le plateau
            if (!this.ctrl.estPresent(valeurBouton))
            {    
                btnTmp.setBackground(Color.WHITE);
            }
            else // S'il est déjà sur le plateau d'origine
            {
                btnTmp.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE); // GRIS
            }
            
            // On ajoute le bouton au groupe et aux actions dans tous les cas
            this.grpBtn.add(btnTmp);
            btnTmp.addActionListener(this);
            this.pnlNbres.add(btnTmp);
        }

        // On force Swing à mettre à jour l'affichage de cette zone
        this.pnlNbres.revalidate();
        this.pnlNbres.repaint();
    }

    public void majPlateau()
    {
        // 1. Remettre les cases de jeu de la bonne couleur et actualiser le texte
        for (int lig = 0; lig < this.nbLigne; lig++)
        {
            for (int col = 0; col < this.nbColonne; col++)
            {
                Color couleurCase = Color.WHITE;

                // Si c'est la case cliquée par la souris, on l'affiche en bleu
                if (lig == this.ligSelectionne && col == this.colSelectionne) 
                {
                    couleurCase = ConstantesIHM.COULEUR_SELECTION;
                }

                this.tabPnlCases[lig][col].setBackground(couleurCase);

                // 1. On récupère le grand Label (qui est toujours le composant 0 de notre paquet)
                JLabel lbl = (JLabel) this.tabPnlCases[lig][col].getComponent(0);
                
                // 2. On récupère le "croupier" (le CardLayout) qui gère cette case précise
                CardLayout croupier = (CardLayout) this.tabPnlCases[lig][col].getLayout();
                
                // 3. On demande au métier s'il y a un vrai chiffre placé ici
                int vraiChiffre = this.ctrl.getNombreCasePlateau(lig, col);
                
                if (vraiChiffre == 0) 
                {
                    lbl.setText(""); 
                    // La case est vide : on dit au croupier d'afficher la mini-grille !
                    croupier.show(this.tabPnlCases[lig][col], "BROUILLON");
                } 
                else 
                {
                    lbl.setText(String.valueOf(vraiChiffre)); 
                    
                    if (!this.ctrl.getCasePlateau(lig, col).estModifiable()) 
                        lbl.setFont(new Font("Sans-Serif", Font.BOLD, 24));
                    else 
                        lbl.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
                    
                    // La case est remplie : on dit au croupier de cacher le brouillon et d'afficher le gros chiffre !
                    croupier.show(this.tabPnlCases[lig][col], "VALEUR");
                }
            }
        }

        // 2. Gestion de la coloration des Labels de totaux en mode Validation
        Color vertFonce = ConstantesIHM.COULEUR_VERT_VALIDE; 

        for (int lig = 0; lig < this.nbLigne; lig++)
        {
            if (this.modeValidation) 
            {
                if (this.ctrl.estLigneValide(lig)) { this.tabLblTotauxLignes[lig].setForeground(vertFonce); } 
                else                               { this.tabLblTotauxLignes[lig].setForeground(Color.RED); }
            } 
            else { this.tabLblTotauxLignes[lig].setForeground(null); } // Remet en noir si on annule la validation
        }

        for (int col = 0; col < this.nbColonne; col++)
        {
            if (this.modeValidation) 
            {
                if (this.ctrl.estColonneValide(col)) { this.tabLblTotauxColonnes[col].setForeground(vertFonce); } 
                else                                 { this.tabLblTotauxColonnes[col].setForeground(Color.RED); }
            } 
            else { this.tabLblTotauxColonnes[col].setForeground(null); } // Remet en noir si on annule la validation
        }

        this.btnAnnuler.setEnabled(this.ctrl.peutAnnuler());
        this.repaint();
    }

    public void majBoutons()
    {
        java.util.Enumeration<AbstractButton> elements = this.grpBtn.getElements();
        while (elements.hasMoreElements()) 
        {
            AbstractButton btn = elements.nextElement();
            int numero = Integer.parseInt(btn.getText());

            // On utilise notre variable 'btn' pour faire toutes les modifications
            if (this.ctrl.estPresent(numero))
                btn.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
            else
                btn.setBackground(Color.WHITE);

            btn.setEnabled(true);
        }
    }

    private void initBoutonIndice()
    {
        int difficulte = this.ctrl.getDifficulte();
        if      (difficulte == 0) this.nbIndicesRestants = 2;
        else if (difficulte == 1) this.nbIndicesRestants = 1;
        else                      this.nbIndicesRestants = 0;

        // On gère la couleur et l'activation du bouton Indice
        this.btnIndice.setText("  " + this.nbIndicesRestants); // Optionnel : met à jour le chiffre sur le bouton
        if (this.nbIndicesRestants > 0)
        {
            this.btnIndice.setBackground(ConstantesIHM.COULEUR_INDICE_ACTIVE);
            this.btnIndice.setEnabled(true);
        }
        else
        {
            this.btnIndice.setBackground(Color.WHITE);
            this.btnIndice.setEnabled(false); 
        }
    }

    private void initNbValidations()
    {
        this.nbValidationsMax = 3;

        this.nbValidationsRestantes = this.nbValidationsMax;
        this.majAffichageValidation();
    }

    private void majAffichageValidation()
    {
        this.lblValidation.setText("Nombre de validations restantes : " + this.nbValidationsRestantes);

        // Calcul du ratio pour les couleurs
        if (this.nbValidationsRestantes > 2) 
        {
            this.lblValidation.setForeground(new Color(0, 150, 0)); // VERT (loin de la défaite)
        } 
        else if (this.nbValidationsRestantes > 1) 
        {
            this.lblValidation.setForeground(new Color(255, 140, 0)); // ORANGE (attention)
        } 
        else 
        {
            this.lblValidation.setForeground(Color.RED); // ROUGE (au bord de la défaite)
        }
    }

    public void reset()
    {
        this.ligSelectionne = -1;
        this.colSelectionne = -1;
        this.modeValidation = false;
        this.modeBrouillon  = false;

        this.btnBrouillon.setBackground(Color.WHITE);
        this.btnActif = null;
        this.btnGomme.setEnabled(true);
        
        for (int lig = 0; lig < this.nbLigne; lig++)
        {
            for (int col = 0; col < this.nbColonne; col++)
            {
                // -- NOUVEAU : Nettoyage de la carte Brouillon --
                JPanel pnlBrouillon = (JPanel) tabPnlCases[lig][col].getComponent(1);

                for (int i = 0; i < 9; i++) 
                    ((JLabel) pnlBrouillon.getComponent(i)).setText("");
                
                // -- Suite du code existant --
                // On vérifie si la case est modifiable...
                 // On vérifie si la case est modifiable (on ne touche pas aux chiffres de départ)
                if (this.ctrl.getCasePlateau(lig, col).estModifiable())
                {
                    JLabel lblContenu = (JLabel) tabPnlCases[lig][col].getComponent(0);
                    String chiffreEfface = lblContenu.getText();

                    // Si la case n'est pas déjà vide
                    if (!chiffreEfface.equals("") && !chiffreEfface.equals("0"))
                    {
                        this.ctrl.effacerChiffre(lig, col);
                                
                        lblContenu.setText(""); // Chaîne vide pour masquer le chiffre
                        lblContenu.setForeground(null);

                        // On réactive le bouton correspondant dans le pavé numérique
                        if (this.grpBtn != null) 
                        {
                            java.util.Enumeration<AbstractButton> elements = this.grpBtn.getElements();
                            while (elements.hasMoreElements()) 
                            {
                                AbstractButton btn = elements.nextElement();
                                if (btn.getText().equals(chiffreEfface)) 
                                {
                                    btn.setBackground(Color.WHITE);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        this.majPlateau();
    }

    public void restart()
    {
        // On demande un tout nouveau plateau au métier via le contrôleur
        this.ctrl.resetJeu();

        // On remet nos variables de sélection à zéro
        this.ligSelectionne = -1;
        this.colSelectionne = -1;
        this.modeValidation = false;
        this.modeBrouillon  = false;

        this.btnBrouillon.setBackground(Color.WHITE);
        this.initBoutonIndice();
        this.initNbValidations();
        this.btnActif = null;
        this.btnGomme.setEnabled(true);
        
        // On redessine nos deux panneaux graphiques (qui vont s'auto-nettoyer)
        this.dessinerPlateau();
        this.dessinerBoutons();

        this.majPlateau();
    }


    // Classe interne permettant de gérer le clic de la souris pendant le Jeu
    private class GereSouris extends MouseAdapter
    {
        private int ligSouris;
        private int colSouris;

        public GereSouris(int lig, int col)
        {
            this.ligSouris = lig;
            this.colSouris = col;
        }

        public void mouseClicked(MouseEvent e)
        {
            if (PanelJeu.this.ctrl.estGagne()) 
            {
                return; // Stoppe l'exécution de la méthode ici
            }
            
            if (!PanelJeu.this.modeBrouillon) 
            {
                // Interception d'un échange
                if (PanelJeu.this.ligSelectionne != -1 && PanelJeu.this.btnActif == null)
                {
                    // Deuxième chiffre qu'on veut échanger
                    int valCliquee = PanelJeu.this.ctrl.getNombreCasePlateau(this.ligSouris, this.colSouris);
                    // Premier chiffre qu'on veut échanger
                    int valPremiereCase = PanelJeu.this.ctrl.getNombreCasePlateau(PanelJeu.this.ligSelectionne, PanelJeu.this.colSelectionne);
                    
                    boolean cibleModifiable = PanelJeu.this.ctrl.getCasePlateau(this.ligSouris, this.colSouris).estModifiable();
                    boolean sourceModifiable = PanelJeu.this.ctrl.getCasePlateau(PanelJeu.this.ligSelectionne, PanelJeu.this.colSelectionne).estModifiable();

                    // On exige que la case ait un chiffre (!= 0) et soit modifiables
                    if (/*valCliquee != 0 &&*/ valPremiereCase != 0 && cibleModifiable && sourceModifiable && 
                        (PanelJeu.this.ligSelectionne != this.ligSouris || PanelJeu.this.colSelectionne != this.colSouris))
                    {
                        // Action Métier
                        PanelJeu.this.ctrl.echangerNbr(PanelJeu.this.ligSelectionne, PanelJeu.this.colSelectionne, this.ligSouris, this.colSouris);
                        
                        PanelJeu.this.modeValidation = false;
                        
                        PanelJeu.this.ligSelectionne = -1;
                        PanelJeu.this.colSelectionne = -1;

                        /*this.ligSouris = -1;
                        this.colSouris = -1;*/

                        PanelJeu.this.majPlateau();
                        return; // ON S'ARRÊTE LÀ POUR NE PAS CASSER LA SUITE
                    }
                }
                
                // Sélection classique
                if (PanelJeu.this.ligSelectionne == this.ligSouris && PanelJeu.this.colSelectionne == this.colSouris) 
                {
                    PanelJeu.this.ligSelectionne = -1;
                    PanelJeu.this.colSelectionne = -1;
                } 
                else 
                {
                    PanelJeu.this.ligSelectionne = this.ligSouris;
                    PanelJeu.this.colSelectionne = this.colSouris;
                }

                // Placement d'un chiffre sur le plateau
                if (PanelJeu.this.btnActif != null 
                    && PanelJeu.this.ctrl.getNombreCasePlateau(this.ligSouris, this.colSouris) == 0
                    && PanelJeu.this.ctrl.getCasePlateau(this.ligSouris, this.colSouris).estModifiable())
                {
                    int valeur = Integer.parseInt(PanelJeu.this.btnActif.getText());

                    // On refuse le placement si le chiffre fait partie de ceux d'origine
                    if (PanelJeu.this.ctrl.estPresent(valeur)) 
                    {
                        PanelJeu.this.btnActif.setSelected(false);
                        PanelJeu.this.btnActif.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
                        PanelJeu.this.btnActif = null;
                    }
                    else 
                    {
                        JLabel lblContenu = (JLabel) tabPnlCases[this.ligSouris][this.colSouris].getComponent(0);
                        //lblContenu.setText(String.valueOf(valeur));
                        lblContenu.setForeground(Color.BLACK); 

                        PanelJeu.this.ctrl.setNbrCase(this.ligSouris, this.colSouris, valeur);
                        
                        PanelJeu.this.btnActif.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
                        PanelJeu.this.grpBtn.clearSelection(); 
                        PanelJeu.this.btnActif = null;

                        PanelJeu.this.ligSelectionne = -1;
                        PanelJeu.this.colSelectionne = -1;

                        PanelJeu.this.modeValidation = false; 
                    }
                }

                PanelJeu.this.majPlateau();
            }
            else
            {
                // 1. On vérifie qu'un bouton chiffre est bien sélectionné, que la case est modifiable et qu'elle est VIDE !
                if (PanelJeu.this.btnActif != null 
                    && PanelJeu.this.ctrl.getCasePlateau(this.ligSouris, this.colSouris).estModifiable()
                    && PanelJeu.this.ctrl.getNombreCasePlateau(this.ligSouris, this.colSouris) == 0)
                {
                    int valeur = Integer.parseInt(PanelJeu.this.btnActif.getText());

                    // NOUVEAU : On refuse si le chiffre est déjà sur le plateau d'origine
                    if (PanelJeu.this.ctrl.estPresent(valeur)) 
                    {
                        PanelJeu.this.btnActif.setSelected(false);
                        PanelJeu.this.btnActif.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
                        PanelJeu.this.btnActif = null;
                    }
                    else 
                    {
                        JPanel pnlBrouillon = (JPanel) tabPnlCases[this.ligSouris][this.colSouris].getComponent(1);
                        JLabel lblPetitNum = (JLabel) pnlBrouillon.getComponent(valeur - 1);

                        if (lblPetitNum.getText().equals("")) lblPetitNum.setText(String.valueOf(valeur)); 
                        else                                  lblPetitNum.setText(""); 
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) 
        {
            // On ne met la couleur de survol que si la case n'est pas celle actuellement sélectionnée
            if (PanelJeu.this.ligSelectionne != this.ligSouris || PanelJeu.this.colSelectionne != this.colSouris) 
            {
                PanelJeu.this.tabPnlCases[this.ligSouris][this.colSouris].setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) 
        {
            // Quand la souris part, on remet en blanc QUE si la case n'est pas sélectionnée
            if (PanelJeu.this.ligSelectionne != this.ligSouris || PanelJeu.this.colSelectionne != this.colSouris) 
            {
                PanelJeu.this.tabPnlCases[this.ligSouris][this.colSouris].setBackground(Color.WHITE);
            }
        }
    }

    private class GereDepot extends TransferHandler
    {
        private int ligCible;
        private int colCible;

        public GereDepot(int lig, int col)
        {
            this.ligCible = lig;
            this.colCible = col;
        }

        // Cette méthode vérifie si la case accepte le chiffre qui la survole
        @Override
        public boolean canImport(TransferSupport support)
        {
            // On vérifie que c'est bien une action de "déposer"
            if (!support.isDrop()) return false;

            // On vérifie que l'étiquette du colis est bien du texte
            if (!support.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor)) return false;

            // On accepte si la case cible est modifiable
            return PanelJeu.this.ctrl.getCasePlateau(ligCible, colCible).estModifiable();
        }

        // Cette méthode est appelée quand le joueur lâche le clic sur la case
        @Override
        public boolean importData(TransferSupport support)
        {
            if (!canImport(support)) return false;

            try 
            {
                // On récupère le texte transporté
                String donnees = (String) support.getTransferable().getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
                int valeurAjoutee = Integer.parseInt(donnees);

                // Si le chiffre est d'origine, on refuse
                if (PanelJeu.this.ctrl.estPresent(valeurAjoutee)) return false;

                // Interception si le Mode Brouillon est activé
                if (PanelJeu.this.modeBrouillon) 
                {
                    JPanel pnlBrouillon = (JPanel) tabPnlCases[ligCible][colCible].getComponent(1);
                    JLabel lblPetitNum = (JLabel) pnlBrouillon.getComponent(valeurAjoutee - 1);
                    
                    if (lblPetitNum.getText().equals("")) lblPetitNum.setText(String.valueOf(valeurAjoutee));
                    else                                  lblPetitNum.setText("");

                    PanelJeu.this.majPlateau();
                    return true; // On s'arrête là pour ne pas placer le gros chiffre
                }

                // Logique de placement classique (similaire à GereSouris)
                // Si la case cible contenait déjà un chiffre, on réactive son ancien bouton
                JLabel lblContenu = (JLabel) tabPnlCases[ligCible][colCible].getComponent(0);
                String chiffreEcrase = lblContenu.getText();
                
                if (!chiffreEcrase.equals("") && !chiffreEcrase.equals("0")) 
                {
                    if (PanelJeu.this.grpBtn != null) 
                    {
                        java.util.Enumeration<AbstractButton> elements = PanelJeu.this.grpBtn.getElements();
                        while (elements.hasMoreElements()) 
                        {
                            AbstractButton btn = elements.nextElement();
                            if (btn.getText().equals(chiffreEcrase)) 
                            {
                                btn.setBackground(Color.WHITE);
                                break;
                            }
                        }
                    }
                }

                // On met à jour le métier et l'interface
                PanelJeu.this.ctrl.setNbrCase(ligCible, colCible, valeurAjoutee);
                lblContenu.setForeground(Color.BLACK);

                // On grise le bouton qu'on vient de glisser
                if (PanelJeu.this.grpBtn != null) 
                {
                    java.util.Enumeration<AbstractButton> elements = PanelJeu.this.grpBtn.getElements();
                    while (elements.hasMoreElements()) 
                    {
                        AbstractButton btn = elements.nextElement();
                        if (btn.getText().equals(String.valueOf(valeurAjoutee))) 
                        {
                            btn.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
                            PanelJeu.this.grpBtn.clearSelection();
                            break;
                        }
                    }
                }

                // Nettoyage de fin
                PanelJeu.this.ligSelectionne = -1;
                PanelJeu.this.colSelectionne = -1;
                PanelJeu.this.modeValidation = false;
                PanelJeu.this.btnActif = null;
                
                PanelJeu.this.majPlateau();
                
                return true;
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                return false;
            }
        }
    }
}