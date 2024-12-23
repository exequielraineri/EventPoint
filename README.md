# EventPoint

*Aplicación Android - Final Laboratorio VI - Exequiel Raineri*

### Descripción
**EventPoint** es una aplicación móvil diseñada para facilitar la búsqueda de eventos cercanos al usuario.

La aplicación muestra eventos locales, permitiendo a los usuarios:
- Encontrar eventos cercanos.
- Adquirir entradas de manera sencilla.
- Recibir notificaciones sobre categorias de interés.

## Tecnologías a usar
- **Backend**
  - Java y Spring
- **Frontend**
  - Android  


## Roles
 - Administrador
 - Usuario


# Definición de Requerimientos
## Requerimientos funcionales
- **Autenticación y registro**
  - El sistema debe permitir a los usuarios registrarse utilizando correo y contraseña.
  - El sistema debe permitir a los usuarios recuperar la contraseña olvidada.
- **Gestión de Eventos**
  - El sistema debe permitir a los usuarios visualizar los eventos y filtrarlos por categoría, distancia, precio y fecha.
  - El sistema debe permitir a los administradores crear, editar o eliminar eventos.
  - El sistema debe permitir visualizar eventos sin necesidad de autenticación.
- **Geolocalización**
  - El sistema debe poder obtener la ubicación actual del usuario y mostrar eventos cercanos.
  - El sistema debe poder mostrar los eventos en un mapa interactivo.
- **Entradas**
  - El sistema debe permitir a los usuarios obtener una entrada y realizar pagos mediante pasarela de pago(Mercado Pago).
  - El sistema debe permitir al usuario ver las entradas que compró.
  - El sistema debe poder generar un código QR para cada entrada.
- **Notificaciones**
  - El sistema debe poder enviar notificaciones push sobre eventos cercanos del interés del usuario (ej., conciertos, eventos deportivos).
  - El sistema debe poder enviar notificaciones al usuario sobre nuevos eventos del interés del usuario.
  - El sistema debe poder enviar notificaciones al usuario, cuando un evento se modifique.

 ## Requerimientos no funcionales
 - **Rendimiento**
   - La aplicacion deber responder en menos de 2 segundos para la mayoria de solicitudes.
 - **Usabilidad**
   - La interfaz debe ser lo suficiente intuitiva para el usuario.
 - **Seguridad**
   - Las contraseñas deben ser encriptadas.
   - Se debe validar token de acceso.
