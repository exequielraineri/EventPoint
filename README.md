# EventPoint

_Aplicación Android - Final Laboratorio VI - Exequiel Raineri_

[Diseño Figma](https://www.figma.com/design/fDKiU0nGbbIezb89pT04ii/EventPoint?node-id=0-1&t=X8PrBNWhehJmYGIK-1)


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

# Modelado de datos

## Entidades

### User

- **ID**: Identificador único del usuario.
- **firstName**: Nombre del usuario.
- **lastName**: Apellido del usuario.
- **email**: Correo electrónico del usuario.
- **password**: Contraseña del usuario.
- **role**: Rol del usuario (Administrador o Usuario).
- **location_id**: Identificador de la ubicacion del usuario.
- **active**: Usuario activo o inactivo.
- **created_at**: Fecha de creación del usuario.
- **updated_at**: Fecha de ultima actualizacación del usuario.
- **reset_password_token**: Token para recuperar contraseña.

### Event

- **ID**: Identificador único del evento.
- **title**: Título del evento.
- **description**: Descripción del evento.
- **capacity**: Capacidad del evento.
- **status**: Estado del evento(ACTIVO, CANCELADO, FINALIZADO).
- **start_date**: Fecha y hora de inicio del evento.
- **end_date**: Fecha y hora de fin del evento.
- **max_tickets**: Cantidad maxima de entradas disponibles.
- **image_url**: Url para la imagen del evento.
- **base_price**: Precio base del evento.
- **category_id**: Identificador de la categoria del evento.
- **location_id**: Identificador de la ubicación del evento.
- **organizer_id**: Identificador del usuario que organiza el evento.
- **active**: Evento activo o inactivo.
- **created_at**: Fecha de creación del evento.
- **updated_at**: Fecha de última actualización del evento.

### Category

- **ID**: Identificador de categoria.
- **name**: Nombre de la categoria.
- **description**: Descripción de la categoria.
- **active**: Categoria activa o inactiva.

### Location

- **ID**: Identificador de ubicación.
- **latitude**: Latitud.
- **longitude**: Longitud.
- **address**: Dirección física.
- **postal_code**: Código postal.

### Notification

- **ID**: Identificador único de la notificación.
- **message**: Contenido de la notificación.
- **user_id**: Identificador del usuario relacionado.
- **event_id**: Identificador del evento relacionado.
- **created_at**: Fecha de creación.

### Ticket

- **ID**: Identificador único de la entrada.
- **user_id**: Identificador del usuario que compró la entrada.
- **event_id**: Identificador del evento para el cual es la entrada.
- **price**: Precio de la entrada.
- **ticket_type**: Tipo de entrada(GENERAL, VIP, OTRO).
- **status**: Estado de la entrada(RESERVADA, COMPRADA, CANCELADA).
- **purchase_data**: Fecha de compra.
- **created_at**: Fecha de creación.
- **updated_at**: Fecha de última actualización.

### Payments

- **ID**: Identificador único del pago.
- **ticket_id**: Identificador del ticket asociado.
- **method**: Metodo de pago.
- **transaction_id**: Identificador único de la transacción.
- **status**: Estado del pago(PENDIENTE, APROBADO, CANCELADO, RECHAZADO).
- **amount**: Monto del pago.
- **created_at**: Fecha de creación.
- **updated_at**: Fecha de última actualización.
- **provider_response**: Cuerpo de la respuesta de pasarela de pago.

## Relaciones entre Entidades

### Usuario(user)

- Un **Usuario** puede tener múltiples **Entradas** (1:N).
- Un **Usuario** puede organizar múltiples **Eventos** (1:N).
- Un **Usuario** tiene una **Ubicacion** (1:1).
- Un **Usuario** puede recibir múltiples **Notificaciones** (1:N).
- Un **Usuario** puede estar interesado en múltiples **Categorias** y una **Categoria** puede tener múltiples **Usuarios** (N:M).

### Evento(event)

- Un **Evento** puede tener múltiples **Entradas** (1:N).
- Un **Evento** es organizado por un **Usuario** (M:1).
- Un **Evento** pertenece a una **Categoria** (M:1).
- Un **Evento** tiene una **Ubicacion** (M:1).

### Entrada(ticket)

- Una **Entrada** pertenece a un **Usuario** (M:1).
- Una **Entrada** tiene un **Pago** (1:1).
- Una **Entrada** pertenece a un **Evento** (M:1).

### Pago(payment)

- Un **Pago** está asociado a una **Entrada** (1:1).

### Categoria(category)

- Una **Categoria** puede tener múltiples **Eventos** (1:M).

### Notificacion(notification)

- Una **Notificacion** pertenece a un **Usuario** (M:1).

### Ubicacion

- Una **Ubicacion** está asociada a un **Usuario** (1:1).
- Una **Ubicacion** puede tener múltiples **Eventos** (1:M).
