package ihm;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PanelJeu extends JPanel implements ActionListener
{
    public static final Color          COULEUR_FOND         = new Color(245, 245, 220);
    public static final Color          COULEUR_SELECTION    = new Color(168, 168, 168);
    public static final Color          COULEUR_VERT_VALIDE  = new Color(0  , 150  , 0);
    public static final Color          COULEUR_GRIS_PLACE   = new Color(110, 110, 110);

    public static final Border         MARGE_GOMME          = BorderFactory.createEmptyBorder(2, 15, 2, 15);  // Marge invisible (H, G, B, D)
    
    private JPanel[][]          tabPnlCases;

    private Controleur          ctrl;
    private FrameJeu            frame;
    private int                 indice;

    private Dimension           tailleEcran;
    private int                 taillePlateau;
    private int                 tailleBoutonPave;

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
    private JButton             btnReset;
    private JButton             btnRestart;
    private JButton             btnAccueil;
    private JButton             btnAnnuler;

    private ButtonGroup         grpBtn;

    private JToggleButton       btnActif;
    private JButton             btnGomme;

    //private JRadioButton        chGomme;

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

        // Récupère la taille de l'écran de l'utilisateur
        this.tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        // On décide que le plateau prendra par exemple 55% de la hauteur de l'écran
        this.taillePlateau = (int) (tailleEcran.height * 0.55);

        // On calcule la taille des boutons du pavé numérique proportionnellement
        this.tailleBoutonPave = (int) (taillePlateau * 0.15);

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
        this.pnlCentre = new JPanel(new GridBagLayout());
        this.pnlCentre.setOpaque(false);

        // Création des règles de placement
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Panel qui affichera le Plateau
        this.pnlPlateau = new JPanel();
        this.pnlPlateau.setLayout(new GridLayout(this.nbLigne + 1, this.nbColonne + 1, 2, 2));
        this.pnlPlateau.setPreferredSize(new Dimension(600, 600));
        this.pnlPlateau.setBackground(COULEUR_FOND);

        JPanel pnlDroit     = new JPanel();
        pnlDroit.setLayout(new GridBagLayout());
        pnlDroit.setOpaque(false);
        pnlDroit.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

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
        this.btnGomme   .setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, MARGE_GOMME));
        this.btnGomme   .setFocusPainted(false);

        // Ajout de l'icône gomme.png au bouton btnGomme en redimensionnant l'image au préalable
		this.btnGomme.setIcon(new ImageIcon(this.creerImages("./images/icones/gomme.png", 30, 30)));

        /*this.chGomme    = new JRadioButton("Tout Gommer", false);
        this.chGomme    .setOpaque(false);
        this.chGomme    .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.chGomme    .setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.chGomme    .setFocusPainted(false);*/

        // Parcours pour dessiner le plateau
        this.dessinerPlateau();   

        this.btnValide      = new JButton("Valider");
        this.btnValide      .setBackground(Color.WHITE);
        this.btnValide      .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnValide      .setPreferredSize(new Dimension(tailleEcran.width/6, 50));
        this.btnValide      .setIcon(new ImageIcon(this.creerImages("./images/icones/valide.png", 30, 30)));
        this.btnValide      .setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, PanelAccueil.MARGE));
        this.btnValide      .setFocusPainted(false);

        this.btnReset       = new JButton("Réintialiser le plateau");
        this.btnReset       .setBackground(Color.WHITE);
        this.btnReset       .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnReset       .setPreferredSize(new Dimension(tailleEcran.width/6, 50));
        this.btnReset       .setIcon(new ImageIcon(this.creerImages("./images/icones/reset.png", 30, 25)));
        this.btnReset       .setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, PanelAccueil.MARGE));
        this.btnReset       .setFocusPainted(false);

        this.btnRestart       = new JButton("Recommencer une partie");
        this.btnRestart       .setBackground(Color.WHITE);
        this.btnRestart       .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnRestart       .setPreferredSize(new Dimension(tailleEcran.width/6, 50));
        this.btnRestart       .setIcon(new ImageIcon(this.creerImages("./images/icones/restart.png", 30, 25)));
        this.btnRestart       .setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, PanelAccueil.MARGE));
        this.btnRestart       .setFocusPainted(false);

        this.btnAnnuler     = new JButton("Annuler");
        this.btnAnnuler     .setBackground(Color.WHITE);
        this.btnAnnuler     .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnAnnuler     .setPreferredSize(new Dimension(tailleEcran.width/6, 50));
        this.btnAnnuler     .setIcon(new ImageIcon(this.creerImages("./images/icones/annuler.png", 30, 25)));
        this.btnAnnuler     .setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, PanelAccueil.MARGE));
        this.btnAnnuler     .setFocusPainted(false);

        this.btnAccueil     = new JButton("Retourner à l'accueil");
        this.btnAccueil     .setBackground(Color.WHITE);
        this.btnAccueil     .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnAccueil     .setPreferredSize(new Dimension(tailleEcran.width/6, 50));
        this.btnAccueil     .setIcon(new ImageIcon(this.creerImages("./images/icones/accueil.png", 30, 25)));
        this.btnAccueil     .setBorder(BorderFactory.createCompoundBorder(PanelAccueil.RELIEF, PanelAccueil.MARGE));
        this.btnAccueil     .setFocusPainted(false);

        pnlBoutons.add(this.btnValide );
        pnlBoutons.add(this.btnReset);
        pnlBoutons.add(this.btnRestart);
        pnlBoutons.add(this.btnAnnuler);
        pnlBoutons.add(this.btnAccueil);

        this.dessinerBoutons();    

        /*-------------------------------*/
        /* Positionnement des composants */
        /*-------------------------------*/

        //pnlGomme    .add(this.chGomme);
        pnlGomme    .add(this.btnGomme        );
        
        pnlHaut     .add(pnlGomme      , BorderLayout.EAST  );
        pnlHaut     .add(separateurHaut , BorderLayout.SOUTH );

        pnlBas      .add(separateurBas  , BorderLayout.NORTH );
        pnlBas      .add(pnlBoutons       , BorderLayout.CENTER);

        gbc.anchor = GridBagConstraints.NORTH;
        
        // 1ère règle : Le plateau (Colonne 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 100); // Ajoute une PanelAccueil.MARGE de 60 pixels à droite du plateau
        this.pnlCentre.add(this.pnlPlateau, gbc);

        // 2ème règle : Le pavé numérique (Colonne 1)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0); // Pas de PanelAccueil.MARGE supplémentaire
        this.pnlCentre.add(this.pnlNbres, gbc);

        this.add(pnlHaut        , BorderLayout.NORTH );
        this.add(this.pnlCentre , BorderLayout.CENTER);
        this.add(pnlDroit       , BorderLayout.EAST  );
        this.add(pnlBas         , BorderLayout.SOUTH );
        
        /* ----------------------------- */
        /* Activation des Composants     */
        /* ----------------------------- */
        this.btnGomme   .addActionListener(this);
        this.btnValide  .addActionListener(this);
        this.btnReset   .addActionListener(this);
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
           /*// --- MODE TOUT GOMMER ---
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
                */if (this.ligSelectionne != -1 && this.colSelectionne != -1)
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
                    this.ligSelectionne = -1;
                    this.colSelectionne = -1;
                    
                    this.majPlateau();
                }
            //}
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
            this.frame.setPnl(this.frame.getPnl(0));
        }

        if (e.getSource() == this.btnReset)
        {
            this.reset();
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
                    
                    this.tabPnlCases[lig][col] = pnlCellule;    
                    pnlCellule       .addMouseListener(new GereSouris(lig, col));

                    // L'ASTUCE : On donne le droit de recevoir au panneau ET au texte
                    GereDepot depot = new GereDepot(lig, col);
                    pnlCellule.setTransferHandler(depot);
                    lblNombre.setTransferHandler(depot);
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

                    pnlCellule.setBackground(COULEUR_FOND);
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
            btnTmp.setPreferredSize(new Dimension(tailleBoutonPave, tailleBoutonPave));
            btnTmp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            btnTmp.setFocusPainted(false);

            // Le nouveau TransferHandler 100% sécurisé
            btnTmp.setTransferHandler(new TransferHandler() {
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
        // 1. Remettre les cases de jeu de la bonne couleur et ACTUALISER LE TEXTE
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

                // On récupère le Label de la case
                JLabel lbl = (JLabel) this.tabPnlCases[lig][col].getComponent(0);
                
                // LA CORRECTION MVC : On force l'interface à afficher la vraie valeur du Métier
                int vraiChiffre = this.ctrl.getNombreCasePlateau(lig, col);
                if (vraiChiffre == 0) {
                    lbl.setText(""); // Si la case est vide dans le métier, on efface l'écran
                } else {
                    lbl.setText(String.valueOf(vraiChiffre)); // Sinon, on affiche le chiffre métier
                }
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

    public void restart()
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
                    PanelJeu.this.btnActif.setBackground(COULEUR_GRIS_PLACE);
                    PanelJeu.this.btnActif = null;
                }
                else 
                {
                    JLabel lblContenu = (JLabel) tabPnlCases[this.ligSouris][this.colSouris].getComponent(0);
                    //lblContenu.setText(String.valueOf(valeur));
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
                            btn.setBackground(COULEUR_GRIS_PLACE);
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