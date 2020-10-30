/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielrigolon.hospital.bean;

import com.gabrielrigolon.hospital.dao.PacientesDAO;
import com.gabrielrigolon.hospital.model.Pacientes;
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
public class PacienteBean implements Serializable{
    
     List pacientes;
     Pacientes paciente;

    public PacienteBean() {
        pacientes = new PacientesDAO().buscarTodas();
        paciente = new Pacientes();
    }
    
     public void salvar(ActionEvent actionEvent) {
        new PacientesDAO().persistir(paciente);
        pacientes = new PacientesDAO().buscarTodas();
        paciente = new Pacientes();
    }

    public List getPacientes() {
        return pacientes;
    }

    public void setPacientes(List pacientes) {
        this.pacientes = pacientes;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    public void gravar(ActionEvent av) {
        msgScreen(PacientesDAO.getInstance().persistir(paciente));
        pacientes = PacientesDAO.getInstance().buscarTodas();
        paciente = new Pacientes();
    }

    public void remover(ActionEvent av) {
        msgScreen(PacientesDAO.getInstance().remover(paciente));
        pacientes = PacientesDAO.getInstance().buscarTodas();
        paciente = new Pacientes();
    }

    public Pacientes buscar(ActionEvent av) {
        return PacientesDAO.getInstance().buscar(paciente);
    }

    public Pacientes buscar(long id) {
        return PacientesDAO.getInstance().buscar(id);
    }

    public List<Pacientes> buscar(String nome) {
        return PacientesDAO.getInstance().buscar(nome);
    }

    public void msgScreen(String msg) {
        if (msg.contains("Não")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

}
    