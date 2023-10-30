package org.zerock.myapp.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
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
    public List<StoreVO> findKategorieList(){
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
    public Integer insertProduct(StoreVO product) {
        return productMapper.insertProduct(product);
    }

    @Override
    public Integer updateProduct(StoreVO product) {
        return productMapper.updateProduct(product);
    }

    @Override
    public Integer deleteProduct(Long id) {
        return productMapper.deleteProduct(id);
    }

} // end class
