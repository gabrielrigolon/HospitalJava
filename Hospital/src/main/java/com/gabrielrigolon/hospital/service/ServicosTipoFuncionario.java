/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielrigolon.hospital.service;

import com.gabrielrigolon.hospital.dao.TipoFuncionarioDAO;
import com.gabrielrigolon.hospital.model.TipoFuncionario;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author tassi
 */
@Path("servicosTipoFuncionario")
@RequestScoped
public class ServicosTipoFuncionario {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ServicosTipoFuncionarioResource
     */
    public ServicosTipoFuncionario() {
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void atualizar(TipoFuncionario tf) {
        TipoFuncionarioDAO.getInstance().persistir(tf);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void gravar(TipoFuncionario tf) {
        TipoFuncionarioDAO.getInstance().persistir(tf);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoFuncionario> buscarTodos() {
        return TipoFuncionarioDAO.getInstance().buscarTodas();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TipoFuncionario buscar(TipoFuncionario tf) {
        return TipoFuncionarioDAO.getInstance().buscar(tf.getNomeTipoFuncionario());
    }

    @DELETE
    @Path("{id}")
    public void remover(TipoFuncionario tf) {
        TipoFuncionarioDAO.getInstance().remover(tf);
    }

}
