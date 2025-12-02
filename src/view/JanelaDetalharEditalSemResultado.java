package view;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import models.recurses.*;

public class JanelaDetalharEditalSemResultado extends JanelaPadrao{
    private String [] colunas = {"Matéria: ", "Professor: ", "Período: " };
    private Object [][] dados = {};

    public JanelaDetalharEditalSemResultado(Edital edital) {
        super("Detalhar Edital Sem Resultado",600,500);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        JLabel titulo = new JLabel("Edital:" + edital.getNumEdital() + "    Status: " + (edital.isAtivo()? "Ativo" : "Encerrado" ));
        titulo.setForeground(COR_TEXTO);
        titulo.setFont(fontPadrao);
        titulo.setBounds(20, 10, 300, 30);
        JLabel data = new JLabel("Data: " + formato.format(edital.getDataIncio()) + " / " + formato.format(edital.getDataFinal()));
        data.setForeground(COR_TEXTO);
        data.setFont(fontPadrao);
        data.setBounds(320, 10, 300, 30);
        add(data);
        add(titulo);
        // Criando tabela
        DefaultTableModel modelo = new DefaultTableModel(dados, colunas);
        for (Disciplina d: edital.getDisciplinas()){
            modelo.addRow(new Object[]{d.getNomeDisciplina(),d.getDoscente(),d.getPeriodo() });
        }
        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 35, 550, 300);
        tabela.setRowSelectionAllowed(true);
        tabela.setColumnSelectionAllowed(false);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);
        add(scroll);
        // Botões
        JButton jbInscrever = new JButton("Inscrever");
        jbInscrever.setBounds(20, 350, 120, 35);
        add(jbInscrever);

        JButton jbVoltar = new JButton("Voltar");
        jbVoltar.setBounds(180, 350, 120, 35);
        
        add(jbVoltar);

        JButton jbAtualizar = new JButton("Atualizar");
        jbAtualizar.setBounds(340, 350, 120, 35);
        
        add(jbAtualizar);
        // Listeners
        jbInscrever.addActionListener(e -> {
            int linha = tabela.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma matéria primeiro!");
                return;
            }
            String materia = tabela.getValueAt(linha, 0).toString();
            String professor = tabela.getValueAt(linha, 1).toString();
            String periodo = tabela.getValueAt(linha, 2).toString();
            int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja se inscrever em " + materia + "?",
                "Confirmar inscrição",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (opcao == JOptionPane.YES_OPTION){

                JOptionPane.showMessageDialog(this, 
                    "Inscrição realizada para:\n" + materia + " com " + professor + " ("+ "Periodo: " + periodo + ")"
                );
            }
        });
        jbAtualizar.addActionListener(e -> {

            DefaultTableModel modelo2 = (DefaultTableModel) tabela.getModel();
            modelo2.setRowCount(0); 

            for (Disciplina d: edital.getDisciplinas()){
            modelo.addRow(new Object[]{d.getNomeDisciplina(),d.getDoscente(),d.getPeriodo() });
            }

            JOptionPane.showMessageDialog(this, "Tabela atualizada!");
        });
    }
    //teste!!
    public static void main(String[] args) {
        ArrayList<Disciplina> Disciplinas = new ArrayList();
        Disciplinas.add(new Disciplina("POO", 2, 0.5f, 0.5f, "Cleyton", 2 ));
        Disciplinas.add(new Disciplina("Rede de computadores", 2, 0.5f, 0.5f, "Bruno", 2 ));
        Disciplinas.add(new Disciplina("Banco de dados", 2, 0.5f, 0.5f, "Caze", 2 ));
        Edital teste = new Edital("2026/01", LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 30), Disciplinas, 20);

        JanelaDetalharEditalSemResultado janela = new JanelaDetalharEditalSemResultado(teste);
        janela.setVisible(true);
    }
   
    
}
