package modelo.transacciones;

import java.time.LocalDateTime;
import modelo.cuentas.Cuenta;

public class Retiro extends Transaccion implements Comisionable {
    private Canal canal;

    public Retiro(double monto, Cuenta cuentaOrigen, Canal canal) {
        super(monto, cuentaOrigen);
        this.canal = canal;
    }

    @Override
    public void ejecutar() {
        double total = monto-calcularComision();
        if (cuenta_origen.retirar(total)) {
            estado = EstadoTransaccion.APLICADA;
        } else {
            estado = EstadoTransaccion.RECHAZADA;
        }
        fecha_hora = LocalDateTime.now();
    }

    @Override
    public double calcularComision() {
        return monto*canal.getPorcentaje();
    }

    @Override
    public String toString() {
        return "Retiro{" + "canal=" + canal + "}";
    }

}
