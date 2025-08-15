/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.model.entity;

/**
 *
 * @author rafae
 */
public enum ServiceCategory {
    MECANICA("Mecânica"),
    ELETRICA("Elétrica"),
    TROCA_OLEO("Troca de óleo"),
    REVISAO("Revisão"),
    ALINHAMENTO("Alinhamento"),
    BALANCEAMENTO("Balanceamento");

    private final String label;

    ServiceCategory(String label) {
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
