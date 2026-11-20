# Analisis del Problema y sus posibles objetos

Voy a revisar cómo definen un banco la ley y los reguladores en México, y qué productos y operaciones maneja. Con eso armo el mapa del dominio y lo comparo con tu lista y tus requisitos.

## ¿Qué es un Banco?

se dedica principalmente a captar recursos del público a través de productos, tales como cuentas de cheques, cuentas de ahorro, depósitos a plazo fijo, y con ese dinero da créditos.

## Clases candidatas

Aquí va la lista completa. Marqué cuáles ya están en tus requisitos ("actual") y cuáles serían nuevas ("nueva"), para que decidas el alcance.

### Personas y roles

| Clase | Tipo | Hereda de | Responsabilidad | Origen |
|---|---|---|---|---|
| `Persona` | abstracta | — | Datos comunes: nombre, teléfono, correo, dirección, CURP, fecha de nacimiento | actual |
| `Cliente` | concreta | `Persona` | Titular de cuentas y productos | actual |
| `Empleado` | abstracta | `Persona` | Número de empleado, sueldo, sucursal asignada | actual |
| `Cajero` | concreta | `Empleado` | Opera efectivo; tiene turno | actual |
| `Beneficiario` | concreta | — | Persona designada en una cuenta, con su porcentaje | nueva |

> Justificación de que Persona sea una superClase: dado que las clases hijas como Cliente o Empleado cumple con "Es un" se puede intuir que Persona es padre de ambas, además de que ambas si estuvieran separadas compartirian datos de información personal como la CURP, nombre, contacto, fecha nacimiento. Además no es una clase normal debido a que no existe una Persona que solamente sea Persona, cumple una función.

### Organización

| Clase | Tipo | Hereda de | Responsabilidad | Origen |
|---|---|---|---|---|
| `Banco` | concreta | — | Raíz del sistema: crea sucursales, registra clientes, busca cuentas | actual |
| `Sucursal` | concreta | — | Agrupa empleados; la crea y la posee el banco | actual |
| `BancoExterno` | concreta | — | Otro banco identificado por su código de tres dígitos y nombre | nueva |

### Cuentas (captación)

| Clase | Tipo | Hereda de | Responsabilidad | Origen |
|---|---|---|---|---|
| `Cuenta` | abstracta | — | Número, saldo, estado, titular; `retirar()` usa `puedeRetirar()` | actual |
| `CuentaAhorro` | concreta | `Cuenta` | Tasa, máximo de retiros al mes, sin saldo negativo | actual |
| `CuentaCorriente` | concreta | `Cuenta` | Sobregiro hasta su límite, comisión de manejo | actual |
| `CuentaInversion` | concreta | `Cuenta` | Plazo, tasa anual, vencimiento; no hay retiros antes de que venza | actual |
| `HistorialMovimientos` | concreta | — | Movimientos de la cuenta, con consultas por fecha (sobrecarga) | actual |
| `CuentaExterna` | objeto de valor | — (no hereda de `Cuenta`) | Destino interbancario: CLABE, titular y banco externo | nueva |

> Justificación de Cuenta: Cumple con "Es un" de parte de las clases hijas y sirve para diversificar y sustituir la funcionalidad (Polimorfismo) en clases hijas

### Transacciones

| Clase | Tipo | Hereda de | Responsabilidad | Origen |
|---|---|---|---|---|
| `Transaccion` | abstracta | — | Monto, fecha, estado; `ejecutar()` abstracto | actual |
| `Deposito` | concreta | `Transaccion` | Abono indicando el medio | actual |
| `Retiro` | concreta | `Transaccion` | Cargo con la regla de la cuenta | actual |
| `Transferencia` | concreta | `Transaccion` | Entre dos cuentas del mismo banco | actual |
| `TransferenciaInterbancaria` | concreta | `Transaccion` | Hacia una cuenta externa, con clave de rastreo | nueva |
| `PagoPrestamo` | concreta | `Transaccion` | Abono a un préstamo | nueva |
| `Comprobante` | concreta | — | Folio con contador `static`, fecha, monto | actual |

> Justificación de Transaccion: dado a que "Es un" se cumple en Retiro, Depositov, Transferencia, etc. Además de que tiene lógica de padre, las clases hijas requieren de estado y viven una sola vez por lo que el molde sería necesario. 

### Crédito

| Clase | Tipo | Hereda de | Responsabilidad | Origen |
|---|---|---|---|---|
| `SolicitudCredito` | concreta | — | Estados de revisión, aprobación o rechazo; quién la autoriza | nueva |
| `Prestamo` | abstracta | — | Monto, tasa, plazo, saldo pendiente; `calcularCuota()` abstracto | nueva |
| `PrestamoPersonal` | concreta | `Prestamo` | Sin garantía | nueva |
| `PrestamoNomina` | concreta | `Prestamo` | Se descuenta de la cuenta donde cae la nómina | nueva |
| `PrestamoAutomotriz` | concreta | `Prestamo` | Con garantía (el vehículo) | nueva |
| `PrestamoHipotecario` | concreta | `Prestamo` | Con garantía (el inmueble), plazo largo | nueva |
| `TablaAmortizacion` | concreta | — | Calendario de pagos; composición del préstamo | nueva |
| `Amortizacion` | concreta | — | Cada renglón: periodo, capital, interés, saldo | nueva |
| `Garantia` | concreta | — | Bien que respalda el préstamo | nueva |

> Justificación Prestámo: "Es un", tiene lógica de padre para las 4 clases que heredan, y permite pasar datos comunes entre ellas, además los métodos como plazoMaximo() ayuda a determinarlo. 

### Tarjetas

| Clase | Tipo | Hereda de | Responsabilidad | Origen |
|---|---|---|---|---|
| `Tarjeta` | abstracta | — | Número, vencimiento, estado; `pagar()` abstracto | nueva |
| `TarjetaDebito` | concreta | `Tarjeta` | Paga con el saldo de la cuenta que tiene asociada | nueva |
| `TarjetaCredito` | concreta | `Tarjeta` | Línea de crédito, fecha de corte, pago mínimo | nueva |

> Justificación Tarjeta: "Es un" se cumple y se hereda los atributos comunes

### Interfaces

| Interfaz | Quién la implementa | Para qué | Origen |
|---|---|---|---|
| `Comisionable` | depósito, retiro, transferencia (y las nuevas transacciones) | Calcular comisiones sin `instanceof` | actual |
| `GeneraInteres` | cuentas de ahorro e inversión, préstamos | Separar el interés de la jerarquía de cuentas, para que la cuenta corriente no lo implemente | nueva |
| `SistemaPagos` | `SpeiSimulado` | Enviar transferencias interbancarias sin acoplarse al sistema real | nueva |

### Objetos de valor

| Clase | Responsabilidad |
|---|---|
| `Dinero` | Envuelve un `BigDecimal` con redondeo a dos decimales; evita los errores de `double` |
| `Curp` | Valida el formato en su constructor |
| `Clabe` | Valida 18 dígitos y extrae el código del banco |

### Enums

`EstadoCuenta` (actual), `EstadoTransaccion`, `EstadoPrestamo`, `EstadoSolicitud`, `MedioDeposito`, `Turno`, `TipoPrestamo`
