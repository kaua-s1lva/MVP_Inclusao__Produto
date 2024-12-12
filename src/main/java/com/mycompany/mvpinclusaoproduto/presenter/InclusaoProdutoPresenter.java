package com.mycompany.mvpinclusaoproduto.presenter;

import javax.swing.JOptionPane;

import com.mycompany.mvpinclusaoproduto.db.ProdutoCollection;
import com.mycompany.mvpinclusaoproduto.model.Produto;
import com.mycompany.mvpinclusaoproduto.view.InclusaoProdutoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InclusaoProdutoPresenter {
    private Produto produto;
    private InclusaoProdutoView view;
    private ProdutoCollection produtos;

    public InclusaoProdutoPresenter(ProdutoCollection produtos) {
        this.produtos = produtos;
        this.view = new InclusaoProdutoView();
        this.view.setVisible(false);

        configuraView();
        view.setVisible(true);
    }

    private void configuraView() {
        this.view.getBtnIncluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    salvar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
   
        this.view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });

    }
   
    private void salvar() throws Exception{
        String nome = view.getTxtNome().getText();
        if (nome == null || nome.isEmpty()) {
            throw new Exception("Nome do produto é obrigatório");
        }
        double precoCusto = Double.parseDouble(view.getTxtPrecoCusto().getText());
        if (precoCusto  <= 0) {
            throw new Exception("Preço de custo deve ser maior que zero");
        }
        double percentualLucro = Double.parseDouble(view.getTxtPercentualLucro().getText());
        if (percentualLucro <= 0) {
            throw new Exception("Percentual de lucro deve ser maior que zero");
        }
        produto = new Produto(nome, precoCusto, percentualLucro);

        produtos.incluir(produto);
        produtos.notificarObservadores();

        JOptionPane.showMessageDialog(view, "Produto incluído com sucesso!");

        System.out.println(produtos.toString());
    }

    private void cancelar() {
        view.dispose();
    }

}
