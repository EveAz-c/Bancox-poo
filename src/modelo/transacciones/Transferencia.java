package modelo.transacciones;

import modelo.cuentas.Cuenta;

import java.time.LocalDateTime;

public class Transferencia extends Transaccion implements Comisionable {
    private Canal canal;

    public Transferencia(double monto, Cuenta cuentaOrigen, Cuenta cuentaDestino, Canal canal) {
        super(monto, cuentaOrigen);
        this.cuentaDestino = cuentaDestino;
        this.canal = canal;
    }

    @Override
    public void ejecutar() {
        if (cuentaDestino == null || !cuentaDestino.puedeRecibir(monto)) {
            estado = EstadoTransaccion.RECHAZADA;
        } else if (cuentaOrigen.retirar(monto + calcularComision())) {
            cuentaDestino.depositar(monto);
            estado = EstadoTransaccion.APLICADA;
        } else {
            estado = EstadoTransaccion.RECHAZADA;
        }
        fechaHora= LocalDateTime.now();
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
