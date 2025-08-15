/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.view.tablemodel;

import br.edu.ifpr.model.entity.Vehicle;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rafael
 */
public class VehicleTableModel extends AbstractTableModel {

    private List<Vehicle> data = new LinkedList<Vehicle>();

    private String[] columns = {"ID", "Placa", "Modelo"};

    public VehicleTableModel() {
    }

    public void setData(List<Vehicle> data) {
        this.data = data;
        this.fireTableDataChanged();
    }

    public Vehicle get(int row) {
        return data.get(row);
    }

    public void add(Vehicle v) {
        this.data.add(v);
        this.fireTableDataChanged();
    }

    public void remove(int row) {
        this.data.remove(row);
        this.fireTableDataChanged();
    }

    public int getRowIndexById(Integer vehicleId) {
        if (vehicleId == null) {
            return -1;
        }
        for (int i = 0; i < data.size(); i++) {
            Integer id = data.get(i).getId();
            if (vehicleId.equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public void updateRow(Vehicle vehicle) {
        int row = getRowIndexById(vehicle.getId());
        this.data.set(row, vehicle);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vehicle vehicle = data.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return vehicle.getId();
            case 1:
                return vehicle.getLicensePlate();
            case 2:
                return vehicle.getModel();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

}
