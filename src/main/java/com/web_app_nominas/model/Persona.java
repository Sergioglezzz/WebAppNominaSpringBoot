package com.web_app_nominas.model;

import jakarta.persistence.*;

@MappedSuperclass
public class Persona {

    @Column(name = "nombre", nullable = false)
    protected String nombre;

    @Id
    @Column(name = "dni", unique = true, nullable = false, length = 20)
    protected String dni;

    @Column(name = "sexo", nullable = false, length = 1)
    protected char sexo;

    // Constructor vacío necesario para JPA
    public Persona() {}

    public Persona(String nombre, String dni, char sexo) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
    }

    public Persona(String nombre, char sexo) {
        this.nombre = nombre;
        this.sexo = sexo;
    }

    public void imprime() {
        System.out.println(nombre + ", " + dni);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
}
