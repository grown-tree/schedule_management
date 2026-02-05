## 일정 관리 앱

#### 공통조건
- 3 Layer Architecture에 따라 각 Layer의 목적에 맞게 개발
- CRUD 필수 기능은 모두 데이터베이스 연결 및 JPA를 사용해서 개발
- 일정 작성, 수정, 조회 시 반환 받은 일정 정보에 비밀번호는 제외
- 일정 수정, 삭제 시 선택한 일정의 비밀번호와 요청할 때 함께 보낸 비밀번호가 일치할 경우에만 가능

### 구현 내용
- [x] API 명세 및 ERD 작성
- [x] Lv 1. 일정 생성
- [x] Lv 2. 일정 조회
- [x] Lv 3. 일정 수정
- [x] Lv 4. 일정 삭제

※**과제 제출 시에는 아래 질문을 고민해 보고 답변을 함께 제출해 주세요.**

1. 3 Layer Architecture(Controller, Service, Repository)를 적절히 적용했는지 확인해 보고, 왜 이러한 구조가 필요한지 작성해 주세요.
- 3 Layer Architecture구조를 적용하여 구현하였습니다. 이 구조는 DB에 저장된 데이터들 중 민감한 데이터들이 코드를 통해 Clinet쪽으로 유출될 가능성을 막아주고(계층간 데이터 전달시 Entity 대신 DTO활용) 오류 발생시 디버깅이나 기능 추가시에도 그 영향범위를 줄여 유지보수에 도움을 줍니다.
2. `@RequestParam`, `@PathVariable`, `@RequestBody`가 각각 어떤 어노테이션인지, 어떤 특징을 갖고 있는지 작성해 주세요.
- @RequestParam은 URL에 쿼리 파라미터->?key=value로 사용되고 옵션으로 필수여부를 선택할수있음(required) 기본값은 true 검색 필터, 페이징기능시 주로사용함.
- @PathVariable은 URI 경로 /user/1 자체에 포함된 변수값을 식별하기위해 사용
- @RequestBody는 HTTP 요청 본문(Body)에 담긴 데이터(JSON)를 통째로 자바 객체로 변환할 때 사용
### ※ schedules ERD
![img.png](img.png)
### ※ 일정관리 API명세서
일정 관리를 위한 schedules 데이터 정의

| **데이터 명** | **키 (영문)** | **데이터 타입**   | **키 예시 (값)** |
| --- | --- |--------------| --- |
| 번호 | `id` | BIGINT       | `1` |
| 일정 제목 | `title` | VARCHAR(30)  | `"회의 일정"` |
| 일정 내용 | `content` | VARCHAR(200) | `"주간 회의 내용입니다."` |
| 작성자명 | `author` | VARCHAR(20)  | `"홍길동"` |
| 비밀번호 | `password` | VARCHAR(255) | `"hashed_password"` |
| 작성일 | `created_date` | DATETIME     | `"2025-01-01T10:00:00"` |
| 수정일 | `updated_date` | DATETIME     | `"2025-01-01T10:00:00"` |
### - 일정 생성

**Request - 요청**

- **Method:** `POST`
- **URL:** `/schedules`
- **Content-Type:** `application/json`
- **Body:**

    ```json
    {
      "title": "팀 프로젝트 마감 회의",
      "content": "오후 3시까지 회의실로 모여주세요.",
      "author": "김철수",
      "password": "kcs1234"
    }
    ```
  
**Response - 응답**

- **Status Code:** `201 Created`
  - **Body:**
    
      ```json
      {
        "id": 1,
        "title": "팀 프로젝트 마감 회의",
        "content": "오후 3시까지 회의실로 모여주세요.",
        "author": "김철수",
        "created_date": "2026-02-04T17:30:00",
        "updated_date": "2026-02-04T17:30:00"
      }
      ```
- Status Code: 400 Bad Request (필수 데이터 누락)

### - 일정 조회(전체)

**Request - 요청**

- **Method:** `GET`
- **URL:** `/schedules` (전체 조회) 또는 `/schedules?author={author}` ex)`/schedules?author=박영희`
- **Query Parameter :**

    | 파라미터 이름 | 필수 여부 | 내용 |
    | --- | --- | --- |
    | `author` | X | 작성자 이름 |
