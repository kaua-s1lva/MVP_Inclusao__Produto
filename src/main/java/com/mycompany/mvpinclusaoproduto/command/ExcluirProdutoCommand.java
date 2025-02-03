package com.mycompany.mvpinclusaoproduto.command;

import javax.swing.JOptionPane;

import com.mycompany.mvpinclusaoproduto.presenter.ManterProdutoPresenter;

public class ExcluirProdutoCommand implements IProdutoPresenterCommand {
    @Override
    public void executar(ManterProdutoPresenter presenter) {
        int confirmacao = JOptionPane.showConfirmDialog(
            presenter.getView(), 
            "Tem certeza de que deseja remover o produto selecionado?", 
            "Confirmação de Remoção", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            presenter.getProdutos().removerProduto(presenter.getProduto().getId());
            presenter.getProdutos().getProdutoDAO().notificarObservadores();
            JOptionPane.showMessageDialog(presenter.getView(), "Produto removido com sucesso!", "Remoção", JOptionPane.INFORMATION_MESSAGE);
            presenter.setAllBtnVisibleFalse();
            presenter.getView().dispose();
        }
    }
}
