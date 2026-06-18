# Alediesme — API de Joyería

API REST en **Java 21** (bytecode 17) y **Spring Boot 2.7** con arquitectura hexagonal, acceso JDBC a **Oracle**, conexión vía **JNDI en WebLogic** (prod) o JDBC directo (dev), y autenticación **JWT**.

## Stack

| Componente | Tecnología |
|---|---|
| Lenguaje | Java 21 (toolchain) / bytecode 17 |
| Framework | Spring Boot 2.7.6 |
| Servidor embebido (dev) | Undertow |
| Base de datos | Oracle (JDBC + pool HikariCP en dev) |
| Despliegue prod | WebLogic 14.1.2 + JNDI `jdbc/AlediesmeDS` |
| Seguridad | Spring Security + JWT (jjwt) |
| Documentación API | springdoc-openapi (solo perfil `dev`) |
| Monitoreo | Spring Actuator |
| Build | Gradle 8.5 multi-módulo |

## Arquitectura

```
src/main/          → Arranque Spring Boot
domain/            → Entidades, value objects, puertos out, servicios de dominio
application/       → Casos de uso, DTOs, puertos in (Java puro, sin Spring)
infrastructure/    → Adaptadores JDBC, controllers, seguridad, configuración
```

**Regla de dependencias:** `infrastructure → application → domain`

```
HTTP Request
    → Controller (infrastructure)
    → Use Case (application)
    → Domain Service (domain)
    → Repository port → Jdbc*Repository (infrastructure)
```

## Módulos Gradle

| Módulo | Responsabilidad |
|---|---|
| `domain` | Lógica de negocio pura (sin Spring) |
| `application` | Orquestación de casos de uso (sin Spring) |
| `infrastructure` | Spring, JDBC, JWT, configs, adapters |
| raíz (`boot`) | `Application.java`, properties, empaquetado JAR |

## Requisitos

| Entorno | Requisito |
|---|---|
| Build | JDK 21 (Gradle Toolchain; Foojay puede descargarlo) |
| Runtime dev | Oracle accesible o variables `DB_*` configuradas |
| Runtime prod | WebLogic 14.1.2 con JDK 21 y DataSource JNDI |
| Build tool | Gradle 8.5 (wrapper incluido) |

### Versión de Java (cambio centralizado)

Editar `gradle.properties`:

```properties
javaVersion=21    # JDK para compilar y ejecutar
javaRelease=17    # Bytecode (.class) — Spring Boot 2.7 soporta hasta 17
```

> Para Java 21 completo (sintaxis + bytecode), migrar a Spring Boot 3.x y poner `javaRelease=21`.

---

## Configuración de conexión a base de datos

### Desarrollo local (`dev`)

Spring Boot crea el pool HikariCP con propiedades directas:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

### Producción WebLogic (`prod`)

La conexión la administra **WebLogic** vía JNDI. La aplicación solo referencia el nombre:

```properties
spring.datasource.jndi-name=${DB_JNDI_NAME:jdbc/AlediesmeDS}
```

**En consola WebLogic:**

1. *Services → Data Sources → New*
2. Nombre: `AlediesmeDS`
3. JNDI Name: `jdbc/AlediesmeDS`
4. Database Type: Oracle
5. Configurar URL, usuario y password del pool (WebLogic los gestiona)
6. Asignar *Target* al servidor o cluster
7. Desplegar la aplicación con perfil `prod`

> Ya **no** se usa tabla `app_database_config` ni variables `STARTUP_DB_*`.

---

## Scripts SQL

### Orden de ejecución recomendado

| # | Script | Conectado como | Propósito |
|---|---|---|---|
| 1 | `create_data_base.sql` | `SYS` / DBA (bloque inicial) + `ALEDIESME` (catálogos y `customer`) | Usuario Oracle, catálogos geográficos, `customer`, monedas, etc. |
| 2 | `app_business_tables.sql` | `ALEDIESME` | Tablas de negocio adicionales (si aplica) |
| 3 | `app_security_users.sql` | `ALEDIESME` | Usuarios JWT (`app_user`, `app_role`) + FK `customer → app_user` |
| 4 | `consultas_ejemplos.sql` | `ALEDIESME` | Consultas de referencia (opcional) |

