package modelo.transacciones;

import java.time.LocalDateTime;

public class Comprobante {
    private int folio;
    private LocalDateTime fechaEmision;
    private Transaccion transaccion;
    private static int totalEmitidos = 0;
    private double montoTotal = 0;

    public Comprobante(Transaccion transaccion) {
        this.folio= ++totalEmitidos;
        this.fechaEmision=LocalDateTime.now();
        this.transaccion=transaccion;
        this.montoTotal=transaccion.getMonto();
    }

    public String generarTexto() {
        return "COMPROBANTE #" + folio + "\n"
                + "Fecha: " + fechaEmision + "\n"
                + "Tipo: " + transaccion.getClass().getSimpleName() + "\n"
                + "Estado: " + transaccion.getEstado() + "\n"
                + "Fecha/hora: " + transaccion.getFechaHora() + "\n"
                + "Monto: $" + montoTotal + "\n"
                + transaccion;
    }

    public int getFolio() {
        return folio;
    }

}
