package com.mycompany.mvpinclusaoproduto.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.mycompany.mvpinclusaoproduto.presenter.ManterProdutoPresenter;
import com.mycompany.mvpinclusaoproduto.view.ManterProdutoView;

public class VisualizacaoProdutoState extends ProdutoPresenterState {
    private int linha;

    public VisualizacaoProdutoState(ManterProdutoPresenter presenter, int linha) {
        super(presenter);
        this.linha = linha;
        configuraView();
    }

    private void configuraView() {
        ManterProdutoView view = presenter.getView();
        view.getBtnEditar().setVisible(true);
        view.getBtnExcluir().setVisible(true);
        view.getBtnFechar().setVisible(true);

        view.getTxtNome().setEnabled(false);
        view.getTxtPercentualLucro().setEnabled(false);
        view.getTxtPrecoCusto().setEnabled(false);

        view.getTxtNome().setText(presenter.getProdutos().getProdutos().get(linha).getNome());
        view.getTxtPercentualLucro().setText(Double.toString(presenter.getProdutos().getProdutos().get(linha).getPercentualLucro()));
        view.getTxtPrecoCusto().setText(Double.toString(presenter.getProdutos().getProdutos().get(linha).getPrecoCusto()));

        view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    excluir();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    presenter.setAllBtnVisibleFalse();
                    presenter.setEstado(new EdicaoProdutoState(presenter, linha));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        view.getBtnFechar().addActionListener(new ActionListener() {
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
    public void excluir() {
        int confirmacao = JOptionPane.showConfirmDialog(
            presenter.getView(), 
            "Tem certeza de que deseja remover o produto selecionado?", 
            "Confirmação de Remoção", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            presenter.getProdutos().remover(linha);
            JOptionPane.showMessageDialog(presenter.getView(), "Produto removido com sucesso!", "Remoção", JOptionPane.INFORMATION_MESSAGE);
            presenter.setAllBtnVisibleFalse();
            presenter.getView().dispose();
        }
    }

    @Override
    public void cancelar() {
        presenter.setAllBtnVisibleFalse();
        presenter.getView().dispose();
    }
}
