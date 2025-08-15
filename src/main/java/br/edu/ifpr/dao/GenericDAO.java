/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.dao;

import br.edu.ifpr.infrastructure.JPAUtil;
import jakarta.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @author rafae
 */
public class GenericDAO<PK, T> {

    private final EntityManager em;

    public GenericDAO() {
        this.em = JPAUtil.getEntityManager();
    }

    protected EntityManager getEm() {
        return em;
    }

    public Class<?> getTypeClass() {
        Class<?> clazz = (Class< ?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[1];
        return clazz;
    }

    public void create(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    public T retrieve(PK pk) {
        T t = (T) em.find(getTypeClass(), pk);
        return t;
    }

    public void update(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    public void delete(T entity) {
        em.getTransaction().begin();
        T managed = em.contains(entity) ? entity : em.merge(entity);
        em.remove(managed);
        em.getTransaction().commit();
    }

    public List<T> findAll() {
        List<T> entities = em.createQuery("FROM " + getTypeClass()
                .getSimpleName())
                .getResultList();
        return entities;
    }

    public void close() {
        try {
            if (em != null && em.isOpen()) {
                em.close();
            }
        } catch (Exception ignore) {
        }
    }
}
