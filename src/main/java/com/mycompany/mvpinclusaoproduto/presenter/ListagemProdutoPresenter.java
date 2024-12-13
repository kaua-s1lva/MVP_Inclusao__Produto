package com.mycompany.mvpinclusaoproduto.presenter;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mycompany.mvpinclusaoproduto.db.ProdutoCollection;
import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.observer.IObserver;
import com.mycompany.mvpinclusaoproduto.view.ListagemProdutoView;

public class ListagemProdutoPresenter implements IObserver {
    private ListagemProdutoView view;
    private ProdutoCollection produtos;

    public ListagemProdutoPresenter(ProdutoCollection produtos) {
        this.view = new ListagemProdutoView();
        this.view.setVisible(false);
        this.produtos = produtos;

        configuraView();
        atualizar();
        produtos.adicionarObservador(this);
        view.setVisible(true);
    }

    private void configuraView() {
        this.view.getTableProdutos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                setStatusBotaoRemover(true);
            }
        });

        this.view.getBtnRemover().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    remover();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

    }

    private void remover() {
        int linha = view.getTableProdutos().getSelectedRow();

        int confirmacao = JOptionPane.showConfirmDialog(
            view, 
            "Tem certeza de que deseja remover o produto selecionado?", 
            "Confirmação de Remoção", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            produtos.remover(linha);
            JOptionPane.showMessageDialog(view, "Produto removido com sucesso!", "Remoção", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setStatusBotaoRemover(boolean status) {
        this.view.getBtnRemover().setEnabled(status);
    }

    @Override
    public void atualizar() {
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
}
