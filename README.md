# SPRING ADVANCED

# 개요
1. 프로젝트 실행을 위해 JWT 키를 등록하고 DB와 연결함.
2. ArgumentResolver 는 따로 등록이 필요하기 때문에, WebMvcConfiguretion 을 통해 등록함.
3. 회원가입 기능 - Early Return 하게끔
4. WeatherClient - 불필요한 if-else 삭제 (예외를 던지면 바로 종료됨, else문 불필요.)
5. UserService - Validation적용 (requestDto를 통해 제대로 검증하게끔 수정)
6. TodoRepository - @EntityGraph 사용하도록 변경
7. test - 테스트 작동하도록 변경 및 test 메서드 이름 변경
