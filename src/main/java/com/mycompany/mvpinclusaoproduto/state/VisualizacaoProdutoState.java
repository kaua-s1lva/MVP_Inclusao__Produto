package com.mycompany.mvpinclusaoproduto.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.mycompany.mvpinclusaoproduto.command.CancelarProdutoCommand;
import com.mycompany.mvpinclusaoproduto.command.ExcluirProdutoCommand;
import com.mycompany.mvpinclusaoproduto.presenter.ManterProdutoPresenter;
import com.mycompany.mvpinclusaoproduto.view.ManterProdutoView;

public class VisualizacaoProdutoState extends ProdutoPresenterState {
    public VisualizacaoProdutoState(ManterProdutoPresenter presenter) {
        super(presenter);
        configuraView();
    }

    private void configuraView() {
        ManterProdutoView view = presenter.getView();
        view.setTitle("Visualizar Produto");
        
        view.getBtnEditar().setVisible(true);
        view.getBtnExcluir().setVisible(true);
        view.getBtnFechar().setVisible(true);

        view.getTxtNome().setEnabled(false);
        view.getTxtPercentualLucro().setEnabled(false);
        view.getTxtPrecoCusto().setEnabled(false);

        view.getTxtNome().setText(presenter.getProdutoSelecionado().getNome());
        view.getTxtPercentualLucro().setText(Double.toString(presenter.getProdutoSelecionado().getPercentualLucro()));
        view.getTxtPrecoCusto().setText(Double.toString(presenter.getProdutoSelecionado().getPrecoCusto()));

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
                    presenter.setEstado(new EdicaoProdutoState(presenter));
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
        new ExcluirProdutoCommand().executar(presenter);
    }

    @Override
    public void cancelar() {
        new CancelarProdutoCommand().executar(presenter);
    }
}
