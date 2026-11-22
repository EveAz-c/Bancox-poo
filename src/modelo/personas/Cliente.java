package modelo.personas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.cuentas.Cuenta;

public class Cliente extends Persona {
    private String direccion;
    private String curp;
    private LocalDate fecha_nacimiento;
    private List<Cuenta> cuentas = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String telefono, String correo, String direccion, String curp, LocalDate fechaNacimiento) {
        super(nombre, apellido, telefono, correo);
        this.direccion = direccion;
        this.curp = curp;
        this.fecha_nacimiento = fechaNacimiento;
    }

    public boolean esMayorDeEdad() {
        // TODO: implementar
        return false;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String obtenerRol() {
        // TODO: implementar
        return null;
    }

    @Override
    public String toString() {
        return "Cliente{" + "direccion=" + direccion + ", curp=" + curp + ", fecha_nacimiento=" + fecha_nacimiento + "}";
    }

}
