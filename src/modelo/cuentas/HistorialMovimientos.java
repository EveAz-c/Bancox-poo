package modelo.cuentas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.transacciones.Transaccion;

public class HistorialMovimientos {
    private String numero_cuenta;
    private List<Transaccion> movimientos = new ArrayList<>();

    public HistorialMovimientos(String numeroCuenta) {
        this.numero_cuenta = numeroCuenta;
    }

    public void registrar(Transaccion t) {
        // TODO: implementar
    }

    public List<Transaccion> consultarPorFecha(LocalDate fecha) {
        // TODO: implementar
        return new ArrayList<>();
    }

    public List<Transaccion> consultarPorFecha(LocalDate desde, LocalDate hasta) {
        // TODO: implementar
        return new ArrayList<>();
    }

    public List<Transaccion> obtenerMovimientos() {
        // TODO: implementar
        return new ArrayList<>();
    }

}
