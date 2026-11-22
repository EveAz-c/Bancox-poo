package modelo.personas;

public class EjecutivoBancario extends Empleado {
    private String area;
    private double limiteAutorizacion;

    public EjecutivoBancario(String nombre, String apellido, String telefono, String correo, String numeroEmpleado, String area, double limiteAutorizacion) {
        super(nombre, apellido, telefono, correo, numeroEmpleado);
        this.area = area;
        this.limiteAutorizacion = limiteAutorizacion;
    }

    public boolean autorizarApertura(Cliente cliente) {
        if (cliente != null && cliente.esMayorDeEdad() && cliente.getCurp() != null){
            return true;
        }
        return false;
    }

    @Override
    public String obtenerRol() {
        return "Ejecutivo bancario";
    }

}
