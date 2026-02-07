[English](README.md) | [Español](README.es.md)

# AI Safe Password API

Um serviço moderno de geração e validação de senhas alimentado por IA, construído com abordagem **API-First** usando Micronaut e LangChain4j.

## 🚀 Funcionalidades

- **Geração de Senhas com IA**: Gere senhas seguras usando modelos GPT da OpenAI
- **Validação de Senhas com IA**: Valide senhas com feedback inteligente usando IA
- **Validação por Expressão Regular**: Validação tradicional de senhas baseada em regex
- **Design API-First**: Especificação OpenAPI/Swagger com código gerado automaticamente
- **Testes Abrangentes**: Cobertura completa de testes unitários com Mockito
- **Java Moderno**: Construído com Java 21 e frameworks mais recentes
- **Pronto para Produção**: Logging adequado, tratamento de erros e monitoramento

## 🏗️ Arquitetura

### Abordagem API-First
Este projeto segue a metodologia **API-First**:
1. **Especificação OpenAPI**: O design da API começa com `swagger.yml`
2. **Geração de Código**: Controladores e modelos gerados automaticamente a partir da especificação
3. **Implementação**: Lógica de negócio implementada na estrutura gerada
4. **Documentação**: Swagger UI interativo para exploração da API

### Frameworks e Tecnologias

#### Framework Principal
- **Micronaut 4.9.1**: Framework moderno baseado em JVM para construção de microsserviços
  - Injeção de dependência e AOP
  - Servidor HTTP com Netty
  - Gerenciamento de configuração
  - Validação integrada

#### Integração com IA
- **LangChain4j**: Framework Java para aplicações LLM
  - Integração com OpenAI para geração e validação de senhas
  - Interfaces estruturadas de serviços de IA
  - Engenharia de prompts e parsing de respostas

#### Ferramentas de Desenvolvimento
- **Lombok**: Reduz código boilerplate
- **JUnit 5 + Mockito**: Testes abrangentes
- **Maven**: Build e gerenciamento de dependências
- **OpenAPI Generator**: Geração de código a partir de especificações de API

## 📁 Estrutura do Projeto

```
ai-safe-password/
├── src/
│   ├── main/
│   │   ├── java/com/password/
│   │   │   ├── Application.java                 # Classe principal da aplicação
│   │   │   ├── controller/                      # Controladores da API
│   │   │   │   ├── AiPasswordApiImpl.java      # Endpoints de senha com IA
│   │   │   │   └── RegularExpressionPasswordApiImpl.java # Endpoints regex
│   │   │   ├── domain/                          # Lógica de Negócio
│   │   │   │   ├── ai/
│   │   │   │   │   ├── creator/
│   │   │   │   │   │   ├── AIPasswordCreator.java        # Geração de senha com IA
│   │   │   │   │   │   └── AIPasswordCreatorDecorator.java # Geração com validação
│   │   │   │   │   └── validator/
│   │   │   │   │       ├── AIPasswordValidator.java      # Validação de senha com IA
│   │   │   │   │       └── AIPasswordValidatorDecorator.java # Adaptador de validação
│   │   │   │   └── expression/
│   │   │   │       ├── PasswordValidator.java   # Validação de senha por regex
│   │   │   │       └── PasswordRules.java       # Regras de validação de senha
│   │   │   └── model/                           # Modelos de Dados (Gerados automaticamente)
│   │   │       ├── PasswordResponse.java
│   │   │       ├── PasswordResponseStatus.java
│   │   │       ├── ValidateRequest.java
│   │   │       └── ErrorResponse.java
│   │   ├── core/
│   │   │   └── HttpResponseUtils.java          # Utilitários de resposta HTTP
│   │   └── resources/
│   │       ├── application.yml                 # Configuração da aplicação
│   │       ├── swagger.yml                     # Especificação OpenAPI
│   │       └── logback.xml                     # Configuração de logging
│   └── test/                                   # Testes Unitários
│       └── java/com/password/
│           ├── controller/
│           │   ├── AiPasswordApiImplTest.java
│           │   └── RegularExpressionPasswordApiImplTest.java
│           └── domain/
│               ├── ai/creator/AIPasswordCreatorDecoratorTest.java
│               └── expression/PasswordValidatorTest.java
├── docs/                                       # Documentação
│   ├── chat.md                                # Conversa de desenvolvimento
│   └── requests.md                            # Exemplos de requisições da API
├── target/                                    # Código gerado e artefatos de build
├── pom.xml                                    # Configuração Maven
└── README.md                                  # Este arquivo
```

