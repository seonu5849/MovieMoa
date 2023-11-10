package org.zerock.myapp.service;

import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.myapp.domain.StoreDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Setter(onMethod_ = @Autowired)
    private ProductService productService;

    @Test
    @DisplayName("상품 등록")
    void testInsertProduct(){

        Long adminId = 1L;
        String title = "테스트상품23";
        String content = "맛있음";
        String price = "1000";
        Long kategorieId = 2L;
        String usageLocation = "제주";
        String posterPath = "www.asdasd";

        StoreDTO dto = new StoreDTO();
        dto.setTitle(title);
        dto.setContent(content);
        dto.setPrice(price);
        dto.setKategorieId(kategorieId);
        dto.setUsageLocation(usageLocation);
        dto.setPosterPath(posterPath);

        Integer affectedRows = this.productService.createProduct(adminId, dto);
        assertThat(affectedRows).isEqualTo(1);

    }

}