/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielrigolon.hospital.dao;

import com.gabrielrigolon.hospital.model.Funcionarios;
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
public class FuncionariosDAO {

    public static FuncionariosDAO funcionarioDAO;

    public static FuncionariosDAO getInstance() {
        if (funcionarioDAO == null) {
            funcionarioDAO = new FuncionariosDAO();
        }
        return funcionarioDAO;
    }

    public String persistir(Funcionarios funcionario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            funcionario = em.merge(funcionario);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Funcionario salvo com sucesso!");
            return "Funcionario " + funcionario.getNomeFuncionario()+ " salvo com sucesso!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível salvar o funcionario!", e.getMessage());
            if (e.getMessage().contains("ConstraintViolationException")) {
                return "Não foi possível salvar o funcionario " + funcionario.getNomeFuncionario() + ", pois o título deve ser único";
            }
            return "Não foi possível salvar o funcionario " + funcionario.getNomeFuncionario() + "!";
        }
    }

    public String removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("TRUNCATE Funcionario");
            query.executeUpdate();
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Todos os funcionarios foram deletados!");
            return "Todos os funcionarios foram deletados!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível deletar todos os funcionarios!", e.getMessage());
            return "Não foi possível deletar todos os funcionarios!";
        } finally {
            em.close();
        }
    }

    public String remover(Funcionarios funcionario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            funcionario = em.merge(funcionario);
            em.remove(funcionario);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Funcionario removido com sucesso!");
            return "Funcionario " + funcionario.getNomeFuncionario() + " removido com sucesso!";
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.flush();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível remover o funcionario!", e.getMessage());
            return "Não foi possível remover o funcionario " + funcionario.getNomeFuncionario() + ", pois está vinculado a um ou mais exemplares";
        }
    }

    public List<Funcionarios> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Funcionario l");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados funcionarios!", e.getMessage());
            return new ArrayList<>();
        }
    }

    public Funcionarios buscar(long id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Funcionario l WHERE l.id = :id");
            query.setParameter("id", id);
            Funcionarios funcionario = (Funcionarios) query.getSingleResult();
            if (funcionario != null && funcionario.getIdFuncionario() > 0) {
                return funcionario;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados funcionarios!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados funcionarios!", e.getMessage());
            return null;
        }
    }

    public List<Funcionarios> buscar(String titulo) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Funcionario l WHERE l.titulo LIKE :titulo");
            query.setParameter("titulo", "%" + titulo + "%");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados funcionarios!", e.getMessage());
            return null;
        }
    }

    public Funcionarios buscar(Funcionarios l) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Funcionario l WHERE l.id = :id");
            query.setParameter("id", l.getIdFuncionario());
            Funcionarios funcionario = (Funcionarios) query.getSingleResult();
            if (funcionario != null && funcionario.getIdFuncionario() > 0) {
                return funcionario;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados funcionarios!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados funcionarios!", e.getMessage());
            return null;
        }
    }
}
