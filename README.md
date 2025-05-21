# **Comunicación entre servicios (Inter-Service Communication)**

# Índice
### Definición del proyecto
### Tecnología aplicada
### Pruebas
### Índice de proyectos Spring

#  **Definición del proyecto**

El objetivo de este proyecto es demostrar la comunicación HTTP en servicios desarrollados con Spring Boot. 

No se abordarán conceptos como mensajería, llamadas asincrónicas, circuit breaker, gRPC ni balanceo de carga. 

El enfoque principal será ilustrar las llamadas REST síncronas entre microservicios, destacando las librerías utilizadas para facilitar su implementación.

Se utilizan dos librerías para realizar llamadas a APIs que exponen servicios: RestTemplate y Feign.

El proyecto consta de tres APIs:

- API Consumer: Actúa como cliente y consume los servicios de la API Provider y API Processor.
- API Provider: Expone servicios y provee información a la API Consumer. Las llamadas a esta API se realizan mediante RestTemplate.
- API Processor: Expone servicios, provee y procesa información para la API Consumer. Las llamadas a esta API se realizan a través de Feign.

Adicionalmente, se implementaron:

- Interceptor centralizado: Se agregó un interceptor para manejar errores en todas las llamadas a servicios, tanto con RestTemplate como con Feign.

- Validación de autorización: Se implementó una validación del Authorization que se envia en el Header a las APIs que exponen servicios. Se utilizan tokens estáticos para este propósito.

- Patrón Retry: Se aplicó el patrón de reintentos en operaciones idempotentes para garantizar la resiliencia en caso de fallos transitorios.


#  **Tecnología aplicada**

La aplicación está desarrollada utilizando Spring Boot 3.3.11 y Java 17.

## Dependencias del proyecto:
- spring-boot-starter-web: Para construir aplicaciones web. 
- spring-cloud-starter-openfeign: Para realizar llamadas a APIs externas.
- spring-retry y spring-boot-starter-aop: Para gestionar los reintentos en operaciones fallidas.
- spring-boot-starter-validation: Facilita la validación de datos en la aplicación.
- lombok: Para reducir el código repetitivo mediante anotaciones.
- springdoc-openapi-starter-webmvc-ui: Para la documentación y prueba de las APIs (Swagger).


#  **Pruebas**
Para probar la API se puede ingresar a:

http://localhost:8080/api-consumer/swagger-ui/index.html

http://localhost:8080/api-consumer-docs

---
#  **Índice de proyectos Spring**
##  **Proyectos Spring Boot**
- [Response Uniforme](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Response-Wrapper)
- [LogBack](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Logback)
- [Profile](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Profile)
- [Spring Doc](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Swagger)
- [Validate](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Validate)
- [Inter-Service Communication](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Inter-Service-Communication)
##  **Proyectos Spring Data**
- [Spring Data + Redis(Cache)](https://github.com/pabloEmanuelIgoldi/Spring-Data-Redis)
- [Spring Data + Mongo DB (NoSQL)](https://github.com/pabloEmanuelIgoldi/Spring-Data-Mongo)


