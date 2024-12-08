/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mvpinclusaoproduto;

import com.mycompany.mvpinclusaoproduto.db.ProdutoCollection;
import com.mycompany.mvpinclusaoproduto.presenter.InclusaoProdutoPresenter;

/**
 *
 * @author kauac
 */
public class MVPInclusaoProduto {

    public static void main(String[] args) {
        ProdutoCollection produtos = new ProdutoCollection();
        new InclusaoProdutoPresenter(produtos);
    }
}
