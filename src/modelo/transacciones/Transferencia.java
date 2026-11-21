package modelo.transacciones;

import modelo.cuentas.Cuenta;

import java.time.LocalDateTime;

public class Transferencia extends Transaccion implements Comisionable {
    private Canal canal;

    public Transferencia(double monto, Cuenta cuentaOrigen, Cuenta cuentaDestino, Canal canal) {
        super(monto, cuentaOrigen);
        this.cuenta_destino = cuentaDestino;
        this.canal = canal;
    }

    @Override
    public void ejecutar() {
        if (cuenta_destino == null || !cuenta_destino.puedeRecibir(monto)) {
            estado = EstadoTransaccion.RECHAZADA;
        } else if (cuenta_origen.retirar(monto + calcularComision())) {
            cuenta_destino.depositar(monto);
            estado = EstadoTransaccion.APLICADA;
        } else {
            estado = EstadoTransaccion.RECHAZADA;
        }
        fecha_hora= LocalDateTime.now();
    }

    @Override
    public double calcularComision() {
        return monto*canal.getPorcentaje();
    }

    @Override
    public String toString() {
        return "Transferencia{" + "canal=" + canal + "}";
    }

}
