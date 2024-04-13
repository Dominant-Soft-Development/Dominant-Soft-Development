package com.example.dominantsoftdevelopment.service.category;

import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.dto.CategoryDTO;
import com.example.dominantsoftdevelopment.model.Category;
import com.example.dominantsoftdevelopment.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final ModelMapper mapper;

    @Override
    public ApiResult<List<CategoryDTO>> all() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {
            CategoryDTO categoryDTO = CategoryDTO.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .attachment(category.getAttachment())
                    .categoryDTOList(findChildCategory(category.getId()))
                    .build();
            categoryDTOList.add(categoryDTO);
        }

        return ApiResult.successResponse(categoryDTOList);
    }

    private List<CategoryDTO> findChildCategory(Long parentCategoryId) {
       List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (Category category : categoryRepository.findByParentCategoryId(parentCategoryId)) {

            List<CategoryDTO> categoryDTOS = null;

            if (category.getParentCategory()!=null)
                categoryDTOS = findChildCategory(category.getId());

            CategoryDTO categoryDTO = mapper.map(category, CategoryDTO.class);

            if (category.getParentCategory()!=null)
                categoryDTO.setCategoryDTOList(categoryDTOS);

           categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }
}
