/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.controller;

import br.edu.ifpr.dao.SchedulingDAO;
import br.edu.ifpr.model.entity.Scheduling;
import br.edu.ifpr.model.entity.Service;
import br.edu.ifpr.model.entity.Vehicle;
import br.edu.ifpr.model.entity.Workshop;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author rafae
 */
public class SchedulingController {

    private static final LocalTime OPEN = LocalTime.of(8, 0);
    private static final LocalTime CLOSE = LocalTime.of(18, 0);

    public Scheduling create(Workshop w, Vehicle v, Service s, LocalDateTime start) {
        validateInputs(w, v, s, start);

        LocalDateTime end = start.plusMinutes(s.getDurationMin());

        SchedulingDAO dao = new SchedulingDAO();
        try {
            long already = dao.countByWorkshopAndDate(w, start.toLocalDate());
            if (already >= w.getVacanciesDay()) {
                throw new ValidationException("Capacidade diária da oficina atingida para essa data.");
            }

            boolean hasOverlap = !dao.findOverlaps(w, start, end).isEmpty();
            if (hasOverlap) {
                throw new ValidationException("Horário conflita com outro agendamento dessa oficina.");
            }

            Scheduling sch = new Scheduling();
            sch.setWorkshop(w);
            sch.setVehicle(v);
            sch.setService(s);
            sch.setStartTime(start);

            dao.create(sch);
            return sch;
        } finally {
            dao.close();
        }
    }

    public Scheduling update(Integer id, Workshop w, Vehicle v, Service s, LocalDateTime start) {
        if (id == null) {
            throw new ValidationException("ID do agendamento inválido.");
        }
        validateInputs(w, v, s, start);

        LocalDateTime end = start.plusMinutes(s.getDurationMin());
        SchedulingDAO dao = new SchedulingDAO();
        try {
            Scheduling current = dao.retrieve(id);
            if (current == null) {
                throw new ValidationException("Agendamento não encontrado.");
            }

            if (blockedLessThan24h(current.getStartTime())) {
                throw new ValidationException("Não é possível reagendar com menos de 24 horas de antecedência.");
            }

            LocalDate newDay = start.toLocalDate();
            long already = dao.countByWorkshopAndDate(w, newDay);

            boolean sameWorkshopAndDay
                    = Objects.equals(current.getWorkshop().getId(), w.getId())
                    && current.getStartTime().toLocalDate().equals(newDay);

            if (sameWorkshopAndDay) {
                already = Math.max(0, already - 1);
            }

            if (already >= w.getVacanciesDay()) {
                throw new ValidationException("Capacidade diária da oficina atingida para essa data.");
            }

            boolean hasOverlap = dao.hasOverlap(w, start, end, current.getId());
            if (hasOverlap) {
                throw new ValidationException("Horário conflita com outro agendamento dessa oficina.");
            }

            current.setWorkshop(w);
            current.setVehicle(v);
            current.setService(s);
            current.setStartTime(start);

            dao.update(current);
            return current;
        } finally {
            dao.close();
        }
    }

    public void delete(Integer id) {
        if (id == null) {
            throw new ValidationException("ID do agendamento inválido.");
        }

        SchedulingDAO dao = new SchedulingDAO();
        try {
            Scheduling current = dao.retrieve(id);
            if (current == null) {
                throw new ValidationException("Agendamento não encontrado.");
            }

            // bloqueio de cancelamento < 24h
            if (blockedLessThan24h(current.getStartTime())) {
                throw new ValidationException("Não é possível cancelar com menos de 24 horas de antecedência.");
            }

            dao.delete(current);
        } finally {
            dao.close();
        }
    }

    private void validateInputs(Workshop w, Vehicle v, Service s, LocalDateTime start) {
        if (w == null) {
            throw new ValidationException("Selecione a oficina.");
        }
        if (v == null) {
            throw new ValidationException("Selecione o veículo.");
        }
        if (s == null) {
            throw new ValidationException("Selecione o serviço.");
        }
        if (start == null) {
            throw new ValidationException("Informe data e hora.");
        }

        if (start.isBefore(LocalDateTime.now())) {
            throw new ValidationException("Não é possível agendar no passado.");
        }

        DayOfWeek dow = start.getDayOfWeek();
        if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
            throw new ValidationException("Agendamento somente em dias úteis (segunda a sexta).");
        }

        Integer dur = s.getDurationMin();
        if (dur == null || dur < 15 || dur > 240) {
            throw new ValidationException("Duração do serviço deve estar entre 15 e 240 minutos.");
        }
        LocalDateTime end = start.plusMinutes(dur);
        if (start.toLocalTime().isBefore(OPEN)) {
            throw new ValidationException("Início antes de 08:00.");
        }
        if (end.toLocalTime().isAfter(CLOSE)) {
            throw new ValidationException("Término precisa ser até 18:00.");
        }
    }

    private boolean blockedLessThan24h(LocalDateTime startTime) {
        return Duration.between(LocalDateTime.now(), startTime).toHours() < 24;
    }

    public Scheduling findById(Integer id) {
        if (id == null) {
            return null;
        }
        SchedulingDAO dao = new SchedulingDAO();
        try {
            return dao.retrieve(id);
        } finally {
            dao.close();
        }
    }

    public List<Scheduling> findAll() {
        SchedulingDAO dao = new SchedulingDAO();
        try {
            return dao.findAll();
        } finally {
            dao.close();
        }
    }
}
