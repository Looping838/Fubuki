package ihm;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Collections;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.Border;

// Classe publique PanelJeu : panneau principal gérant l'affichage du plateau, des outils et des interactions de jeu
public class PanelJeu extends JPanel implements ActionListener
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    private static final int NB_VALIDATIONS_MAX = 3; // Nb max. d'essais de validation par partie

    private Controleur    ctrl;                 // Réf. vers le contrôleur du jeu
    private FrameJeu      frame;                // Réf. vers la fenêtre principale
    private int           indice;               // Position du panneau dans tabPanel de FrameJeu

    private int           taillePlateau;        // Taille en pixels du plateau carré
    private int           tailleBoutonPave;     // Taille en pixels d'une touche du pavé numérique
    private int           tailleBoutonOutil;    // Largeur totale alignée des boutons d'outils

    private int           nbLigne;              // Nb de lignes de la grille jouable
    private int           nbColonne;            // Nb de colonnes de la grille jouable
    private int           ligSelectionne;       // Indice de ligne de la case sélectionnée (-1 si aucune)
    private int           colSelectionne;       // Indice de colonne de la case sélectionnée (-1 si aucune)

    private JPanel[][]    tabPnlCases;          // Matrice des panneaux graphiques des cases jouables
    private Border[][]    tabBorduresNormales;  // Matrice des bordures par défaut de chaque case
    private Border[][]    tabBorduresSelection; // Matrice des bordures roses de sélection de chaque case
    private JLabel[]      tabLblTotauxLignes;   // Tab. des labels affichant les sommes cibles des lignes
    private JLabel[]      tabLblTotauxColonnes; // Tab. des labels affichant les sommes cibles des colonnes

    private JPanel        pnlHaut;              // Conteneur supérieur hébergeant le bandeau de validations
    private JPanel        pnlCentre;            // Conteneur central regroupant le plateau et la colonne droite
    private JPanel        pnlBas;               // Conteneur inférieur regroupant le séparateur et les boutons
    private JPanel        pnlBandeau;           // Bandeau affichant le compteur de validations restantes
    private JPanel        pnlPlateau;           // Grille visuelle contenant les cases et les totaux
    private JPanel        pnlNbres;             // Grille 3x3 du pavé numérique (chiffres 1 à 9)
    private JPanel        pnlBoutons;           // Barre inférieure des boutons d'action généraux
    private JPanel        pnlOutils;            // Panneau regroupant Gomme, Indice et Mode Brouillon
    private JPanel        pnlColonneDroite;     // Colonne droite superposant le pavé numérique et les outils
    private JSeparator    separateurBas;        // Ligne horizontale séparant le plateau de la barre du bas
    private JMenuBar      barreMenu;            // Barre de menu supérieure contenant l'aide

    private JLabel        lblValidation;        // Texte descriptif du compteur de validations
    private JLabel        lblNbValidations;     // Label affichant le cœur et le nb de validations restantes

    private JButton       btnValide;            // Bouton permettant de vérifier si la grille est gagnante
    private JButton       btnReset;             // Bouton vidant toutes les cases modifiables de la grille
    private JButton       btnRestart;           // Bouton générant une nouvelle grille de même difficulté
    private JButton       btnAccueil;           // Bouton permettant de revenir à l'accueil
    private JButton       btnAnnuler;           // Bouton annulant le dernier coup via la pile d'historique
    private JButton       btnGomme;             // Outil effaçant le chiffre de la case sélectionnée
    private JButton       btnIndice;            // Outil révélant et verrouillant une case correcte au hasard
    private JButton       btnBrouillon;         // Bouton basculant entre placement définitif et notes brouillon

    private ButtonGroup   grpBtn;               // Groupe assurant l'exclusivité des 9 touches du pavé
    private JToggleButton btnActif;             // Touche du pavé numérique actuellement sélectionnée

    private boolean       modeValidation = false; // Indique si les totaux doivent être colorés en vert/rouge
    private boolean       modeBrouillon  = false; // Indique si le mode saisie de candidats est activé
    private int           nbIndicesRestants;      // Compteur d'indices encore disponibles dans la partie
    private int           nbValidationsRestantes; // Compteur d'essais de validation restants
    private long          tempsDebut;             // Horodatage (en ms) du début de la partie

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    // Constructeur qui initialise l'interface complète de jeu et affiche le plateau
    public PanelJeu(Controleur ctrl, FrameJeu frame, int indice)
    {
        UIManager.put("ToggleButton.select", ConstantesIHM.COULEUR_ROSE_CERVEAU);
        UIManager.put("ToggleButton.disabledText", Color.BLACK);

        this.setLayout(new BorderLayout());
        this.setBackground(ConstantesIHM.COULEUR_FOND);

        this.ctrl   = ctrl;
        this.frame  = frame;
        this.indice = indice;

        this.creerComposants();
        this.positionnerComposants();
        this.activerComposants();
        this.majPlateau();

        this.setVisible(true);
    }

    /*-------------------------------*/
    /*    Méthodes événementielles   */
    /*-------------------------------*/

    // Méthode événementielle qui gère les clics sur le pavé numérique, les outils et les boutons d'action
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        // Sélection ou désélection d'un chiffre sur le pavé numérique
        if (source instanceof JToggleButton btnClique)
        {
            int valCliquee = Integer.parseInt(btnClique.getText());
            if (this.ctrl.estPresent(valCliquee))
            {
                this.grpBtn.clearSelection();
                return;
            }

            // Bascule la sélection si on reclique sur le bouton déjà actif
            if (this.btnActif == btnClique)
            {
                this.grpBtn.clearSelection();
                this.btnActif = null;
            }
            else
            {
                this.btnActif = btnClique;
            }

            // Met à jour l'apparence visuelle de toutes les touches disponibles du pavé
            if (this.grpBtn != null)
            {
                for (AbstractButton btn : Collections.list(this.grpBtn.getElements()))
                {
                    if (btn.getBackground() != ConstantesIHM.COULEUR_GRIS_PLACE)
                    {
                        btn.setBackground(btn.isSelected() ? ConstantesIHM.COULEUR_ROSE_CERVEAU : Color.WHITE);
                        btn.setForeground(btn.isSelected() ? Color.WHITE : ConstantesIHM.COULEUR_ROSE_CERVEAU);
                    }
                }
            }

            this.ligSelectionne = -1;
            this.colSelectionne = -1;
            this.majPlateau();
        }
        // Effacement de la case actuellement sélectionnée via l'outil Gomme
        else if (source == this.btnGomme)
        {
            this.effacerCase(this.ligSelectionne, this.colSelectionne);
            this.majPlateau();
        }
        // Révélation d'une case correcte via l'outil Indice
        else if (source == this.btnIndice)
        {
            this.reinitialiserSelectionPave();

            if (this.nbIndicesRestants > 0)
            {
                this.nbIndicesRestants--;
                this.btnIndice.setText("  " + this.nbIndicesRestants);

                // Désactive le bouton Indice dès que le quota est épuisé
                if (this.nbIndicesRestants == 0)
                {
                    this.btnIndice.setBackground(Color.WHITE);
                    this.btnIndice.setEnabled(false);
                    this.btnIndice.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    if (this.btnIndice.getMousePosition() != null)
                        this.pnlOutils.setCursor(java.awt.dnd.DragSource.DefaultCopyNoDrop);
                }

                this.ctrl.donnerIndice();
                this.majPlateau();
                this.majBoutons();
            }
        }
        // Activation ou désactivation du mode Brouillon
        else if (source == this.btnBrouillon)
        {
            this.modeBrouillon  = !this.modeBrouillon;
            this.ligSelectionne = -1;
            this.colSelectionne = -1;
            this.btnBrouillon.setBackground(this.modeBrouillon ? ConstantesIHM.COULEUR_BROUILLON_ACTIVE : ConstantesIHM.COULEUR_GRIS_SURVOL);
            this.majPlateau();
        }
        // Vérification de la grille et gestion de la victoire ou de la perte d'un essai
        else if (source == this.btnValide)
        {
            this.modeValidation = true;
            this.ligSelectionne = -1;
            this.colSelectionne = -1;
            this.majPlateau();

            // Si la grille est incorrecte, décrémente le compteur d'essais et vérifie la défaite
            if (!this.ctrl.estGagne())
            {
                this.nbValidationsRestantes--;
                this.majAffichageValidation();

                if (this.nbValidationsRestantes <= 0)
                {
                    this.cacherMenuBar(false);
                    this.frame.afficherPanelResultat(false, "");
                    this.frame.setPnl(this.frame.getPnl(this.indice + 1));
                    this.ctrl.resetJeu();
                }
            }
            // Si la grille est gagnante, verrouille les contrôles et affiche l'écran de victoire
            else
            {
                this.btnGomme.setEnabled(false);
                for (AbstractButton btn : Collections.list(this.grpBtn.getElements()))
                    btn.setEnabled(false);

                this.cacherMenuBar(false);
                this.frame.afficherPanelResultat(true, this.getTempsEcoule());
            }
        }
        // Réinitialisation de la grille en cours
        else if (source == this.btnReset)
        {
            this.reset();
        }
        // Lancement d'une nouvelle partie sur une autre grille
        else if (source == this.btnRestart)
        {
            this.restart();
        }
        // Annulation du dernier coup enregistré dans la pile
        else if (source == this.btnAnnuler)
        {
            this.reinitialiserSelectionPave();
            this.ctrl.depilerCase();
            this.majPlateau();
            this.majBoutons();
        }
        // Retour vers l'écran d'accueil
        else if (source == this.btnAccueil)
        {
            this.frame.setPnl(this.frame.getPnl(0));
            this.cacherMenuBar(false);
        }
    }

    /*-------------------------------*/
    /*            Getters            */
    /*-------------------------------*/

    // Getter qui retourne le nombre d'essais de validation restants
    public int getNbValidationsRestantes()
    {
        return this.nbValidationsRestantes;
    }

    // Getter qui calcule et formate le temps écoulé depuis le début de la partie
    public String getTempsEcoule()
    {
        long tempsTotalSecondes = (System.currentTimeMillis() - this.tempsDebut) / 1000;
        return String.format("Temps total : %02d:%02d ", tempsTotalSecondes / 60, tempsTotalSecondes % 60);
    }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    // Méthode utilitaire qui affiche ou masque la barre de menu supérieure
    public void cacherMenuBar(boolean estVisible)
    {
        this.barreMenu.setVisible(estVisible);
    }

    // Méthode utilitaire qui dimensionne et instancie tous les panneaux, labels et boutons du jeu
    private void creerComposants()
    {
        Dimension tailleEcran  = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.taillePlateau     = (int) (tailleEcran.height * 0.55);
        this.tailleBoutonPave  = (int) (this.taillePlateau * 0.15);
        this.tailleBoutonOutil = (this.tailleBoutonPave * 3) + 20;

        this.nbLigne        = this.ctrl.getNbLigne();
        this.nbColonne      = this.ctrl.getNbColonne();
        this.ligSelectionne = -1;
        this.colSelectionne = -1;
        this.tempsDebut     = System.currentTimeMillis();

        this.pnlHaut = new JPanel(new BorderLayout());
        this.pnlHaut.setOpaque(false);

        this.pnlBandeau = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 15));
        this.pnlBandeau.setOpaque(false);

        this.lblValidation = new JLabel("Nombre de validations restantes :");
        this.lblValidation.setFont(new Font("Sans-Serif", Font.BOLD, 18));

        this.lblNbValidations = new JLabel();
        this.lblNbValidations.setFont(new Font("Sans-Serif", Font.BOLD, 18));
        this.lblNbValidations.setIcon(new ImageIcon(this.frame.creerDessins("./images/logos/coeur.png")));
        this.lblNbValidations.setHorizontalTextPosition(SwingConstants.RIGHT);
        this.lblNbValidations.setIconTextGap(6);
        this.initNbValidations();

        this.pnlCentre = new JPanel(new GridBagLayout());
        this.pnlCentre.setOpaque(false);

        this.pnlPlateau = new JPanel(new GridLayout(this.nbLigne + 1, this.nbColonne + 1, 2, 2));
        this.pnlPlateau.setPreferredSize(new Dimension(this.taillePlateau, this.taillePlateau));
        this.pnlPlateau.setBackground(ConstantesIHM.COULEUR_FOND);

        this.pnlBas = new JPanel(new BorderLayout());
        this.pnlBas.setOpaque(false);

        this.pnlBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.pnlBoutons.setBackground(ConstantesIHM.COULEUR_FOND_MENUBAR);

        this.pnlNbres = new JPanel(new GridLayout(3, 3, 10, 10));
        this.pnlNbres.setBackground(ConstantesIHM.COULEUR_FOND);

        this.pnlOutils = new JPanel(new GridBagLayout());
        this.pnlOutils.setOpaque(false);

        this.pnlColonneDroite = new JPanel(new GridBagLayout());
        this.pnlColonneDroite.setOpaque(false);

        this.separateurBas = new JSeparator(SwingConstants.HORIZONTAL);
        this.separateurBas.setForeground(ConstantesIHM.COULEUR_SEPARATEUR);
        this.separateurBas.setBackground(ConstantesIHM.COULEUR_FOND_SEPARATEUR);

        this.dessinerPlateau();
        this.dessinerBoutons();

        int largeurBtnBarre = tailleEcran.width / 6;
        this.btnValide  = this.creerBoutonBarre("  Valider",                ConstantesIHM.COULEUR_VERT_VALIDE, Color.WHITE, "./images/icones/valide.png",  30, 30, largeurBtnBarre, "Valider votre choix");
        this.btnReset   = this.creerBoutonBarre("  Réinitialiser le plateau", Color.WHITE,                       Color.BLACK, "./images/icones/reset.png",   30, 25, largeurBtnBarre, "Réinitialiser le plateau comme à l'origine");
        this.btnRestart = this.creerBoutonBarre("  Recommencer une partie", Color.WHITE,                       Color.BLACK, "./images/icones/restart.png", 35, 35, largeurBtnBarre, "Recommencer une partie différente");
        this.btnAnnuler = this.creerBoutonBarre("  Annuler",                Color.WHITE,                       Color.BLACK, "./images/icones/annuler.png", 30, 25, largeurBtnBarre, "Annuler le coup précédent");
        this.btnAccueil = this.creerBoutonBarre("  Retourner à l'accueil",  Color.WHITE,                       Color.BLACK, "./images/icones/accueil.png", 30, 25, largeurBtnBarre, "Retourner à l'accueil du jeu");
        this.btnAnnuler.setEnabled(false);

        this.btnGomme = new JButton("");
        this.btnGomme.setBackground(Color.WHITE);
        this.btnGomme.setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE_GOMME));
        this.btnGomme.setPreferredSize(new Dimension((this.tailleBoutonOutil - 10) / 2, 50));
        this.btnGomme.setIcon(new ImageIcon(this.frame.creerImages("./images/icones/gomme.png", 30, 30)));
        this.btnGomme.setFocusPainted(false);
        this.btnGomme.setToolTipText("Gommer un chiffre du plateau en cliquant la case en amont");

        this.btnIndice = new JButton("");
        this.btnIndice.setBackground(Color.WHITE);
        this.btnIndice.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnIndice.setPreferredSize(new Dimension((this.tailleBoutonOutil - 10) / 2, 50));
        this.btnIndice.setIcon(new ImageIcon(this.frame.creerImages("./images/icones/indice.png", 40, 35)));
        this.btnIndice.setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnIndice.setFocusPainted(false);
        this.btnIndice.setToolTipText("Révéler une case du plateau");
        this.initBoutonIndice();

        this.btnBrouillon = new JButton("  Mode Brouillon");
        this.btnBrouillon.setBackground(Color.WHITE);
        this.btnBrouillon.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnBrouillon.setPreferredSize(new Dimension(this.tailleBoutonOutil, 50));
        this.btnBrouillon.setIcon(new ImageIcon(this.frame.creerImages("./images/icones/brouillon.png", 30, 25)));
        this.btnBrouillon.setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnBrouillon.setFocusPainted(false);
        this.btnBrouillon.setToolTipText("Passer au mode Brouillon pour placer vos candidats");
    }

    // Méthode utilitaire qui crée et stylise un bouton de la barre d'actions inférieure
    private JButton creerBoutonBarre(String texte, Color fond, Color texteCoul, String icone, int l, int h, int largeur, String bulle)
    {
        JButton btn = new JButton(texte);
        btn.setBackground(fond);
        btn.setForeground(texteCoul);
        btn.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        btn.setPreferredSize(new Dimension(largeur, 50));
        btn.setIcon(new ImageIcon(this.frame.creerImages(icone, l, h)));
        btn.setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        btn.setFocusPainted(false);
        btn.setToolTipText(bulle);
        return btn;
    }

    // Méthode utilitaire qui assemble et positionne les différents panneaux dans la fenêtre
    private void positionnerComposants()
    {
        this.creerMenuBar();

        this.pnlBoutons.add(this.btnValide);
        this.pnlBoutons.add(this.btnReset);
        this.pnlBoutons.add(this.btnRestart);
        this.pnlBoutons.add(this.btnAnnuler);
        this.pnlBoutons.add(this.btnAccueil);

        this.pnlBandeau.add(this.lblValidation);
        this.pnlBandeau.add(this.lblNbValidations);
        this.pnlHaut.add(this.pnlBandeau, BorderLayout.CENTER);

        GridBagConstraints gbcOutils = new GridBagConstraints();
        gbcOutils.fill = GridBagConstraints.BOTH;

        gbcOutils.gridx = 0; gbcOutils.gridy = 0; gbcOutils.gridwidth = 1;
        gbcOutils.insets = new Insets(15, 0, 0, 5);
        this.pnlOutils.add(this.btnGomme, gbcOutils);

        gbcOutils.gridx = 1; gbcOutils.gridy = 0;
        gbcOutils.insets = new Insets(15, 5, 0, 0);
        this.pnlOutils.add(this.btnIndice, gbcOutils);

        gbcOutils.gridx = 0; gbcOutils.gridy = 1; gbcOutils.gridwidth = 2;
        gbcOutils.insets = new Insets(10, 0, 0, 0);
        this.pnlOutils.add(this.btnBrouillon, gbcOutils);

        GridBagConstraints gbcDroite = new GridBagConstraints();
        gbcDroite.gridx = 0; gbcDroite.gridy = 0;
        this.pnlColonneDroite.add(this.pnlNbres, gbcDroite);

        gbcDroite.gridy  = 1;
        gbcDroite.insets = new Insets(38, 0, 0, 0);
        this.pnlColonneDroite.add(this.pnlOutils, gbcDroite);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 100);
        this.pnlCentre.add(this.pnlPlateau, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.pnlCentre.add(this.pnlColonneDroite, gbc);

        this.pnlBas.add(this.separateurBas, BorderLayout.NORTH);
        this.pnlBas.add(this.pnlBoutons,    BorderLayout.CENTER);

        this.add(this.pnlHaut,   BorderLayout.NORTH);
        this.add(this.pnlCentre, BorderLayout.CENTER);
        this.add(this.pnlBas,    BorderLayout.SOUTH);
    }

    // Méthode utilitaire qui attache les écouteurs d'événements et le raccourci clavier Ctrl+Z
    private void activerComposants()
    {
        this.configurerSurvolBouton(this.btnGomme,   Color.WHITE, ConstantesIHM.COULEUR_GRIS_SURVOL, Color.BLACK);
        this.configurerSurvolBouton(this.btnIndice,  Color.WHITE, ConstantesIHM.COULEUR_GRIS_SURVOL, Color.BLACK);
        this.configurerSurvolBouton(this.btnReset,   Color.WHITE, ConstantesIHM.COULEUR_GRIS_SURVOL, Color.BLACK);
        this.configurerSurvolBouton(this.btnRestart, Color.WHITE, ConstantesIHM.COULEUR_GRIS_SURVOL, Color.BLACK);
        this.configurerSurvolBouton(this.btnAnnuler, Color.WHITE, ConstantesIHM.COULEUR_GRIS_SURVOL, Color.BLACK);
        this.configurerSurvolBouton(this.btnAccueil, Color.WHITE, ConstantesIHM.COULEUR_GRIS_SURVOL, Color.BLACK);
        this.configurerSurvolBouton(this.btnValide,  ConstantesIHM.COULEUR_VERT_VALIDE, ConstantesIHM.COULEUR_VERT_VALIDE_SURVOL, Color.WHITE);

        this.btnBrouillon.addActionListener(this);
        this.btnBrouillon.addMouseListener(new MouseAdapter()
        {
            // Méthode événementielle qui grise le bouton Brouillon au survol s'il est inactif
            @Override
            public void mouseEntered(MouseEvent e)
            {
                btnBrouillon.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (!PanelJeu.this.modeBrouillon)
                    btnBrouillon.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
            }

            // Méthode événementielle qui rétablit la couleur selon l'état actif/inactif du brouillon
            @Override
            public void mouseExited(MouseEvent e)
            {
                btnBrouillon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnBrouillon.setBackground(PanelJeu.this.modeBrouillon ? ConstantesIHM.COULEUR_BROUILLON_ACTIVE : Color.WHITE);
            }

            // Méthode événementielle qui réinitialise le style lors de l'appui
            @Override
            public void mousePressed(MouseEvent e)
            {
                btnBrouillon.setBackground(Color.WHITE);
                btnBrouillon.setForeground(Color.BLACK);
            }
        });

        // Désélectionne la case active lors d'un clic en dehors de la grille
        MouseAdapter clicDansLeVide = new MouseAdapter()
        {
            // Méthode événementielle qui annule la sélection de case au clic dans le vide
            @Override
            public void mousePressed(MouseEvent e)
            {
                PanelJeu.this.ligSelectionne = -1;
                PanelJeu.this.colSelectionne = -1;
                PanelJeu.this.majPlateau();
            }
        };
        this.addMouseListener(clicDansLeVide);
        this.pnlCentre.addMouseListener(clicDansLeVide);

        // Associe le raccourci clavier Ctrl + Z au bouton Annuler
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "actionAnnuler");
        this.getActionMap().put("actionAnnuler", new AbstractAction()
        {
            // Méthode événementielle qui déclenche un clic sur le bouton Annuler
            @Override
            public void actionPerformed(ActionEvent e)
            {
                btnAnnuler.doClick();
            }
        });
    }

    // Méthode utilitaire qui gère l'apparence et le curseur d'un bouton lors du survol et du clic
    private void configurerSurvolBouton(JButton bouton, Color couleurBase, Color couleurSurvol, Color couleurTexte)
    {
        bouton.addActionListener(this);
        bouton.addMouseListener(new MouseAdapter()
        {
            // Méthode événementielle qui applique le survol ou affiche un curseur interdit si le bouton est désactivé
            @Override
            public void mouseEntered(MouseEvent e)
            {
                if (bouton.isEnabled())
                {
                    bouton.setBackground(couleurSurvol);
                    bouton.setForeground(couleurTexte);
                    bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                else if (bouton.getParent() != null)
                {
                    bouton.getParent().setCursor(java.awt.dnd.DragSource.DefaultCopyNoDrop);
                }
            }

            // Méthode événementielle qui rétablit la couleur et le curseur à la sortie de la souris
            @Override
            public void mouseExited(MouseEvent e)
            {
                bouton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (bouton.getParent() != null)
                    bouton.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                if (bouton.isEnabled())
                {
                    bouton.setBackground(couleurBase);
                    bouton.setForeground(couleurTexte);
                }
            }

            // Méthode événementielle qui ajuste le fond du bouton lors de l'appui
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (bouton.isEnabled())
                {
                    bouton.setBackground(bouton == btnValide ? couleurSurvol : couleurBase);
                    bouton.setForeground(couleurTexte);
                }
            }
        });
    }

    // Méthode utilitaire qui construit la barre de menu supérieure et ses rubriques d'aide
    private void creerMenuBar()
    {
        UIManager.put("Menu.selectionBackground",     ConstantesIHM.COULEUR_ROSE_SURVOL);
        UIManager.put("Menu.selectionForeground",     Color.BLACK);
        UIManager.put("MenuItem.selectionBackground", ConstantesIHM.COULEUR_ROSE_SURVOL);
        UIManager.put("MenuItem.selectionForeground", Color.BLACK);
        UIManager.put("MenuItem.background",          Color.WHITE);

        this.barreMenu = new JMenuBar();
        this.barreMenu.setOpaque(true);
        this.barreMenu.setBackground(ConstantesIHM.COULEUR_FOND_MENUBAR);
        this.barreMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ConstantesIHM.COULEUR_SEPARATEUR));

        JMenu menuAide = new JMenu("Aide");
        menuAide.setFont(new Font("Sans-Serif", Font.BOLD, 16));
        menuAide.setForeground(Color.BLACK);
        menuAide.setIcon(new ImageIcon(this.frame.creerImages("./images/icones/aide.png", 20, 20)));
        menuAide.setIconTextGap(8);
        menuAide.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        menuAide.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPopupMenu popup = menuAide.getPopupMenu();
        popup.setBackground(Color.WHITE);
        popup.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ConstantesIHM.COULEUR_SEPARATEUR, 1),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));

        JButton btnCompris = new JButton("Compris !");
        btnCompris.setFont(new Font("Sans-Serif", Font.BOLD, 15));
        btnCompris.setBackground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
        btnCompris.setForeground(Color.WHITE);
        btnCompris.setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        btnCompris.setFocusPainted(false);
        btnCompris.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCompris.addActionListener(ae ->
        {
            Window w = SwingUtilities.getWindowAncestor(btnCompris);
            if (w != null) w.dispose();
        });
        btnCompris.addMouseListener(new MouseAdapter()
        {
            // Méthode événementielle qui éclaircit le bouton Compris au survol
            @Override public void mouseEntered(MouseEvent e)  { btnCompris.setBackground(ConstantesIHM.COULEUR_ROSE_SURVOL); }
            // Méthode événementielle qui rétablit le rose principal du bouton Compris
            @Override public void mouseExited(MouseEvent e)   { btnCompris.setBackground(ConstantesIHM.COULEUR_ROSE_CERVEAU); }
            // Méthode événementielle qui fonce le bouton Compris à l'appui
            @Override public void mousePressed(MouseEvent e)  { btnCompris.setBackground(ConstantesIHM.COULEUR_ROSE_CERVEAU_FONCE); }
            // Méthode événementielle qui rétablit le rose principal au relâchement
            @Override public void mouseReleased(MouseEvent e) { btnCompris.setBackground(ConstantesIHM.COULEUR_ROSE_CERVEAU); }
        });

        menuAide.add(this.creerItemAide("Règles du jeu", "Règles du jeu", "./images/icones/regles.png", btnCompris,
            "<html><body style='width: 390px; font-family: Sans-Serif; font-size: 13px; color: #222222;'>"
          + "<div style='font-size: 16px; font-weight: bold; color: #BE185D; margin-bottom: 8px;'>Bienvenue dans Fubuki !</div>"
          + "<p style='margin: 0 0 10px 0;'>Complétez la grille en respectant ces 3 règles :</p>"
          + "<div style='background-color: #F8FAFC; border: 1px solid #E2E8F0; padding: 8px 12px;'>"
          + "<p style='margin: 5px 0;'><b style='color: #BE185D;'>• Chiffres de 1 à 9 :</b> remplissez chaque case vide.</p>"
          + "<p style='margin: 5px 0;'><b style='color: #BE185D;'>• Unicité :</b> chaque chiffre n'est utilisé qu'une seule fois.</p>"
          + "<p style='margin: 5px 0;'><b style='color: #BE185D;'>• Totaux :</b> la somme de chaque ligne et colonne doit correspondre au résultat indiqué.</p>"
          + "</div>"
          + "<div style='margin-top: 10px; color: #555555; font-style: italic;'>Astuce : jouez par clic simple ou par glisser-déposer depuis le pavé numérique.</div>"
          + "</body></html>"));

        menuAide.add(this.creerItemAide("Les Niveaux de Difficultés", "Niveaux de Difficulté", "./images/icones/difficulte.png", btnCompris,
            "<html><body style='width: 380px; font-family: Sans-Serif; font-size: 13px; color: #222222;'>"
          + "<div style='font-size: 16px; font-weight: bold; color: #BE185D; margin-bottom: 8px;'>Les 3 niveaux de difficulté</div>"
          + "<div style='background-color: #F8FAFC; border: 1px solid #E2E8F0; padding: 8px 12px;'>"
          + "<p style='margin: 6px 0;'><b style='color: #2E8B57;'>• FACILE :</b> 2 indices disponibles.</p>"
          + "<p style='margin: 6px 0;'><b style='color: #D97706;'>• MOYEN :</b> 1 indice disponible.</p>"
          + "<p style='margin: 6px 0;'><b style='color: #DC2626;'>• DIFFICILE :</b> Aucun indice disponible.</p>"
          + "</div>"
          + "<div style='margin-top: 10px; color: #555555; font-style: italic;'>Plus la difficulté est élevée, moins de chiffres sont dévoilés au départ.</div>"
          + "</body></html>"));

        menuAide.add(this.creerItemAide("Utilisation des Outils", "Utilisation des Outils", "./images/icones/outils.png", btnCompris,
            "<html><body style='width: 420px; font-family: Sans-Serif; font-size: 13px; color: #222222;'>"
          + "<div style='font-size: 16px; font-weight: bold; color: #BE185D; margin-bottom: 8px;'>Outils disponibles en partie</div>"
          + "<div style='background-color: #F8FAFC; border: 1px solid #E2E8F0; padding: 8px 12px;'>"
          + "<p style='margin: 6px 0;'><b style='color: #1E293B;'>• GOMME :</b> efface le chiffre de la case sélectionnée.</p>"
          + "<p style='margin: 6px 0;'><b style='color: #D97706;'>• INDICE :</b> révèle une case correcte au hasard et la verrouille.</p>"
          + "<p style='margin: 6px 0;'><b style='color: #2E8B57;'>• VALIDER :</b> vérifie votre grille (attention aux essais restants).</p>"
          + "<p style='margin: 6px 0;'><b style='color: #2563EB;'>• ANNULER :</b> annule le dernier coup (placement, effacement ou échange).</p>"
          + "</div></body></html>"));

        menuAide.add(this.creerItemAide("Le Mode Brouillon", "Le Mode Brouillon", "./images/icones/brouillon.png", btnCompris,
            "<html><body style='width: 420px; font-family: Sans-Serif; font-size: 13px; color: #222222;'>"
          + "<div style='font-size: 16px; font-weight: bold; color: #2563EB; margin-bottom: 8px;'>Le Mode Brouillon</div>"
          + "<p style='margin: 0 0 10px 0;'>Testez plusieurs hypothèses dans une même case avant de décider :</p>"
          + "<div style='background-color: #F8FAFC; border: 1px solid #E2E8F0; padding: 8px 12px;'>"
          + "<p style='margin: 6px 0;'><b style='color: #2563EB;'>1.</b> Activez le mode (le bouton devient bleu).</p>"
          + "<p style='margin: 6px 0;'><b style='color: #2563EB;'>2.</b> Cliquez sur une case vide, puis choisissez vos chiffres à noter.</p>"
          + "<p style='margin: 6px 0;'><b style='color: #2563EB;'>3.</b> Sélectionnez à nouveau un chiffre pour retirer la note.</p>"
          + "<p style='margin: 6px 0;'><b style='color: #2563EB;'>4.</b> Désactivez le mode pour placer vos chiffres définitifs.</p>"
          + "</div></body></html>"));

        menuAide.add(this.creerItemAide("Astuces & Raccourcis", "Astuces & Raccourcis", "./images/icones/raccourci.png", btnCompris,
            "<html><body style='width: 430px; font-family: Sans-Serif; font-size: 13px; color: #222222;'>"
          + "<div style='font-size: 16px; font-weight: bold; color: #BE185D; margin-bottom: 8px;'>Astuces &amp; Raccourcis</div>"
          + "<div style='background-color: #F8FAFC; border: 1px solid #E2E8F0; padding: 8px 12px;'>"
          + "<p style='margin: 6px 0;'><b style='color: #BE185D;'>• GLISSER-DÉPOSER :</b> maintenez le clic sur un chiffre du pavé et relâchez-le sur une case.</p>"
          + "<p style='margin: 6px 0;'><b style='color: #BE185D;'>• CLIC DROIT :</b> efface instantanément le chiffre d'une case modifiable.</p>"
          + "<p style='margin: 6px 0;'><b style='color: #BE185D;'>• CTRL + Z :</b> annule rapidement votre dernière action.</p>"
          + "<p style='margin: 6px 0;'><b style='color: #BE185D;'>• ÉCHANGE RAPIDE :</b> cliquez successivement sur deux chiffres placés pour les permuter.</p>"
          + "</div></body></html>"));

        menuAide.addSeparator();

        menuAide.add(this.creerItemAide("À propos", "À propos de Fubuki", "./images/icones/a-propos.png", btnCompris,
            "<html><body style='width: 380px; font-family: Sans-Serif; font-size: 13px; color: #222222;'>"
          + "<div style='font-size: 17px; font-weight: bold; color: #BE185D;'>Fubuki <span style='font-size: 12px; color: #64748B;'>— Version 2.0</span></div>"
          + "<p style='margin: 8px 0 10px 0; color: #475569;'>Projet informatique développé par :</p>"
          + "<div style='background-color: #F8FAFC; border: 1px solid #E2E8F0; padding: 8px 12px;'>"
          + "<p style='margin: 5px 0;'><b>• Alexis COFFARD</b> — Interface Graphique &amp; Ergonomie</p>"
          + "<p style='margin: 5px 0;'><b>• Timothé LARBI</b> — Moteur logique &amp; Métier</p>"
          + "</div>"
          + "<div style='margin-top: 10px; color: #555555; font-style: italic;'>Merci d'avoir joué !</div>"
          + "</body></html>"));

        this.barreMenu.add(Box.createHorizontalGlue());
        this.barreMenu.add(menuAide);
        this.frame.setJMenuBar(this.barreMenu);
    }

    // Méthode utilitaire qui crée un élément de menu ouvrant une boîte de dialogue d'aide
    private JMenuItem creerItemAide(String libelleMenu, String titreFenetre, String cheminIcone, JButton btnCompris, String html)
    {
        JMenuItem item = new JMenuItem(libelleMenu);
        item.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        item.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        item.setIconTextGap(12);
        item.addActionListener(e -> JOptionPane.showOptionDialog(
            this.frame,
            new JLabel(html),
            titreFenetre,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            new ImageIcon(this.frame.creerDessins(cheminIcone)),
            new Object[]{ btnCompris },
            btnCompris
        ));
        return item;
    }

    // Méthode utilitaire qui génère graphiquement les cases de la grille et les totaux périphériques
    public void dessinerPlateau()
    {
        this.pnlPlateau.removeAll();

        this.tabPnlCases          = new JPanel[this.nbLigne][this.nbColonne];
        this.tabLblTotauxLignes   = new JLabel[this.nbLigne];
        this.tabLblTotauxColonnes = new JLabel[this.nbColonne];
        this.tabBorduresNormales  = new Border[this.nbLigne][this.nbColonne];
        this.tabBorduresSelection = new Border[this.nbLigne][this.nbColonne];

        // Parcourt la grille étendue (nbLigne+1 x nbColonne+1) pour inclure les totaux de lignes et colonnes
        for (int lig = 0; lig <= this.nbLigne; lig++)
        {
            for (int col = 0; col <= this.nbColonne; col++)
            {
                JPanel pnlCellule = new JPanel(new CardLayout());
                pnlCellule.setBackground(Color.WHITE);

                // Construit une case jouable avec ses deux calques (VALEUR et BROUILLON)
                if (lig < this.nbLigne && col < this.nbColonne)
                {
                    JPanel pnlBrouillon = new JPanel(new GridLayout(3, 3));
                    pnlBrouillon.setOpaque(false);
                    for (int i = 0; i < 9; i++)
                    {
                        JLabel lblNote = new JLabel("", SwingConstants.CENTER);
                        lblNote.setFont(new Font("Sans-Serif", Font.ITALIC, 18));
                        lblNote.setForeground(ConstantesIHM.COULEUR_GRIS_BROUILLON);
                        pnlBrouillon.add(lblNote);
                    }

                    int valInitiale = this.ctrl.getNombreCasePlateau(lig, col);
                    JLabel lblNombre = new JLabel(valInitiale != 0 ? String.valueOf(valInitiale) : "", SwingConstants.CENTER);
                    lblNombre.setFont(new Font("Sans-Serif", valInitiale != 0 ? Font.BOLD : Font.ITALIC, 24));
                    lblNombre.setOpaque(false);

                    pnlCellule.add(lblNombre,    "VALEUR");
                    pnlCellule.add(pnlBrouillon, "BROUILLON");

                    // Épaissit les bordures extérieures de la grille de jeu
                    Border bordureBase = BorderFactory.createMatteBorder(
                        lig == 0 ? 3 : 1,
                        col == 0 ? 3 : 1,
                        lig == this.nbLigne - 1   ? 3 : 1,
                        col == this.nbColonne - 1 ? 3 : 1,
                        Color.BLACK
                    );

                    this.tabBorduresNormales[lig][col]  = BorderFactory.createCompoundBorder(bordureBase, ConstantesIHM.BORDURE_INTERIEURE_VIDE);
                    this.tabBorduresSelection[lig][col] = BorderFactory.createCompoundBorder(bordureBase, ConstantesIHM.BORDURE_INTERIEURE_SELECTION);
                    pnlCellule.setBorder(this.tabBorduresNormales[lig][col]);

                    this.tabPnlCases[lig][col] = pnlCellule;
                    pnlCellule.addMouseListener(new GereSouris(lig, col));

                    GereDepot depot = new GereDepot(lig, col);
                    pnlCellule.setTransferHandler(depot);
                    lblNombre.setTransferHandler(depot);
                }
                // Construit la cellule affichant la somme cible de la ligne en dernière colonne
                else if (lig < this.nbLigne && col == this.nbColonne)
                {
                    JLabel lblTtlLigne = new JLabel(String.valueOf(this.ctrl.getTotauxLignes(lig)), SwingConstants.CENTER);
                    lblTtlLigne.setFont(new Font("Sans-Serif", Font.BOLD, 24));
                    this.tabLblTotauxLignes[lig] = lblTtlLigne;

                    pnlCellule.setBackground(ConstantesIHM.COULEUR_FOND);
                    pnlCellule.add(lblTtlLigne);
                    pnlCellule.setBorder(null);
                }
                // Construit la cellule affichant la somme cible de la colonne en dernière ligne
                else if (lig == this.nbLigne && col < this.nbColonne)
                {
                    JLabel lblTtlColonne = new JLabel(String.valueOf(this.ctrl.getTotauxColonnes(col)), SwingConstants.CENTER);
                    lblTtlColonne.setFont(new Font("Sans-Serif", Font.BOLD, 24));
                    this.tabLblTotauxColonnes[col] = lblTtlColonne;

                    pnlCellule.setBackground(ConstantesIHM.COULEUR_FOND);
                    pnlCellule.add(lblTtlColonne);
                    pnlCellule.setBorder(null);
                }
                // Laisse vide le coin inférieur droit à l'intersection des totaux
                else
                {
                    pnlCellule.setBorder(null);
                    pnlCellule.setBackground(this.getBackground());
                }

                this.pnlPlateau.add(pnlCellule);
            }
        }
    }

    // Méthode utilitaire qui génère les 9 boutons du pavé numérique et configure leur glisser-déposer
    public void dessinerBoutons()
    {
        this.pnlNbres.removeAll();
        this.grpBtn = new ButtonGroup();

        // Crée chacune des 9 touches de 1 à 9 avec ses écouteurs de glisser-déposer et de survol
        for (int valeurBouton = 1; valeurBouton <= 9; valeurBouton++)
        {
            JToggleButton btnTmp = new JToggleButton(String.valueOf(valeurBouton));
            btnTmp.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
            btnTmp.setPreferredSize(new Dimension(this.tailleBoutonPave, this.tailleBoutonPave));
            btnTmp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            btnTmp.setFocusPainted(false);

            // Configure le bouton comme source de glisser-déposer (Drag & Drop)
            btnTmp.setTransferHandler(new TransferHandler()
            {
                // Méthode de transfert qui autorise uniquement la copie de la valeur du bouton
                @Override
                public int getSourceActions(JComponent c)
                {
                    return TransferHandler.COPY;
                }

                // Méthode de transfert qui encapsule le chiffre du bouton dans un objet transférable
                @Override
                protected Transferable createTransferable(JComponent c)
                {
                    return new StringSelection(((JToggleButton) c).getText());
                }

                // Méthode de transfert qui actualise l'état visuel du bouton une fois le dépôt terminé
                @Override
                protected void exportDone(JComponent source, Transferable data, int action)
                {
                    JToggleButton btn = (JToggleButton) source;
                    btn.getModel().setArmed(false);
                    btn.getModel().setPressed(false);

                    int val = Integer.parseInt(btn.getText());
                    if (PanelJeu.this.ctrl.estPresent(val))
                    {
                        btn.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
                        btn.setForeground(ConstantesIHM.COULEUR_GRIS_PLACE_LABEL);
                        PanelJeu.this.grpBtn.clearSelection();
                    }
                    else if (btn == PanelJeu.this.btnActif)
                    {
                        btn.setBackground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
                        btn.setForeground(Color.WHITE);
                    }
                    else
                    {
                        btn.setBackground(Color.WHITE);
                        btn.setForeground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
                    }
                }
            });

            // Déclenche le glisser-déposer dès que la souris glisse avec le clic maintenu
            btnTmp.addMouseMotionListener(new MouseMotionAdapter()
            {
                // Méthode événementielle qui lance l'exportation par glissement
                @Override
                public void mouseDragged(MouseEvent e)
                {
                    JComponent composant = (JComponent) e.getSource();
                    composant.getTransferHandler().exportAsDrag(composant, e, TransferHandler.COPY);
                }
            });

            btnTmp.addMouseListener(new MouseAdapter()
            {
                // Méthode événementielle qui gère le curseur et le survol rose si le chiffre est disponible
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    int val = Integer.parseInt(btnTmp.getText());
                    if (PanelJeu.this.ctrl.estPresent(val))
                    {
                        btnTmp.setCursor(java.awt.dnd.DragSource.DefaultCopyNoDrop);
                    }
                    else
                    {
                        btnTmp.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        if (!btnTmp.isSelected())
                        {
                            btnTmp.setBackground(ConstantesIHM.COULEUR_ROSE_SURVOL);
                            btnTmp.setForeground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
                        }
                    }
                }

                // Méthode événementielle qui rétablit le fond blanc si le bouton n'est ni posé ni sélectionné
                @Override
                public void mouseExited(MouseEvent e)
                {
                    btnTmp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    if (btnTmp.getBackground() != ConstantesIHM.COULEUR_GRIS_PLACE && !btnTmp.isSelected())
                    {
                        btnTmp.setBackground(Color.WHITE);
                        btnTmp.setForeground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
                    }
                }

                // Méthode événementielle qui colore le bouton en rose vif à l'appui
                @Override
                public void mousePressed(MouseEvent e)
                {
                    if (!PanelJeu.this.ctrl.estPresent(Integer.parseInt(btnTmp.getText())))
                    {
                        btnTmp.setBackground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
                        btnTmp.setForeground(Color.WHITE);
                    }
                }

                // Méthode événementielle qui rétablit le style si le clic est relâché sans sélection
                @Override
                public void mouseReleased(MouseEvent e)
                {
                    if (btnTmp.getBackground() != ConstantesIHM.COULEUR_GRIS_PLACE && !btnTmp.isSelected())
                    {
                        btnTmp.setForeground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
                        btnTmp.setBackground(Color.WHITE);
                    }
                }
            });

            // Grise d'emblée le bouton si son chiffre est déjà dévoilé au départ
            if (this.ctrl.estPresent(valeurBouton))
            {
                btnTmp.setForeground(ConstantesIHM.COULEUR_GRIS_PLACE_LABEL);
                btnTmp.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
                btnTmp.setFont(new Font("Sans-Serif", Font.BOLD, 20));
            }
            else
            {
                btnTmp.setForeground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
                btnTmp.setBackground(Color.WHITE);
            }

            this.grpBtn.add(btnTmp);
            btnTmp.addActionListener(this);
            this.pnlNbres.add(btnTmp);
        }

        this.pnlNbres.revalidate();
        this.pnlNbres.repaint();
    }

    // Méthode utilitaire qui actualise l'affichage des cases, des totaux et du bouton Annuler
    public void majPlateau()
    {
        // Met à jour le fond, la bordure et le calque affiché (VALEUR ou BROUILLON) de chaque case
        for (int lig = 0; lig < this.nbLigne; lig++)
        {
            for (int col = 0; col < this.nbColonne; col++)
            {
                boolean estModifiable   = this.ctrl.getCasePlateau(lig, col).estModifiable();
                boolean estSelectionnee = (lig == this.ligSelectionne && col == this.colSelectionne);

                if (!estModifiable)
                {
                    this.tabPnlCases[lig][col].setBackground(ConstantesIHM.COULEUR_CASE_VERROUILLEE);
                    this.tabPnlCases[lig][col].setBorder(this.tabBorduresNormales[lig][col]);
                }
                else
                {
                    this.tabPnlCases[lig][col].setBackground(Color.WHITE);
                    this.tabPnlCases[lig][col].setBorder(estSelectionnee ? this.tabBorduresSelection[lig][col] : this.tabBorduresNormales[lig][col]);
                }

                JLabel     lbl      = (JLabel) this.tabPnlCases[lig][col].getComponent(0);
                CardLayout croupier = (CardLayout) this.tabPnlCases[lig][col].getLayout();
                int        chiffre  = this.ctrl.getNombreCasePlateau(lig, col);

                // Affiche la grille de brouillon si la case est vide, sinon le chiffre placé
                if (chiffre == 0)
                {
                    lbl.setText("");
                    croupier.show(this.tabPnlCases[lig][col], "BROUILLON");
                }
                else
                {
                    lbl.setText(String.valueOf(chiffre));
                    lbl.setFont(new Font("Sans-Serif", estModifiable ? Font.ITALIC : Font.BOLD, 24));
                    croupier.show(this.tabPnlCases[lig][col], "VALEUR");
                }
            }
        }

        // Colore les totaux des lignes en vert ou rouge uniquement après un clic sur Valider
        for (int lig = 0; lig < this.nbLigne; lig++)
            this.tabLblTotauxLignes[lig].setForeground(this.modeValidation ? (this.ctrl.estLigneValide(lig) ? ConstantesIHM.COULEUR_VERT_VALIDE : Color.RED) : null);

        // Colore les totaux des colonnes en vert ou rouge uniquement après un clic sur Valider
        for (int col = 0; col < this.nbColonne; col++)
            this.tabLblTotauxColonnes[col].setForeground(this.modeValidation ? (this.ctrl.estColonneValide(col) ? ConstantesIHM.COULEUR_VERT_VALIDE : Color.RED) : null);

        // Active ou désactive le bouton Annuler selon l'état de la pile d'historique
        this.btnAnnuler.setEnabled(this.ctrl.peutAnnuler());
        if (!this.btnAnnuler.isEnabled())
        {
            this.btnAnnuler.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            if (this.pnlBoutons != null && this.btnAnnuler.getMousePosition() != null)
                this.pnlBoutons.setCursor(java.awt.dnd.DragSource.DefaultCopyNoDrop);
        }

        this.repaint();
    }

    // Méthode utilitaire qui met à jour l'état visuel des 9 boutons du pavé selon les chiffres placés
    public void majBoutons()
    {
        for (AbstractButton btn : Collections.list(this.grpBtn.getElements()))
        {
            int numero = Integer.parseInt(btn.getText());
            if (this.ctrl.estPresent(numero))
            {
                btn.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
                btn.setForeground(ConstantesIHM.COULEUR_GRIS_PLACE_LABEL);
            }
            else
            {
                btn.setBackground(Color.WHITE);
                btn.setForeground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
                btn.setSelected(false);
            }
            btn.setEnabled(true);
        }
    }

    // Méthode utilitaire qui initialise le compteur d'indices selon la difficulté choisie
    private void initBoutonIndice()
    {
        int difficulte = this.ctrl.getDifficulte();
        this.nbIndicesRestants = (difficulte == 0) ? 2 : (difficulte == 1 ? 1 : 0);
        this.btnIndice.setText("  " + this.nbIndicesRestants);
        this.btnIndice.setEnabled(this.nbIndicesRestants > 0);
    }

    // Méthode utilitaire qui réinitialise le nombre maximal de validations autorisées
    private void initNbValidations()
    {
        this.nbValidationsRestantes = NB_VALIDATIONS_MAX;
        this.majAffichageValidation();
    }

    // Méthode utilitaire qui met à jour le texte du compteur de validations restantes
    private void majAffichageValidation()
    {
        this.lblNbValidations.setText(String.valueOf(this.nbValidationsRestantes));
    }

    // Méthode utilitaire qui désélectionne le chiffre actif sur le pavé numérique
    private void reinitialiserSelectionPave()
    {
        this.btnActif = null;
        if (this.grpBtn != null)
            this.grpBtn.clearSelection();
    }

    // Méthode utilitaire qui efface tous les chiffres et brouillons saisis pour recommencer la grille actuelle
    public void reset()
    {
        this.ligSelectionne = -1;
        this.colSelectionne = -1;
        this.modeValidation = false;
        this.modeBrouillon  = false;
        this.tempsDebut     = System.currentTimeMillis();

        this.btnBrouillon.setBackground(Color.WHITE);
        this.btnGomme.setEnabled(true);
        this.reinitialiserSelectionPave();

        // Parcourt toutes les cases pour vider les notes de brouillon et les chiffres modifiables
        for (int lig = 0; lig < this.nbLigne; lig++)
        {
            for (int col = 0; col < this.nbColonne; col++)
            {
                JPanel pnlBrouillon = (JPanel) this.tabPnlCases[lig][col].getComponent(1);
                for (int i = 0; i < 9; i++)
                    ((JLabel) pnlBrouillon.getComponent(i)).setText("");

                if (this.ctrl.getCasePlateau(lig, col).estModifiable())
                {
                    JLabel lblContenu = (JLabel) this.tabPnlCases[lig][col].getComponent(0);
                    if (!lblContenu.getText().isEmpty() && !lblContenu.getText().equals("0"))
                    {
                        this.ctrl.effacerChiffre(lig, col);
                        lblContenu.setText("");
                        lblContenu.setForeground(null);
                    }
                }
            }
        }

        this.ctrl.viderPile();
        this.majBoutons();
        this.majPlateau();
    }

    // Méthode utilitaire qui génère une nouvelle grille de même difficulté et réinitialise l'interface
    public void restart()
    {
        this.ctrl.resetJeu();

        this.ligSelectionne = -1;
        this.colSelectionne = -1;
        this.modeValidation = false;
        this.modeBrouillon  = false;
        this.tempsDebut     = System.currentTimeMillis();

        this.btnBrouillon.setBackground(Color.WHITE);
        this.btnGomme.setEnabled(true);
        this.btnActif = null;

        this.initBoutonIndice();
        this.initNbValidations();
        this.dessinerPlateau();
        this.dessinerBoutons();
        this.majPlateau();
    }

    // Méthode utilitaire qui supprime le chiffre d'une case modifiable et réactive son bouton
    private void effacerCase(int lig, int col)
    {
        if (lig == -1 || col == -1) return;

        JLabel lblContenu    = (JLabel) this.tabPnlCases[lig][col].getComponent(0);
        String chiffreEfface = lblContenu.getText();

        if (chiffreEfface.isEmpty() || chiffreEfface.equals("0")) return;

        // Efface le chiffre côté métier et réactive la touche correspondante du pavé
        if (this.ctrl.getCasePlateau(lig, col).estModifiable())
        {
            this.ctrl.effacerChiffre(lig, col);
            lblContenu.setText("");
            lblContenu.setForeground(null);

            if (this.grpBtn != null)
            {
                for (AbstractButton btn : Collections.list(this.grpBtn.getElements()))
                {
                    if (btn.getText().equals(chiffreEfface))
                    {
                        btn.setBackground(Color.WHITE);
                        btn.setForeground(ConstantesIHM.COULEUR_ROSE_CERVEAU);
                        break;
                    }
                }
            }
        }
    }

    // Méthode utilitaire qui inscrit un chiffre dans une case et grise le bouton correspondant
    private void placerChiffre(int lig, int col, int valeur)
    {
        JLabel lblContenu = (JLabel) this.tabPnlCases[lig][col].getComponent(0);
        lblContenu.setForeground(Color.BLACK);

        this.ctrl.setNbrCase(lig, col, valeur);

        // Grise la touche du pavé numérique correspondant au chiffre posé
        if (this.grpBtn != null)
        {
            for (AbstractButton btn : Collections.list(this.grpBtn.getElements()))
            {
                if (btn.getText().equals(String.valueOf(valeur)))
                {
                    btn.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
                    btn.setForeground(ConstantesIHM.COULEUR_GRIS_PLACE_LABEL);
                    this.grpBtn.clearSelection();
                    break;
                }
            }
        }

        this.btnActif       = null;
        this.ligSelectionne = -1;
        this.colSelectionne = -1;
        this.modeValidation = false;
    }

    // Méthode utilitaire qui ajoute ou retire un chiffre candidat dans la grille de brouillon
    private void basculerNoteBrouillon(int lig, int col, int valeur)
    {
        JPanel pnlBrouillon = (JPanel) this.tabPnlCases[lig][col].getComponent(1);
        JLabel lblPetitNum  = (JLabel) pnlBrouillon.getComponent(valeur - 1);
        lblPetitNum.setText(lblPetitNum.getText().isEmpty() ? String.valueOf(valeur) : "");
    }

    /*-------------------------------*/
    /*       Classes internes        */
    /*-------------------------------*/

    // Classe privée interne GereSouris : gère les clics et le survol de la souris sur une case du plateau
    private class GereSouris extends MouseAdapter
    {
        private int ligSouris; // Indice de ligne de la case écoutée
        private int colSouris; // Indice de colonne de la case écoutée

        // Constructeur qui mémorise les coordonnées de la case associée à l'écouteur
        public GereSouris(int lig, int col)
        {
            this.ligSouris = lig;
            this.colSouris = col;
        }

        // Méthode événementielle qui traite le clic droit (effacement) et le clic gauche (sélection, échange, placement ou brouillon)
        @Override
        public void mousePressed(MouseEvent e)
        {
            if (PanelJeu.this.ctrl.estGagne()) return;
            if (!PanelJeu.this.ctrl.getCasePlateau(this.ligSouris, this.colSouris).estModifiable()) return;

            // Effacement rapide par clic droit sur une case modifiable
            if (SwingUtilities.isRightMouseButton(e))
            {
                PanelJeu.this.effacerCase(this.ligSouris, this.colSouris);
                PanelJeu.this.modeValidation = false;
                PanelJeu.this.ligSelectionne = -1;
                PanelJeu.this.colSelectionne = -1;
                PanelJeu.this.majPlateau();
                return;
            }

            // Mode normal : gestion de l'échange de cases, de la sélection et du placement
            if (!PanelJeu.this.modeBrouillon)
            {
                // Échange les chiffres si une première case remplie est sélectionnée sans touche du pavé active
                if (PanelJeu.this.ligSelectionne != -1 && PanelJeu.this.btnActif == null)
                {
                    int     valPremiereCase  = PanelJeu.this.ctrl.getNombreCasePlateau(PanelJeu.this.ligSelectionne, PanelJeu.this.colSelectionne);
                    boolean sourceModifiable = PanelJeu.this.ctrl.getCasePlateau(PanelJeu.this.ligSelectionne, PanelJeu.this.colSelectionne).estModifiable();

                    if (valPremiereCase != 0 && sourceModifiable
                        && (PanelJeu.this.ligSelectionne != this.ligSouris || PanelJeu.this.colSelectionne != this.colSouris))
                    {
                        PanelJeu.this.ctrl.echangerNbr(PanelJeu.this.ligSelectionne, PanelJeu.this.colSelectionne, this.ligSouris, this.colSouris);
                        PanelJeu.this.modeValidation = false;
                        PanelJeu.this.ligSelectionne = -1;
                        PanelJeu.this.colSelectionne = -1;
                        PanelJeu.this.majPlateau();
                        return;
                    }
                }

                // Sélectionne la case cliquée ou la désélectionne si on reclique dessus
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

                // Place le chiffre sélectionné sur le pavé si la case est vide
                if (PanelJeu.this.btnActif != null && PanelJeu.this.ctrl.getNombreCasePlateau(this.ligSouris, this.colSouris) == 0)
                {
                    int valeur = Integer.parseInt(PanelJeu.this.btnActif.getText());
                    if (PanelJeu.this.ctrl.estPresent(valeur))
                    {
                        PanelJeu.this.btnActif.setSelected(false);
                        PanelJeu.this.btnActif.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
                        PanelJeu.this.btnActif = null;
                    }
                    else
                    {
                        PanelJeu.this.placerChiffre(this.ligSouris, this.colSouris, valeur);
                    }
                }

                PanelJeu.this.majPlateau();
            }
            // Mode brouillon : ajoute ou retire une note candidate sur une case vide
            else if (PanelJeu.this.btnActif != null && PanelJeu.this.ctrl.getNombreCasePlateau(this.ligSouris, this.colSouris) == 0)
            {
                int valeur = Integer.parseInt(PanelJeu.this.btnActif.getText());
                if (PanelJeu.this.ctrl.estPresent(valeur))
                {
                    PanelJeu.this.btnActif.setSelected(false);
                    PanelJeu.this.btnActif.setBackground(ConstantesIHM.COULEUR_GRIS_PLACE);
                    PanelJeu.this.btnActif = null;
                }
                else
                {
                    PanelJeu.this.basculerNoteBrouillon(this.ligSouris, this.colSouris, valeur);
                }
            }
        }

        // Méthode événementielle qui met en surbrillance la case survolée si elle est modifiable
        @Override
        public void mouseEntered(MouseEvent e)
        {
            if (PanelJeu.this.ctrl.estGagne()) return;

            if (!PanelJeu.this.ctrl.getCasePlateau(this.ligSouris, this.colSouris).estModifiable())
            {
                PanelJeu.this.tabPnlCases[this.ligSouris][this.colSouris].setCursor(java.awt.dnd.DragSource.DefaultCopyNoDrop);
                return;
            }

            PanelJeu.this.tabPnlCases[this.ligSouris][this.colSouris].setCursor(new Cursor(Cursor.HAND_CURSOR));
            if (PanelJeu.this.ligSelectionne != this.ligSouris || PanelJeu.this.colSelectionne != this.colSouris)
                PanelJeu.this.tabPnlCases[this.ligSouris][this.colSouris].setBackground(ConstantesIHM.COULEUR_ROSE_SURVOL);
        }

        // Méthode événementielle qui restaure la couleur de fond initiale lorsque la souris quitte la case
        @Override
        public void mouseExited(MouseEvent e)
        {
            if (PanelJeu.this.ctrl.estGagne()) return;

            PanelJeu.this.tabPnlCases[this.ligSouris][this.colSouris].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            if (!PanelJeu.this.ctrl.getCasePlateau(this.ligSouris, this.colSouris).estModifiable()) return;

            if (PanelJeu.this.ligSelectionne != this.ligSouris || PanelJeu.this.colSelectionne != this.colSouris)
                PanelJeu.this.tabPnlCases[this.ligSouris][this.colSouris].setBackground(Color.WHITE);
        }
    }

    // Classe privée interne GereDepot : gère la réception d'un chiffre par glisser-déposer sur une case
    private class GereDepot extends TransferHandler
    {
        private int ligCible; // Indice de ligne de la case cible du dépôt
        private int colCible; // Indice de colonne de la case cible du dépôt

        // Constructeur qui mémorise les coordonnées de la case cible du dépôt
        public GereDepot(int lig, int col)
        {
            this.ligCible = lig;
            this.colCible = col;
        }

        // Méthode de transfert qui vérifie si la case cible peut recevoir le chiffre glissé
        @Override
        public boolean canImport(TransferSupport support)
        {
            if (!support.isDrop() || !support.isDataFlavorSupported(DataFlavor.stringFlavor))
                return false;

            return PanelJeu.this.ctrl.getCasePlateau(this.ligCible, this.colCible).estModifiable();
        }

        // Méthode de transfert qui dépose le chiffre glissé en valeur définitive ou en note de brouillon
        @Override
        public boolean importData(TransferSupport support)
        {
            if (!this.canImport(support)) return false;

            try
            {
                String donnees       = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                int    valeurAjoutee = Integer.parseInt(donnees);

                if (PanelJeu.this.ctrl.estPresent(valeurAjoutee)) return false;
                if (PanelJeu.this.ctrl.getNombreCasePlateau(this.ligCible, this.colCible) != 0) return false;

                // Inscrit le chiffre en note de brouillon ou en valeur définitive selon le mode actif
                if (PanelJeu.this.modeBrouillon)
                    PanelJeu.this.basculerNoteBrouillon(this.ligCible, this.colCible, valeurAjoutee);
                else
                    PanelJeu.this.placerChiffre(this.ligCible, this.colCible, valeurAjoutee);

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