> **Importante:** `app_security_users.sql` debe ejecutarse **después** de `create_data_base.sql` porque agrega la FK de `customer.app_user_id` hacia `app_user`.

### Ajustes obligatorios por script

#### `scripts/create_data_base.sql`

| Qué ajustar | Dónde | Notas |
|---|---|---|
| **Password del usuario** | `CREATE USER ALEDIESME IDENTIFIED BY "..."` | Debe coincidir con `DB_PASSWORD` en dev y con el pool JDBC de WebLogic en prod. |
| **Pluggable DB** | `ALTER SESSION SET CONTAINER = XEPDB1` | Cambiar si tu PDB tiene otro nombre. |
| **Ejecución parcial** | Todo el archivo | Si el usuario ya existe, omitir el bloque `CREATE USER` y ejecutar solo catálogos + `customer`. |

#### `scripts/app_business_tables.sql`

| Qué ajustar | Notas |
|---|---|
| IDs y datos seed | Los `INSERT` son de ejemplo. Adaptar o eliminar en producción. |

**Tablas que consume la API actual:**

| Tabla | Uso |
|---|---|
| `customer` | Registro y consulta de clientes (`GET/POST /customer`) |
| `app_user`, `app_role`, `app_user_role` | Autenticación JWT |
| Catálogos (`document_type`, `user_type`, etc.) | FKs de `customer` |

#### `scripts/app_security_users.sql`

| Qué ajustar | Notas |
|---|---|
| **Password del admin** | Seed: `admin` / `admin123` (BCrypt). **Cambiar en producción.** |
| **Roles** | `ROLE_ADMIN`, `ROLE_USER_WEB`, etc. |
| **Orden** | Ejecutar después de `create_data_base.sql` |

#### `scripts/consultas_ejemplos.sql`

| Qué ajustar | Notas |
|---|---|
| Nombres de tablas/columnas | Referencia al schema de `create_data_base.sql`. |

### Script eliminado (ya no usar)

| Script | Motivo |
|---|---|
| ~~`app_database_config.sql`~~ | Reemplazado por JNDI en WebLogic (prod) y `DB_*` en dev. |
| ~~`crear_base_datos_oracle.sql`~~ | Nombre anterior; usar `create_data_base.sql`. |

---

## Variables de entorno

Copiar `.env.example` como referencia.

### Desarrollo (`dev`)

```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
$env:DB_URL="jdbc:oracle:thin:@//host:1521/XEPDB1"
$env:DB_USERNAME="ALEDIESME"
$env:DB_PASSWORD="tu_password"
$env:JWT_SECRET="clave-secreta-minimo-32-caracteres"
```

| Variable | Descripción |
|---|---|
| `DB_URL` | JDBC URL Oracle |
| `DB_USERNAME` | Usuario de BD |
| `DB_PASSWORD` | Password de BD (**obligatorio**) |
| `JWT_SECRET` | Firma HS256 (**obligatorio**, ≥ 32 caracteres) |
| `JWT_EXPIRATION_MS` | Expiración token (default: 3600000) |
| `SQL_DIALECT` | Dialecto SQL runtime: `oracle` (dev/prod) o `common` (tests) |

### Producción WebLogic (`prod`)

```powershell
$env:SPRING_PROFILES_ACTIVE="prod"
$env:DB_JNDI_NAME="jdbc/AlediesmeDS"   # opcional si usas el default
$env:JWT_SECRET="clave-secreta-minimo-32-caracteres"
```

| Variable | Descripción |
|---|---|
| `DB_JNDI_NAME` | JNDI del DataSource en WebLogic (default: `jdbc/AlediesmeDS`) |
| `JWT_SECRET` | Firma HS256 (**obligatorio**, ≥ 32 caracteres) |
| `SERVER_PORT` | Puerto HTTP (default: 8080) |

En **desarrollo**, `DevSecretsValidator` falla al arrancar si faltan `DB_PASSWORD` o `JWT_SECRET` (≥ 32 caracteres).

