#  Premium Parts — Sistema de Gestión de Inventario y Ventas

Sistema web para la gestión interna de inventario, ventas y reportes de repuestos automotrices, desarrollado con Spring Boot, Thymeleaf, Spring Security y MySQL.

---

##  Descripción

Premium Parts centraliza el catálogo de repuestos automotrices, automatiza el control de inventario en tiempo real y gestiona las ventas internas mediante un panel administrativo con roles diferenciados. El catálogo es de acceso público; el panel interno requiere autenticación.

---

##  Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17+ | Lenguaje principal |
| Spring Boot | 4.1.0 | Framework principal |
| Spring Data JPA | Incluido | Acceso a base de datos (DAO) |
| Spring Security | 7.1.0 | Autenticación y autorización por roles |
| Spring Validation | Incluido | Validación de formularios |
| Spring Actuator | Incluido | Monitoreo del sistema |
| Spring Mail | Incluido | Envío de correos para recuperación de contraseña |
| Spring AOP | Incluido | Aspectos de auditoría, logging y rendimiento |
| Logback | Incluido vía Spring Boot | Registro de logs por nivel (INFO, ERROR, WARN) |
| Thymeleaf | Incluido | Motor de plantillas HTML |
| MySQL | 8.x | Base de datos relacional |
| Lombok | Incluido | Reducción de código repetitivo |
| Bootstrap | 5.3.8 | Diseño responsive (vistas públicas) |
| Tailwind CSS | 4 (CDN) | Diseño dark mode (panel administrativo) |
| Maven Wrapper | Incluido | Gestión de dependencias y build |

---

## Arquitectura del sistema

El proyecto implementa **MVC + DAO + AOP**:

```
src/main/java/com/proyecto/sistema/inventario/
│
├── aspect/              ← Programación Orientada a Aspectos (AOP)
│   ├── AuditoriaAspect.java       → Auditoría de operaciones críticas
│   ├── LoggingAspect.java         → Logging con Logback
│   └── PerformanceAspect.java     → Monitoreo de rendimiento
│
├── controller/          ← Capa MVC: recibe peticiones HTTP
│   ├── CatalogoController.java
│   ├── EmpleadoController.java
│   ├── HistorialVentasController.java
│   ├── InventarioController.java
│   ├── LoginDashboardController.java
│   ├── PageController.java
│   ├── RecuperarPasswordController.java
│   ├── ReporteProductController.java
│   └── VentasController.java
│
├── entities/            ← Capa Modelo: entidades JPA
│   ├── Usuario.java
│   ├── Empleado.java
│   ├── Rol.java
│   ├── Categoria.java
│   ├── Producto.java
│   ├── Venta.java
│   └── VentaDetalle.java
│
├── repository/          ← Capa DAO: acceso a base de datos
│   ├── CategoriaRepository.java
│   ├── EmpleadoRepository.java
│   ├── ProductoRepository.java
│   ├── UsuarioRepository.java
│   ├── VentaRepository.java
│   └── VentaDetalleRepository.java
│
├── service/             ← Lógica de negocio
│   ├── CategoriaService.java
│   ├── EmailService.java
│   ├── EmpleadoService.java
│   ├── ProductoService.java
│   ├── ReporteService.java
│   ├── UsuarioService.java
│   └── VentaService.java
│
├── security/            ← Configuración de seguridad
│   ├── CustomSuccessHandler.java
│   ├── SecurityConfig.java
│   └── UserDetailsServiceImpl.java
│
└── InventarioApplication.java
```

---

##  Base de datos

**Motor:** MySQL 8.0
**Nombre:** `inventario_pruebas`

### Tablas

| Tabla | Entidad Java | Descripción |
|---|---|---|
| `usuarios` | `Usuario.java` | Datos base de autenticación (herencia JOINED) |
| `empleados` | `Empleado.java` | Personal interno con rol asignado |
| `categorias` | `Categoria.java` | Categorías de productos |
| `productos` | `Producto.java` | Repuestos del catálogo con stock |
| `ventas` | `Venta.java` | Cabecera de cada venta registrada |
| `venta_detalle` | `VentaDetalle.java` | Líneas de detalle de cada venta |

### Relaciones

- `Empleado` extiende `Usuario` → herencia JOINED
- `Producto` → `Categoria`: relación **N:1**
- `Venta` → `VentaDetalle`: relación **1:N** (cascade ALL)
- `VentaDetalle` → `Producto`: relación **N:1**

