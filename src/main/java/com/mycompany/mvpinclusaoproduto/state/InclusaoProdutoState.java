package com.mycompany.mvpinclusaoproduto.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.mycompany.mvpinclusaoproduto.command.CancelarProdutoCommand;
import com.mycompany.mvpinclusaoproduto.command.SalvarProdutoCommand;
import com.mycompany.mvpinclusaoproduto.presenter.ManterProdutoPresenter;

public class InclusaoProdutoState extends ProdutoPresenterState {
    public InclusaoProdutoState(ManterProdutoPresenter presenter) {
        super(presenter);
        configuraView();
    }

    private void configuraView() {
        presenter.getView().setTitle("Incluir Produto");
        presenter.getView().getBtnCancelar().setVisible(true);
        presenter.getView().getBtnSalvar().setVisible(true);

        presenter.getView().getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    salvar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        presenter.getView().getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });
    }

    @Override
    public void salvar() {
        new SalvarProdutoCommand().executar(presenter);
    }

    @Override
    public void cancelar() {
        new CancelarProdutoCommand().executar(presenter);
    }
}
