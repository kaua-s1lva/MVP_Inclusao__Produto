package com.mycompany.mvpinclusaoproduto.dao;

import java.util.List;

import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.observer.IObserver;
import com.mycompany.mvpinclusaoproduto.observer.Observavel;

public abstract class ProdutoDAO extends Observavel {
    public abstract void inserir(Produto produto);
    public abstract Produto buscarPorId(int id);
    public abstract List<Produto> listarTodos();
    public abstract void atualizar(Produto produto);
    //void atualizar(int id);
    //void remover(Produto produto);
    public abstract void remover(int id);
    //int getTotalProdutos();

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
