/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author rafae
 */
public class JPAUtil {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("scheduling_db");

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }
}
