package modelo.cuentas;

import modelo.personas.Cliente;

public class CuentaAhorro extends Cuenta {
    private double tasa_interes;
    private int retiros_mensuales;
    private static final int limite_retiros = 5;

    public CuentaAhorro() {
    }

    public CuentaAhorro(String numeroCuenta, Cliente titular, double saldoInicial, double tasaInteres) {
        super(numeroCuenta, titular, saldoInicial);
        this.tasa_interes = tasaInteres;
    }

    @Override
    public double calcularInteres() {
        // TODO: implementar
        return 0;
    }

    @Override
    protected boolean puedeRetirar(double monto) {
        // TODO: implementar
        return false;
    }

    @Override
    public String toString() {
        return "CuentaAhorro{" + "tasa_interes=" + tasa_interes + ", retiros_mensuales=" + retiros_mensuales + "}";
    }

}
