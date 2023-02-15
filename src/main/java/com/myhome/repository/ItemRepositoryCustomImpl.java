package com.myhome.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.myhome.constant.ItemSellStatus;
import com.myhome.dto.ItemSearchDTO;
import com.myhome.dto.MainItemDTO;
import com.myhome.dto.QMainItemDTO;
import com.myhome.model.Item;
import com.myhome.model.QItem;
import com.myhome.model.QItemImg;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

	// 동적 쿼리 생성하기 위해 JPAQueryFactory 클래스 사용
	private JPAQueryFactory queryFactory;

	// JPAQueryFactory 생성자 >> EntityManager객체를 넣어줌
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression itemNmLike(String searchQuery) { // 검색어가 null이 아니면 상품명에 해당하는 검색어가 포함되는 상품을 조회하는 조건을 반환

		// 상품명을 like 조건문에 맞게 들어가도록 설정
		return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");
	}

	private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
		// 상품의 상태가 품절인지 판매중 상태인 조건의 상품 조회
		return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
	}

	@Override
	public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
		QItem item = QItem.item;
		QItemImg itemImg = QItemImg.itemImg;

		List<MainItemDTO> content = queryFactory
				.select(new QMainItemDTO(item.id, item.itemNm, item.itemDetail, 
						itemImg.imgUrl, item.price, item.itemSellStatus, item.regDate, item.bookWtr, item.bookPsr))
				.from(itemImg).join(itemImg.item, item).where(itemImg.repimgYn.eq("Y"))
				.where(itemNmLike(itemSearchDTO.getSearchQuery())).orderBy(item.id.desc()).offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetch();
		
		long total = queryFactory.select(Wildcard.count).from(itemImg).join(itemImg.item, item)
				.where(itemImg.repimgYn.eq("Y")).where(itemNmLike(itemSearchDTO.getSearchQuery())).fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
	
		QueryResults<Item> results = queryFactory
				.selectFrom(QItem.item)
				.where(searchSellStatusEq(itemSearchDTO.getSearchSellStatus()), itemNmLike(itemSearchDTO.getSearchQuery()))
				.orderBy(QItem.item.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		List<Item> content = results.getResults();
		long total = results.getTotal();
		
		
		return new PageImpl<>(content, pageable, total);
	}

}