En **producción**, `ProductionSecretsValidator` falla al arrancar si faltan `JWT_SECRET` o `spring.datasource.jndi-name`.

---

## Ejecución

### Desarrollo local

```powershell
$env:DB_PASSWORD="tu_password"
$env:JWT_SECRET="clave-secreta-minimo-32-caracteres"
.\gradlew bootRun
```

- Perfil por defecto: `dev`
- Puerto: `8083`
- Swagger: http://localhost:8083/alediesme/swagger-ui.html
- Context path: `/alediesme`

### Producción

```powershell
$env:SPRING_PROFILES_ACTIVE="prod"
$env:JWT_SECRET="clave-secreta-minimo-32-caracteres"
.\gradlew bootJar
java -jar build\libs\alediesme-0.0.1-SNAPSHOT.jar
```

En WebLogic, desplegar el JAR/WAR con perfil `prod` y DataSource JNDI configurado en el servidor.

- Swagger deshabilitado
- Logging: WARN (root), INFO (aplicación)

---

## API

Base URL (dev): `http://localhost:8083/alediesme`

### Autenticación

```http
POST /auth/login
Content-Type: application/json

{"username":"admin","password":"admin123"}
```

Respuesta:

```json
{
  "accessToken": "eyJ...",
  "tokenType": "Bearer",
  "expiresInMs": 3600000,
  "username": "admin",
  "roles": ["ROLE_ADMIN"]
}
```

Requests protegidos:

```http
Authorization: Bearer <accessToken>
```

### Endpoints de negocio

| Método | Ruta | Descripción | Auth / Rol |
|---|---|---|---|
| GET | `/customer/{id}` | Obtener cliente | JWT — `ROLE_ADMIN` o `ROLE_USER_WEB` |
| POST | `/customer` | Registrar cliente | JWT — `ROLE_ADMIN` |

### Actuator

| Ruta | Auth | Descripción |
|---|---|---|
| `/actuator/health` | Pública | Liveness/readiness |
| `/actuator/info` | `ROLE_ADMIN` | Información de la app (`info.app.*`) |

### Observabilidad

| Mecanismo | Descripción |
|---|---|
| `X-Request-Id` | Header de correlación en request/response; aparece en logs como `[requestId]` |
| `/actuator/health` | Liveness/readiness (público) |
| Logback | Archivo rotativo en `logging.file.path` (prod: `ALEDIESME_LOG_PATH`) |

En **dev**, Swagger UI incluye esquema **Bearer JWT** para probar endpoints protegidos. En **prod**, OpenAPI está deshabilitado.

---

## Tests

```powershell
.\gradlew test
```

| Tipo | Ubicación | Descripción |
|---|---|---|
| Unitarios dominio | `domain/src/test` | Entidades, servicios, VOs |
| Unitarios application | `application/src/test` | Casos de uso |
| Unitarios infra | `infrastructure/src/test` | Controllers, JWT, BCrypt, seguridad |
| Integración | `src/test/.../integration` | Spring Boot + H2 en memoria (perfil `test`) |

Los tests de integración **no requieren Oracle** ni WebLogic.

---

## Portabilidad multi-BD

### SQL de runtime (JDBC)

Las consultas de la API viven en `infrastructure/src/main/resources/sql/`:

| Carpeta | Uso |
|---|---|
| `sql/common/` | SQL ANSI portable (customer, security) — usado por H2 en tests |
| `sql/oracle/` | Overrides solo cuando Oracle requiera sintaxis distinta (vacío hoy) |

Resolución en arranque (`SqlStatementProcessor`):

1. `sql/{SQL_DIALECT}/...`
2. fallback → `sql/common/...`

Variable: `app.persistence.sql-dialect` / `SQL_DIALECT` (`oracle` en dev/prod, `common` en tests).

> El SQL de negocio **no** usa `DUAL`, `SYSDATE` ni secuencias; eso queda en scripts DDL de `scripts/` (solo Oracle).

### Validación de pool por perfil

