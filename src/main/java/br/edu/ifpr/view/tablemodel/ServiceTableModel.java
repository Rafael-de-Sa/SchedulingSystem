/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.view.tablemodel;

import br.edu.ifpr.model.entity.Service;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rafael
 */
public class ServiceTableModel extends AbstractTableModel {

    private List<Service> data = new LinkedList<Service>();

    private String[] columns = {"ID", "Descrição", "Duração(mm)", "Categoria"};

    public ServiceTableModel() {
    }

    public List<Service> getData() {
        return data;
    }

    public void setData(List<Service> data) {
        this.data = data;
        this.fireTableDataChanged();
    }

    public Service get(int row) {
        return data.get(row);
    }

    public void add(Service s) {
        this.data.add(s);
        this.fireTableDataChanged();
    }

    public void remove(int row) {
        this.data.remove(row);
        this.fireTableDataChanged();
    }

    public int getRowIndexById(Integer serviceId) {
        if (serviceId == null) {
            return -1;
        }
        for (int i = 0; i < data.size(); i++) {
            Integer id = data.get(i).getId();
            if (serviceId.equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public void updateRow(Service service) {
        int row = getRowIndexById(service.getId());
        this.data.set(row, service);
        this.fireTableRowsUpdated(row, row);
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

        Service service = data.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return service.getId();
            case 1:
                return service.getDescription();
            case 2:
                return service.getDurationMin();
            case 3:
                return service.getCategory().toString();
            default:
                return null;
        }

    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

}
