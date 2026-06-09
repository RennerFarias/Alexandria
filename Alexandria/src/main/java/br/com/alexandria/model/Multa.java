package br.com.alexandria.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Multa {

    private int id;
    private int idEmprestimo;
    private BigDecimal valor;
    private LocalDate dataGeracao;
    private boolean paga;

    public Multa(int id, int idEmprestimo, BigDecimal valor, LocalDate dataGeracao, boolean paga) {
        this.id = id;
        this.idEmprestimo = idEmprestimo;
        this.valor = valor;
        this.dataGeracao = dataGeracao;
        this.paga = paga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor da multa não pode ser negativo.");
        }
        this.valor = valor;
    }

    public LocalDate getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDate dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }
}