# Acceso Y Control de Clientes

Proyecto en desarrollo, enfocado en aplicar buenas prácticas de codificación (DRY, SoC, etc.).
Este proyecto está centrado en la autenticación y autorización de usuarios, priorizando la gestión de usuarios y roles, con el objetivo de crear una base reutilizable y adaptable para diferentes aplicaciones.

## Tabla de Contenido 
- [Descripción](#descripción)
- [Características](#características)
- [Tecnologías](#tecnologías)
- [Gestión de Seguridad](#gestióndeseguridad)
- [Flujo de Usuario](#flujodeusuario)


## Descripción
Este proyecto nació como una exploración de Spring Security y se transformó en una base sólida y adaptable para la gestión de usuarios y roles. Se priorizó mantener el código organizado, limpio y desacoplado para facilitar su mantenimiento y extensión.
El objetivo es que cualquier desarrollador pueda integrarlo fácilmente en otro sistema sin necesidad de una reestructuración significativa.

## Características
1. Autenticación de usuarios con manejo de sesiones.
2. Registro y login seguros.
3. Gestión de roles por parte de usuarios con permisos de administrador.
4. Diseño desacoplado usando DTOs y capa de servicio.
5. Actualización dinámica de la sesión del usuario si se cambia su nombre de usuario o roles.
6. Contraseñas encriptadas con BCrypt.
7. Patrón MVC y buenas prácticas de arquitectura.

## Tecnologías
- Java 21
- Spring Boot
- Thymeleaf
- Maven
- Spring Security
- Lombok
- Mapper (para mapeo DTO/Entidad)
- MySQL server.
- Hibernate/JPA

## Gestión de Seguridad
- Los roles y permisos se gestionan a través de una base de datos relacional (MySQL).
- Las contraseñas son codificadas con BCrypt.
- Se utiliza el patrón DTO para evitar exponer entidades directamente a la vista.
- Al modificar el usuario autenticado actual (por ejemplo, cambiar su nombre o rol), la sesión se refresca automáticamente, excepto si se intenta quitar el rol de ADMIN a sí mismo, lo cual está prohibido por seguridad.

## Flujo de Seguridad
1. El usuario puede registrarse mediante un formulario.
2. Al registrarse, se le asigna automáticamente el rol básico USER.
3. Un usuario con el rol ADMIN puede:
- Agregar o quitar roles a otros usuarios.
- Eliminar roles excepto el rol ADMIN a sí mismo.
4. Se permite iniciar y cerrar sesión con credenciales válidas.
5. La interfaz se adapta según el rol del usuario autenticado.

<!--## Instalación
### Clonar el repositorio
git clone 

### Entrar en el proyecto
cd 

### Construir el proyecto
./mvnw clean install

### Ejecutar
./mvnw spring-boot:run
-->
