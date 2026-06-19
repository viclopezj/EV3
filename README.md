# Regifood - Sistema de Gestión Administrativa

* **Estudiante:** Vicente López
* **Empresa / Cliente:** Tasty Feast
* **Tecnologías Principales:** Java 25, Spring Boot, Spring Cloud, JPA/Hibernate, MySQL, OpenFeign.

---

## Descripción del Proyecto

**Regifood** es una solución de software empresarial distribuida diseñada como hito técnico formal para la corporación gastronómica **Tasty Feast**. El sistema aborda de manera integral las ineficiencias logísticas y de control operativo derivadas de la gestión manual y el uso de sistemas monolíticos heredados en su red nacional de franquicias.

A través de un enfoque basado en una **Arquitectura de Microservicios**, el ecosistema descentraliza la persistencia de datos mediante el patrón *Database-per-Microservice* sobre MySQL y utiliza el stack de **Spring Cloud** para garantizar una topología altamente elástica, desacoplada y tolerante a fallos.

### Componentes de Infraestructura de Soporte

* **Eureka Server (`eureka-service`):** Servidor de descubrimiento que centraliza el registro dinámico de instancias en el puerto `8761`.
* **Config Server (`config-server`):** Servidor de configuración centralizada que expone las propiedades de entorno en el puerto `8888` utilizando almacenamiento nativo local (`./config-microservicios`).
* **API Gateway (`api-gateway`):** Enrutador perimetral único del ecosistema expuesto en el puerto `8080`. Implementa balanceo de carga reactivo dinámico (`lb://`) aislando la red interna del backend.

---

## Instrucciones de Instalación y Ejecución

Para desplegar localmente el ecosistema Regifood, siga rigurosamente los siguientes pasos secuenciales:

### Paso 1: Configuración de los Esquemas en MySQL
Abra su gestor de bases de datos y ejecute las siguientes sentencias para inicializar de forma independiente las bases de datos requeridas por el ecosistema:

```sql
CREATE DATABASE tf_bd_empleados;
CREATE DATABASE tf_bd_equipos;
CREATE DATABASE tf_bd_gerentes;
CREATE DATABASE tf_bd_inventarios;
CREATE DATABASE tf_bd_locales;
CREATE DATABASE tf_bd_menus;
CREATE DATABASE tf_bd_proveedores;
CREATE DATABASE tf_bd_ventas;
```

### Paso 2: Orden Secuencial de Encendido de los Servicios
Debido a que los microservicios operativos se inicializan en **puertos dinámicos aleatorios (`server.port=0`)** para permitir escalabilidad horizontal elástica, se recomienda levantar siguiendo este orden:

1. **`eureka-service`:** Servidor de Descubrimiento (Se inicializa primero para habilitar la escucha de registros).
2. **`config-server`:** Servidor de Configuración (Debe estar listo antes de los módulos de dominio para inyectarles sus credenciales y propiedades).
3. **Microservicios de Dominio (Orden Indiferente):**
   * `locales-service`
   * `empleados-service`
   * `gerentes-service`
   * `proveedores-service`
   * `inventarios-service`
   * `menus-service`
   * `equipos-service`
   * `ventas-service`
4. **`api-gateway`:** Enrutador del Ecosistema. Se enciende en el último lugar de la secuencia. Al configurarlo al final, se garantiza que todas las rutas y mapeos dinámicos de los microservicios de dominio ya se encuentren totalmente registrados y disponibles en Eureka, optimizando la resolución de rutas del punto de entrada único y previniendo fallos perimetrales.

### Paso 3: Verificación de la Infraestructura
Puede validar la correcta conexión, la salud de las instancias de la JVM y el mapeo automático de puertos dinámicos ingresando al panel de control interactivo:

* **Dashboard de Eureka:** http://localhost:8761

---

## Mapeo Completo de Rutas y Endpoints Detallados

