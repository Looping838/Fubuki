package ihm;

import controleur.Controleur;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

/**
 * Panneau d'accueil permettant de lancer une partie ou quitter l'application.
 *
 * @author Alexis COFFARD
 * @author Timothé LARBI
 * @version 2.0
 */
public class PanelAccueil extends JPanel implements ActionListener
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    /** Réf. vers la fenêtre principale */
    private FrameJeu frame;

    /** Position du panneau dans tabPanel de FrameJeu */
    private int      indice;

    /** Logo principal Fubuki */
    private JLabel   lblTitre;

    /** Slogan affiché sous le logo */
    private JLabel   lblSousTitre;

    /** Bouton permettant d'accéder au choix de difficulté */
    private JButton  btnJouer;

    /** Bouton permettant de fermer l'application */
    private JButton  btnQuitter;

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    /**
     * Constructeur qui initialise le panneau d'accueil et ses composants.
     *
     * @param ctrl Référence vers le contrôleur de l'application
     * @param frame Référence vers la fenêtre principale
     * @param indice Indice du panneau dans {@link FrameJeu}
     */
    public PanelAccueil(Controleur ctrl, FrameJeu frame, int indice)
    {
        this.setLayout(new GridBagLayout());
        this.setBackground(ConstantesIHM.COULEUR_FOND);

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

    /**
     * Méthode événementielle qui gère les clics sur les boutons Jouer et Quitter.
     *
     * @param e Événement d'action déclenché par le bouton
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Ouvre l'écran de choix de difficulté
        if (e.getSource() == this.btnJouer)
        {
            this.frame.creerPanelChoixDiff();
            this.frame.setPnl(this.frame.getPnl(this.indice + 1));
        }
        // Ferme proprement l'application
        else if (e.getSource() == this.btnQuitter)
        {
            System.exit(0);
        }
    }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    /**
     * Méthode utilitaire qui instancie et configure les labels et boutons de l'accueil.
     */
    private void creerComposants()
    {
        this.lblTitre     = new JLabel(new ImageIcon("./images/logos/Fubuki.gif"));
        this.lblSousTitre = new JLabel("- Le casse-tête des additions -");
        this.lblSousTitre.setFont(new Font("Noto Serif Display Black", Font.PLAIN, 32));

        this.btnJouer   = this.creerBouton("Jouer !", ConstantesIHM.COULEUR_ROSE_CERVEAU);
        this.btnQuitter = this.creerBouton("Quitter", Color.WHITE);
    }

    /**
     * Méthode utilitaire qui crée et stylise un bouton standard de l'accueil.
     *
     * @param texte Libellé affiché sur le bouton
     * @param fond Couleur de fond du bouton
     * @return Bouton configuré
     */
    private JButton creerBouton(String texte, Color fond)
    {
        JButton btn = new JButton(texte);
        btn.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
        btn.setBackground(fond);
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(250, 60));
        return btn;
    }

    /**
     * Méthode utilitaire qui place les composants sur la grille GridBagLayout.
     */
    private void positionnerComposants()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx  = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy  = 0;
        gbc.insets = new Insets(130, 0, 50, 0);
        this.add(this.lblTitre, gbc);

        gbc.gridy  = 1;
        gbc.insets = new Insets(0, 0, 120, 0);
        this.add(this.lblSousTitre, gbc);

        gbc.gridy  = 2;
        gbc.insets = new Insets(50, 0, 20, 0);
        this.add(this.btnJouer, gbc);

        gbc.gridy  = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(this.btnQuitter, gbc);

        gbc.gridy   = 4;
        gbc.weighty = 1.0;
        this.add(Box.createVerticalGlue(), gbc);
    }

    /**
     * Méthode utilitaire qui associe les écouteurs d'action et de survol aux boutons.
     */
    private void activerComposants()
    {
        this.configurerSurvolBouton(this.btnJouer,   ConstantesIHM.COULEUR_ROSE_CERVEAU, ConstantesIHM.COULEUR_ROSE_CERVEAU_FONCE);
        this.configurerSurvolBouton(this.btnQuitter, Color.WHITE,                        ConstantesIHM.COULEUR_GRIS_SURVOL);
    }

    /**
     * Méthode utilitaire qui applique les effets visuels de survol et de clic sur un bouton.
     *
     * @param bouton Bouton à configurer
     * @param couleurBase Couleur au repos
     * @param couleurSurvol Couleur au survol de la souris
     */
    private void configurerSurvolBouton(JButton bouton, Color couleurBase, Color couleurSurvol)
    {
        bouton.addActionListener(this);
        bouton.addMouseListener(new MouseAdapter()
        {
            // Méthode événementielle qui applique la couleur de survol et le curseur main
            @Override
            public void mouseEntered(MouseEvent e)
            {
                bouton.setBackground(couleurSurvol);
                bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            // Méthode événementielle qui rétablit la couleur et le curseur par défaut
            @Override
            public void mouseExited(MouseEvent e)
            {
                bouton.setBackground(couleurBase);
                bouton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            // Méthode événementielle qui réinitialise le fond lors de l'appui
            @Override
            public void mousePressed(MouseEvent e)
            {
                bouton.setBackground(couleurBase);
            }
        });
    }
}