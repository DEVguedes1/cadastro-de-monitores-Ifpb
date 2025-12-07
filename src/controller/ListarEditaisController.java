package controller;

/**
 * Controlador central de Gestão de Editais (Visão do Coordenador).
 * <p>
 * Possui uma lógica de <b>Triagem Inteligente</b> ao abrir um edital:
 * <ul>
 * <li>Se o edital tem inscritos ou está fechado -> Abre a tela de <b>Resultados</b>.</li>
 * <li>Se o edital está vazio -> Abre a tela de <b>Configuração</b> (Adicionar Disciplinas).</li>
 * </ul>
 * Também gerencia exclusão e clonagem de editais.
 */

import view.ListarEditaisView;
import view.DashboardCoordenador;
import view.DetalharEditalView; // Tela de ADICIONAR DISCIPLINAS
import view.ResultadoEditalView; // Tela de RANKING/NOTIFICAR
import view.EditarEditalDialog;

import models.CentralDeInformacoes;
import models.Persistencia;
import models.recurses.Disciplina;
import models.recurses.Edital;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListarEditaisController implements ActionListener {

    private ListarEditaisView view;
    private String nomeCoordenador;

    public ListarEditaisController(ListarEditaisView view, String nomeCoord) {
        this.view = view;
        this.nomeCoordenador = nomeCoord;
        
        this.view.addVoltarListener(e -> voltar());
        this.view.addDetalharListener(this); // Ação de abrir
        this.view.addEditarListener(e -> editarEdital());
        this.view.addExcluirListener(e -> excluirEdital());
        this.view.addClonarListener(e -> clonarEdital()); // Listener do Clone
        
        carregarTabela();
    }

    // --- A MÁGICA DA TRIAGEM ACONTECE AQUI ---
    @Override
    public void actionPerformed(ActionEvent e) {
        String idStr = view.getIdSelecionado();
        
        if (idStr != null) {
            long idEdital = Long.parseLong(idStr);
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            Edital edital = central.recuperarEditalPorId(idEdital);
            
            if (edital == null) return;

            view.dispose(); // Fecha a lista

            // LÓGICA INTELIGENTE:
            // Verifica se existe ALGUM aluno inscrito em ALGUMA disciplina
            boolean temInscricoes = false;
            if (edital.getDisciplinas() != null) {
                for (Disciplina d : edital.getDisciplinas()) {
                    if (d.getInscricoes() != null && !d.getInscricoes().isEmpty()) {
                        temInscricoes = true;
                        break;
                    }
                }
            }

            // DECISÃO:
            // Se tiver inscritos OU estiver encerrado -> Vai para RESULTADOS (Botão Notificar, PDF)
            // Se estiver vazio -> Vai para CONFIGURAÇÃO (Botão Adicionar Disciplina)
            if (temInscricoes || edital.getStatus() == Edital.Status.ENCERRADO) {
                ResultadoEditalView resultView = new ResultadoEditalView(edital);
                resultView.setVisible(true);
                new ResultadoEditalController(resultView, edital, nomeCoordenador);
            } else {
                DetalharEditalView detalheView = new DetalharEditalView();
                detalheView.setVisible(true);
                // ATENÇÃO: Essa é a tela que tem os campos para adicionar disciplina!
                new DetalharEditalController(detalheView, idEdital, nomeCoordenador);
            }
            
        } else {
            view.mostrarMensagem("Selecione um edital na tabela primeiro!");
        }
    }

    private void carregarTabela() {
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        ArrayList<Edital> lista = central.getTodosOsEditais();
        DefaultTableModel model = view.getModeloTabela();
        model.setRowCount(0);
        
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Edital e : lista) {
            String status = "Aberto";
            LocalDate hoje = LocalDate.now();
            if (e.getStatus() == Edital.Status.ENCERRADO) status = "Finalizado";
            else if (hoje.isBefore(e.getDataIncio())) status = "Não Iniciado";
            else if (hoje.isAfter(e.getDataFinal())) status = "Prazo Encerrado";

            model.addRow(new Object[]{
                e.getId(),
                e.getNumEdital(),
                e.getDataIncio().format(fmt),
                e.getDataFinal().format(fmt),
                status
            });
        }
    }

    private void editarEdital() {
        String idStr = view.getIdSelecionado();
        if (idStr == null) { view.mostrarMensagem("Selecione para editar."); return; }
        
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        Edital edital = central.recuperarEditalPorId(Long.parseLong(idStr));
        
        if (edital != null) {
            EditarEditalDialog dialog = new EditarEditalDialog(view, edital);
            new EditarEditalController(dialog, edital, () -> carregarTabela());
            dialog.setVisible(true);
        }
    }

    private void excluirEdital() {
        String idStr = view.getIdSelecionado();
        if (idStr == null) { view.mostrarMensagem("Selecione para excluir."); return; }

        int confirm = JOptionPane.showConfirmDialog(view, 
            "Tem certeza que deseja EXCLUIR este edital?", "Excluir", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            Edital edital = central.recuperarEditalPorId(Long.parseLong(idStr));
            if (edital != null) {
                central.getTodosOsEditais().remove(edital);
                Persistencia.salvarCentral(central);
                view.mostrarMensagem("Edital excluído.");
                carregarTabela();
            }
        }
    }
    
    private void clonarEdital() {
        String idStr = view.getIdSelecionado();
        if (idStr == null) { view.mostrarMensagem("Selecione para clonar."); return; }

        CentralDeInformacoes central = Persistencia.recuperarCentral();
        Edital original = central.recuperarEditalPorId(Long.parseLong(idStr));

        if (original != null) {
            if(JOptionPane.showConfirmDialog(view, "Clonar edital " + original.getNumEdital() + "?", "Clonar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                Edital clone = original.clonarEdital();
                central.adicionarEdital(clone);
                Persistencia.salvarCentral(central);
                view.mostrarMensagem("Clonado com sucesso!");
                carregarTabela();
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