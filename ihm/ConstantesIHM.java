package ihm;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/* ----------------------------------------------- */
/* Regroupe toutes les constantes de la partie IHM */
/* ----------------------------------------------- */
public class ConstantesIHM
{
    // Couleurs de Jeu
    public static final Color COULEUR_FOND              = new Color(251, 252, 250);
    public static final Color COULEUR_SELECTION         = new Color(168, 168, 168);
    public static final Color COULEUR_VERT_VALIDE       = new Color(0  , 150, 0  );
    public static final Color COULEUR_VERT_VALIDE_SURVOL= new Color(0  , 175, 0  );
    public static final Color COULEUR_GRIS_PLACE_LABEL  = new Color(185, 190, 195);
    public static final Color COULEUR_GRIS_PLACE        = new Color(225, 230, 235);
    public static final Color COULEUR_GRIS_SURVOL       = new Color(200, 200, 200);
    public static final Color COULEUR_ROSE_SURVOL       = new Color(252, 228, 244);
    public static final Color COULEUR_BROUILLON_ACTIVE  = new Color(223, 242, 255);
    public static final Color COULEUR_GRIS_BROUILLON    = new Color(121, 128, 129);
    public static final Color COULEUR_ROSE_CERVEAU      = new Color(221, 100, 183);
    public static final Color COULEUR_ROSE_CERVEAU_FONCE= new Color(230, 94 , 187);
    public static final Color COULEUR_VICTOIRE          = new Color(0  , 150, 0  );
    public static final Color COULEUR_DEFAITE           = new Color(255, 0  , 0  );
    public static final Color COULEUR_SEPARATEUR        = new Color(150, 150, 150);
    public static final Color COULEUR_FOND_SEPARATEUR   = new Color(0 , 0 , 0 , 0);
    public static final Color COULEUR_FOND_MENUBAR      = new Color(240, 241, 237); 
    public static final Color COULEUR_CASE_VERROUILLEE  = new Color(225, 228, 232);

    public static final Color LUMIERE               = Color.WHITE;
    public static final Color OMBRE                 = Color.LIGHT_GRAY;

    // Couleurs de difficulté
    public static final Color VERT_DIFFICULTE       = new Color(135, 233, 144);
    public static final Color ORANGE_DIFFICULTE     = new Color(255, 215, 0  );
    public static final Color ROUGE_DIFFICULTE      = new Color(255, 94 , 77 );

    public static final Color OMBRE_FACILE          = new Color(135, 233, 144);
    public static final Color OMBRE_MOYEN           = new Color(255, 215, 0 );
    public static final Color OMBRE_DIFFICILE       = new Color(255, 94 , 77 );

    // Bordures
    public static final Border RELIEF               = BorderFactory.createRaisedBevelBorder();
    public static final Border MARGE                = BorderFactory.createEmptyBorder(10, 25, 10, 25);
    public static final Border MARGE_GOMME          = BorderFactory.createEmptyBorder(2 , 15, 2 , 15);

    public static final Border BORDURE_INTERIEURE_VIDE      = BorderFactory.createEmptyBorder(3, 3, 3, 3);
    public static final Border BORDURE_INTERIEURE_SELECTION = BorderFactory.createLineBorder(COULEUR_ROSE_CERVEAU, 3);

    // Constructeur privé pour empêcher de faire un "new ConstantesIHM()"
    private ConstantesIHM() {}
}