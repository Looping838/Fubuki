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
    public final Color          COULEUR_FOND = new Color(200, 219, 250);
    
    private JPanel[][]          tabPnlCases;
    private Controleur          ctrl;
    
    private FrameJeu            frame;

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
    private JButton             btn3;
    private JButton             btn4;

    private ButtonGroup         grpBtn;

    private JToggleButton       btnActif;
    private JButton             btnGomme;

    private JRadioButton        chGomme;

    private boolean             modeValidation = false;

    public PanelJeu(Controleur ctrl)
    {
        this.setLayout(new BorderLayout());
        this.setBackground(COULEUR_FOND);

        
        /*-------------------------------*/
        /*   Création des composants     */
        /*-------------------------------*/

        this.ctrl           = ctrl;
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
        this.pnlCentre.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
        this.pnlCentre.setOpaque(false);
        
        // Panel qui affichera le Plateau
        this.pnlPlateau = new JPanel();
        this.pnlPlateau.setLayout(new GridLayout(this.nbLigne + 1, this.nbColonne + 1, 2, 2));
        //pnlPlateau              .setBackground(new Color(60, 60, 75));
        this.pnlPlateau.setPreferredSize(new Dimension(375, 375));
        this.pnlPlateau.setBackground(COULEUR_FOND);

        JPanel pnlDroit     = new JPanel();
        pnlDroit.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 140));
        pnlDroit.setOpaque(false);

        // Panel qui contiendra pnlNbres
        JPanel pnlBas       = new JPanel();
        pnlBas.setLayout(new BorderLayout());
        pnlBas.setOpaque(false);

        JPanel pnlBoutons   = new JPanel();
        pnlBoutons.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
        pnlBoutons.setOpaque(false);

        /*// Panel qui affichera les nombres à placer
        this.pnlNbres   = new JPanel();
        this.pnlNbres   .setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        this.pnlNbres   .setBackground(COULEUR_FOND);*/

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
        //this.btnGomme   .setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Ajout de l'icône gomme.png au bouton btnGomme en redimensionnant l'image au préalable
		this.btnGomme.setIcon(new ImageIcon(this.creerImages("./images/icones/gomme.png", 30, 30)));

        this.chGomme    = new JRadioButton("Tout Gommer", false);
        this.chGomme    .setOpaque(false);
        this.chGomme    .setFont(new Font("Sans-Serif", Font.PLAIN, 18));

        // Parcours pour dessiner le plateau
        this.dessinerPlateau();   

        this.btnValide      = new JButton("Valider");
        this.btnValide      .setOpaque(false);
        this.btnValide      .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnValide      .setIcon(new ImageIcon(this.creerImages("./images/icones/valide.png", 30, 30)));

        this.btnReset       = new JButton("Recommencer");
        this.btnReset       .setOpaque(false);
        this.btnReset       .setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnReset       .setIcon(new ImageIcon(this.creerImages("./images/icones/reset.png", 30, 25)));

        this.btn3           = new JButton("Bouton 3");
        this.btn3           .setOpaque(false);
        this.btn3           .setFont(new Font("Sans-Serif", Font.PLAIN, 18));

        this.btn4           = new JButton("Bouton 4");
        this.btn4           .setOpaque(false);
        this.btn4           .setFont(new Font("Sans-Serif", Font.PLAIN, 18));

        pnlBoutons.add(this.btnValide);
        pnlBoutons.add(this.btnReset );
        pnlBoutons.add(this.btn3);
        pnlBoutons.add(this.btn4);


        this.dessinerBoutons();    

        /*-------------------------------*/
        /* Positionnement des composants */
        /*-------------------------------*/

        JLabel lblScore     = new JLabel("Score : ");
        lblScore            .setFont(new Font("Sans-Serif", Font.PLAIN, 18));

        pnlBandeau  .add(lblScore);

        pnlGomme    .add(this.chGomme);
        pnlGomme    .add(this.btnGomme        );
        
        pnlHaut     .add(pnlBandeau     , BorderLayout.CENTER);
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
        this.btnReset   .addActionListener(this);
        
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
                                            btn.setEnabled(true);
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
                                    btn.setEnabled(true);
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
                System.out.println("VICTOIRE !");

                this.btnGomme.setEnabled(false);

                java.util.Enumeration<AbstractButton> elements = this.grpBtn.getElements();
                while (elements.hasMoreElements()) 
                {
                    AbstractButton btn = elements.nextElement();

                    btn.setEnabled(false);
                }
            }
        }

        if (e.getSource() == this.btnReset)
        {
            this.reset();
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
                        lblNombre.setFont(new Font("Sans-Serif", Font.BOLD, 18));
                    }
                    else
                    {
                        lblNombre = new JLabel(""/* + this.ctrl.getNombreCasePlateau(lig , col )*/);
                        lblNombre.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
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
                    lblTtlLigne         .setFont(new Font("Sans-Serif", Font.BOLD, 18));
                    lblTtlLigne         .setBackground(COULEUR_FOND);
                    
                    // NOUVEAU : On sauvegarde ce label
                    this.tabLblTotauxLignes[lig] = lblTtlLigne; 

                    pnlCellule          .setBackground(COULEUR_FOND);
                    pnlCellule.add(lblTtlLigne);
                    pnlCellule.setBorder(null);
                }
                else if (lig == this.nbLigne && col < this.nbColonne)
                {
                    JLabel lblTtlColonne = new JLabel(String.valueOf(this.ctrl.getTotauxColonnes(col)));
                    lblTtlColonne        .setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
                    lblTtlColonne        .setFont(new Font("Sans-Serif", Font.BOLD, 18));
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
            btnTmp.setPreferredSize(new Dimension(60, 50));

            // Si le nombre n'est pas déjà placé sur le plateau
            if (!this.ctrl.estPresent(valeurBouton))
            {    
                btnTmp.setBackground(new Color(177, 206, 255)); // Bleu
            }
            else // S'il est déjà sur le plateau d'origine
            {
                btnTmp.setBackground(new Color(177, 237, 190)); // Vert
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
                    couleurCase = new Color(177, 206, 252);
                }

                this.tabPnlCases[lig][col].setBackground(couleurCase);

                // Ajuste la couleur du texte (blanc sur fond bleu, noir sinon)
                JLabel lbl = (JLabel) this.tabPnlCases[lig][col].getComponent(0);
                if (couleurCase.equals(new Color(177, 206, 252))) { lbl.setForeground(Color.WHITE); }
                else                                              { lbl.setForeground(null);        }
            }
        }

        // 2. Gestion de la coloration des Labels de totaux en mode Validation
        Color vertFonce = new Color(0, 150, 0); // Plus lisible pour du texte que le vert pastel

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
        // 1. On demande un tout nouveau plateau au métier via le contrôleur
        this.ctrl.resetJeu();

        // 2. On remet nos variables de sélection à zéro
        this.ligSelectionne = -1;
        this.colSelectionne = -1;
        this.modeValidation = false;
        this.btnActif = null;
        this.btnGomme.setEnabled(true);
        
        // 3. On redessine nos deux panneaux graphiques (qui vont s'auto-nettoyer)
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
            
            // Si on reclique sur la case déjà bleue, on la désélectionne (on met à -1)
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

            // Gestion du changement des chiffres sur la grille avec ceux à mettre
            if (PanelJeu.this.btnActif != null && PanelJeu.this.ctrl.getNombreCasePlateau(this.ligSouris, this.colSouris) == 0)
            {
                // Si le bouton actif est vert (chiffre de départ), on empêche le placement
                if (PanelJeu.this.btnActif.getBackground().equals(new Color(177, 237, 190))) 
                {
                    PanelJeu.this.btnActif.setSelected(false);
                    PanelJeu.this.btnActif = null;
                }
                else 
                {
                    // Placement normal pour les boutons bleus
                    JLabel lblContenu = (JLabel) tabPnlCases[this.ligSouris][this.colSouris].getComponent(0);
                    lblContenu.setText(PanelJeu.this.btnActif.getText());

                    PanelJeu.this.ctrl.setNbrCase(this.ligSouris, this.colSouris, Integer.parseInt(PanelJeu.this.btnActif.getText()));
                    
                    PanelJeu.this.btnActif.setSelected(false);
                    PanelJeu.this.btnActif.setEnabled(false); // On verrouille le bouton bleu une fois placé
                    PanelJeu.this.btnActif = null;

                    // Dès qu'on modifie la grille, on annule l'affichage rouge/vert
                    PanelJeu.this.modeValidation = false; 
                }
            }

            PanelJeu.this.majPlateau();
        
        }
    }
}