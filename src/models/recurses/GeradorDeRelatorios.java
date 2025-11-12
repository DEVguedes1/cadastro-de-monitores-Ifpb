package models.recurses;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

// --- IMPORTS DO ITEXT (CORRETOS) ---
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document; 
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;     
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// --- IMPORTS DOS MODELOS ---
import models.Aluno;
import models.CentralDeInformacoes;

public class GeradorDeRelatorios {
	
	private static final Font FONT_TITULO = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private static final Font FONT_SUBTITULO = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	private static final Font FONT_CORPO = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static final Font FONT_CABECALHO_TABELA = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	
	public static void gerarRelatorioInscricoes(String matricula, long idEdital, CentralDeInformacoes ci) {
		
		Aluno aluno = ci.recuperarAlunoPorMatricula(matricula);
		Edital edital = ci.recuperarEditalPorId(idEdital); 
		
		if (aluno == null) {
			System.err.println("[!] Erro ao gerar PDF: Aluno com matrícula " + matricula + " não encontrado.");
			return;
		}
		if (edital == null) {
			System.err.println("[!] Erro ao gerar PDF: Edital com ID " + idEdital + " não encontrado.");
			return;
		}
		List<Disciplina> inscricoes = ci.recuperarInscriçõesDeUmAlunoEmUmEdital(matricula, idEdital);
		
		if (inscricoes == null || inscricoes.isEmpty()) {
			System.out.println("\n[i] Relatório: O aluno " + aluno.getNomeDoAluno() + " não possui inscrições ativas no edital " + edital.getNumEdital() + ".");
			return;
		}
		
		String NOME_ARQUIVO = "relatorio_" + matricula + ".pdf"; 
		Document documento = new Document(PageSize.A4);
		
		try {
			PdfWriter.getInstance(documento, new FileOutputStream(NOME_ARQUIVO));
			
			documento.open();
			
			Paragraph titulo = new Paragraph("Relatório de Inscrições", FONT_TITULO);
			titulo.setAlignment(Element.ALIGN_CENTER);
			titulo.setSpacingAfter(20); 
			documento.add(titulo);
			
			documento.add(new Paragraph("Aluno: " + aluno.getNomeDoAluno(), FONT_SUBTITULO)); 
			documento.add(new Paragraph("Matrícula: " + aluno.getMatricula(), FONT_CORPO));
			documento.add(new Paragraph("Edital: " + edital.getNumEdital(), FONT_CORPO));
			documento.add(new Paragraph("Período: " + edital.getDataIncio() + " a " + edital.getDataFinal(), FONT_CORPO));
			
			documento.add(new Paragraph(" ")); 
			
			PdfPTable tabela = new PdfPTable(2);
			tabela.setWidthPercentage(100); 
			tabela.setSpacingBefore(10);
			
			PdfPCell cellHeader1 = new PdfPCell(new Paragraph("Disciplina Inscrita", FONT_CABECALHO_TABELA));
			cellHeader1.setBackgroundColor(BaseColor.DARK_GRAY);
			cellHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellHeader1.setPadding(5);
			
			PdfPCell cellHeader2 = new PdfPCell(new Paragraph("Vagas Ofertadas", FONT_CABECALHO_TABELA));
			cellHeader2.setBackgroundColor(BaseColor.DARK_GRAY);
			cellHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellHeader2.setPadding(5);
			
			tabela.addCell(cellHeader1);
			tabela.addCell(cellHeader2);
			
			for (Disciplina disc : inscricoes) {
				tabela.addCell(new PdfPCell(new Paragraph(disc.getNomeDisciplina(), FONT_CORPO)));
				tabela.addCell(new PdfPCell(new Paragraph(String.valueOf(disc.getQntdVagas()), FONT_CORPO)));
			}
			
			documento.add(tabela);
			
			System.out.println("\n[+] Relatório gerado com sucesso: " + NOME_ARQUIVO);
			
		} catch (DocumentException | FileNotFoundException e) {
			System.err.println("[!] Erro ao gerar o PDF:");
			e.printStackTrace();
		} finally {
			if (documento.isOpen()) {
				documento.close();
			}
		}
	}
}