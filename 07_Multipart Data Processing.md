## `Multipart Data` 처리

- 이미지, 동영상, 음악 파일과 같은 `Binary data`는 일반적인 `text data`를 받는 것과 다른 형식으로 `data`가 전송이 된다.
- 그 이유는, `Binary data`에는 `text data`를 받아올 때, 사용하는 구분자인 `&`, `?`, `=`를 포함할 수도 있다. 따라서, 일반적인 구분자가 아닌 `multipart` 구분자로 구분하여 `binary data`를 전송한다.
- 받는 `Server`측 에서도 이러한 `multipart` 구분자로 `data`를 구분하여 받아야되기 때문에 기존의 `Request` 변수나 `Response` 변수가 아닌 변수를 사용한다.



### `File Upload` 할 때의 처리

- `MultipartHttpServletRequest`를 이용하여 받아온다.

- `Server` 측 `UploadController` `method`

  ```java
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.ResponseBody;
  import org.springframework.web.multipart.MultipartFile;
  import org.springframework.web.multipart.MultipartHttpServletRequest;
  
  @Controller
  public class UploadController {
  	@GetMapping("/upload1")
  	public String upload1() {
  		return "upload1";
  	}
  
  	@PostMapping("/upload1")
  	@ResponseBody
  	public String upload1Post(MultipartHttpServletRequest mRequest) {
          //MultipartHttpServletRequest를 이용하여 바이너리 파일들을 받아온다.
  		String result = "";
  		
  		//어떤 형태로 받든지 최종적으로 바이너리 파일을 저장할 수 있는 변수는
  		//MultipartFile이다.
  		//아래줄은 file inputform에 저장된 하나의 파일만을 가져와서 저장한다.
  		//MultipartFile mFile = mRequest.getFile("file");
  		
  		//file이라는 inputform에 저장된 모든 파일들을 List형태로 저장한다. 
  		List<MultipartFile> mFiles = mRequest.getFiles("file");
  		for(int i=0; i<mFiles.size(); i++) {
  			MultipartFile mf = mFiles.get(i);
  			
  			//첨부파일의 이름을 가져온다.
  			String oName = mf.getOriginalFilename();
  			result += oName + "\n";
  			
  			try {
                  //첨부파일을 지정한 경로에 저장한다.
  				mf.transferTo(new File("C:/Users/yongh/" + oName));
  			} catch (IllegalStateException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			} catch (IOException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  		}
  		
  		return result;
  	}
  }
  ```

- `Client` 측 `upload1.html`

  ```html
  <meta charset="utf-8">
  <form method="post" enctype="multipart/form-data">
  	<!--  input type=file에서 multiple 옵션은 여러 개의 파일을 받아올 수 있다. -->
  	<input type="file" name="file" multiple><br> <input
  		type="submit" value="업로드">
  </form>
  ```

- 사실, `@RequestParam`이나 `@ModelAttribute`를 이용할 수도 있지만, 여러 가지 불편함이 존재한다.

- `@RequestParam`과 `@ModelAttribute`의 경우, 인자를 하나씩 받아올 수 밖에 없어서 여러 개의 `File`을 한 번에 받을 수가 없다.



### `File Download` 할 때의 처리

- `ResponseEntity`를 이용하여 전송한다.

- `Server` 측 `DownloadController`

  ```java
  import java.io.File;
  import java.io.FileInputStream;
  import java.net.URLEncoder;
  import org.springframework.core.io.InputStreamResource;
  import org.springframework.core.io.Resource;
  import org.springframework.http.MediaType;
  import org.springframework.http.ResponseEntity;
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.GetMapping;
  
  @Controller
  public class DownloadController {
  	@GetMapping("/download")
      //Resource의 실제 의미는 실제로 전송하는 data이다.
  	public ResponseEntity<Resource> download() throws Exception {
          //Server Pc에 저장된 File을 File객체로 생성한다.
  		File file = new File("C:\\Users\\yongh\\Desktop\\vi-vim-shortcut.png");
          
          //File을 가져와서 resource라는 변수에 생성한다.
  		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
          
          //ResponseEntity 응답 코드를 OK, 200으로 설정 
  		return ResponseEntity.ok()
              .header("content-disposition", "filename=" + URLEncoder.encode(file.getName(), "utf-8"))
  				//content - type => mime - type
  				//mime type로 image인 것을 알려주면, image를 위한 동작을 진행함, 여기서는 이미지 보기 기능
  //.contentLength(file.length()).contentType(MediaType.parseMediaType("image/png"))
  				
  				//octet-stream type은 파일 다운로드를 하게 해줌
  .contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream"))
              //body에 resource를 저장한다.
  				.body(resource);
  	}
  }
  ```



### 실제 `File`을 업로드하고, 다운로드 서비스를 제공할 때 주의사항

- `File` 을 저장할 때에는 중복되는 파일명이 없도록 한다.
- `File` 명 형식뒤에 : [_현재 시간] 형식으로 서버에 업로드한다.
- 다시 사용자에게 돌려줄 때에는 원래의 파일명으로 되돌려주도록 한다. (그렇지 않다면, 사용자가 자신의 데이터를 건드렸다고 생각해 불편한 느낌을 받을 수 있다.)