| Perfil | `connection-test-query` |
|---|---|
| `dev` (Oracle) | `SELECT 1 FROM DUAL` |
| `test` (H2) | `SELECT 1` |
| `prod` (WebLogic JNDI) | Lo define el DataSource del servidor |

### Puertos de persistencia (contratos)

**`CustomerRepository`** (`domain`)

| Método | Entrada | Salida |
|---|---|---|
| `findById` | `CustomerId` | `Optional<Customer>` |
| `existsByDocument` | `documentTypeId`, `documentNumber` | `boolean` |
| `existsByEmail` | `email` | `boolean` |
| `save` | `CustomerRegistration` | `Customer` |

**`UserRepository`** + **`TokenProvider`** (`domain/security`)

| Puerto | Responsabilidad |
|---|---|
| `UserRepository` | Buscar usuario y roles por username |
| `TokenProvider` | Generar y validar JWT |
| `PasswordHasher` | Comparar hash BCrypt |

Implementaciones JDBC/JWT en `infrastructure`; el dominio no conoce Oracle ni H2.

---

## Perfiles Spring

| Perfil | Conexión BD | Swagger | Secretos |
|---|---|---|---|
| `dev` | JDBC directo (`DB_*`) | Habilitado (JWT en UI) | `DB_PASSWORD` + `JWT_SECRET` obligatorios |
| `prod` | JNDI WebLogic (`DB_JNDI_NAME`) | Deshabilitado | JWT obligatorio |
| `test` | H2 en memoria | Deshabilitado | Valores fijos en `application-test.properties` |

---

## Estructura del proyecto

```
alediesme/
├── domain/                 # Nucleo de negocio
├── application/            # Casos de uso
├── infrastructure/         # Adapters, Spring, JDBC, JWT
│   └── src/main/resources/sql/
│       ├── common/         # SQL portable (runtime)
│       └── oracle/         # Overrides Oracle (si aplica)
├── src/main/               # Boot application + properties
├── src/test/               # Tests de integracion
├── scripts/                # SQL Oracle
│   ├── create_data_base.sql
│   ├── app_business_tables.sql
│   ├── app_security_users.sql
│   └── consultas_ejemplos.sql
├── gradle.properties       # javaVersion, javaRelease, Spring Boot
└── .env.example            # Plantilla de variables de entorno
```

---

## Historial de cambios arquitectónicos (resumen)

| Fase | Cambio principal |
|---|---|
| 0 | Build estable, namespace `com.alediesme.joyeria` |
| 1 | Dominio rico, value objects, puertos |
| 2 | Capa application sin Spring, use cases |
| 3 | Infra limpia: `Jdbc*Repository`, controllers REST |
| 4 | ~~Config conexión desde tabla BD~~ → **reemplazado por JNDI** |
| 5 | JWT + usuarios en BD (`app_user`, `app_role`) |
| 6 | Perfiles dev/prod, Actuator, eliminación JPA |
| 7 | Tests integración + documentación |
| Actual | **Java 21**, conexión **JNDI WebLogic** en prod, JDBC directo en dev |

---

## Solución de problemas frecuentes

| Error | Causa | Solución |
|---|---|---|
| `ORA-01005: contraseña nula` | Falta `DB_PASSWORD` en dev | Definir `$env:DB_PASSWORD` antes de `bootRun` |
| `Unable to obtain connection from JNDI` | DataSource no existe en WebLogic | Crear `jdbc/AlediesmeDS` en consola WL |
| `401 Unauthorized` en API | Sin token JWT | Hacer `POST /auth/login` primero |
| `403 Forbidden` en POST `/customer` | Usuario sin `ROLE_ADMIN` | Usar token de admin o asignar rol |
| `JWT_SECRET must be at least 32 characters` | Secret corto en prod | Usar clave ≥ 32 caracteres |
| Tests OK pero bootRun falla | Variables solo en IDE/terminal | Configurar env vars en run configuration |

---

## Próximos pasos sugeridos

- Migración a **Spring Boot 3.x** para bytecode Java 21 completo
- Refresh token JWT (opcional)
- Rate limit en `/auth/login` (anti fuerza bruta)
- Abstracción SQL por dialecto (multi-BD)
