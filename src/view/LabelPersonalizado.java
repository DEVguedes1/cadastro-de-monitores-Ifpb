package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class LabelPersonalizado extends JLabel{
	
	
	public LabelPersonalizado(String texto, int x, int y, int w, int h, JFrame janela) {
		super(texto);
		setBounds(x, y, w, h);
		setOpaque(true);
		setBackground(new Color(0, 255, 0));
		setFont(JanelaPadrao.FONTE_PROJETO);
		janela.add(this);
	}
	
}
