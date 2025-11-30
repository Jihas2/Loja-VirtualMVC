package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Conexao;
import model.PecaRoupa;

public class PecaRoupaDAO {
     // CREATE
    public boolean adicionar(PecaRoupa peca) {
        String sql = "INSERT INTO Roupas (nome, tipo, tamanho, cor, preco, descricao, caminhoImagem, estoque) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new Conexao().conectar();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, peca.getNome());
            stmt.setString(2, peca.getTipo());
            stmt.setString(3, peca.getTamanho());
            stmt.setString(4, peca.getCor());
            stmt.setBigDecimal(5, peca.getPreco());
            stmt.setString(6, peca.getDescricao());
            stmt.setString(7, peca.getCaminhoImagem());
            stmt.setInt(8, peca.getEstoque());
            
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - listar todas as peças
    public List<PecaRoupa> listar() {
        List<PecaRoupa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Roupas";

        try (Connection conn = new Conexao().conectar();

             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PecaRoupa p = new PecaRoupa(
                        rs.getInt("idRoupa"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getString("tamanho"),
                        rs.getString("cor"),
                        rs.getBigDecimal("preco"),
                        rs.getString("descricao"),
                        rs.getString("caminhoImagem"),
                        rs.getInt("estoque")
                );
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // READ - buscar por ID
    public PecaRoupa buscarPorId(int id) {
        String sql = "SELECT * FROM Roupas WHERE idRoupa = ?";

        try (Connection conn = new Conexao().conectar();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new PecaRoupa(
                        rs.getInt("idRoupa"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getString("tamanho"),
                        rs.getString("cor"),
                        rs.getBigDecimal("preco"),
                        rs.getString("descricao"),
                        rs.getString("caminhoImagem"),
                        rs.getInt("estoque")

                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE
    public boolean atualizar(PecaRoupa peca) {
        String sql = "UPDATE Roupas SET nome=?, tipo=?, tamanho=?, cor=?, preco=?, descricao=?, estoque=?, caminhoImagem=? "
                + "WHERE idRoupa=?";

        try (Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, peca.getNome());
            stmt.setString(2, peca.getTipo());
            stmt.setString(3, peca.getTamanho());
            stmt.setString(4, peca.getCor());
            stmt.setBigDecimal(5, peca.getPreco());
            stmt.setString(6, peca.getDescricao());
            stmt.setInt(7, peca.getEstoque());           // CORRETO
            stmt.setString(8, peca.getCaminhoImagem());  // CORRETO
            stmt.setInt(9, peca.getId());                // WHERE

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // DELETE
    public boolean remover(int id) {
        String sql = "DELETE FROM Roupas WHERE idRoupa = ?";

        try (Connection conn = new Conexao().conectar();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void atualizarEstoque(int id, int novoEstoque) {
        String sql = "UPDATE Roupas SET estoque = ? WHERE idRoupa = ?";

        try (Connection conn = new Conexao().conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novoEstoque);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
