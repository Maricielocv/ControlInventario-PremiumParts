# AGENTS.md — ControlInventario PremiumParts

## What this is

Spring Boot 4.1 server-side rendered inventory management app (Java 17, Maven).
Thymeleaf templates, Spring Security with role-based auth, JPA/Hibernate with MySQL.
Uses Lombok throughout — `@Data`, `@RequiredArgsConstructor`, `@NoArgsConstructor` are everywhere.

## Commands

```bash
# Build & compile (skip tests for speed)
./mvnw clean compile

# Run full build + tests
./mvnw clean verify

# Run a single test class
./mvnw test -Dtest=InventarioApplicationTests

# Run the app (requires MySQL on localhost:3306)
./mvnw spring-boot:run
```

There is no linter, formatter, or typecheck step configured. The only verification is Maven compile + JUnit.

## Architecture

```
com.proyecto.sistema.inventario
├── InventarioApplication.java          # Entry point
├── aspect/                             # AOP: logging, auditing, performance
├── controller/                         # MVC controllers (return Thymeleaf view names)
├── entities/                           # JPA entities (Lombok-managed)
├── repository/                         # Spring Data JPA repositories
├── security/                           # SecurityConfig, CustomSuccessHandler, UserDetailsServiceImpl
└── service/                            # Business logic layer
```

Base package: `com.proyecto.sistema.inventario`

### Key domain model

- `Usuario` → `Empleado` (JOINED inheritance, adds `Rol` enum). This is the Spring Security principal.
- `Producto` → `Categoria` (ManyToOne). Images stored as `byte[]` (LONGBLOB) in DB.
- `Venta` → `VentaDetalle` (sale line items).

### Roles & access (SecurityConfig.java)

| Role | Access |
|------|--------|
| `ROLE_ADMINISTRADOR` | `/empleados/**` + everything |
| `ROLE_ALMACENERO` | `/inventario/**` |
| `ROLE_VENDEDOR` | `/ventas/**` |

After login, `CustomSuccessHandler` redirects by role:
- ADMINISTRADOR → `/empleados`
- VENDEDOR → `/ventas`
- ALMACENERO → `/inventario`

### CSRF

CSRF is disabled only for `/login/recuperar` and `/login/reset-password` (password reset flow). All other POST endpoints require CSRF tokens (auto-included by Thymeleaf).

## Database

- MySQL on `localhost:3306`, database `inventario_pruebas`, user `root`/`root`.
- `spring.jpa.hibernate.ddl-auto=update` — schema is auto-migrated, never dropped.
- `spring.sql.init.mode=always` — `data.sql` seeds categories + products on every startup.
- `data.sql` inserts run on every boot; if you add unique constraints, duplicates will fail silently or throw.

## Gotchas

- **Lombok + Spring Security**: `Empleado` uses manual getters/setters instead of `@Data` because it extends `Usuario` (which has `@Data`). Don't add `@Data` to `Empleado`.
- **`spring-boot-starter-aop` is pinned to 3.5.15** in pom.xml — this is intentional, not a mistake (incompatible with the rest of Spring Boot 4.1).
- **Product images** are stored as raw `byte[]` in the DB via `@Lob`/`LONGBLOB`. New products require an image upload on creation.
- **`data.sql`** is UTF-8 encoded and contains Spanish product catalog data. It re-runs every boot with `spring.sql.init.mode=always`.
- **Thymeleaf templates** are in `src/main/resources/templates/`. Static assets in `src/main/resources/static/{css,js,img}/`.
- **Validation errors** in controllers return the same view with `BindingResult` — check for `model.addAttribute` calls that reload reference data on validation failure.

## OpenCode skills

Relevant skills for this codebase:
- `java-springboot` — Spring Boot best practices
- `java-coding-standards` — Java conventions (Lombok, Optional, streams)
- `java-docs` — Javadoc standards
