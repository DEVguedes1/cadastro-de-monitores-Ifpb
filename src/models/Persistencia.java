package models;

import static com.thoughtworks.xstream.XStream.setupDefaultSecurity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class Persistencia {
	private static XStream xstream = new XStream(new DomDriver());
	private static File arq = new File("central.xml");

	static {
		// SOLUÇÃO DO ERRO: Libera geral. Permite ler qualquer classe do projeto.
		xstream.addPermission(AnyTypePermission.ANY);
	}
	
	public static void salvarCentral(CentralDeInformacoes ci) {
		
		String xml = xstream.toXML(ci);
		
		try {
			if (!arq.exists()) {
				arq.createNewFile();
			}
			PrintWriter salvar = new PrintWriter(arq);
			salvar.print(xml);
			salvar.close();	
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static CentralDeInformacoes recuperarCentral() {
		
		setupDefaultSecurity(xstream);
        xstream.allowTypesByWildcard(new String[] { "models.*" });
		
		try {
			if (arq.exists()) {
				FileInputStream fis = new FileInputStream(arq);
				return (CentralDeInformacoes) xstream.fromXML(fis);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return new CentralDeInformacoes();
	}
	
	public ArrayList<Aluno> carregarAlunos(String caminhoArquivo) {
        ArrayList<Aluno> alunos = new ArrayList<>();
        try {
            File arquivoXml = new File(caminhoArquivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(arquivoXml);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("aluno");
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoAluno = (Element) node;
                    String nome = ((Document) elementoAluno).getElementsByTagName("nome").item(0).getTextContent();
                    String email = ((Document) elementoAluno).getElementsByTagName("email").item(0).getTextContent();
                    String cre = ((Document) elementoAluno).getElementsByTagName("cre").item(0).getTextContent();
                    String matricula = ((Document) elementoAluno).getElementsByTagName("matricula").item(0).getTextContent();         
                    String senha = ((Document) elementoAluno).getElementsByTagName("senha").item(0).getTextContent();   
                    String sexoString = ((Document) elementoAluno).getElementsByTagName("sexo").item(0).getTextContent().toUpperCase();
                    double creD = 0;
                    try {
                    	creD = Double.valueOf(cre);
                    }catch(IllegalArgumentException e) {
                    	System.err.println("AVISO: Valor do cre inválido no XML ('" + cre + "') para o aluno " + nome);
                    }
                    
                    Sexo sexoEnum = null;
                    try {
                        sexoEnum = Sexo.valueOf(sexoString);
                    } catch (IllegalArgumentException e) {
                        System.err.println("AVISO: Valor de sexo inválido no XML ('" + sexoString + "') para o aluno " + nome);
                    }
                    alunos.add(new Aluno(nome, matricula,creD,email,senha,sexoEnum));                
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar o XML com DOM: " + e.getMessage());
            e.printStackTrace();
        }
        return alunos;
    }

	public static CentralDeInformacoes carregarOuCriarCentral(Scanner sc) {
	    CentralDeInformacoes ci = recuperarCentral();

	    // Garante listas nunca nulas
	    if (ci.getTodosOsCoordenadores() == null) {
	        try {
	            var campo = CentralDeInformacoes.class.getDeclaredField("todosOsCoordenadores");
	            campo.setAccessible(true);
	            campo.set(ci, new ArrayList<>());
	        } catch (Exception e) {
	            ci.getTodosOsCoordenadores().clear();
	        }
	    }

	    // Requisita cadastro via console (igual no LoginController)
	    if (ci.getTodosOsCoordenadores().isEmpty()) {
	        System.out.println("[!] Nenhum coordenador encontrado no sistema.");
	        System.out.println("[+] Cadastre o primeiro coordenador:");

	        Coordenador coord = Coordenador.cadastrarViaCentral(sc, ci);

	        ci.adicionarCoordenador(coord);
	        salvarCentral(ci);

	        System.out.println("[+] Coordenador salvo com sucesso!");
	    }

	    return ci;
	}

}
