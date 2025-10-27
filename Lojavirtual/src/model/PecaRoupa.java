package model;

import java.math.BigDecimal;

public class PecaRoupa {
    private int id;
    private String nome;
    private String tipo;
    private String tamanho;
    private String cor;
    private BigDecimal preco;
    private String descricao;
    private String caminhoImagem; // NOVO campo para imagem

    public PecaRoupa() {}

    public PecaRoupa(String nome, String tipo, String tamanho, String cor, BigDecimal preco) {
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.cor = cor;
        this.preco = preco;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCaminhoImagem() { return caminhoImagem; }
    public void setCaminhoImagem(String caminhoImagem) { this.caminhoImagem = caminhoImagem; }

    @Override
    public String toString() {
        return nome + " - " + tipo + " - R$ " + preco;
    }
}