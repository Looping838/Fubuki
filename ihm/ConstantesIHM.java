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
    public static final Color COULEUR_FOND          = new Color(245, 245, 220);
    public static final Color COULEUR_SELECTION     = new Color(168, 168, 168);
    public static final Color COULEUR_VERT_VALIDE   = new Color(0  , 150, 0  );
    public static final Color COULEUR_GRIS_PLACE    = new Color(110, 110, 110);
    public static final Color COULEUR_GRIS_SURVOL   = new Color(200, 200, 200);

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

    // Constructeur privé pour empêcher de faire un "new ConstantesIHM()"
    private ConstantesIHM() {}
}