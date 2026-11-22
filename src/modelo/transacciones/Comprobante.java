package modelo.transacciones;

import java.time.LocalDateTime;

public class Comprobante {
    private int folio;
    private LocalDateTime fechaEmision;
    private double montoTotal;
    private static int totalEmitidos = 0;

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
