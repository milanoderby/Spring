## `JPA` : `Spring Frmework`에서 `DB`를 다루기 쉽게 하기 위한 `ORM Framework`

- `MyBatis`와 다르게 SQL문장을 사용하지 않음. 
- `JPA`에서 인식할 수 있는 규칙에 맞게 `Method 명`으로 `method`를 만들고, 내부 기능을 구현하면 자동으로 기능에 맞는 `SQL`문장을 생성 및 실행한다.



- `application.properties` : Project 전체의 설정을 저장하는 `File`

- `application.properties` 에 기본 설정을 저장할 수 있음 : 사용하는 `DB` 등

- 이를 통해 `DB`에 종속적이지 않고, 유지보수 및 생산성이 향상된다.

  ```properties
  # jpa
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
  spring.jpa.show-sql=true
  ```



- `Model` 을 작성하면, 자동으로 `Table`을 생성한다.

  ```java
  import javax.persistence.Entity;
  import javax.persistence.GeneratedValue;
  import javax.persistence.GenerationType;
  import javax.persistence.Id;
  import lombok.Data;
  
  @Data	//Data를 붙이면, lombok이 Getter/Setter method 및 부가적인 method를 생성한다.
  @Entity	//DB에 추가될 수 있는 Entity 형태라는 것을 인식시킨다.
  public class Product {
  	@Id	//id라는 변수에 붙여서, primary key를 의미한다.
  	@GeneratedValue(strategy = GenerationType.AUTO)	//자동으로 생성하고, 순차적으로 증가하는 값을 의미한다.
  	private long id;
      
  	private String name;
  	private int price;
  	private int count;
  }
  ```

  

- `JPA Repository` 라는 `class`를 상속하는 `interface`를 만들고, 내부에 `JPA method`를 만든다.

  ```java
  import org.springframework.data.jpa.repository.JpaRepository;
  import org.springframework.stereotype.Repository;
  
  import com.yonghwa.basic.model.Product;
  
  // Spring Framework에서 Repository 객체로 생성해놓는 Annotation
  @Repository
  public interface ProductRepository extends JpaRepository<Product, Long> {
  
      //사용할 JPA method를 인터페이스에서 정의해놓음
  	Product findByName(String name);
  }
  ```



- 주요 메소드

  - `save` : 기존에 행이 있으면, 덮어쓰기를 하기 때문에 구현 방식에 따라 `SQL`의 `insert`, `update`기능을 구현할 수 있다.
  - `findAll` , `findById` : 조건에 해당하는 `model` 이 여러 개라면, `findAll`을 사용하고, 아니라면 `findById`를 사용하여 `Id`로 특정한 `model` 하나만 가져온다. `SQL`의 `select` 기능
  - `delete` : `SQL`의 `delete` 기능
  - `count` : 데이터의 개수 확인

  ```java
  package com.yonghwa.basic.controller;
  
  import java.util.List;
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.ModelAttribute;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  import com.yonghwa.basic.model.Product;
  import com.yonghwa.basic.repository.ProductRepository;
  
  @RestController
  public class JpaController {
  	//`Autowired` : `Controller`, `Component`, `Repository`, `Service` 등과 같이 객체로 만들어놓은 것을 `Spring Framework`에서 연결하는 것
      @Autowired
  	ProductRepository productRepository;
  
  	@GetMapping("/jpa/product")
  	public List<Product> product() {
          //findAll로 DB에 저장된 모든 data들을 리스트에 저장한다.
  		List<Product> list = productRepository.findAll();
  		return list;
  	}
  	
  	@GetMapping("/jpa/product2")
  	public Product product2(String name) {
          //findByName으로 name과 일치하는 data하나를 저장한다.
  		Product list = productRepository.findByName(name);
  		return list;
  	}
  
  	@PostMapping("/jpa/product")
  	public String productPost(@ModelAttribute Product product) {
          //save로 받아온 Model Attribute를 저장한다.
  		productRepository.save(product);
          
          //RestController 방식으로 보내는 것임에 유의하자.
          //redirect:/jpa/product를 ResponseBody로 보내면 리다이렉트 된다.
  		return "redirect:/jpa/product";
  	}
  }
  ```

  