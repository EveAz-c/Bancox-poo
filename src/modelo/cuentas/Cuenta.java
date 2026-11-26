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
        this.estado = EstadoCuenta.ACTIVA;
        this.fechaApertura = LocalDate.now();
        this.historial = new HistorialMovimientos(numeroCuenta);
        totalCuentas++;
    }

    public EstadoCuenta getEstado() {
        return estado;
    }

    public boolean depositar(double monto) {
        if (estado==EstadoCuenta.ACTIVA && monto>0){
            saldo+=monto;
            return true;
        }
        return false;
    }

    public boolean retirar(double monto) {
        if(!puedeRetirar(monto)){
            return false;
        }
        saldo-=monto;
        return true;
    }

    public double consultarSaldo() {
        if(estado==EstadoCuenta.ACTIVA){
            return saldo;
        }
        return 0;
    }

    public void cambiarEstado(EstadoCuenta nuevo) {
        estado = nuevo;
    }

    public boolean puedeRecibir(double monto) {
        return estado == EstadoCuenta.ACTIVA && monto > 0;
    }

    public abstract double calcularInteres();

    protected abstract boolean puedeRetirar(double monto);

    @Override
    public String toString() {
        return "Cuenta{" + "numeroCuenta=" + numeroCuenta + ", saldo=" + saldo + ", fechaApertura=" + fechaApertura + ", estado=" + estado + "}";
    }

}
