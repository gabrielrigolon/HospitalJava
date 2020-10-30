/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielrigolon.hospital.bean;

import com.gabrielrigolon.hospital.dao.TipoFuncionarioDAO;
import com.gabrielrigolon.hospital.model.TipoFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.event.ActionEvent;

import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author tassio
 */
@Named
@ViewScoped
public class TipoFuncionarioBean implements Serializable {

    TipoFuncionario tipoFuncionario = new TipoFuncionario();

    List tipoFuncionarios = new ArrayList();

    public TipoFuncionarioBean() {
        tipoFuncionarios = new TipoFuncionarioDAO().buscarTodas();
        tipoFuncionario = new TipoFuncionario();
    }

    public void salvar(ActionEvent actionEvent) {
        new TipoFuncionarioDAO().persistir(tipoFuncionario);
        tipoFuncionarios = new TipoFuncionarioDAO().buscarTodas();
        tipoFuncionario = new TipoFuncionario();
    }

    public void remover(ActionEvent actionEvent) {
        new TipoFuncionarioDAO().remover(tipoFuncionario);
        tipoFuncionarios = new TipoFuncionarioDAO().buscarTodas();
        tipoFuncionario = new TipoFuncionario();
    }

    public TipoFuncionario getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public List getTipoFuncionarios() {
        return tipoFuncionarios;
    }

    public void setTipoFuncionarios(List tipoFuncionarios) {
        this.tipoFuncionarios = tipoFuncionarios;
    }

}
