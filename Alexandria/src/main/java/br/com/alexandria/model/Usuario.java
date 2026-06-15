package br.com.alexandria.model;

public abstract class Usuario {

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String tipo;

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;

    }

    public Usuario(int id, String nome, String cpf, String email, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }


    public abstract int getDiasPrazoEmprestimo();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido: deve conter exatamente 11 dígitos.");
        }
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}