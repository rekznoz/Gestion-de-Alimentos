# Documentación de Endpoints: Inventarios

## Obtener todos los inventarios

### Descripción

Este endpoint permite obtener una lista paginada de todos los inventarios. También ofrece la opción de filtrar los
resultados por el identificador de un usuario (`usuarioId`).

### Método HTTP

`GET`

### URL

`/inventarios`

### Parámetros de Entrada

| Nombre      | Tipo       | Obligatorio | Descripción                                                         |
|-------------|------------|-------------|---------------------------------------------------------------------|
| `usuarioId` | `Long`     | No          | Identificador del usuario para filtrar los inventarios por usuario. |
| `pageable`  | `Pageable` | No          | Información de paginación, como el número de página y tamaño.       |

### Respuestas

| Código de Estado  | Descripción                                                                     |
|-------------------|---------------------------------------------------------------------------------|
| `200 OK`          | Devuelve una página de inventarios, filtrada por `usuarioId` si se proporciona. |
| `400 Bad Request` | La solicitud no es válida, probablemente debido a parámetros incorrectos.       |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 1,
      "usuarioId": 2,
      "nombre": "Inventario A",
      "descripcion": "Descripción del Inventario A"
    },
    {
      "id": 2,
      "usuarioId": 2,
      "nombre": "Inventario B",
      "descripcion": "Descripción del Inventario B"
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

## Obtener un inventario por su ID

### Descripción

Este endpoint permite obtener un inventario específico utilizando su identificador único (`id`). Si el inventario no se
encuentra, se devuelve un error `404 Not Found`.

### Método HTTP

`GET`

### URL

`/inventarios/{id}`

### Parámetros de Entrada

| Nombre | Tipo   | Obligatorio | Descripción                                                |
|--------|--------|-------------|------------------------------------------------------------|
| `id`   | `Long` | Sí          | Identificador único del inventario que se desea consultar. |

### Respuestas

| Código de Estado | Descripción                                                   |
|------------------|---------------------------------------------------------------|
| `200 OK`         | Devuelve el inventario correspondiente al `id` proporcionado. |
| `404 Not Found`  | No se encontró un inventario con el `id` proporcionado.       |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "id": 1,
  "usuarioId": 2,
  "nombre": "Inventario A",
  "descripcion": "Descripción del Inventario A"
}
```

---

## Crear un inventario

### Descripción

Este endpoint permite crear un nuevo inventario proporcionando los datos necesarios a través de un objeto
`InventarioCreateDTO`.

### Método HTTP

`POST`

### URL

`/inventarios`

### Parámetros de Entrada

| Nombre      | Tipo                  | Obligatorio | Descripción                                                        |
|-------------|-----------------------|-------------|--------------------------------------------------------------------|
| `createDTO` | `InventarioCreateDTO` | Sí          | Objeto que contiene los datos necesarios para crear un inventario. |

#### `InventarioCreateDTO` (Ejemplo de estructura)

```json
{
  "usuarioId": 2,
  "nombre": "Inventario Nuevo",
  "descripcion": "Descripción del Inventario Nuevo"
}
```

---

## Actualizar un inventario

### Descripción

Este endpoint permite actualizar un inventario existente utilizando su identificador único (`id`) y un objeto
`InventarioUpdateDTO` con los datos a modificar.

### Método HTTP

`PUT`

### URL

`/inventarios/{id}`

### Parámetros de Entrada

| Nombre      | Tipo                  | Obligatorio | Descripción                                                 |
|-------------|-----------------------|-------------|-------------------------------------------------------------|
| `id`        | `Long`                | Sí          | Identificador único del inventario que se desea actualizar. |
| `updateDTO` | `InventarioUpdateDTO` | Sí          | Objeto que contiene los datos a actualizar del inventario.  |

#### `InventarioUpdateDTO` (Ejemplo de estructura)

```json
{
  "nombre": "Inventario Actualizado",
  "descripcion": "Descripción actualizada del Inventario"
}
```

---

## Eliminar un inventario

### Descripción

Este endpoint permite eliminar un inventario existente proporcionando su identificador único (`id`). Si el inventario no
se encuentra, se devuelve un error `404 Not Found`.

### Método HTTP

`DELETE`

### URL

`/inventarios/{id}`

### Parámetros de Entrada

| Nombre | Tipo   | Obligatorio | Descripción                                               |
|--------|--------|-------------|-----------------------------------------------------------|
| `id`   | `Long` | Sí          | Identificador único del inventario que se desea eliminar. |

### Respuestas

| Código de Estado | Descripción                                             |
|------------------|---------------------------------------------------------|
| `204 No Content` | El inventario se eliminó exitosamente.                  |
| `404 Not Found`  | No se encontró el inventario con el `id` proporcionado. |

### Ejemplo de Respuesta Exitosa (204 No Content)

```json
{
  "message": "El inventario se eliminó exitosamente."
}
```