package com.mycompany.mvpinclusaoproduto.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mycompany.mvpinclusaoproduto.model.Produto;

public class ProdutoCollection {
    private List<Produto> produtos;

    public ProdutoCollection() {
        produtos = new ArrayList<>();
    }

    public void incluir(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Informe um produto válido");
        }
        produtos.add(produto);
    }
    public List<Produto> getProdutos() {
        return produtos;
    }

    public Optional<Produto> findProdutoByNome(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return Optional.of(produto);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "ProdutoCollection{" + "produtos=" + produtos.toString() + '}';
    }
    
    
}