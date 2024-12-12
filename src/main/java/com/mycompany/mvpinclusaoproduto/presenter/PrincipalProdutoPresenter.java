package com.mycompany.mvpinclusaoproduto.presenter;

import com.mycompany.mvpinclusaoproduto.db.ProdutoCollection;
import com.mycompany.mvpinclusaoproduto.observer.IObserver;
import com.mycompany.mvpinclusaoproduto.view.PrincipalProdutoView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalProdutoPresenter implements IObserver {
    private PrincipalProdutoView view;
    private ProdutoCollection produtos;

    public PrincipalProdutoPresenter() {
        this.view = new PrincipalProdutoView();
        this.view.setVisible(false);
        produtos = new ProdutoCollection();

        configuraView();
        view.setVisible(true);
        view.getLblQuantProdutosCadastrados().setText("0");
    }

    private void configuraView() {
        this.view.getMenuIncluirProduto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InclusaoProdutoPresenter(produtos);
            }
        });

        this.view.getMenuListarProduto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListagemProdutoPresenter(produtos);
            }
        });
        produtos.adicionarObservador(this);
    }

    @Override
    public void atualizar() {
        int quantProdutos = produtos.getProdutos().size();
        view.getLblQuantProdutosCadastrados().setText(Integer.toString(quantProdutos));
    }
}
