# Documentación de Endpoints: Ubicaciones

## Obtener todas las ubicaciones

### Descripción

Este endpoint permite obtener una lista paginada de todas las ubicaciones, con la opción de filtrar los resultados por
el nombre de la ubicación (`nombreUbicacion`).

### Método HTTP

`GET`

### URL

`/ubicaciones`

### Parámetros de Entrada

| Nombre            | Tipo       | Obligatorio | Descripción                                                   |
|-------------------|------------|-------------|---------------------------------------------------------------|
| `nombreUbicacion` | `String`   | No          | Nombre de la ubicación para filtrar los resultados.           |
| `pageable`        | `Pageable` | No          | Información de paginación, como el número de página y tamaño. |

### Respuestas

| Código de Estado  | Descripción                                                                            |
|-------------------|----------------------------------------------------------------------------------------|
| `200 OK`          | Devuelve una página de ubicaciones, filtradas por `nombreUbicacion` si se proporciona. |
| `400 Bad Request` | La solicitud no es válida, probablemente debido a parámetros incorrectos.              |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 1,
      "nombre": "Ubicación A",
      "descripcion": "Descripción de la Ubicación A"
    },
    {
      "id": 2,
      "nombre": "Ubicación B",
      "descripcion": "Descripción de la Ubicación B"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalPages": 1,
  "totalElements": 2
}
```

---

## Obtener una ubicación por su ID

### Descripción

Este endpoint permite obtener una ubicación específica utilizando su identificador único (`id`). Si la ubicación no se
encuentra, se devuelve un error `404 Not Found`.

### Método HTTP

`GET`

### URL

`/ubicaciones/{id}`

### Parámetros de Entrada

| Nombre | Tipo   | Obligatorio | Descripción                                                 |
|--------|--------|-------------|-------------------------------------------------------------|
| `id`   | `Long` | Sí          | Identificador único de la ubicación que se desea consultar. |

### Respuestas

| Código de Estado | Descripción                                                  |
|------------------|--------------------------------------------------------------|
| `200 OK`         | Devuelve la ubicación correspondiente al `id` proporcionado. |
| `404 Not Found`  | No se encontró la ubicación con el `id` proporcionado.       |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "id": 1,
  "nombre": "Ubicación A",
  "descripcion": "Descripción de la Ubicación A"
}
```

---

## Crear una ubicación

### Descripción

Este endpoint permite crear una nueva ubicación proporcionando los datos necesarios en un objeto `UbicacionCreateDTO`.
Si la solicitud es exitosa, se devuelve la ubicación creada; si ocurre un error, se devuelve un estado
`400 Bad Request`.

### Método HTTP

`POST`

### URL

`/ubicaciones`

### Parámetros de Entrada

| Nombre               | Tipo                 | Obligatorio | Descripción                                                       |
|----------------------|----------------------|-------------|-------------------------------------------------------------------|
| `ubicacionCreateDTO` | `UbicacionCreateDTO` | Sí          | Objeto que contiene los datos necesarios para crear la ubicación. |

#### `UbicacionCreateDTO` (Ejemplo de estructura)

```json
{
  "nombre": "Ubicación Nueva",
  "descripcion": "Descripción de la nueva ubicación"
}
```

---

## Actualizar una ubicación

### Descripción

Este endpoint permite actualizar una ubicación existente mediante su identificador único (`id`) y un objeto
`UbicacionUpdateDTO` con los nuevos datos. Si la ubicación no se encuentra, se devuelve un error `404 Not Found`. Si la
actualización es exitosa, se devuelve la ubicación actualizada.

### Método HTTP

`PUT`

### URL

`/ubicaciones/{id}`

### Parámetros de Entrada

| Nombre               | Tipo                 | Obligatorio | Descripción                                                        |
|----------------------|----------------------|-------------|--------------------------------------------------------------------|
| `id`                 | `Long`               | Sí          | Identificador único de la ubicación que se desea actualizar.       |
| `ubicacionUpdateDTO` | `UbicacionUpdateDTO` | Sí          | Objeto que contiene los nuevos datos para actualizar la ubicación. |

#### `UbicacionUpdateDTO` (Ejemplo de estructura)

```json
{
  "nombre": "Ubicación Actualizada",
  "descripcion": "Descripción actualizada de la ubicación"
}
```

---

## Eliminar una ubicación

### Descripción

Este endpoint permite eliminar una ubicación específica mediante su identificador único (`id`). Si la ubicación no se
encuentra, se devuelve un error `404 Not Found`. Si la eliminación es exitosa, se devuelve una respuesta sin contenido (
`204 No Content`).

### Método HTTP

`DELETE`

### URL

`/ubicaciones/{id}`

### Parámetros de Entrada

| Nombre | Tipo   | Obligatorio | Descripción                                                |
|--------|--------|-------------|------------------------------------------------------------|
| `id`   | `Long` | Sí          | Identificador único de la ubicación que se desea eliminar. |

### Respuestas

| Código de Estado | Descripción                                            |
|------------------|--------------------------------------------------------|
| `204 No Content` | La ubicación fue eliminada exitosamente.               |
| `404 Not Found`  | No se encontró la ubicación con el `id` proporcionado. |

### Ejemplo de Respuesta Exitosa (204 No Content)

No hay contenido en la respuesta, pero el estado de la respuesta es `204 No Content`.

### Ejemplo de Respuesta de Error (404 Not Found)

```json
{
  "error": "No se encontró la ubicación con el ID proporcionado."
}
```
