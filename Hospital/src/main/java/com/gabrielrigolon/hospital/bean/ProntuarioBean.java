/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielrigolon.hospital.bean;

import com.gabrielrigolon.hospital.dao.ProntuariosDAO;
import com.gabrielrigolon.hospital.model.Prontuarios;
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
public class ProntuarioBean implements Serializable{
    
     List prontuarios;
     Prontuarios prontuario;

    public ProntuarioBean() {
        prontuarios = new ProntuariosDAO().buscarTodas();
        prontuario = new Prontuarios();
    }
    
     public void salvar(ActionEvent actionEvent) {
        new ProntuariosDAO().persistir(prontuario);
        prontuarios = new ProntuariosDAO().buscarTodas();
        prontuario = new Prontuarios();
    }

    public List getProntuarios() {
        return prontuarios;
    }

    public void setProntuarios(List prontuarios) {
        this.prontuarios = prontuarios;
    }

    public Prontuarios getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuarios prontuario) {
        this.prontuario = prontuario;
    }

    public void gravar(ActionEvent av) {
        msgScreen(ProntuariosDAO.getInstance().persistir(prontuario));
        prontuarios = ProntuariosDAO.getInstance().buscarTodas();
        prontuario = new Prontuarios();
    }

    public void remover(ActionEvent av) {
        msgScreen(ProntuariosDAO.getInstance().remover(prontuario));
        prontuarios = ProntuariosDAO.getInstance().buscarTodas();
        prontuario = new Prontuarios();
    }

    public Prontuarios buscar(ActionEvent av) {
        return ProntuariosDAO.getInstance().buscar(prontuario);
    }

    public Prontuarios buscar(long id) {
        return ProntuariosDAO.getInstance().buscar(id);
    }

    public List<Prontuarios> buscar(String nome) {
        return ProntuariosDAO.getInstance().buscar(nome);
    }

    public void msgScreen(String msg) {
        if (msg.contains("Não")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

}
    