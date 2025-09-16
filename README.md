# DevJobs – AA2 EV01 (Java 17 + Maven + JDBC + MySQL)

Proyecto del SENA que implementa conexión **JDBC** a MySQL y un **CRUD** de ofertas de empleo.

## Requisitos

- Java 17
- Maven 3.8/3.9
- MySQL Server 8/9 + MySQL Workbench

## Base de datos

Ejecuta en MySQL Workbench el siguiente script:

```sql
CREATE DATABASE IF NOT EXISTS devjobs_aa2;
USE devjobs_aa2;

CREATE TABLE companies (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL
);

CREATE TABLE job_offers (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(120) NOT NULL,
  description TEXT,
  min_salary DECIMAL(10,2),
  max_salary DECIMAL(10,2),
  location VARCHAR(80),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  company_id BIGINT NOT NULL,
  CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES companies(id)
);

INSERT INTO companies(name) VALUES ('Acme'), ('Globant');
```

## Configuración

Archivo `src/main/resources/db.properties`:

```
DB_URL=jdbc:mysql://localhost:3306/devjobs_aa2?useSSL=false&serverTimezone=UTC
DB_USER=root
DB_PASSWORD=TU_PASSWORD
```

## Compilar y ejecutar

```bash
mvn clean compile
mvn exec:java
```

## Uso (menú de consola)

El menú de consola en `App.java` permite probar el CRUD:

1. Listar ofertas (paginado)
2. Crear oferta
3. Buscar por ID
4. Actualizar título
5. Eliminar por ID
6. Salir

## Estructura del proyecto

```
src/main/java/com/devjobs/
  App.java
  config/ConnectionFactory.java
  domain/JobOffer.java
  dao/JobOfferDao.java
  dao/jdbc/JdbcJobOfferDao.java
src/main/resources/db.properties
pom.xml
```

## Estándar de codificación

- Paquetes: `com.devjobs.*`
- Clases/Interfaces: `PascalCase` (ejemplo: `JobOffer`, `JobOfferDao`)
- Métodos/variables: `camelCase` (ejemplo: `findById`, `minSalary`)
- Constantes: `UPPER_SNAKE_CASE`
- Commits: Conventional Commits (`feat`, `fix`, `docs`, `chore`)

## Versionamiento (Git)

Flujo simple con ramas y Pull Requests:

- `master` (rama principal estable)
- `feature/connection`, `feature/crud-joboffer`, `feature/menu-console`
- `docs/readme` (documentación)

Comandos de evidencia:

```bash
git log --oneline --graph --decorate --all
git branch -a
git remote -v
```

## Licencia

Este proyecto está bajo licencia MIT. Ver [LICENSE](./LICENSE).
