# Executando o sistema

## Execução local
### Requisitos
- Banco de dados MySQL
- Java
```
mvn clean compile
java -jar target/gerenciador-0.0.1-SNAPSHOT.jar
``` 

## Execução docker compose
### Requisitos
- docker-compose

```
mvn clean compile jib:dockerBuild -DskipTests
docker-compose up
```
# Documentação

[Swagger ui](http://localhost:8080/swagger-ui/index.html)
