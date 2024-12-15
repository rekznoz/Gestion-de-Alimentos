# Documentación de Endpoints: Usuarios

## Obtener todos los usuarios

### Descripción

Este endpoint permite obtener una lista de todos los usuarios registrados. Se puede filtrar la lista por el nombre de
usuario (`username`). Si no se proporciona un nombre de usuario, se devuelve la lista completa de usuarios.

### Método HTTP

`GET`

### URL

`/usuarios`

### Parámetros de Entrada

| Nombre     | Tipo       | Obligatorio | Descripción                                                                                                |
|------------|------------|-------------|------------------------------------------------------------------------------------------------------------|
| `username` | `String`   | No          | Nombre de usuario para filtrar los resultados. Si no se proporciona, se devuelve todos los usuarios.       |
| `pageable` | `Pageable` | No          | Parámetros de paginación (como `page`, `size`, etc.) para controlar la paginación de la lista de usuarios. |

### Respuestas

| Código de Estado  | Descripción                                                               |
|-------------------|---------------------------------------------------------------------------|
| `200 OK`          | Devuelve la lista de usuarios, ya sea filtrada o completa.                |
| `400 Bad Request` | La solicitud es incorrecta, posiblemente debido a un error de parámetros. |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 1,
      "username": "usuario1"
    },
    {
      "id": 2,
      "username": "usuario2"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "totalElements": 50
  }
}
```

---

## Obtener un usuario por su ID

### Descripción

Este endpoint permite obtener un usuario específico mediante su identificador único (`id`). Si el usuario no existe, se
devuelve un error `404 Not Found`.

### Método HTTP

`GET`

### URL

`/usuarios/{id}`

### Parámetros de Entrada

| Nombre | Tipo   | Obligatorio | Descripción                                           |
|--------|--------|-------------|-------------------------------------------------------|
| `id`   | `Long` | Sí          | Identificador único del usuario que se desea obtener. |

### Respuestas

| Código de Estado | Descripción                                          |
|------------------|------------------------------------------------------|
| `200 OK`         | Devuelve el usuario con el `id` especificado.        |
| `404 Not Found`  | No se encontró el usuario con el `id` proporcionado. |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "id": 1,
  "username": "usuario1"
}
```

---

## Crear un usuario

### Descripción

Este endpoint permite crear un nuevo usuario en el sistema utilizando los datos proporcionados en el cuerpo de la
solicitud. Si la creación es exitosa, devuelve el usuario creado. Si ocurre un error, se devuelve un error
`400 Bad Request`.

### Método HTTP

`POST`

### URL

`/usuarios`

### Parámetros de Entrada

| Nombre             | Tipo               | Obligatorio | Descripción                                                                                                                |
|--------------------|--------------------|-------------|----------------------------------------------------------------------------------------------------------------------------|
| `usuarioCreateDTO` | `UsuarioCreateDTO` | Sí          | Objeto que contiene los datos necesarios para crear un nuevo usuario, como nombre, correo electrónico y nombre de usuario. |

### Respuestas

| Código de Estado  | Descripción                                                    |
|-------------------|----------------------------------------------------------------|
| `200 OK`          | El usuario fue creado exitosamente.                            |
| `400 Bad Request` | Hubo un error en la solicitud (por ejemplo, datos no válidos). |

### Ejemplo de Cuerpo de Solicitud

```json
{
  "username": "nuevoUsuario",
  "nombre": "Carlos Sánchez",
  "email": "carlos.sanchez@example.com",
  "password": "password123"
}
```

---

## Crear un usuario

### Descripción

Este endpoint permite crear un nuevo usuario en el sistema utilizando los datos proporcionados en el cuerpo de la
solicitud. Si la creación es exitosa, devuelve el usuario creado. Si ocurre un error, se devuelve un error
`400 Bad Request`.

### Método HTTP

`POST`

### URL

`/usuarios`

### Parámetros de Entrada

| Nombre             | Tipo               | Obligatorio | Descripción                                                                                                                |
|--------------------|--------------------|-------------|----------------------------------------------------------------------------------------------------------------------------|
| `usuarioCreateDTO` | `UsuarioCreateDTO` | Sí          | Objeto que contiene los datos necesarios para crear un nuevo usuario, como nombre, correo electrónico y nombre de usuario. |

### Respuestas

| Código de Estado  | Descripción                                                    |
|-------------------|----------------------------------------------------------------|
| `200 OK`          | El usuario fue creado exitosamente.                            |
| `400 Bad Request` | Hubo un error en la solicitud (por ejemplo, datos no válidos). |

### Ejemplo de Cuerpo de Solicitud

```json
{
  "username": "nuevoUsuario",
  "password": "password123"
}
```

---

## Actualizar un usuario

### Descripción

Este endpoint permite actualizar los detalles de un usuario existente utilizando su identificador único (`id`). Si el
usuario es encontrado y la actualización es exitosa, se devuelve el usuario actualizado. Si el usuario no es encontrado,
se devuelve un error `404 Not Found`.

### Método HTTP

`PUT`

### URL

`/usuarios/{id}`

### Parámetros de Entrada

| Nombre             | Tipo               | Obligatorio | Descripción                                                                                          |
|--------------------|--------------------|-------------|------------------------------------------------------------------------------------------------------|
| `id`               | `Long`             | Sí          | Identificador único del usuario que se desea actualizar.                                             |
| `usuarioUpdateDTO` | `UsuarioUpdateDTO` | Sí          | Objeto que contiene los nuevos datos del usuario a actualizar, como nombre, correo electrónico, etc. |

### Respuestas

| Código de Estado | Descripción                                          |
|------------------|------------------------------------------------------|
| `200 OK`         | El usuario fue actualizado correctamente.            |
| `404 Not Found`  | No se encontró un usuario con el `id` proporcionado. |

### Ejemplo de Cuerpo de Solicitud

```json
{
  "username": "usuarioActualizado",
  "password": "nuevoPassword123"
}
```

---

## Eliminar un usuario

### Descripción

Este endpoint permite eliminar un usuario existente mediante su identificador único (`id`). Si el usuario es encontrado
y eliminado correctamente, se devuelve una respuesta con estado `204 No Content`. Si el usuario no se encuentra, se
devuelve un error `404 Not Found`.

### Método HTTP

`DELETE`

### URL

`/usuarios/{id}`

### Parámetros de Entrada

| Nombre | Tipo   | Obligatorio | Descripción                                            |
|--------|--------|-------------|--------------------------------------------------------|
| `id`   | `Long` | Sí          | Identificador único del usuario que se desea eliminar. |

### Respuestas

| Código de Estado | Descripción                                          |
|------------------|------------------------------------------------------|
| `204 No Content` | El usuario fue eliminado correctamente.              |
| `404 Not Found`  | No se encontró un usuario con el `id` proporcionado. |

### Ejemplo de Respuesta Exitosa (204 No Content)

```json
{
  "message": "El usuario fue eliminado correctamente."
}
```