package com.web_app_nominas.model;

import com.web_app_nominas.exceptions.DatosNoCorrectosException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados")
public class Empleado extends Persona {

    @Column(name = "categoria")
    private int categoria;

    @Column(name = "anyos")
    private int anyos;

    public Empleado() {
        super();
    }

    public Empleado(String nombre, String dni, char sexo, int categoria, int anyos) throws DatosNoCorrectosException {
        super(nombre, dni, sexo);
        setCategoria(categoria);
        setAnyos(anyos);
    }

    public Empleado(String nombre, String dni, char sexo) throws DatosNoCorrectosException {
        super(nombre, dni, sexo);
        this.categoria = 1;
        this.anyos = 0;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) throws DatosNoCorrectosException {
        if (categoria < 1 || categoria > 10) {
            throw new DatosNoCorrectosException("Categoría incorrecta, tiene que ser entre 1 y 10");
        }
        this.categoria = categoria;
    }

    public int getAnyos() {
        return anyos;
    }

    public void setAnyos(int anyos) throws DatosNoCorrectosException {
        if (anyos < 0) {
            throw new DatosNoCorrectosException("Los años no pueden ser negativos");
        }
        this.anyos = anyos;
    }

    public void incrAnyo() {
        this.anyos++;
    }

    // Método para imprimir datos del empleado
    public void imprime() {
        super.imprime();
        System.out.println(super.getSexo() + ", " + categoria + ", " + anyos);
    }
}
