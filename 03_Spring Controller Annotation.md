## `Controller` 제어

### 1. AOP

- 어떠한 클래스든, 어떠한 메소드도 대상이 될 수 있음.
- `Spring`의 기능



### 2. Filter

- 접속하는 주소(URL)이 대상이 됨.
- `Java`의 고유기능



### 3. Interceptor

- `Filter`와 마찬가지로 접속하는 주소(URL)를 대상으로 함.
- `Spring`의 기능



### 4. 실제 작동순서 : Filter - Interceptor - AOP 순으로 실행되고, PageController가 실행되고 나서는 역순으로 종료된다.