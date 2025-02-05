package com.mycompany.mvpinclusaoproduto.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.mycompany.mvpinclusaoproduto.command.CancelarProdutoCommand;
import com.mycompany.mvpinclusaoproduto.command.SalvarProdutoCommand;
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

        view.getTxtNome().setText(presenter.getProdutoSelecionado().getNome());
        view.getTxtPercentualLucro().setText(Double.toString(presenter.getProdutoSelecionado().getPercentualLucro()));
        view.getTxtPrecoCusto().setText(Double.toString(presenter.getProdutoSelecionado().getPrecoCusto()));

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
        new SalvarProdutoCommand().executar(presenter);
        presenter.setEstado(new VisualizacaoProdutoState(presenter));
    }

    @Override
    public void cancelar() {
        new CancelarProdutoCommand().executar(presenter);
    }
}
