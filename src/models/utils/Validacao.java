package models.utils;

import java.util.regex.Pattern;

public class Validacao {

    public static boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(gmail\\.com|hotmail\\.com|academico\\.ifpb\\.edu\\.br)$";
        return Pattern.matches(regex, email);
    }

    public static boolean senhaForte(String senha) {
        if (senha == null || senha.length() < 8) return false;

        boolean temLetra = senha.matches(".*[A-Za-z].*");
        boolean temNumero = senha.matches(".*[0-9].*");
        boolean temEspecial = senha.matches(".*[^A-Za-z0-9].*");

        return temLetra && temNumero && temEspecial;
    }
}