package org.zerock.myapp.service;

import org.zerock.myapp.domain.*;

import java.util.List;

public interface ProductService {

    // 카테고리 목록 조회
    public abstract List<StoreKategoriesVO> findKategorieList();

    // 특정 카테고리에 속한 제품 목록 조회
    public abstract List<StoreVO> findProductList(Long kategorieId);

    // 특정 제품 상세 정보 조회
    public abstract StoreVO findProduct(Long id);

    // 특정 제품의 번호 조회
    public abstract StoreVO findProductId(Long adminId, String title);

    // 새로운 제품 추가
    public abstract Integer createProduct(Long adminId, StoreDTO product);

    // 기존 제품 정보 업데이트
    public abstract Integer updateProduct(Long adminId, StoreDTO product);

    // 포토 리뷰 작성
    public abstract Integer writePhotoReview(Long memberId, PhotoReviewDTO review);

    // 특정 제품 삭제
    public abstract Integer deleteProduct(Long id);

    // 특정 제품의 포토리뷰 조회
    public abstract List<PhotoReviewVO> selectPhotoReviewsByStoreId(Long id);

} // end interface
