package ihm;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Fenêtre principale gérant l'affichage et la navigation entre les panneaux de l'application.
 *
 * @author Alexis COFFARD
 * @author Timothé LARBI
 * @version 2.0
 */
public class FrameJeu extends JFrame
{
    /*-------------------------------*/
    /*           Attributs           */
    /*-------------------------------*/

    /** Réf. vers le contrôleur de l'application */
    private Controleur ctrl;

    /** Panneau actuellement affiché au centre de la fenêtre */
    private JPanel     panelActif;

    /** Tableau des écrans du jeu (0 : Accueil, 1 : Difficulté, 2 : Jeu, 3 : Résultat) */
    private JPanel[]   tabPanel;

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    /**
     * Constructeur qui initialise la fenêtre principale et affiche le panneau d'accueil.
     *
     * @param ctrl Référence vers le contrôleur de l'application
     */
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

    /**
     * Getter qui retourne le panneau stocké à l'indice demandé.
     *
     * @param indice Indice du panneau dans le tableau (0 à 3)
     * @return Instance de {@link JPanel} correspondante
     */
    public JPanel getPnl(int indice)
    {
        return this.tabPanel[indice];
    }

    /**
     * Getter qui retourne le texte formaté des validations restantes depuis PanelJeu.
     *
     * @return Chaîne indiquant le nombre de validations restantes
     */
    public String getNbValidationsRestantes()
    {
        return String.format("Nombre de validations restantes : %d ", ((PanelJeu) this.tabPanel[2]).getNbValidationsRestantes());
    }

    /**
     * Setter qui remplace le panneau actif affiché au centre de la fenêtre.
     *
     * @param pnl Nouveau panneau à afficher
     */
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

    /**
     * Méthode utilitaire qui affiche ou masque la barre de menu du panneau de jeu.
     *
     * @param estVisible {@code true} pour afficher la barre de menu, {@code false} pour la masquer
     */
    public void cacherMenuBar(boolean estVisible) { ((PanelJeu) this.tabPanel[2]).cacherMenuBar(estVisible); }

    /**
     * Méthode utilitaire qui instancie le panneau de choix de difficulté.
     */
    public void creerPanelChoixDiff()             { this.tabPanel[1] = new PanelChoixDiff(this.ctrl, this, 1); }

    /**
     * Méthode utilitaire qui instancie le panneau principal de la partie.
     */
    public void creerPanelJeu()                   { this.tabPanel[2] = new PanelJeu      (this.ctrl, this, 2); }

    /**
     * Méthode utilitaire qui relance une nouvelle partie dans le panneau de jeu.
     */
    public void restartJeu()                      { ((PanelJeu) this.tabPanel[2]).restart(); }

    /**
     * Méthode utilitaire qui crée et affiche l'écran de victoire ou de défaite.
     *
     * @param estVictoire {@code true} en cas de victoire, {@code false} en cas de défaite
     * @param temps Temps total formaté de la partie
     */
    public void afficherPanelResultat(boolean estVictoire, String temps)
    {
        this.tabPanel[3] = new PanelResultat(this.ctrl, this, 3, estVictoire, temps);
        this.setPnl(this.tabPanel[3]);
    }

    /**
     * Méthode utilitaire qui charge et redimensionne une image aux dimensions données.
     *
     * @param chemin Chemin relatif vers le fichier image
     * @param longueur Largeur souhaitée en pixels
     * @param largeur Hauteur souhaitée en pixels
     * @return Image redimensionnée
     */
    public Image creerImages(String chemin, int longueur, int largeur)
    {
        return new ImageIcon(chemin).getImage().getScaledInstance(longueur, largeur, Image.SCALE_SMOOTH);
    }

    /**
     * Méthode utilitaire qui charge une image à sa taille d'origine.
     *
     * @param chemin Chemin relatif vers le fichier image
     * @return Image chargée
     */
    public Image creerDessins(String chemin)
    {
        return new ImageIcon(chemin).getImage();
    }
}