package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import view.style.Cores;
import view.style.Componentes;
import view.style.UIUtils; // <--- Importante
import java.awt.Color;
import java.awt.event.ActionListener;

public class ListarEditaisView extends JanelaModerna {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnDetalhar, btnEditar, btnExcluir, btnClonar, btnVoltar;

    public ListarEditaisView() {
        super("Gerenciar Editais");
        inicializar();
    }

    private void inicializar() {
        // ... (Código do Título e Sidebar mantém igual) ...
        JLabel lblTitulo = new JLabel("Editais Cadastrados");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(30, 30, 400, 30);
        painelConteudo.add(lblTitulo);

        btnVoltar = Componentes.criarBotaoSidebar("Voltar ao Painel");
        btnVoltar.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnVoltar);

        // Tabela
        String[] colunas = {"ID", "Número", "Início", "Fim", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        tabela = new JTable(modeloTabela);
        UIUtils.styleTable(tabela); // Estilo Tabela
        
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(30, 80, 720, 400);
        painelConteudo.add(scroll);

        // --- BOTÕES CORRIGIDOS COM UIUTILS ---
        int yBtn = 500;
        int hBtn = 45;

        // 1. Abrir (Azul)
        btnDetalhar = new JButton("ABRIR / RESULTADOS");
        btnDetalhar.setBounds(30, yBtn, 180, hBtn);
        UIUtils.styleButton(btnDetalhar, Cores.AZUL); // <--- AQUI
        painelConteudo.add(btnDetalhar);

        // 2. Editar (Laranja)
        btnEditar = new JButton("EDITAR DATAS");
        btnEditar.setBounds(220, yBtn, 150, hBtn);
        UIUtils.styleButton(btnEditar, Cores.LARANJA); // <--- AQUI
        painelConteudo.add(btnEditar);

        // 3. Clonar (Roxo)
        btnClonar = new JButton("CLONAR");
        btnClonar.setBounds(380, yBtn, 150, hBtn);
        UIUtils.styleButton(btnClonar, new Color(142, 68, 173)); // <--- AQUI
        painelConteudo.add(btnClonar);

        // 4. Excluir (Vermelho)
        btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setBounds(540, yBtn, 150, hBtn);
        UIUtils.styleButton(btnExcluir, Cores.VERMELHO); // <--- AQUI
        painelConteudo.add(btnExcluir);
    }

    // ... (Getters e Listeners mantidos iguais) ...
    public DefaultTableModel getModeloTabela() { return modeloTabela; }
    public String getIdSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) return tabela.getValueAt(linha, 0).toString();
        return null;
    }
    public void addDetalharListener(ActionListener l) { btnDetalhar.addActionListener(l); }
    public void addEditarListener(ActionListener l) { btnEditar.addActionListener(l); }
    public void addExcluirListener(ActionListener l) { btnExcluir.addActionListener(l); }
    public void addClonarListener(ActionListener l) { btnClonar.addActionListener(l); }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}