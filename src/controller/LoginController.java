package controller;

import view.DashboardView;
import view.LoginView;
import models.utils.UsuarioService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private LoginView view;
    private UsuarioService usuarioService;

    public LoginController(LoginView view) {
        this.view = view;
        this.usuarioService = new UsuarioService();
        this.view.addLoginListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = view.getEmail();
        String senha = view.getSenha();

        // Agora esperamos uma String (o nome), não um boolean
        String nomeUsuario = usuarioService.validarUsuarioXML(email, senha);

        if (nomeUsuario != null) {
            view.dispose(); 
            
            // Passamos o NOME (Ex: "pim" ou "mr.boss") em vez do e-mail
            DashboardView dashboard = new DashboardView(nomeUsuario);
            dashboard.setVisible(true);
            
        } else {
            view.mostrarMensagem("E-mail ou senha incorretos!");
        }
    }
}