package com.mycompany.mvpinclusaoproduto.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.mycompany.mvpinclusaoproduto.model.Produto;
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
        //esse método é responsável por validar as informações também, está correto isso?
        String nome = presenter.getView().getTxtNome().getText();
        if (nome == null || nome.isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }
        double precoCusto = Double.parseDouble(presenter.getView().getTxtPrecoCusto().getText());
        if (precoCusto  <= 0) {
            throw new RuntimeException("Preço de custo deve ser maior que zero");
        }
        double percentualLucro = Double.parseDouble(presenter.getView().getTxtPercentualLucro().getText());
        if (percentualLucro <= 0) {
            throw new RuntimeException("Percentual de lucro deve ser maior que zero");
        }
        Produto produto = new Produto(nome, precoCusto, percentualLucro);

        presenter.getProdutos().inserir(produto);
        presenter.getProdutos().notificarObservadores();

        JOptionPane.showMessageDialog(presenter.getView(), "Produto incluído com sucesso!");

        presenter.setAllBtnVisibleFalse();
        presenter.getView().dispose();
    }

    @Override
    public void cancelar() {
        presenter.setAllBtnVisibleFalse();
        presenter.getView().dispose();
    }
}
