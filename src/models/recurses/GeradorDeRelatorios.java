package models.recurses;

/**
 * Classe utilitária responsável pela geração de arquivos PDF do sistema.
 * <p>
 * Utiliza a biblioteca <b>iTextPDF</b> para criar documentos formatados contendo:
 * <ul>
 * <li>Cabeçalhos com informações do Edital.</li>
 * <li>Tabelas de ranking por disciplina.</li>
 * <li>Lista de aprovados e classificados.</li>
 * </ul>
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.Inscricao;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GeradorDeRelatorios {

    private static final Font FONT_TITULO = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font FONT_SUB = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font FONT_NORMAL = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private static final Font FONT_HEADER = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);

    /**
     * Gera um arquivo PDF contendo o resultado final do edital.
     * <p>
     * O arquivo é salvo na raiz do projeto com o nome padronizado:
     * {@code Resultado_Edital_NUMERO.pdf}.
     * <p>
     * A estrutura do documento segue a ordem:
     * 1. Título e Datas.
     * 2. Loop por Disciplina (com tabela de inscritos ordenada por nota).
     * * @param edital O objeto Edital contendo os dados processados.
     * @throws Exception Se houver erro de I/O ou falha na biblioteca PDF.
     */

    public static void gerarRelatorioRanking(Edital edital) throws Exception {
        
        // Nome do arquivo: Resultado_Edital_2025-1.pdf
        String nomeArquivo = "Resultado_Edital_" + edital.getNumEdital().replace("/", "-") + ".pdf";
        
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(nomeArquivo));
        document.open();

        // 1. Título do PDF
        Paragraph titulo = new Paragraph("Resultado Final - Edital " + edital.getNumEdital(), FONT_TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String textoData = "Período: " + edital.getDataIncio().format(fmt) + " a " + edital.getDataFinal().format(fmt);
        Paragraph sub = new Paragraph(textoData + "\n\n", FONT_NORMAL);
        sub.setAlignment(Element.ALIGN_CENTER);
        document.add(sub);

        // 2. Loop por Disciplina (Cria uma tabela para cada matéria)
        if (edital.getDisciplinas() != null) {
            for (Disciplina d : edital.getDisciplinas()) {
                
                // Nome da Disciplina
                Paragraph pDisc = new Paragraph("Disciplina: " + d.getNomeDisciplina() + 
                                                " (" + d.getQntdVagas() + " vagas)", FONT_SUB);
                pDisc.setSpacingAfter(5);
                document.add(pDisc);

                // Configuração da Tabela (4 Colunas)
                PdfPTable tabela = new PdfPTable(4); 
                tabela.setWidthPercentage(100);
                tabela.setWidths(new float[]{40f, 20f, 15f, 25f}); // Largura das colunas

                // Cabeçalho da Tabela
                addCelulaCabecalho(tabela, "Nome do Aluno");
                addCelulaCabecalho(tabela, "Nota Final");
                addCelulaCabecalho(tabela, "Posição");
                addCelulaCabecalho(tabela, "Situação");

                // Dados dos Alunos
                ArrayList<Inscricao> inscricoes = d.getInscricoes();
                
                // Garante que está ordenado por nota (segurança extra)
                inscricoes.sort((a, b) -> Double.compare(b.getNotaFinal(), a.getNotaFinal()));

                int posicao = 1;
                for (Inscricao i : inscricoes) {
                    tabela.addCell(new Phrase(i.getAluno().getNomeDoAluno(), FONT_NORMAL));
                    tabela.addCell(new Phrase(String.format("%.2f", i.getNotaFinal()), FONT_NORMAL));
                    tabela.addCell(new Phrase(posicao + "º", FONT_NORMAL));
                    
                    // Pinta a célula de situação (Opcional, mas fica legal)
                    PdfPCell cellSit = new PdfPCell(new Phrase(i.getSituacao().toString(), FONT_NORMAL));
                    if(i.getSituacao().toString().contains("APROVADO")) {
                        cellSit.setBackgroundColor(new BaseColor(200, 255, 200)); // Verde claro
                    }
                    tabela.addCell(cellSit);
                    
                    posicao++;
                }

                document.add(tabela);
                document.add(new Paragraph("\n")); // Espaço em branco
            }
        } else {
            document.add(new Paragraph("Nenhuma disciplina cadastrada neste edital."));
        }

        document.close();
        System.out.println("PDF Gerado: " + nomeArquivo);
    }

    // Auxiliar para criar cabeçalho cinza bonito
    private static void addCelulaCabecalho(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FONT_HEADER));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }
    
}