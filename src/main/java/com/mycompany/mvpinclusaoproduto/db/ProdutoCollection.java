package com.mycompany.mvpinclusaoproduto.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.observer.IObserver;
import com.mycompany.mvpinclusaoproduto.observer.Observavel;

public class ProdutoCollection extends Observavel {
    private List<Produto> produtos;

    public ProdutoCollection() {
        produtos = new ArrayList<>();
    }

    public void incluir(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Informe um produto válido");
        }
        produtos.add(produto);
        notificarObservadores();
    }

    public void remover(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Informe um produto válido");
        }
        produtos.remove(produto);
        notificarObservadores();
    }

    public void remover(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Selecione um produto");
        }
        produtos.remove(index);
        notificarObservadores();
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

    public int getTotalProdutos() {
        return produtos.size();
    }

    @Override
    public void adicionarObservador(IObserver observer) {
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

    @Override
    public String toString() {
        return "ProdutoCollection{" + "produtos=" + produtos.toString() + '}';
    }
}