package ihm;

import controleur.Controleur;
import java.awt.*;
import javax.swing.*;

public class FrameJeu extends JFrame
{
    private Controleur ctrl;
    
    private JPanel   panelActif;
    private JPanel[] tabPanel;
    
    public FrameJeu (Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.setTitle("Fubuki");
		this.setSize(800, 800);
		this.setLayout(new BorderLayout());

        ImageIcon icone = new ImageIcon("./images/icones/fubuki.png");
        this.setIconImage(icone.getImage());

        this.creerMenuBar();

        this.tabPanel = new JPanel[4];
        this.tabPanel[0] = new PanelAccueil(ctrl, this, 0);
        
        this.panelActif = this.tabPanel[0];

        this.add(this.panelActif, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /*-------------------------------*/
    /*            Getters            */
    /*-------------------------------*/

    public JPanel getPnl(int indice) {return this.tabPanel[indice];}

    /*-------------------------------*/
    /*            Setters            */
    /*-------------------------------*/
    
    // Méthode permettant de changer de panel sur l'écran
	public void setPnl(JPanel pnl)
	{
		this.remove(this.panelActif);  // On enlève le panel de la Frame
		this.panelActif = pnl;         // Le panelActif devient le nouveau panel passé en paramètre
		this.add(this.panelActif);     // Le nouveau panelActif est ajouté à la Frame

        // On actualise
		this.repaint();
		this.revalidate();
	}

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    public void creerPanelChoixDiff()   { this.tabPanel[1] = new PanelChoixDiff     (ctrl , this, 1); }
    public void creerPanelJeu()         { this.tabPanel[2] = new PanelJeu           (ctrl , this, 2); }
    
    public void afficherPanelResultat(boolean estVictoire)
    {
        this.tabPanel[3] = new PanelResultat(ctrl, this, 3, estVictoire);
        this.setPnl(this.tabPanel[3]);
    }

    public void restartJeu() 
    { 
        PanelJeu pnlJeu = (PanelJeu) this.tabPanel[2];
        pnlJeu.restart(); 
    }

    // Permet de créer une image à partir d'un chemin et de la redimensionner
    public Image creerImages (String chemin, int longueur, int largeur)
    {
        ImageIcon   imgOriginale;
        Image       imgRedimensionnee;

        imgOriginale        = new ImageIcon(chemin);
        imgRedimensionnee   = imgOriginale.getImage().getScaledInstance(longueur, largeur, Image.SCALE_SMOOTH);
		
        return imgRedimensionnee;
    }

    // Permet de créer la barre de menu et ses sous-menus
    private void creerMenuBar()
    {
        UIManager.put("Menu.selectionBackground", ConstantesIHM.COULEUR_GRIS_SURVOL);
        UIManager.put("Menu.selectionForeground", Color.BLACK);
        UIManager.put("MenuItem.selectionBackground", ConstantesIHM.COULEUR_GRIS_SURVOL);
        UIManager.put("MenuItem.selectionForeground", Color.BLACK);
        UIManager.put("MenuItem.background", Color.WHITE);
        
        // Création de la barre de menu principale
        JMenuBar barreMenu      = new JMenuBar();
        barreMenu               .setOpaque(true);
        barreMenu               .setBackground(ConstantesIHM.COULEUR_FOND);
        barreMenu               .setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ConstantesIHM.COULEUR_SEPARATEUR));

        // Création de l'onglet "Aide"
        JMenu menuAide          = new JMenu("Aide");
        menuAide                .setFont(new Font("Sans-Serif", Font.BOLD, 18));            // Change la police de l'onglet "Aide"
        menuAide                .setForeground(Color.BLACK);                                // Change la couleur du texte de l'onglet "Aide"
        menuAide                .setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));    // Ajoute un peu d'espace autour du texte de l'onglet "Aide"

        // Création du sous-bouton "Règles du jeu"
        JMenuItem itemRegles    = new JMenuItem("Règles du jeu");
        itemRegles              .setFont(new Font("Sans-Serif", Font.PLAIN, 16));           
        itemRegles              .setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // On ajoute l'action quand on clique sur "Règles du jeu"
        itemRegles.addActionListener(new java.awt.event.ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) 
            {
               String message = "Bienvenue dans Fubuki !\n\n"
                               + "Le but du jeu est de remplir la grille en respectant ces règles :\n\n"
                               + "  •  Complétez avec les chiffres de 1 à 9.\n"
                               + "  •  Chaque chiffre ne peut être utilisé qu'une seule fois.\n"
                               + "  •  La somme des lignes et des colonnes doit correspondre aux totaux indiqués.\n\n"
                               + "Utilisez le clic simple ou le glisser-déposer pour jouer.";

                ImageIcon iconeRegle = new ImageIcon(FrameJeu.this.creerImages("./images/icones/regles.png", 50, 50));

                // Affichage
                JOptionPane.showMessageDialog(FrameJeu.this, message, "Règles du Fubuki", JOptionPane.PLAIN_MESSAGE, iconeRegle);
            }
        });


        JMenuItem itemDifficulte    = new JMenuItem("Les Niveaux de Difficultés");
        itemDifficulte              .setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        itemDifficulte              .setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        itemDifficulte.addActionListener(new java.awt.event.ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) 
            {
                String message = "Fubuki propose 3 niveaux de difficulté :\n\n"
                               + "  •  FACILE : 2 indices.\n"
                               + "  •  MOYEN : 1 indice.\n"
                               + "  •  DIFFICILE : Aucun indice.\n\n"
                               + "Plus la difficulté est élevée, moins de chiffres seront dévoilés au départ.";
                
                ImageIcon iconeDifficulte = new ImageIcon(FrameJeu.this.creerImages("./images/icones/difficulte.png", 50, 50));

                JOptionPane.showMessageDialog(FrameJeu.this, message, "Niveaux de Difficulté", JOptionPane.PLAIN_MESSAGE, iconeDifficulte);
            }
        });


        JMenuItem itemOutils    = new JMenuItem("Utilisation des Outils");
        itemOutils              .setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        itemOutils              .setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        itemOutils.addActionListener(new java.awt.event.ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) 
            {
                String message = "Outils à votre disposition en cours de partie :\n\n"
                               + "  •  GOMME : Efface le chiffre de la case sélectionnée.\n"
                               + "  •  INDICE : Révèle une case correcte au hasard et la verrouille.\n"
                               + "  •  VALIDER : Vérifie votre grille. Attention au nombre d'essais restants !\n"
                               + "  •  ANNULER : Annule votre dernier coup (chiffre placé, effacé ou échangé).";
                
                ImageIcon iconeOutils = new ImageIcon(FrameJeu.this.creerImages("./images/icones/outils.png", 50, 50));

                JOptionPane.showMessageDialog(FrameJeu.this, message, "Utilisation des Outils", JOptionPane.PLAIN_MESSAGE, iconeOutils);
            }
        });


        JMenuItem itemBrouillon    = new JMenuItem("Le Mode Brouillon");
        itemBrouillon              .setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        itemBrouillon              .setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        itemBrouillon.addActionListener(new java.awt.event.ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) 
            {
                String message = "Le Mode Brouillon vous permet de tester plusieurs options sur une case.\n\n"
                               + "  1.  Activez le mode (le bouton devient bleu).\n"
                               + "  2.  Cliquez sur une case vide, puis sélectionnez vos chiffres à noter.\n"
                               + "  3.  Pour retirer une note, sélectionnez à nouveau le chiffre.\n"
                               + "  4.  Désactivez le mode pour reprendre la partie normalement.";

                ImageIcon iconeBrouillon = new ImageIcon(FrameJeu.this.creerImages("./images/icones/brouillon.png", 50, 50));
                
                // Affichage (on garde INFORMATION_MESSAGE pour l'icône)
                JOptionPane.showMessageDialog(FrameJeu.this, message, "Le Mode Brouillon", JOptionPane.PLAIN_MESSAGE, iconeBrouillon);
            }
        });


        JMenuItem itemAstuces   = new JMenuItem("Astuces & Raccourcis");
        itemAstuces              .setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        itemAstuces              .setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        itemAstuces.addActionListener(new java.awt.event.ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) 
            {
                String message = "Astuces pour jouer plus rapidement :\n\n"
                               + "  •  GLISSER-DÉPOSER : Maintenez le clic sur un chiffre du pavé\n"
                               + "     et relâchez-le directement sur une case.\n\n"
                               + "  •  ANNULER (CTRL + Z) : Utilisez ce raccourci clavier pour\n"
                               + "     revenir en arrière sans utiliser la souris.\n\n"
                               + "  •  ÉCHANGE : Cliquez sur deux chiffres déjà placés sur le plateau\n"
                               + "     pour échanger leurs positions instantanément.";

                ImageIcon iconeAstuce = new ImageIcon(FrameJeu.this.creerImages("./images/icones/indice.png", 50, 50));

                JOptionPane.showMessageDialog(FrameJeu.this, message, "Astuces & Raccourcis", JOptionPane.PLAIN_MESSAGE, iconeAstuce);
            }
        });


        JMenuItem itemAPropos   = new JMenuItem("A propos");
        itemAPropos              .setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        itemAPropos              .setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        itemAPropos.addActionListener(new java.awt.event.ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) 
            {
                String message = "Fubuki - Version 1.0\n\n"
                               + "Projet informatique développé par :\n"
                               + "  •  Alexis COFFARD (Interface Graphique & Ergonomie)\n"
                               + "  •  Timothé LARBI (Moteur logique & Métier)\n\n"
                               + "Merci d'avoir joué !";

                ImageIcon iconeAPropos = new ImageIcon(FrameJeu.this.creerImages("./images/icones/a-propos.png", 50, 50));

                JOptionPane.showMessageDialog(FrameJeu.this, message, "À propos de Fubuki", JOptionPane.PLAIN_MESSAGE, iconeAPropos);
            }
        });

        menuAide.add(itemRegles);
        menuAide.addSeparator();
        menuAide.add(itemDifficulte);
        menuAide.addSeparator();
        menuAide.add(itemOutils);
        menuAide.addSeparator();
        menuAide.add(itemBrouillon);
        menuAide.addSeparator();
        menuAide.add(itemAstuces);
        menuAide.addSeparator();
        menuAide.add(itemAPropos);

        barreMenu.add(menuAide);

        // On applique la barre à la fenêtre (Frame)
        this.setJMenuBar(barreMenu);
    }
}