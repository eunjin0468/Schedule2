---

## **📌 API 명세**

---

[](https://github.com/f-api/basic-spring#api-%EB%AA%85%EC%84%B8)

### **1\. 일정(Schedule) API**

[](https://github.com/f-api/basic-spring#1-%EC%9D%BC%EC%A0%95schedule-api)

#### **1.1 일정 생성**

| 항목 | 내용 |
| --- | --- |
| URL | POST /schedules |
| Request Body | title (String, 100자 이내, 팔수)   content (String, 500자 이내, 필수 아님)   userId (필수 아님) |
| Response | 201 Created - 응답 본문 포함 |
| Response Body | scheduleId (Long)   title (String)   content (String)   createTime (LocalTime)   modifiedTime (LocalTime) |
| Error | 400 Bad Request - 필수값 누락, 글자수 초과   401 Unauthorized - 비로그인 사용자가 접근 시 |

### **1.2 일정 목록 조회**

[](https://github.com/f-api/basic-spring#12-%EC%9D%BC%EC%A0%95-%EB%AA%A9%EB%A1%9D-%EC%A1%B0%ED%9A%8C)

| 항목 | 내용 |
| --- | --- |
| URL | GET /schedules |
| Response | 200 OK |
| Response Body | 일정 목록 배열   \- scheduleId (Long)   \- title (String)   \- content (String)   \-createdAt (LocalTime)   \- modifiedAt (LocalTime) |
| Error | 401 Unauthorized - 비로그인 사용자가 접근 시 |

### **1.3 일정 단건 조회**

[](https://github.com/f-api/basic-spring#13-%EC%9D%BC%EC%A0%95-%EB%8B%A8%EA%B1%B4-%EC%A1%B0%ED%9A%8C)

| 항목 | 내용 |
| --- | --- |
| URL | GET  /schedules/scheduleId |
| Path Variable | scheduleId (Long, 필수) |
| Response | 200 OK  |
| Response Body | 일정 정보   \- scheduleId (Long)   \- userName (String)   \- tilte (String)   \- content (String)   \- createdAt (LocalTime)   \- modifiedAt (LocalTime) |
| Error | 401 Unauthorized - 비로그인 사용자가 접근 시   404 NOT FOUND - 잘못된 scheduleId 입력 |

### **1.4 일정 수정**

| 항목 | 내용 |
| --- | --- |
| URL | PUT  /schedules/scheduleId |
| Path Variable | scheduleId (Long, 필수) |
| Request Body | title (String, 100자 이내, 팔수)   content (String, 500자 이내, 필수 아님)   userId (String, 필수)   password (String,  필수) |
| Response | 200 OK - 응답 본문 포함 |
| Response Body | title, content |
| Error | 400 Bad Request - 필수값 누락 |
| 비고 | 제목 수정 필수, 제목과 내용만 수정 가능 |

[](https://github.com/f-api/basic-spring#14-%EC%9D%BC%EC%A0%95-%EC%88%98%EC%A0%95)

#### **1.5 일정 삭제**

[](https://github.com/f-api/basic-spring#15-%EC%9D%BC%EC%A0%95-%EC%82%AD%EC%A0%9C)

| 항목 | 내용 |
| --- | --- |
| URL | DELETE /schedules/{scheduleId} |
| Path Variable | scheduleId (Long, 필수) |
| Query Parameter | password (String, 필수) |
| Response | 200 OK |
| Error | 401 Unauthorized - 비로그인 사용자가 접근 시   404 NOT FOUND - 잘못된 scheduleId 입력 |

---

### **2\. 유저(User) API**

#### **2.1 회원가입**

| 항목 | 내용 |
| --- | --- |
| URL | POST /users/signup |
| Request Body | email (String, 이메일 형식, 필수)   userName (String, 필수)   password (String, 대소문자/특수문자/8자이상 20자이내, 필수) |
| Response | 201 Created - 응답 본문 포함 |
| Response Body | userId (Long)   userName (String)   email (String)   createdAt (LocalTime)   modifiedAt (LocalTime)   message - "회원 가입이 완료되었습니다" |
| Error | 400 Bad Request - 필수값 누락 (필수값 요청 메시지 포함) |

#### **2.2 로그인**

| 항목 | 내용 |
| --- | --- |
| URL | POST /users/signin |
| Request Body |   email (String, 필수)  password (String, 필수)     |
| Response | 200 OK |
| Request Body | 로그인 성공 메시지 |
| Error  | 400 Bad Request \- 필수값 누락 (필수값 요청 메시지 포함) |

#### **2.3 유저 정보 수정**

| 항목 | 내용 |
| --- | --- |
| URL | PUT /users |
| Request Body | userName (새로운 userName, String, 필수)   email (새로운 email, String, 필수)   password (기존 비밀번호, String, 필수)  |
| Response | 200 OK |
| Request Body | userName (String)   email (String)   message - "회원 정보가 성공적으로 수정되었습니다." |
| Error  | 400 Bad Request - 비밀번호 불일치 및 필수값 누락 (필수값 요청 메시지 포함)   401 Unauthorized - 비로그인 사용자가 접근 시 |

#### **2.4 로그아웃**

| 항목 | 내용 |
| --- | --- |
| URL | POST /users/logout |
| Response | 200 OK |
| Request Body | 로그아웃 되었습니다. |
| Error  | 401 Unauthorized - 비로그인 사용자가 접근 시 |

---

### 📌ERD 다이어그램
![](/diagram.png)

