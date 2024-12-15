# Documentación de Endpoints: Alimentos

## Obtener todos los alimentos

### Descripción

Este endpoint permite obtener una lista paginada de alimentos. Además, proporciona la capacidad de filtrar los
resultados basándose en ciertos criterios como el nombre, si está abierto, si es perecedero, y su fecha de caducidad.

### Método HTTP

`GET`

### URL

`/alimentos`

### Parámetros de Entrada

| Nombre       | Tipo        | Obligatorio | Descripción                                                         |
|--------------|-------------|-------------|---------------------------------------------------------------------|
| `pageable`   | `Pageable`  | No          | Información de paginación como número de página y tamaño de página. |
| `nombre`     | `String`    | No          | Nombre del alimento para filtrar los resultados.                    |
| `abierto`    | `boolean`   | No          | Indica si el alimento está abierto (`true`) o no (`false`).         |
| `perecedero` | `boolean`   | No          | Indica si el alimento es perecedero (`true`) o no (`false`).        |
| `caducidad`  | `LocalDate` | No          | Fecha de caducidad del alimento para filtrar los resultados.        |

### Respuestas

| Código de Estado  | Descripción                                                                |
|-------------------|----------------------------------------------------------------------------|
| `200 OK`          | Devuelve una página de alimentos (filtrada si se especificaron criterios). |
| `400 Bad Request` | Ocurrió un error en el procesamiento de la solicitud.                      |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 1,
      "nombre": "Manzana",
      "abierto": false,
      "perecedero": true,
      "caducidad": "2024-12-31"
    },
    {
      "id": 2,
      "nombre": "Leche",
      "abierto": true,
      "perecedero": true,
      "caducidad": "2024-12-20"
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

## Obtener un alimento por su ID

### Descripción

Este endpoint permite obtener los detalles de un alimento específico utilizando su identificador único (`id`).

### Método HTTP

`GET`

### URL

`/alimentos/{id}`

### Parámetros de Entrada

| Nombre | Tipo   | Obligatorio | Descripción                       |
|--------|--------|-------------|-----------------------------------|
| `id`   | `Long` | Sí          | Identificador único del alimento. |

### Respuestas

| Código de Estado | Descripción                                                       |
|------------------|-------------------------------------------------------------------|
| `200 OK`         | Devuelve los detalles del alimento correspondiente al ID enviado. |
| `404 Not Found`  | No se encontró un alimento con el ID proporcionado.               |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "id": 1,
  "nombre": "Manzana",
  "abierto": false,
  "perecedero": true,
  "caducidad": "2024-12-31"
}
```

---

## Obtener alimentos próximos a caducar

### Descripción

Este endpoint devuelve una lista paginada de alimentos que están próximos a caducar, ordenados según su fecha de
caducidad.

### Método HTTP

`GET`

### URL

`/alimentos/proximos-a-caducar`

### Parámetros de Entrada

| Nombre     | Tipo       | Obligatorio | Descripción                                                |
|------------|------------|-------------|------------------------------------------------------------|
| `pageable` | `Pageable` | No          | Información de paginación, como número de página y tamaño. |

### Respuestas

| Código de Estado | Descripción                                                    |
|------------------|----------------------------------------------------------------|
| `200 OK`         | Devuelve una página de alimentos que están próximos a caducar. |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 5,
      "nombre": "Yogur",
      "abierto": true,
      "perecedero": true,
      "caducidad": "2024-12-20"
    },
    {
      "id": 8,
      "nombre": "Queso",
      "abierto": false,
      "perecedero": true,
      "caducidad": "2024-12-21"
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

## Obtener alimentos próximos a caducar por inventario

### Descripción

Este endpoint devuelve una lista paginada de alimentos próximos a caducar que pertenecen a un inventario específico,
identificado por su `id`.

### Método HTTP

`GET`

### URL

`/alimentos/proximos-a-caducar/{id}`

### Parámetros de Entrada

| Nombre     | Tipo       | Obligatorio | Descripción                                                |
|------------|------------|-------------|------------------------------------------------------------|
| `id`       | `Long`     | Sí          | Identificador único del inventario.                        |
| `pageable` | `Pageable` | No          | Información de paginación, como número de página y tamaño. |

### Respuestas

| Código de Estado | Descripción                                                                      |
|------------------|----------------------------------------------------------------------------------|
| `200 OK`         | Devuelve una página de alimentos próximos a caducar del inventario especificado. |
| `404 Not Found`  | No se encontró un inventario con el ID proporcionado.                            |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 12,
      "nombre": "Mantequilla",
      "abierto": false,
      "perecedero": true,
      "caducidad": "2024-12-22"
    },
    {
      "id": 14,
      "nombre": "Leche",
      "abierto": true,
      "perecedero": true,
      "caducidad": "2024-12-23"
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

## Obtener alimentos caducados

### Descripción

Este endpoint devuelve una lista paginada de alimentos que han caducado, es decir, aquellos cuya fecha de caducidad ya
ha pasado.

### Método HTTP

`GET`

### URL

`/alimentos/caducados`

### Parámetros de Entrada

| Nombre     | Tipo       | Obligatorio | Descripción                                                |
|------------|------------|-------------|------------------------------------------------------------|
| `pageable` | `Pageable` | No          | Información de paginación, como número de página y tamaño. |

### Respuestas

| Código de Estado | Descripción                                           |
|------------------|-------------------------------------------------------|
| `200 OK`         | Devuelve una página de alimentos que ya han caducado. |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 3,
      "nombre": "Pan",
      "abierto": true,
      "perecedero": true,
      "caducidad": "2024-11-30"
    },
    {
      "id": 7,
      "nombre": "Jugo",
      "abierto": false,
      "perecedero": true,
      "caducidad": "2024-12-01"
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

## Obtener los alimentos más usados

### Descripción

Este endpoint devuelve una lista paginada de los alimentos más usados, basándose en un criterio definido en el servicio
para determinar cuáles son los más utilizados. Este criterio podría estar relacionado con la frecuencia de consumo, la
cantidad de veces que se ha solicitado, entre otros.

### Método HTTP

`GET`

### URL

`/alimentos/mas-usados`

### Parámetros de Entrada

| Nombre     | Tipo       | Obligatorio | Descripción                                                |
|------------|------------|-------------|------------------------------------------------------------|
| `pageable` | `Pageable` | No          | Información de paginación, como número de página y tamaño. |

### Respuestas

| Código de Estado | Descripción                                      |
|------------------|--------------------------------------------------|
| `200 OK`         | Devuelve una página de los alimentos más usados. |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 2,
      "nombre": "Arroz",
      "abierto": false,
      "perecedero": false,
      "caducidad": "2025-01-01"
    },
    {
      "id": 4,
      "nombre": "Pasta",
      "abierto": true,
      "perecedero": false,
      "caducidad": "2024-12-15"
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

## Obtener los alimentos menos usados

### Descripción

Este endpoint devuelve una lista paginada de los alimentos menos usados, basándose en un criterio definido en el
servicio para determinar cuáles son los menos utilizados. Este criterio podría estar relacionado con la frecuencia de
consumo o la cantidad de veces que se ha solicitado.

### Método HTTP

`GET`

### URL

`/alimentos/menos-usados`

### Parámetros de Entrada

| Nombre     | Tipo       | Obligatorio | Descripción                                                |
|------------|------------|-------------|------------------------------------------------------------|
| `pageable` | `Pageable` | No          | Información de paginación, como número de página y tamaño. |

### Respuestas

| Código de Estado | Descripción                                        |
|------------------|----------------------------------------------------|
| `200 OK`         | Devuelve una página de los alimentos menos usados. |

### Ejemplo de Respuesta Exitosa (200 OK)

```json
{
  "content": [
    {
      "id": 9,
      "nombre": "Salchichas",
      "abierto": false,
      "perecedero": true,
      "caducidad": "2024-12-25"
    },
    {
      "id": 11,
      "nombre": "Atún",
      "abierto": true,
      "perecedero": false,
      "caducidad": "2025-03-10"
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

## Crear un alimento

### Descripción

Este endpoint permite crear un nuevo alimento en el sistema, proporcionando la información del alimento a través de un
objeto `AlimentoCreateDTO`.

### Método HTTP

`POST`

### URL

`/alimentos`

### Parámetros de Entrada

| Nombre      | Tipo                | Obligatorio | Descripción                                                      |
|-------------|---------------------|-------------|------------------------------------------------------------------|
| `createDTO` | `AlimentoCreateDTO` | Sí          | Objeto que contiene los datos necesarios para crear el alimento. |

#### `AlimentoCreateDTO` (Ejemplo de estructura)

```json
{
  "nombre": "Pan",
  "abierto": false,
  "perecedero": true,
  "caducidad": "2024-12-20"
}
```

---

## Actualizar un alimento

### Descripción

Este endpoint permite actualizar la información de un alimento existente en el sistema. Se proporciona el `id` del
alimento a actualizar y los nuevos datos a través de un objeto `AlimentoUpdateDTO`.

### Método HTTP

`PUT`

### URL

`/alimentos/{id}`

### Parámetros de Entrada

| Nombre      | Tipo                | Obligatorio | Descripción                                                       |
|-------------|---------------------|-------------|-------------------------------------------------------------------|
| `id`        | `Long`              | Sí          | Identificador único del alimento a actualizar.                    |
| `updateDTO` | `AlimentoUpdateDTO` | Sí          | Objeto que contiene los nuevos datos para actualizar el alimento. |

#### `AlimentoUpdateDTO` (Ejemplo de estructura)

```json
{
  "nombre": "Pan integral",
  "abierto": true,
  "perecedero": true,
  "caducidad": "2025-01-10"
}
```

---

## Eliminar un alimento

### Descripción

Este endpoint permite eliminar un alimento del sistema mediante su identificador único (`id`).

### Método HTTP

`DELETE`

### URL

`/alimentos/{id}`

### Parámetros de Entrada

| Nombre | Tipo   | Obligatorio | Descripción                                             |
|--------|--------|-------------|---------------------------------------------------------|
| `id`   | `Long` | Sí          | Identificador único del alimento que se desea eliminar. |

### Respuestas

| Código de Estado | Descripción                                           |
|------------------|-------------------------------------------------------|
| `204 No Content` | El alimento se eliminó correctamente.                 |
| `404 Not Found`  | No se encontró un alimento con el `id` proporcionado. |

### Ejemplo de Respuesta Exitosa (204 No Content)

```json
{
  "message": "El alimento fue eliminado correctamente."
}
```

