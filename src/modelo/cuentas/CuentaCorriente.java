package modelo.cuentas;

import modelo.personas.Cliente;

public class CuentaCorriente extends Cuenta {
    private double limiteSobregiro;
    private double comisionManejo;

    public CuentaCorriente() {
    }

    public CuentaCorriente(String numeroCuenta, Cliente titular, double saldoInicial, double limiteSobregiro) {
        super(numeroCuenta, titular, saldoInicial);
        this.limiteSobregiro = limiteSobregiro;
    }

    @Override
    public double calcularInteres() {
        // TODO: implementar
        return 0;
    }

    // POLIMORFISMO:
    @Override
    protected boolean puedeRetirar(double monto) {
        return monto >= saldo + limiteSobregiro;
    }

    public double cobrarComisionManejo() {
        // TODO: implementar
        return 0;
    }

}