---

##  Roles y acceso

| Rol | Ruta tras login | Módulos accesibles |
|---|---|---|
| `ROLE_ADMINISTRADOR` | `/empleados` | Todos los módulos |
| `ROLE_ALMACENERO` | `/inventario` | Inventario y productos |
| `ROLE_VENDEDOR` | `/ventas` | Módulo de ventas |
| Público (sin login) | `/page`, `/catalogo` | Catálogo y página de inicio |

---

##  Rutas del sistema

### Rutas públicas

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/page` | Página de inicio con productos aleatorios |
| GET | `/catalogo` | Catálogo con filtros por nombre, categoría y orden |
| GET | `/login` | Formulario de inicio de sesión |
| GET | `/login/registro` | Registro de nuevo empleado |
| POST | `/login/registrar-empleado` | Guardar nuevo empleado |
| GET | `/login/recuperar` | Formulario recuperación de contraseña |
| GET | `/login/reset-password` | Formulario resetear contraseña |

### Inventario (ADMINISTRADOR + ALMACENERO)

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/inventario` | Vista principal con KPIs y listado |
| POST | `/inventario/categorias` | Crear nueva categoría |
| POST | `/inventario/productos` | Crear o editar producto |
| GET | `/inventario/productos/eliminar/{id}` | Eliminar producto |
| GET | `/inventario/productos/sugerencia?q=` | Autocompletado |

### Ventas (ADMINISTRADOR + VENDEDOR)

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/ventas` | Panel de ventas con KPIs y carrito |
| POST | `/ventas/agregar` | Agregar producto al carrito |
| POST | `/ventas/eliminar` | Eliminar producto del carrito |
| POST | `/ventas/completar` | Completar venta y descontar stock |
| GET | `/ventas/venta/precio?nombreProducto=` | Precio de producto |
| GET | `/ventas/venta/total` | Total de la venta actual |
| GET | `/historial-ventas` | Historial de ventas |

### Reportes (ADMINISTRADOR + ALMACENERO)

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/reporte-productos` | Reporte de productos del inventario |

### Empleados (solo ADMINISTRADOR)

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/empleados` | Listado de empleados |
| POST | `/empleados/guardar` | Crear o editar empleado |
| POST | `/empleados/eliminar/{id}` | Eliminar empleado |

---

##  Instalación y ejecución

### Requisitos

- Java 17+
- MySQL 8.x
- No necesita Maven instalado (incluye Maven Wrapper)

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/Maricielocv/ControlInventario-PremiumParts.git
cd ControlInventario-PremiumParts
```

**2. Crear la base de datos**
```sql
CREATE DATABASE inventario_pruebas;
```

**3. Configurar credenciales en `application.properties`**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventario_pruebas
spring.datasource.username=root
spring.datasource.password=root
```

**4. Primera ejecución**
```bash
./mvnw spring-boot:run
```
Las tablas se crean automáticamente. El archivo `data.sql` carga categorías y productos de prueba.

**5. Cambiar a modo actualización** (para no perder datos)
En `application.properties` cambiar:
```properties
spring.jpa.hibernate.ddl-auto=update
```
Y eliminar o comentar el archivo `data.sql`.

**6. Acceder al sistema**
```
Página pública:   http://localhost:8080/page
Catálogo:         http://localhost:8080/catalogo
Login interno:    http://localhost:8080/login
```

**7. Crear primer usuario**
Ir a `http://localhost:8080/login/registro` y registrar un empleado con rol `ROLE_ADMINISTRADOR`.

---

##  Seguridad

- Contraseñas cifradas con **BCrypt**
- Rutas protegidas por rol con **Spring Security**
- Validación de formularios con **Bean Validation** (`@NotBlank`, `@Email`, `@Size`, `@Min`)
- Auditoría de operaciones mediante **AOP** (`AuditoriaAspect`)
- Logging de métodos críticos con **Logback** (`LoggingAspect`)
- Monitoreo de rendimiento con **AOP** (`PerformanceAspect`)
- Páginas de error personalizadas: `403`, `404`, `500`

---

##  Recuperación de contraseña (opcional)

Configurar en `application.properties`:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_correo@gmail.com
spring.mail.password=tu_contrasena_de_aplicacion
```



##  Repositorio

https://github.com/Maricielocv/ControlInventario-PremiumParts