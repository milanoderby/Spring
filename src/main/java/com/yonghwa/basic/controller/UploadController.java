package com.yonghwa.basic.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
			
			//첨부파일을 지정한 경로에 저장한다.
			try {
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