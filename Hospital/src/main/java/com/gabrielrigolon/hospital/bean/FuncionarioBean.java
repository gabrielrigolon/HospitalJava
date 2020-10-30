package com.gabrielrigolon.hospital.bean;

import com.gabrielrigolon.hospital.dao.FuncionariosDAO;
import com.gabrielrigolon.hospital.model.Funcionarios;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author gabrielrigolon
 */
        
@Named
@ViewScoped
public class FuncionarioBean implements Serializable{
    
     List funcionarios;
     Funcionarios funcionario;

    public FuncionarioBean() {
        funcionarios = new FuncionariosDAO().buscarTodas();
        funcionario = new Funcionarios();
    }
    
     public void salvar(ActionEvent actionEvent) {
        new FuncionariosDAO().persistir(funcionario);
        funcionarios = new FuncionariosDAO().buscarTodas();
        funcionario = new Funcionarios();
    }

    public List getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Funcionarios getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionarios funcionario) {
        this.funcionario = funcionario;
    }

    public void gravar(ActionEvent av) {
        msgScreen(FuncionariosDAO.getInstance().persistir(funcionario));
        funcionarios = FuncionariosDAO.getInstance().buscarTodas();
        funcionario = new Funcionarios();
    }

    public void remover(ActionEvent av) {
        msgScreen(FuncionariosDAO.getInstance().remover(funcionario));
        funcionarios = FuncionariosDAO.getInstance().buscarTodas();
        funcionario = new Funcionarios();
    }

    public Funcionarios buscar(ActionEvent av) {
        return FuncionariosDAO.getInstance().buscar(funcionario);
    }

    public Funcionarios buscar(long id) {
        return FuncionariosDAO.getInstance().buscar(id);
    }

    public List<Funcionarios> buscar(String nome) {
        return FuncionariosDAO.getInstance().buscar(nome);
    }

    public void msgScreen(String msg) {
        if (msg.contains("Não")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

}
    