package models;

public class UsuarioLogado {
    private String nome;
    private String tipo; // "COORDENADOR" ou "ALUNO"

    public UsuarioLogado(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }	

    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
}