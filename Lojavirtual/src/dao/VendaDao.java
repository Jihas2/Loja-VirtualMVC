package dao;

import database.Conexao;
import model.ItemCarrinho;
import model.PecaRoupa;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class VendaDAO {

    // 1️⃣ Inserir venda e retornar ID
    public int inserirVenda(BigDecimal valorTotal) {
        String sql = "INSERT INTO Vendas (dataVenda, valorTotal) VALUES (NOW(), ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setBigDecimal(1, valorTotal);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // ID da venda gerado
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // erro
    }

    // 2️⃣ Inserir itens da venda
    public void inserirItensVenda(int idVenda, List<ItemCarrinho> itens) {
        String sql = "INSERT INTO ItensVenda (idVenda, idRoupa, quantidade, valorUnitario) VALUES (?, ?, ?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (ItemCarrinho item : itens) {
                PecaRoupa peca = item.getPeca();

                stmt.setInt(1, idVenda);
                stmt.setInt(2, peca.getId());
                stmt.setInt(3, item.getQuantidade());
                stmt.setBigDecimal(4, peca.getPreco());

                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
