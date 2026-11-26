package ihm;

import controleur.Controleur;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

// Classe publique PanelChoixDiff : panneau permettant au joueur de choisir le niveau de difficulté
public class PanelChoixDiff extends JPanel implements ActionListener
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    private Controleur ctrl;          // Réf. vers le contrôleur pour initialiser la partie
    private FrameJeu   frame;         // Réf. vers la fenêtre principale
    private int        indice;        // Position du panneau dans tabPanel de FrameJeu

    private JLabel     lblTitre;      // Titre invitant à choisir une difficulté
    private JPanel     pnlDifficulte; // Conteneur alignant horizontalement les 3 cartes de difficulté
    private JButton    btnFacile;     // Carte de sélection du mode Facile (2 indices)
    private JButton    btnMoyen;      // Carte de sélection du mode Moyen (1 indice)
    private JButton    btnDifficile;  // Carte de sélection du mode Difficile (0 indice)
    private JButton    btnRetour;     // Bouton de retour vers l'écran d'accueil

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    // Constructeur qui initialise le panneau de choix de difficulté
    public PanelChoixDiff(Controleur ctrl, FrameJeu frame, int indice)
    {
        this.setLayout(new GridBagLayout());
        this.setBackground(ConstantesIHM.COULEUR_FOND);

        this.ctrl   = ctrl;
        this.frame  = frame;
        this.indice = indice;

        this.creerComposants();
        this.positionnerComposants();
        this.activerComposants();

        this.setVisible(true);
    }

    /*-------------------------------*/
    /*    Méthodes événementielles   */
    /*-------------------------------*/

    // Méthode événementielle qui traite le retour à l'accueil ou le lancement d'une partie
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Retourne au panneau d'accueil précédent
        if (e.getSource() == this.btnRetour)
        {
            this.frame.setPnl(this.frame.getPnl(this.indice - 1));
            return;
        }

        // Détermine le niveau de difficulté selon la carte cliquée
        int difficulte = -1;
        if      (e.getSource() == this.btnFacile)    difficulte = 0;
        else if (e.getSource() == this.btnMoyen)     difficulte = 1;
        else if (e.getSource() == this.btnDifficile) difficulte = 2;

        // Génère le plateau et bascule vers l'écran de jeu si une difficulté valide est choisie
        if (difficulte != -1)
        {
            this.ctrl.initPlateau(difficulte);
            this.frame.creerPanelJeu();
            this.frame.setPnl(this.frame.getPnl(this.indice + 1));
        }
    }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    // Méthode utilitaire qui instancie le titre, les trois boutons de difficulté et le bouton retour
    private void creerComposants()
    {
        this.lblTitre = new JLabel("Choisissez une difficulté :");
        this.lblTitre.setFont(new Font("Sans-Serif", Font.BOLD, 70));

        this.btnFacile    = this.creerBoutonDifficulte("Facile",    "./images/logos/etoile-pleine.png",  ConstantesIHM.COULEUR_FOND_BOUTON_FACILE);
        this.btnMoyen     = this.creerBoutonDifficulte("Moyen",     "./images/logos/etoile-pleine2.png", ConstantesIHM.COULEUR_FOND_BOUTON_MOYEN);
        this.btnDifficile = this.creerBoutonDifficulte("Difficile", "./images/logos/etoile-pleine3.png", ConstantesIHM.COULEUR_FOND_BOUTON_DIFF);

        this.btnRetour = new JButton("  Retour à l'accueil");
        this.btnRetour.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.btnRetour.setBackground(Color.WHITE);
        this.btnRetour.setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        this.btnRetour.setFocusPainted(false);
        this.btnRetour.setPreferredSize(new Dimension(250, 60));
        this.btnRetour.setIcon(new ImageIcon(this.frame.creerImages("./images/icones/accueil.png", 30, 25)));

        this.pnlDifficulte = new JPanel(new GridLayout(1, 3, 25, 0));
        this.pnlDifficulte.setOpaque(false);
    }

    // Méthode utilitaire qui crée et met en forme un bouton de sélection de difficulté
    private JButton creerBoutonDifficulte(String libelle, String cheminIcone, Color fond)
    {
        JButton btn = new JButton(libelle);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        btn.setBackground(fond);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(250, 250));
        btn.setIcon(new ImageIcon(this.frame.creerDessins(cheminIcone)));
        btn.setIconTextGap(15);
        return btn;
    }

    // Méthode utilitaire qui dispose les boutons de difficulté et le bouton retour dans le panneau
    private void positionnerComposants()
    {
        this.pnlDifficulte.add(this.btnFacile);
        this.pnlDifficulte.add(this.btnMoyen);
        this.pnlDifficulte.add(this.btnDifficile);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx  = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy  = 0;
        gbc.insets = new Insets(180, 0, 80, 0);
        this.add(this.lblTitre, gbc);

        gbc.gridy  = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(this.pnlDifficulte, gbc);

        gbc.gridy  = 2;
        gbc.insets = new Insets(50, 0, 0, 0);
        this.add(this.btnRetour, gbc);

        gbc.gridy   = 3;
        gbc.weighty = 1.0;
        this.add(Box.createVerticalGlue(), gbc);
    }

    // Méthode utilitaire qui active les écouteurs sur les boutons de difficulté et de retour
    private void activerComposants()
    {
        this.activerBoutonDifficulte(this.btnFacile,    ConstantesIHM.COULEUR_FOND_BOUTON_FACILE, ConstantesIHM.VERT_DIFFICULTE,   ConstantesIHM.OMBRE_FACILE);
        this.activerBoutonDifficulte(this.btnMoyen,     ConstantesIHM.COULEUR_FOND_BOUTON_MOYEN,  ConstantesIHM.ORANGE_DIFFICULTE, ConstantesIHM.OMBRE_MOYEN);
        this.activerBoutonDifficulte(this.btnDifficile, ConstantesIHM.COULEUR_FOND_BOUTON_DIFF,   ConstantesIHM.ROUGE_DIFFICULTE,  ConstantesIHM.OMBRE_DIFFICILE);

        this.btnRetour.addActionListener(this);
        this.btnRetour.addMouseListener(new MouseAdapter()
        {
            // Méthode événementielle qui grise légèrement le bouton Retour au survol
            @Override
            public void mouseEntered(MouseEvent e)
            {
                btnRetour.setBackground(ConstantesIHM.COULEUR_GRIS_SURVOL);
                btnRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            // Méthode événementielle qui rétablit le fond blanc du bouton Retour
            @Override
            public void mouseExited(MouseEvent e)
            {
                btnRetour.setBackground(Color.WHITE);
                btnRetour.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            // Méthode événementielle qui rétablit le fond blanc lors du clic
            @Override
            public void mousePressed(MouseEvent e)
            {
                btnRetour.setBackground(Color.WHITE);
            }
        });
    }

    // Méthode utilitaire qui configure les bordures 3D et le survol d'un bouton de difficulté
    private void activerBoutonDifficulte(JButton btn, Color fondBase, Color fondSurvol, Color couleurOmbre)
    {
        Border ombre          = BorderFactory.createMatteBorder(0, 0, 3, 3, couleurOmbre);
        Border lumiere        = BorderFactory.createMatteBorder(2, 2, 0, 0, Color.WHITE);
        Border bordureNormale = BorderFactory.createCompoundBorder(ombre, ConstantesIHM.MARGE);
        Border bordureSurvol  = BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(ombre, lumiere), ConstantesIHM.MARGE);

        btn.setBorder(bordureNormale);
        btn.addActionListener(this);
        btn.addMouseListener(new MouseAdapter()
        {
            // Méthode événementielle qui intensifie la couleur et ajoute un reflet lumineux au survol
            @Override
            public void mouseEntered(MouseEvent e)
            {
                btn.setBackground(fondSurvol);
                btn.setBorder(bordureSurvol);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            // Méthode événementielle qui restaure l'apparence normale de la carte
            @Override
            public void mouseExited(MouseEvent e)
            {
                btn.setBackground(fondBase);
                btn.setBorder(bordureNormale);
                btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
}