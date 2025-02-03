package com.mycompany.mvpinclusaoproduto.command;

import com.mycompany.mvpinclusaoproduto.presenter.ManterProdutoPresenter;

public interface IProdutoPresenterCommand {
    void executar(ManterProdutoPresenter presenter);
}
