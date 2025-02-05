package com.mycompany.mvpinclusaoproduto.presenter;

import com.mycompany.mvpinclusaoproduto.dao.ProdutoDAOSQLite;
import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.observer.IObserver;
import com.mycompany.mvpinclusaoproduto.repository.ProdutoRepository;
import com.mycompany.mvpinclusaoproduto.view.PrincipalProdutoView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class PrincipalProdutoPresenter implements IObserver {
    private PrincipalProdutoView view;
    private ProdutoRepository repository;

    public PrincipalProdutoPresenter() {
        this.view = new PrincipalProdutoView();
        this.view.setVisible(false);
        repository = new ProdutoRepository(new ProdutoDAOSQLite());

        configuraView();
        atualizar();
        view.setVisible(true);
        view.getLblTotalItens().setText(Integer.toString(repository.listarProdutos().size()));
    }

    private void configuraView() {
        repository.getProdutoDAO().adicionarObservador(this);
        this.view.getBtnNovo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManterProdutoPresenter(repository, null);
            }
        });

        this.view.getBtnVisualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linha = view.getTableProdutos().getSelectedRow();
                int id = (int) view.getTableProdutos().getValueAt(linha, 0);

                Produto produtoSelecionado = repository.buscarProdutoPorId(id);
                new ManterProdutoPresenter(repository, produtoSelecionado);
            }
        });

        this.view.getTableProdutos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                setStatusBotaoVisualizar(true);
            }
        });
        
    }

    @Override
    public void atualizar() {
        int quantProdutos = repository.listarProdutos().size();
        view.getLblTotalItens().setText(Integer.toString(quantProdutos));

        DefaultTableModel dtmProdutos = (DefaultTableModel) view.getTableProdutos().getModel();
        dtmProdutos.setRowCount(0);

        for (int i = 0; i < repository.listarProdutos().size(); i++) {
            Produto produto = repository.listarProdutos().get(i);
            Object[] linha = {  produto.getId(), 
                                produto.getNome(), 
                                produto.getPercentualLucro(), 
                                produto.getPrecoCusto(),
                                (produto.getPercentualLucro()/100 * produto.getPrecoCusto()) + produto.getPrecoCusto()
                            };
            dtmProdutos.addRow(linha);
        }
        setStatusBotaoVisualizar(false);
    }

    private void setStatusBotaoVisualizar(boolean status) {
        this.view.getBtnVisualizar().setEnabled(status);
    }
}
