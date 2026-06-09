package br.com.alexandria.model;

import java.math.BigDecimal;

public class Livro {

    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private BigDecimal precoCusto;
    private int quantidadeEstoque;
    private String status;

    public Livro(int id, String titulo, String autor, String isbn, BigDecimal precoCusto, int quantidadeEstoque, String status) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.precoCusto = precoCusto;
        this.quantidadeEstoque = quantidadeEstoque;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(BigDecimal precoCusto) {
        if (precoCusto == null || precoCusto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço de custo não pode ser negativo.");
        }
        this.precoCusto = precoCusto;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa.");
        }
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}