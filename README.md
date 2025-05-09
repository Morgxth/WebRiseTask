# Subscription Service

## Описание проекта

Это микросервис для управления пользователями и их подписками на цифровые сервисы, такие как **YouTube Premium**, **Netflix** и другие.

## Технологии

- **Backend**: Spring Boot 3
- **Язык**: Java 17
- **База данных**: PostgreSQL
- **Логирование**: SLF4J
- **Open API**: Swagger
- **Контейнеризация**: Docker, Docker Compose
- **Тесты**: Junit, Testcontainers

## Как запустить

### Клонировать репозиторий:

   ```bash
   git clone https://github.com/Morgxth/WebRiseTask.git
   cd subscription-service
```

### Запуск с помощью Docker Compose

```bash
docker-compose up --build
```

Это развернет два контейнера: приложение и базу данных PostgreSQL.

### Доступ к проекту
    API: http://localhost:8080
    PostgreSQL: localhost:5432 (если нужно подключение для разработки или отладки)

### API Документация
   Пример использования API через curl:

## Создание пользователя
POST /users

```bash
curl -X POST http://localhost:8080/users \
-H "Content-Type: application/json" \
-d '{"name": "John Doe", "email": "john.doe@example.com"}'
```
## Получение пользователя
GET /users/{id}

```bash
curl -X GET http://localhost:8080/users/1
```
## Создание подписки
POST /users/{userId}/subscriptions

```bash
curl -X POST http://localhost:8080/users/1/subscriptions \
-H "Content-Type: application/json" \
-d '{"serviceName": "Netflix", "subscriptionType": "Premium"}'
```

## Получение подписок пользователя
GET /users/{userId}/subscriptions

```bash
curl -X GET http://localhost:8080/users/1/subscriptions
```

## Получение ТОП-3 подписок
GET /subscriptions/top

```bash
curl -X GET http://localhost:8080/subscriptions/top
```

Структура проекта
```css
src/
├── main/
│    ├── java/
│    │    ├── com/
│    │    │    ├── example/
│    │    │    │    ├── restapi/
│    │    │    │    │    ├── controller/
│    │    │    │    │    ├── dto/
│    │    │    │    │    ├── entity/
│    │    │    │    │    ├── exception/
│    │    │    │    │    ├── repository/
│    │    │    │    │    ├── service/
├── resources/
│    ├── application.properties
├── docker-compose.yaml
└── Dockerfile
```

## Как обновить проект
1. Обновление кода

```bash
docker-compose up --build
```

2. Перезапуск контейнеров

```bash
docker-compose down
docker-compose up -d
```

## Тестирование
### Для тестирования API используй Postman, curl или аналогичные инструменты.

Примеры запросов для получения подписок пользователя см. выше;

### Интеграционные тесты
Немного тестов с использованием Testcontainers.
