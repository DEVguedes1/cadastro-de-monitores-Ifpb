package view;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Aluno;
import models.Inscricao;
import models.Sexo;
import models.recurses.Disciplina;
import models.recurses.Edital;

public class CalcularResultadoDeEdital extends JanelaPadrao{
    private String [] colunas = {"Disciplina", "Alunos", "Resultado"};
    Object[][] dados = new Object[0][3];
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
        for (Disciplina d : edital.getDisciplinas()){
            for (Inscricao i : d.getInscricoes()){
                if (i.getDisciplina().getNomeDisciplina().equals(d.getNomeDisciplina())){
                    if (!disciplinaExiste(modelo, d.getNomeDisciplina())){
                    modelo.addRow(new Object[] {d.getNomeDisciplina(), "", ""});
                    }
                    String nome = i.getAluno().getNomeDoAluno();
                    double notaFinal = (i.getNota() * d.getPesoNota()) + (i.getCRE() * d.getPesoCRE());
                    modelo.addRow(new Object[] {"", nome, notaFinal});
                }
            }
        }
        add(scroll);
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
        Aluno Atest = new Aluno("keldson", "202514190000", 91.7,"emailtest@gmail.com", "senha123", Sexo.MASCULINO );
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
        CalcularResultadoDeEdital test = new CalcularResultadoDeEdital(teste);
        test.setVisible(true);
    }
    
}