## 🛠️ Começando

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6+
- Chave de API da OpenAI

### Instalação

1. **Clone o repositório**
   ```bash
   git clone https://github.com/pereirrd/ai-safe-password
   cd ai-safe-password
   ```

2. **Crie um arquivo .env na raiz do projeto com sua chave de API da OpenAI**
   ```bash
   OPENAI_API_KEY="sua-chave-de-api-openai"
   ```

3. **Compile o projeto**
   ```bash
   mvn clean compile
   ```

4. **Use o VSCode para executar a aplicação, ou**
   ```bash
   mvn mn:run
   ```

## 🧪 Testes

### Endpoints

#### Geração de Senha com IA
```
curl --location 'http://localhost:8080/ai/generate'
```
Gera uma senha segura usando IA e valida antes de retornar.

#### Validação de Senha com IA
```
curl --location 'http://localhost:8080/ai/validate' \
--header 'Content-Type: application/json' \
--data '{
    "password": "nv77678Klsd2222"
}'
```

#### Validação de Senha por Expressão Regular
```
curl --location 'http://localhost:8080/validate' \
--header 'Content-Type: application/json' \
--data '{
    "password": "nv77678Klsd!"
}'
```

### Formato de Resposta
```json
{
  "password": "GeneratedPassword123!",
  "status": "VALID",
  "message": "Awesome password, bro!"
}
```

### Executar Testes
```bash
mvn test
```

### Cobertura de Testes
- **Testes Unitários**: 100% de cobertura da lógica de negócio
- **Testes de Integração**: Testes de endpoints da API
- **Testes com Mock**: Mocking abrangente com Mockito

## 📖 Documentação


### Acessar a Documentação da API
   - **Swagger UI**: http://localhost:8080/swagger-ui
   - **Especificação OpenAPI**: http://localhost:8080/swagger/ai-safe-password-0.0.yml

### 🗣️ Chat de Desenvolvimento
Para uma visão detalhada do processo de desenvolvimento, decisões técnicas e abordagens de resolução de problemas, confira nosso **[Chat de Desenvolvimento](docs/chat.md)**. Este documento contém:
- Conversa completa de desenvolvimento
- Decisões técnicas e padrões de arquitetura
- Abordagens e soluções de resolução de problemas
- Lições aprendidas durante o desenvolvimento
- Detalhes de implementação de código
- Estratégias e abordagens de testes

### Documentação dos Frameworks
- [Documentação do Micronaut](https://docs.micronaut.io/4.9.1/guide/index.html)
- [Documentação do LangChain4j](https://github.com/langchain4j/langchain4j)
- [Especificação OpenAPI](https://swagger.io/specification/)

## 🔧 Configuração

### Configuração da Aplicação (`application.yml`)
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
    api-key: ${OPENAI_API_KEY} # do arquivo .env
    model-name: gpt-4o-mini
```

## 🤝 Contribuindo

1. Faça um fork do repositório
2. Crie uma branch para sua feature
3. Faça suas alterações
4. Adicione testes para novas funcionalidades
5. Certifique-se de que todos os testes passam
6. Envie um pull request

## 🙏 Agradecimentos

- **Equipe Micronaut** pelo excelente framework
- **Equipe LangChain4j** pelas capacidades de integração com IA
- **OpenAI** por fornecer os modelos de IA
- **Iniciativa OpenAPI** pelos padrões de especificação de API

---

**Construído com ❤️ usando abordagem API-First e tecnologias Java modernas**
