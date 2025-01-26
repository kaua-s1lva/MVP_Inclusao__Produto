package com.mycompany.mvpinclusaoproduto.presenter;

import com.mycompany.mvpinclusaoproduto.db.ProdutoCollection;
import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.observer.IObserver;
import com.mycompany.mvpinclusaoproduto.view.PrincipalProdutoView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class PrincipalProdutoPresenter implements IObserver {
    private PrincipalProdutoView view;
    private ProdutoCollection produtos;

    public PrincipalProdutoPresenter() {
        this.view = new PrincipalProdutoView();
        this.view.setVisible(false);
        produtos = new ProdutoCollection();

        configuraView();
        atualizar();
        view.setVisible(true);
        view.getLblTotalItens().setText("0");
    }

    private void configuraView() {
        produtos.adicionarObservador(this);
        this.view.getBtnNovo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManterProdutoPresenter(produtos, -1);
            }
        });

        this.view.getBtnVisualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Produto produto = resgatarProduto();
                int linha = view.getTableProdutos().getSelectedRow();
                new ManterProdutoPresenter(produtos, linha);
            }
        });

        this.view.getTableProdutos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                setStatusBotaoRemover(true);
            }
        });
        
    }

    @Override
    public void atualizar() {
        int quantProdutos = produtos.getTotalProdutos();
        view.getLblTotalItens().setText(Integer.toString(quantProdutos));

        DefaultTableModel dtmProdutos = (DefaultTableModel) view.getTableProdutos().getModel();
        dtmProdutos.setRowCount(0);

        for (int i = 0; i < produtos.getProdutos().size(); i++) {
            Produto produto = produtos.getProdutos().get(i);
            Object[] linha = {  i+1, 
                                produto.getNome(), 
                                produto.getPercentualLucro(), 
                                produto.getPrecoCusto(),
                                (produto.getPercentualLucro()/100 * produto.getPrecoCusto()) + produto.getPrecoCusto()
                            };
            dtmProdutos.addRow(linha);
        }
        setStatusBotaoRemover(false);
    }

    private void setStatusBotaoRemover(boolean status) {
        this.view.getBtnVisualizar().setEnabled(status);
    }
}
