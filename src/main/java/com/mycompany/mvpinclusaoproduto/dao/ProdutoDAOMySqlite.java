package com.mycompany.mvpinclusaoproduto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.mvpinclusaoproduto.model.Produto;

import io.github.cdimascio.dotenv.Dotenv;

public class ProdutoDAOMySqlite extends ProdutoDAO {
    private Connection conexao;

    public ProdutoDAOMySqlite () {
        try {
            Dotenv dotenv = Dotenv.load();
            String url = "jdbc:sqlite:" + dotenv.get("DB_PATH") + dotenv.get("DB_DATABASE") + ".db";
            conexao = DriverManager.getConnection(url);
            criarTabela();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, precoCusto, percentualLucro) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoCusto());
            stmt.setDouble(3, produto.getPercentualLucro());
            stmt.executeUpdate();

        } catch(SQLException e) {
            throw new RuntimeException("Erro ao inserir produto: " + e.getMessage());
        }
    }

    @Override
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto(
                                    rs.getString("nome"), 
                                    rs.getDouble("precoCusto"), 
                                    rs.getDouble("percentualLucro")
                    );
                    return produto;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage());
        }
    }

    @Override
    public List<Produto> listarTodos() {
        String sql = "SELECT * FROM produtos";

        List<Produto> produtos = new ArrayList<>();
        try (
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Produto produto = new Produto(
                                        rs.getString("nome"), 
                                        rs.getDouble("precoCusto"), 
                                        rs.getDouble("percentualLucro")
                );
                produto.setId(rs.getInt("id"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os produtos: " + e.getMessage());
        }

        return produtos;
    }

    @Override
    public void atualizar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, precoCusto = ?, percentualLucro = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoCusto());
            stmt.setDouble(3, produto.getPercentualLucro());
            stmt.setInt(4, produto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o produto: " + e.getMessage());
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover o produto: " + e.getMessage());
        }
    }

    private void criarTabela() {
        String sql = """
            CREATE TABLE IF NOT EXISTS produtos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome VARCHAR(999) NOT NULL,
                precoCusto DECIMAL(5,2),
                percentualLucro DECIMAL(5,2)
            );
        """;

        try (Statement stmt = conexao.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Erro na criação de tabela no banco de dados: " + e.getMessage(), e);
        }
    }

}
