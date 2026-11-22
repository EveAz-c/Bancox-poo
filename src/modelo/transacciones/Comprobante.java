package modelo.transacciones;

import java.time.LocalDateTime;

public class Comprobante {
    private int folio;
    private LocalDateTime fecha_emision;
    private double monto_total;
    private static int total_emitidos = 0;

    public Comprobante(Transaccion transaccion) {
    }

    public String generarTexto() {
        // TODO: implementar
        return null;
    }

    public int getFolio() {
        return folio;
    }

}
