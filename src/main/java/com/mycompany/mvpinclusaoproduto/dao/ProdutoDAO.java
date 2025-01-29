package com.mycompany.mvpinclusaoproduto.dao;

import java.util.List;

import com.mycompany.mvpinclusaoproduto.model.Produto;

public interface ProdutoDAO {
    void inserir(Produto produto);
    Produto buscarPorId(int id);
    List<Produto> listarTodos();
    void atualizar(Produto produto);
    //void atualizar(int id);
    //void remover(Produto produto);
    void remover(int id);
    //int getTotalProdutos();
}
