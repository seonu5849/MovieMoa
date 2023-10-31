package org.zerock.myapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.myapp.domain.StoreKategoriesVO;
import org.zerock.myapp.domain.StoreVO;
import org.zerock.myapp.service.ProductService;

import java.util.List;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/store/*")
@Controller
public class StoreController {

    private final ProductService productService;

// "kategorie" 요청 매개변수를 통해 선택된 카테고리에 해당하는 제품 목록을 가져옴
// 선택된 카테고리가 없으면 기본값으로 "1"을 사용함
    @GetMapping("/tickets")
    public String storeTicketsView(@RequestParam(value="kategorie", required = false, defaultValue="1")Long kategorieId, Model model) {
        log.trace("storeTicketsView({}) invoked.", kategorieId);

        // 카테고리 목록과 선택된 카테고리에 해당하는 제품 목록을 조회
        List<StoreKategoriesVO> kategorieList = this.productService.findKategorieList();
        List<StoreVO> productList = this.productService.findProductList(kategorieId);

        model.addAttribute("kategorieList", kategorieList);
        model.addAttribute("productList", productList);
        model.addAttribute("kategorie", kategorieId);

        return "/store/tickets";
    } // storeTicketsView

// "product_id" 경로 변수를 통해 조회할 제품의 ID를 받음
    @GetMapping("/detailProduct/{product_id}")
    public String detailProductView(@PathVariable(value = "id") Long id,Model model) {
        log.trace("detailProductView() invoked.");

        // 제품 ID에 해당하는 제품의 상세 정보를 조회
        StoreVO product = this.productService.findProduct(id);

        model.addAttribute("product", product);

        return "/store/detailProduct";
    } //detailProductView

    @DeleteMapping("/detailProduct")
    public String detailProductDelete() {
        log.trace("detailProductDelete() invoked.");

        return "redirect:/store/tickets";
    } // detailProductDelete

    @GetMapping("/writeProduct")
    public String writeProductView() {
        log.trace("writeProductView() invoked.");

        return "/store/writeProduct";
    } // writeProductView

    @PostMapping("/writeProduct")
    public String writeProduct() {
        log.trace("writeProduct() invoked.");

        return "redirect:/store/detailProduct";
    } // writeProduct

    @GetMapping("/updateProduct")
    public String updateProductView() {
        log.trace("updateProductView() invoked.");

        return "/store/updateProduct";
    } // eventUpdateView

    @PutMapping("/updateProduct")
    public String updateProduct() {
        log.trace("updateProduct() invoked.");

        return "redirect:/store/detailProduct";
    } // eventUpdateView

    @GetMapping("/photoReply")
    public String photoReplyView(){
        log.trace("photoReplyView() invoked.");

        return "/store/photoReply";
    } // photoReplyView

    @PostMapping("/photoReply")
    public String photoReplyWrite(){
        log.trace("photoReplyWrite() invoked.");

        return "redirect:/store/detailProduct";
    } // photoReplyWrite

    @PutMapping("/photoReply")
    public String photoReplyUpdate(){
        log.trace("photoReplyUpdate() invoked.");

        return "redirect:/store/detailProduct";
    } // photoReplyUpdate

    @DeleteMapping("/photoReply")
    public String photoReplyDelete(){
        log.trace("photoReplyDelete() invoked.");

        return "redirect:/store/tickets";
    } // photoReplyDelete

} // end class
