package modelo.cuentas;

import modelo.personas.Cliente;

public class CuentaAhorro extends Cuenta {
    private double tasaInteres;
    private int retirosMensuales;
    private static final int LIMITE_RETIROS = 5;

    public CuentaAhorro() {
    }

    public CuentaAhorro(String numeroCuenta, Cliente titular, double saldoInicial, double tasaInteres) {
        super(numeroCuenta, titular, saldoInicial);
        this.tasaInteres = tasaInteres;
    }

    @Override
    public double calcularInteres() {
        // TODO: implementar
        return 0;
    }

    // POLIMORFISMO:
    @Override
    protected boolean puedeRetirar(double monto) {
        return monto <= saldo && retirosMensuales <= LIMITE_RETIROS;
    }

    @Override
    public String toString() {
        return "CuentaAhorro{" + "tasaInteres=" + tasaInteres + ", retirosMensuales=" + retirosMensuales + "}";
    }

    @Override
    public boolean retirar(double monto) {
       if (!super.retirar(monto)) return false;
       retirosMensuales++;
       return true;
    }
}
