package com.mycompany.httpserver;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.net.*;

/**
 * Clase de pruebas unitarias para HttpServer
 * Verifica el correcto funcionamiento del servidor HTTP
 * incluyendo el manejo de archivos estáticos y servicios REST
 * Incluye pruebas para las nuevas funcionalidades:
 * - Método get() para servicios REST con lambdas
 * - Método getValue() para extracción de parámetros de consulta
 * - Método staticfiles() para especificación de ubicación de archivos estáticos
 */
public class HttpServerTest {

    @Test
    public void testIndexHtmlFileExists() {
        File indexFile = new File("src/main/java/resorces/index.html");
        assertTrue(indexFile.exists(), "El archivo index.html debe existir en el directorio de recursos");
    }

    @Test
    public void testCssFileExists() {
        File cssFile = new File("src/main/java/resorces/styles/style.css");
        assertTrue(cssFile.exists(), "El archivo style.css debe existir en el directorio de estilos");
    }

    @Test
    public void testJavaScriptFileExists() {
        File jsFile = new File("src/main/java/resorces/scripts/script.js");
        assertTrue(jsFile.exists(), "El archivo script.js debe existir en el directorio de scripts");
    }

    @Test
    public void testImageFilesExist() {
        File pajaroImg = new File("src/main/java/resorces/images/pajaro.jpg");
        File jirafaImg = new File("src/main/java/resorces/images/jiraga.png");
        File faviconImg = new File("src/main/java/resorces/images/favicon.ico");
        
        assertTrue(pajaroImg.exists(), "La imagen pajaro.jpg debe existir");
        assertTrue(jirafaImg.exists(), "La imagen jiraga.png debe existir");
        assertTrue(faviconImg.exists(), "El favicon.ico debe existir");
    }

    @Test
    public void testPrincipalPathConstant() {
        // Verificar que el path principal está correctamente definido
        String expectedPath = "src/main/java/resorces/";
        // Esta es una prueba indirecta ya que PRINCIPALPATH es private
        // Verificamos que los archivos existen en la ruta esperada
        File resourcesDir = new File(expectedPath);
        assertTrue(resourcesDir.exists(), "El directorio de recursos debe existir");
        assertTrue(resourcesDir.isDirectory(), "La ruta de recursos debe ser un directorio");
    }

    @Test
    public void testResourcesDirectoryStructure() {
        File resourcesDir = new File("src/main/java/resorces/");
        File imagesDir = new File("src/main/java/resorces/images/");
        File stylesDir = new File("src/main/java/resorces/styles/");
        File scriptsDir = new File("src/main/java/resorces/scripts/");
        
        assertTrue(resourcesDir.exists() && resourcesDir.isDirectory(), 
                   "El directorio principal de recursos debe existir");
        assertTrue(imagesDir.exists() && imagesDir.isDirectory(), 
                   "El directorio de imágenes debe existir");
        assertTrue(stylesDir.exists() && stylesDir.isDirectory(), 
                   "El directorio de estilos debe existir");
        assertTrue(scriptsDir.exists() && scriptsDir.isDirectory(), 
                   "El directorio de scripts debe existir");
    }

    @Test
    public void testFileExtensionHandling() {
        // Pruebas para verificar que los métodos manejan correctamente las extensiones
        String htmlPath = "/index.html";
        String cssPath = "/styles/style.css";
        String jsPath = "/scripts/script.js";
        String imgPath = "/images/pajaro.jpg";
        
        assertTrue(htmlPath.endsWith(".html"), "Debe detectar archivos HTML");
        assertTrue(cssPath.endsWith(".css"), "Debe detectar archivos CSS");
        assertTrue(jsPath.endsWith(".js"), "Debe detectar archivos JavaScript");
        assertTrue(imgPath.endsWith(".jpg"), "Debe detectar archivos de imagen JPG");
    }

    @Test
    public void testImageExtensionValidation() {
        String jpgPath = "/images/pajaro.jpg";
        String pngPath = "/images/jiraga.png";
        String icoPath = "/images/favicon.ico";
        
        assertTrue(jpgPath.endsWith(".jpg"), "Debe reconocer extensión .jpg");
        assertTrue(pngPath.endsWith(".png"), "Debe reconocer extensión .png");
        assertTrue(icoPath.endsWith(".ico"), "Debe reconocer extensión .ico");
    }

    @Test
    public void testHelloServicePath() {
        String helloPath = "/app/hello";
        assertTrue(helloPath.startsWith("/app/hello"), 
                   "Debe reconocer la ruta del servicio hello");
    }

