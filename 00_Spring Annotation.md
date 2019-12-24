## Spring Annotation(@) 정리

- `GetMapping` : `요청한 URL`로 들어온 `HTTP GET request`에 대해 처리하는 `method`를 지정한다.

  ```java
  // @GetMapping을 이용하여 req/get URL로 들어온 HTTP GET요청에 대해서 처리하는 메소드를 맵핑한다.
  @GetMapping("req/get")
  
  // @RequestMapping은 @GetMapping이 아직 안만들어졌을 때, 사용하던 옛날 방식
  // @RequestMapping 이나 @GetMapping 둘 중 하나를 선택해서 사용하면 됨.
  @RequestMapping(value = "req/get", method = RequestMethod.GET)
  
  public String get() {
      return "GET";
  }
  ```

  

- `PostMapping` : `요청한 URL`로 들어온 `HTTP POST request`에 대해 처리하는 `method`를 지정한다.

  ```java
  // @PostMapping을 이용하여 req/post URL로 들어온 HTTP POST요청에 대해서 처리하는 메소드를 맵핑한다.
  @PostMapping("req/post")
  
  // @RequestMapping은 @PostMapping이 아직 안만들어졌을 때, 사용하던 옛날 방식
  // @RequestMapping 이나 @PostMapping 둘 중 하나를 선택해서 사용하면 됨.
  @RequestMapping(value = "req/post", method = RequestMethod.POST)
  
  public String post() {
      return "POST";
  }
  ```

  

- `PutMapping` : `요청한 URL`로 들어온 `HTTP PUT request`에 대해 처리하는 `method`를 지정한다.

  ```java
  // @PostMapping을 이용하여 req/put URL로 들어온 HTTP PUT요청에 대해서 처리하는 메소드를 맵핑한다.
  @PutMapping("req/put")
  
  // @RequestMapping은 @PutMapping이 아직 안만들어졌을 때, 사용하던 옛날 방식
  // @RequestMapping 이나 @PutMapping 둘 중 하나를 선택해서 사용하면 됨.
  @RequestMapping(value = "req/put", method = RequestMethod.PUT)
  
  public String put() {
      return "PUT";
  }
  ```

  

- `DeleteMapping` : `요청한 URL`로 들어온 `HTTP DELETE request`에 대해 처리하는 `method`를 지정한다.

  ```java
  // @PostMapping을 이용하여 req/delete URL로 들어온 HTTP DELETE요청에 대해서 처리하는 메소드를 맵핑한다.
  @DeleteMapping("req/delete")
  
  // @RequestMapping은 @DeleteMapping이 아직 안만들어졌을 때, 사용하던 옛날 방식
  // @RequestMapping 이나 @DeleteMapping 둘 중 하나를 선택해서 사용하면 됨.
  @RequestMapping(value = "req/delete", method = RequestMethod.DELETE)
  
  public String delete() {
  	return "DELETE";
  }
  ```

  

- `Controller` : `class`가 return하는  값과 같은 이름을 가진 `html, jsp file 등`을 `resource`디렉토리에서 찾아서 return하도록 한다.

  ```java
  @Controller
  public class TestController2 {
  	@GetMapping("/test2")
  	public String test2() {
          // resources 디렉토리의 templates 디렉토리에서 hello라는 파일명을 가진 html, jsp file을 찾아 return한다.
  		return "hello";
  	}
  }
  ```

  

- `RestController` : `class` 가 return하는 값이 `html, jsp file 등`이 아닌 `Map, list, String`과 같은 자체 생성 `data`를 return 하도록 한다.

  ```java
  @RestController
  public class TestController {
  	@GetMapping("/test")
  	public String test() {
          // "Hello~" 라는 String을 HTML 페이지에 출력하여 return한다.
  		return "Hello~";
  	}
  }
  ```

  