Todas las llamadas externas del cliente se unifican a través del API Gateway (http://localhost:8080). A continuación, se expone el catálogo exhaustivo de los endpoints implementados en el ecosistema, organizados por microservicio y funcionalidad técnica:

### 1. Servicio de Locales (`/api/v1/locales`)
* **Métodos CRUD:**
  * `GET /api/v1/locales` - Obtiene la lista completa de locales en formato de entidad pura.
  * `GET /api/v1/locales/{id}` - Busca una sucursal específica por su ID primario.
  * `POST /api/v1/locales` - Registra un nuevo local.
  * `PUT /api/v1/locales/{id}` - Modifica los datos de un local existente.
  * `DELETE /api/v1/locales/{id}` - Elimina una sucursal del sistema.
* **Métodos de Transferencia de Datos (DTO):**
  * `GET /api/v1/locales/listado` - Retorna los locales mapeados a respuestas optimizadas en formato `LocalDTO`.
  * `GET /api/v1/locales/listado/{id}` - Busca una sucursal y la expone formateada mediante su DTO estructurado.
* **Métodos y Consultas Personalizadas:**
  * `GET /api/v1/locales/comuna/{comuna}` - Filtra los restaurantes basándose en la comuna a la que pertenecen.
  * `GET /api/v1/locales/gerente-id/{id}` - Busca coincidencias exactas por el id del gerente.
  * `GET /api/v1/locales/gerente-tipo/{tipo}` - Busca coincidencias exactas por el tipo de gerente.

### 2. Servicio de Empleados (`/api/v1/empleados`)
* **Métodos CRUD:**
  * `GET /api/v1/empleados` - Recupera la lista global de trabajadores en formato de entidad pura.
  * `GET /api/v1/empleados/{id}` - Busca un empleado específico por su ID.
  * `POST /api/v1/empleados` - Registra un nuevo trabajador validando correo institucional y fono (`+569` con mínimo 11 caracteres).
  * `PUT /api/v1/empleados/{id}` - Actualiza el perfil de un empleado bajo validaciones estrictas.
  * `DELETE /api/v1/empleados/{id}` - Remueve un trabajador del esquema de personal.
* **Métodos de Transferencia de Datos (DTO):**
  * `GET /api/v1/empleados/listado` - Retorna el listado de empleados en formato `EmpleadoDTO`, integrando datos síncronos del local remoto consumidos mediante `OpenFeign`.
  * `GET /api/v1/empleados/listado/{id}` - Expone la vista limpia DTO de un empleado incluyendo la información consolidada de su sucursal de destino.
* **Métodos y Consultas Personalizadas:**
  * `GET /api/v1/empleados/email/{email}` - Localiza un operario mediante coincidencia exacta de su dirección de correo electrónico (`.cl`, `.com`, `.net`).
  * `GET /api/v1/empleados/salario-mayor/{salario}` - Filtra el personal cuyos sueldos base superen la cifra dada.
  * `GET /api/v1/empleados/salario-menor/{salario}` - Filtra el personal cuyos sueldos base sean inferiores a la cifra dada.
  * `GET /api/v1/empleados/local/{local}` - Extrae y agrupa la nómina completa de trabajadores pertenecientes a una sucursal específica.

### 3. Servicio de Gerentes (`/api/v1/gerentes`)
* **Métodos CRUD:**
  * `GET /api/v1/gerentes` - Lista el personal directivo completo en formato de entidad pura.
  * `GET /api/v1/gerentes/{id}` - Busca un gerente específico por su ID corporativo.
  * `POST /api/v1/gerentes` - Registra un mando superior validando teléfono, correo y las matrices de bonos acotadas por jerarquía (`Junior`, `Senior`, `Regional`).
  * `PUT /api/v1/gerentes/{id}` - Modifica los parámetros de un gerente aplicando las mismas restricciones jerárquicas.
  * `DELETE /api/v1/gerentes/{id}` - Elimina un registro de la planta directiva.
* **Métodos de Transferencia de Datos (DTO):**
  * `GET /api/v1/gerentes/listado` - Entrega los gerentes formateados con respuestas limpias (`GerenteDTO`) exponiendo ID, nombre completo y nivel de mando.
  * `GET /api/v1/gerentes/listado/{id}` - Recupera un gerente individual estructurado a través de su DTO.
* **Métodos y Consultas Personalizadas:**
  * `GET /api/v1/gerentes/email/{email}` - Busca un directivo por su correo electrónico único (`.cl`, `.com`, `.net`).
  * `GET /api/v1/gerentes/salario-mayor/{salario}` - Filtra los gerentes cuyos sueldos sean mayores o iguales al parámetro.
  * `GET /api/v1/gerentes/salario-menor/{salario}` - Filtra los gerentes cuyos sueldos sean menores o iguales al parámetro.
  * `GET /api/v1/gerentes/bono-mayor/{bono}` - Lista directivos cuyos bonos de cumplimiento superen o igualen el monto.
  * `GET /api/v1/gerentes/bono-menor/{bono}` - Lista directivos cuyos bonos de cumplimiento sean inferiores o iguales al monto.

### 4. Servicio de Proveedores (`/api/v1/proveedores`)
* **Métodos CRUD:**
  * `GET /api/v1/proveedores` - Retorna los proveedores externos registrados en su entidad pura.
  * `GET /api/v1/proveedores/{id}` - Localiza un proveedor mediante su ID único.
  * `POST /api/v1/proveedores` - Añade una entidad proveedora al sistema resguardando la unicidad del nombre de la empresa, fono (`+569`) y correo.
  * `PUT /api/v1/proveedores/{id}` - Edita el perfil de contacto o datos fiscales de un proveedor.
  * `DELETE /api/v1/proveedores/{id}` - Elimina un proveedor del registro de la cadena.
* **Métodos de Transferencia de Datos (DTO):**
  * `GET /api/v1/proveedores/listado` - Muestra los proveedores en formato `ProveedorDTO` omitiendo datos de control interno redundantes.
  * `GET /api/v1/proveedores/listado/{id}` - Entrega los datos formateados de un proveedor individual a través de su DTO.
* **Métodos y Consultas Personalizadas:**
  * `GET /api/v1/proveedores/email/{email}` - Busca una entidad por correspondencia de su e-mail corporativo.
  * `GET /api/v1/proveedores/tipo-proveedor/{tipo}` - Lista a los proveedores por los uno de los 3 tipos (`Alimentos`,`Limpieza`,`Maquinaria`).
  * `GET /api/v1/proveedores/region/{region}` - Filtra proveedores buscando por coincidencia de la región.

### 5. Servicio de Inventarios (`/api/v1/inventarios`)
* **Métodos CRUD:**
  * `GET /api/v1/inventarios` - Lista todas las existencias globales de insumos.
  * `GET /api/v1/inventarios/{id}` - Busca una partida de stock o lote de insumo específico por su ID.
  * `POST /api/v1/inventarios` - Registra un insumo aplicando la regla de negocio: el nombre del insumo no puede duplicarse dentro del mismo local físico.
  * `PUT /api/v1/inventarios/{id}` - Modifica niveles de stock o metadatos de un insumo validando la restricción de duplicados por sucursal.
  * `DELETE /api/v1/inventarios/{id}` - Da de baja un ítem del almacén.
* **Métodos de Transferencia de Datos (DTO):**
  * `GET /api/v1/inventarios/listado` - Entrega el inventario en formato `InventarioDTO` inyectando datos del local remoto acoplado por `OpenFeign`.
  * `GET /api/v1/inventarios/listado/{id}` - Muestra el DTO consolidado de un ítem de stock individual.
* **Métodos y Consultas Personalizadas:**
  * `GET /api/v1/inventarios/local/{local}` - Extrae de manera exclusiva las existencias pertenecientes a un ID de sucursal determinado.
  * `GET /api/v1/inventarios/cantidad-menor/{cantidad}` - Alerta de stock crítico filtrando insumos con cantidades inferiores o iguales al límite dado.
  * `GET /api/v1/inventarios/cantidad-mayor/{cantidad}` - Filtra lotes con abundancia de existencias superiores o iguales al valor enviado.
  * `GET /api/v1/inventarios/proveedor/{proveedor}` - Filtra los lotes dados por el ID del proveedor determinado.


### 6. Servicio de Menús (`/api/v1/menus`)
* **Métodos CRUD:**
  * `GET /api/v1/menus` - Recupera la oferta gastronómica completa en su formato de entidad base.
  * `GET /api/v1/menus/{id}` - Busca una preparación del menú por su ID único.
  * `POST /api/v1/menus` - Agrega un menú al sistema asegurando que no se duplique la misma minuta dentro del mismo local gastronómico.
  * `PUT /api/v1/menus/{id}` - Actualiza el precio o los componentes de un menú resguardando la regla de unicidad por local.
  * `DELETE /api/v1/menus/{id}` - Remueve un plato o combo de la oferta de la sucursal.
* **Métodos de Transferencia de Datos (DTO):**
  * `GET /api/v1/menus/listado` - Retorna las minutas mapeadas a `MenuDTO` resolviendo los datos cruzados distribuidos del local.
  * `GET /api/v1/menus/listado/{id}` - Expone el DTO estructurado de un ítem del menú de forma individual.
* **Métodos y Consultas Personalizadas:**
  * `GET /api/v1/menus/local/{id}` - Extrae la carta de minutas vigente configurada de forma exclusiva para una sucursal determinada por su ID.
  * `GET /api/v1/menus/precio-menor/{precio}` - Filtra la oferta de platos cuyos precios sean menores o iguales al monto.
  * `GET /api/v1/menus/precio-mayor/{precio}` - Filtra la oferta de platos cuyos precios superen o igualen el monto.
  * `GET /api/v1/menus/categoria/{codigo}` - Agrupa las minutas asociadas a un código de categoría gastronómica.

### 7. Servicio de Categorías (`/api/v1/categorias`)
*(Este componente opera como un nanoservicio integrado dentro de la frontera lógica del servicio de menús)*
* **Métodos CRUD:**
  * `GET /api/v1/categorias` - Muestra todas las clasificaciones gastronómicas activas.
  * `GET /api/v1/categorias/{codigo}` - Localiza una categoría mediante su código alfanumérico único (No permite indexación ni búsquedas mediante IDs numéricos convencionales)..
  * `POST /api/v1/categorias` - Registra una nueva categoría validando que el Código y el Nombre sean estrictamente únicos.
  * `PUT /api/v1/categorias/{codigo}` - Modifica los parámetros de una categoría localizándola mediante su código alfanumérico único.
  * `DELETE /api/v1/categorias/{codigo}` - Remueve una categoría del sistema utilizando su código alfanumérico único.

### 8. Servicio de Equipos (`/api/v1/equipos`)
* **Métodos CRUD:**
  * `GET /api/v1/equipos` - Despliega la lista total de activos y maquinaria en su formato de entidad pura.
  * `GET /api/v1/equipos/{id}` - Busca un equipo de cocina específico utilizando su ID primario.
  * `POST /api/v1/equipos` - Registra un nuevo activo físico en el sistema.
  * `PUT /api/v1/equipos/{id}` - Actualiza el estado técnico u hoja de ruta de mantenimiento de un equipo.
  * `DELETE /api/v1/equipos/{id}` - Elimina la maquinaria física del inventario de activos de la empresa.
* **Métodos de Transferencia de Datos (DTO):**
  * `GET /api/v1/equipos/listado` - Devuelve la maquinaria mapeada a respuestas limpias en formato `EquipoDTO`.
  * `GET /api/v1/equipos/listado/{id}` - Muestra los datos limpios de un equipo individual a través de su DTO estructurado.
* **Métodos y Consultas Personalizadas:**
  * `GET /api/v1/equipos/marcas/{codigo}` - Filtra y extrae los activos que pertenezcan al código único de una marca específica.
  * `GET /api/v1/equipos/proveedor/{proveedor}` - Agrupa la maquinaria suministrada por un ID de proveedor determinado (vía `OpenFeign`).
  * `GET /api/v1/equipos/año-de-compra/{anio}` - Mapea y agrupa los activos tecnológicos basándose en su año exacto de adquisición.

### 9. Servicio de Marcas (`/api/v1/marcas`)
*(Este componente opera como un nanoservicio integrado dentro de la frontera lógica del servicio de equipos)*
* **Métodos CRUD:**
  * `GET /api/v1/marcas` - Despliega el catálogo de marcas de maquinaria disponibles en la corporación.
  * `GET /api/v1/marcas/{codigo}` - Localiza una marca usando su código alfanumérico único (No permite indexación ni búsquedas mediante IDs numéricos convencionales).
  * `POST /api/v1/marcas` - Registra una marca validando que su Código y Nombre sean únicos a nivel de plataforma.
  * `PUT /api/v1/marcas/{codigo}` - Modifica los metadatos de una marca localizándola mediante su código alfanumérico único.
  * `DELETE /api/v1/marcas/{codigo}` - Remueve la marca utilizando su código alfanumérico único.

### 10. Servicio de Ventas (`/api/v1/ventas`)
* **Métodos CRUD:**
  * `GET /api/v1/ventas` - Recupera la totalidad de los históricos de auditorías comerciales en formato de entidad pura.
  * `GET /api/v1/ventas/{id}` - Localiza un reporte financiero diario específico mediante su ID numérico.
  * `POST /api/v1/ventas` - Guarda un reporte comercial aplicando la regla de auditoría estricta: queda prohibido duplicar la fecha de reporte para un mismo local (un reporte diario consolidado por sucursal).
  * `PUT /api/v1/ventas/{id}` - Modifica los datos financieros de un reporte validando la restricción estricta de fecha única por sucursal.
  * `DELETE /api/v1/ventas/{id}` - Elimina un registro de auditoría comercial.
* **Métodos de Transferencia de Datos (DTO):**
  * `GET /api/v1/ventas/listado` - Entrega las auditorías financieras estructuradas bajo formato `VentaDTO`, acoplando los datos del local remoto vía `OpenFeign`.
  * `GET /api/v1/ventas/listado/{id}` - Expone el DTO estructurado consolidado de una venta diaria individual.
* **Métodos y Consultas Personalizadas (Reportes Especializados):**
  * `GET /api/v1/ventas/local/{local}` - Filtra y consolida la totalidad de las ventas pertenecientes a un ID de local específico.
  * `GET /api/v1/ventas/venta-maxima-menor/{venta}` - Filtra reportes comerciales cuyas ventas máximas registradas sean inferiores o iguales al monto indicado.
  * `GET /api/v1/ventas/venta-minima-mayor/{venta}` - Filtra reportes comerciales cuyas ventas mínimas superen o igualen el parámetro provisto.
  * `GET /api/v1/ventas/venta-promedio-mayor/{venta}` - Filtra los balances consolidados cuyo rendimiento promedio de venta sea mayor o igual al valor ingresado.
