package com.mycompany.mvpinclusaoproduto.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.presenter.ManterProdutoPresenter;
import com.mycompany.mvpinclusaoproduto.view.ManterProdutoView;

public class EdicaoProdutoState extends ProdutoPresenterState {
    private int linha;

    public EdicaoProdutoState(ManterProdutoPresenter presenter, int linha) {
        super(presenter);
        this.linha = linha;
        configuraView();
    }

    private void configuraView() {
        ManterProdutoView view = presenter.getView();
        view.setTitle("Editar Produto");

        view.getBtnSalvar().setVisible(true);
        view.getBtnCancelar().setVisible(true);

        view.getTxtNome().setEnabled(true);
        view.getTxtPercentualLucro().setEnabled(true);
        view.getTxtPrecoCusto().setEnabled(true);

        Produto produto = presenter.getProdutos().buscarProdutoPorId(linha);

        view.getTxtNome().setText(produto.getNome());
        view.getTxtPercentualLucro().setText(Double.toString(produto.getPercentualLucro()));
        view.getTxtPrecoCusto().setText(Double.toString(produto.getPrecoCusto()));

        view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    salvar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cancelar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

    }

    @Override
    public void salvar() {
        String nome = presenter.getView().getTxtNome().getText();
        double precoCusto = Double.parseDouble(presenter.getView().getTxtPrecoCusto().getText());
        double percentualLucro = Double.parseDouble(presenter.getView().getTxtPercentualLucro().getText());

        Produto produto = new Produto(nome, precoCusto, percentualLucro);
        produto.setId(linha);

        presenter.getProdutos().atualizarProduto(produto);
        //presenter.getProdutos().listarTodos().get(linha).setNome(nome);
        //presenter.getProdutos().listarTodos().get(linha).setPrecoCusto(precoCusto);
        //presenter.getProdutos().listarTodos().get(linha).setPercentualLucro(percentualLucro);
        
        JOptionPane.showMessageDialog(presenter.getView(), "Produto alterado com sucesso!");

        presenter.getProdutos().getProdutoDAO().notificarObservadores();

        presenter.setAllBtnVisibleFalse();
        presenter.setEstado(new VisualizacaoProdutoState(presenter, linha));
    }

    @Override
    public void cancelar() {
        presenter.setAllBtnVisibleFalse();
        presenter.setEstado(new VisualizacaoProdutoState(presenter, linha));
    }
}
