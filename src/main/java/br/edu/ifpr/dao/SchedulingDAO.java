/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.dao;

import br.edu.ifpr.model.entity.Scheduling;
import br.edu.ifpr.model.entity.Workshop;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author rafae
 */
public class SchedulingDAO extends GenericDAO<Integer, Scheduling> {

    public SchedulingDAO() {
        super();
    }

    public long countByWorkshopAndDate(Workshop workshop, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        TypedQuery<Long> q = getEm().createQuery(
                "select count(s) from Scheduling s "
                + " where s.workshop = :w and s.startTime >= :start and s.startTime < :end",
                Long.class
        );
        q.setParameter("w", workshop);
        q.setParameter("start", start);
        q.setParameter("end", end);
        return q.getSingleResult();
    }

    public List<Scheduling> findOverlaps(Workshop workshop, LocalDateTime start, LocalDateTime end) {
        TypedQuery<Scheduling> q = getEm().createQuery(
                "select s from Scheduling s "
                + " where s.workshop = :w and s.startTime < :end and s.endTime > :start",
                Scheduling.class
        );
        q.setParameter("w", workshop);
        q.setParameter("start", start);
        q.setParameter("end", end);
        return q.getResultList();
    }

    public List<Scheduling> findOverlapsIgnoringId(Workshop workshop, LocalDateTime start, LocalDateTime end, Integer ignoreId) {
        String jpql = "select s from Scheduling s "
                + " where s.workshop = :w and s.startTime < :end and s.endTime > :start";
        if (ignoreId != null) {
            jpql += " and s.id <> :id";
        }
        TypedQuery<Scheduling> q = getEm().createQuery(jpql, Scheduling.class);
        q.setParameter("w", workshop);
        q.setParameter("start", start);
        q.setParameter("end", end);
        if (ignoreId != null) {
            q.setParameter("id", ignoreId);
        }
        return q.getResultList();
    }

    public List<Scheduling> findByWorkshopAndDate(Workshop workshop, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        TypedQuery<Scheduling> q = getEm().createQuery(
                "select s from Scheduling s "
                + " where s.workshop = :w and s.startTime >= :start and s.startTime < :end"
                + " order by s.startTime",
                Scheduling.class
        );
        q.setParameter("w", workshop);
        q.setParameter("start", start);
        q.setParameter("end", end);
        return q.getResultList();
    }

    public boolean hasOverlap(Workshop workshop, LocalDateTime start, LocalDateTime end, Integer ignoreId) {
        String jpql = "select count(s) from Scheduling s "
                + " where s.workshop = :w and s.startTime < :end and s.endTime > :start";
        if (ignoreId != null) {
            jpql += " and s.id <> :id";
        }
        TypedQuery<Long> q = getEm().createQuery(jpql, Long.class);
        q.setParameter("w", workshop);
        q.setParameter("start", start);
        q.setParameter("end", end);
        if (ignoreId != null) {
            q.setParameter("id", ignoreId);
        }
        return q.getSingleResult() > 0;
    }

}
