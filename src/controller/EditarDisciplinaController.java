package controller;

/**
 * Controlador para edição de uma Disciplina específica.
 * <p>
 * Gerencia a janela modal onde o coordenador altera pesos e vagas de uma matéria.
 * Responsabilidades:
 * <ul>
 * <li>Validar se a soma dos pesos (Nota + CRE) é igual a 1.0.</li>
 * <li>Atualizar os dados do objeto {@link Disciplina} na memória.</li>
 * <li>Persistir as alterações no XML imediatamente.</li>
 * </ul>
 */

import view.EditarDisciplinaDialog;
import models.CentralDeInformacoes;
import models.Persistencia;
import models.recurses.Disciplina;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarDisciplinaController implements ActionListener {

    private EditarDisciplinaDialog view;
    private Disciplina disciplinaOriginal;
    private Runnable aoTerminar;

    public EditarDisciplinaController(EditarDisciplinaDialog view, Disciplina disciplina, Runnable aoTerminar) {
        this.view = view;
        this.disciplinaOriginal = disciplina;
        this.aoTerminar = aoTerminar;
        
        this.view.addSalvarListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String nome = view.getNome();
            int vagas = Integer.parseInt(view.getVagas());
            float pNota = Float.parseFloat(view.getPesoNota());
            float pCRE = Float.parseFloat(view.getPesoCRE());

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(view, "O nome não pode ser vazio.");
                return;
            }

            // Validação da Soma 1.0
            if (Math.abs((pNota + pCRE) - 1.0) > 0.001) {
                JOptionPane.showMessageDialog(view, "Erro: A soma dos pesos (Nota + CRE) deve ser 1.0!");
                return;
            }

            // Atualiza o objeto na memória (como é referência, atualiza na lista do Edital tbm)
            disciplinaOriginal.setNomeDisciplina(nome);
            disciplinaOriginal.setQntdVagas(vagas);
            disciplinaOriginal.setPesoNota(pNota);
            disciplinaOriginal.setPesoCRE(pCRE);

            // Salva no XML (Recuperando a central apenas para persistir o estado atual)
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            // Truque: O objeto disciplinaOriginal já está dentro de um Edital que está na Central.
            // Apenas salvar a central atualiza tudo.
            Persistencia.salvarCentral(central);

            JOptionPane.showMessageDialog(view, "Disciplina atualizada!");
            view.dispose();
            
            if (aoTerminar != null) aoTerminar.run();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Erro: Verifique os números (Vagas inteiro, Pesos decimais com ponto).");
        }
    }
}