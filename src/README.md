# Transaction API

API de entrada para la evaluación técnica de transacciones.

Esta aplicación recibe las solicitudes provenientes del frontend, realiza la validación de los datos, maneja la autenticación y seguridad, descifra el secreto recibido mediante AES-256-GCM y comunica las operaciones de transacciones con `transaction-service` mediante OpenFeign.

## Arquitectura

```text
┌─────────────────────┐
│     React Front     │
│      :5173          │
└──────────┬──────────┘
           │
           │ HTTP + JWT
           ▼
┌─────────────────────┐
│   transaction-api   │
│       :8081         │
│                     │
│ - Spring Security   │
│ - JWT               │
│ - BCrypt            │
│ - AES-256-GCM       │
│ - OpenFeign         │
│ - Validaciones      │
└──────────┬──────────┘
           │
           │ OpenFeign
           ▼
┌─────────────────────┐
│ transaction-service │
│       :8082         │
└─────────────────────┘
```

## Tecnologías

* Java 17
* Spring Boot 4.1.1
* Spring Web
* Spring Security
* Spring Data JPA
* Spring Validation
* Spring Cloud OpenFeign
* JWT
* BCrypt
* AES-256-GCM
* H2 Database
* Maven

## Responsabilidades

`transaction-api` es responsable de:

* Autenticación de usuarios.
* Generación y validación de JWT.
* Almacenamiento de usuarios en H2.
* Hash de contraseñas mediante BCrypt.
* Validación de solicitudes.
* Descifrado del atributo `secreto`.
* Comunicación con `transaction-service`.
* Validación de parámetros de paginación y ordenamiento.
* Configuración de CORS para el frontend.

La persistencia de las transacciones es responsabilidad de `transaction-service`.

## Base de datos

La aplicación utiliza una base H2 en memoria exclusivamente para autenticación.

```text
Base de datos: authdb
Usuario: sa
```

La tabla principal es:

```text
users
├── id
├── username
└── password
```

Las contraseñas no se almacenan en texto plano. Se almacenan utilizando BCrypt.

> La base de datos es en memoria, por lo que la información se pierde al reiniciar la aplicación.

## Variables de entorno

La aplicación utiliza variables de entorno para evitar almacenar secretos directamente en el código fuente.

### Windows PowerShell

```powershell
$env:ADMIN_USERNAME="admin"
$env:ADMIN_PASSWORD="Admin123!"
$env:ENCRYPTION_KEY="0123456789abcdef0123456789abcdef"
$env:JWT_SECRET="0123456789abcdef0123456789abcdef"
```

### Variables utilizadas

| Variable         | Descripción                     |
| ---------------- | ------------------------------- |
| `ADMIN_USERNAME` | Usuario inicial                 |
| `ADMIN_PASSWORD` | Contraseña inicial              |
| `ENCRYPTION_KEY` | Llave AES-256                   |
| `JWT_SECRET`     | Llave utilizada para firmar JWT |

No se deben almacenar valores reales de estas variables en el repositorio.

## Ejecución

Requisitos:

* Java 17
* Maven 3.9+
* `transaction-service` ejecutándose en el puerto `8082`

Ejecutar:

```bash
mvn clean spring-boot:run
```

La aplicación estará disponible en:

```text
http://localhost:8081
```

Health check:

```http
GET http://localhost:8081/api/health
```

Respuesta:

```text
UP
```

## Autenticación

### Login

```http
POST http://localhost:8081/api/auth/login
Content-Type: application/json
```

Request:

```json
{
  "username": "admin",
  "password": "Admin123!"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

El token debe enviarse posteriormente mediante:

```http
Authorization: Bearer <token>
```

## Registro de transacción

```http
POST http://localhost:8081/api/transactions
Authorization: Bearer <token>
Content-Type: application/json
```

Ejemplo:

```json
{
  "operacion": "venta",
  "importe": 100.00,
  "cliente": "Angel",
  "secreto": "<secreto-cifrado>"
}
```

El atributo `secreto` es recibido cifrado y descifrado en la API antes de enviarlo al servicio de persistencia.

## Consulta paginada

```http
GET http://localhost:8081/api/transactions?page=0&size=10&sortBy=id&direction=asc
Authorization: Bearer <token>
```

Parámetros:

| Parámetro   | Descripción           | Default |
| ----------- | --------------------- | ------- |
| `page`      | Número de página      | `0`     |
| `size`      | Registros por página  | `10`    |
| `sortBy`    | Campo de ordenamiento | `id`    |
| `direction` | Dirección             | `asc`   |

Campos permitidos para ordenamiento:

```text
id
operacion
importe
cliente
referencia
estatus
```

El tamaño de página permitido es de `1` a `100`.

## Cancelación

```http
PATCH http://localhost:8081/api/transactions
Authorization: Bearer <token>
Content-Type: application/json
```

Request:

```json
{
  "id": 1,
  "referencia": "164354",
  "estatus": "cancelar"
}
```

La solicitud se envía mediante OpenFeign a `transaction-service`.

## Seguridad

Se implementaron las siguientes medidas:

* JWT para autenticación.
* BCrypt para contraseñas.
* AES-256-GCM para cifrado del secreto.
* Validación de entrada mediante Bean Validation.
* CORS configurado para el frontend.
* Validación de campos permitidos para ordenamiento.
* Secretos configurados mediante variables de entorno.
* API sin estado mediante `SessionCreationPolicy.STATELESS`.
* Manejo centralizado de excepciones.

## Estructura principal

```text
src/main/java/mx/com/evaluacion/transaction/api

├── client
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
├── service
└── TransactionApiApplication.java
```

## Flujo de una transacción

```text
Frontend
   │
   │ POST /api/transactions
   ▼
transaction-api
   │
   ├── Validación
   ├── JWT
   ├── Descifrado AES-256-GCM
   │
   ▼
OpenFeign
   │
   ▼
transaction-service
   │
   ├── Generación de referencia
   ├── Persistencia
   └── Estatus APROBADA
   │
   ▼
transaction-api
   │
   ▼
Frontend
```
