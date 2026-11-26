package ihm;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * Regroupe toutes les constantes visuelles (couleurs, bordures et marges) de l'IHM.
 *
 * @author Alexis COFFARD
 * @author Timothé LARBI
 * @version 2.0
 */
public final class ConstantesIHM
{
    /*-------------------------------*/
    /*          Constantes           */
    /*-------------------------------*/

    // --- Fonds & Structure ---

    /** Fond principal des panneaux */
    public static final Color COULEUR_FOND               = new Color(251, 252, 250);

    /** Fond de la barre de menu et du bandeau bas */
    public static final Color COULEUR_FOND_MENUBAR       = new Color(240, 241, 237);

    /** Couleur des lignes de séparation */
    public static final Color COULEUR_SEPARATEUR         = new Color(150, 150, 150);

    /** Fond transparent des séparateurs */
    public static final Color COULEUR_FOND_SEPARATEUR    = new Color(0 , 0 , 0 , 0);

    // --- Thème Rose (Accents & Sélection) ---

    /** Rose principal des boutons et sélections */
    public static final Color COULEUR_ROSE_CERVEAU       = new Color(221, 100, 183);

    /** Rose accentué au survol des boutons principaux */
    public static final Color COULEUR_ROSE_CERVEAU_FONCE = new Color(230, 94 , 187);

    /** Rose clair au survol des cases et du pavé */
    public static final Color COULEUR_ROSE_SURVOL        = new Color(242, 218, 234);

    // --- États de la Grille & Outils ---

    /** Fond gris des cases fixes ou révélées par indice */
    public static final Color COULEUR_CASE_VERROUILLEE   = new Color(225, 228, 232);

    /** Fond d'un bouton du pavé dont le chiffre est posé */
    public static final Color COULEUR_GRIS_PLACE         = new Color(225, 230, 235);

    /** Texte grisé d'un chiffre déjà posé sur la grille */
    public static final Color COULEUR_GRIS_PLACE_LABEL   = new Color(185, 190, 195);

    /** Fond gris au survol des boutons standards */
    public static final Color COULEUR_GRIS_SURVOL        = new Color(200, 200, 200);

    /** Fond bleu clair indiquant que le brouillon est actif */
    public static final Color COULEUR_BROUILLON_ACTIVE   = new Color(223, 242, 255);

    /** Couleur des petits chiffres candidats en brouillon */
    public static final Color COULEUR_GRIS_BROUILLON     = new Color(121, 128, 129);

    /** Vert du bouton Valider et des totaux corrects */
    public static final Color COULEUR_VERT_VALIDE        = new Color(0  , 150, 0  );

    /** Vert éclairci au survol du bouton Valider */
    public static final Color COULEUR_VERT_VALIDE_SURVOL = new Color(0  , 175, 0  );

    // --- Niveaux de Difficulté ---

    /** Fond de base de la carte Facile */
    public static final Color COULEUR_FOND_BOUTON_FACILE = new Color(176, 242, 182);

    /** Fond au survol de la carte Facile */
    public static final Color VERT_DIFFICULTE            = new Color(135, 233, 144);

    /** Couleur de l'ombre portée de la carte Facile */
    public static final Color OMBRE_FACILE               = new Color(135, 233, 144);

    /** Fond de base de la carte Moyen */
    public static final Color COULEUR_FOND_BOUTON_MOYEN  = new Color(254, 243, 199);

    /** Fond au survol de la carte Moyen */
    public static final Color ORANGE_DIFFICULTE          = new Color(253, 224, 130);

    /** Couleur de l'ombre portée de la carte Moyen */
    public static final Color OMBRE_MOYEN                = new Color(255, 215, 0  );

    /** Fond de base de la carte Difficile */
    public static final Color COULEUR_FOND_BOUTON_DIFF   = new Color(254, 226, 226);

    /** Fond au survol de la carte Difficile */
    public static final Color ROUGE_DIFFICULTE           = new Color(252, 185, 185);

    /** Couleur de l'ombre portée de la carte Difficile */
    public static final Color OMBRE_DIFFICILE            = new Color(255, 94 , 77 );

    // --- Bordures & Marges ---

    /** Bordure en relief 3D des boutons */
    public static final Border RELIEF                       = BorderFactory.createRaisedBevelBorder();

    /** Marge interne standard des boutons */
    public static final Border MARGE                        = BorderFactory.createEmptyBorder(10, 25, 10, 25);

    /** Marge interne réduite pour le bouton Gomme */
    public static final Border MARGE_GOMME                  = BorderFactory.createEmptyBorder(2 , 15, 2 , 15);

    /** Marge interne d'une case non sélectionnée */
    public static final Border BORDURE_INTERIEURE_VIDE      = BorderFactory.createEmptyBorder(3 , 3 , 3 , 3 );

    /** Encadrement rose d'une case sélectionnée */
    public static final Border BORDURE_INTERIEURE_SELECTION = BorderFactory.createLineBorder(COULEUR_ROSE_CERVEAU, 3);

    /*-------------------------------*/
    /*         Constructeur          */
    /*-------------------------------*/

    /**
     * Constructeur privé qui empêche l'instanciation de cette classe utilitaire.
     */
    private ConstantesIHM() {}
}