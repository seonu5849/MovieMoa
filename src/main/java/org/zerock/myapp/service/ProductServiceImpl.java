package org.zerock.myapp.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.PhotoReviewDTO;
import org.zerock.myapp.domain.StoreDTO;
import org.zerock.myapp.domain.StoreKategoriesVO;
import org.zerock.myapp.domain.StoreVO;
import org.zerock.myapp.mapper.ProductMapper;

import java.util.List;

@Log4j2
@RequiredArgsConstructor

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductMapper productMapper;

    private Integer perPage = 8;

    @Override
    public List<StoreKategoriesVO> findKategorieList(){
        return this.productMapper.findKategorieList();
    }

    @Override
    public List<StoreVO> findProductList(Long kategorieId) {
        return this.productMapper.findProductList(kategorieId);
    }

    @Override
    public StoreVO findProduct(Long id) {
        return this.productMapper.findProduct(id);
    }

    @Override
    public StoreVO findProductId(Long adminId, String title) {
        return this.productMapper.selectProductId(adminId, title);
    }

    @Override
    public Integer createProduct(Long adminId, StoreDTO product) {
        log.trace("createProduct({}, {}) invoked.",adminId, product);
        product.setAdminId(adminId);

        return productMapper.insertProduct(product);
    }

    @Override
    public Integer updateProduct(Long adminId, StoreDTO product) {
        product.setAdminId(adminId);

        return productMapper.updateProduct(product);
    }

    @Override
    public Integer writePhotoReview(Long memberId, PhotoReviewDTO review) {
        log.trace("writePhotoReview({}, {}) invoked.", memberId, review);

        review.setMemberId(memberId);
        return this.productMapper.insertPhotoReview(review);
    }

    @Override
    public Integer deleteProduct(Long id) {
        return productMapper.deleteProduct(id);
    }

} // end class
