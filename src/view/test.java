package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Aluno;
import models.Inscricao;
import models.Inscricao.Situacao;
import models.Sexo;
import models.recurses.Disciplina;
import models.recurses.Edital;
import models.recurses.Edital.Status;

public class test extends JanelaPadrao {

    public test(Edital edital) {

        super("Detalhar Edital com Resultado", 600, 500);
        setResizable(true);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        setLayout(new BorderLayout());

        // ========== PAINEL SUPERIOR (NORTH) ==========
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new GridLayout(1, 3)); // 3 colunas lado a lado

        JLabel titulo = new JLabel("EDITAL: " + edital.getNumEdital());
        titulo.setForeground(COR_TEXTO);
        titulo.setFont(fontPadrao);

        JLabel status = new JLabel("Status: " + edital.getStatus());
        status.setForeground(COR_TEXTO);
        status.setFont(fontPadrao);

        JLabel data = new JLabel(
                "Data: " + formato.format(edital.getDataIncio()) +
                        " / " + formato.format(edital.getDataFinal())
        );
        data.setForeground(COR_TEXTO);
        data.setFont(fontPadrao);

        painelTopo.add(titulo);
        painelTopo.add(status);
        painelTopo.add(data);

        add(painelTopo, BorderLayout.NORTH);


        // ========== TABELA (CENTER) ==========
        DefaultTableModel modelo = TabelaResultadoBuilder.montarTabela(edital);
        JTable tabela = new JTable(modelo);

        int idxSituacao = 3;
        tabela.getColumnModel().getColumn(idxSituacao)
                .setCellRenderer(new StatusCellRenderer(idxSituacao));

        JScrollPane scroll = new JScrollPane(tabela);

        add(scroll, BorderLayout.CENTER);


        // ========== PAINEL DOS BOTÕES (SOUTH) ==========
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton JBdesistir = new JButton("Desistir");
        JButton JBFinalizar = new JButton("Finalizar edital");

        // Só aparece se o edital está ativo
        if (edital.getStatus().equals(Status.ATIVO)) {
            painelBotoes.add(JBdesistir);
            painelBotoes.add(JBFinalizar);
        }

        add(painelBotoes, BorderLayout.SOUTH);


        // ========== AÇÕES ==========
        if (edital.getStatus().equals(Status.ATIVO)) {
            JBdesistir.addActionListener((actionEvent) -> {
                int linha = tabela.getSelectedRow();
                if (linha == -1) {
                    JOptionPane.showMessageDialog(this, "Selecione uma matéria primeiro!");
                    return;
                }
                Object aluno = tabela.getValueAt(linha, 0);
                if (aluno == null) {
                    JOptionPane.showMessageDialog(this, "Selecione uma matéria válida!");
                    return;
                }
                if (!aluno.toString().equalsIgnoreCase("keldson")) {
                    JOptionPane.showMessageDialog(this,
                            "Atenção! Escolha somente a linha que tiver o seu nome.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja desistir da monitoria?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    String nomeDisciplina = tabela.getValueAt(linha, 1).toString();
                    Disciplina disciplina = edital.buscarDisciplina(nomeDisciplina);

                    Inscricao i = Disciplina.buscarInscricao(disciplina, "keldson");
                    i.setSituacao(Situacao.DESISTENTE);

                    JOptionPane.showMessageDialog(this,
                            "Você desistiu da monitoria de " + disciplina.getNomeDisciplina() + "!");

                    TabelaResultadoBuilder.atualizarTabela(modelo, edital);
                    tabela.clearSelection();
                }
            });

            JBFinalizar.addActionListener((actionEvent) -> {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja finalizar este edital?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    edital.setStatus(Status.ENCERRADO);
                    JOptionPane.showMessageDialog(null,
                            "Edital: " + edital.getNumEdital() + " Finalizado!");

                    status.setText("Status: ENCERRADO");
                    JBdesistir.setVisible(false);
                    JBFinalizar.setVisible(false);
                }
            });
        }
    }
    public static void main(String[] args) {
        ArrayList<Disciplina> Disciplinas = new ArrayList<>();
        Aluno Atest = new Aluno("keldson", "202514190000", 91.7,"emailtest@gmail.com", "senha123", Sexo.MASCULINO );
        Aluno Atest2 = new Aluno("Davi", "202514190000", 71.4,"emailtest@gmail.com", "senha123", Sexo.MASCULINO );
        Aluno Atest3 = new Aluno("Nicolas", "202514190000", 70.4,"emailtest@gmail.com", "senha123", Sexo.MASCULINO );
        Disciplina d1 = new Disciplina("POO", 2, 0.5f, 0.5f, "Cleyton", 2 );
        Disciplina d2 = new Disciplina("Rede de computadores", 2, 0.5f, 0.5f, "Bruno", 2);
        Disciplina d3 =new Disciplina("Banco de dados", 2, 0.5f, 0.5f, "Cazé", 2 );
        Disciplinas.add(d3);
        Disciplinas.add(d1);
        Disciplinas.add(d2);
        Edital teste = new Edital("2026/01", LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 30), Disciplinas, 20);
        teste.inscrever(Atest, d3, 71);
        teste.inscrever(Atest, d2, 61);
        teste.inscrever(Atest, d1, 100);
        teste.inscrever(Atest2, d3, 51);
        teste.inscrever(Atest2, d2, 100);
        teste.inscrever(Atest2, d1, 99);
        teste.inscrever(Atest3, d3, 100);
        teste.inscrever(Atest3, d2, 52);
        teste.inscrever(Atest3, d1, 60);
        test test = new test(teste);
        test.setVisible(true);
    }
}

