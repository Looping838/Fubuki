package ihm;

import controleur.Controleur;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PanelAccueil extends JPanel implements ActionListener
{
    public static final Color   LUMIERE         = Color.WHITE;
    public static final Color   OMBRE           = Color.LIGHT_GRAY;
    public static final Border  RELIEF          = BorderFactory.createRaisedBevelBorder();          // Relief 3D
    public static final Border  MARGE           = BorderFactory.createEmptyBorder(10, 25, 10, 25);  // Marge invisible (H, G, B, D)
    
    private Controleur ctrl;
    private FrameJeu   frame;
    private int        indice;

    private JButton    btnJouer;
    private JButton    btnQuitter;

    public PanelAccueil(Controleur ctrl, FrameJeu frame, int indice)
    {
        // On applique le GridBagLayout directement au panneau principal
        this.setLayout(new GridBagLayout());
        this.setBackground(PanelJeu.COULEUR_FOND);

        this.ctrl   = ctrl;
        this.frame  = frame;
        this.indice = indice;

        /*-------------------------------*/
        /*   Création des composants     */
        /*-------------------------------*/

        JLabel lblTitre = new JLabel("Fubuki");
        lblTitre.setFont(new Font("Sans-Serif", Font.BOLD, 80)); // Un peu plus gros pour l'accueil !

        this.btnJouer = new JButton("Jouer !");
        this.btnJouer.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
        this.btnJouer.setBackground(Color.WHITE);
        this.btnJouer.setBorder(BorderFactory.createCompoundBorder(RELIEF, MARGE));
        this.btnJouer.setFocusPainted(false);
        this.btnJouer.setPreferredSize(new Dimension(250, 60)); 

        this.btnQuitter = new JButton("Quitter");
        this.btnQuitter.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
        this.btnQuitter.setBackground(Color.WHITE);
        this.btnQuitter.setBorder(BorderFactory.createCompoundBorder(RELIEF, MARGE));
        this.btnQuitter.setFocusPainted(false);
        this.btnQuitter.setPreferredSize(new Dimension(250, 60));

        /*-------------------------------*/
        /* Positionnement des composants */
        /*-------------------------------*/
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Tout sera empilé dans la même colonne
        gbc.anchor = GridBagConstraints.CENTER; // Centré horizontalement

        // Le Titre
        gbc.gridy = 0; // Tout en haut de notre grille
        // Insets(Haut, Gauche, Bas, Droite) = grand espace sous le titre
        gbc.insets = new Insets(130, 0, 150, 0);
        this.add(lblTitre, gbc);

        // Le bouton Jouer
        gbc.gridy = 1; // En dessous du titre
        gbc.insets = new Insets(50, 0, 20, 0); // Petit espace de 20px sous ce bouton
        this.add(this.btnJouer, gbc);

        // Le bouton Quitter
        gbc.gridy = 2; // Tout en bas
        gbc.insets = new Insets(0, 0, 0, 0); // Pas d'espace en dessous
        this.add(this.btnQuitter, gbc);

        // 3. Le bouton Quitter
        gbc.gridy = 2; 
        gbc.insets = new Insets(0, 0, 0, 0); 
        this.add(this.btnQuitter, gbc);

        // 4. LE RESSORT INVISIBLE
        gbc.gridy = 3; // On le place sur une 4ème ligne
        gbc.weighty = 1.0; // La magie opère ici : il aspire tout l'espace vertical restant !
        this.add(new JLabel(""), gbc); // On ajoute un texte vide

        /* ----------------------------- */
        /* Activation des Composants     */
        /* ----------------------------- */

        this.btnJouer.addActionListener(this);
        this.btnQuitter.addActionListener(this);

        this.setVisible(true);
    }

    public void actionPerformed ( ActionEvent e )
	{
        if (e.getSource() == this.btnJouer)
        {
            //this.ctrl.initPlateau(0); // changer 0 par difficulter quand boutton placer 
            //this.frame.creerPanelJeu();
            this.frame.creerPanelChoixDiff();
            this.frame.setPnl(this.frame.getPnl(this.indice + 1));
        }

        if (e.getSource() == this.btnQuitter)
        {
            System.exit(0);
        }
    }
}