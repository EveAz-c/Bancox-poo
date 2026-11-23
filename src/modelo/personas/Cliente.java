package modelo.personas;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import modelo.cuentas.Cuenta;



public class Cliente extends Persona {
    protected String direccion;
    protected String curp;
    protected LocalDate fechaNacimiento;
    protected List<Cuenta> cuentas = new ArrayList<>();

    static final int MAYORIA_DE_EDAD = 18;

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String telefono, String correo, String direccion, String curp, LocalDate fechaNacimiento) {
        super(nombre, apellido, telefono, correo);
        this.direccion = direccion;
        this.curp = curp;
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String getNombreCompleto() {
        return nombre + " " + apellido + " ";
    }

    public boolean esMayorDeEdad() {
        if (fechaNacimiento == null) {
            return false; // Lanzar el error podría provocar que la compilación se pare, no debe de ser un NPE (NullPointerException)
            // throw new NullPointerException();
        }
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad >= MAYORIA_DE_EDAD) { return true;}
        return false;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCurp(){
        return curp;
    }

    @Override
    public String obtenerRol() {
        return "Cliente";
    }

    @Override
    public String toString() {
        return "Cliente{" + "direccion=" + direccion + ", curp=" + curp + ", fechaNacimiento=" + fechaNacimiento + "}";
    }

}
