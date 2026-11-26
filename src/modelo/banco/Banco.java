package modelo.banco;

import java.util.ArrayList;
import java.util.List;
import modelo.cuentas.Cuenta;
import modelo.cuentas.EstadoCuenta;
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
        if (clave == null || nombre == null || direccion == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        if (sucursales.stream().anyMatch(s -> s.getClave().equals(clave))) {
            throw new IllegalArgumentException("Ya existe una sucursal con la misma clave");
        }
        Sucursal sucursal = new Sucursal(clave, nombre, direccion);
        sucursales.add(sucursal);
        return sucursal;
    }

    public void registrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        if (cliente.getCurp() == null || cliente.getCurp().isBlank()) {
            throw new IllegalArgumentException("El cliente debe tener CURP");
        }
        if (!cliente.esMayorDeEdad()) {
            throw new IllegalArgumentException("El cliente debe ser mayor de 18 años");
        }
        if (buscarCliente(cliente.getCurp()) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con la misma CURP");
        }
        clientes.add(cliente);
    }

    public Cliente buscarCliente(String curp) {
        if (curp == null) {
            throw new IllegalArgumentException("La CURP no puede ser nula");
        }
        return clientes.stream()
                .filter(c -> curp.equals(c.getCurp()))
                .findFirst()
                .orElse(null);
    }

    public boolean eliminarCliente(String curp) {
        Cliente cliente = buscarCliente(curp);
        if (cliente == null) {
            return false;
        }
        boolean sinCuentasPendientes = cliente.getCuentas().stream()
                .allMatch(c -> c.getEstado() == EstadoCuenta.CERRADA || c.consultarSaldo() == 0);
        if (!sinCuentasPendientes) {
            throw new IllegalStateException("No se puede dar de baja: el cliente tiene cuentas activas o con saldo");
        }
        return clientes.remove(cliente);
    }

    public Cuenta buscarCuenta(String numeroCuenta) {
        if (numeroCuenta == null) {
            throw new IllegalArgumentException("El número de cuenta no puede ser nulo");
        }
        return null;
    }

    public List<Cuenta> buscarCuenta(Cliente titular) {
        if (titular == null) {
            throw new IllegalArgumentException("El titular no puede ser nulo");
        }
        return titular.getCuentas();
    }

    public List<Transaccion> historialPorCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        return new ArrayList<>();
    }
}