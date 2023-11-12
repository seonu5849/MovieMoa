package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.*;

import java.util.List;


@Mapper
public interface ProductMapper {

    // 카테고리 목록 조회
    public abstract List<StoreKategoriesVO> findKategorieList();

    // 특정 카테고리에 속한 제품 목록 조회
    public abstract List<StoreVO> findProductList(@Param("kategorieId") Long kategorieId);

    // 특정 제품 상세 정보 조회
    public abstract StoreVO findProduct(@Param("id") Long id);

    // 특정 제품의 번호 조회
    public abstract StoreVO selectProductId(@Param("adminId") Long adminId,
                                            @Param("title") String title);

    // 새로운 제품 추가
    public abstract Integer insertProduct(@Param("product") StoreDTO product);

    // 기존 제품 정보 업데이트
    public abstract Integer updateProduct(@Param("product") StoreDTO product);

    // 포토 리뷰 추가
    public abstract Integer insertPhotoReview(@Param("review")PhotoReviewDTO review);

    // 특정 제품 삭제
    public abstract Integer deleteProduct(@Param("id") Long id);

    // 특정 제품의 포토리뷰 조회
    public abstract List<PhotoReviewVO> selectPhotoReviewsByStoreId(@Param("id") Long id);

} // end interface
