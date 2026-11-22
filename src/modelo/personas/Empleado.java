package modelo.personas;

import java.time.LocalDate;

public abstract class Empleado extends Persona {
    protected String numero_empleado;
    protected LocalDate fecha_ingreso;

    public Empleado(String nombre, String apellido, String telefono, String correo, String numeroEmpleado) {
        super(nombre, apellido, telefono, correo);
        this.numero_empleado = numeroEmpleado;
    }

    public String getNumeroEmpleado() {
        return numero_empleado;
    }

}
