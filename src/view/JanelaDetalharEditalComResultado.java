package view;

import java.time.LocalDate;
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

public class JanelaDetalharEditalComResultado extends JanelaPadrao {
    public JanelaDetalharEditalComResultado(Edital edital){
        //Titulo
        super("Detalhar Edital com Resultado", 600, 500);
        JLabel titulo = new JLabel("EDITAL: " + edital.getNumEdital());
        titulo.setBounds(10, 10, 300, 10);
        add(titulo);
        //Tabela
        DefaultTableModel modelo = TabelaResultadoBuilder.montarTabela(edital);
        JTable tabela = new JTable(modelo);
        tabela.setDefaultEditor(Object.class, null); //Não pode editar cédulas
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 35, 565, 250);
        add(scroll);
        JButton JBdesistir = new JButton("Desistir"); 
        JBdesistir.setBounds(10, 400, 200, 30);
        add(JBdesistir);
        JBdesistir.addActionListener((actionEvent) -> {
            int linha = tabela.getSelectedRow();

            // 1. Verifica se alguma linha foi selecionada
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma matéria primeiro!");
                return;
            }

            // 2. Pega o aluno da linha selecionada (agora é seguro)
            Object aluno = tabela.getValueAt(linha, 0);

            if (aluno == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma matéria válida!");
                return;
            }

            // 3. Verifica se é realmente o aluno logado
            if (!aluno.toString().equalsIgnoreCase("keldson")) {
                JOptionPane.showMessageDialog(this,
                        "Atenção! Escolha somente a linha que tiver o seu nome.");
                return;
            }

            // 4. Confirmação
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

                // Aqui você chama o método que realmente faz a desistência
                Inscricao i = Disciplina.buscarInscricao(disciplina, "keldson");
                i.setSituacao(Situacao.DESISTENTE);
                JOptionPane.showMessageDialog(this, "Você desistiu da monitoria de " + disciplina.getNomeDisciplina() + "!");
                TabelaResultadoBuilder.atualizarTabela(modelo, edital);
            }
        });
    }
    public static void main(String[] args) {
        ArrayList<Disciplina> Disciplinas = new ArrayList();
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
