package controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import models.Aluno;
import models.Usuario;

public class LoginController {

    private List<Usuario> usuarios = new ArrayList<>();
    private final String ARQUIVO_JSON = "usuarios.json";
    private final Gson gson;

    public LoginController() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        carregarUsuarios();
    }

    // Refatorado: Lança exceção com a mensagem de erro específica
    // Isso permite que o teste capture o motivo exato da falha
    public void cadastrarUsuario(Usuario usuario) throws IllegalArgumentException {
        if (!validarEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail inválido! Domínios aceitos: gmail, hotmail, academico.ifpb.");
        }

        if (!validarSenhaForte(usuario.getSenha())) {
            throw new IllegalArgumentException("Senha fraca! Mínimo 8 caracteres, letras, números e especiais.");
        }

        if (emailExiste(usuario.getEmail())) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado.");
        }

        usuarios.add(usuario);
        salvarUsuarios();
    }

    public Usuario fazerLogin(String email, String senha) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha))
                .findFirst()
                .orElse(null);
    }

    // ... Mantenha os métodos auxiliares (validarEmail, validarSenhaForte, salvar, carregar) iguais ...
    
    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(gmail\\.com|hotmail\\.com|academico\\.ifpb\\.edu\\.br)$";
        return Pattern.matches(regex, email);
    }

    private boolean validarSenhaForte(String senha) {
        if (senha == null || senha.length() < 8) return false;
        boolean temLetra = senha.matches(".*[A-Za-z].*");
        boolean temNumero = senha.matches(".*[0-9].*");
        boolean temEspecial = senha.matches(".*[^A-Za-z0-9].*");
        return (temLetra && temNumero && temEspecial);
    }
    
    public boolean emailExiste(String email) {
        return usuarios.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public void salvarUsuarios() {
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public void carregarUsuarios() {
        try (FileReader reader = new FileReader(ARQUIVO_JSON)) {
            Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            usuarios = gson.fromJson(reader, listType);
            if (usuarios == null) usuarios = new ArrayList<>();
        } catch (IOException e) {
            usuarios = new ArrayList<>();
        }
    }
    
    // Adicionado para facilitar limpeza em testes
    public void limparBaseDados() {
        this.usuarios.clear();
        salvarUsuarios();
    }
    
    public List<Aluno> listarApenasAlunos() {
        // Filtra a lista de usuarios pegando apenas quem é instancia de Aluno
        return usuarios.stream()
                .filter(u -> u instanceof Aluno)
                .map(u -> (Aluno) u)
                .collect(Collectors.toList());
    }

    public Aluno buscarAlunoPorMatricula(String matricula) {
        return usuarios.stream()
                .filter(u -> u instanceof Aluno)
                .map(u -> (Aluno) u)
                .filter(a -> a.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElse(null);
    }
}