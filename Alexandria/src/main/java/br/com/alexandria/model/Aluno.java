package br.com.alexandria.model;

public class Aluno extends Usuario {

    private String matricula;

    public Aluno(int id, String nome, String cpf, String email, String senha, String matricula) {
        super(id, nome, cpf, email, senha);
        this.matricula = matricula;
    }

    @Override
    public int getDiasPrazoEmprestimo() {
        return 7;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}