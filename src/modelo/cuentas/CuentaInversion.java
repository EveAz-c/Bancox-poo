package modelo.cuentas;

import java.time.LocalDate;
import modelo.personas.Cliente;

public class CuentaInversion extends Cuenta {
    private int plazo_dias;
    private double tasa_anual;
    private LocalDate fecha_vencimiento;

    public CuentaInversion() {
    }

    public CuentaInversion(String numeroCuenta, Cliente titular, double saldoInicial, int plazoDias, double tasaAnual) {
        super(numeroCuenta, titular, saldoInicial);
        this.plazo_dias = plazoDias;
        this.tasa_anual = tasaAnual;
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
