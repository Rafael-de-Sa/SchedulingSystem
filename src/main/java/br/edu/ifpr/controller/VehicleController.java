/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.controller;

import br.edu.ifpr.dao.VehicleDAO;
import br.edu.ifpr.model.entity.Vehicle;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 *
 * @author rafae
 */
public class VehicleController {

    private static final Pattern PLATE_MERCOSUL = Pattern.compile("^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$");
    private static final Pattern PLATE_ANTIGA = Pattern.compile("^[A-Z]{3}[0-9]{4}$");
    private static final int MAX_MODEL_LEN = 30;

    private String normalizePlate(String plate) {
        return plate == null ? ""
                : plate.replace("-", "").replace(" ", "").toUpperCase(Locale.ROOT);
    }

    private void validate(String licensePlate, String model) {
        String plate = normalizePlate(licensePlate);
        if (plate.isEmpty()) {
            throw new ValidationException("Informe a placa.");
        }
        if (!(PLATE_MERCOSUL.matcher(plate).matches() || PLATE_ANTIGA.matcher(plate).matches())) {
            throw new ValidationException("Placa inválida. Ex.: ABC1234 (antiga) ou ABC1D23 (Mercosul).");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new ValidationException("Informe o modelo.");
        }
        if (model.trim().length() > MAX_MODEL_LEN) {
            throw new ValidationException("Modelo não pode exceder " + MAX_MODEL_LEN + " caracteres.");
        }
    }

    public Vehicle create(String licensePlate, String model) {
        validate(licensePlate, model);
        String normPlate = normalizePlate(licensePlate);

        VehicleDAO dao = new VehicleDAO();
        try {
            if (dao.existsByLicensePlate(normPlate)) {
                throw new ValidationException("Já existe um veículo cadastrado com essa placa.");
            }
            Vehicle v = new Vehicle();
            v.setLicensePlate(normPlate);
            v.setModel(model.trim());
            dao.create(v);
            return v;
        } finally {
            dao.close();
        }
    }

    public Vehicle update(Integer id, String licensePlate, String model) {
        if (id == null) {
            throw new ValidationException("ID inválido.");
        }
        validate(licensePlate, model);
        String normPlate = normalizePlate(licensePlate);

        VehicleDAO dao = new VehicleDAO();
        try {
            Vehicle v = dao.retrieve(id);
            if (v == null) {
                throw new ValidationException("Veículo não encontrado.");
            }

            boolean mudouPlaca = !normPlate.equalsIgnoreCase(normalizePlate(v.getLicensePlate()));
            if (mudouPlaca && dao.existsByLicensePlate(normPlate)) {
                throw new ValidationException("Já existe um veículo cadastrado com essa placa.");
            }

            v.setLicensePlate(normPlate);
            v.setModel(model.trim());
            dao.update(v);
            return v;
        } finally {
            dao.close();
        }
    }

    public void delete(Integer id) {
        if (id == null) {
            throw new ValidationException("ID inválido.");
        }
        VehicleDAO dao = new VehicleDAO();
        try {
            Vehicle v = dao.retrieve(id);
            if (v == null) {
                throw new ValidationException("Veículo não encontrado.");
            }
            dao.delete(v);
        } finally {
            dao.close();
        }
    }

    public Vehicle findById(Integer id) {
        if (id == null) {
            return null;
        }
        VehicleDAO dao = new VehicleDAO();
        try {
            return dao.retrieve(id);
        } finally {
            dao.close();
        }
    }

    public List<Vehicle> findAll() {
        VehicleDAO dao = new VehicleDAO();
        try {
            return dao.findAll();
        } finally {
            dao.close();
        }
    }
}
