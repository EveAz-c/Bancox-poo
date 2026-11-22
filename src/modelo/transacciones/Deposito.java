package modelo.transacciones;

import modelo.cuentas.Cuenta;

public class Deposito extends Transaccion implements Comisionable {
    private String medio;

    public Deposito() {
    }

    public Deposito(double monto, Cuenta cuentaOrigen, String medio) {
        super(monto, cuentaOrigen);
        this.medio = medio;
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
        return "Deposito{" + "medio=" + medio + "}";
    }

}
