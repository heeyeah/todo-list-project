# todo-list-project
TODO LIST PROJECT🐣

### 개요 (Overview)
할일 목록(todo-list) 웹 어플리케이션 구현

### 문제해결 전략 (Strategy)
* 기본적인 Create, Read, Update 필요
* 할일을 완료처리할 때, 참조가 걸린 완료되지 않은 할일이 있으면 완료처리할 수 없음을 명시해야 함


### 프로젝트 구성 환경 (Feature)
* Spring Boot
  - java 1.8
  - RestfulAPI
  - Handle CORS
  - Redis
  - build with gradle
  - Swagger (API features)

* VueJS
  - node & npm
  - using vue-cli
  - axios, bootstrap-vue etc

* Unit Test
  - JUnit

### 프로젝트 빌드 및 실행 방법 (Build & Run)

* Backend
  ```gradle bootRun```
<br>

* Frontend
  ```npm run serve```
