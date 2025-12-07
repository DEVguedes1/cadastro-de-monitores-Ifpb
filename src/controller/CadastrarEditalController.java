package controller;

/**
 * Controlador responsável por gerenciar a criação de novos Editais.
 *
 * <p>Esta classe executa:
 * <ul>
 *     <li>Validação dos campos preenchidos;</li>
 *     <li>Conversão das datas;</li>
 *     <li>Criação do objeto Edital;</li>
 *     <li>Registro na Central de Informações;</li>
 *     <li>Navegação de retorno ao Dashboard do Coordenador.</li>
 * </ul>
 *
 * O fluxo geral é:
 * <ol>
 *     <li>O coordenador preenche o formulário;</li>
 *     <li>O sistema valida as entradas;</li>
 *     <li>O edital é criado e salvo no XML;</li>
 *     <li>Retorna ao dashboard com mensagem de sucesso.</li>
 * </ol>
 *
 * @author Seu Nome
 */

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

    /**
     * Construtor.
     *
     * @param view tela de cadastro do edital
     * @param nomeCoord nome do coordenador logado
     */

    public CadastrarEditalController(CadastrarEditalView view, String nomeCoord) {
        this.view = view;
        this.nomeCoordenadorLogado = nomeCoord;
        this.view.addSalvarListener(this);
        this.view.addCancelarListener(e -> voltarDashboard());
    }

     /**
     * Retorna o usuário ao dashboard principal.
     */

    private void voltarDashboard() {
        view.dispose();
        new DashboardCoordenador(nomeCoordenadorLogado).setVisible(true);
    }

    /** {@inheritDoc} */
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