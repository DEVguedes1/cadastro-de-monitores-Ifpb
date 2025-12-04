package controller;

import view.CadastrarEditalView;
import view.DashboardCoordenador;
import view.ListarAlunosView;
import view.ListarEditaisView; // Importe a lista
import view.LoginView;
import models.utils.BackupService;
import javax.swing.JOptionPane;

public class DashboardCoordController {
    
    private DashboardCoordenador view;
    private String nomeCoordenador;

    public DashboardCoordController(DashboardCoordenador view, String nome) {
        this.view = view;
        this.nomeCoordenador = nome;
        
        // 1. Botão Criar Novo
        this.view.addNovoEditalListener(e -> {
            view.dispose();
            CadastrarEditalView editalView = new CadastrarEditalView();
            editalView.setVisible(true);
            new CadastrarEditalController(editalView, nomeCoordenador);
        });

        // 2. Botão Listar/Gerenciar (O QUE VOCÊ QUER AGORA)
        this.view.addListarEditaisListener(e -> {
             System.out.println("Botão Listar clicado!"); // Debug
             view.dispose();
             ListarEditaisView listaView = new ListarEditaisView();
             listaView.setVisible(true);
             // Liga o controller da lista passando o nome do chefe
             new ListarEditaisController(listaView, nomeCoordenador);
        });

        // 3. Botão Sair
        this.view.addSairListener(e -> {
            view.dispose();
            LoginView login = new LoginView();
            login.setVisible(true);
            new LoginController(login);
        });
        
        // Ação: Gerenciar Alunos
        this.view.addListarAlunosListener(e -> {
            view.dispose();
            ListarAlunosView listaView = new ListarAlunosView();
            listaView.setVisible(true);
            new ListarAlunosController(listaView, nomeCoordenador);
        });
        
        // Ação: Botão Sair
        this.view.addSairListener(e -> {
            // Tenta fazer backup silencioso antes de sair
            try {
                BackupService.realizarBackup();
                System.out.println("Backup automático realizado.");
            } catch (Exception ex) {
                System.err.println("Falha no backup automático: " + ex.getMessage());
            }

            view.dispose();
            view.LoginView login = new view.LoginView();
            login.setVisible(true);
            new LoginController(login);
        });
    }
}