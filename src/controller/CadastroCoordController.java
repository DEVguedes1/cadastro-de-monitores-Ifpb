package controller;

import view.CadastroCoordenadorView;
import view.LoginView;
import models.Coordenador;
import models.CentralDeInformacoes;
import models.Persistencia;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroCoordController implements ActionListener {
    
    private CadastroCoordenadorView view;

    public CadastroCoordController(CadastroCoordenadorView view) {
        this.view = view;
        
        // --- AQUI ESTÁ O SEGREDO ---
        this.view.addAcaoCadastrar(this);
        
        this.view.addVoltarListener(e -> {
            view.dispose();
            LoginView login = new LoginView();
            login.setVisible(true);
            new LoginController(login);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("[DEBUG] Botão clicado!"); // Debug para ver se funcionou
        
        String nome = view.getNome();
        String email = view.getEmail();
        String senha = view.getSenha();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha tudo!");
            return;
        }

        try {
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            Coordenador coord = new Coordenador(nome, email, senha);
            
            if(central.adicionarCoordenador(coord)) {
                Persistencia.salvarCentral(central);
                System.out.println("[DEBUG] Salvo no XML com sucesso.");
                
                JOptionPane.showMessageDialog(view, "Sucesso! Faça login.");
                view.dispose();
                
                // Abre o Login
                LoginView login = new LoginView();
                new LoginController(login); // Liga o controller do login
                login.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(view, "Erro: Email já existe.");
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Mostra erro no console se falhar ao salvar
            JOptionPane.showMessageDialog(view, "Erro técnico: " + ex.getMessage());
        }
    }
}