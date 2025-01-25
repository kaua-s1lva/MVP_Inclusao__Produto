package com.mycompany.mvpinclusaoproduto.model;

public class Produto {
    private String nome;
    private double precoCusto;
    private double percentualLucro;
    private double precoVenda;

    public Produto(String nome, double precoCusto, double percentualLucro) {
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.percentualLucro = percentualLucro;
        this.precoVenda = calcularPrecoVenda();
    }

    public double calcularPrecoVenda() {
        return precoCusto + (precoCusto * percentualLucro / 100);
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }
        this.nome = nome;
    }

    public void setPrecoCusto(double precoCusto) {
        if (precoCusto <= 0) {
            throw new RuntimeException("Preço de custo deve ser maior que zero");
        }
        this.precoCusto = precoCusto;
    }

    public void setPercentualLucro(double percentualLucro) {
        if (percentualLucro <= 0) {
            throw new RuntimeException("Percentual de lucro deve ser maior que zero");
        }
        this.percentualLucro = percentualLucro;
    }

    @Override
    public String toString() {
        return "Produto{" + "nome=" + nome + ", precoCusto=" + precoCusto + ", percentualLucro=" + percentualLucro + ", precoVenda=" + precoVenda + '}';
    }

    
    
}
