package com.example.dominantsoftdevelopment.controller.product;

import com.example.dominantsoftdevelopment.dto.AddProductDTO;
import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public HttpEntity<ApiResult<Boolean>> add(@Valid @RequestBody AddProductDTO addProductDTO){
        return ResponseEntity.ok(productService.add(addProductDTO));
    }
}
