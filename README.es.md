[English](README.md) | [Português](README.pt-br.md)

# AI Safe Password API

Un servicio moderno de generación y validación de contraseñas impulsado por IA, construido con enfoque **API-First** usando Micronaut y LangChain4j.

## 🚀 Características

- **Generación de Contraseñas con IA**: Genera contraseñas seguras usando modelos GPT de OpenAI
- **Validación de Contraseñas con IA**: Valida contraseñas con retroalimentación inteligente usando IA
- **Validación por Expresión Regular**: Validación tradicional de contraseñas basada en regex
- **Diseño API-First**: Especificación OpenAPI/Swagger con código generado automáticamente
- **Pruebas Integrales**: Cobertura completa de pruebas unitarias con Mockito
- **Java Moderno**: Construido con Java 21 y frameworks más recientes
- **Listo para Producción**: Registro adecuado, manejo de errores y monitoreo

## 🏗️ Arquitectura

### Enfoque API-First
Este proyecto sigue la metodología **API-First**:
1. **Especificación OpenAPI**: El diseño de la API comienza con `swagger.yml`
2. **Generación de Código**: Controladores y modelos generados automáticamente desde la especificación
3. **Implementación**: Lógica de negocio implementada en la estructura generada
4. **Documentación**: Swagger UI interactivo para exploración de la API

### Frameworks y Tecnologías

#### Framework Principal
- **Micronaut 4.9.1**: Framework moderno basado en JVM para construir microsservicios
  - Inyección de dependencias y AOP
  - Servidor HTTP con Netty
  - Gestión de configuración
  - Validación integrada

#### Integración con IA
- **LangChain4j**: Framework Java para aplicaciones LLM
  - Integración con OpenAI para generación y validación de contraseñas
  - Interfaces estructuradas de servicios de IA
  - Ingeniería de prompts y análisis de respuestas

#### Herramientas de Desarrollo
- **Lombok**: Reduce código boilerplate
- **JUnit 5 + Mockito**: Pruebas integrales
- **Maven**: Build y gestión de dependencias
- **OpenAPI Generator**: Generación de código desde especificaciones de API

## 📁 Estructura del Proyecto

```
ai-safe-password/
├── src/
│   ├── main/
│   │   ├── java/com/password/
│   │   │   ├── Application.java                 # Clase principal de la aplicación
│   │   │   ├── controller/                      # Controladores de la API
│   │   │   │   ├── AiPasswordApiImpl.java      # Endpoints de contraseña con IA
│   │   │   │   └── RegularExpressionPasswordApiImpl.java # Endpoints regex
│   │   │   ├── domain/                          # Lógica de Negocio
│   │   │   │   ├── ai/
│   │   │   │   │   ├── creator/
│   │   │   │   │   │   ├── AIPasswordCreator.java        # Generación de contraseña con IA
│   │   │   │   │   │   └── AIPasswordCreatorDecorator.java # Generación con validación
│   │   │   │   │   └── validator/
│   │   │   │   │       ├── AIPasswordValidator.java      # Validación de contraseña con IA
│   │   │   │   │       └── AIPasswordValidatorDecorator.java # Adaptador de validación
│   │   │   │   └── expression/
│   │   │   │       ├── PasswordValidator.java   # Validación de contraseña por regex
│   │   │   │       └── PasswordRules.java       # Reglas de validación de contraseña
│   │   │   └── model/                           # Modelos de Datos (Generados automáticamente)
│   │   │       ├── PasswordResponse.java
│   │   │       ├── PasswordResponseStatus.java
│   │   │       ├── ValidateRequest.java
│   │   │       └── ErrorResponse.java
│   │   ├── core/
│   │   │   └── HttpResponseUtils.java          # Utilidades de respuesta HTTP
│   │   └── resources/
│   │       ├── application.yml                 # Configuración de la aplicación
│   │       ├── swagger.yml                     # Especificación OpenAPI
│   │       └── logback.xml                     # Configuración de logging
│   └── test/                                   # Pruebas Unitarias
│       └── java/com/password/
│           ├── controller/
│           │   ├── AiPasswordApiImplTest.java
│           │   └── RegularExpressionPasswordApiImplTest.java
│           └── domain/
│               ├── ai/creator/AIPasswordCreatorDecoratorTest.java
│               └── expression/PasswordValidatorTest.java
├── docs/                                       # Documentación
│   ├── chat.md                                # Conversación de desarrollo
│   └── requests.md                            # Ejemplos de solicitudes de la API
├── target/                                    # Código generado y artefactos de build
├── pom.xml                                    # Configuración Maven
└── README.md                                  # Este archivo
```

