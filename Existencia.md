# Documentación de Endpoints: Existencias

## Obtener todas las existencias

### Descripción

Este endpoint devuelve una lista paginada de todas las existencias, con la posibilidad de filtrar por el `alimento` y/o
la `ubicación`. Si no se proporcionan filtros, se devolverán todas las existencias disponibles.

### Método HTTP

`GET`

### URL

`/existencias`

### Parámetros de Entrada

| Nombre      | Tipo       | Obligatorio | Descripción                                                               |
|-------------|------------|-------------|---------------------------------------------------------------------------|
| `alimento`  | `Long`     | No          | Identificador del alimento para filtrar las existencias por alimento.     |
| `ubicacion` | `Long`     | No          | Identificador de la ubicación para filtrar las existencias por ubicación. |
| `pageable`  | `Pageable` | No          | Información de paginación, como número de página y tamaño.                |

### Respuestas

| Código de Estado  | Descripción                                                                                        |
|-------------------|----------------------------------------------------------------------------------------------------|
| `200 OK`          | Devuelve una página de existencias filtradas o todas las existencias si no se especifican filtros. |
| `400 Bad Request` | La solicitud no es válida, probablemente debido a datos incorrectos.                               |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 1,
      "alimentoId": 2,
      "ubicacionId": 3,
      "cantidad": 50
    },
    {
      "id": 2,
      "alimentoId": 4,
      "ubicacionId": 3,
      "cantidad": 30
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

## Obtener una existencia por su ID

### Descripción

Este endpoint devuelve una existencia específica utilizando su identificador único (`id`). Si la existencia no se
encuentra, se devuelve un error `404 Not Found`.

### Método HTTP

`GET`

### URL

`/existencias/{id}`

### Parámetros de Entrada

| Nombre | Tipo   | Obligatorio | Descripción                                                  |
|--------|--------|-------------|--------------------------------------------------------------|
| `id`   | `Long` | Sí          | Identificador único de la existencia que se desea consultar. |

### Respuestas

| Código de Estado | Descripción                                                   |
|------------------|---------------------------------------------------------------|
| `200 OK`         | Devuelve la existencia correspondiente al `id` proporcionado. |
| `404 Not Found`  | No se encontró una existencia con el `id` proporcionado.      |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "id": 1,
  "alimentoId": 2,
  "ubicacionId": 3,
  "cantidad": 50
}
```

---

## Crear una existencia

### Descripción

Este endpoint permite crear una nueva existencia en el sistema, proporcionando los datos necesarios a través de un
objeto `ExistenciaCreateDTO`.

### Método HTTP

`POST`

### URL

`/existencias`

### Parámetros de Entrada

| Nombre      | Tipo                  | Obligatorio | Descripción                                                        |
|-------------|-----------------------|-------------|--------------------------------------------------------------------|
| `createDTO` | `ExistenciaCreateDTO` | Sí          | Objeto que contiene los datos necesarios para crear la existencia. |

#### `ExistenciaCreateDTO` (Ejemplo de estructura)

```json
{
  "alimentoId": 2,
  "ubicacionId": 3,
  "cantidad": 100
}
```

---

## Actualizar una existencia

### Descripción

Este endpoint permite actualizar los detalles de una existencia existente en el sistema. Para ello, se proporciona el
identificador de la existencia (`id`) y los nuevos datos a través de un objeto `ExistenciaUpdateDTO`.

### Método HTTP

`PUT`

### URL

`/existencias/{id}`

### Parámetros de Entrada

| Nombre      | Tipo                  | Obligatorio | Descripción                                                         |
|-------------|-----------------------|-------------|---------------------------------------------------------------------|
| `id`        | `Long`                | Sí          | Identificador único de la existencia que se desea actualizar.       |
| `updateDTO` | `ExistenciaUpdateDTO` | Sí          | Objeto que contiene los nuevos datos para actualizar la existencia. |

#### `ExistenciaUpdateDTO` (Ejemplo de estructura)

```json
{
  "alimentoId": 2,
  "ubicacionId": 3,
  "cantidad": 150
}
```

---

## Eliminar una existencia

### Descripción

Este endpoint permite eliminar una existencia específica en el sistema mediante su identificador único (`id`).

### Método HTTP

`DELETE`

### URL

`/existencias/{id}`

### Parámetros de Entrada

| Nombre | Tipo   | Obligatorio | Descripción                                                 |
|--------|--------|-------------|-------------------------------------------------------------|
| `id`   | `Long` | Sí          | Identificador único de la existencia que se desea eliminar. |

### Respuestas

| Código de Estado | Descripción                                              |
|------------------|----------------------------------------------------------|
| `204 No Content` | La existencia fue eliminada correctamente.               |
| `404 Not Found`  | No se encontró una existencia con el `id` proporcionado. |

### Ejemplo de Respuesta Exitosa (204 No Content)

```json
{
  "message": "La existencia fue eliminada correctamente."
}
```

---

## Obtener todas las existencias ordenadas por fecha de entrada

### Descripción

Este endpoint permite obtener una lista paginada de todas las existencias, ordenadas de manera ascendente por la fecha
de entrada.

### Método HTTP

`GET`

### URL

`/existencias/fecha`

### Parámetros de Entrada

| Nombre     | Tipo       | Obligatorio | Descripción                                                   |
|------------|------------|-------------|---------------------------------------------------------------|
| `pageable` | `Pageable` | No          | Información de paginación, como el número de página y tamaño. |

### Respuestas

| Código de Estado  | Descripción                                                               |
|-------------------|---------------------------------------------------------------------------|
| `200 OK`          | Devuelve una página de existencias ordenadas por fecha de entrada.        |
| `400 Bad Request` | La solicitud no es válida, probablemente debido a parámetros incorrectos. |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 1,
      "alimentoId": 2,
      "ubicacionId": 3,
      "cantidad": 100,
      "fechaEntrada": "2024-12-01"
    },
    {
      "id": 2,
      "alimentoId": 4,
      "ubicacionId": 3,
      "cantidad": 50,
      "fechaEntrada": "2024-12-02"
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
