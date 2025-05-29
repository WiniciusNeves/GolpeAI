package dev.inove.backend.DTO;

public class LoginResponse {
    private String nome;
    private String token;

    public LoginResponse(String nome, String token) {
        this.nome = nome;
        this.token = token;
    }

    public String getNome() {
        return nome;
    }

    public String getToken() {
        return token;
    }
}
