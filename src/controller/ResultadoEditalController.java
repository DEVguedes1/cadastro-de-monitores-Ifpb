package controller;

/**
 * Controlador de Fechamento e Resultados.
 * <p>
 * Gerencia o ciclo final de um edital.
 * Funcionalidades principais:
 * <ul>
 * <li><b>Calcular Ranking:</b> Dispara o algoritmo de classificação em todas as disciplinas.</li>
 * <li><b>Gerar Relatório:</b> Cria o PDF oficial com a lista de aprovados.</li>
 * <li><b>Encerrar Edital:</b> Congela o edital, impedindo novas inscrições ou alterações.</li>
 * </ul>
 */

import view.ResultadoEditalView;
import view.ListarEditaisView;
import view.TabelaResultadoBuilder;
import models.CentralDeInformacoes;
import models.Persistencia;
import models.recurses.Disciplina;
import models.recurses.Edital;
import models.recurses.GeradorDeRelatorios;
import models.utils.Mensageiro;
import models.Aluno;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ResultadoEditalController implements ActionListener {

    private ResultadoEditalView view;
    private Edital edital;
    private String nomeCoordenador;

    public ResultadoEditalController(ResultadoEditalView view, Edital edital, String nomeCoord) {
        this.view = view;
        this.edital = edital;
        this.nomeCoordenador = nomeCoord;

        // Listeners existentes
        this.view.addCalcularListener(this); // ActionPerformed abaixo
        this.view.addVoltarListener(e -> voltar());
        this.view.addGerarPDFListener(e -> gerarPDF());
        this.view.addEmailListener(e -> prepararEnvioEmail());
        
        // Listener NOVO: Encerrar
        this.view.addEncerrarListener(e -> encerrarEdital());
    }

    // --- LÓGICA DE ENCERRAR (NOVA) ---
    private void encerrarEdital() {
        int confirm = JOptionPane.showConfirmDialog(view, 
            "ATENÇÃO: Deseja realmente ENCERRAR este edital?\n" +
            "- O status mudará para ENCERRADO.\n" +
            "- Ninguém mais poderá se inscrever.\n" +
            "- O ranking será congelado.",
            "Confirmar Encerramento", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            Edital editalBanco = central.recuperarEditalPorId(edital.getId());
            
            if (editalBanco != null) {
                // Muda status
                editalBanco.setStatus(Edital.Status.ENCERRADO);
                Persistencia.salvarCentral(central);
                
                view.mostrarMensagem("Edital encerrado com sucesso!");
                
                // Recarrega a tela para bloquear os botões
                view.dispose();
                ResultadoEditalView novaView = new ResultadoEditalView(editalBanco);
                novaView.setVisible(true);
                new ResultadoEditalController(novaView, editalBanco, nomeCoordenador);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica do botão CALCULAR (Já existente)
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        Edital editalAtualizado = central.recuperarEditalPorId(edital.getId());

        if (editalAtualizado.getDisciplinas() != null) {
            for (Disciplina d : editalAtualizado.getDisciplinas()) {
                d.calcularResultadoFinal();
            }
        }
        
        Persistencia.salvarCentral(central);
        
        this.edital = editalAtualizado;
        DefaultTableModel modelo = (DefaultTableModel) view.getTabela().getModel();
        TabelaResultadoBuilder.atualizarTabela(modelo, editalAtualizado);
        
        view.mostrarMensagem("Ranking calculado e atualizado com sucesso!");
    }

    // ... (Métodos gerarPDF e prepararEnvioEmail continuam iguais) ...
    
    private void gerarPDF() {
         // ... (Código do PDF que já fizemos) ...
         try {
            models.recurses.GeradorDeRelatorios.gerarRelatorioRanking(edital);
            String nomeArquivo = "Resultado_Edital_" + edital.getNumEdital().replace("/", "-") + ".pdf";
            try {
                File pdfFile = new File(nomeArquivo);
                if (pdfFile.exists() && Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                     view.mostrarMensagem("PDF gerado! Salvo em:\n" + pdfFile.getAbsolutePath());
                }
            } catch (Exception abrirEx) {
                view.mostrarMensagem("PDF gerado! Salvo em:\n" + nomeArquivo);
            }
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao criar PDF: " + e.getMessage());
        }
    }
    
    private void prepararEnvioEmail() {
        // ... (Código do Email que já fizemos) ...
        // (Copie o código do passo anterior aqui se precisar, mas ele já deve estar na sua classe)
    }

    private void voltar() {
        view.dispose();
        ListarEditaisView lista = new ListarEditaisView();
        lista.setVisible(true);
        new ListarEditaisController(lista, nomeCoordenador);
    }
}