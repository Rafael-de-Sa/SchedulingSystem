/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.controller;

import br.edu.ifpr.dao.ServiceDAO;
import br.edu.ifpr.model.entity.Service;
import br.edu.ifpr.model.entity.ServiceCategory;
import java.util.List;

/**
 *
 * @author rafae
 */
public class ServiceController {

    private static final int MIN_DURATION = 1;
    private static final int MAX_DURATION = 240;

    private void validate(String description, Integer durationMin, ServiceCategory category) {
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("Informe a descrição do serviço.");
        }
        if (description.trim().length() > 100) {
            throw new ValidationException("Descrição não pode exceder 100 caracteres.");
        }
        if (durationMin == null || durationMin < MIN_DURATION || durationMin > MAX_DURATION) {
            throw new ValidationException("Duração deve estar entre " + MIN_DURATION + " e " + MAX_DURATION + " minutos.");
        }
        if (category == null) {
            throw new ValidationException("Selecione a categoria.");
        }
    }

    public Service create(String description, Integer durationMin, ServiceCategory category) {
        validate(description, durationMin, category);
        Service s = new Service();
        s.setDescription(description.trim());
        s.setDurationMin(durationMin);
        s.setCategory(category);

        ServiceDAO dao = new ServiceDAO();
        try {
            dao.create(s);
            return s;
        } finally {
            dao.close();
        }
    }

    public Service update(Integer id, String description, Integer durationMin, ServiceCategory category) {
        if (id == null) {
            throw new ValidationException("ID inválido.");
        }
        validate(description, durationMin, category);
        ServiceDAO dao = new ServiceDAO();
        try {
            Service s = dao.retrieve(id);
            if (s == null) {
                throw new ValidationException("Serviço não encontrado.");
            }
            s.setDescription(description.trim());
            s.setDurationMin(durationMin);
            s.setCategory(category);
            dao.update(s);
            return s;
        } finally {
            dao.close();
        }
    }

    public void delete(Integer id) {
        if (id == null) {
            throw new ValidationException("ID inválido.");
        }
        ServiceDAO dao = new ServiceDAO();
        try {
            Service s = dao.retrieve(id);
            if (s == null) {
                throw new ValidationException("Serviço não encontrado.");
            }
            dao.delete(s);
        } finally {
            dao.close();
        }
    }

    public Service findById(Integer id) {
        if (id == null) {
            return null;
        }
        ServiceDAO dao = new ServiceDAO();
        try {
            return dao.retrieve(id);
        } finally {
            dao.close();
        }
    }

    public List<Service> findAll() {
        ServiceDAO dao = new ServiceDAO();
        try {
            return dao.findAll();
        } finally {
            dao.close();
        }
    }
}
