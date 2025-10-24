## **🧠 Desafio Técnico – Sistema de Gestão de Projetos e Demandas**

RESTful API em **Java 17 + Spring Boot 3** para gerenciar projetos e tarefas (demandas) de uma empresa, com **autenticação Basic Auth**.

---

## 📝 Funcionalidades

- CRUD de **Projetos** e **Tarefas**
- Filtragem de tarefas por **status**, **prioridade** e **projeto**
- Atualização de status da tarefa (**TODO / DOING / DONE**)
- Validação de campos com **Bean Validation**
- Tratamento global de erros com `@ControllerAdvice`
- **Autenticação simples com Basic Auth** para todos os endpoints
- Testes automatizados unitários e de integração com **Rest Assured**
- Documentação da API com **Swagger/OpenAPI**
- Containerização com **Docker / docker-compose**
- MapStruct para mapeamento entre DTOs e Entities, garantindo código limpo e testável
- **Lombok** para reduzir boilerplate em **entities, DTOs e mappers** (`@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`)

---

## 🛠️ Tecnologias

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Docker / docker-compose**
- **Springdoc OpenAPI (Swagger UI)**
- **MapStruct** (DTO <-> Entity mapping)
- **Lombok** (redução de boilerplate em classes Java)
- **Rest Assured** (Integration testing of REST endpoints)
- **Spring Security** (Basic Auth)

---

## 💻 Rodando localmente (PostgreSQL)

1️⃣ Configure o `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pmdb
    username: postgres
    password: 123
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

2️⃣ Clone o repositório:

```bash
git clone https://github.com/MarcoAntonioLobo/dev.matheuslf.desafio.inscritos.git
cd dev.matheuslf.desafio.inscritos
```

3️⃣ Compile e rode:

```bash
./mvnw clean package
./mvnw spring-boot:run
```

A API estará disponível em: [http://localhost:8080](http://localhost:8080)

> 🔐 **Todos os endpoints estão protegidos com Basic Auth**  
> Usuário: `admin`  
> Senha: `123`

4️⃣ Acesse a documentação Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
> Observação: o Swagger também está protegido, será necessário autenticar.

---

## 🐳 Rodando com Docker

1️⃣ Build da imagem:

```bash
docker build -t projectmanager .
```

2️⃣ Subir containers com PostgreSQL:

```bash
docker-compose up --build
```

A API será inicializada automaticamente e conectada ao PostgreSQL do docker-compose. Logs aparecem no terminal.

> 🔐 **Todos os endpoints requerem Basic Auth** (usuário `admin`, senha `123`).

---

## 3️⃣ Endpoints de exemplo

**Criar projeto:**

```bash
curl -u admin:123 -X POST http://localhost:8080/projects \
-H "Content-Type: application/json" \
-d '{"name":"API Teste","description":"Projeto API","startDate":"2025-10-19"}'
```

**Listar projetos:**

```bash
curl -u admin:123 -X GET http://localhost:8080/projects
```

**Criar tarefa vinculada a projeto:**

```bash
curl -u admin:123 -X POST http://localhost:8080/tasks \
-H "Content-Type: application/json" \
-d '{"title":"Fazer endpoint","description":"Implementar API REST","priority":"HIGH","dueDate":"2025-10-25","projectId":"<UUID_DO_PROJETO>"}'
```

---

## 🧪 Testes

Rodar todos os testes unitários e de integração:

```bash
./mvnw test
```

Testes individuais (exemplo: TaskServiceTest):

```bash
./mvnw -Dtest=TaskServiceTest test
```

> 🔐 **Nos testes de integração, Basic Auth é incluído nas requisições** (`httpBasic()` para MockMvc e `.auth().basic()` para Rest Assured).  
Testes incluem validação de mapeamentos do MapStruct entre DTOs e Entities, além de testes de integração de endpoints com Rest Assured.
Lombok garante que getters, setters e builders funcionem corretamente nos DTOs e Entities.

---

## ⚙️ Configurações

- Logs SQL podem ser habilitados em `application.yml` (já mostrado acima).  
- Para usar PostgreSQL local sem Docker, configure `spring.datasource.*` no `application.yml`.  
- **MapStruct** já está configurado como componentModel = "spring" permitindo injeção de dependência direta nos serviços. 
- **Lombok** já está integrado para reduzir boilerplate nas classes. 
- Spring Security já está configurado para **Basic Auth** nos endpoints.

---

## 📌 Observações

- Projeto iniciado a partir do fork do repositório [https://github.com/matheuslf/dev.matheuslf.desafio.inscritos](https://github.com/matheuslf.dev.matheuslf.desafio.inscritos)  
- Desenvolvido como parte do desafio técnico do processo seletivo SIS Innov & Tech  
- Licença: uso exclusivo para processos seletivos, não comercial

---

## 📬 Contato

- GitHub: [https://github.com/MarcoAntonioLobo](https://github.com/MarcoAntonioLobo)  
- LinkedIn: [https://www.linkedin.com/in/marco-antonio-lobo-35568628b/](https://www.linkedin.com/in/marco-antonio-lobo-35568628b/)  
- Email: [marcoantoniolobo82@gmail.com](mailto:marcoantoniolobo82@gmail.com)

