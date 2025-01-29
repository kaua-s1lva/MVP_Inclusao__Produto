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
import com.mycompany.mvpinclusaoproduto.observer.IObserver;
import com.mycompany.mvpinclusaoproduto.observer.Observavel;

import io.github.cdimascio.dotenv.Dotenv;

public class ProdutoDAOMySqlite extends Observavel implements ProdutoDAO {
    private Connection conexao;

    public ProdutoDAOMySqlite () {
        try {
            Dotenv dotenv = Dotenv.load();
            String url = "jdbc:sqlite:" + dotenv.get("DB_PATH") + dotenv.get("DB_DATABASE") + ".db";
            conexao = DriverManager.getConnection(url);
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
/* 
    @Override
    public int getTotalProdutos() {
        String sql = "SELECT COUNT (*) AS total FROM produtos";

        try (
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            if(rs.next()) {
                return rs.getInt("total");
            }
            return 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar os produtos: " + e.getMessage());
        }
    }
*/
    @Override
    public void adicionarObservador(IObserver observer) {
        if (this.observers.contains(observer)) {
            throw new RuntimeException("Não é possível adicionar o mesmo observador");
        }
        this.observers.add(observer);
    }

    @Override
    public void removerObservador(IObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notificarObservadores() {
        for (IObserver observer : this.observers) {
            observer.atualizar();
        }
    }
}
