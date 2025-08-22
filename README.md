# Microframeworks WEB (Taller 2 AREP)

Un framework de servidor HTTP implementado en Java que permite el desarrollo de aplicaciones web con servicios REST backend. Este proyecto convierte un servidor HTTP básico en un framework completo que soporta la definición de servicios REST usando funciones lambda, manejo de parámetros de consulta y especificación de ubicación de archivos estáticos.

## Nuevas Funcionalidades del Taller 2

### 1. Método GET Estático para Servicios REST
Implementa un método `get()` que permite a los desarrolladores definir servicios REST usando funciones lambda.

**Ejemplo de uso:**
```java
get("/hello", (req, res) -> "hello world!");
```

Esta funcionalidad permite a los desarrolladores definir rutas simples y claras dentro de sus aplicaciones, mapeando URLs a expresiones lambda específicas que manejan las peticiones y respuestas.

### 2. Mecanismo de Extracción de Valores de Consulta
Desarrolla un mecanismo para extraer parámetros de consulta de las peticiones entrantes y hacerlos accesibles dentro de los servicios REST.

**Ejemplo de uso:**
```java
get("/hello", (req, res) -> "hello " + req.getValue("name"));
```

Esta funcionalidad facilita la creación de servicios REST dinámicos y parametrizados, permitiendo a los desarrolladores acceder y utilizar fácilmente los parámetros de consulta dentro de sus implementaciones de servicios.

### 3. Especificación de Ubicación de Archivos Estáticos
Introduce un método `staticfiles()` que permite a los desarrolladores definir la carpeta donde se ubican los archivos estáticos.

**Ejemplo de uso:**
```java
staticfiles("webroot/public");
```

La función `staticfiles()` establece el nuevo directorio donde se servirán los archivos estáticos. Esta función automáticamente:
- Crea el directorio especificado en `target/classes/[ruta-especificada]/`
- Copia todos los archivos estáticos existentes desde `src/main/java/resorces/` a la nueva ruta establecida por el desarrollador
- Permite que el desarrollador pueda añadir o modificar más archivos estáticos en la nueva ubicación

El framework buscará archivos estáticos en el directorio especificado, como `target/classes/webroot/public`, facilitando a los desarrolladores la organización y gestión de los recursos estáticos de su aplicación.

### Ejemplo de Uso del Nuevo Framework

```java
public class WebApplication {
    public static void main(String[] args) {
        staticfiles("/webroot");
        get("/hello", (req, resp) -> "Hello " + req.getValue("name"));
        get("/pi", (req, resp) -> {
            return String.valueOf(Math.PI); 
        });
    }
}
```

Este código simple iniciará un servidor web y servirá una aplicación web con archivos estáticos ubicados en "target/classes/webroot". Los servicios REST GET responderán a las siguientes peticiones:

- `http://localhost:35000/App/hello?name=Pedro`
- `http://localhost:35000/App/pi`

El código también responde a peticiones de archivos estáticos:
- `http://localhost:35000/index.html`

En el ejemplo, los servicios REST se publican con el prefijo "/App".


## Características principales

- **Framework de servidor HTTP**: Implementación desde cero usando sockets Java convertida en framework completo
- **Servicios REST con Lambdas**: Definición de servicios usando expresiones lambda para mayor simplicidad
- **Extracción de parámetros de consulta**: Mecanismo automático para acceder a parámetros URL
- **Gestión flexible de archivos estáticos**: Configuración personalizable de ubicación de recursos estáticos
- **Servicio de archivos estáticos**: HTML, CSS, JavaScript, imágenes
- **Manejo de formularios**: Soporte para métodos GET y POST
- **Múltiples tipos MIME**: Detección automática del tipo de contenido
- **Arquitectura modular**: Separación de responsabilidades en diferentes clases

## Estructura del proyecto

