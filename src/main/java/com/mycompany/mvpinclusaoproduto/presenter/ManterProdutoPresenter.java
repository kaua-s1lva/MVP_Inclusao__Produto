package com.mycompany.mvpinclusaoproduto.presenter;

import com.mycompany.mvpinclusaoproduto.db.ProdutoCollection;
import com.mycompany.mvpinclusaoproduto.state.InclusaoProdutoState;
import com.mycompany.mvpinclusaoproduto.state.ProdutoPresenterState;
import com.mycompany.mvpinclusaoproduto.state.VisualizacaoProdutoState;
import com.mycompany.mvpinclusaoproduto.view.ManterProdutoView;

public class ManterProdutoPresenter {
    private ManterProdutoView view;
    private ProdutoCollection produtos;
    private ProdutoPresenterState estado;

    public ManterProdutoPresenter(ProdutoCollection produtos, int linha) {
        this.produtos = produtos;
        this.view = new ManterProdutoView();
        this.view.setVisible(false);

        setAllBtnVisibleFalse();

        if (linha == -1) {
            this.estado = new InclusaoProdutoState(this);
        } else {
            this.estado = new VisualizacaoProdutoState(this, linha);
        }

        this.view.setVisible(true);
    }

    public void salvar () throws Exception{
        estado.salvar();
    }

    public void excluir() throws Exception {
        estado.excluir();
    }
   
    public void editar() throws Exception {
        estado.editar();
    }

    public void cancelar() throws Exception {
        estado.cancelar();
    }

    public ManterProdutoView getView() {
        return view;
    }

    public ProdutoCollection getProdutos() {
        return produtos;
    }

    public void setEstado(ProdutoPresenterState estado) {
        this.estado = estado;
    }

    public void setAllBtnVisibleFalse() {
        this.view.getBtnCancelar().setVisible(false);
        this.view.getBtnSalvar().setVisible(false);
        this.view.getBtnEditar().setVisible(false);
        this.view.getBtnExcluir().setVisible(false);
        this.view.getBtnFechar().setVisible(false);
    }

}
