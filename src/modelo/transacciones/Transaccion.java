package modelo.transacciones;

import java.time.LocalDateTime;
import modelo.banco.Sucursal;
import modelo.cuentas.Cuenta;

public abstract class Transaccion {
    protected String idTransaccion;
    protected double monto;
    protected LocalDateTime fechaHora;
    protected EstadoTransaccion estado;
    private static int contador = 0;
    protected Cuenta cuentaOrigen;
    protected Cuenta cuentaDestino;
    protected Sucursal realizadaEn;
    protected Comprobante comprobante;

    public Transaccion() {
    }

    public Transaccion(double monto, Cuenta cuentaOrigen) {
        this.monto = monto;
        this.cuentaOrigen = cuentaOrigen;
    }

    public abstract void ejecutar();

    public void cancelar() {
        // TODO: implementar
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "Transaccion{" + "idTransaccion=" + idTransaccion + ", monto=" + monto + ", fechaHora=" + fechaHora + ", estado=" + estado + "}";
    }

}
