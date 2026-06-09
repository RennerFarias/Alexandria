package br.com.alexandria.model;

public class Funcionario extends Usuario {

    private String cargo;

    public Funcionario(int id, String nome, String cpf, String email, String senha, String cargo) {
        super(id, nome, cpf, email, senha);
        this.cargo = cargo;
    }

    @Override
    public int getDiasPrazoEmprestimo() {
        return 14;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}