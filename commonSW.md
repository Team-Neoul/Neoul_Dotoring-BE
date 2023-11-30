Sleeper
---
![sleeper](https://user-images.githubusercontent.com/91835827/224555330-d3d1d072-f43b-4007-8b78-7d42b7c28be0.png)
Sleeper는 20대 대학생을 위한 규칙적인 수면관리 어플리케이션입니다.
---
## 기술 스택
- Java 11
- Springboot
- JPA
- H2
---
## 역할 분담
[임수미](https://github.com/LimSumi/sleeper)
- 로그인
  [황대선](https://github.com/hwangdaesun/sleeper)
- 회원가입
- 감사일기
- 수면 관리
- 케릭터
- 재화
## 제공 기능
---
| 기능       | 설명                           |
|----------|------------------------------|
| 회원가입 기능 | 회원가입 기능 제공                   |
| 알람 기능    | 잠들시간이 다가오면 사용자에게 알람을 보내주는 기능 제공 |
| 감사일기 관련 기능 | 감사일기에 대한 생성,조회,수정,삭제 기능 제공   |
| 수면 관련 기능 | 기상 시간 추천 기능, 수면 기록 생성,조회 기능 제공 |
| 케릭터 관련 기능 | 케릭터의 성장에 대한 기능 제공            |
| 재화 관련 기능| 사용자가 앱 내에서 사용할 수 있는 리워드 제공   |
## 도메인 설계
---
![도메인 설계](https://user-images.githubusercontent.com/91835827/224559570-e949de31-cd21-436c-a01d-fdb385670997.png)
### 핵심 도메인
- XpPolicy : 케릭터 경험치 정책 추상화
- DatePolicy : sleeper 내부 날짜 정책 추상화
- RewardPolicy : 리워드 지급 정책 추상화
- SleepAdvisor : 사용자의 수면정보를 바탕으로 기상시간을 추천할 책임
  도메인에대한 상세 설명은 [‘여기‘](https://github.com/hwangdaesun/sleeper/blob/master/docs/%EB%8F%84%EB%A9%94%EC%9D%B8%20%EC%83%81%EC%84%B8%20%EC%84%A4%EB%AA%85.md
  )에 있습니다.
---
## 커밋 컨벤션
| 메시지      | 설명 |
|----------|------|
| feat     | 기능 추가 작업에 대한 커밋 |
| update   | 기능 수정 작업에 대한 커밋 |
| fix      | 버그 코드 수정에 대한 커밋 |
| refactor | 코드 리팩토링 작업에 대한 커밋|
| docs     | 문서 작업에 대한 커밋 |
---
### 에코노베이션 복덩이팀 발표영상
- [기획](https://youtu.be/wOqimi4O8H4?t=2123) - PM 이시현
- [백엔드](https://youtu.be/wOqimi4O8H4?t=2499) - 백엔드 개발자 황대선