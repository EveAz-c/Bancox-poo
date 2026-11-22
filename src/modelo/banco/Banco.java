package modelo.banco;

import java.util.ArrayList;
import java.util.List;
import modelo.cuentas.Cuenta;
import modelo.personas.Cliente;
import modelo.transacciones.Transaccion;

public class Banco {
    private String nombre;
    private String rfc;
    private List<Sucursal> sucursales = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public Banco(String nombre, String rfc) {
        this.nombre = nombre;
        this.rfc = rfc;
    }

    public Sucursal crearSucursal(String clave, String nombre, String direccion) {
        // TODO: implementar
        return null;
    }

    public void registrarCliente(Cliente cliente) {
        // TODO: implementar
    }

    public Cliente buscarCliente(String curp) {
        // TODO: implementar
        return null;
    }

    public boolean eliminarCliente(String curp) {
        // TODO: implementar
        return false;
    }

    public Cuenta buscarCuenta(String numeroCuenta) {
        // TODO: implementar
        return null;
    }

    public List<Cuenta> buscarCuenta(Cliente titular) {
        // TODO: implementar
        return new ArrayList<>();
    }

    public List<Transaccion> historialPorCliente(Cliente cliente) {
        // TODO: implementar
        return new ArrayList<>();
    }

}
