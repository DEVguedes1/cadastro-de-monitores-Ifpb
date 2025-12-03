package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

public class JanelaDetalharEditalComResultado extends JanelaPadrao {
    public JanelaDetalharEditalComResultado(Edital edital){
        //Titulo
        super("Detalhar Edital com Resultado", 600, 500);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        JLabel titulo = new JLabel("EDITAL: " + edital.getNumEdital());
        titulo.setForeground(COR_TEXTO);
        titulo.setFont(fontPadrao);
        titulo.setBounds(10, 20, 300, 20);
        add(titulo);
        JLabel status = new JLabel("Status: " + edital.getStatus());
        status.setForeground(COR_TEXTO);
        status.setFont(fontPadrao);
        status.setBounds(170, 20, 300, 20);
        add(status);
        JLabel data = new JLabel("Data: " + formato.format(edital.getDataIncio()) + 
            " / " + formato.format(edital.getDataFinal()));
        data.setBounds(325, 20, 300, 20);
        data.setForeground(COR_TEXTO);
        data.setFont(fontPadrao);
        add(data);
        //Tabela
        DefaultTableModel modelo = TabelaResultadoBuilder.montarTabela(edital);
        JTable tabela = new JTable(modelo);
        int idxSituacao = 3;
        tabela.getColumnModel().getColumn(idxSituacao).setCellRenderer(new StatusCellRenderer(idxSituacao));
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 45, 565, 250);
        add(scroll);
        JButton JBdesistir = new JButton("Desistir");
        if (edital.getStatus().equals(Status.ATIVO)){
            JBdesistir.setBounds(10, 400, 150, 30);
            add(JBdesistir);
            JButton JBFinalizar = new JButton("Finalizar edital"); 
            JBFinalizar.setBounds(170, 400, 150, 30);
            add(JBFinalizar);
            JBdesistir.addActionListener((actionEvent) -> {
            int linha = tabela.getSelectedRow();

            // Verifica se alguma linha foi selecionada
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma matéria primeiro!");
                return;
            }

            // Pega o aluno da linha selecionada
            Object aluno = tabela.getValueAt(linha, 0);

            if (aluno == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma matéria válida!");
                return;
            }

            // Verifica se é realmente o aluno logado
            if (!aluno.toString().equalsIgnoreCase("keldson")) {
                JOptionPane.showMessageDialog(this,
                        "Atenção! Escolha somente a linha que tiver o seu nome.");
                return;
            }

            // Confirmação
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
                
                JOptionPane.showMessageDialog(this, "Você desistiu da monitoria de " + disciplina.getNomeDisciplina() + "!");
                TabelaResultadoBuilder.atualizarTabela(modelo, edital);
                tabela.clearSelection();
            }
            });
            JBFinalizar.addActionListener((ActionEvent) -> {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja finalizar este edital?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION){
                    edital.setStatus(Status.ENCERRADO);
                    JOptionPane.showMessageDialog(null, "Edital: " + edital.getNumEdital() + " Finalizado!");
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
        JanelaDetalharEditalComResultado test = new JanelaDetalharEditalComResultado(teste);
        test.setVisible(true);
    }
}
