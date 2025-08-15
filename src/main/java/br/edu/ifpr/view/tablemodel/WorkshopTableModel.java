/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.view.tablemodel;

import br.edu.ifpr.model.entity.Workshop;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Aluno
 */
public class WorkshopTableModel extends AbstractTableModel {

    private List<Workshop> data = new LinkedList<Workshop>();

    private String[] columns = {"ID", "Nome", "Vagas"};

    public WorkshopTableModel() {
    }

    public List<Workshop> getData() {
        return data;
    }

    public void setData(List<Workshop> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public Workshop get(int row) {
        return data.get(row);
    }

    public void add(Workshop a) {
        this.data.add(a);
        this.fireTableDataChanged();
    }

    public void remove(int row) {
        data.remove(row);
        this.fireTableDataChanged();
    }

    public int getRowIndexById(Integer workshopId) {
        if (workshopId == null) {
            return -1;
        }
        for (int i = 0; i < data.size(); i++) {
            Integer id = data.get(i).getId();
            if (workshopId.equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public void updateRow(Workshop workshop) {
        int row = getRowIndexById(workshop.getId());
        this.data.set(row, workshop);
        fireTableRowsUpdated(row, row);
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
        Workshop workshop = data.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return workshop.getId();
            case 1:
                return workshop.getName();
            case 2:
                return workshop.getVacanciesDay();
            default:
                return null;
        }

    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
