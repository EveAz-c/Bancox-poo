package modelo.cuentas;

import modelo.personas.Cliente;

public class CuentaCorriente extends Cuenta {
    private double limite_sobregiro;
    private double comision_manejo;

    public CuentaCorriente() {
    }

    public CuentaCorriente(String numeroCuenta, Cliente titular, double saldoInicial, double limiteSobregiro) {
        super(numeroCuenta, titular, saldoInicial);
        this.limite_sobregiro = limiteSobregiro;
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

    public double cobrarComisionManejo() {
        // TODO: implementar
        return 0;
    }

}
