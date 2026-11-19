# Requisitos Funcionales

**Proyecto:** Sistema de consola para un banco (Java, POO)
**Dominio:** Banco comercial con sucursales, clientes, cuentas y transacciones

---

## Resumen

| ID | Requisito | Operación | Concepto de POO |
|---|---|---|---|
| RF-01 | Alta de clientes | Create | Herencia (`Persona`) |
| RF-02 | Consulta de clientes | Read | Colecciones, sobrecarga |
| RF-03 | Actualización de datos del cliente | Update | Encapsulamiento |
| RF-04 | Baja de clientes | Delete | Agregación |
| RF-05 | Apertura de cuentas | Create | Herencia y clase abstracta (`Cuenta`) |
| RF-06 | Consulta, estado y cierre de cuentas | Read / Update / Delete | Composición (`HistorialMovimientos`) |
| RF-07 | Registro de depósitos | Create | Herencia (`Transaccion`) |
| RF-08 | Registro de retiros | Create | Polimorfismo (`puedeRetirar()`) |
| RF-09 | Transferencias entre cuentas | Create | Asociación |
| RF-10 | Cálculo de comisiones | Read | Interfaz (`Comisionable`) |
| RF-11 | Gestión de sucursales y empleados | Create / Update / Delete | Composición y agregación |
| RF-12 | Cálculo de intereses | Read | Polimorfismo (`calcularInteres()`) |
| RF-13 | Consulta de historial de movimientos | Read | Sobrecarga |
| RF-14 | Generación de comprobantes | Create | Miembros `static` |

---

## Detalle de los requisitos

### RF-01: Alta de clientes

El sistema debe permitir registrar un cliente con nombre, apellido, teléfono, correo, dirección, CURP y fecha de nacimiento.

- **Validaciones:** el cliente debe ser mayor de 18 años y la CURP no puede repetirse.
- **Clases involucradas:** `Persona`, `Cliente`, `Banco`.
- **Justificación de POO:** `Cliente` hereda los datos comunes de `Persona` y agrega los propios mediante `super(...)`.

### RF-02: Consulta de clientes

El sistema debe permitir buscar un cliente por CURP y listar todos los clientes registrados.

- **Clases involucradas:** `Banco`, `Cliente`.
- **Justificación de POO:** la búsqueda usa un método propio de `Banco` (`buscarCliente`) y el listado recorre una colección de `Persona` aprovechando `obtenerRol()`.

### RF-03: Actualización de datos del cliente

El sistema debe permitir modificar el teléfono, el correo y la dirección de un cliente.

- **Validaciones:** los setters rechazan valores vacíos y lanzan `IllegalArgumentException`.
- **Clases involucradas:** `Persona`, `Cliente`.
- **Justificación de POO:** los atributos son `private` o `protected` y solo se modifican a través de setters que validan.

### RF-04: Baja de clientes

El sistema debe permitir dar de baja a un cliente.

- **Validaciones:** solo se permite si todas sus cuentas están cerradas o con saldo cero.
- **Clases involucradas:** `Banco`, `Cliente`, `Cuenta`.
- **Justificación de POO:** el banco solo agrupa a los clientes (agregación). Al quitarlo del banco, el objeto deja de tener referencias y queda disponible para el recolector de basura.

### RF-05: Apertura de cuentas

El sistema debe permitir abrir una cuenta de ahorro, corriente o de inversión para un cliente existente.

- **Datos propios por tipo:**
  - Ahorro: tasa de interés y retiros mensuales.
  - Corriente: límite de sobregiro y comisión de manejo.
  - Inversión: plazo en días, tasa anual y fecha de vencimiento.
- **Clases involucradas:** `Cuenta` (abstracta), `CuentaAhorro`, `CuentaCorriente`, `CuentaInversion`, `Cliente`.
- **Justificación de POO:** los tres tipos comparten número, saldo y estado, pero se comportan distinto. Esto justifica una clase abstracta con tres hijas.

### RF-06: Consulta, estado y cierre de cuentas

El sistema debe permitir consultar el saldo y los datos de una cuenta, cambiar su estado (activa, bloqueada o cerrada) y eliminar una cuenta cerrada.

- **Validaciones:** una cuenta cerrada no puede reactivarse ni recibir operaciones.
- **Clases involucradas:** `Cuenta`, `EstadoCuenta`, `HistorialMovimientos`.
- **Justificación de POO:** al eliminar una cuenta se elimina también su historial, porque la cuenta lo crea y lo posee (composición).

### RF-07: Registro de depósitos

El sistema debe permitir depositar dinero en una cuenta activa, indicando el monto y el medio (efectivo, cheque, etc.).

- **Validaciones:** el monto debe ser mayor a cero.
- **Clases involucradas:** `Transaccion` (abstracta), `Deposito`, `Cuenta`.
- **Justificación de POO:** `Deposito` hereda de `Transaccion` e implementa su método abstracto `ejecutar()`.

### RF-08: Registro de retiros

El sistema debe permitir retirar dinero de una cuenta, aplicando la regla propia de cada tipo de cuenta.

