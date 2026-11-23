package modelo.transacciones;

public enum Canal {
    CAJERO_EXTERNO(0.05),
    CAJERO_PROPIO(0),
    TRANSFERENCIA(0.01);

    private final double porcentaje;

    Canal(double porcentaje){
        this.porcentaje=porcentaje;
    }

    public double getPorcentaje(){
        return porcentaje;
    }
}