- **Content-Type:** `application/json`

**Response - 응답** 

- **Status Code:** `200 OK`
- **Body: updated_date(수정)기준으로 내림차순 정렬**

    ```json
    [
      { 
        "id": 2,
        "title": "주간 보고서 제출",
        "content": "금요일까지 제출 완료 부탁드립니다.",
        "author": "박영희",
        "created_date": "2026-02-03T09:00:00",
        "updated_date": "2026-02-04T17:00:00"
      },
      {
        "id": 1,
        "title": "팀 프로젝트 마감 회의",
        "content": "오후 3시까지 회의실로 모여주세요.",
        "author": "김철수",
        "created_date": "2026-02-04T12:30:00",
        "updated_date": "2026-02-04T14:30:00" 
      }
    ]
    ```
    박영희 이름으로 요청했을 경우

    ```json
    [
      {
        "id": 2,
        "title": "주간 보고서 제출",
        "content": "금요일까지 제출 완료 부탁드립니다.",
        "author": "박영희",
        "created_date": "2026-02-03T09:00:00",
        "updated_date": "2026-02-04T17:00:00"
      },
      {
        "id": 1,
        "title": "팀 프로젝트 마감 회의",
        "content": "오후 3시까지 회의실로 모여주세요.",
        "author": "박영희",
        "created_date": "2026-02-04T12:30:00",
        "updated_date": "2026-02-04T14:30:00" 
      }
    ]
    ```

### - 일정 조회(선택 일정)

**Request - 요청**

- **Method:** `GET`
- **URL:** `/schedules/{id}`
- **Path Parameter :**

    | 파라미터 이름 | 필수 여부 | 내용 |
    | --- | --- | --- |
    | id | O | 일정 id |
- **Content-Type:** `application/json`

**Response - 응답**

- **Status Code:** **`200 OK`**
  - **Body (성공 시):**

      ```json
      {
        "id": 1,
        "title": "팀 프로젝트 마감 회의",
        "content": "오후 3시까지 회의실로 모여주세요.",
        "author": "김철수",
        "created_date": "2026-02-04T17:30:00",
        "updated_date": "2026-02-04T17:30:00"
      }
      ```
- **Status Code:** **`404 Not Found`**


### - 일정 수정

  **Request - 요청**

- **Method:** `PATCH`
- **URL:** `/schedules/{id}` (예시: `/schedules/1`)
- **Path Parameter :**

    | 파라미터 이름 | 필수 여부 | 내용 |
    | --- | --- | --- |
    | id | O | 일정 id |
- **Content-Type:** `application/json`
- **Body:** 수정데이터와 비밀번호 필요

    ```json
    {
      "title": "팀 프로젝트 마감 회의 (시간 변경)",
      "author": "김철수",
      "password": "secure_password_1234"
    }
    ```

**Response - 응답** ( 비밀번호 제외, 수정일 현재시간으로 변경)

- Status Code: `200 OK`
  - **Body**  (성공 시):

      ```json
      {
        "id": 1,
        "title": "팀 프로젝트 마감 회의 (시간 변경)",
        "content": "오후 3시까지 회의실로 모여주세요.",
        "author": "김철수",
        "created_date": "2026-02-04T17:30:00",
        "updated_date": "2026-02-04T18:00:00"
      }
      ```
- Status Code: `401 Unauthorized` (비밀번호 불일치)
- Status Code: `404 Not Found` (ID가 없을 경우)


### - 일정 삭제

  **Request - 요청**
  - **Method:** `DELETE`
  - **URL:** `/schedules/{id}` (예시: `/schedules/1`)
  - **Path Parameter :**

    | 파라미터 이름 | 필수 여부 | 내용 |
    | --- | --- | --- |
    | id | O | 일정 id |
  - **Content-Type:** `application/json`
  - **Body :** (비밀번호 필요)
      ```json
      {
        "password": "secure_password_1234"
      }
      ```
    **Response 응답**
- Status Code: `204 No Content` (삭제 성공, 응답 본문 없음)
- Status Code: `401 Unauthorized` (비밀번호 불일치)
- Status Code: `404 Not Found` (ID가 없을 경우)