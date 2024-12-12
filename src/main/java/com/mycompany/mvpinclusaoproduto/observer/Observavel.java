package com.mycompany.mvpinclusaoproduto.observer;

import java.util.List;
import java.util.ArrayList;

public abstract class Observavel {
    List<IObserver> observers;

    public Observavel () {
        this.observers = new ArrayList<>();
    }

    public abstract void adicionarObservador(IObserver observer);
    public abstract void removerObservador(IObserver observer);
    public abstract void notificarObservadores();

    public List<IObserver> getObservers() {
        return observers;
    }
}