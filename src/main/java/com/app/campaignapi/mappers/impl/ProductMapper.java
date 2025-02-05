package com.app.campaignapi.mappers.impl;

import com.app.campaignapi.domain.Dtos.ProductDTO;
import com.app.campaignapi.domain.Entities.Product;
import com.app.campaignapi.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<Product, ProductDTO> {

    private final ModelMapper mapper;

    public ProductMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ProductDTO mapToDto(Product product) {
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public Product mapToEntity(ProductDTO productDTO) {
        return mapper.map(productDTO, Product.class);
    }
}