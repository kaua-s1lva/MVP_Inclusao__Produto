package com.mycompany.mvpinclusaoproduto.repository;

import java.util.List;

import com.mycompany.mvpinclusaoproduto.dao.ProdutoDAO;
import com.mycompany.mvpinclusaoproduto.model.Produto;

public class ProdutoRepository {
    private ProdutoDAO produtoDAO;

    public ProdutoRepository (ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public void adicionarProduto(Produto produto) {
        produtoDAO.inserir(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoDAO.listarTodos();
    }

    public Produto buscarProdutoPorId(int id) {
        return produtoDAO.buscarPorId(id);
    }

    public void atualizarProduto(Produto produto) {
        produtoDAO.atualizar(produto);
    }

    public void removerProduto(int id) {
        produtoDAO.remover(id);
    }

    public ProdutoDAO getProdutoDAO() {
        return produtoDAO;
    }
}
