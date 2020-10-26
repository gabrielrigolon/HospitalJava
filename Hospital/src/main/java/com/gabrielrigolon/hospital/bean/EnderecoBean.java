/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielrigolon.hospital.bean;

import com.gabrielrigolon.hospital.dao.EnderecosDAO;
import com.gabrielrigolon.hospital.model.Enderecos;
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
public class EnderecoBean implements Serializable{
    
    private List enderecos;
    private Enderecos endereco;

    public EnderecoBean() {
        enderecos = new EnderecosDAO().buscarTodas();
        endereco = new Enderecos();
    }
    
     public void salvar(ActionEvent actionEvent) {
        new EnderecosDAO().persistir(endereco);
        enderecos = new EnderecosDAO().buscarTodas();
        endereco = new Enderecos();
    }

    public List getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List enderecos) {
        this.enderecos = enderecos;
    }

    public Enderecos getEndereco() {
        return endereco;
    }

    public void setEndereco(Enderecos endereco) {
        this.endereco = endereco;
    }

    public void gravar(ActionEvent av) {
        msgScreen(EnderecosDAO.getInstance().persistir(endereco));
        enderecos = EnderecosDAO.getInstance().buscarTodas();
        endereco = new Enderecos();
    }

    public void remover(ActionEvent av) {
        msgScreen(EnderecosDAO.getInstance().remover(endereco));
        enderecos = EnderecosDAO.getInstance().buscarTodas();
        endereco = new Enderecos();
    }

    public Enderecos buscar(ActionEvent av) {
        return EnderecosDAO.getInstance().buscar(endereco);
    }

    public Enderecos buscar(long id) {
        return EnderecosDAO.getInstance().buscar(id);
    }

    public List<Enderecos> buscar(String nome) {
        return EnderecosDAO.getInstance().buscar(nome);
    }

    public void msgScreen(String msg) {
        if (msg.contains("Não")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

}
    