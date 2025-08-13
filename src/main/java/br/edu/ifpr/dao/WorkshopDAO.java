/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.dao;

import br.edu.ifpr.infrastructure.JPAUtil;
import br.edu.ifpr.model.entity.Workshop;
import jakarta.persistence.EntityManager;

/**
 *
 * @author rafae
 */
public class WorkshopDAO extends GenericDAO<Integer, Workshop> {

    public WorkshopDAO() {
        super();
    }

    public boolean existsByName(String name) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                    "select count(w) from Workshop w where lower(w.name) = :n", Long.class)
                    .setParameter("n", name.toLowerCase())
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }

    }
}
