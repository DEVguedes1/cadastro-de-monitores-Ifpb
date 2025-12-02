package view;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Inscricao;
import models.recurses.Disciplina;
import models.recurses.Edital;

public class CalcularResultadoDeEdital extends JanelaPadrao{
    private String [] colunas = {"Disciplina", "Professor/Alunos", "Resultado"};
    Object[][] dados = { };
    public CalcularResultadoDeEdital(Edital edital){
        super("Calcular Resultado De Edital",600,500);
        // Titulo
        JLabel titulo = new JLabel("Resultado do Edital: " + edital.getNumEdital());
        titulo.setBounds(20, 10, 300, 20);
        titulo.setForeground(COR_TEXTO);
        titulo.setFont(fontPadrao);
        add(titulo);
        //Tabela de Rank
        DefaultTableModel modelo = new DefaultTableModel(dados, colunas);
        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 50, 565, 200);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getTableHeader().setResizingAllowed(false);
        add(scroll);
        for (Disciplina d : edital.getDisciplinas()){
            boolean disciplinaAdicionada = false;
            for (Inscricao i : d.getInscricoes()){
                if (disciplinaExiste(modelo, d.getNomeDisciplina())){
                    modelo.addRow(new Object[] {d.getNomeDisciplina(), d.getDoscente()});
                }
                else{
                    String nome = i.getAluno().getNomeDoAluno();
                    double notaFinal = (i.getNota() * d.getPesoNota()) + (i.getCRE() * d.getPesoCRE());
                    modelo.addRow(new Object[] {"", nome, notaFinal});
                }
            }
        }
        
        
    }
    public boolean disciplinaExiste(DefaultTableModel modelo, String disciplina) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (modelo.getValueAt(i, 0).equals(disciplina)) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        ArrayList<Disciplina> Disciplinas = new ArrayList();
        Disciplinas.add(new Disciplina("POO", 2, 0.5f, 0.5f, "Cleyton", 2 ));
        Disciplinas.add(new Disciplina("Rede de computadores", 2, 0.5f, 0.5f, "Bruno", 2 ));
        Disciplinas.add(new Disciplina("Banco de dados", 2, 0.5f, 0.5f, "Caze", 2 ));
        Edital teste = new Edital("2026/01", LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 30), Disciplinas, 20);

        CalcularResultadoDeEdital test = new CalcularResultadoDeEdital(teste);
        test.setVisible(true);
    }
    
}
