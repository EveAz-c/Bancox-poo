package modelo.cuentas;

import java.time.LocalDate;
import modelo.personas.Cliente;

public abstract class Cuenta {
    protected String numeroCuenta;
    protected double saldo;
    protected LocalDate fechaApertura;
    protected EstadoCuenta estado;
    private static int totalCuentas = 0;
    protected Cliente titular;
    protected HistorialMovimientos historial;

    public Cuenta() {
    }

    public Cuenta(String numeroCuenta, Cliente titular, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public void depositar(double monto) {
        // TODO: implementar
    }

    public boolean retirar(double monto) {
        // TODO: implementar
        return false;
    }

    public boolean retirar(double monto, String concepto) {
        // TODO: implementar
        return false;
    }

    public double consultarSaldo() {
        // TODO: implementar
        return 0;
    }

    public void cambiarEstado(EstadoCuenta nuevo) {
        // TODO: implementar
    }

    public abstract double calcularInteres();

    protected abstract boolean puedeRetirar(double monto);

    @Override
    public String toString() {
        return "Cuenta{" + "numeroCuenta=" + numeroCuenta + ", saldo=" + saldo + ", fechaApertura=" + fechaApertura + ", estado=" + estado + "}";
    }

}
