/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifpr.model.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author Aluno
 */
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(length = 100, nullable = false)
    private String descrition;

    @Basic
    @Column(nullable = false)
    private int duration_min;

    private List<String> category;

}
