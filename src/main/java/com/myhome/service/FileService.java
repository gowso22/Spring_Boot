package com.myhome.service;

import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class FileService {

	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
		
		UUID uuid = UUID.randomUUID();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String savedFileName = uuid.toString() + extension;
		String fileuploadFullUrl = uploadPath + "/" + savedFileName;
		FileOutputStream fos = new FileOutputStream(fileuploadFullUrl);
		
		fos.write(fileData);
		fos.close();
		
		return savedFileName;
	}
	
}
