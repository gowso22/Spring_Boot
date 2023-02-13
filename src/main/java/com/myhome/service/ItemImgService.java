package com.myhome.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.myhome.model.ItemImg;
import com.myhome.repository.ItemImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class ItemImgService { // 상품이미지 업로드와 상품이미지 정보를 저장
	
	@org.springframework.beans.factory.annotation.Value("${itemImgLocation}")
	private String itemImgLocation;
	
	@Autowired
	private ItemImgRepository itemImgRepository;
	@Autowired
	private FileService fileService;
	
	
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
		String oriImgName = itemImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		//파일 업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}
		
		// 상품 이미지 정보 저장
		itemImg.updateItemImg(oriImgName, imgName, imgUrl);
		itemImgRepository.save(itemImg);
		
	}
	
	
	
			
}
