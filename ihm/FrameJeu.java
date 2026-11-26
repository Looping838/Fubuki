package ihm;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Classe publique FrameJeu : fenêtre principale gérant l'affichage et la navigation entre les panneaux
public class FrameJeu extends JFrame
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    private Controleur ctrl;       // Réf. vers le contrôleur de l'application
    private JPanel     panelActif; // Panneau actuellement affiché au centre de la fenêtre
    private JPanel[]   tabPanel;   // Tab. des écrans du jeu (0:Accueil, 1:Difficulté, 2:Jeu, 3:Résultat)

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    // Constructeur qui initialise la fenêtre principale et affiche le panneau d'accueil
    public FrameJeu(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.setTitle("Fubuki");
        this.setSize(800, 800);
        this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon("./images/icones/fubuki.png").getImage());

        this.tabPanel    = new JPanel[4];
        this.tabPanel[0] = new PanelAccueil(ctrl, this, 0);
        this.panelActif  = this.tabPanel[0];

        this.add(this.panelActif, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    /*-------------------------------*/
    /*      Getters & Setters        */
    /*-------------------------------*/

    // Getter qui retourne le panneau stocké à l'indice demandé
    public JPanel getPnl(int indice)
    {
        return this.tabPanel[indice];
    }

    // Getter qui retourne le texte formaté des validations restantes depuis PanelJeu
    public String getNbValidationsRestantes()
    {
        return String.format("Nombre de validations restantes : %d ", ((PanelJeu) this.tabPanel[2]).getNbValidationsRestantes());
    }

    // Setter qui remplace le panneau actif affiché au centre de la fenêtre
    public void setPnl(JPanel pnl)
    {
        this.remove(this.panelActif);
        this.panelActif = pnl;
        this.add(this.panelActif, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    /*-------------------------------*/
    /*      Méthodes utilitaires     */
    /*-------------------------------*/

    // Méthode utilitaire qui affiche ou masque la barre de menu du panneau de jeu
    public void cacherMenuBar(boolean estVisible) { ((PanelJeu) this.tabPanel[2]).cacherMenuBar(estVisible); }

    // Méthode utilitaire qui instancie le panneau de choix de difficulté
    public void creerPanelChoixDiff()             { this.tabPanel[1] = new PanelChoixDiff(this.ctrl, this, 1); }

    // Méthode utilitaire qui instancie le panneau principal de la partie
    public void creerPanelJeu()                   { this.tabPanel[2] = new PanelJeu      (this.ctrl, this, 2); }

    // Méthode utilitaire qui relance une nouvelle partie dans le panneau de jeu
    public void restartJeu()                      { ((PanelJeu) this.tabPanel[2]).restart(); }

    // Méthode utilitaire qui crée et affiche l'écran de victoire ou de défaite
    public void afficherPanelResultat(boolean estVictoire, String temps)
    {
        this.tabPanel[3] = new PanelResultat(this.ctrl, this, 3, estVictoire, temps);
        this.setPnl(this.tabPanel[3]);
    }

    // Méthode utilitaire qui charge et redimensionne une image aux dimensions données
    public Image creerImages(String chemin, int longueur, int largeur)
    {
        return new ImageIcon(chemin).getImage().getScaledInstance(longueur, largeur, Image.SCALE_SMOOTH);
    }

    // Méthode utilitaire qui charge une image à sa taille d'origine
    public Image creerDessins(String chemin)
    {
        return new ImageIcon(chemin).getImage();
    }
}