package modelo.transacciones;

import modelo.cuentas.Cuenta;

public class Retiro extends Transaccion implements Comisionable {
    private String canal;

    public Retiro(double monto, Cuenta cuentaOrigen, String canal) {
        super(monto, cuentaOrigen);
        this.canal = canal;
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
        return "Retiro{" + "canal=" + canal + "}";
    }

}
