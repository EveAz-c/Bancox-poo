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
        if(empleado == null){
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }
        if(empleados.contains(empleado)){
            throw new IllegalArgumentException("El empleado ya está asignado a la sucursal");
        } else{
            empleados.add(empleado);
        }
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
