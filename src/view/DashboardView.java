package view;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class DashboardView extends JanelaPadrao {

    public DashboardView(String emailUsuario) {
        super("Sistema Principal", 800, 600); // Janela grande
        
        JLabel lblMsg = new JLabel("Olá, " + emailUsuario);
        lblMsg.setForeground(COR_TEXTO);
        lblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
        lblMsg.setBounds(0, 50, 800, 50);
        add(lblMsg);
        
        JLabel lblInfo = new JLabel("Login realizado com sucesso.");
        lblInfo.setForeground(COR_TEXTO);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblInfo.setBounds(0, 100, 800, 30);
        add(lblInfo);
    }
}