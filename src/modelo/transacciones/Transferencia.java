package modelo.transacciones;

import modelo.cuentas.Cuenta;

public class Transferencia extends Transaccion implements Comisionable {
    private String referencia;

    public Transferencia(double monto, Cuenta cuentaOrigen, Cuenta cuentaDestino, String referencia) {
        super(monto, cuentaOrigen);
        this.cuentaDestino = cuentaDestino;
        this.referencia = referencia;
    }

    @Override
    public void ejecutar() {
        // TODO: implementar
    }

    @Override
    public double calcularComision() {
        // TODO: implementar
        return 0;
    }

    @Override
    public String toString() {
        return "Transferencia{" + "referencia=" + referencia + "}";
    }

}
