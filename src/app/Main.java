package app;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import modelo.banco.Banco;
import modelo.banco.Sucursal;
import modelo.cuentas.Cuenta;
import modelo.cuentas.CuentaAhorro;
import modelo.cuentas.CuentaCorriente;
import modelo.cuentas.CuentaInversion;
import modelo.cuentas.EstadoCuenta;
import modelo.personas.Cajero;
import modelo.personas.Cliente;
import modelo.personas.EjecutivoBancario;
import modelo.personas.Empleado;
import modelo.transacciones.Comisionable;
import modelo.transacciones.Comprobante;
import modelo.transacciones.Deposito;
import modelo.transacciones.Retiro;
import modelo.transacciones.Transaccion;
import modelo.transacciones.Transferencia;

/**
 * Punto de entrada de Bancox. Menu de consola que se repite hasta elegir salir.
 *
 * <p>Esta clase solo pide datos y muestra resultados: toda la logica y las
 * reglas de negocio viven en las clases del modelo.</p>
 */
public class Main {

    private static final Scanner ENTRADA = new Scanner(System.in);

    private static Banco banco;
    private static Sucursal sucursal;
    private static final List<Comisionable> operacionesAplicadas = new ArrayList<>();

    private static int consecutivoCuenta = 0;
    private static int consecutivoEmpleado = 0;

    public static void main(String[] args) {
        banco = new Banco("Bancox", "BXO010101AB1");
        sucursal = banco.crearSucursal("S-001", "Matriz", "Av. Principal 100");
        if (sucursal == null) {
            System.out.println("Aviso: Banco.crearSucursal() todavia no devuelve la sucursal.");
        }

        try {
            int opcion;
            do {
                mostrarMenu();
                opcion = leerOpcion();
                final int elegida = opcion;
                intentar(() -> ejecutarOpcion(elegida));
            } while (opcion != 0);
        } catch (NoSuchElementException e) {
            System.out.println("\nSe termino la entrada de datos.");
        }
        System.out.println("Hasta luego.");
    }


    private static void mostrarMenu() {
        System.out.println();
        System.out.println("========== BANCOX ==========");
        System.out.println(" 1. Registrar cliente");
        System.out.println(" 2. Consultar y actualizar un cliente");
        System.out.println(" 3. Dar de baja a un cliente");
        System.out.println(" 4. Abrir cuenta (ahorro, corriente o inversion)");
        System.out.println(" 5. Consultar una cuenta y cambiar su estado");
        System.out.println(" 6. Depositar");
        System.out.println(" 7. Retirar");
        System.out.println(" 8. Transferir entre cuentas");
        System.out.println(" 9. Registrar empleado y asignarlo a la sucursal");
        System.out.println("10. Calcular intereses de las cuentas de un cliente");
        System.out.println("11. Consultar historial de movimientos de un cliente");
        System.out.println("12. Total de comisiones de la sesion");
        System.out.println("13. Ejecutar escenario de demostracion");
        System.out.println(" 0. Salir");
    }

