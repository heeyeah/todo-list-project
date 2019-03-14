# todo-list-project
TODO LIST PROJECT🐣
<br>

### 개요 (Overview)
할일 목록(todo-list) 웹 어플리케이션 구현
<br>

### 문제해결 전략 (Strategy)

#### 요구사항
* 사용자는 텍스트로 된 할일을 추가할 수 있다. **➡Create**
  * 할일 추가 시 다른 할일들을 참조 걸 수 있다.
  * 참조는 다른 할일의 id를 명시하는 형태로 구현한다.
<br/>

* 사용자는 할일을 수정할 수 있다. **➡Update**
<br/>

* 사용자는 할일 목록을 조회할 수 있다. **➡Read**
  * 조회시 작성일, 최종수정일, 내용이 조회 가능하다.
  * 할일 목록은 페이징 기능이 있다.
<br/>  

* 사용자는 할일을 완료처리 할 수 있다. **➡Update**
  * 완료처리 시 참조가 걸린 완료되지 않은 할일이 있다면 완료처리할 수 없다.
<br/>

* 단위테스트 필수, 통합테스트는 선택 **➡JUnit**
#### 해결전략

* RestfulAPI를 사용해서 todo resource에 대해 처리
  * GET, POST, PATCH method 사용
  * API feature를 파악하기 쉽게 Swagger를 사용
<br/>

* Redis - NoSQL 데이터베이스 사용
  * Embedded Redis 환경을 구성
  * CrudRepository interface 사용 [(Spring Redis 제공)](https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/)
<br/>

* Spring에서 @ControllerAdvice를 활용한 GlobalException 처리
<br/>

* 동일 출처 정책(Single Origin Policy, SOP)을 해결하기 위해 CORS(Cross Origin Resource Sharing) 설정
<br/>

* Frontend Debugging
  * vue-devtools (Chrome only) 사용
    * vue component 확인가능
    * vue-cli를 사용해 프로젝트를 생성한 경우,
    devtool config *cheap-module-eval-source-map* 값을 **source-map** 으로 설정해야 breakpoint 사용해 debugging 가능 (```/node_modules/@vue/cli-service/lib/config/dev.js```)
<br/>

### 프로젝트 구성 환경 (Feature)
#### Server
* Spring Boot
  - RestfulAPI
  - Build with Gradle
* Java1.8
* Redis
* Swagger (API features)
* JUnit Test

#### Frontend
* Vue.js
  - node & npm
  - vue-cli 3.0
  - jquery, axios, bootstrap-vue, sweetalert2
<br>

### 프로젝트 빌드 및 실행 방법 (Build & Run)

#### Required Module
* node & npm
* gradle
#### Backend
```sh
$ cd ~/
$ gradle bootRun
```

#### Frontend
```sh
$ cd ~/frontend
$ npm run serve
```

<br><br><br>
