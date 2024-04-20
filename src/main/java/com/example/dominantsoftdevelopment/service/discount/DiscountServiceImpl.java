package com.example.dominantsoftdevelopment.service.discount;

import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.dto.DiscountDTO;
import com.example.dominantsoftdevelopment.dto.ProductDTO;
import com.example.dominantsoftdevelopment.exceptions.RestException;
import com.example.dominantsoftdevelopment.model.Discount;
import com.example.dominantsoftdevelopment.model.Product;
import com.example.dominantsoftdevelopment.repository.DiscountRepository;
import com.example.dominantsoftdevelopment.repository.ProductRepository;
import com.example.dominantsoftdevelopment.rsql.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Override
    public ApiResult<Boolean> createDiscount(DiscountDTO discountDTO) {
        Long productId = discountDTO.getProduct().getId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> RestException.restThrow("product not found"));

        LocalDateTime finishedAt = discountDTO.getFinishedAt();
        checkDate(finishedAt);

        Discount existDiscount = discountRepository
                .findLastDiscountByProductId(product.getId()).orElse(null);
        if (existDiscount != null) {
            existDiscount.setIsActive(false);
            discountRepository.save(existDiscount);
        }

        Discount discount = Discount.builder()
                    .productId(product)
                    .isActive(discountDTO.getIsActive())
                    .percentage(discountDTO.getPercentage())
                    .finishedAt(finishedAt)
                    .build();

        discountRepository.save(discount);
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<DiscountDTO> getDiscount(Long discountId) {
        Discount discount = get(discountId);
        DiscountDTO discountDTO = DiscountDTO.builder()
                .isActive(discount.getIsActive())
                .percentage(discount.getPercentage())
                .finishedAt(discount.getFinishedAt())
                .product(mapper.map(discount.getProductId(), ProductDTO.class))
                .build();
        return ApiResult.successResponse(discountDTO);
    }

    @Override
    public ApiResult<Page<DiscountDTO>> getAll(Pageable pageable, String predicate) {
        Specification<Discount> specification = SpecificationBuilder.build( predicate );
        if( specification == null ) {
             return ApiResult.successResponse(discountRepository.findAll(pageable)
                     .map(discount -> mapper.map(discount, DiscountDTO.class)));
        }
        return ApiResult.successResponse(discountRepository.findAll( specification, pageable )
                .map( discount -> mapper.map(discount, DiscountDTO.class)));
    }

    @Override
    public ApiResult<Boolean> updateDiscount(Long discountId, DiscountDTO discountDTO) {
        Discount discount = get(discountId);
        checkDate(discount.getFinishedAt());

        mapper.map(discountDTO, discount);
        discountRepository.save(discount);
        return ApiResult.successResponse(true);
    }

    @Override
    public void deleteDiscount(Long discountId) {
        Discount discount = get(discountId);
        discountRepository.deleteById(discountId);
    }

    public Discount get(Long id) {
        return discountRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("product not found"));
    }

    private void checkDate(LocalDateTime finishedAt) {
        if (finishedAt.isBefore(LocalDateTime.now())) {
            throw RestException.restThrow("Discount finishing date cannot be before current time : %s".formatted(LocalDateTime.now()));
        }
    }
}