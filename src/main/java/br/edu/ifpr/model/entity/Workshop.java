/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.model.entity;

import jakarta.persistence.*;

/**
 *
 * @author Aluno
 */
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(length = 50, nullable = false)
    private String name;

    @Basic
    @Column(nullable = false)
    private int vacanciesDay;

    public Workshop() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVacanciesDay() {
        return vacanciesDay;
    }

    public void setVacanciesDay(int vacanciesDay) {
        this.vacanciesDay = vacanciesDay;
    }

}
