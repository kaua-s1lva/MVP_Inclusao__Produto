package com.mycompany.mvpinclusaoproduto.presenter;

import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.repository.ProdutoRepository;
import com.mycompany.mvpinclusaoproduto.state.InclusaoProdutoState;
import com.mycompany.mvpinclusaoproduto.state.ProdutoPresenterState;
import com.mycompany.mvpinclusaoproduto.state.VisualizacaoProdutoState;
import com.mycompany.mvpinclusaoproduto.view.ManterProdutoView;

public class ManterProdutoPresenter {
    private ManterProdutoView view;
    private ProdutoRepository produtos;
    private Produto produto;
    private ProdutoPresenterState estado;

    public ManterProdutoPresenter(ProdutoRepository produtos, Produto produto) {
        this.produtos = produtos;
        this.produto = produto;
        this.view = new ManterProdutoView();
        this.view.setVisible(false);

        setAllBtnVisibleFalse();

        if (produto == null) {
            this.estado = new InclusaoProdutoState(this);
        } else {
            this.estado = new VisualizacaoProdutoState(this);
        }

        this.view.setVisible(true);
    }

    //os métodos aqui não são utilizados de fato? 
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

    public ProdutoRepository getProdutos() {
        return produtos;
    }

    public Produto getProduto() {
        return produto;
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
