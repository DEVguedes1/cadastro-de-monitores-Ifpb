package models.utils;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UsuarioService {

    private static final String CAMINHO_ARQUIVO = "central.xml";

    // Agora retorna String (o nome) em vez de boolean
    public String validarUsuarioXML(String emailDigitado, String senhaDigitada) {
        try {
            File arquivo = new File(CAMINHO_ARQUIVO);
            if (!arquivo.exists()) return null;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(arquivo);
            doc.getDocumentElement().normalize();

            // 1. TENTA COORDENADOR (Tag de nome é "nome")
            String nomeEncontrado = buscarNaLista(doc, "models.Coordenador", emailDigitado, senhaDigitada, "nome");
            if (nomeEncontrado != null) {
                return nomeEncontrado;
            }

            // 2. TENTA ALUNO (Tag de nome é "nomeDoAluno")
            nomeEncontrado = buscarNaLista(doc, "models.Aluno", emailDigitado, senhaDigitada, "nomeDoAluno");
            if (nomeEncontrado != null) {
                return nomeEncontrado;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null; // Não achou ninguém
    }

    // Método auxiliar atualizado para pegar o nome correto
    private String buscarNaLista(Document doc, String tagUsuario, String emailAlvo, String senhaAlvo, String tagNome) {
        NodeList lista = doc.getElementsByTagName(tagUsuario);

        for (int i = 0; i < lista.getLength(); i++) {
            Node node = lista.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) node;

                // Verifica se tem email (ignora referências vazias)
                if (elemento.getElementsByTagName("email").getLength() > 0) {
                    
                    String emailXml = elemento.getElementsByTagName("email").item(0).getTextContent();
                    String senhaXml = elemento.getElementsByTagName("senha").item(0).getTextContent();

                    if (emailXml.equalsIgnoreCase(emailAlvo) && senhaXml.equals(senhaAlvo)) {
                        // ACHOU! Agora retorna o conteúdo da tag de nome específica
                        return elemento.getElementsByTagName(tagNome).item(0).getTextContent();
                    }
                }
            }
        }
        return null; // Não achou nesta lista
    }
}