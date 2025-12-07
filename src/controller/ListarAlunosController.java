package controller;

/**
 * Controlador da tela de Gestão de Alunos (Visão do Coordenador).
 * <p>
 * Responsabilidades:
 * <ul>
 * <li>Carregar a lista de todos os alunos cadastrados.</li>
 * <li>Implementar o filtro de busca em tempo real (KeyReleased).</li>
 * <li>Redirecionar para o perfil detalhado do aluno selecionado.</li>
 * </ul>
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import models.Aluno;
import models.CentralDeInformacoes;
import models.Persistencia;
import view.DashboardCoordenador;
import view.ListarAlunosView;
import view.PerfilAlunoView;

public class ListarAlunosController {

    private ListarAlunosView view;
    private String nomeCoordenador;

    public ListarAlunosController(ListarAlunosView view, String nomeCoord) {
        this.view = view;
        this.nomeCoordenador = nomeCoord;

        this.view.addVoltarListener(e -> voltar());
        
        // Filtro em tempo real
        this.view.addBuscaListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e) {}
            @Override 
            public void keyReleased(KeyEvent e) {
                carregarTabela(view.getTextoBusca());
            }
        });
        
        // Ação: Ver Perfil
        this.view.addVerPerfilListener(e -> {
            String matricula = view.getMatriculaSelecionada();
            
            if (matricula != null) {
                // Recupera o objeto Aluno completo pela matrícula
                CentralDeInformacoes central = Persistencia.recuperarCentral();
                Aluno alunoSelecionado = central.recuperarAlunoPorMatricula(matricula);
                
                if (alunoSelecionado != null) {
                    view.dispose();
                    PerfilAlunoView perfilView = new PerfilAlunoView(alunoSelecionado);
                    perfilView.setVisible(true);
                    new PerfilAlunoController(perfilView, alunoSelecionado, nomeCoordenador);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(view, "Erro: Aluno não encontrado no banco.");
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(view, "Selecione um aluno na tabela primeiro!");
            }
        });
        
        // Carrega tudo inicialmente
        carregarTabela("");
    }

    private void carregarTabela(String filtro) {
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        ArrayList<Aluno> alunos = central.getTodosOsAlunos();
        var modelo = view.getModelo();
        modelo.setRowCount(0);

        for (Aluno a : alunos) {
            // Se o filtro estiver vazio OU se o nome conter o texto digitado (ignorando maiúsculas)
            if (filtro.isEmpty() || a.getNomeDoAluno().toLowerCase().contains(filtro.toLowerCase())) {
                modelo.addRow(new Object[]{
                    a.getMatricula(),
                    a.getNomeDoAluno(),
                    a.getEmail(),
                    a.getCre()
                });
            }
        }
    }

    private void voltar() {
        view.dispose();
        DashboardCoordenador dash = new DashboardCoordenador(nomeCoordenador);
        dash.setVisible(true);
        new DashboardCoordController(dash, nomeCoordenador);
    }
}