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

    public void asignarEmpleado(Empleado empleado) {
        // TODO: implementar
    }

    public boolean removerEmpleado(String numeroEmpleado) {
        // TODO: implementar
        return false;
    }

    public List<Empleado> listarEmpleados() {
        // TODO: implementar
        return new ArrayList<>();
    }

}
