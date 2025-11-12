package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoCompras {
    private List<ItemCarrinho> itens;

    public CarrinhoCompras() {
        this.itens = new ArrayList<>();
    }

    public void adicionarPeca(PecaRoupa peca) {
        for (ItemCarrinho item : itens) {
            if (item.getPeca().getId() == peca.getId()) {
                item.incrementarQuantidade();
                return;
            }
        }
        itens.add(new ItemCarrinho(peca));
    }

    public void removerPeca(int pecaId) {
        itens.removeIf(item -> item.getPeca().getId() == pecaId);
    }

    public void limpar() {
        itens.clear();
    }

    public List<ItemCarrinho> getItens() {
        return new ArrayList<>(itens);
    }

    public int getQuantidadeTotal() {
        return itens.stream()
                .mapToInt(ItemCarrinho::getQuantidade)
                .sum();
    }

    public BigDecimal getValorTotal() {
        return itens.stream()
                .map(ItemCarrinho::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() {
        return itens.isEmpty();
    }

    public int getTamanho() {
        return itens.size();
    }

    public void incrementarQuantidade(int pecaId) {
        for (ItemCarrinho item : itens) {
            if (item.getPeca().getId() == pecaId) {
                item.incrementarQuantidade();
                return;
            }
        }
    }

    public void decrementarQuantidade(int pecaId) {
        for (ItemCarrinho item : itens) {
            if (item.getPeca().getId() == pecaId) {
                item.decrementarQuantidade();
                return;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Carrinho: %d itens - Total: R$ %.2f",
                getQuantidadeTotal(), getValorTotal());
    }
}