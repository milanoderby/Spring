## `thymeleaf`

- `thymeleaf` : `Controller` 에서 `View` 쪽 HTML로 `Model`을 넘겨주는 것을 지원하고, `View`쪽에서 `Model`을 출력하는 것을 지원하는 HTML 템플릿
- `spring boot`에서 권장하는 `HTML 템플릿`



### `thymeleaf` 에서 제공하는 문법

- `th:text` : `thymeleaf`를 통해 받아온 `Model`의 인자 값들을 `View`에서 출력할 때, 사용

  ```html
  <span th:text="${page}"></span>
  <!-- Controller에서 보내온 Model에 저장된 page라는 변수를 출력한다.-->
  ```



- `th:if` : `thymeleaf`에서 지원하는 `if문`

  ```html
  <span th:if="${pageNumber} == ${page}" th:text="${pageNumber}"></span>
  <!-- pageNumber가 page와 같을 때, 이를 출력한다. -->
  
  <!-- thymeleaf 문법으로 아래와 같은 문도 허용하다. -->
  <span th:if="${pageNumber == page}" th:text="${pageNumber}"></span>
  ```

  

- `th:unless` : `thymeleaf` 에서 지원하는 `if not문` , `else 문` 대신에 사용한다.

  ```html
  <span th:if="${pageNumber} == ${page}" th:text="${pageNumber}"></span>
  <!-- pageNumber가 page와 다를 때, 이를 출력한다. -->
  ```



- `th:block` : `thymeleaf`에서 반복문을 지원하기 위해 만든 `tag`

- `th:each` : `thymeleaf`에서 지원하는 `for ~ each문`

  ```html
  <!-- 아래의 #numbers는 thymeleaf에서 제공하는 라이브러리이고, sequence(a,b)는 a에서 b까지의 숫자를 배열에 넣어주는 함수이다. -->
  <th:block th:each="pageNumber : ${#numbers.sequence(startPage, endPage)}">
  	<a th:text="${pageNumber}"></a>
  </th:block>
  <!-- startPage~endPage까지의 숫자들을 출력한다. -->
  ```



- `th:href` : `thymeleaf`에서 지원하는 `link`

  ```html
  <a th:href="@{/linkUrl(page=${pageNumber})} th:text="${pageNumber}"></a>
  <!-- link를 누르게 되면, [기본URL]/linkUrl?page=[pageNumber]로 접속하게 된다.-->
  ```



