/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielrigolon.hospital.dao;

import com.gabrielrigolon.hospital.model.Enderecos;
import com.gabrielrigolon.hospital.util.PersistenceUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;


/**
 *
 * @author gabrielrigolon
 */
public class EnderecosDAO {

    public static EnderecosDAO enderecoDAO;

    public static EnderecosDAO getInstance() {
        if (enderecoDAO == null) {
            enderecoDAO = new EnderecosDAO();
        }
        return enderecoDAO;
    }

    public String persistir(Enderecos endereco) {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            endereco = em.merge(endereco);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Endereco salvo com sucesso!");
            return "Endereco " + endereco.getLogradouro()+ " salvo com sucesso!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível salvar o endereco!", e.getMessage());
            if (e.getMessage().contains("ConstraintViolationException")) {
                return "Não foi possível salvar o endereco " + endereco.getLogradouro() + ", pois o título deve ser único";
            }
            return "Não foi possível salvar o endereco " + endereco.getLogradouro() + "!";
        }
    }

    public String removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("TRUNCATE Endereco");
            query.executeUpdate();
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Todos os enderecos foram deletados!");
            return "Todos os enderecos foram deletados!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível deletar todos os enderecos!", e.getMessage());
            return "Não foi possível deletar todos os enderecos!";
        } finally {
            em.close();
        }
    }

    public String remover(Enderecos endereco) {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            endereco = em.merge(endereco);
            em.remove(endereco);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Endereco removido com sucesso!");
            return "Endereco " + endereco.getLogradouro() + " removido com sucesso!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível remover o endereco!", e.getMessage());
            return "Não foi possível remover o endereco " + endereco.getLogradouro() + ", pois está vinculado a um ou mais exemplares";
        }
    }

    public List<Enderecos> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Endereco l");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados enderecos!", e.getMessage());
            return new ArrayList<>();
        }
    }

    public Enderecos buscar(long id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Endereco l WHERE l.id = :id");
            query.setParameter("id", id);
            Enderecos endereco = (Enderecos) query.getSingleResult();
            if (endereco != null && endereco.getIdEndereco() > 0) {
                return endereco;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados enderecos!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados enderecos!", e.getMessage());
            return null;
        }
    }

    public List<Enderecos> buscar(String titulo) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Endereco l WHERE l.titulo LIKE :titulo");
            query.setParameter("titulo", "%" + titulo + "%");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados enderecos!", e.getMessage());
            return null;
        }
    }

    public Enderecos buscar(Enderecos l) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Endereco l WHERE l.id = :id");
            query.setParameter("id", l.getIdEndereco());
            Enderecos endereco = (Enderecos) query.getSingleResult();
            if (endereco != null && endereco.getIdEndereco() > 0) {
                return endereco;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados enderecos!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados enderecos!", e.getMessage());
            return null;
        }
    }
}
