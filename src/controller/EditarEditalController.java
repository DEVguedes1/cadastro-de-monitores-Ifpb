package controller;

import view.EditarEditalDialog;
import models.CentralDeInformacoes;
import models.Persistencia;
import models.recurses.Edital;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditarEditalController implements ActionListener {

    private EditarEditalDialog view;
    private Edital editalOriginal;
    private Runnable aoTerminar;

    public EditarEditalController(EditarEditalDialog view, Edital edital, Runnable aoTerminar) {
        this.view = view;
        this.editalOriginal = edital;
        this.aoTerminar = aoTerminar;
        
        this.view.addSalvarListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String dtIniStr = view.getDataInicio();
            String dtFimStr = view.getDataFim();
            String maxStr = view.getMaxInscricoes();

            if (dtIniStr.contains("_") || dtFimStr.contains("_") || maxStr.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
                return;
            }

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate novaDataInicio = LocalDate.parse(dtIniStr, fmt);
            LocalDate novaDataFim = LocalDate.parse(dtFimStr, fmt);
            int novoMax = Integer.parseInt(maxStr);

            if (novaDataFim.isBefore(novaDataInicio)) {
                JOptionPane.showMessageDialog(view, "Erro: Data final não pode ser antes da inicial.");
                return;
            }

            // --- ATUALIZAÇÃO ---
            // Recupera a central para garantir a referência correta
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            Edital editalBanco = central.recuperarEditalPorId(editalOriginal.getId());

            if (editalBanco != null) {
                editalBanco.setDataIncio(novaDataInicio);
                editalBanco.setDataFinal(novaDataFim);
                editalBanco.setMaxInc(novoMax);
                
                Persistencia.salvarCentral(central);
                
                JOptionPane.showMessageDialog(view, "Edital atualizado com sucesso!");
                view.dispose();
                
                if (aoTerminar != null) aoTerminar.run(); // Atualiza a tabela de baixo
            } else {
                JOptionPane.showMessageDialog(view, "Erro: Edital não encontrado no banco.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro nos dados: " + ex.getMessage());
        }
    }
}