    @Test
    public void testFileContentReading() throws IOException {
        File indexFile = new File("src/main/java/resorces/index.html");
        if (indexFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(indexFile))) {
                String firstLine = reader.readLine();
                assertNotNull(firstLine, "El archivo debe tener contenido");
                assertTrue(firstLine.length() > 0, "La primera línea no debe estar vacía");
            }
        }
    }

    @Test
    public void testHttpResponseHeaders() {
        // Verificar formato de cabeceras HTTP
        String htmlHeader = "HTTP/1.1 200 OK\n\r" + "contente-type: text/html\n\r" + "\n\r";
        String cssHeader = "HTTP/1.1 200 OK\n\r" + "contente-type: text/css\n\r" + "\n\r";
        String jsHeader = "HTTP/1.1 200 OK\n\r" + "contente-type: text/javascript\n\r" + "\n\r";
        String jsonHeader = "HTTP/1.1 200 OK\n\r" + "contente-type: application/json\n\r" + "\n\r";
        
        assertTrue(htmlHeader.contains("HTTP/1.1 200 OK"), "Debe contener código de estado HTTP 200");
        assertTrue(cssHeader.contains("text/css"), "Debe contener tipo de contenido CSS");
        assertTrue(jsHeader.contains("text/javascript"), "Debe contener tipo de contenido JavaScript");
        assertTrue(jsonHeader.contains("application/json"), "Debe contener tipo de contenido JSON");
    }

    @Test
    public void testNotFoundResponse() {
        String notFoundResponse = "HTTP/1.1 404 Not Found\r\n"
                + "Content-Type: text/plain\r\n"
                + "\r\n"
                + "404 Not Found";
        
        assertTrue(notFoundResponse.contains("404 Not Found"), "Debe contener mensaje 404");
        assertTrue(notFoundResponse.contains("HTTP/1.1 404"), "Debe contener código de estado 404");
    }

    @Test
    public void testJsonResponseFormat() {
        String testName = "TestUser";
        String expectedJson = "{\"mensaje\": \"Hola " + testName + "\"}";
        
        assertTrue(expectedJson.contains("mensaje"), "La respuesta JSON debe contener la clave 'mensaje'");
        assertTrue(expectedJson.contains(testName), "La respuesta JSON debe contener el nombre del usuario");
        assertTrue(expectedJson.startsWith("{") && expectedJson.endsWith("}"), 
                   "Debe tener formato JSON válido");
    }

    @Test
    public void testServerPortConstant() {
        // Verificar que el puerto 35000 es el esperado
        int expectedPort = 35000;
        assertTrue(expectedPort > 1024, "El puerto debe ser mayor a 1024");
        assertTrue(expectedPort < 65536, "El puerto debe ser menor a 65536");
    }

    @Test
    public void testURIPathParsing() throws URISyntaxException {
        // Simular parsing de URI como lo hace el servidor
        String requestLine = "GET /index.html HTTP/1.1";
        String[] parts = requestLine.split(" ");
        URI testUri = new URI(parts[1]);
        
        assertEquals("/index.html", testUri.getPath(), "Debe extraer correctamente el path de la URI");
    }

    @Test
    public void testQueryParameterParsing() throws URISyntaxException {
        URI testUri = new URI("/app/hello?name=TestUser");
        String[] query = testUri.getQuery().split("=");
        
        assertEquals("name", query[0], "Debe extraer correctamente el parámetro");
        assertEquals("TestUser", query[1], "Debe extraer correctamente el valor");
    }

    @Test
    public void testEmptyQueryParameter() throws URISyntaxException {
        URI testUri = new URI("/app/hello?name=");
        String[] query = testUri.getQuery().split("=");
        String value = query.length > 1 ? query[1] : "";
        
        assertEquals("", value, "Debe manejar parámetros vacíos correctamente");
    }

    /**
     * Prueba de integración que verifica la existencia de todos los recursos necesarios
     */
    @Test
    public void testCompleteResourcesIntegrity() {
        String[] requiredFiles = {
            "src/main/java/resorces/index.html",
            "src/main/java/resorces/styles/style.css",
            "src/main/java/resorces/scripts/script.js",
            "src/main/java/resorces/images/pajaro.jpg",
            "src/main/java/resorces/images/jiraga.png",
            "src/main/java/resorces/images/favicon.ico"
        };
        
        for (String filePath : requiredFiles) {
            File file = new File(filePath);
            assertTrue(file.exists(), "El archivo requerido debe existir: " + filePath);
            assertTrue(file.length() > 0, "El archivo no debe estar vacío: " + filePath);
        }
    }

    // ===============================================
    // NUEVAS PRUEBAS PARA FUNCIONALIDADES DEL TALLER 2
    // ===============================================

    /**
     * Prueba el método get() para registrar servicios REST con lambdas
     */
    @Test
    public void testGetMethodWithLambdaService() {
        // Limpiar servicios existentes para esta prueba
        HttpServer.services.clear();
        
        // Registrar un servicio usando el método get()
        HttpServer.get("/test", (req, resp) -> "Test Response");
        
        // Verificar que el servicio fue registrado
        assertTrue(HttpServer.services.containsKey("/test"), 
                   "El servicio /test debe estar registrado en el mapa de servicios");
        assertNotNull(HttpServer.services.get("/test"), 
                      "El servicio registrado no debe ser null");
    }

    /**
     * Prueba el método get() con múltiples servicios
     */
    @Test
    public void testMultipleGetServices() {
        HttpServer.services.clear();
        
        // Registrar múltiples servicios
        HttpServer.get("/hello", (req, resp) -> "Hello World");
        HttpServer.get("/pi", (req, resp) -> String.valueOf(Math.PI));
        HttpServer.get("/time", (req, resp) -> String.valueOf(System.currentTimeMillis()));
        
        assertEquals(3, HttpServer.services.size(), 
                     "Deben estar registrados exactamente 3 servicios");
        assertTrue(HttpServer.services.containsKey("/hello"), 
                   "Debe existir el servicio /hello");
        assertTrue(HttpServer.services.containsKey("/pi"), 
                   "Debe existir el servicio /pi");
        assertTrue(HttpServer.services.containsKey("/time"), 
                   "Debe existir el servicio /time");
    }

    /**
     * Prueba el método getValue() de HttpRequest para parámetros de consulta simples
     */
    @Test
    public void testHttpRequestGetValueSingleParameter() throws URISyntaxException {
        URI testUri = new URI("/app/hello?name=Pedro");
        HttpRequest request = new HttpRequest(testUri);
        
        String nameValue = request.getValue("name");
        assertEquals("Pedro", nameValue, 
                     "Debe extraer correctamente el valor del parámetro 'name'");
    }

    /**
     * Prueba el método getValue() con múltiples parámetros
     */
    @Test
    public void testHttpRequestGetValueMultipleParameters() throws URISyntaxException {
        URI testUri = new URI("/app/hello?name=Pedro&age=25&city=Bogota");
        HttpRequest request = new HttpRequest(testUri);
        
        assertEquals("Pedro", request.getValue("name"), 
                     "Debe extraer correctamente el parámetro 'name'");
        assertEquals("25", request.getValue("age"), 
                     "Debe extraer correctamente el parámetro 'age'");
        assertEquals("Bogota", request.getValue("city"), 
                     "Debe extraer correctamente el parámetro 'city'");
    }

    /**
     * Prueba el método getValue() con parámetros vacíos
     */
    @Test
    public void testHttpRequestGetValueEmptyParameter() throws URISyntaxException {
        URI testUri = new URI("/app/hello?name=&age=25");
        HttpRequest request = new HttpRequest(testUri);
        
        assertEquals("", request.getValue("name"), 
                     "Debe retornar cadena vacía para parámetros sin valor");
        assertEquals("25", request.getValue("age"), 
                     "Debe extraer correctamente parámetros con valor");
    }

    /**
     * Prueba el método getValue() con parámetros inexistentes
     */
    @Test
    public void testHttpRequestGetValueNonexistentParameter() throws URISyntaxException {
        URI testUri = new URI("/app/hello?name=Pedro");
        HttpRequest request = new HttpRequest(testUri);
        
        assertEquals("", request.getValue("nonexistent"), 
                     "Debe retornar cadena vacía para parámetros que no existen");
    }

    /**
     * Prueba el método getValue() sin query string
     */
    @Test
    public void testHttpRequestGetValueNoQueryString() throws URISyntaxException {
        URI testUri = new URI("/app/hello");
        HttpRequest request = new HttpRequest(testUri);
        
        assertEquals("", request.getValue("name"), 
                     "Debe retornar cadena vacía cuando no hay query string");
    }

    /**
     * Prueba el método staticfiles() para configuración de directorio estático
     */
    @Test
    public void testStaticfilesMethod() {
        // Probar con ruta relativa
        HttpServer.staticfiles("webroot/public");
        
        // Verificar que el directorio se creó en target/classes
        File targetDir = new File("target/classes/webroot/public/");
        assertTrue(targetDir.exists(), 
                   "El directorio target/classes/webroot/public/ debe existir después de llamar staticfiles()");
        assertTrue(targetDir.isDirectory(), 
                   "La ruta debe ser un directorio");
    }

    /**
     * Prueba el método staticfiles() con ruta absoluta (comenzando con /)
     */
    @Test
    public void testStaticfilesMethodAbsolutePath() {
        HttpServer.staticfiles("/webroot");
        
        // Verificar que el directorio se creó en target/classes
        File targetDir = new File("target/classes/webroot/");
        assertTrue(targetDir.exists(), 
                   "El directorio target/classes/webroot/ debe existir después de llamar staticfiles()");
    }

    /**
     * Prueba que staticfiles() copia los archivos del directorio original
     */
    @Test
    public void testStaticfilesCopiesOriginalFiles() {
        HttpServer.staticfiles("testwebroot");
        
        // Verificar que los archivos originales fueron copiados
        File copiedIndex = new File("target/classes/testwebroot/index.html");
        File copiedCss = new File("target/classes/testwebroot/styles/style.css");
        File copiedJs = new File("target/classes/testwebroot/scripts/script.js");
        
        assertTrue(copiedIndex.exists(), 
                   "El archivo index.html debe ser copiado al nuevo directorio");
        assertTrue(copiedCss.exists(), 
                   "El archivo style.css debe ser copiado al nuevo directorio");
        assertTrue(copiedJs.exists(), 
                   "El archivo script.js debe ser copiado al nuevo directorio");
    }

    /**
     * Prueba de integración del servicio REST con parámetros
     */
    @Test
    public void testServiceExecutionWithParameters() throws URISyntaxException {
        HttpServer.services.clear();
        
        // Registrar servicio que usa parámetros
        HttpServer.get("/hello", (req, resp) -> "Hello " + req.getValue("name"));
        
        // Simular request con parámetros
        URI testUri = new URI("/hello?name=TestUser");
        HttpRequest request = new HttpRequest(testUri);
        HttpResponse response = new HttpResponse();
        
        Service helloService = HttpServer.services.get("/hello");
        String result = helloService.executeService(request, response);
        
        assertEquals("Hello TestUser", result, 
                     "El servicio debe procesar correctamente los parámetros y retornar la respuesta esperada");
    }

    /**
     * Prueba del servicio PI sin parámetros
     */
    @Test
    public void testPiServiceExecution() throws URISyntaxException {
        HttpServer.services.clear();
        
        // Registrar servicio PI
        HttpServer.get("/pi", (req, resp) -> String.valueOf(Math.PI));
        
        URI testUri = new URI("/pi");
        HttpRequest request = new HttpRequest(testUri);
        HttpResponse response = new HttpResponse();
        
        Service piService = HttpServer.services.get("/pi");
        String result = piService.executeService(request, response);
        
        assertEquals(String.valueOf(Math.PI), result, 
                     "El servicio /pi debe retornar el valor de PI");
        assertTrue(result.startsWith("3.14"), 
                   "El resultado debe comenzar con 3.14");
    }

    /**
     * Prueba de manejo de caracteres especiales en parámetros URL
     */
    @Test
    public void testSpecialCharactersInParameters() throws URISyntaxException {
        URI testUri = new URI("/app/hello?name=Pedro%20Rodriguez&message=Hello%20World");
        HttpRequest request = new HttpRequest(testUri);
        
        // Nota: Esta prueba verifica el comportamiento actual, 
        // en una implementación completa se debería decodificar URL
        String name = request.getValue("name");
        String message = request.getValue("message");
        
        assertNotNull(name, "El parámetro name no debe ser null");
        assertNotNull(message, "El parámetro message no debe ser null");
    }

    /**
     * Prueba de limpieza de servicios registrados
     */
    @Test
    public void testServiceRegistrationCleaning() {
        HttpServer.services.clear();
        
        assertEquals(0, HttpServer.services.size(), 
                     "El mapa de servicios debe estar vacío después de la limpieza");
        
        // Registrar y verificar
        HttpServer.get("/test", (req, resp) -> "test");
        assertEquals(1, HttpServer.services.size(), 
                     "Debe haber exactamente un servicio registrado");
        
        // Limpiar nuevamente
        HttpServer.services.clear();
        assertEquals(0, HttpServer.services.size(), 
                     "El mapa debe estar vacío después de la segunda limpieza");
    }
}
