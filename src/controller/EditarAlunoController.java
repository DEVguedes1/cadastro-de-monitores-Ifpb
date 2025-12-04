package controller;

import view.EditarAlunoDialog;
import models.Aluno;
import models.CentralDeInformacoes;
import models.Persistencia;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditarAlunoController implements ActionListener {

    private EditarAlunoDialog view;
    private Aluno alunoOriginal;
    private Runnable aoTerminar; // Uma ação para rodar quando fechar (atualizar a tela de baixo)

    public EditarAlunoController(EditarAlunoDialog view, Aluno aluno, Runnable aoTerminar) {
        this.view = view;
        this.alunoOriginal = aluno;
        this.aoTerminar = aoTerminar;

        this.view.addSalvarListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String novoNome = view.getNome();
        String novoEmail = view.getEmail();
        String novoCREStr = view.getCRE();

        if (novoNome.isEmpty() || novoEmail.isEmpty() || novoCREStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return;
        }

        try {
            double novoCRE = Double.parseDouble(novoCREStr.replace(",", "."));

            // 1. Atualizar o Objeto na Memória
            // Como o objeto 'alunoOriginal' é uma referência direta ao que está na lista da Central,
            // alterar ele aqui já altera na lista da memória.
            alunoOriginal.setNomeDoAluno(novoNome);
            alunoOriginal.setEmail(novoEmail);
            // alunoOriginal.setCre(novoCRE); // SE VOCÊ TIVER O SETTER NA CLASSE ALUNO
            // Se a classe Aluno não tiver setCre (estava private no código antigo), adicione lá!
            // Vou assumir que você tem ou vai adicionar.
            
            // GAMBIARRA TEMPORÁRIA SE NÃO TIVER SETTER:
            // Você precisaria recriar o aluno, mas vamos supor que você adicionou o setCre.

            // 2. Salvar no XML
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            // Truque: O 'alunoOriginal' veio da tela anterior, mas precisamos garantir 
            // que estamos salvando a instância correta da central.
            // O jeito mais seguro é buscar de novo na central pela matrícula.
            
            Aluno alunoNoBanco = central.recuperarAlunoPorMatricula(alunoOriginal.getMatricula());
            if (alunoNoBanco != null) {
                alunoNoBanco.setNomeDoAluno(novoNome);
                alunoNoBanco.setEmail(novoEmail);
                // alunoNoBanco.setCre(novoCRE); // <--- ATENÇÃO: Adicione setCre na classe Aluno se não tiver
            }

            Persistencia.salvarCentral(central);

            JOptionPane.showMessageDialog(view, "Dados atualizados com sucesso!");
            view.dispose();

            // 3. Avisar a tela de baixo para se atualizar
            if (aoTerminar != null) {
                aoTerminar.run();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "CRE inválido! Digite apenas números.");
        }
    }
}