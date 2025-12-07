package controller;

/**
 * Controlador do Painel Principal do Coordenador.
 * <p>
 * Centraliza o acesso às funções administrativas do sistema:
 * <ul>
 * <li>Cadastro e gestão de novos editais.</li>
 * <li>Gerenciamento da base de alunos.</li>
 * <li>Realização de backups (Local e Nuvem).</li>
 * </ul>
 * Implementa a lógica de integração com o {@link JFileChooser} para
 * selecionar o destino do backup na nuvem (Google Drive/Dropbox).
 */

import java.io.File;
import javax.swing.JFileChooser;
import models.utils.BackupService;
import view.CadastrarEditalView;
import view.DashboardCoordenador;
import view.ListarAlunosView;
import view.ListarEditaisView; // Importe a lista
import view.LoginView;

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
     // Ação: Botão Sair
        this.view.addSairListener(e -> {
            try {
                BackupService.realizarBackupLocal(); 
                System.out.println("Backup automático realizado.");
            } catch (Exception ex) {
                System.err.println("Falha no backup automático: " + ex.getMessage());
            }

            view.dispose();
            view.LoginView login = new view.LoginView();
            login.setVisible(true);
            new LoginController(login);
        });
        
        this.view.addBackupListener(e -> {
            // Cria o seletor de arquivos
            JFileChooser chooser = new JFileChooser(); 
            chooser.setDialogTitle("Selecione sua pasta do Google Drive ou Dropbox");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Só deixa escolher pastas
            
            // Abre a janela para o usuário escolher
            int resposta = chooser.showSaveDialog(view);
            
            if (resposta == JFileChooser.APPROVE_OPTION) {
                File pastaSelecionada = chooser.getSelectedFile();
                
                try {
                    view.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
                    
                    // Salva o backup na pasta escolhida
                    String caminho = BackupService.realizarBackupEmPasta(pastaSelecionada);
                    
                    view.setCursor(java.awt.Cursor.getDefaultCursor());
                    
                    view.mostrarMensagem(
                        "✅ BACKUP REALIZADO!\n\n" +
                        "O arquivo foi salvo em:\n" + caminho + "\n\n" +
                        "Se esta pasta estiver sincronizada (Drive/Dropbox),\n" +
                        "o upload começará automaticamente.");
                        
                } catch (Exception ex) {
                    view.setCursor(java.awt.Cursor.getDefaultCursor());
                    view.mostrarMensagem("Erro ao salvar backup: " + ex.getMessage());
                }
            }
        });
    }
}