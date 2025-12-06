package model;

import java.math.BigDecimal;
import java.util.Objects;

public class PecaRoupa {
    private int id;
    private String nome;
    private String tipo;
    private String tamanho;
    private String cor;
    private BigDecimal preco;
    private String descricao;
    private String imagemBase64; // <-- AGORA A IMAGEM É SALVA EM BASE64
    private int estoque;

    // ----- CONSTRUTOR COMPLETO -----
    public PecaRoupa(int id, String nome, String tipo, String tamanho,
                     String cor, BigDecimal preco, String descricao,
                     String imagemBase64, int estoque) {

        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.cor = cor;
        this.preco = preco != null ? preco : BigDecimal.ZERO;
        this.descricao = descricao;
        this.imagemBase64 = imagemBase64; // Base64 aqui
        this.estoque = estoque;
    }

    // ----- CONSTRUTOR PARA NOVAS PEÇAS -----
    public PecaRoupa(String nome, String tipo, String tamanho,
                     String cor, BigDecimal preco, String descricao,
                     String imagemBase64, int estoque) {

        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.cor = cor;
        this.preco = preco != null ? preco : BigDecimal.ZERO;
        this.descricao = descricao;
        this.imagemBase64 = imagemBase64;
        this.estoque = estoque;
    }

    // ----- GETTERS E SETTERS -----
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

    public String getImagemBase64() { return imagemBase64; }
    public void setImagemBase64(String imagemBase64) { this.imagemBase64 = imagemBase64; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public boolean isValida() {
        return nome != null && !nome.trim().isEmpty() &&
               tipo != null && !tipo.trim().isEmpty() &&
               tamanho != null && !tamanho.trim().isEmpty() &&
               preco != null && preco.compareTo(BigDecimal.ZERO) >= 0;
    }

    public String getResumo() {
        return String.format("%s - %s (Tamanho: %s) - R$ %.2f",
                nome, tipo, tamanho, preco);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PecaRoupa pecaRoupa = (PecaRoupa) o;
        return id == pecaRoupa.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() { return getResumo(); }
}
