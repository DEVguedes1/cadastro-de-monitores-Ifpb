package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Usuario;
import models.Aluno;
import models.Coordenador;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List; 
import java.util.regex.Pattern;

public class LoginController {

    private List<Usuario> usuarios = new ArrayList<>();
    private final String ARQUIVO_JSON = "usuarios.json";
    private final Gson gson;

    public LoginController() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
        carregarUsuarios();
    }

    // VALIDAÇÕES
    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(gmail\\.com|hotmail\\.com|academico\\.ifpb\\.edu\\.br)$";
        return Pattern.matches(regex, email);
    }

    private boolean validarSenhaForte(String senha) {
        if (senha.length() < 8) return false;
        boolean temLetra = senha.matches(".*[A-Za-z].*");
        boolean temNumero = senha.matches(".*[0-9].*");
        boolean temEspecial = senha.matches(".*[^A-Za-z0-9].*");
        return (temLetra && temNumero && temEspecial);
    }

    public boolean emailExiste(String email) {
        return usuarios.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    // CADASTRO
    public boolean cadastrarUsuario(Usuario usuario) {
        if (!validarEmail(usuario.getEmail())) {
            System.out.println("E-mail inválido! Deve ser @gmail.com, @hotmail.com ou @academico.ifpb.edu.br");
            return false;
        }

        if (!validarSenhaForte(usuario.getSenha())) {
            System.out.println("Senha inválida! Deve ter pelo menos 8 caracteres, incluindo letras, números e caracteres especiais.");
            return false;
        }

        if (emailExiste(usuario.getEmail())) {
            System.out.println("Esse e-mail já está em uso.");
            return false;
        }

        usuarios.add(usuario);
        salvarUsuarios(); 
        System.out.println("Usuário cadastrado com sucesso!");
        return true;
    }

    // LOGIN
    public Usuario fazerLogin(String email, String senha) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha))
                .findFirst()
                .orElse(null);
    }

    // USUÁRIOS
    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            usuarios.forEach(System.out::println);
        }
    }

    // Salvar e carregar usuários
    public void salvarUsuarios() {
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            gson.toJson(usuarios, writer);
            System.out.println("Usuários salvos em JSON com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar JSON: " + e.getMessage());
        }
    }
    
    public void carregarUsuarios() {
        try (FileReader reader = new FileReader(ARQUIVO_JSON)) {
            Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            usuarios = gson.fromJson(reader, listType);
            if (usuarios == null) {
                usuarios = new ArrayList<>();
            }
            System.out.println("Usuários carregados do JSON com sucesso!");
        } catch (IOException e) {
            System.out.println("Arquivo JSON não encontrado ou vazio, iniciando com lista vazia.");
            usuarios = new ArrayList<>();
        }
    }

    public Usuario getUsuarioPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}