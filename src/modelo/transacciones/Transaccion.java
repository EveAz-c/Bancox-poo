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
        this.idTransaccion = "ID-" + (++contador);
        this.monto = monto;
        this.cuentaOrigen = cuentaOrigen;
        this.estado = EstadoTransaccion.PENDIENTE;
        this.fechaHora = LocalDateTime.now();
    }

    public abstract void ejecutar();

    public void cancelar() {
        if (estado==EstadoTransaccion.PENDIENTE) {
            estado=EstadoTransaccion.CANCELADA;
        }
    }

    public double getMonto() {
        return monto;
    }

    public EstadoTransaccion getEstado() {
        return estado;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    @Override
    public String toString() {
        return "Transaccion{" + "idTransaccion=" + idTransaccion + ", monto=" + monto + ", fechaHora=" + fechaHora + ", estado=" + estado + "}";
    }

}
