package ihm;

import controleur.Controleur;
import java.awt.*;
import javax.swing.*;

public class FrameJeu extends JFrame
{
    private Controleur ctrl;
    
    private PanelJeu   panelActif;
    
    public FrameJeu (Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.setTitle("Acte de Présence");
		this.setSize(700, 700);
		this.setLayout(new BorderLayout());

        this.panelActif = new PanelJeu(ctrl);

        this.add(this.panelActif, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}