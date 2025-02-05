package com.mycompany.mvpinclusaoproduto.presenter;

import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.repository.ProdutoRepository;
import com.mycompany.mvpinclusaoproduto.state.InclusaoProdutoState;
import com.mycompany.mvpinclusaoproduto.state.ProdutoPresenterState;
import com.mycompany.mvpinclusaoproduto.state.VisualizacaoProdutoState;
import com.mycompany.mvpinclusaoproduto.view.ManterProdutoView;

public class ManterProdutoPresenter {
    private ManterProdutoView view;
    private ProdutoRepository repository;
    private Produto produtoSelecionado;
    private ProdutoPresenterState estado;

    public ManterProdutoPresenter(ProdutoRepository repository, Produto produtoSelecionado) {
        this.repository = repository;
        this.produtoSelecionado = produtoSelecionado;
        this.view = new ManterProdutoView();
        this.view.setVisible(false);

        setAllBtnVisibleFalse();

        if (produtoSelecionado == null) {
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

    public ProdutoRepository getProdutosRepository() {
        return repository;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
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