- `ResponseBody` : `method` 가 return하는 값이 `html, jsp file 등`이 아닌 `Map, list, String`과 같은 자체 생성 `data`를 return 하도록 한다.

  ```java
  @GetMapping("json/object")
  
  // class는 @Controller로 구현되어 있지만, jsonObject method에만 ResponseBody를 붙임으로써 자체 data를 return한다.
  @ResponseBody
  
  public Member jsonObject() {
      Member member = new Member();
      member.setName("kim");
      return member;
  }
  ```

  

- `RequestParam` : 요청 시 지정한 `parameter`값을 읽어오고, 이를 지정한 변수에 대입한다.

  `parameter` 일부가 입력되지 않아도 요청을 받을 수 있도록 유연성을 가지고 있다.

  ```java
  @GetMapping("req/param1")
  
  //GET 요청으로 들어온 parameter 중 key1값은 key1변수에, key2값은 key변수에 저장한다.
  //GET 요청 예시) localhost:8080/req/param1?key1=v1&key2=v2
  public String param1(@RequestParam("key1") String key1,@RequestParam("key2") String key2){
      return key1 + ", " + key2;
  }
  
  //@RequestParam 내에 paramter를 지정해주지 않으면, 지정한 변수명으로 paramter값을 읽어온다.
  //아래의 경우, "val"값을 읽어와 val변수에 저장한다.
  public String param1(@RequestParam() int val){}
  
  //parameter를 반드시 받아야 되는 경우, required option을 이용한다.
  //parameter가 없는 경우, 400오류를 return한다.
  public String param1(@RequestParam(required) int val){}
  
  //parameter를 없어도 기본 값을 부여하고 싶은 경우, defaultValue option을 이용한다.
  //parameter가 없는 경우, 내가 지정한 defaultValue를 parameter에 대입한다.
  public String param1(@RequestParam(defaultValue=1) int val){}
  ```

  

- `ModelAttribute` : 요청 시, 내가 지정한 `Model`과 동일한 자료형/변수명의 `paramter`가 들어올 때에만 값을 받아오는 방식

  MyBatis, JPA와 연계 시 편리하게 사용가능하며, 정확한 처리를 위해 사용하므로 기업에서 선호

  ```java
  @GetMapping("req/model")
  //요청시 들어오는 paramter이름과 자료형이 Member 객체에 저장된 자료형과 변수명과 일치한다면,
  //member에 자동으로 저장한다.
  public String model(@ModelAttribute Member member) {
      return member.toString();
  }
  ```

- `Data` : `lombok` 라이브러리에서 제공하는 `annotation`으로 내부적으로 보이지 않게`Getter`, `Setter`, `toString` 등의 기능을 구현하여 사용할 수 있게 한다.

  ```java
  @Data
  public class Member {
  	private String name;
  	private String userId;
  	private String userPassword;
  }
  ```

- `Slf4j` : `lombok`라이브러리에서 제공하는 `annotation`으로 `Console`창에 특정 상황이 발생할 때, Log를 출력하는 기능을 제공한다.

  ```java
  @Slf4j
  @Controller
  public class HomeController {
  	//Logger의 장점
  	//1. 출력속도가 빠르다.
  	//2. 파일 등을 이용하여 관리하기 용이하다.
      
      //Logger 객체를 생성해서 사용해도 되고, log라는 static method를 사용해도 된다.
  	//Logger logger = LoggerFactory.getLogger(HomeController.class);
  	
  	@RequestMapping("/")
  	public String home() {
          //logger.trace("trace!")와 같은 출력문
  		log.trace("trace!");
          
  		log.debug("debug!"); // 개발단계에서 확인용
  		log.info("info!");	// 운용 상 필요한 정보
  		log.warn("warn!");	// 메모리 문제 등 경고
  		log.error("error!");	//치명적인 오류
  		 
  		return "home";
  	}
  }
  ```

  

`REST` 방식, `RESTful` 방식이 잘 되어있는 사이트: `okky.kr` 하나의 웹 페이지 콘텐츠가 고유한 주소 값을 가지게 코딩하는 방식

주소에 Mapping하는 URL을 각 게시글의 번호를 이용하여 URL을 계속 변경하는 방식