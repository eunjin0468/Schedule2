
### 📌 **일정 생성 API**

- **Method**: POST

- **URL**: `/schedule`

- **설명**: 새로운 일정 등록


##### ✅ **요청값 (Request JSON)**

| **필드명** | **타입** | 설명 | **필수 여부** |
| --- | --- | --- | --- |
| title | String | 일정 제목 | ⭕️ |
| content | String | 일정 내용 | ⭕️ |
| author | String | 작성자명 | ⭕️ |
| password | String | 비밀번호 | ⭕️ |

``` json
{
  "title": "회의",
  "content": "주간회의 내용",
  "author": "홍길동",
  "password": "1234"
}

 ```

##### ✅ **응답값 (Response JSON)**

| **필드명** | **타입** | **설명** | **필수 여부** |
| --- | --- | --- | --- |
| id | Long | 생성된 일정의 고유 식별자 | ⭕️ |
| title | String | 일정 제목 | ⭕️ |
| content | String | 일정 내용 | ⭕️ |
| author | String | 작성자명 | ⭕️ |
| createdAt | String | 생성 시각 (날짜와 시간 포함) | ⭕️ |
| modifedAt | String | 수정 시각 (최초 생성 시에는 createdAt과 동일) | ⭕️ |

``` json
{
  "id": 1,
  "title": "회의",
  "content": "주간회의 내용",
  "author": "이은진",
  "createdAt": "2025-08-01T14:00:00",
  "modifiedAt": "2025-08-01T14:00:00"
}

 ```

> 참고 : API 응답에는 password 제외하였음



---

### 📌 **일정 조회 API**

#### 1️⃣ 일정 단건 조회

- **Method**: GET

- **URL**: `/schedule/{id}`

- **설명**: 특정 일정의 상세 정보를 조회


##### ✅ **요청값 (Path Parameter)**

| **필드명** | **타입** | **설명** | **필수 여부** |
| --- | --- | --- | --- |
| id | Long | 조회할 일정의 고유 ID | ⭕️ |

##### ✅ **응답값 (Response JSON)**

| **필드명** | **타입** | **설명** | **필수 여부** |
| --- | --- | --- | --- |
| id | Long | 일정 고유 식별자 | ⭕️ |
| title | String | 일정 제목 | ⭕️ |
| content | String | 일정 내용 | ⭕️ |
| author | String | 작성자명 | ⭕️ |
| createdAt | String | 생성 시각 (날짜와 시간 포함) | ⭕️ |
| modifiedAt | String | 수정 시각 (최초 생성 시 동일) | ⭕️ |

``` json
{
  "id": 1,
  "title": "회의",
  "content": "주간회의 내용",
  "author": "이은진",
  "createdAt": "2025-08-01T14:00:00",
  "modifiedAt": "2025-08-01T14:00:00"
}

 ```

#### 2️⃣ 전체 일정 조회
- **Method**: GET

- **URL**: `/schedule`

- **설명**: 전체 일정 목록 조회


##### ✅ **요청값 (Query Parameter)**

| **필드명** | **타입** | **설명** | **필수 여부** |
| --- | --- | --- | --- |
| author | String | (선택) 작성자명 기준 필터링 | ❌ (선택) |

- 예시 요청: /schedule?author=홍길동


##### ✅ **응답값 (Response JSON)**

- 조건에 맞는 일정이 없을 경우: **HTTP 204 No Content** 반환


| **필드명** | **타입** | **설명** | **필수 여부** |
| --- | --- | --- | --- |
| id | Long | 일정 고유 식별자 | ⭕️ |
| title | String | 일정 제목 | ⭕️ |
| content | String | 일정 내용 | ⭕️ |
| author | String | 작성자명 | ⭕️ |
| createdAt | String | 생성 시각 (날짜와 시간 포함) | ⭕️ |
| modifiedAt | String | 수정 시각 (최초 생성 시 동일) | ⭕️ |

``` json
[
  {
    "id": 1,
    "title": "회의",
    "content": "주간회의 내용",
    "author": "홍길동",
    "createdAt": "2025-08-01T14:00:00",
    "modifiedAt": "2025-08-01T15:00:00"
  },
  {
    "id": 2,
    "title": "점심 약속",
    "content": "친구와 점심",
    "author": "홍길동",
    "createdAt": "2025-07-30T11:30:00",
    "modifiedAt": "2025-07-30T11:30:00"
  }
]

 ```

> 참고: 응답 객체에 password는 포함되지 않음  
정렬 기준: modifiedAt 내림차순

---

### 📌 **일정 수정 API**

- **Method**: PUT

- **URL**:`/schedule/{id}`

- **설명**:

    - 선택한 일정의 제목 또는 작성자명을 수정합니다. 수정 시 비밀번호 필요

    - 최소 1개 항목(title, author)은 수정해야 함

---

##### ✅ **요청값**

- **Path Parameter**


| **필드명** | **타입** | **설명** | **필수 여부** |
| --- | --- | --- | --- |
| id | Long | 수정할 일정의 고유 ID | ⭕️ |

- **Body (Request JSON)**


| **필드명** | **타입** | **설명** | **필수 여부** |
| --- | --- | --- | --- |
| title | String | 수정할 일정 제목 | ❌ (선택) |
| author | String | 수정할 작성자명 | ❌ (선택) |
| password | String | 해당 일정의 비밀번호 | ⭕️ |

``` json
{
  "title": "일정 제목 수정",
  "author": "수정된 작성자",
  "password": "1234"
}

 ```

##### ✅ **응답값 (Response JSON)**

- 비밀번호는 응답값에 포함되지 않음

- 작성일은 수정되지 않으며, 수정일은 자동으로 갱신됨


| **필드명** | **타입** | **설명** | **필수 여부** |
| --- | --- | --- | --- |
| id | Long | 일정 고유 식별자 | ⭕️ |
| title | String | 일정 제목 | ⭕️ |
| content | String | 일정 내용 | ⭕️ |
| author | String | 작성자명 | ⭕️ |
| createdAt | String | 생성 시각 | ⭕️ |
| modifiedAt | String | 수정 시각 (수정된 현재 시각) | ⭕️ |

``` json
{
  "id": 1,
  "title": "일정 제목 수정",
  "content": "주간회의 내용",
  "author": "수정된 작성자",
  "createdAt": "2025-08-01T14:00:00",
  "modifiedAt": "2025-08-01T16:00:00"
}

 ```

---

### 📌 **일정 삭제 API**

- **Method**: DELETE

- **URL**: `/schedule/{id}`

- **설명**:

    - 선택한 일정을 삭제

    - 삭제 시 비밀번호 필요


##### ✅ **요청값**

- **Path Parameter**


| **필드명** | **타입** | **설명** | **필수 여부** |
| --- | --- | --- | --- |
| id | Long | 삭제할 일정의 고유 ID | ⭕️ |

- **Body (Request JSON)**


| **필드명** | **타입** | **설명** | **필수 여부** |
| --- | --- | --- | --- |
| password | String | 해당 일정의 비밀번호 | ⭕️ |

``` json
{
  "password": "1234"
}

 ```

##### ✅ **응답값**

| 항목 | 설명 |
| --- | --- |
| 상태코드 | 204 No Content (삭제 성공 시) |
| 응답 바디 | 없음 |

*비밀번호가 일치하지 않으면 403 Forbidden 또는 400 Bad Request 등의 에러 반환

---
### 📌ERD 다이어그램
![](/erd.png)

