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
		
		//� ���·� �޵��� ���������� ���̳ʸ� ������ ������ �� �ִ� ������
		//MultipartFile�̴�.
		//�Ʒ����� file inputform�� ����� �ϳ��� ���ϸ��� �����ͼ� �����Ѵ�.
		//MultipartFile mFile = mRequest.getFile("file");
		
		//file�̶�� inputform�� ����� ��� ���ϵ��� List���·� �����Ѵ�. 
		List<MultipartFile> mFiles = mRequest.getFiles("file");
		for(int i=0; i<mFiles.size(); i++) {
			MultipartFile mf = mFiles.get(i);
			
			//÷�������� �̸��� �����´�.
			String oName = mf.getOriginalFilename();
			result += oName + "\n";
			
			//÷�������� ������ ��ο� �����Ѵ�.
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