- **Validaciones por tipo:**
  - Ahorro: el saldo no puede quedar negativo y se permiten máximo 5 retiros al mes.
  - Corriente: el saldo puede bajar hasta el límite de sobregiro.
  - Inversión: solo se permite después de la fecha de vencimiento.
- **Clases involucradas:** `Retiro`, `Cuenta`, `CuentaAhorro`, `CuentaCorriente`, `CuentaInversion`.
- **Justificación de POO:** `Cuenta.retirar()` llama al método abstracto `puedeRetirar()`, que cada hija sobreescribe con `@Override`. Se evita preguntar el tipo con `instanceof`.

### RF-09: Transferencias entre cuentas

El sistema debe permitir transferir dinero entre dos cuentas distintas.

- **Validaciones:**
  - La cuenta de origen y la de destino no pueden ser la misma.
  - Ambas cuentas deben estar activas.
  - La cuenta de origen debe cumplir su regla de retiro.
- **Clases involucradas:** `Transferencia`, `Cuenta`.
- **Justificación de POO:** la transferencia referencia dos cuentas independientes (asociación con roles `cuentaOrigen` y `cuentaDestino`).

### RF-10: Cálculo de comisiones

El sistema debe calcular la comisión de cada operación según su tipo.

- **Reglas:** la comisión nunca puede ser negativa.
- **Clases involucradas:** `Comisionable` (interfaz), `Deposito`, `Retiro`, `Transferencia`.
- **Justificación de POO:** las tres clases implementan `calcularComision()` de forma distinta. Una `List<Comisionable>` calcula el total sin usar `instanceof`.

### RF-11: Gestión de sucursales y empleados

El sistema debe permitir crear sucursales, registrar empleados (cajeros y ejecutivos bancarios) y asignarlos o quitarlos de una sucursal.

- **Datos propios:** el cajero tiene turno; el ejecutivo tiene área y límite de autorización.
- **Clases involucradas:** `Banco`, `Sucursal`, `Empleado` (abstracta), `Cajero`, `EjecutivoBancario`.
- **Justificación de POO:**
  - La sucursal la crea y la posee el banco (composición).
  - Los empleados se crean aparte y se asignan a la sucursal (agregación).
  - Es una segunda jerarquía con un nivel intermedio abstracto (`Persona` → `Empleado` → `Cajero` / `EjecutivoBancario`).

### RF-12: Cálculo de intereses

El sistema debe calcular el interés generado por todas las cuentas en un cierre mensual.

- **Reglas por tipo:**
  - Ahorro: interés según su tasa y saldo.
  - Corriente: sin interés, solo cobra la comisión de manejo.
  - Inversión: interés según tasa anual y plazo.
- **Clases involucradas:** `Cuenta`, `CuentaAhorro`, `CuentaCorriente`, `CuentaInversion`.
- **Justificación de POO:** una `List<Cuenta>` mezcla los tres tipos y llama a `calcularInteres()`. Cada objeto responde según su clase (polimorfismo).

### RF-13: Consulta de historial de movimientos

El sistema debe permitir consultar los movimientos de una cuenta (completos o por rango de fechas) y el historial completo de un cliente.

- **Reglas:** todo cobro debe poder consultarse por cuenta y por cliente.
- **Clases involucradas:** `HistorialMovimientos`, `Banco`.
- **Justificación de POO:** se usa sobrecarga con `consultarPorFecha(LocalDate)` y `consultarPorFecha(LocalDate, LocalDate)`, y también `buscarCuenta(String)` y `buscarCuenta(Cliente)`.

### RF-14: Generación de comprobantes

El sistema debe generar un comprobante al aplicar una operación, con folio, fecha, monto total y datos de la cuenta.

- **Clases involucradas:** `Comprobante`, `Transaccion`.
- **Justificación de POO:** el folio se asigna con un contador `static` (`totalEmitidos`) compartido por todos los comprobantes.

---

## Reglas de negocio asociadas

| Regla | Descripción | Requisitos relacionados |
|---|---|---|
| RN-01 | El monto de toda operación debe ser mayor a cero. | RF-07, RF-08, RF-09 |
| RN-02 | Los costos y comisiones nunca son negativos. | RF-10, RF-14 |
| RN-03 | Solo pueden ser clientes los mayores de 18 años, con CURP única. | RF-01 |
| RN-04 | La cuenta de ahorro no puede quedar en saldo negativo y permite máximo 5 retiros al mes. | RF-08 |
| RN-05 | La cuenta corriente puede sobregirarse hasta su límite. | RF-08 |
| RN-06 | La cuenta de inversión no permite retiros antes de su vencimiento. | RF-08 |
| RN-07 | Una cuenta bloqueada o cerrada no admite retiros ni transferencias. | RF-06, RF-08, RF-09 |
| RN-08 | Una transferencia no puede tener la misma cuenta de origen y destino. | RF-09 |
| RN-09 | Una transacción rechazada o cancelada no puede pasar a aplicada. | RF-09 |
| RN-10 | No se puede dar de baja a un cliente con cuentas activas o con saldo. | RF-04 |
| RN-11 | Todo movimiento debe poder consultarse por cuenta y por cliente. | RF-13 |
