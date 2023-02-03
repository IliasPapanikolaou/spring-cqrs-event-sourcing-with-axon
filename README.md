# CQRS and Event-Sourcing

CQRS stands for Commmand and Query Responsibility Segregation

Commands in CQRS: POST, PUT, DELETE, PATCH (Write DB) and Event-Sourcing (Event Store)
Queries in CQRS: GET (Read DB)

> <https://developer.axoniq.io/event-sourcing/overview>

Axon Server Dockerfile:

```bash
docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
```

Axon Server url: <http://localhost:8024>

## Command API

- Command
- Command Handler
- Events Handler

## Query API

- Query
- Query Handler
- Events handler

## Maven dependencies for Axon server

```xml
<dependency>
    <groupId>org.axonframework</groupId>
    <artifactId>axon-spring-boot-starter</artifactId>
    <version>4.7.1</version>
</dependency>

<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>31.1-jre</version>
</dependency>
```

## REST API

POST: <http://localhost:8081/products>

```json
{
    "name": "Playstation 5",
    "price": 600,
    "quantity": 1
}
```

GET: <http://localhost:8081/products>
