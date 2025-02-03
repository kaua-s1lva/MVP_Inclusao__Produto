package com.mycompany.mvpinclusaoproduto.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.presenter.ManterProdutoPresenter;
import com.mycompany.mvpinclusaoproduto.view.ManterProdutoView;

public class EdicaoProdutoState extends ProdutoPresenterState {
    public EdicaoProdutoState(ManterProdutoPresenter presenter) {
        super(presenter);
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

        //Produto produto = presenter.getProdutos().buscarProdutoPorId(linha);

        view.getTxtNome().setText(presenter.getProduto().getNome());
        view.getTxtPercentualLucro().setText(Double.toString(presenter.getProduto().getPercentualLucro()));
        view.getTxtPrecoCusto().setText(Double.toString(presenter.getProduto().getPrecoCusto()));

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

        Produto produto = presenter.getProduto();
        produto.setNome(nome);
        produto.setPrecoCusto(precoCusto);
        produto.setPercentualLucro(percentualLucro);

        presenter.getProdutos().atualizarProduto(produto);
        
        JOptionPane.showMessageDialog(presenter.getView(), "Produto alterado com sucesso!");

        presenter.getProdutos().getProdutoDAO().notificarObservadores();

        presenter.setAllBtnVisibleFalse();
        presenter.setEstado(new VisualizacaoProdutoState(presenter));
    }

    @Override
    public void cancelar() {
        presenter.setAllBtnVisibleFalse();
        presenter.setEstado(new VisualizacaoProdutoState(presenter));
    }
}
