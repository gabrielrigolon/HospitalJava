/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielrigolon.hospital.dao;

import com.gabrielrigolon.hospital.model.Prontuarios;
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
public class ProntuariosDAO {
    
    public static ProntuariosDAO prontuarioDAO;

    public static ProntuariosDAO getInstance() {
        if (prontuarioDAO == null) {
            prontuarioDAO = new ProntuariosDAO();
        }
        return prontuarioDAO;
    }

    public String persistir(Prontuarios prontuario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            prontuario = em.merge(prontuario);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Prontuario salvo com sucesso!");
            return "Prontuario " + prontuario.getDescricao()+ " salvo com sucesso!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível salvar o prontuario!", e.getMessage());
            if (e.getMessage().contains("ConstraintViolationException")) {
                return "Não foi possível salvar o prontuario " + prontuario.getDescricao()+ ", pois o título deve ser único";
            }
            return "Não foi possível salvar o prontuario " + prontuario.getDescricao()+ "!";
        }
    }

    public String removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("TRUNCATE Prontuarios");
            query.executeUpdate();
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Todos os prontuarios foram deletados!");
            return "Todos os prontuarios foram deletados!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível deletar todos os prontuarios!", e.getMessage());
            return "Não foi possível deletar todos os prontuarios!";
        } finally {
            em.close();
        }
    }

    public String remover(Prontuarios prontuario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            prontuario = em.merge(prontuario);
            em.remove(prontuario);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Prontuario removido com sucesso!");
            return "Prontuario " + prontuario.getDescricao()+ " removido com sucesso!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível remover o prontuario!", e.getMessage());
            return "Não foi possível remover o prontuario " + prontuario.getDescricao()+ ", pois está vinculado a um ou mais exemplares";
        }
    }

    public List<Prontuarios> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Prontuarios l");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados prontuarios!", e.getMessage());
            return new ArrayList<>();
        }
    }

    public Prontuarios buscar(long id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Prontuarios l WHERE l.id = :id");
            query.setParameter("id", id);
            Prontuarios prontuario = (Prontuarios) query.getSingleResult();
            if (prontuario != null && prontuario.getIdProntuario() > 0) {
                return prontuario;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados prontuarios!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados prontuarios!", e.getMessage());
            return null;
        }
    }

    public List<Prontuarios> buscar(String titulo) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Prontuarios l WHERE l.titulo LIKE :titulo");
            query.setParameter("titulo", "%" + titulo + "%");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados prontuarios!", e.getMessage());
            return null;
        }
    }

    public Prontuarios buscar(Prontuarios l) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Prontuarios l WHERE l.id = :id");
            query.setParameter("id", l.getIdProntuario());
            Prontuarios prontuario = (Prontuarios) query.getSingleResult();
            if (prontuario != null && prontuario.getIdProntuario() > 0) {
                return prontuario;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados prontuarios!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados prontuarios!", e.getMessage());
            return null;
        }
    }
}
