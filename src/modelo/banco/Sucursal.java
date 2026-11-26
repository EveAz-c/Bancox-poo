package modelo.banco;

import java.util.ArrayList;
import java.util.List;
import modelo.personas.Empleado;

public class Sucursal {
    private String clave;
    private String nombre;
    private String direccion;
    private List<Empleado> empleados = new ArrayList<>();

    public Sucursal(String clave, String nombre, String direccion) {
        this.clave = clave;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getClave() {
        return clave;
    }

    public void asignarEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }
        if (empleados.contains(empleado)) {
            throw new IllegalArgumentException("El empleado ya está asignado a la sucursal");
        }
        empleados.add(empleado);
    }

    public boolean removerEmpleado(String numeroEmpleado) {
        if (numeroEmpleado == null) {
            throw new IllegalArgumentException("El número de empleado no puede ser nulo");
        }
        return empleados.removeIf(empleado -> empleado.getNumeroEmpleado().equals(numeroEmpleado));
    }

    public List<Empleado> listarEmpleados() {
        return new ArrayList<>(empleados);
    }
}