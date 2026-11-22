package modelo.personas;

public class Cajero extends Empleado {
    private String turno;

    public Cajero(String nombre, String apellido, String telefono, String correo, String numeroEmpleado, String turno) {
        super(nombre, apellido, telefono, correo, numeroEmpleado);
        this.turno = turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String obtenerRol() {
        return "Cajero";
    }

}
