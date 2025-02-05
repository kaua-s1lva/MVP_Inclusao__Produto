package com.mycompany.mvpinclusaoproduto.command;

import javax.swing.JOptionPane;

import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.presenter.ManterProdutoPresenter;
import com.mycompany.mvpinclusaoproduto.state.VisualizacaoProdutoState;

public class SalvarProdutoCommand implements IProdutoPresenterCommand {
    @Override
    public void executar(ManterProdutoPresenter presenter) {
        String nome = presenter.getView().getTxtNome().getText();
        double precoCusto = Double.parseDouble(presenter.getView().getTxtPrecoCusto().getText());
        double percentualLucro = Double.parseDouble(presenter.getView().getTxtPercentualLucro().getText());

        if(presenter.getProdutoSelecionado() == null) {
            Produto produto = new Produto(0, nome, precoCusto, percentualLucro);

            presenter.getProdutosRepository().adicionarProduto(produto);
            presenter.getProdutosRepository().getProdutoDAO().notificarObservadores();
    
            JOptionPane.showMessageDialog(presenter.getView(), "Produto incluído com sucesso!");

            presenter.getView().dispose();
        } else {
            Produto produto = presenter.getProdutoSelecionado();
            produto.setNome(nome);
            produto.setPrecoCusto(precoCusto);
            produto.setPercentualLucro(percentualLucro);

            presenter.getProdutosRepository().atualizarProduto(produto);
            
            JOptionPane.showMessageDialog(presenter.getView(), "Produto alterado com sucesso!");

            presenter.getProdutosRepository().getProdutoDAO().notificarObservadores();

            presenter.setEstado(new VisualizacaoProdutoState(presenter));
        }

        presenter.setAllBtnVisibleFalse();
        
    }
}
