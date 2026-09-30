package ihm;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class PanelJeu extends JPanel 
{
    public final Color COULEUR_FOND = new Color(200, 219, 250);
    
    private final JPanel[][]    tabPnlCases;
    private Controleur          ctrl;
    
    private FrameJeu            frame;

    private int                 nbLigne;          // Permet de stocker le nombre de lignes   du plateau
    private int                 nbColonne;        // Permet de stocker le nombre de colonnes du plateau

    private int                 ligSelectionne;   // Permet de stocker la ligne   sélectionnée lors du jeu
    private int                 colSelectionne;   // Permet de stocker la colonne sélectionnée lors du jeu

    private JPanel              pnlPlateau;
    private JPanel              pnlCentre;
    private JPanel              pnlTotal;

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
        
        this.ligSelectionne = -1;
        this.colSelectionne = -1;

        // Panel qui contiendra pnlBandeau
        JPanel pnlHaut = new JPanel();
        pnlHaut.setLayout(new BorderLayout());
        pnlHaut.setOpaque(false);

        // Panel qui contiendra le JLabel du bandeau
        JPanel pnlBandeau = new JPanel();
        pnlBandeau.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        pnlBandeau.setOpaque(false);

        // Panel qui contiendra pnlPlateau
        this.pnlCentre = new JPanel();
        this.pnlCentre.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));
        this.pnlCentre.setOpaque(false);
        
        // Panel qui affichera le Plateau
        this.pnlPlateau = new JPanel();
        this.pnlPlateau.setLayout(new GridLayout(this.nbLigne + 1, this.nbColonne + 1, 2, 2));
        //pnlPlateau              .setBackground(new Color(60, 60, 75));
        this.pnlPlateau.setPreferredSize(new Dimension(450, 450));
        this.pnlPlateau.setBackground(COULEUR_FOND);

        // Crée une ligne séparatrice à l'aide de JSeparator
        JSeparator separateur = new JSeparator(SwingConstants.HORIZONTAL);
        separateur.setForeground(new Color(150, 150, 150));
        separateur.setBackground(new Color(0, 0, 0, 0));

        // Parcours pour dessiner le plateau
        for (int lig = 0; lig <= this.nbLigne; lig++)
        {
            for (int col = 0; col <= this.nbColonne; col++)
            {
                JPanel pnlCellule = new JPanel(new GridBagLayout());
                pnlCellule          .setBorder(BorderFactory.createLineBorder(Color.BLACK));        
                pnlCellule          .setBackground(Color.WHITE);
                
                if (lig < this.nbLigne && col < this.nbColonne)
                {    
                    JLabel lblNombre = new JLabel("0");
                    lblNombre        .setOpaque(false);   // Transparent par défaut
                    lblNombre        .setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
                    lblNombre.setFont(new Font("Sans-Serif", Font.BOLD, 18));

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

        /*-------------------------------*/
        /* Positionnement des composants */
        /*-------------------------------*/

        pnlBandeau.add(new JLabel("*Texte si besoin* :"));
        
        pnlHaut.add(pnlBandeau, BorderLayout.CENTER);
        pnlHaut.add(separateur, BorderLayout.SOUTH);

        this.pnlCentre.add(this.pnlPlateau, BorderLayout.CENTER);

        this.add(pnlHaut, BorderLayout.NORTH);
        this.add(this.pnlCentre, BorderLayout.CENTER);
        
        /* ----------------------------- */
        /* Activation des Composants     */
        /* ----------------------------- */
        
        this.setVisible(true);
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
            PanelJeu.this.ligSelectionne = this.ligSouris;
            PanelJeu.this.colSelectionne = this.colSouris;

            // On récupère le JPanel qui a été cliqué
            JPanel pnlClique = tabPnlCases[this.ligSouris][this.colSouris];
            
            // On récupère le premier composant de ce panel (le JLabel)
            JLabel lblContenu = (JLabel) pnlClique.getComponent(0);

            if (pnlClique.getBackground().equals(Color.WHITE))
            {
                pnlClique.setBackground(new Color(177, 206, 252));
                lblContenu.setForeground(Color.WHITE); // On passe le texte en blanc
            }
            else
            {
                pnlClique.setBackground(Color.WHITE);
                lblContenu.setForeground(Color.BLACK); // On remet le texte en noir
            }

            PanelJeu.this.repaint();
        }
    }
}