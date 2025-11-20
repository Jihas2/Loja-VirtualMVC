package controller;

import dao.PecaRoupaDAO;
import model.PecaRoupa;

import java.math.BigDecimal;
import java.util.List;

public class PecaRoupaController {

    private PecaRoupaDAO dao;

    public PecaRoupaController() {
        this.dao = new PecaRoupaDAO();
    }

    public boolean adicionarPeca(String nome, String tipo, String tamanho,
                                 String cor, BigDecimal preco,
                                 String descricao, String caminhoImagem) {

        PecaRoupa peca = new PecaRoupa(nome, tipo, tamanho, cor,
                preco, descricao, caminhoImagem);

        return dao.adicionar(peca);
    }

    public List<PecaRoupa> listarPecas() {
        return dao.listar();
    }

    public boolean atualizarPeca(int id, String nome, String tipo, String tamanho,
                                 String cor, BigDecimal preco,
                                 String descricao, String caminhoImagem) {

        PecaRoupa peca = new PecaRoupa(id, nome, tipo, tamanho, cor,
                preco, descricao, caminhoImagem);

        return dao.atualizar(peca);
    }

    public boolean removerPeca(int id) {
        return dao.remover(id);
    }

    public PecaRoupa buscarPorId(int id) {
        return dao.buscarPorId(id);
    }
}
