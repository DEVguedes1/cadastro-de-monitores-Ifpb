package controller;

import view.CadastroAlunoView;
import view.LoginView;
import models.Aluno;
import models.Sexo; // Importante
import models.CentralDeInformacoes;
import models.Persistencia;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastroAlunoController implements ActionListener {
    private CadastroAlunoView view;
    
    public CadastroAlunoController(CadastroAlunoView view) {
        this.view = view;
        this.view.addSalvarListener(this);
        this.view.addVoltarListener(e -> voltarLogin());
        
    }
    
    private void voltarLogin() {
        view.dispose();
        LoginView lv = new LoginView();
        lv.setVisible(true);
        new LoginController(lv);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Validação básica
        if(view.getNome().isEmpty() || view.getMatricula().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return;
        }

        CentralDeInformacoes central = Persistencia.recuperarCentral();
        
        // Criando aluno (CRE começa com 0, Sexo padrão OUTRO por enquanto pra simplificar a tela)
        Aluno novo = new Aluno(
            view.getNome(), 
            view.getMatricula(), 
            0.0, 
            view.getEmail(), 
            view.getSenha(), 
            Sexo.OUTRO
        );
        
        if (central.adicionarAluno(novo)) {
            Persistencia.salvarCentral(central);
            JOptionPane.showMessageDialog(view, "Aluno cadastrado com sucesso!");
            voltarLogin();
        } else {
            JOptionPane.showMessageDialog(view, "Erro: Matrícula já existe!");
        }
    }
}