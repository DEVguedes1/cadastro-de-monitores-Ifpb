package models;

/**
 * Objeto de Transferência de Dados (DTO) que representa a Sessão Atual.
 * <p>
 * Esta classe é usada apenas para transportar as informações básicas do usuário
 * que acabou de fazer login para a próxima tela (Dashboard).
 * <p>
 * Não contém lógica complexa, apenas dados imutáveis de identificação.
 */

public class UsuarioLogado {
    private String nome;
    private String tipo; // "COORDENADOR" ou "ALUNO"

    /**
     * Cria uma nova sessão de usuário.
     * * @param nome O nome do usuário para exibição nas boas-vindas.
     * @param tipo O tipo de perfil para definir qual Dashboard abrir.
     */

    public UsuarioLogado(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }	
    
    /**
     * Recupera o nome do usuário logado.
     * @return O nome completo ou primeiro nome.
     */
    
    public String getNome() { return nome; }
    
    /**
     * Recupera o tipo de acesso do usuário.
     * @return String indicando "COORDENADOR" ou "ALUNO".
     */
    
    public String getTipo() { return tipo; }
}