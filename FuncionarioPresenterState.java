public class FuncionarioPresenter {
    private Funcionario funcionario;
    private FuncionarioRepository repository;
    private FuncionarioPresenterState estado;
    // ...

    public void salvar() {
        estado.salvar();
    }

    public void excluir() {
        estado.excluir();
    }
   
    public void editar() {
        estado.editar();
    }

    public void cancelar() {
        estado.cancelar();
    }

    public FuncionarioPresenter(Funcionario funcionario) {
//        if (funcionarioOpcional.isPresent())
        if (funcionario == null) {
            this.estado = new InclusaoState(this);            
        } else {
            this.funcionario = funcionario;
            this.estado = new VisualizacaoState(this);            
        }
        // ...
    }

    void setState(FuncionarioPresenterState estado) {
        this.estado = estado;
    }

}

public abstract class FuncionarioPresenterState {

    protected FuncionarioPresenter presenter;

    public FuncionarioPresenterState(FuncionarioPresenter presenter) {
        this.presenter = presenter;
        removeListeners();
    }

    public void salvar() {
        throw new RuntimeException("Não é possível salvar a partir do estado " + toString());
    }

    public void excluir() {
        throw new RuntimeException("Não é possível excluir a partir do estado " + toString());
    }

    public void editar() {
        throw new RuntimeException("Não é possível editar a partir do estado " + toString());
    }

    public void cancelar() {
        throw new RuntimeException("Não é possível cancelar a partir do estado " + toString());
    }
   
    private removeListeners() {

        ActionListeners listeners =  presenter.getView().getBtnSalvar().getActionListeners();
        for(ActionListeners listener: listeners) {
            presenter.getView().getBtnSalvar().removeActionListener(listener);            
        }
    }
}


public class InclusaoState extends FuncionarioPresenterState {

    public InclusaoState(FuncionarioPresenter presenter) {
        super(presenter) ;        
        presenter.getView().getBtnSalvar.setEnable(true);
        presenter.getView().getBtnSalvar.addActionListener(new addActionListener() {
            @Override
            public actionPerformed(Event event) {
                salvar();
            }
        });
        presenter.getView().getBtnCancelar.setEnable(true);
        presenter.getView().getBtnCancelar.addActionListener(new addActionListener() {
            @Override
            public actionPerformed(Event event) {
                cancelar();
            }
        });
        presenter.getView().getTxtNome().setEnable(true);
        presenter.getView().getTxtNome().setText("");
        presenter.getView().getCmbCargo().setEnable(true);        
        presenter.getView().getTxtSalarioBase().setEnable(true);
       
    }

    @Override
     public void salvar() {
        String nome = presenter.getView().getTxtNome().getText();
        String cargo = presenter.getView().getCmbCargo().getText();        
        Double getTxtSalarioBase = Double.parse(presenter.getView().getCmbCargo().getText());
        Funcionario novoFuncionario = new Funcionario(nome, cargo, getTxtSalarioBase);
        presenter.getRepository().salvar(novoFuncionario);
        presenter.setFuncionario(novoFuncionario);
        presenter.setState(new VisualizacaoState(presenter));        
    }

    @Override
    public void cancelar() {
        presenter.getView().dispose();
    }

    @Override
    public String toString() {
        return "Inclusão";
     }

}


public class EdicaoState extends FuncionarioPresenterState {

    public EdicaoState(FuncionarioPresenter presenter) {
        super(presenter) ;        
        presenter.getView().getBtnSalvar.setEnable(true);
        presenter.getView().getBtnSalvar.addActionListener(new addActionListener() {
            @Override
            public actionPerformed(Event event) {
                salvar();
            }
        });
        presenter.getView().getBtnCancelar.setEnable(true);
        presenter.getView().getBtnCancelar.addActionListener(new addActionListener() {
            @Override
            public actionPerformed(Event event) {
                cancelar();
            }
        });
        presenter.getView().getTxtNome().setEnable(true);
        presenter.getView().getTxtNome().setText(presenter.getFuncionario().getNome());
        presenter.getView().getCmbCargo().setEnable(true);        
       
        presenter.getView().getTxtSalarioBase().setEnable(true);

    }

    @Override
     public void salvar() {
        //Comportamento da inclusao;      
    }

    @Override
    public void cancelar() {
        //Comportamento da inclusao;              
    }

    @Override
    public String toString() {
        return "Edição";
     }

}

public class VisualizacaoState extends FuncionarioPresenterState {

    public VisualizacaoState(FuncionarioPresenter presenter) {
        super(presenter) ;
    }

    @Override
     public void excluir() {
     // Completar
     int opcao = {sim,nao};
     if (opcao == "sim") {
        presenter.getRepository.excluir(presenter.getFuncionario().getId());
        JOptionPane.showMessageDialog(presente.getView(), "Registro excluído", "Funcionário " + funcionario.getNome() + " excluído com sucesso");
        fechar();
     } else if (opcao == "nao") {
        presenter.setState(new VisualizacaoState(presenter));
     }
             
    }

    @Override
    public void editar() {
        presenter.setState(new EdicaoState(presenter));
    }

    @Override
    public void fechar() {
        presente.getView().dispose();          
    }


    @Override
    public String toString() {
        return "Visualização";
     }

}