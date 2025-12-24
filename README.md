# Informe Técnico -- API Usuarios con Spring Boot + JWT

## 1. Objetivo del Proyecto

El objetivo del proyecto es construir una API REST segura para la gestión de usuarios utilizando Spring Boot, PostgreSQL y autenticación basada en JSON Web Tokens (JWT).

La API permite registrar usuarios, autenticarlos mediante login y proteger endpoints utilizando filtros de seguridad y validación de tokens.

## 2. Tecnologías Utilizadas

- Java 21

- Spring Boot 4.x

- Spring Web MVC

- Spring Security

- Spring Data JPA

- PostgreSQL

- JWT (io.jsonwebtoken)

- Maven

- Lombok

## 3. Arquitectura General

La API sigue una arquitectura en capas:

- Controller: recibe solicitudes HTTP

- Service: contiene la lógica de negocio

- Repository: comunicación con la base de datos

- Security: configuración de autenticación JWT

- DTOs: transporte de datos

- Exception Handling: manejo centralizado de errores

## 4. Flujo de Autenticación JWT

1. El cliente envía credenciales al endpoint /api/v1/auth/login

2. El sistema valida usuario contra la base de datos

3. Si es válido, JwtService genera un token firmado

4. El cliente guarda el token

5. Para acceder a recursos protegidos, envía:

Authorization: Bearer \<token\>

6. El filtro JwtAuthenticationFilter intercepta la solicitud

7. Valida token → extrae email → crea autenticación

8. Spring Security autoriza acceso

## 5. Componentes de Seguridad Implementados

JwtProperties: almacena secret y expiración.

JwtService: genera, valida y extrae claims del token.

JwtAuthenticationFilter: intercepta requests y valida token.

SecurityConfig: define reglas de seguridad, deshabilita sesión y agrega filtro JWT.

## 6. Endpoints Principales

- POST /api/v1/auth/login -> Login + devuelve JWT

- GET /secure/test -> Requiere token

- CRUD Usuarios (/api/v1/usuarios)

## 7. Manejo de Errores

Se utiliza GlobalExceptionHandler para devolver respuestas consistentes en formato JSON ante excepciones como:

- ResourceNotFoundException

- BusinessException

- Errores de validación

## 8. Estado Actual del Proyecto

- API funcional

- Seguridad JWT operativa

- Protección de endpoints confirmada mediante Postman

- Aplicación levanta correctamente sin errores de beans


# Documentación Complementaria


## A) Diagrama Arquitectura Cliente-Servidor

El sistema sigue una arquitectura cliente-servidor.
- El CLIENTE (Postman / Frontend futuro) envía solicitudes HTTP.
- El SERVIDOR Spring Boot recibe la solicitud a través del Controller.
- El Controller delega en Services para la lógica de negocio.
- Los Services interactúan con el Repositorio JPA.
- El Repositorio consulta o persiste datos en PostgreSQL.
- En endpoints protegidos, interviene primero el filtro JWT.

Flujo simplificado:
CLIENTE -> Controller -> Service -> Repository -> Base de Datos -> Respuesta al Cliente

## B) Diagrama Flujo Autenticación JWT

1) El cliente envía credenciales a /api/v1/auth/login
2) El sistema valida usuario + contraseña
3) Si es correcto, se genera un token JWT firmado
4) El cliente usa el token en Authorization: Bearer TOKEN
5) Cada petición protegida pasa primero por JwtAuthenticationFilter
6) El filtro valida token con JwtService
7) Si es válido, se autoriza el acceso; si no, se rechaza

## C) UML Básico

<img width="707" height="542" alt="Captura de pantalla 2025-12-24 a la(s) 16 00 46" src="https://github.com/user-attachments/assets/0a4de20b-8308-424a-8fa1-3fcccffb320f" />

## D) Diagrama de secuencia (Autenticación y requests)

<img width="1032" height="654" alt="Captura de pantalla 2025-12-24 a la(s) 16 02 13" src="https://github.com/user-attachments/assets/bfd55289-aeab-469d-8b30-f5ebe66424de" />