```
httpserver/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mycompany/httpserver/
│   │   │       ├── HttpServer.java      # Servidor principal con nuevas funcionalidades
│   │   │       ├── HttpRequest.java     # Manejo de peticiones con extracción de parámetros
│   │   │       ├── HttpResponse.java    # Manejo de respuestas
│   │   │       ├── Service.java         # Interfaz para servicios REST
│   │   │       └── webaplication/
│   │   │           └── WebApplication.java # Ejemplo de uso del framework
│   │   └── resorces/                    # Archivos estáticos originales
│   │       ├── index.html
│   │       ├── styles/style.css
│   │       ├── scripts/script.js
│   │       └── images/
│   └── test/
│       └── java/
│           └── com/mycompany/httpserver/
│               └── HttpServerTest.java  # Pruebas unitarias completas                        
├── target/
│   └── classes/                         # Archivos compilados y recursos copiados
├── pom.xml                              
└── README.md
```

## Pruebas Implementadas

El proyecto incluye pruebas unitarias completas que verifican:

### Pruebas de Funcionalidades Básicas:
- Existencia y integridad de archivos estáticos
- Estructura correcta de directorios
- Manejo de diferentes tipos de archivos (HTML, CSS, JS, imágenes)
- Formato de cabeceras HTTP
- Respuestas de error 404

### Pruebas de Nuevas Funcionalidades del Taller 2:
- **Método get()**: Registro de servicios REST con funciones lambda
- **Extracción de parámetros**: Método `getValue()` con diferentes escenarios:
  - Parámetros simples y múltiples
  - Parámetros vacíos y inexistentes
  - URIs sin query string
  - Caracteres especiales en parámetros
- **Método staticfiles()**: 
  - Creación automática de directorios
  - Copia de archivos estáticos originales
  - Manejo de rutas relativas y absolutas
- **Integración completa**: Ejecución de servicios con parámetros

### Ejecutar las pruebas:
```bash
mvn test
```

## Ejemplos de Uso

### Definir Servicios REST:
```java
// Servicio simple
get("/hello", (req, resp) -> "Hello World!");

// Servicio con parámetros
get("/greet", (req, resp) -> "Hello " + req.getValue("name"));

// Servicio con lógica compleja
get("/calculate", (req, resp) -> {
    int a = Integer.parseInt(req.getValue("a"));
    int b = Integer.parseInt(req.getValue("b"));
    return String.valueOf(a + b);
});
```

### Configurar Archivos Estáticos:
```java
// Configurar directorio de archivos estáticos
staticfiles("/public");        // Relativo: target/classes/public/
staticfiles("/assets/web");    // Absoluto: target/classes/assets/web/
```

### URLs de Ejemplo:
- `http://localhost:35000/App/hello`
- `http://localhost:35000/App/greet?name=Pedro`
- `http://localhost:35000/App/calculate?a=5&b=3`
- `http://localhost:35000/index.html` 

## Cómo Ejecutar el Proyecto

### Ejecutar el servidor:
```bash
mvn clean compile exec:java
```

### Probar las funcionalidades:

1. **Archivo estático (index.html):**
   ```
   http://localhost:35000/
   http://localhost:35000/index.html
   ```

2. **Servicio REST con parámetros:**
   ```
   http://localhost:35000/app/hello?name=Pedro
   ```
   Respuesta esperada: "Hello Pedro"

3. **Servicio REST sin parámetros:**
   ```
   http://localhost:35000/app/pi
   ```
   Respuesta esperada: "3.141592653589793"

4. **Archivos estáticos desde nueva ubicación:**
   Los archivos se copian automáticamente a `target/classes/webroot/public/` y se sirven desde allí.

## Arquitectura de la Solución

### Componentes Principales:

1. **HttpServer**: Servidor principal que maneja:
   - Registro de servicios REST con el método `get()`
   - Configuración de archivos estáticos con `staticfiles()`
   - Ruteo de peticiones HTTP
   - Respuesta a diferentes tipos de archivos

2. **HttpRequest**: Maneja las peticiones entrantes:
   - Extrae parámetros de consulta con `getValue()`
   - Parsea URIs y query strings
   - Proporciona acceso a datos de la petición

3. **Service**: Interfaz funcional que permite:
   - Definición de servicios usando lambdas
   - Procesamiento de peticiones y respuestas
   - Lógica de negocio personalizada

4. **WebApplication**: Ejemplo de implementación que demuestra:
   - Configuración del framework
   - Definición de servicios REST
   - Especificación de ubicación de archivos estáticos


## Authors

* **Zayra Gutierrez**
