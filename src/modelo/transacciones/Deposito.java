package modelo.transacciones;

import modelo.cuentas.Cuenta;

import java.time.LocalDateTime;

public class Deposito extends Transaccion implements Comisionable {
    private Canal canal;

    public Deposito() {
    }

    public Deposito(double monto, Cuenta cuentaOrigen, Canal canal) {
        super(monto, cuentaOrigen);
        this.canal = canal;
    }

    @Override
    public void ejecutar() {
        double total = monto-calcularComision();
        if(cuentaOrigen.depositar(total)) {
            estado = EstadoTransaccion.APLICADA;
        } else {
            estado = EstadoTransaccion.RECHAZADA;
        }
        fechaHora = LocalDateTime.now();
    }

    @Override
    public double calcularComision() {
        return monto*canal.getPorcentaje();
    }

    @Override
    public String toString() {
        return "Deposito{" + "medio=" + canal + "}";
    }

}
