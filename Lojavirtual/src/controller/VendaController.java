package controller;

import dao.VendaDAO;
import dao.PecaRoupaDAO;
import model.ItemCarrinho;
import model.PecaRoupa;

import java.math.BigDecimal;
import java.util.List;

public class VendaController {

    private VendaDAO vendaDAO = new VendaDAO();
    private PecaRoupaDAO pecaDAO = new PecaRoupaDAO();

    public boolean finalizarVenda(List<ItemCarrinho> itens, BigDecimal total) {
        // 1️⃣ Inserir venda
        int idVenda = vendaDAO.inserirVenda(total);
        if (idVenda <= 0) {
            return false;
        }

        // 2️⃣ Inserir itens
        vendaDAO.inserirItensVenda(idVenda, itens);

        // 3️⃣ Atualizar estoque
        for (ItemCarrinho item : itens) {
            PecaRoupa peca = item.getPeca();
            int novoEstoque = peca.getEstoque() - item.getQuantidade();
            pecaDAO.atualizarEstoque(peca.getId(), novoEstoque);
        }

        return true;
    }
}
