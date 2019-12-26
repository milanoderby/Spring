package com.yonghwa.basic.controller;

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
	public ResponseEntity<Resource> download() throws Exception {
		File file = new File("C:\\Users\\yongh\\Desktop\\vi-vim-shortcut.png");
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok()
				.header("content-disposition", "filename=" + URLEncoder.encode(file.getName(), "utf-8"))
				//content - type => mime - type
				
				//mime type로 image인 것을 알려주면, image를 위한 동작을 진행함, 여기서는 이미지 보기 기능
				//.contentLength(file.length()).contentType(MediaType.parseMediaType("image/png"))
				
				//octet-stream type은 파일 다운로드를 하게 해줌
				.contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}
}