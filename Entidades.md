# Entidad `Alimento`

La entidad `Alimento` representa los alimentos almacenados en un sistema de gestión de inventarios. Esta clase es una
entidad JPA que mapea la tabla `alimento` en la base de datos.

## Atributos

- **id** (`Long`): Identificador único del alimento. Este campo se genera automáticamente con una estrategia de
  identidad.
- **nombre** (`String`): Nombre del alimento. Este campo no puede ser nulo y tiene un máximo de 255 caracteres.
- **fechaCaducidad** (`LocalDate`): Fecha de caducidad del alimento. Este campo es obligatorio.
- **abierto** (`boolean`): Indica si el alimento ha sido abierto. Este campo es obligatorio.
- **perecedero** (`boolean`): Indica si el alimento es perecedero o no. Este campo es obligatorio.
- **numeroUsos** (`int`): Número de usos o raciones disponibles del alimento. Este campo es obligatorio.
- **existencia** (`List<Existencia>`): Relación de uno a muchos con la entidad `Existencia`. Representa las existencias
  de este alimento.
- **inventario** (`Inventario`): Relación de muchos a uno con la entidad `Inventario`. Representa el inventario al que
  pertenece este alimento.

## Relaciones

### Relación con `Existencia`

- Tipo de relación: **Uno a muchos**
- La entidad `Alimento` tiene una relación de uno a muchos con la entidad `Existencia`, donde un alimento puede tener
  varias existencias asociadas.
- Se utiliza la anotación `@OneToMany(mappedBy = "alimento", cascade = {CascadeType.PERSIST, CascadeType.MERGE})` para
  definir esta relación.
- La propiedad `@JsonManagedReference` se utiliza para evitar problemas de recursión al serializar la relación en
  formato JSON.

### Relación con `Inventario`

- Tipo de relación: **Muchos a uno**
- La entidad `Alimento` tiene una relación de muchos a uno con la entidad `Inventario`, indicando que cada alimento
  pertenece a un inventario específico.
- Se usa la anotación `@ManyToOne` para definir esta relación.

---

# Entidad `Existencia`

La entidad `Existencia` representa las existencias de un alimento en un sistema de gestión de inventarios. Esta clase es
una entidad JPA que mapea la tabla `existencia` en la base de datos.

## Atributos

- **id** (`Long`): Identificador único de la existencia. Este campo se genera automáticamente con una estrategia de
  identidad.
- **cantidad_alimento** (`int`): Cantidad disponible del alimento en existencia. Este campo es obligatorio.
- **fechaEntrada** (`LocalDate`): Fecha en la que el alimento ingresó al inventario. Este campo es obligatorio.
- **alimento** (`Alimento`): Relación de muchos a uno con la entidad `Alimento`. Representa el alimento asociado a esta
  existencia.
- **ubicacion** (`Ubicacion`): Relación de muchos a uno con la entidad `Ubicacion`. Representa la ubicación en el
  inventario donde se encuentra este alimento.

## Relaciones

### Relación con `Alimento`

- Tipo de relación: **Muchos a uno**
- La entidad `Existencia` tiene una relación de muchos a uno con la entidad `Alimento`, indicando que muchas existencias
  pueden estar asociadas a un mismo alimento.
- Se usa la anotación `@ManyToOne` y `@JoinColumn(name = "alimento_id")` para establecer la relación con la tabla
  `alimento` y la columna correspondiente.
- La propiedad `@JsonBackReference` se utiliza para evitar problemas de recursión al serializar la relación en formato
  JSON.

### Relación con `Ubicacion`

- Tipo de relación: **Muchos a uno**
- La entidad `Existencia` tiene una relación de muchos a uno con la entidad `Ubicacion`, indicando que muchas
  existencias pueden estar asociadas a una misma ubicación en el inventario.
- Se usa la anotación `@ManyToOne` y `@JoinColumn(name = "ubicacion_id")` para establecer la relación con la tabla
  `ubicacion` y la columna correspondiente.
- La propiedad `@JsonBackReference` se utiliza para evitar problemas de recursión al serializar la relación en formato
  JSON.

---

