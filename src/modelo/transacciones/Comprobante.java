package modelo.transacciones;

import java.time.LocalDateTime;

public class Comprobante {
    private int folio;
    private LocalDateTime fecha_emision;
    private Transaccion transaccion;
    private static int total_emitidos = 0;
    private double monto_total = 0;

    public Comprobante(Transaccion transaccion) {
        this.folio= ++total_emitidos;
        this.fecha_emision=LocalDateTime.now();
        this.transaccion=transaccion;
        this.monto_total=transaccion.getMonto();
    }

    public String generarTexto() {
        return "COMPROBANTE #" + folio + "\n"
                + "Fecha: " + fecha_emision + "\n"
                +  transaccion+ "\n"
                + "Total: $" + monto_total;
    }

    public int getFolio() {
        return folio;
    }

}
