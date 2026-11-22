package modelo.personas;

public abstract class Persona {
    protected String nombre;
    protected String apellido;
    protected String telefono;
    protected String correo;

    public Persona() {
    }

    public Persona(String nombre, String apellido, String telefono, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido + " ";
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public abstract String obtenerRol();

    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono + ", correo=" + correo + "}";
    }

}
