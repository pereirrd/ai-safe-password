# AI Safe Password API

A modern, AI-powered password generation and validation service built with **API-First** approach using Micronaut and LangChain4j.

## 🚀 Features

- **AI-Powered Password Generation**: Generate secure passwords using OpenAI GPT models
- **AI Password Validation**: Validate passwords with intelligent feedback using AI
- **Regular Expression Validation**: Traditional regex-based password validation
- **API-First Design**: OpenAPI/Swagger specification with auto-generated code
- **Comprehensive Testing**: Full unit test coverage with Mockito
- **Modern Java**: Built with Java 21 and latest frameworks
- **Production Ready**: Proper logging, error handling, and monitoring

## 🏗️ Architecture

### API-First Approach
This project follows the **API-First** methodology:
1. **OpenAPI Specification**: API design starts with `swagger.yml`
2. **Code Generation**: Controllers and models auto-generated from spec
3. **Implementation**: Business logic implemented in generated structure
4. **Documentation**: Interactive Swagger UI for API exploration

### Frameworks & Technologies

#### Core Framework
- **Micronaut 4.9.1**: Modern, JVM-based framework for building microservices
  - Dependency injection and AOP
  - HTTP server with Netty
  - Configuration management
  - Built-in validation

#### AI Integration
- **LangChain4j**: Java framework for LLM applications
  - OpenAI integration for password generation and validation
  - Structured AI service interfaces
  - Prompt engineering and response parsing

#### Development Tools
- **Lombok**: Reduces boilerplate code
- **JUnit 5 + Mockito**: Comprehensive testing
- **Maven**: Build and dependency management
- **OpenAPI Generator**: Code generation from API specs

## 📁 Project Structure

```
ai-safe-password/
├── src/
│   ├── main/
│   │   ├── java/com/password/
│   │   │   ├── Application.java                 # Main application class
│   │   │   ├── controller/                      # API Controllers
│   │   │   │   ├── AiPasswordApiImpl.java      # AI password endpoints
│   │   │   │   └── RegularExpressionPasswordApiImpl.java # Regex endpoints
│   │   │   ├── domain/                          # Business Logic
│   │   │   │   ├── ai/
│   │   │   │   │   ├── creator/
│   │   │   │   │   │   ├── AIPasswordCreator.java        # AI password generation
│   │   │   │   │   │   └── AIPasswordCreatorDecorator.java # Generation with validation
│   │   │   │   │   └── validator/
│   │   │   │   │       ├── AIPasswordValidator.java      # AI password validation
│   │   │   │   │       └── AIPasswordValidatorDecorator.java # Validation adapter
│   │   │   │   └── expression/
│   │   │   │       ├── PasswordValidator.java   # Regex password validation
│   │   │   │       └── PasswordRules.java       # Password validation rules
│   │   │   └── model/                           # Data Models (Auto-generated)
│   │   │       ├── PasswordResponse.java
│   │   │       ├── PasswordResponseStatus.java
│   │   │       ├── ValidateRequest.java
│   │   │       └── ErrorResponse.java
│   │   ├── core/
│   │   │   └── HttpResponseUtils.java          # HTTP response utilities
│   │   └── resources/
│   │       ├── application.yml                 # Application configuration
│   │       ├── swagger.yml                     # OpenAPI specification
│   │       └── logback.xml                     # Logging configuration
│   └── test/                                   # Unit Tests
│       └── java/com/password/
│           ├── controller/
│           │   ├── AiPasswordApiImplTest.java
│           │   └── RegularExpressionPasswordApiImplTest.java
│           └── domain/
│               ├── ai/creator/AIPasswordCreatorDecoratorTest.java
│               └── expression/PasswordValidatorTest.java
├── docs/                                       # Documentation
│   ├── chat.md                                # Development conversation
│   └── requests.md                            # API request examples
├── target/                                    # Generated code and build artifacts
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

## 🛠️ Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6+
- OpenAI API key

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/pereirrd/ai-safe-password
   cd ai-safe-password
   ```

2. **Create .env file on root of project with your OpenAi API key**
   ```bash
   OPENAI_API_KEY="your-openai-api-key"
   ```

3. **Build the project**
   ```bash
   mvn clean compile
   ```

4. **Use VSCode to run the application, or**
   ```bash
   mvn mn:run
   ```

## 🧪 Testing

### Endpoints

#### AI Password Generation
```
curl --location 'http://localhost:8080/ai/generate'
```
Generates a secure password using AI and validates it before returning.

#### AI Password Validation
```
curl --location 'http://localhost:8080/ai/validate' \
--header 'Content-Type: application/json' \
--data '{
    "password": "nv77678Klsd2222"
}'
```

#### Regular Expression Password Validation
```
curl --location 'http://localhost:8080/validate' \
--header 'Content-Type: application/json' \
--data '{
    "password": "nv77678Klsd!"
}'
```

### Response Format
```json
{
  "password": "GeneratedPassword123!",
  "status": "VALID",
  "message": "Awesome password, bro!"
}
```

### Run Tests
```bash
mvn test
```

### Test Coverage
- **Unit Tests**: 100% coverage of business logic
- **Integration Tests**: API endpoint testing
- **Mock Testing**: Comprehensive mocking with Mockito

## 📖 Documentation


### Access the API Documentation
   - **Swagger UI**: http://localhost:8080/swagger-ui
   - **OpenAPI Spec**: http://localhost:8080/swagger/ai-safe-password-0.0.yml

### 🗣️ Development Chat
For a detailed look at the development process, technical decisions, and problem-solving approaches, check out our **[Development Chat](docs/chat.md)**. This document contains:
- Complete development conversation
- Technical decisions and architecture patterns
- Problem-solving approaches and solutions
- Lessons learned during development
- Code implementation details
- Testing strategies and approaches

### Framework Documentation
- [Micronaut Documentation](https://docs.micronaut.io/4.9.1/guide/index.html)
- [LangChain4j Documentation](https://github.com/langchain4j/langchain4j)
- [OpenAPI Specification](https://swagger.io/specification/)

## 🔧 Configuration

### Application Configuration (`application.yml`)
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
    api-key: ${OPENAI_API_KEY} # from .env file
    model-name: gpt-4o-mini
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## 🙏 Acknowledgments

- **Micronaut Team** for the excellent framework
- **LangChain4j Team** for AI integration capabilities
- **OpenAI** for providing the AI models
- **OpenAPI Initiative** for API specification standards

---


**Built with ❤️ using API-First approach and modern Java technologies**


