/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.model.entity;

/**
 *
 * @author rafae
 */
public enum SchedulingStatus {
    AGENDADO("Agendado"),
    CANCELADO("Cancelado"),
    COMPLETO("Completo");

    private final String label;

    SchedulingStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
