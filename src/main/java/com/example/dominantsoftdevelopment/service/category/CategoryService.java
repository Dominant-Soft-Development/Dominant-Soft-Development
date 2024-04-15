package com.example.dominantsoftdevelopment.service.category;

import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    ApiResult<List<CategoryDTO>> all();
}
