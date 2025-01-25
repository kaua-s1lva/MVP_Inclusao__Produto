package com.mycompany.mvpinclusaoproduto.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

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

        view.getTxtNome().setText(presenter.getProdutos().getProdutos().get(linha).getNome());
        view.getTxtPercentualLucro().setText(Double.toString(presenter.getProdutos().getProdutos().get(linha).getPercentualLucro()));
        view.getTxtPrecoCusto().setText(Double.toString(presenter.getProdutos().getProdutos().get(linha).getPrecoCusto()));

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

        presenter.getProdutos().getProdutos().get(linha).setNome(nome);
        presenter.getProdutos().getProdutos().get(linha).setPrecoCusto(precoCusto);
        presenter.getProdutos().getProdutos().get(linha).setPercentualLucro(percentualLucro);
        
        JOptionPane.showMessageDialog(presenter.getView(), "Produto alterado com sucesso!");

        presenter.getProdutos().notificarObservadores();

        presenter.setAllBtnVisibleFalse();
        presenter.setEstado(new VisualizacaoProdutoState(presenter, linha));
    }

    @Override
    public void cancelar() {
        presenter.setAllBtnVisibleFalse();
        presenter.setEstado(new VisualizacaoProdutoState(presenter, linha));
    }
}
