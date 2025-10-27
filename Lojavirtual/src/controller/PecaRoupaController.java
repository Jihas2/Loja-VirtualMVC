package controller;

import model.PecaRoupa;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PecaRoupaController {
    private List<PecaRoupa> listaPecas;
    private int proximoId;

    public PecaRoupaController() {
        this.listaPecas = new ArrayList<>();
        this.proximoId = 1;
    }

    public boolean adicionarPeca(String nome, String tipo, String tamanho,
                                 String cor, String precoStr, String descricao, String caminhoImagem) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome é obrigatório!");
            }
            if (tipo == null || tipo.trim().isEmpty()) {
                throw new IllegalArgumentException("Tipo é obrigatório!");
            }
            if (tamanho == null || tamanho.trim().isEmpty()) {
                throw new IllegalArgumentException("Tamanho é obrigatório!");
            }

            BigDecimal preco = new BigDecimal(precoStr);
            if (preco.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Preço não pode ser negativo!");
            }

            PecaRoupa peca = new PecaRoupa(nome, tipo, tamanho, cor, preco);
            peca.setId(proximoId++);
            peca.setDescricao(descricao);
            peca.setCaminhoImagem(caminhoImagem);

            listaPecas.add(peca);
            return true;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Preço inválido!");
        }
    }

    public List<PecaRoupa> listarPecas() {
        return new ArrayList<>(listaPecas);
    }

    public boolean removerPeca(int id) {
        return listaPecas.removeIf(peca -> peca.getId() == id);
    }

    public PecaRoupa buscarPorId(int id) {
        return listaPecas.stream()
                .filter(peca -> peca.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean atualizarPeca(int id, String nome, String tipo, String tamanho,
                                 String cor, String precoStr, String descricao, String caminhoImagem) {
        PecaRoupa peca = buscarPorId(id);
        if (peca != null) {
            peca.setNome(nome);
            peca.setTipo(tipo);
            peca.setTamanho(tamanho);
            peca.setCor(cor);
            peca.setPreco(new BigDecimal(precoStr));
            peca.setDescricao(descricao);
            peca.setCaminhoImagem(caminhoImagem);
            return true;
        }
        return false;
    }
}