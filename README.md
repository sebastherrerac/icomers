## Nombres
Matias Contreras Gajardo
Bastian Zuñiga Montecino
Sebastian Herrera Correa


REST API para gestion de una tienda de figuras de coleccion. Permite administrar productos, categorias, clientes, ventas, metodos de pago y envios.

## Sack
-Java 21
-Spring Boot 4.0.6
-Spring Data JPA** + **MySQL
-Lombok
-Bean Validation
-Maven


## entidades principales
| Entidad     | Descripción                              |
|-------------|------------------------------------------|
| Figuras   | Productos (nombre, precio, stock, categoria) |
| Categoria | Categoria de figuras                     |
| Personaje | Personaje asociado a una figura          |
| Cliente   | Cliente con nombre, email, teléfono      |
| Ventas    | venta de clientes, figura y pago         |
| TipoPago  | Metodos de pago disponibles              |
| TipoEnvio | Tipos de envío disponibles               |
| Region    | Regiones de despacho                     |
| Comuna    | Comunas de despacho                      |

## endpoints

path: /api/v1

| Recurso      | Ruta                            |
|--------------|---------------------------------|
| Figuras      | /api/v1/figuras                 |
| Categorías   | /api/v1/categorias              |
| Personajes   | /api/v1/personajes              |
| Clientes     | /api/v1/clientes                |
| Ventas       | /api/v1/ventas                  |
| Tipos de pago| /api/v1/tipopago                |
| Tipos envío  | /api/v1/tipoenvio               |
| Regiones     | /api/v1/regiones                |
| Comuna       | /api/v1/Comuna                  |

Cada recurso sporta operacuiones CRUD estandar.

Figuras /api/v1/figuras   
GET /: Listar todas   
GET /{id}: Buscar por ID   
GET /categoria/{id}: Buscar por categoría   
POST /: Crear — { nombre, precio, stock, descripcion, categoria: {idCategoria} }   
PUT /actualizar/{id}: Actualizar   
DELETE /{id}: Eliminar   

Categorías /api/v1/categorias   
GET /: Sin Body (—)   
POST /: Body { nombreCategoria }   
PUT /{id}: Body { nombreCategoria }   
DELETE /{id}: Sin Body (—)   

Clientes /api/clientes   
GET /: Sin Body (—)   
POST /: Body { nombre, email, telefono, direccion }   
PUT /{id}: Body { nombre, email, telefono, direccion }   
DELETE /{id}: Sin Body (—)

Ventas /api/ventas   
GET /: Sin Body (—)   
GET /{id}: Sin Body (—)   
POST /: Body { cliente: {id}, figuras: {idFiguras}, tipoPago: {idTipoPago}, tipoEnvio, total }   
PUT /{id}: Body igual al POST   
DELETE /{id}: Sin Body (—)   

Personajes /api/personajes   
GET /: Sin Body (—)   
GET /buscar/{nombre}: Sin Body (—)   
POST /: Body { nombre, franquicia }   
PUT /{id}: Body { nombre, franquicia }   
DELETE /{id}: Sin Body (—)   

Tipos de Pago /api/v1/tipopago   
GET / · /{id}: Sin Body (—)   
POST /: Body { nombreTipoPago }   
PUT /{id}: Body { nombreTipoPago }   
DELETE /{id}: Sin Body (—)   

Tipos de Envío /api/v1/tipoenvio   
GET / · /{id}: Sin Body (—)   
POST /: Body { nombreEnvio, costoEnvio, activoEnvio }   
PUT /{id}: Body { nombreEnvio, costoEnvio, activoEnvio }   
DELETE /{id}: Sin Body (—)   

Regiones /api/v1/region   
GET / · /{id}: Sin Body (—)   
POST /: Body { nombreRegion, activo }   
PUT /{id}: Body { nombreRegion, activo }   
DELETE /{id}: Sin Body (—)   

Comunas /api/comuna   
GET /: Sin Body (—)   
GET /{id}: Sin Body (—)   
POST /: Body { nombreComuna }   
PUT /{id}: Body { comuna: {idcomuna}, {nombrecomuna}, {activocomuna}, {regionid} }   
DELETE /{id}: Sin Body (—)

Marcas /api/v1/marcas  
GET /: Sin Body (—)  
GET /{id}: Sin Body (—)  
POST /: Body { nombreMarca }   
PUT /{id}: Body { nombreMarca }  
DELETE /{id}: Sin Body (—)  
  
## requisitos
-Java 21+
-MySQL 8+
-Maven 3.8

## Ejecutar

http://localhost:8080



