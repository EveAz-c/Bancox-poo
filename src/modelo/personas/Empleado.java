package modelo.personas;

import java.time.LocalDate;

public abstract class Empleado extends Persona {
    protected String numeroEmpleado;
    protected LocalDate fechaIngreso;

    public Empleado(String nombre, String apellido, String telefono, String correo, String numeroEmpleado) {
        super(nombre, apellido, telefono, correo);
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

}
