package dao;

import database.Conexao;
import model.ItemCarrinho;
import model.PecaRoupa;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class VendaDAO {

    public int inserirVenda(BigDecimal valorTotal) {
        String sql = "INSERT INTO Vendas (dataVenda, valorTotal) VALUES (NOW(), ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setBigDecimal(1, valorTotal);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // retorna idVenda gerado
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void inserirItensVenda(int idVenda, List<ItemCarrinho> itens) {

        String sql = "INSERT INTO ItensVenda (id_venda, id_produto, quantidade, preco_unit) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (ItemCarrinho item : itens) {

                PecaRoupa peca = item.getPeca();

                stmt.setInt(1, idVenda);                // FK da venda
                stmt.setInt(2, peca.getId());           // FK do produto
                stmt.setInt(3, item.getQuantidade());   // quantidade comprada
                stmt.setBigDecimal(4, peca.getPreco()); // preço unitário no momento da venda

                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir itens da venda: " + e.getMessage(), e);
        }
    }
}
