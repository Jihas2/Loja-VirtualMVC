package controller;

import model.CarrinhoCompras;
import model.ItemCarrinho;
import model.PecaRoupa;
import java.math.BigDecimal;
import java.util.List;

public class CarrinhoController {
    private static CarrinhoController instance;
    private CarrinhoCompras carrinho;

    private CarrinhoController() {
        this.carrinho = new CarrinhoCompras();
    }

    public static CarrinhoController getInstance() {
        if (instance == null) {
            instance = new CarrinhoController();
        }
        return instance;
    }

    public void adicionarPeca(PecaRoupa peca) {
        carrinho.adicionarPeca(peca);
    }

    public void removerPeca(int pecaId) {
        carrinho.removerPeca(pecaId);
    }

    public void incrementarQuantidade(int pecaId) {
        carrinho.incrementarQuantidade(pecaId);
    }

    public void decrementarQuantidade(int pecaId) {
        carrinho.decrementarQuantidade(pecaId);
    }

    public List<ItemCarrinho> getItens() {
        return carrinho.getItens();
    }

    public BigDecimal getValorTotal() {
        return carrinho.getValorTotal();
    }

    public int getQuantidadeTotal() {
        return carrinho.getQuantidadeTotal();
    }

    public boolean isEmpty() {
        return carrinho.isEmpty();
    }

    public void limparCarrinho() {
        carrinho.limpar();
    }

    public CarrinhoCompras getCarrinho() {
        return carrinho;
    }
}