/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielrigolon.hospital.bean;
import com.gabrielrigolon.hospital.dao.TipoFuncionarioDAO;
import com.gabrielrigolon.hospital.model.TipoFuncionario;
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
public class TipoFuncionarioBean implements Serializable {

    private List tipoFuncionarios;
    private TipoFuncionario tipoFuncionario;

    public TipoFuncionarioBean() {
        tipoFuncionarios = new TipoFuncionarioDAO().buscarTodas();
        tipoFuncionario = new TipoFuncionario();
    }
    
     public void salvar(ActionEvent actionEvent) {
        new TipoFuncionarioDAO().persistir(tipoFuncionario);
        tipoFuncionarios = new TipoFuncionarioDAO().buscarTodas();
        tipoFuncionario = new TipoFuncionario();
    }

    public List getTipoFuncionarios() {
        return tipoFuncionarios;
    }

    public void setTipoFuncionarios(List tipoFuncionarios) {
        this.tipoFuncionarios = tipoFuncionarios;
    }

    public TipoFuncionario getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public void gravar(ActionEvent av) {
        msgScreen(TipoFuncionarioDAO.getInstance().persistir(tipoFuncionario));
        tipoFuncionarios = TipoFuncionarioDAO.getInstance().buscarTodas();
        tipoFuncionario = new TipoFuncionario();
    }

    public void remover(ActionEvent av) {
        msgScreen(TipoFuncionarioDAO.getInstance().remover(tipoFuncionario));
        tipoFuncionarios = TipoFuncionarioDAO.getInstance().buscarTodas();
        tipoFuncionario = new TipoFuncionario();
    }

    public TipoFuncionario buscar(ActionEvent av) {
        return TipoFuncionarioDAO.getInstance().buscar(tipoFuncionario);
    }

    public TipoFuncionario buscar(long id) {
        return TipoFuncionarioDAO.getInstance().buscar(id);
    }

    public List<TipoFuncionario> buscar(String nome) {
        return TipoFuncionarioDAO.getInstance().buscar(nome);
    }

    public void msgScreen(String msg) {
        if (msg.contains("Não")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

}