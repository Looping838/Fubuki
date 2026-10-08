package ihm;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;

public class PanelJeu extends JPanel implements ActionListener
{
    public static final Color          COULEUR_FOND        = new Color(245, 245, 220);
    public static final Color          COULEUR_SELECTION   = new Color(168, 168, 168);
    public static final Color          COULEUR_VERT_VALIDE = new Color(0  , 150  , 0);
    public static final Color          COULEUR_GRIS_PLACE  = new Color(110, 110, 110);
    
    private JPanel[][]          tabPnlCases;

    private Controleur          ctrl;
    private FrameJeu            frame;
    private int                 indice;

    private int                 nbLigne;          // Permet de stocker le nombre de lignes   du plateau
    private int                 nbColonne;        // Permet de stocker le nombre de colonnes du plateau

    private int                 ligSelectionne;   // Permet de stocker la ligne   sélectionnée lors du jeu
    private int                 colSelectionne;   // Permet de stocker la colonne sélectionnée lors du jeu

    private JPanel              pnlPlateau;
    private JPanel              pnlCentre;
    private JPanel              pnlNbres;

    private JLabel[]            tabLblTotauxLignes;
    private JLabel[]            tabLblTotauxColonnes;

    private JButton             btnValide;
    private JButton             btnRestart;
    private JButton             btnAccueil;
    private JButton             btnAnnuler;

    private ButtonGroup         grpBtn;

    private JToggleButton       btnActif;
    private JButton             btnGomme;

    private JRadioButton        chGomme;

    private boolean             modeValidation = false;

