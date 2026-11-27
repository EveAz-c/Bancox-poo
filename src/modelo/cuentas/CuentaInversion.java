package modelo.cuentas;

import java.time.LocalDate;
import modelo.personas.Cliente;

public class CuentaInversion extends Cuenta {
    private int plazoDias;
    private double tasaAnual;
    private LocalDate fechaVencimiento;

    public CuentaInversion() {
    }

    public CuentaInversion(String numeroCuenta, Cliente titular, double saldoInicial, int plazoDias, double tasaAnual) {
        super(numeroCuenta, titular, saldoInicial);
        this.plazoDias = plazoDias;
        this.tasaAnual = tasaAnual;
        this.fechaVencimiento = fechaApertura.plusDays(plazoDias);
    }

    @Override
    public double calcularInteres() {
        // TODO: implementar
        return 0;
    }

    @Override
    protected boolean puedeRetirar(double monto) {
        return haVencido() && monto <= saldo;
    }

    public boolean haVencido() {
        return LocalDate.now().isAfter(fechaVencimiento) || LocalDate.now().isEqual(fechaVencimiento);
    }

}
