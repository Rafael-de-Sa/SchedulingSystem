/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.controller;

import br.edu.ifpr.dao.WorkshopDAO;
import br.edu.ifpr.model.entity.Workshop;
import br.edu.ifpr.view.tablemodel.WorkshopTableModel;
import java.util.List;

/**
 *
 * @author rafae
 */
public class WorkshopController {

    private WorkshopTableModel workshopTableModel;

    public WorkshopController() {
    }

    public WorkshopController(WorkshopTableModel workshopTableModel) {
        this.workshopTableModel = workshopTableModel;
    }

    private void validate(String name, Integer vacancies, Integer editingId) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Informe o nome da oficina.");
        }
        String trimmed = name.trim();
        if (trimmed.length() > 50) {
            throw new ValidationException("Nome da oficina não pode exceder 50 caracteres.");
        }
        if (vacancies == null || vacancies < 1) {
            throw new ValidationException("Vagas/dia deve ser pelo menos 1.");
        }

        WorkshopDAO dao = new WorkshopDAO();
        try {
            boolean exists = dao.existsByName(trimmed);
            if (exists) {
                if (editingId != null) {
                    Workshop current = dao.retrieve(editingId);
                    if (current != null && trimmed.equalsIgnoreCase(current.getName())) {
                        return;
                    }
                }
                throw new ValidationException("Já existe uma oficina com esse nome.");
            }
        } finally {
            dao.close();
        }
    }

    public Workshop create(String name, Integer vacanciesDay) {
        validate(name, vacanciesDay, null);

        Workshop w = new Workshop();
        w.setName(name.trim());
        w.setVacanciesDay(vacanciesDay);

        WorkshopDAO dao = new WorkshopDAO();
        try {
            dao.create(w);
            return w;
        } finally {
            dao.close();
        }
    }

    public Workshop update(Integer id, String name, Integer vacanciesDay) {
        if (id == null) {
            throw new ValidationException("ID inválido para atualização.");
        }
        validate(name, vacanciesDay, id);

        WorkshopDAO dao = new WorkshopDAO();
        try {
            Workshop w = dao.retrieve(id);
            if (w == null) {
                throw new ValidationException("Oficina não encontrada.");
            }

            w.setName(name.trim());
            w.setVacanciesDay(vacanciesDay);
            dao.update(w);
            workshopTableModel.updateRow(w);
            return w;
        } finally {
            dao.close();
        }
    }

    public void delete(Integer row) {
        Workshop workshop = workshopTableModel.get(row);
        WorkshopDAO dao = new WorkshopDAO();

        try {
            if (workshop == null) {
                throw new ValidationException("Oficina não encontrada.");
            }
            dao.delete(workshop);
            workshopTableModel.remove(row);
        } finally {
            dao.close();
        }
    }

    public List<Workshop> findAll() {
        WorkshopDAO dao = new WorkshopDAO();
        try {
            return dao.findAll();
        } finally {
            dao.close();
        }
    }

    public Workshop findById(Integer id) {
        if (id == null) {
            return null;
        }
        WorkshopDAO dao = new WorkshopDAO();
        try {
            return dao.retrieve(id);
        } finally {
            dao.close();
        }
    }

    public Workshop workshopRetrieve(Integer row) {
        return workshopTableModel.get(row);
    }

}
