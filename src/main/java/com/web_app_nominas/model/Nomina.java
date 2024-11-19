package com.web_app_nominas.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "nominas")
public class Nomina {

    // Definir una lista de sueldos base que dependa de la categoría
    @Transient
    private static final int[] SUELDO_BASE = {50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000, 230000};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "empleado_dni", referencedColumnName = "dni")
    private Empleado empleado;

    private double sueldoFinal;

    // Constructor vacío necesario para JPA
    public Nomina() {}

    public Nomina(Empleado empleado) {
        this.empleado = empleado;
        this.sueldoFinal = calcularSueldo(empleado);
    }

    // Método para calcular el sueldo basado en la categoría y años trabajados del empleado
    public double calcularSueldo(Empleado emp) {
        int categoriaSueldo = emp.getCategoria();
        return SUELDO_BASE[categoriaSueldo - 1] + 5000 * emp.getAnyos();
    }

    public double getSueldoFinal() {
        return sueldoFinal;
    }

    public void setSueldoFinal(double sueldoFinal) {
        this.sueldoFinal = sueldoFinal;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nomina nomina = (Nomina) o;
        return Objects.equals(id, nomina.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Nomina{" +
                "id=" + id +
                ", empleado=" + empleado +
                ", sueldoFinal=" + sueldoFinal +
                '}';
    }
}
