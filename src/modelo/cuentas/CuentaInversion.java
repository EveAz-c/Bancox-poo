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

    public boolean haVencido() {
        // TODO: implementar
        return false;
    }

}
