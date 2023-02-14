package com.myhome.service;


import javax.persistence.EntityNotFoundException;

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
public class ItemImgService { 
	
	@org.springframework.beans.factory.annotation.Value("${itemImgLocation}")
	private String itemImgLocation;
	
	@Autowired
	private ItemImgRepository itemImgRepository;
	@Autowired
	private FileService fileService;
	
	// 상품이미지 업로드와 상품이미지 정보를 저장
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
	
	// 상품 이미지 데이터 수정
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
		
	        if(!itemImgFile.isEmpty()){
	            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
	                    .orElseThrow(EntityNotFoundException::new);

	            //기존에 등록된 상품 이미지 파일이 있을 경우 해당 파일 삭제
	            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
	                fileService.deleteFile(itemImgLocation+"/"+
	                        savedItemImg.getImgName());
	            }

	            String oriImgName = itemImgFile.getOriginalFilename();
	            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes()); // update한 상품 이미지 파일 업로드
	            String imgUrl = "/images/item/" + imgName; 
	            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl); // update한 상품 이미지 정보 세팅
	        
	    }		
	}
	
	
	
			
}
