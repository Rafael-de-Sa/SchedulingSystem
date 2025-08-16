/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.view.tablemodel;

import br.edu.ifpr.model.entity.Scheduling;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rafael
 */
public class SchedulingTableModel extends AbstractTableModel {

    private List<Scheduling> data = new LinkedList<Scheduling>();

    private String[] columns = {"ID", "Data/Hora", "Status", "Vaículo", "Serviço", "Oficina"};

    public SchedulingTableModel() {
    }

    public List<Scheduling> getData() {
        return data;
    }

    public void setData(List<Scheduling> data) {
        this.data = data;
        this.fireTableDataChanged();
    }

    public Scheduling get(int row) {
        return data.get(row);
    }

    public void add(Scheduling s) {
        this.data.add(s);
        this.fireTableDataChanged();
    }

    public void remove(int row) {
        this.data.remove(row);
        this.fireTableDataChanged();
    }

    public int getRowIndexById(Integer schedulingId) {
        if (schedulingId == null) {
            return -1;
        }
        for (int i = 0; i < data.size(); i++) {
            Integer id = data.get(i).getId();
            if (schedulingId.equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public void updateRow(Scheduling scheduling) {
        int row = getRowIndexById(scheduling.getId());
        this.data.set(row, scheduling);
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

        Scheduling scheduling = data.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return scheduling.getId();
            case 1:
                return scheduling.getStartTime();
            case 2:
                return scheduling.getStatus().toString();
            case 3:
                return scheduling.getVehicle().getLicensePlate();
            case 4:
                return scheduling.getService().getDescription();
            case 5:
                return scheduling.getWorkshop().getName();
            default:
                return null;
        }

    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

}
