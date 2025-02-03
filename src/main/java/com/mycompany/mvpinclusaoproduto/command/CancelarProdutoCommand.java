package com.mycompany.mvpinclusaoproduto.command;

import com.mycompany.mvpinclusaoproduto.presenter.ManterProdutoPresenter;

public class CancelarProdutoCommand implements IProdutoPresenterCommand {
    @Override
    public void executar(ManterProdutoPresenter presenter) {
        presenter.setAllBtnVisibleFalse();
        presenter.getView().dispose();
    }
}