    public PanelJeu(Controleur ctrl, FrameJeu frame, int indice)
    {
        // Forcer la couleur grise quand on sélectionne un bouton de pnlNbres
        UIManager.put("ToggleButton.select", COULEUR_SELECTION);
        // Forcer la couleur noire du lbl quand le bouton est désactivé pour être plus lisible
        UIManager.put("ToggleButton.disabledText", Color.BLACK);
        
        this.setLayout(new BorderLayout());
        this.setBackground(COULEUR_FOND);

        
        /*-------------------------------*/
        /*   Création des composants     */
        /*-------------------------------*/

        // 1. On crée le relief 3D
        javax.swing.border.Border relief = BorderFactory.createRaisedBevelBorder();

        // 2. On crée une marge invisible (Haut, Gauche, Bas, Droite)
        javax.swing.border.Border marge         = BorderFactory.createEmptyBorder(10, 25, 10, 25);
        javax.swing.border.Border margeGomme    = BorderFactory.createEmptyBorder(0, 10, 0, 10);

        this.ctrl           = ctrl;
        this.frame          = frame;
        this.indice         = indice;

        this.nbLigne        = this.ctrl.getNbLigne();
        this.nbColonne      = this.ctrl.getNbColonne();

        this.tabPnlCases    = new JPanel[this.nbLigne][this.nbColonne];

        this.tabLblTotauxLignes     = new JLabel[this.nbLigne];
        this.tabLblTotauxColonnes   = new JLabel[this.nbColonne];
        
        this.ligSelectionne = -1;
        this.colSelectionne = -1;

        // Panel qui contiendra pnlBandeau
        JPanel pnlHaut = new JPanel();
        pnlHaut.setLayout(new BorderLayout());
        pnlHaut.setOpaque(false);

        // Panel qui contiendra le JLabel du bandeau
        JPanel pnlBandeau = new JPanel();
        pnlBandeau.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        pnlBandeau.setOpaque(false);

        JPanel pnlGomme = new JPanel();
        pnlGomme.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlGomme.setOpaque(false);

        // Panel qui contiendra pnlPlateau
        this.pnlCentre = new JPanel();
        this.pnlCentre.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));
        this.pnlCentre.setOpaque(false);
        
        // Panel qui affichera le Plateau
        this.pnlPlateau = new JPanel();
        this.pnlPlateau.setLayout(new GridLayout(this.nbLigne + 1, this.nbColonne + 1, 2, 2));
        this.pnlPlateau.setPreferredSize(new Dimension(600, 600));
        this.pnlPlateau.setBackground(COULEUR_FOND);

        JPanel pnlDroit     = new JPanel();
        pnlDroit.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 140));
        pnlDroit.setOpaque(false);

        // Panel qui contiendra pnlNbres
        JPanel pnlBas       = new JPanel();
        pnlBas.setLayout(new BorderLayout());
        pnlBas.setOpaque(false);

        JPanel pnlBoutons   = new JPanel();
        pnlBoutons.setLayout(new FlowLayout(FlowLayout.CENTER , 20, 20));
        pnlBoutons.setOpaque(false);

        // Panel qui affichera les nombres à placer
        this.pnlNbres   = new JPanel();
        this.pnlNbres   .setLayout(new GridLayout(3, 3, 10, 10));
        this.pnlNbres   .setBackground(COULEUR_FOND);

        // Crée une ligne séparatrice à l'aide de JSeparator
        JSeparator separateurHaut = new JSeparator(SwingConstants.HORIZONTAL);
        separateurHaut.setForeground(new Color(150, 150, 150));
        separateurHaut.setBackground(new Color(0, 0, 0, 0));

        JSeparator separateurBas = new JSeparator(SwingConstants.HORIZONTAL);
        separateurBas.setForeground(new Color(150, 150, 150));
        separateurBas.setBackground(new Color(0, 0, 0, 0));

        /*-----------------------------*/

        this.btnGomme   = new JButton("");
        this.btnGomme   .setBackground(Color.WHITE);
        this.btnGomme   .setBorder(BorderFactory.createCompoundBorder(relief, margeGomme));
        this.btnGomme   .setFocusPainted(false);

        // Ajout de l'icône gomme.png au bouton btnGomme en redimensionnant l'image au préalable
		this.btnGomme.setIcon(new ImageIcon(this.creerImages("./images/icones/gomme.png", 30, 30)));

        this.chGomme    = new JRadioButton("Tout Gommer", false);
        this.chGomme    .setOpaque(false);
        this.chGomme    .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.chGomme    .setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Parcours pour dessiner le plateau
        this.dessinerPlateau();   

        this.btnValide      = new JButton("Valider");
        this.btnValide      .setBackground(Color.WHITE);
        this.btnValide      .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnValide      .setPreferredSize(new Dimension(250, 50));
        this.btnValide      .setIcon(new ImageIcon(this.creerImages("./images/icones/valide.png", 30, 30)));
        this.btnValide      .setBorder(BorderFactory.createCompoundBorder(relief, marge));
        this.btnValide      .setFocusPainted(false);

        this.btnRestart     = new JButton("Recommencer");
        this.btnRestart     .setBackground(Color.WHITE);
        this.btnRestart     .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnRestart     .setPreferredSize(new Dimension(250, 50));
        this.btnRestart     .setIcon(new ImageIcon(this.creerImages("./images/icones/restart.png", 30, 25)));
        this.btnRestart     .setBorder(BorderFactory.createCompoundBorder(relief, marge));
        this.btnRestart     .setFocusPainted(false);

        this.btnAnnuler     = new JButton("Annuler");
        this.btnAnnuler     .setBackground(Color.WHITE);
        this.btnAnnuler     .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnAnnuler     .setPreferredSize(new Dimension(250, 50));
        this.btnAnnuler     .setIcon(new ImageIcon(this.creerImages("./images/icones/annuler.png", 30, 25)));
        this.btnAnnuler     .setBorder(BorderFactory.createCompoundBorder(relief, marge));
        this.btnAnnuler     .setFocusPainted(false);

        this.btnAccueil     = new JButton("Retourner à l'accueil");
        this.btnAccueil     .setBackground(Color.WHITE);
        this.btnAccueil     .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnAccueil     .setPreferredSize(new Dimension(250, 50));
        this.btnAccueil     .setIcon(new ImageIcon(this.creerImages("./images/icones/accueil.png", 30, 25)));
        this.btnAccueil     .setBorder(BorderFactory.createCompoundBorder(relief, marge));
        this.btnAccueil     .setFocusPainted(false);

        pnlBoutons.add(this.btnValide );
        pnlBoutons.add(this.btnRestart);
        pnlBoutons.add(this.btnAnnuler);
        pnlBoutons.add(this.btnAccueil);


        this.dessinerBoutons();    

        /*-------------------------------*/
        /* Positionnement des composants */
        /*-------------------------------*/

        pnlGomme    .add(this.chGomme);
        pnlGomme    .add(this.btnGomme        );
        
        pnlHaut     .add(pnlGomme      , BorderLayout.EAST  );
        pnlHaut     .add(separateurHaut , BorderLayout.SOUTH );

        pnlBas      .add(separateurBas  , BorderLayout.NORTH );
        pnlBas      .add(pnlBoutons       , BorderLayout.CENTER);

        pnlDroit    .add(pnlNbres   , BorderLayout.CENTER);
        //pnlDroit    .add(pnlBoutons , BorderLayout.SOUTH );

        this.pnlCentre.add(this.pnlPlateau);
        //this.pnlCentre.add(pnlNbres);

        this.add(pnlHaut        , BorderLayout.NORTH );
        this.add(this.pnlCentre , BorderLayout.CENTER);
        this.add(pnlDroit       , BorderLayout.EAST  );
        this.add(pnlBas         , BorderLayout.SOUTH );
        
        /* ----------------------------- */
        /* Activation des Composants     */
        /* ----------------------------- */
        this.btnGomme   .addActionListener(this);
        this.btnValide  .addActionListener(this);
        this.btnRestart .addActionListener(this);
        this.btnAnnuler .addActionListener(this);
        this.btnAccueil .addActionListener(this);

        // Pour le clic dans le vide (désélectionne la case en cours)
        MouseAdapter clicDansLeVide = new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                PanelJeu.this.ligSelectionne = -1;
                PanelJeu.this.colSelectionne = -1;
                PanelJeu.this.majPlateau();
            }
        };

        // On l'applique au panneau principal et au panneau central
        this.addMouseListener(clicDansLeVide);
        this.pnlCentre.addMouseListener(clicDansLeVide);
        
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() instanceof JToggleButton)
        {
            this.btnActif = (JToggleButton) e.getSource();
        }

        if (e.getSource() == this.btnGomme)
        {
            // --- MODE TOUT GOMMER ---
            if (this.chGomme.isSelected())
            {
                for (int lig = 0; lig < this.nbLigne; lig++)
                {
                    for (int col = 0; col < this.nbColonne; col++)
                    {
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
                this.modeValidation = false;
                this.majPlateau();
            }
            // --- MODE GOMME CLASSIQUE (Une seule case) ---
            else
            {
                if (this.ligSelectionne != -1 && this.colSelectionne != -1)
                {
                    JLabel lblContenu = (JLabel) tabPnlCases[this.ligSelectionne][this.colSelectionne].getComponent(0);
                    String chiffreEfface = lblContenu.getText();
                    
                    if (this.ctrl.getCasePlateau(this.ligSelectionne, this.colSelectionne).estModifiable())
                    {
                        this.ctrl.effacerChiffre(this.ligSelectionne, this.colSelectionne);
                    
                        lblContenu.setText(""); // J'ai remplacé votre "0" par une chaîne vide pour que la case soit vierge
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
                    this.majPlateau();
                }
            }
        }

        if (e.getSource() == this.btnValide)
        {
            this.modeValidation = true; // On active l'affichage rouge/vert
            this.ligSelectionne = -1;   // Optionnel : on enlève la sélection bleue
            this.colSelectionne = -1;

            this.majPlateau();

            if (this.ctrl.estGagne()) 
            {
                this.btnGomme.setEnabled(false);

                java.util.Enumeration<AbstractButton> elements = this.grpBtn.getElements();
                while (elements.hasMoreElements()) 
                {
                    AbstractButton btn = elements.nextElement();

                    btn.setEnabled(false);
                }

                if (this.ctrl.estGagne()) 
                {
                    this.frame.afficherPanelVictoire();
                }
            }
        }

        if (e.getSource() == this.btnAccueil)
        {
            this.frame.setPnl(this.frame.getPnl(this.indice-1));
        }

        if (e.getSource() == this.btnRestart)
        {
            this.restart();
        }
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
                JPanel pnlCellule = new JPanel(new GridBagLayout());
                pnlCellule.setBorder(BorderFactory.createLineBorder(Color.BLACK));        
                pnlCellule.setBackground(Color.WHITE);
                if (lig < this.nbLigne && col < this.nbColonne)
                {    
                    JLabel lblNombre;
                    
                    if (this.ctrl.getNombreCasePlateau(lig , col ) != 0)
                    {    
                        lblNombre = new JLabel("" + this.ctrl.getNombreCasePlateau(lig , col ));
                        lblNombre.setFont(new Font("Sans-Serif", Font.BOLD, 24));
                    }
                    else
                    {
                        lblNombre = new JLabel(""/* + this.ctrl.getNombreCasePlateau(lig , col )*/);
                        lblNombre.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
                    }

                    lblNombre        .setOpaque(false);   // Transparent par défaut
                    lblNombre        .setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));

                    pnlCellule       .add(lblNombre);
                    
                    this.tabPnlCases[lig][col] = pnlCellule;    // On ajoute pnlCellule au tableau de JPanel
                    pnlCellule       .addMouseListener(new GereSouris(lig, col));
                }
                else if (lig < this.nbLigne && col == this.nbColonne)
                {
                    JLabel lblTtlLigne  = new JLabel(String.valueOf(this.ctrl.getTotauxLignes(lig)));
                    lblTtlLigne         .setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
                    lblTtlLigne         .setFont(new Font("Sans-Serif", Font.BOLD, 24));
                    lblTtlLigne         .setBackground(COULEUR_FOND);
                    
                    // On sauvegarde le label dans le tableau 
                    this.tabLblTotauxLignes[lig] = lblTtlLigne; 

                    pnlCellule          .setBackground(COULEUR_FOND);
                    pnlCellule          .add(lblTtlLigne);
                    pnlCellule          .setBorder(null);
                }
                else if (lig == this.nbLigne && col < this.nbColonne)
                {
                    JLabel lblTtlColonne = new JLabel(String.valueOf(this.ctrl.getTotauxColonnes(col)));
                    lblTtlColonne        .setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
                    lblTtlColonne        .setFont(new Font("Sans-Serif", Font.BOLD, 24));
                    lblTtlColonne        .setBackground(COULEUR_FOND);

                    // NOUVEAU : On sauvegarde ce label
                    this.tabLblTotauxColonnes[col] = lblTtlColonne;

                    pnlCellule           .setBackground(COULEUR_FOND);
                    pnlCellule.add(lblTtlColonne);
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
        
        // On boucle simplement de 1 à 9
        for (int valeurBouton = 1; valeurBouton <= 9; valeurBouton++)
        {   
            JToggleButton btnTmp = new JToggleButton(String.valueOf(valeurBouton));
            btnTmp.setFont(new Font("Sans-Serif", Font.BOLD, 18));
            btnTmp.setPreferredSize(new Dimension(100, 90));
            btnTmp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            btnTmp.setFocusPainted(false);

            // Si le nombre n'est pas déjà placé sur le plateau
            if (!this.ctrl.estPresent(valeurBouton))
            {    
                btnTmp.setBackground(Color.WHITE);
            }
            else // S'il est déjà sur le plateau d'origine
            {
                btnTmp.setBackground(COULEUR_GRIS_PLACE); // GRIS
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
        // 1. Remettre les cases de jeu de la bonne couleur (Blanc ou Bleu)
        for (int lig = 0; lig < this.nbLigne; lig++)
        {
            for (int col = 0; col < this.nbColonne; col++)
            {
                Color couleurCase = Color.WHITE;

                // Si c'est la case cliquée par la souris, on l'affiche en bleu
                if (lig == this.ligSelectionne && col == this.colSelectionne) 
                {
                    couleurCase = COULEUR_SELECTION;
                }

                this.tabPnlCases[lig][col].setBackground(couleurCase);

                // Ajuste la couleur du texte (blanc sur fond bleu, noir sinon)
                JLabel lbl = (JLabel) this.tabPnlCases[lig][col].getComponent(0);
                //lbl.setForeground(null);
            }
        }

        // 2. Gestion de la coloration des Labels de totaux en mode Validation
        Color vertFonce = COULEUR_VERT_VALIDE; 

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

        this.repaint();
    }

    public void reset()
    {
        // On demande un tout nouveau plateau au métier via le contrôleur
        this.ctrl.resetJeu();

        // On remet nos variables de sélection à zéro
        this.ligSelectionne = -1;
        this.colSelectionne = -1;
        this.modeValidation = false;
        this.btnActif = null;
        this.btnGomme.setEnabled(true);
        
        // On redessine nos deux panneaux graphiques (qui vont s'auto-nettoyer)
        this.dessinerPlateau();
        this.dessinerBoutons();

        this.majPlateau();
    }

    public void restart()
    {
        this.ligSelectionne = -1;
        this.colSelectionne = -1;
        this.modeValidation = false;
        this.btnActif = null;
        this.btnGomme.setEnabled(true);
        
        for (int lig = 0; lig < this.nbLigne; lig++)
        {
            for (int col = 0; col < this.nbColonne; col++)
            {
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

    public Image creerImages (String chemin, int longueur, int largeur)
    {
        ImageIcon   imgOriginale;
        Image       imgRedimensionnee;

        imgOriginale        = new ImageIcon(chemin);
        imgRedimensionnee   = imgOriginale.getImage().getScaledInstance(longueur, largeur, Image.SCALE_SMOOTH);
		
        return imgRedimensionnee;
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

            // Interception d'un échange
            // Si une case est déjà sélectionnée ET qu'on n'a pas de bouton pavé actif
            if (PanelJeu.this.ligSelectionne != -1 && PanelJeu.this.btnActif == null)
            {
                int valCliquee = PanelJeu.this.ctrl.getNombreCasePlateau(this.ligSouris, this.colSouris);
                
                // Si la case cliquée a un chiffre, est modifiable, et n'est pas la case déjà sélectionnée
                if (valCliquee != 0 && 
                    PanelJeu.this.ctrl.getCasePlateau(this.ligSouris, this.colSouris).estModifiable() && 
                    (PanelJeu.this.ligSelectionne != this.ligSouris || PanelJeu.this.colSelectionne != this.colSouris))
                {
                    // Action Métier
                    PanelJeu.this.ctrl.echangerNbr(PanelJeu.this.ligSelectionne, PanelJeu.this.colSelectionne, this.ligSouris, this.colSouris);

                    // Action Graphique
                    JLabel lblContenu1 = (JLabel) tabPnlCases[PanelJeu.this.ligSelectionne][PanelJeu.this.colSelectionne].getComponent(0);
                    JLabel lblContenu2 = (JLabel) tabPnlCases[this.ligSouris][this.colSouris].getComponent(0);

                    String tmp = lblContenu1.getText();
                    lblContenu1.setText(lblContenu2.getText());
                    lblContenu2.setText(tmp);

                    // Fin de l'échange
                    PanelJeu.this.ligSelectionne = -1;
                    PanelJeu.this.colSelectionne = -1;
                    PanelJeu.this.modeValidation = false;
                    
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
                    PanelJeu.this.btnActif.setBackground(COULEUR_GRIS_PLACE);
                    PanelJeu.this.btnActif = null;
                }
                else 
                {
                    JLabel lblContenu = (JLabel) tabPnlCases[this.ligSouris][this.colSouris].getComponent(0);
                    lblContenu.setText(String.valueOf(valeur));
                    lblContenu.setForeground(Color.BLACK); 

                    PanelJeu.this.ctrl.setNbrCase(this.ligSouris, this.colSouris, valeur);
                    
                    PanelJeu.this.btnActif.setBackground(COULEUR_GRIS_PLACE);
                    PanelJeu.this.grpBtn.clearSelection(); 
                    PanelJeu.this.btnActif = null;

                    PanelJeu.this.ligSelectionne = -1;
                    PanelJeu.this.colSelectionne = -1;

                    PanelJeu.this.modeValidation = false; 
                }
            }

            PanelJeu.this.majPlateau();
        }
    }
}