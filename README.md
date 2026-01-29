# Admin Demo 프로젝트

PostgreSQL과 Spring Boot를 사용한 간단한 CRUD 데모 프로젝트입니다.

## 사전 요구사항

- Java 21
- Docker 및 Docker Compose
- Gradle

## 시작하기

### 1. 데이터베이스 시작

```bash
docker-compose up -d
```

### 2. 애플리케이션 실행

```bash
./gradlew bootRun
```

또는

```bash
./gradlew build
java -jar build/libs/admin-demo-0.0.1-SNAPSHOT.jar
```

애플리케이션은 `http://localhost:8080`에서 실행됩니다.

## API 엔드포인트

### 사용자 CRUD API

#### 모든 사용자 조회
```
GET /api/userEntities
```

#### ID로 사용자 조회
```
GET /api/userEntities/{id}
```

#### 사용자 생성
```
POST /api/userEntities
Content-Type: application/json

{
  "username": "newuser",
  "email": "newuser@example.com"
}
```

#### 사용자 수정
```
PUT /api/userEntities/{id}
Content-Type: application/json

{
  "username": "updateduser",
  "email": "updated@example.com"
}
```

#### 사용자 삭제
```
DELETE /api/userEntities/{id}
```

## 예제 요청

### cURL 예제

```bash
# 모든 사용자 조회
curl http://localhost:8080/api/userEntities

# 사용자 생성
curl -X POST http://localhost:8080/api/userEntities \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com"}'

# 사용자 조회
curl http://localhost:8080/api/userEntities/1

# 사용자 수정
curl -X PUT http://localhost:8080/api/userEntities/1 \
  -H "Content-Type: application/json" \
  -d '{"username":"updateduser","email":"updated@example.com"}'

# 사용자 삭제
curl -X DELETE http://localhost:8080/api/userEntities/1
```

## 데이터베이스 정보

- 호스트: localhost
- 포트: 5432
- 데이터베이스: demodb
- 사용자: demo_user
- 비밀번호: demo_password

## 프로젝트 구조

```
admin-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/happyblock/admindemo/
│   │   │       ├── AdminDemoApplication.java
│   │   │       ├── controller/
│   │   │       │   └── UserController.java
│   │   │       ├── entity/
│   │   │       │   └── User.java
│   │   │       └── repository/
│   │   │           └── UserRepository.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker-compose.yml
├── init.sql
└── build.gradle
```
