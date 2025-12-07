package view;

/**
 * Console de Resultados e Fechamento de Edital.
 * <p>
 * Tela gerencial avançada utilizada para processar o final de um edital.
 * Centraliza as operações críticas:
 * <ul>
 * <li><b>Calcular:</b> Executa o algoritmo de ranking e atualiza a tabela.</li>
 * <li><b>Notificar:</b> Envia e-mails para os candidatos.</li>
 * <li><b>PDF:</b> Gera o relatório oficial impresso.</li>
 * <li><b>Encerrar:</b> Finaliza o edital e congela os resultados.</li>
 * </ul>
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import view.style.Cores;
import view.style.Componentes;
import view.style.UIUtils; // Importante!
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import models.recurses.Edital;

public class ResultadoEditalView extends JanelaModerna {

    private JTable tabela;
    private JButton btnCalcular, btnVoltar, btnGerarPDF, btnEmail, btnEncerrar;
    private JLabel lblStatus;

    public ResultadoEditalView(Edital edital) {
        super("Resultado do Edital");
        // Precisamos garantir que a janela seja larga o suficiente para 5 botões
        setSize(1000, 650); 
        setLocationRelativeTo(null);
        inicializar(edital);
    }

    private void inicializar(Edital edital) {
        // Sidebar (Botão voltar principal)
        btnVoltar = Componentes.criarBotaoSidebar("Voltar");
        btnVoltar.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnVoltar);

        // --- CONTEÚDO ---
        JLabel lblTitulo = new JLabel("Ranking Final: " + edital.getNumEdital());
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(30, 20, 400, 30);
        painelConteudo.add(lblTitulo);

        lblStatus = new JLabel("Status: " + edital.getStatus());
        lblStatus.setFont(Componentes.FONT_SUB);
        if (edital.getStatus() == Edital.Status.ENCERRADO) {
            lblStatus.setForeground(Cores.VERMELHO);
        } else {
            lblStatus.setForeground(Cores.VERDE);
        }
        lblStatus.setBounds(30, 55, 300, 20);
        painelConteudo.add(lblStatus);

        // --- TABELA ---
        DefaultTableModel modelo = TabelaResultadoBuilder.montarTabela(edital);
        tabela = new JTable(modelo);
        
        // Aplica o estilo novo da UIUtils
        UIUtils.styleTable(tabela); 
        tabela.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer(3));
        
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(30, 90, 720, 350);
        painelConteudo.add(scroll);

        // --- BOTÕES (Alinhados na parte inferior) ---
        int yBtn = 480;
        int hBtn = 45;
        int gap = 15; // Espaço entre botões
        int xAtual = 30;

        // 1. ATUALIZAR (Laranja)
        btnCalcular = new JButton("ATUALIZAR");
        btnCalcular.setBounds(xAtual, yBtn, 140, hBtn);
        UIUtils.styleButton(btnCalcular, Cores.LARANJA);
        painelConteudo.add(btnCalcular);
        xAtual += 140 + gap;

        // 2. NOTIFICAR (Azul)
        btnEmail = new JButton("NOTIFICAR");
        btnEmail.setBounds(xAtual, yBtn, 140, hBtn);
        UIUtils.styleButton(btnEmail, Cores.AZUL);
        painelConteudo.add(btnEmail);
        xAtual += 140 + gap;

        // 3. PDF (Verde)
        btnGerarPDF = new JButton("GERAR PDF");
        btnGerarPDF.setBounds(xAtual, yBtn, 140, hBtn);
        UIUtils.styleButton(btnGerarPDF, Cores.VERDE);
        painelConteudo.add(btnGerarPDF);
        xAtual += 140 + gap;

        // 4. ENCERRAR (Vermelho)
        btnEncerrar = new JButton("ENCERRAR");
        btnEncerrar.setBounds(xAtual, yBtn, 140, hBtn);
        UIUtils.styleButton(btnEncerrar, Cores.VERMELHO);
        painelConteudo.add(btnEncerrar);
        
        // Lógica de Edital Encerrado
        if (edital.getStatus() == Edital.Status.ENCERRADO) {
            btnCalcular.setEnabled(false);
            btnCalcular.setBackground(Color.GRAY);
            
            btnEncerrar.setEnabled(false);
            btnEncerrar.setText("FECHADO");
            btnEncerrar.setBackground(Color.GRAY);
        }
    }

    // Getters e Listeners (Mantidos para o Controller funcionar)
    public JTable getTabela() { return tabela; }
    public String getAlunoSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) return tabela.getValueAt(linha, 0).toString();
        return null;
    }
    public void addCalcularListener(ActionListener l) { btnCalcular.addActionListener(l); }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void addGerarPDFListener(ActionListener l) { btnGerarPDF.addActionListener(l); }
    public void addEmailListener(ActionListener l) { btnEmail.addActionListener(l); }
    public void addEncerrarListener(ActionListener l) { btnEncerrar.addActionListener(l); }
    
    // Listeners extras (casos você tenha adicionado antes)
    public void addEditarEditalListener(ActionListener l) { /* Implementar se tiver botão editar aqui */ }
    public void addClonarListener(ActionListener l) { /* Implementar se tiver botão clonar aqui */ }

    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}