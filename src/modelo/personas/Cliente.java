package modelo.personas;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import modelo.cuentas.Cuenta;

public class Cliente extends Persona {
    private static final int MAYORIA_DE_EDAD = 18;

    private String direccion;
    private String curp;
    private LocalDate fechaNacimiento;
    private List<Cuenta> cuentas = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String telefono, String correo, String direccion, String curp, LocalDate fechaNacimiento) {
        super(nombre, apellido, telefono, correo);
        this.direccion = direccion;
        this.curp = curp;
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Cuenta> getCuentas() {
        return new ArrayList<>(cuentas);
    }

    public void agregarCuenta(Cuenta cuenta) {
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no puede ser nula");
        }
        cuentas.add(cuenta);
    }

    @Override
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public boolean esMayorDeEdad() {
        if (fechaNacimiento == null) {
            return false;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= MAYORIA_DE_EDAD;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCurp() {
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