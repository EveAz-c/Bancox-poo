package modelo.transacciones;

import java.time.LocalDateTime;
import modelo.banco.Sucursal;
import modelo.cuentas.Cuenta;

public abstract class Transaccion {
    protected String id_transaccion;
    protected double monto;
    protected LocalDateTime fecha_hora;
    protected EstadoTransaccion estado;
    private static int contador = 0;
    protected Cuenta cuenta_origen;
    protected Cuenta cuenta_destino;
    protected Sucursal realizada_en;
    protected Comprobante comprobante;

    public Transaccion() {
    }

    public Transaccion(double monto, Cuenta cuentaOrigen) {
        this.monto = monto;
        this.cuenta_origen = cuentaOrigen;
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
        return "Transaccion{" + "id_transaccion=" + id_transaccion + ", monto=" + monto + ", fecha_hora=" + fecha_hora + ", estado=" + estado + "}";
    }

}
