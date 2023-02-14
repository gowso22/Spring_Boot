package com.myhome.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.myhome.dto.ItemFormDTO;
import com.myhome.dto.ItemImgDTO;
import com.myhome.dto.ItemSearchDTO;
import com.myhome.dto.MainItemDTO;
import com.myhome.model.Item;
import com.myhome.model.ItemImg;
import com.myhome.repository.ItemImgRepository;
import com.myhome.repository.ItemRepository;




@Service
@Transactional
public class ItemService { // 상품 등록
	
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ItemImgService itemImgService;
	@Autowired
	private ItemImgRepository itemImgRepository;

	public Long saveItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception {

		// 상품 등록
		Item item = itemFormDTO.createItem(); // 입력 받은 데이터를 이용하여 item 객체 생성
		itemRepository.save(item); // 상품 데이터 저장

		// 상품 이미지 등록
		for (int i = 0; i < itemImgFileList.size(); i++) {
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item);
			if (i == 0) { // 첫번째 이미지일 경우 대표상품 이미지 여부 값을 Y
				itemImg.setRepimgYn("Y");
			} else { // 그렇지 않을 시 이미지 여부 값을 N
				itemImg.setRepimgYn("N");
			}
			
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i)); // 파일리스트 사이즈만큼 상품 이미지 정보 저장
		}

		return item.getId(); // 상품의 id를 리턴
	}
	
	@Transactional(readOnly = true)
	public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable){
		return itemRepository.getMainItemPage(itemSearchDTO, pageable);
	}
	
	 @Transactional(readOnly = true) // 트랜잭션을 읽기 전용 설정 : 더티체킹() 수행하지 않아 성능을 향상을 시킴
	    public ItemFormDTO getItemDtl(Long itemId){ // 상품 상세페이지에서 해당 상품 표시해줄 메서드
	        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
	        
	        List<ItemImgDTO> itemImgDTOList = new ArrayList<>();
	        
	        for (ItemImg itemImg : itemImgList) {
	            ItemImgDTO itemImgDTO = ItemImgDTO.of(itemImg);
	            itemImgDTOList.add(itemImgDTO);
	        }

	        Item item = itemRepository.findById(itemId)
	                .orElseThrow(EntityNotFoundException::new); // 상품 아이디 통해 상품entity를 조회 존재하지 않을 시 EntityNotFoundException
	        ItemFormDTO itemFormDTO = ItemFormDTO.of(item);
	        itemFormDTO.setItemImgDTOList(itemImgDTOList);
	        return itemFormDTO;
	    }
	 
	   @Transactional(readOnly = true)
	    public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable){
	        return itemRepository.getAdminItemPage(itemSearchDTO, pageable);
	    }
	   
	   // 상품 및 상품 이미지 수정
	   public Long updateItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception {
		   //상품 수정
	        Item item = itemRepository.findById(itemFormDTO.getId())
	                .orElseThrow(EntityNotFoundException::new);
	        item.updateItem(itemFormDTO);
	        List<Long> itemImgIds = itemFormDTO.getItemImgIds();

	        //이미지 등록
	        for(int i=0;i<itemImgFileList.size();i++){
	            itemImgService.updateItemImg(itemImgIds.get(i),
	                    itemImgFileList.get(i));
	        }

	        return item.getId();
	}
	   
	   
	   
	   
	   
	   

}
