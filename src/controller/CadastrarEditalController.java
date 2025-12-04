package controller;

import view.CadastrarEditalView;
import view.DashboardCoordenador;
import models.recurses.Edital;
import models.CentralDeInformacoes;
import models.Persistencia;
import models.utils.UsuarioService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CadastrarEditalController implements ActionListener {

    private CadastrarEditalView view;
    private String nomeCoordenadorLogado; // Para saber quem criou, se precisar voltar pro dashboard

    public CadastrarEditalController(CadastrarEditalView view, String nomeCoord) {
        this.view = view;
        this.nomeCoordenadorLogado = nomeCoord;
        this.view.addSalvarListener(this);
        this.view.addCancelarListener(e -> voltarDashboard());
    }

    private void voltarDashboard() {
        view.dispose();
        new DashboardCoordenador(nomeCoordenadorLogado).setVisible(true);
        // Nota: Precisaremos conectar o controller do Dashboard aqui também
        // Mas por enquanto, vamos focar em salvar o Edital
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String num = view.getNumero();
            String dtIniStr = view.getDataInicio();
            String dtFimStr = view.getDataFim();
            String maxStr = view.getMaxInscricoes();

            // 1. Validação Simples
            if (num.isEmpty() || dtIniStr.contains("_") || dtFimStr.contains("_") || maxStr.isEmpty()) {
                view.mostrarMensagem("Preencha todos os campos!");
                return;
            }

            // 2. Converter Dados
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataInicio = LocalDate.parse(dtIniStr, formatter);
            LocalDate dataFim = LocalDate.parse(dtFimStr, formatter);
            int maxInscricoes = Integer.parseInt(maxStr);

            if (dataFim.isBefore(dataInicio)) {
                view.mostrarMensagem("Erro: Data final não pode ser antes da inicial!");
                return;
            }

            // 3. Criar Edital (Começa sem disciplinas, vamos adicionar depois)
            Edital novoEdital = new Edital(num, dataInicio, dataFim, new ArrayList<>(), maxInscricoes);

            // 4. Salvar na Central
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            
            if (central.adicionarEdital(novoEdital)) {
                Persistencia.salvarCentral(central);
                view.mostrarMensagem("Edital criado com sucesso!");
                voltarDashboard();
            } else {
                view.mostrarMensagem("Erro: Já existe um edital com esse ID/Número.");
            }

        } catch (NumberFormatException ex) {
            view.mostrarMensagem("Erro: 'Máx Inscrições' deve ser um número inteiro.");
        } catch (Exception ex) {
            ex.printStackTrace();
            view.mostrarMensagem("Erro ao salvar: " + ex.getMessage());
        }
    }
}