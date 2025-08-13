/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.dao;

import br.edu.ifpr.infrastructure.JPAUtil;
import br.edu.ifpr.model.entity.Vehicle;
import jakarta.persistence.EntityManager;

/**
 *
 * @author rafae
 */
public class VehicleDAO extends GenericDAO<Integer, Vehicle> {

    public VehicleDAO() {
        super();
    }

    public boolean existsByLicensePlate(String plate) {
        String norm = plate == null ? "" : plate.replace("-", "").trim().toUpperCase();
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                    "select count(v) from Vehicle v where replace(upper(v.licensePlate), '-', '') = :p",
                    Long.class
            ).setParameter("p", norm).getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
