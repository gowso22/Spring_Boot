package com.myhome.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class FileService { // 파일을 업로드하는 메소드, 파일을 삭제하는 메소드 설정

	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
		
		UUID uuid = UUID.randomUUID(); // 파일명 중복제거
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); 
		String savedFileName = uuid.toString() + extension; // uuid로 받은 값과 원래 파일 이름의 확장자 조합하여 저장 파일 이름 설정
		String fileuploadFullUrl = uploadPath + "/" + savedFileName; 
		// 생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일에 쓸 파일 출력 스트림을 만듦
		FileOutputStream fos = new FileOutputStream(fileuploadFullUrl); 
		
		fos.write(fileData);
		fos.close();
		
		return savedFileName; // 업로드된 파일의 이름을 반환
	}
	
	public void deleteFile(String filePath) throws Exception {
		 File deleteFile = new File(filePath); // 파일이 저장된 경로 이용 파일객체 생성
		 
		 if(deleteFile.exists()) { // 파일이 있다면 파일을 삭제
			 deleteFile.delete();
			 
			 log.info("파일을 삭제하였습니다.");
		 } else {
			 log.info("파일이 존재하지 않습니다.");
		 }
		
	}
	
}
