package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.StoreVO;

import java.util.List;


@Mapper
public interface ProductMapper {

    // 카테고리 목록 조회
    public abstract List<StoreVO> findKategorieList();

    // 특정 카테고리에 속한 제품 목록 조회
    public abstract List<StoreVO> findProductList(@Param("kategorieId") Long kategorieId);

    // 특정 제품 상세 정보 조회
    public abstract StoreVO findProduct(@Param("id") Long id);

    // 새로운 제품 추가
    public abstract Integer insertProduct(@Param("product") StoreVO product);

    // 기존 제품 정보 업데이트
    public abstract Integer updateProduct(@Param("product") StoreVO product);

    // 특정 제품 삭제
    public abstract Integer deleteProduct(@Param("id") Long id);

} // end interface
