package com.web_app_nominas.dao;

import com.web_app_nominas.model.Empleado;
import com.web_app_nominas.exceptions.DatosNoCorrectosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmpleadoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper para convertir filas de la base de datos en objetos Empleado
    private RowMapper<Empleado> empleadoRowMapper = new RowMapper<Empleado>() {
        @Override
        public Empleado mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                Character sexo = rs.getString("sexo").charAt(0);
                int categoria = rs.getInt("categoria");
                int anyos = rs.getInt("anyos");
                return new Empleado(nombre, dni, sexo, categoria, anyos);
            } catch (DatosNoCorrectosException e) {
                throw new SQLException("Error al mapear el empleado: " + e.getMessage(), e);
            }
        }
    };

    // Método para obtener lista de empleados
    public List<Empleado> obtenerEmpleados() throws DatosNoCorrectosException {
        String sql = "SELECT * FROM empleados";
        return jdbcTemplate.query(sql, empleadoRowMapper);
    }

    // Método para obtener empleados filtrados
    public List<Empleado> obtenerEmpleadosFiltrados(String dni, String nombre, String sexo, int categoria, int anyos) throws DatosNoCorrectosException {
        StringBuilder sql = new StringBuilder("SELECT dni, nombre, sexo, categoria, anyos FROM empleados WHERE 1=1");
    
        if (dni != null && !dni.isEmpty()) {
            sql.append(" AND dni LIKE ? ");
        }
        if (nombre != null && !nombre.isEmpty()) {
            sql.append(" AND nombre LIKE ? ");
        }
        if (sexo != null && !sexo.isEmpty()) {
            sql.append(" AND sexo = ? ");
        }
        if (categoria >= 0) {
            sql.append(" AND categoria = ? ");
        }
        if (anyos >= 0) {
            sql.append(" AND anyos = ? ");
        }
    
        return jdbcTemplate.query(sql.toString(), preparedStatement -> {
            int index = 1;
            if (dni != null && !dni.isEmpty()) {
                preparedStatement.setString(index++, "%" + dni + "%");
            }
            if (nombre != null && !nombre.isEmpty()) {
                preparedStatement.setString(index++, "%" + nombre + "%");
            }
            if (sexo != null && !sexo.isEmpty()) {
                preparedStatement.setString(index++, sexo);
            }
            if (categoria >= 0) {
                preparedStatement.setInt(index++, categoria);
            }
            if (anyos >= 0) {
                preparedStatement.setInt(index++, anyos);
            }
        }, empleadoRowMapper);
    }
    


    // Método para obtener un empleado por DNI
    @SuppressWarnings("deprecation")
    public Empleado obtenerEmpleado(String dni) throws DatosNoCorrectosException {
        String sql = "SELECT * FROM empleados WHERE dni = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { dni }, empleadoRowMapper);
    }

    // Método para obtener el salario de un empleado por DNI
    @SuppressWarnings("deprecation")
    public double obtenerSalario(String dni) {
        String sql = "SELECT sueldofinal FROM nominas WHERE dni = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { dni }, Double.class);
    }

    // Método para modificar un empleado
    public int modificarEmpleado(String dni, String campo, String valor) {
        String sql = "UPDATE empleados SET " + campo + " = ? WHERE dni = ?";
        return jdbcTemplate.update(sql, valor, dni);
    }

    // Método para actualizar un empleado completo
    public boolean actualizarEmpleado(String dni, String nombre, String sexo, Integer categoria, int anyos)
            throws DatosNoCorrectosException {
        String sql = "UPDATE empleados SET nombre = ?, sexo = ?, categoria = ?, anyos = ? WHERE dni = ?";
        int rowsUpdated = jdbcTemplate.update(sql, nombre, sexo, categoria, anyos, dni);
        return rowsUpdated > 0;
    }
}
