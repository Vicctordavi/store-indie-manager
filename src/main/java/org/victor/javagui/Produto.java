package org.victor.javagui;

public class Produto {
    private String nome;
    private String codigo;
    private String preco;
    private String validade;

    public Produto(String nome, String codigo, String preco, String validade) {
        this.nome = nome;
        this.codigo = codigo;
        this.preco = preco;
        this.validade = validade;
    }

    public String getNome() { return nome; }
    public String getCodigo() { return codigo; }
    public String getPreco() { return preco; }
    public String getValidade() { return validade; }
}
