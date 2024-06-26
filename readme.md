## Список задач

выполнены все задачи тестового задания

## Управление бюджетом и авторами API

### Обзор

Этот проект представляет собой RESTful API на базе Kotlin для управления записями бюджета и информацией об авторах. В проекте используются Ktor, Exposed ORM, PostgreSQL и Flyway для миграций базы данных. API позволяет клиентам выполнять CRUD-операции с записями бюджета и авторами, применять пагинацию и фильтровать результаты на основе определенных критериев.

### Документация Swagger
Для удобного документирования и тестирования API используется Swagger. Документация доступна по адресу:

http://localhost:8080/swagger-ui/index.html?url=/openapi.json

### Основные технологии

- **Ktor**: Асинхронный фреймворк для создания веб-приложений на Kotlin.
- **Exposed ORM**: ORM-библиотека для работы с базами данных в Kotlin.
- **PostgreSQL**: Реляционная база данных.
- **Flyway**: Инструмент для управления версиями базы данных и миграциями.
- **Swagger**: Инструмент для документирования и тестирования API.

### Функциональные возможности

1. **Управление записями бюджета**:
    - Добавление новой записи бюджета.
    - Получение статистики бюджета за указанный год с поддержкой пагинации и фильтрации по автору.

2. **Управление авторами**:
    - Добавление нового автора.
    - Получение списка всех авторов.

### Структура проекта

- **mobi.sevenwinds.app.budget**: Пакет, содержащий логику управления записями бюджета.
- **mobi.sevenwinds.app.author**: Пакет, содержащий логику управления авторами.
- **common**: Общие утилиты и настройки для всего приложения.

### Установка и запуск

#### Требования

- JDK 11+
- PostgreSQL
- Docker (для запуска базы данных в контейнере)

#### Шаги установки


**Запуск базы данных в Docker**:
    ```bash
    docker run --name dev_mem -e POSTGRES_USER=dev -e POSTGRES_PASSWORD=dev -e POSTGRES_DB=dev_test_mem -p 45532:5432 -d postgres:10
    ```

### Примеры использования API

#### Добавление записи бюджета

**Запрос**:
```http
POST /budget/add
Content-Type: application/json

{
    "year": 2024,
    "month": 6,
    "amount": 100,
    "type": "Приход",
    "author": 1
}
```

**Ответ**:
```json
{
    "year": 2024,
    "month": 6,
    "amount": 100,
    "type": "Приход",
    "author": 1
}
```

#### Получение статистики за год

**Запрос**:
```http
GET /budget/year/2024/stats?limit=3&offset=0&authorName=Иванов
```

**Ответ**:
```json
{
    "total": 5,
    "totalByType": {
        "Приход": 105,
        "Расход": 50
    },
    "items": [
        {
            "year": 2024,
            "month": 6,
            "amount": 100,
            "type": "Приход",
            "authorName": "Иванов Иван",
            "authorCreatedAt": "2024-01-01T12:00:00.000Z"
        }
    ]
}
```

### Тестирование

Проект включает тесты для проверки функциональности API. Тесты расположены в пакете `mobi.sevenwinds.app.budget`.

#### Запуск тестов

```bash
./gradlew test
```


### Лицензия

Этот проект лицензируется на условиях MIT License.