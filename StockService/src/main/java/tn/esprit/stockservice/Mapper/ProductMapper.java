package tn.esprit.stockservice.Mapper;

import tn.esprit.stockservice.Entity.Product;
import tn.esprit.stockservice.dto.ProductDto;

public class ProductMapper {
    public static Product mapToEntity(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .name_product(productDto.getName_product())
                .reference(productDto.getReference())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .image(productDto.getImage())
                .count_order(productDto.getCount_order())
                .size_product(productDto.getSize_product())
                .type_product(productDto.getType_product())
                .quantity(productDto.getQuantity())
                .build();
    }

    public static ProductDto mapToDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name_product(product.getName_product())
                .reference(product.getReference())
                .price(product.getPrice())
                .description(product.getDescription())
                .image(product.getImage())
                .count_order(product.getCount_order())
                .type_product(product.getType_product())
                .size_product(product.getSize_product())
                .quantity(product.getQuantity())
                .build();
    }
}