# Entidad `Inventario`

La entidad `Inventario` representa un inventario de alimentos dentro de un sistema de gestión. Esta clase es una entidad
JPA que mapea la tabla `inventario` en la base de datos.

## Atributos

- **id** (`Long`): Identificador único del inventario. Este campo se genera automáticamente con una estrategia de
  identidad.
- **usuario** (`Usuario`): Relación de uno a uno con la entidad `Usuario`. Representa al usuario que es dueño o
  responsable del inventario.
- **alimentos** (`List<Alimento>`): Relación de uno a muchos con la entidad `Alimento`. Representa los alimentos que
  están asociados a este inventario.

## Relaciones

### Relación con `Usuario`

- Tipo de relación: **Uno a uno**
- La entidad `Inventario` tiene una relación de uno a uno con la entidad `Usuario`, indicando que cada inventario
  pertenece a un único usuario.
- Se usa la anotación `@OneToOne` para definir la relación con la tabla `usuario`.

### Relación con `Alimento`

- Tipo de relación: **Uno a muchos**
- La entidad `Inventario` tiene una relación de uno a muchos con la entidad `Alimento`, indicando que un inventario
  puede tener muchos alimentos asociados.
- Se usa la anotación `@OneToMany(mappedBy = "inventario")` para definir esta relación. Esto indica que la propiedad
  `inventario` en la clase `Alimento` es la que mapea la relación.

---

# Entidad `Ubicacion`

La entidad `Ubicacion` representa las ubicaciones donde se almacenan los alimentos en un sistema de gestión de
inventarios. Esta clase es una entidad JPA que mapea la tabla `ubicacion` en la base de datos.

## Atributos

- **id** (`Long`): Identificador único de la ubicación. Este campo se genera automáticamente con una estrategia de
  identidad.
- **descripcion** (`String`): Descripción de la ubicación. Este campo es obligatorio y tiene un máximo de 255
  caracteres.
- **capacidad** (`int`): Capacidad máxima de la ubicación, es decir, cuántas existencias puede almacenar. Este campo es
  obligatorio.
- **existencias** (`List<Existencia>`): Relación de uno a muchos con la entidad `Existencia`. Representa las existencias
  de alimentos asociadas a esta ubicación.
- **nombreUbicacion** (`EnumUbicacion`): Enum que representa el nombre de la ubicación. Este campo utiliza el tipo
  `EnumUbicacion` para definir valores predefinidos de ubicación (como "Almacen", "Refrigerador", etc.).

## Relaciones

### Relación con `Existencia`

- Tipo de relación: **Uno a muchos**
- La entidad `Ubicacion` tiene una relación de uno a muchos con la entidad `Existencia`, indicando que una ubicación
  puede tener varias existencias asociadas.
- Se usa la anotación `@OneToMany(mappedBy = "ubicacion", cascade = {CascadeType.PERSIST, CascadeType.MERGE})` para
  definir esta relación.
- La propiedad `@JsonManagedReference` se utiliza para evitar problemas de recursión al serializar la relación en
  formato JSON.

### Relación con `EnumUbicacion`

- Tipo de relación: **Enum**
- La entidad `Ubicacion` tiene un campo `nombreUbicacion` de tipo `EnumUbicacion`, lo que asegura que solo se pueden
  usar valores predefinidos en este campo (como "Almacen", "Refrigerador", etc.).
- Se usa la anotación `@Enumerated(EnumType.STRING)` para almacenar el valor del enum como una cadena en la base de
  datos.

---

# Entidad `Usuario`

La entidad `Usuario` representa a un usuario del sistema, el cual puede ser responsable de gestionar los alimentos en el
inventario. Esta clase es una entidad JPA que mapea la tabla `usuario` en la base de datos.

## Atributos

- **id** (`Long`): Identificador único del usuario. Este campo se genera automáticamente con una estrategia de
  identidad.
- **username** (`String`): Nombre de usuario. Este campo es obligatorio y tiene un máximo de 255 caracteres.
- **password** (`String`): Contraseña del usuario. Este campo es obligatorio y tiene un máximo de 255 caracteres.
