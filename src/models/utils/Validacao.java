package models.utils;

/**
 * Classe utilitária para validação de dados de entrada.
 * <p>
 * Contém métodos estáticos para verificar se os dados inseridos pelo usuário
 * (e-mail, senha, matrícula) estão no formato correto antes de serem processados.
 */

import java.util.regex.Pattern;

public class Validacao {

    /**
     * Verifica se uma string é um endereço de e-mail válido.
     * <p>
     * Utiliza Expressões Regulares (Regex) para validar o formato
     * (ex: texto@dominio.com).
     * * @param email O e-mail a ser verificado.
     * @return {@code true} se o formato for válido.
     */

    public static boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(gmail\\.com|hotmail\\.com|academico\\.ifpb\\.edu\\.br)$";
        return Pattern.matches(regex, email);
    }

    /**
     * Verifica se a senha atende aos requisitos de segurança.
     * <p>
     * Critérios comuns:
     * <ul>
     * <li>Mínimo de caracteres (ex: 6 ou 8).</li>
     * <li>Presença de números ou caracteres especiais (opcional).</li>
     * </ul>
     * * @param senha A senha a ser testada.
     * @return {@code true} se a senha for considerada forte/válida.
     */

    public static boolean senhaForte(String senha) {
        if (senha == null || senha.length() < 8) return false;

        boolean temLetra = senha.matches(".*[A-Za-z].*");
        boolean temNumero = senha.matches(".*[0-9].*");
        boolean temEspecial = senha.matches(".*[^A-Za-z0-9].*");

        return temLetra && temNumero && temEspecial;
    }
}