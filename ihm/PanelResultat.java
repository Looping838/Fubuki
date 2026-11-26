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
import javax.swing.SwingConstants;

// Classe publique PanelResultat : panneau de fin de partie affichant la victoire ou la défaite
public class PanelResultat extends JPanel implements ActionListener
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    private Controleur ctrl;           // Réf. vers le contrôleur du jeu
    private FrameJeu   frame;          // Réf. vers la fenêtre principale
    private int        indice;         // Position du panneau dans tabPanel de FrameJeu
    private boolean    estVictoire;    // Indique si la partie s'est terminée par une victoire
    private String     temps;          // Temps total formaté de la partie

    private JLabel     lblResultat;    // Visuel central Victoire ou Défaite
    private JLabel     lblStats;       // Statistiques de fin de partie (temps et validations)
    private JButton    btnRecommencer; // Bouton permettant de relancer une nouvelle partie
    private JButton    btnAccueil;     // Bouton permettant de retourner à l'accueil
    private JButton    btnQuitter;     // Bouton permettant de fermer l'application

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    // Constructeur qui initialise l'écran de résultat selon l'issue de la partie
    public PanelResultat(Controleur ctrl, FrameJeu frame, int indice, boolean estVictoire, String temps)
    {
        this.setLayout(new GridBagLayout());
        this.setBackground(ConstantesIHM.COULEUR_FOND);

        this.ctrl        = ctrl;
        this.frame       = frame;
        this.indice      = indice;
        this.estVictoire = estVictoire;
        this.temps       = temps;

        this.creerComposants();
        this.positionnerComposants();
        this.activerComposants();

        this.setVisible(true);
    }

    /*-------------------------------*/
    /*    Méthodes événementielles   */
    /*-------------------------------*/

    // Méthode événementielle qui gère les clics sur Recommencer, Accueil et Quitter
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Retourne à l'accueil et réinitialise le moteur métier
        if (e.getSource() == this.btnAccueil)
        {
            this.frame.setPnl(this.frame.getPnl(0));
            this.ctrl.resetJeu();
        }
        // Relance immédiatement une nouvelle grille sur le panneau de jeu
        else if (e.getSource() == this.btnRecommencer)
        {
            this.frame.cacherMenuBar(true);
            this.frame.restartJeu();
            this.frame.setPnl(this.frame.getPnl(this.indice - 1));
        }
        // Ferme l'application
        else if (e.getSource() == this.btnQuitter)
        {
            System.exit(0);
        }
    }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    // Méthode utilitaire qui crée les boutons d'action et configure l'affichage victoire/défaite
    private void creerComposants()
    {
        this.btnRecommencer = this.creerBouton("  Recommencer une partie", ConstantesIHM.COULEUR_ROSE_CERVEAU, "./images/icones/restart.png", 35);
        this.btnAccueil     = this.creerBouton("  Retourner à l'accueil",  Color.WHITE,                        "./images/icones/accueil.png", 30);
        this.btnQuitter     = this.creerBouton("  Quitter",                Color.WHITE,                        null,                          0);

        this.lblResultat = new JLabel("", SwingConstants.CENTER);
        this.lblStats    = new JLabel("", SwingConstants.CENTER);
        this.lblStats.setFont(new Font("Noto Serif Display Black", Font.PLAIN, 28));

        // Adapte le logo et les statistiques affichés selon que la partie est gagnée ou perdue
        if (this.estVictoire)
        {
            this.lblResultat.setIcon(new ImageIcon("./images/logos/victoire.png"));
            this.lblStats.setText(this.temps + " | " + this.frame.getNbValidationsRestantes());
        }
        else
        {
            this.lblResultat.setIcon(new ImageIcon("./images/logos/defaite.png"));
        }
    }

    // Méthode utilitaire qui crée et stylise un bouton d'action de l'écran de résultat
    private JButton creerBouton(String texte, Color fond, String cheminIcone, int tailleIcone)
    {
        JButton btn = new JButton(texte);
        btn.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
        btn.setBackground(fond);
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createCompoundBorder(ConstantesIHM.RELIEF, ConstantesIHM.MARGE));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(400, 60));

        if (cheminIcone != null)
            btn.setIcon(new ImageIcon(this.frame.creerImages(cheminIcone, tailleIcone, tailleIcone)));

        return btn;
    }

    // Méthode utilitaire qui place le visuel de résultat, les statistiques et les boutons
    private void positionnerComposants()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx  = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy  = 0;
        gbc.insets = new Insets(130, 0, 80, 0);
        this.add(this.lblResultat, gbc);

        gbc.gridy  = 1;
        gbc.insets = new Insets(0, 0, 40, 0);
        this.add(this.lblStats, gbc);

        gbc.gridy  = 2;
        gbc.insets = new Insets(50, 0, 20, 0);
        this.add(this.btnRecommencer, gbc);

        gbc.gridy  = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(this.btnAccueil, gbc);

        gbc.gridy  = 4;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(this.btnQuitter, gbc);

        gbc.gridy   = 5;
        gbc.weighty = 1.0;
        this.add(Box.createVerticalGlue(), gbc);
    }

    // Méthode utilitaire qui active les écouteurs d'action et de survol sur les boutons
    private void activerComposants()
    {
        this.configurerSurvolBouton(this.btnRecommencer, ConstantesIHM.COULEUR_ROSE_CERVEAU, ConstantesIHM.COULEUR_ROSE_CERVEAU_FONCE);
        this.configurerSurvolBouton(this.btnAccueil,     Color.WHITE,                        ConstantesIHM.COULEUR_GRIS_SURVOL);
        this.configurerSurvolBouton(this.btnQuitter,     Color.WHITE,                        ConstantesIHM.COULEUR_GRIS_SURVOL);
    }

    // Méthode utilitaire qui applique les effets de survol et de clic sur un bouton
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