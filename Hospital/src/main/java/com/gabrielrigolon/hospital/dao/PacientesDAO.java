/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielrigolon.hospital.dao;

import com.gabrielrigolon.hospital.model.Pacientes;
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
public class PacientesDAO {
    
    public static PacientesDAO pacienteDAO;

    public static PacientesDAO getInstance() {
        if (pacienteDAO == null) {
            pacienteDAO = new PacientesDAO();
        }
        return pacienteDAO;
    }

    public String persistir(Pacientes paciente) {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            paciente = em.merge(paciente);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Paciente salvo com sucesso!");
            return "Paciente " + paciente.getNomePaciente()+ " salvo com sucesso!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível salvar o paciente!", e.getMessage());
            if (e.getMessage().contains("ConstraintViolationException")) {
                return "Não foi possível salvar o paciente " + paciente.getNomePaciente() + ", pois o título deve ser único";
            }
            return "Não foi possível salvar o paciente " + paciente.getNomePaciente() + "!";
        }
    }

    public String removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("TRUNCATE Pacientes");
            query.executeUpdate();
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Todos os pacientes foram deletados!");
            return "Todos os pacientes foram deletados!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível deletar todos os pacientes!", e.getMessage());
            return "Não foi possível deletar todos os pacientes!";
        } finally {
            em.close();
        }
    }

    public String remover(Pacientes paciente) {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            paciente = em.merge(paciente);
            em.remove(paciente);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Paciente removido com sucesso!");
            return "Paciente " + paciente.getNomePaciente() + " removido com sucesso!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível remover o paciente!", e.getMessage());
            return "Não foi possível remover o paciente " + paciente.getNomePaciente() + ", pois está vinculado a um ou mais exemplares";
        }
    }

    public List<Pacientes> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Paciente l");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados pacientes!", e.getMessage());
            return new ArrayList<>();
        }
    }

    public Pacientes buscar(long id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Paciente l WHERE l.id = :id");
            query.setParameter("id", id);
            Pacientes paciente = (Pacientes) query.getSingleResult();
            if (paciente != null && paciente.getIdPaciente()> 0) {
                return paciente;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados pacientes!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados pacientes!", e.getMessage());
            return null;
        }
    }

    public List<Pacientes> buscar(String titulo) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Paciente l WHERE l.titulo LIKE :titulo");
            query.setParameter("titulo", "%" + titulo + "%");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados pacientes!", e.getMessage());
            return null;
        }
    }

    public Pacientes buscar(Pacientes l) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Paciente l WHERE l.id = :id");
            query.setParameter("id", l.getIdPaciente());
            Pacientes paciente = (Pacientes) query.getSingleResult();
            if (paciente != null && paciente.getIdPaciente() > 0) {
                return paciente;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados pacientes!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados pacientes!", e.getMessage());
            return null;
        }
    }
}
