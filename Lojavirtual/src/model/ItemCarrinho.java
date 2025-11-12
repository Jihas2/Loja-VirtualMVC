package model;

import java.math.BigDecimal;

public class ItemCarrinho {
    private PecaRoupa peca;
    private int quantidade;

    public ItemCarrinho(PecaRoupa peca) {
        this.peca = peca;
        this.quantidade = 1;
    }

    public ItemCarrinho(PecaRoupa peca, int quantidade) {
        this.peca = peca;
        this.quantidade = quantidade > 0 ? quantidade : 1;
    }

    public PecaRoupa getPeca() {
        return peca;
    }

    public void setPeca(PecaRoupa peca) {
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade > 0 ? quantidade : 1;
    }

    public void incrementarQuantidade() {
        this.quantidade++;
    }

    public void decrementarQuantidade() {
        if (this.quantidade > 1) {
            this.quantidade--;
        }
    }

    public BigDecimal getSubtotal() {
        return peca.getPreco().multiply(new BigDecimal(quantidade));
    }

    @Override
    public String toString() {
        return String.format("%s (x%d) - R$ %.2f",
                peca.getNome(), quantidade, getSubtotal());
    }
}