    private static int leerOpcion() {
        System.out.print("Opcion: ");
        String linea = ENTRADA.nextLine().trim();
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> registrarCliente();
            case 2 -> consultarYActualizarCliente();
            case 3 -> darDeBajaCliente();
            case 4 -> abrirCuenta();
            case 5 -> consultarCuenta();
            case 6 -> depositar();
            case 7 -> retirar();
            case 8 -> transferir();
            case 9 -> registrarEmpleado();
            case 10 -> {
                Cliente cliente = pedirCliente();
                if (cliente != null) {
                    mostrarIntereses(cliente);
                }
            }
            case 11 -> {
                Cliente cliente = pedirCliente();
                if (cliente != null) {
                    mostrarHistorial(cliente);
                }
            }
            case 12 -> mostrarTotalComisiones();
            case 13 -> ejecutarDemostracion();
            case 0 -> { }
            default -> System.out.println("Esa opcion no esta en el menu.");
        }
    }

    private static void registrarCliente() {
        String nombre = leerTexto("Nombre");
        String apellido = leerTexto("Apellido");
        String telefono = leerTexto("Telefono");
        String correo = leerTexto("Correo");
        String direccion = leerTexto("Direccion");
        String curp = leerTexto("CURP").toUpperCase();
        LocalDate nacimiento = leerFecha("Fecha de nacimiento (AAAA-MM-DD)");

        Cliente cliente = new Cliente(nombre, apellido, telefono, correo, direccion, curp, nacimiento);
        if (!cliente.esMayorDeEdad()) {
            System.out.println("No se registro: el cliente debe ser mayor de 18 anios.");
            return;
        }
        banco.registrarCliente(cliente);
        System.out.println("Cliente registrado: " + cliente.getNombreCompleto()
                + " (" + cliente.obtenerRol() + ")");
    }

    private static void consultarYActualizarCliente() {
        Cliente cliente = pedirCliente();
        if (cliente == null) {
            return;
        }
        System.out.println(cliente);
        System.out.println("1. Cambiar telefono  2. Cambiar correo  3. Cambiar direccion  0. Volver");
        switch (leerEntero("Opcion")) {
            case 1 -> cliente.setTelefono(leerTexto("Nuevo telefono"));
            case 2 -> cliente.setCorreo(leerTexto("Nuevo correo"));
            case 3 -> cliente.setDireccion(leerTexto("Nueva direccion"));
            default -> { return; }
        }
        System.out.println("Datos actualizados.");
    }

    private static void darDeBajaCliente() {
        String curp = leerTexto("CURP del cliente").toUpperCase();
        if (!confirmar("Confirmar la baja")) {
            System.out.println("Operacion cancelada.");
            return;
        }
        // Al salir de la lista del banco, el Cliente se queda sin referencias y el
        // recolector de basura de Java libera su memoria. La baja solo la permite
        // Banco si el cliente no tiene cuentas activas con saldo.
        boolean eliminado = banco.eliminarCliente(curp);
        System.out.println(eliminado ? "Cliente dado de baja." : "No se dio de baja al cliente.");
    }

    private static void abrirCuenta() {
        Cliente titular = pedirCliente();
        if (titular == null) {
            return;
        }
        System.out.println("Tipo de cuenta: 1. Ahorro  2. Corriente  3. Inversion");
        int tipo = leerEntero("Tipo");
        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo de cuenta no valido.");
            return;
        }
        double saldoInicial = leerDecimal("Saldo inicial");
        String numero = String.format("BX-%05d", ++consecutivoCuenta);

        // Cuenta es la clase padre: cada rama crea una hija distinta (polimorfismo).
        Cuenta cuenta = switch (tipo) {
            case 1 -> new CuentaAhorro(numero, titular, saldoInicial,
                    leerDecimal("Tasa de interes anual (ejemplo 0.04)"));
            case 2 -> new CuentaCorriente(numero, titular, saldoInicial,
                    leerDecimal("Limite de sobregiro"));
            default -> new CuentaInversion(numero, titular, saldoInicial,
                    leerEntero("Plazo en dias"), leerDecimal("Tasa anual (ejemplo 0.08)"));
        };

        System.out.println("Cuenta creada: " + numero);
        System.out.println(cuenta);
        if (banco.buscarCuenta(numero) == null) {
            System.out.println("Aviso: Banco no encuentra la cuenta recien creada. "
                    + "Falta vincularla a su titular (por ejemplo Cliente.agregarCuenta).");
        }
    }

    private static void consultarCuenta() {
        Cuenta cuenta = pedirCuenta("Numero de cuenta");
        if (cuenta == null) {
            return;
        }
        System.out.println(cuenta);
        System.out.println("Saldo: " + dinero(cuenta.consultarSaldo()));
        System.out.println("Cambiar estado: 1. ACTIVA  2. BLOQUEADA  3. CERRADA  0. Ninguno");
        switch (leerEntero("Opcion")) {
            case 1 -> cuenta.cambiarEstado(EstadoCuenta.ACTIVA);
            case 2 -> cuenta.cambiarEstado(EstadoCuenta.BLOQUEADA);
            case 3 -> cuenta.cambiarEstado(EstadoCuenta.CERRADA);
            default -> { return; }
        }
        System.out.println("Estado actualizado.");
    }


    private static void depositar() {
        Cuenta cuenta = pedirCuenta("Numero de cuenta");
        if (cuenta == null) {
            return;
        }
        double monto = leerDecimal("Monto a depositar");
        String medio = leerTexto("Medio (EFECTIVO, CHEQUE...)").toUpperCase();
        aplicar(new Deposito(monto, cuenta, medio));
    }

    private static void retirar() {
        Cuenta cuenta = pedirCuenta("Numero de cuenta");
        if (cuenta == null) {
            return;
        }
        double monto = leerDecimal("Monto a retirar");
        String canal = leerTexto("Canal (VENTANILLA, CAJERO...)").toUpperCase();
        aplicar(new Retiro(monto, cuenta, canal));
    }

    private static void transferir() {
        Cuenta origen = pedirCuenta("Cuenta de origen");
        if (origen == null) {
            return;
        }
        Cuenta destino = pedirCuenta("Cuenta de destino");
        if (destino == null) {
            return;
        }
        double monto = leerDecimal("Monto a transferir");
        String referencia = leerTexto("Referencia");
        aplicar(new Transferencia(monto, origen, destino, referencia));
    }


    private static <T extends Transaccion & Comisionable> void aplicar(T operacion) {
        operacion.ejecutar();
        operacionesAplicadas.add(operacion);
        System.out.println("Operacion: " + operacion);
        System.out.println("Comision: " + dinero(operacion.calcularComision()));
        String comprobante = new Comprobante(operacion).generarTexto();
        if (comprobante != null) {
            System.out.println(comprobante);
        }
    }

    private static void registrarEmpleado() {
        if (sucursal == null) {
            System.out.println("No hay sucursal a la cual asignar al empleado.");
            return;
        }
        System.out.println("Tipo de empleado: 1. Cajero  2. Ejecutivo bancario");
        int tipo = leerEntero("Tipo");
        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo de empleado no valido.");
            return;
        }
        String nombre = leerTexto("Nombre");
        String apellido = leerTexto("Apellido");
        String telefono = leerTexto("Telefono");
        String correo = leerTexto("Correo");
        String numero = String.format("E-%04d", ++consecutivoEmpleado);

        Empleado empleado = (tipo == 1)
                ? new Cajero(nombre, apellido, telefono, correo, numero,
                        leerTexto("Turno (MATUTINO, VESPERTINO)").toUpperCase())
                : new EjecutivoBancario(nombre, apellido, telefono, correo, numero,
                        leerTexto("Area"), leerDecimal("Limite de autorizacion"));
        sucursal.asignarEmpleado(empleado);
        mostrarPlantilla();
    }

    private static void mostrarPlantilla() {
        System.out.println("Plantilla de la sucursal:");
        for (Empleado empleado : sucursal.listarEmpleados()) {
            System.out.println(" - " + empleado.getNumeroEmpleado() + "  "
                    + empleado.getNombreCompleto() + "  (" + empleado.obtenerRol() + ")");
        }
    }

    private static void mostrarIntereses(Cliente cliente) {
        List<Cuenta> cuentas = banco.buscarCuenta(cliente);
        if (cuentas.isEmpty()) {
            System.out.println("El cliente no tiene cuentas.");
            return;
        }
        double total = 0;
        for (Cuenta cuenta : cuentas) {
            double interes = cuenta.calcularInteres();
            total += interes;
            System.out.println(" - " + cuenta + "  interes: " + dinero(interes));
        }
        System.out.println("Interes total: " + dinero(total));
    }

    private static void mostrarHistorial(Cliente cliente) {
        List<Transaccion> movimientos = banco.historialPorCliente(cliente);
        if (movimientos.isEmpty()) {
            System.out.println("Sin movimientos registrados.");
            return;
        }
        int numero = 1;
        for (Transaccion movimiento : movimientos) {
            System.out.println(numero++ + ". " + movimiento);
        }
    }

    private static void mostrarTotalComisiones() {
        double total = 0;
        for (Comisionable operacion : operacionesAplicadas) {
            total += operacion.calcularComision();
        }
        System.out.println("Operaciones aplicadas en la sesion: " + operacionesAplicadas.size());
        System.out.println("Total de comisiones: " + dinero(total));
    }

    private static void ejecutarDemostracion() {
        paso("1. Registrar dos clientes");
        Cliente ana = new Cliente("Ana", "Perez", "2281234567", "ana@correo.com",
                "Calle 1 #10", "PEGA900101MVZRRN01", LocalDate.of(1990, 1, 1));
        Cliente luis = new Cliente("Luis", "Gomez", "2287654321", "luis@correo.com",
                "Calle 2 #20", "GOLU850505HVZMSS02", LocalDate.of(1985, 5, 5));
        intentar(() -> banco.registrarCliente(ana));
        intentar(() -> banco.registrarCliente(luis));
        System.out.println(ana);
        System.out.println(luis);

        paso("2. Registrar un cajero y un ejecutivo y asignarlos a la sucursal");
        if (sucursal != null) {
            Empleado cajero = new Cajero("Marta", "Ruiz", "2281112233", "marta@bancox.com",
                    String.format("E-%04d", ++consecutivoEmpleado), "MATUTINO");
            Empleado ejecutivo = new EjecutivoBancario("Jorge", "Diaz", "2284445566",
                    "jorge@bancox.com", String.format("E-%04d", ++consecutivoEmpleado),
                    "Credito", 50000);
            intentar(() -> sucursal.asignarEmpleado(cajero));
            intentar(() -> sucursal.asignarEmpleado(ejecutivo));
            mostrarPlantilla();
        }

        paso("3. Abrir una cuenta de cada tipo");
        Cuenta ahorro = new CuentaAhorro(String.format("BX-%05d", ++consecutivoCuenta),
                ana, 10000, 0.04);
        Cuenta corriente = new CuentaCorriente(String.format("BX-%05d", ++consecutivoCuenta),
                ana, 5000, 2000);
        Cuenta inversion = new CuentaInversion(String.format("BX-%05d", ++consecutivoCuenta),
                luis, 20000, 90, 0.08);
        System.out.println(ahorro);
        System.out.println(corriente);
        System.out.println(inversion);

        paso("4. Deposito, retiro y transferencia");
        intentar(() -> aplicar(new Deposito(2500, ahorro, "EFECTIVO")));
        intentar(() -> aplicar(new Retiro(1000, ahorro, "VENTANILLA")));
        intentar(() -> aplicar(new Transferencia(1500, ahorro, inversion, "PAGO RENTA")));

        paso("5. Intento que viola una regla de negocio (retiro de inversion antes del vencimiento)");
        intentar(() -> aplicar(new Retiro(500, inversion, "VENTANILLA")));

        paso("6. Intereses por cuenta (polimorfismo)");
        mostrarIntereses(ana);
        mostrarIntereses(luis);

        paso("7. Total de comisiones (interfaz Comisionable)");
        mostrarTotalComisiones();

        paso("8. Historial de movimientos de Ana");
        mostrarHistorial(ana);
    }

    private static void paso(String titulo) {
        System.out.println();
        System.out.println("--- " + titulo + " ---");
    }

    private static void intentar(Runnable accion) {
        try {
            accion.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Rechazado: " + e.getMessage());
        }
    }

    private static Cliente pedirCliente() {
        String curp = leerTexto("CURP del cliente").toUpperCase();
        Cliente cliente = banco.buscarCliente(curp);
        if (cliente == null) {
            System.out.println("No existe un cliente con esa CURP.");
        }
        return cliente;
    }

    private static Cuenta pedirCuenta(String etiqueta) {
        String numero = leerTexto(etiqueta).toUpperCase();
        Cuenta cuenta = banco.buscarCuenta(numero);
        if (cuenta == null) {
            System.out.println("No existe la cuenta " + numero + ".");
        }
        return cuenta;
    }

    private static String leerTexto(String etiqueta) {
        while (true) {
            System.out.print(etiqueta + ": ");
            String texto = ENTRADA.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Este dato no puede estar vacio.");
        }
    }

    private static int leerEntero(String etiqueta) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(etiqueta));
            } catch (NumberFormatException e) {
                System.out.println("Escribe un numero entero.");
            }
        }
    }

    private static double leerDecimal(String etiqueta) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(etiqueta).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Escribe un numero (ejemplo 1500.50).");
            }
        }
    }

    private static LocalDate leerFecha(String etiqueta) {
        while (true) {
            try {
                return LocalDate.parse(leerTexto(etiqueta));
            } catch (DateTimeParseException e) {
                System.out.println("Fecha no valida. Usa el formato AAAA-MM-DD.");
            }
        }
    }

    private static boolean confirmar(String pregunta) {
        return leerTexto(pregunta + " (S/N)").equalsIgnoreCase("S");
    }

    private static String dinero(double monto) {
        return String.format("$%,.2f", monto);
    }
}1