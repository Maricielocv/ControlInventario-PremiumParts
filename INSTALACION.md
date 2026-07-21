# Manual de Instalación — ControlInventario PremiumParts

Sistema de inventario para autopartes desarrollado con Spring Boot 4.1, Thymeleaf, Spring Security y MySQL.

---

## 1. Requisitos previos

| Componente | Versión mínima | Verificar con |
|------------|----------------|---------------|
| Java JDK | 17+ | `java -version` |
| MySQL | 8.x | `mysql --version` |
| Gmail (opcional) | Cuenta con contraseña de aplicacion | Solo para recuperacion de contrasena |

No es necesario instalar Maven. El proyecto incluye Maven Wrapper (`mvnw` / `mvnw.cmd`).

---

## 2. Base de datos

Crear la base de datos antes de arrancar la aplicacion:

```sql
CREATE DATABASE inventario_pruebas;
```

Las tablas se crean automaticamente al arrancar (`spring.jpa.hibernate.ddl-auto=create`). No ejecutar ningun script de esquema manualmente.

`data.sql` inserta 8 categorias y alrededor de 45 productos de prueba en cada arranque. Si se agregan constraints unicos, las inserciones duplicadas fallaran silenciosamente o lanzaran error.

Para no perder el progreso primero debe arrancar con (`spring.jpa.hibernate.ddl-auto=create`) posteriormente detener la ejecucion y cambiar  (`spring.jpa.hibernate.ddl-auto=create`) a (`spring.jpa.hibernate.ddl-auto=update`) y eliminar el archivo `data.sql`

Las propiedades `spring.jpa.hibernate.ddl-auto= --` se modifican dentro del archivo `application.properties`

---

## 3. Credenciales de base de datos

Las credenciales por defecto en `application.properties` son:

- **Host:** `localhost:3306`
- **Usuario:** `root`
- **Contrasena:** `root`
- **Base de datos:** `inventario_pruebas`

Si tu MySQL usa otra contrasena, editar `src/main/resources/application.properties`:

```properties
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contrasena
```

---

## 4. Usuario administrador | vendedor | almacenero (obligatorio)

**El sistema no incluye usuarios por defecto.** Sin un usuario no se puede acceder al sistema la primera vez, ya que las rutas para el sistema interno (dashboard) necesita autorizacion por roles ,requiere rol `ADMINISTRADOR`, `ALMACENERO` y `VENDEDOR` , según el cargo correspondiente.

Insertar un usuario directamente desde `localhost:8080/login/registro`:

---

## 5. Configuracion de correo electronico (opcional)

El modulo de recuperacion de contrasena usa SMTP de Gmail. En `application.properties`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_correo@gmail.com
spring.mail.password=tu_contrasena_de_aplicacion
mail.urlFront=http://localhost:8080/login/reset-password?token=
```

**Para usar Gmail:** generar una [contraseña de aplicación](https://myaccount.google.com/apppasswords) desde la configuracion de seguridad de la cuenta Google.

Si no se necesita recuperacion de contrasena, el resto del sistema funciona normalmente sin configurar SMTP.

---

## 6. Arrancar la aplicacion

```bash
# Linux / macOS / Windows
./mvnw spring-boot:run
```

La aplicacion estara disponible en: **http://localhost:8080**

---

## 7. Inicio de sesion

- **URL:** `http://localhost:8080/login`
- **Credenciales:** email + contrasena del empleado

---

## 8. Roles y rutas protegidas

| Rol | Redireccion post-login | Rutas accesibles |
|-----|------------------------|------------------|
| `ROLE_ADMINISTRADOR` | `/empleados` | `/empleados/**` + todas las rutas |
| `ROLE_VENDEDOR` | `/ventas` | `/ventas/**` |
| `ROLE_ALMACENERO` | `/inventario` | `/inventario/**` |

**Rutas publicas** (sin autenticacion):
- `/login`
- `/page` y `/`
- `/catalogo/**`
- `/login/recuperar` y `/login/reset-password`

---

## 9. Datos de prueba

El catalogo precargado en `data.sql` contiene productos de una tienda de autopartes en espanol:

| Categoria | Ejemplos |
|-----------|----------|
| Aceites y Lubricantes | Castrol GTX, Mobil Super, Shell Helix |
| Filtros | Toyota, Nissan, Bosch, Mann |
| Frenos | Pastillas Brembo, Discos Brembo, zapatas Toyota |
| Baterias | Bosch, Etna, Record, cargadores portatiles |
| Accesorios | Soportes celulares, camaras retroceso, sensores |
| Suspension | Amortiguadores KYB, Monroe, Bilstein |
| Iluminacion | Faros LED, focos halogenos, barras off-road |
| Neumaticos | Michelin, Bridgestone, Pirelli, Goodyear, Continental |

---

## 10. Solucion de problemas

| Problema | Solucion |
|----------|----------|
| `Access denied for user 'root'` | Verificar usuario y contrasena en `application.properties` |
| `Unknown database 'inventario_pruebas'` | Ejecutar `CREATE DATABASE inventario_pruebas;` |
| No se puede iniciar sesion | Verificar que exista un usuario en `empleados` con `rol` asignado y `password` en BCrypt |
| Puerto 8080 en uso | Cambiar `server.port` en `application.properties` o detener el proceso que lo usa |
| Error de SMTP al recuperar contrasena | Verificar credenciales de Gmail y que la contraseña de aplicacion sea valida |
