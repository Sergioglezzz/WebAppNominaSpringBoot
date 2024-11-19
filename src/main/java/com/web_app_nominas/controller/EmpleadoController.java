package com.web_app_nominas.controller;

import com.web_app_nominas.dao.EmpleadoDAO;
import com.web_app_nominas.exceptions.DatosNoCorrectosException;
import com.web_app_nominas.model.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletResponse;

// import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/empresa")
public class EmpleadoController {

  @Autowired
  private EmpleadoDAO empleadoDAO;

  // obtene la petición y la respuesta
  @GetMapping
  public String handleGetRequests(@RequestParam("opcion") String opcion, Model model) {
    switch (opcion) {
      case "mostrarEmpleados":
        return mostrarEmpleados(model);
      case "consultarSalario":
        return "views/ConsultarSalario";
      case "buscarEmpleados":
        return "views/BuscarEmpleado";
      case "inicio":
        return "redirect:/index";
      default:
        return "views/Error"; // Agrega una vista de error por si la opción no coincide
    }
  }

  @PostMapping
  public String handlePostRequests(@RequestParam("opcion") String opcion,
      HttpServletRequest request,
      Model model) {
    switch (opcion) {
      case "mostrarSalario":
        return mostrarSalario(request, model);
      case "mostrarEmpleadosFiltrados":
        return mostrarEmpleadosFiltrados(request, model);
      case "modificarEmpleado":
        return modificarEmpleado(request, model);
      case "enviarCambios":
        return enviarCambios(request, model);
      default:
        return "views/Error"; // Agrega una vista de error por si la opción no coincide
    }
  }

  private String mostrarEmpleados(Model model) {
    try {
        List<Empleado> empleados = empleadoDAO.obtenerEmpleados();
        model.addAttribute("empleados", empleados);
    } catch (DatosNoCorrectosException e) {
        e.printStackTrace();
    }
    return "views/MostrarEmpleados";
}

private String mostrarEmpleadosFiltrados(HttpServletRequest request, Model model) {
    String dni = request.getParameter("dni");
    String nombre = request.getParameter("nombre");
    String sexo = request.getParameter("sexo");
    Integer categoria = parseIntOrNull(request.getParameter("categoria"));
    Integer antiguedad = parseIntOrNull(request.getParameter("anyos"));

    try {
        List<Empleado> empleados = empleadoDAO.obtenerEmpleadosFiltrados(nombre, dni, sexo, categoria, antiguedad);
        model.addAttribute("empleados", empleados);
    } catch (DatosNoCorrectosException e) {
        e.printStackTrace();
    }
    return "views/MostrarEmpleados";
}

private String mostrarSalario(HttpServletRequest request, Model model) {
    String dni = request.getParameter("dni");
    double salario = empleadoDAO.obtenerSalario(dni);
    model.addAttribute("salario", salario);
    model.addAttribute("dni", dni);
    return "views/MostrarSalario";
}

private String modificarEmpleado(HttpServletRequest request, Model model) {
    String dni = request.getParameter("dni");
    try {
        Empleado empleado = empleadoDAO.obtenerEmpleado(dni);
        model.addAttribute("empleado", empleado);
    } catch (DatosNoCorrectosException e) {
        e.printStackTrace();
    }
    return "views/ModificarEmpleado";
}

private String enviarCambios(HttpServletRequest request, Model model) {
    String dni = request.getParameter("dni");
    String nombre = request.getParameter("nombre");
    String sexo = request.getParameter("sexo");
    Integer categoria = Integer.parseInt(request.getParameter("categoria"));
    Integer antiguedad = Integer.parseInt(request.getParameter("anyos"));

    try {
        if (empleadoDAO.actualizarEmpleado(dni, nombre, sexo, categoria, antiguedad)) {
            return "redirect:/empresa?opcion=mostrarEmpleados";
        } else {
            model.addAttribute("mensaje", "Datos no soportados");
            return "views/Error";
        }
    } catch (DatosNoCorrectosException e) {
        e.printStackTrace();
        return "views/Error";
    }
}

private Integer parseIntOrNull(String str) {
    if (str != null && !str.isEmpty()) {
        return Integer.parseInt(str);
    }
    return null;
}

}