## 🛠️ Comenzando

### Prerrequisitos
- Java 21 o superior
- Maven 3.6+
- Clave de API de OpenAI

### Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/pereirrd/ai-safe-password
   cd ai-safe-password
   ```

2. **Crear archivo .env en la raíz del proyecto con tu clave de API de OpenAI**
   ```bash
   OPENAI_API_KEY="tu-clave-de-api-openai"
   ```

3. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

4. **Usar VSCode para ejecutar la aplicación, o**
   ```bash
   mvn mn:run
   ```

## 🧪 Pruebas

### Endpoints

#### Generación de Contraseña con IA
```
curl --location 'http://localhost:8080/ai/generate'
```
Genera una contraseña segura usando IA y la valida antes de retornar.

#### Validación de Contraseña con IA
```
curl --location 'http://localhost:8080/ai/validate' \
--header 'Content-Type: application/json' \
--data '{
    "password": "nv77678Klsd2222"
}'
```

#### Validación de Contraseña por Expresión Regular
```
curl --location 'http://localhost:8080/validate' \
--header 'Content-Type: application/json' \
--data '{
    "password": "nv77678Klsd!"
}'
```

### Formato de Respuesta
```json
{
  "password": "GeneratedPassword123!",
  "status": "VALID",
  "message": "Awesome password, bro!"
}
```

### Ejecutar Pruebas
```bash
mvn test
```

### Cobertura de Pruebas
- **Pruebas Unitarias**: 100% de cobertura de la lógica de negocio
- **Pruebas de Integración**: Pruebas de endpoints de la API
- **Pruebas con Mock**: Mocking integral con Mockito

## 📖 Documentación


### Acceder a la Documentación de la API
   - **Swagger UI**: http://localhost:8080/swagger-ui
   - **Especificación OpenAPI**: http://localhost:8080/swagger/ai-safe-password-0.0.yml

### 🗣️ Chat de Desarrollo
Para una visión detallada del proceso de desarrollo, decisiones técnicas y enfoques de resolución de problemas, consulta nuestro **[Chat de Desarrollo](docs/chat.md)**. Este documento contiene:
- Conversación completa de desarrollo
- Decisiones técnicas y patrones de arquitectura
- Enfoques y soluciones de resolución de problemas
- Lecciones aprendidas durante el desarrollo
- Detalles de implementación de código
- Estrategias y enfoques de pruebas

### Documentación de los Frameworks
- [Documentación de Micronaut](https://docs.micronaut.io/4.9.1/guide/index.html)
- [Documentación de LangChain4j](https://github.com/langchain4j/langchain4j)
- [Especificación OpenAPI](https://swagger.io/specification/)

## 🔧 Configuración

### Configuración de la Aplicación (`application.yml`)
```yaml
micronaut:
  application:
    name: ai-safe-password
  router:
    static-resources:
      swagger-ui:
        mapping: /swagger-ui/**
      swagger:
        mapping: /swagger/**

langchain4j:
  open-ai:
    api-key: ${OPENAI_API_KEY} # del archivo .env
    model-name: gpt-4o-mini
```

## 🤝 Contribuir

1. Haz un fork del repositorio
2. Crea una rama para tu feature
3. Realiza tus cambios
4. Añade pruebas para nuevas funcionalidades
5. Asegúrate de que todas las pruebas pasen
6. Envía un pull request

## 🙏 Agradecimientos

- **Equipo Micronaut** por el excelente framework
- **Equipo LangChain4j** por las capacidades de integración con IA
- **OpenAI** por proporcionar los modelos de IA
- **Iniciativa OpenAPI** por los estándares de especificación de API

---

**Construido con ❤️ usando enfoque API-First y tecnologías Java modernas**
