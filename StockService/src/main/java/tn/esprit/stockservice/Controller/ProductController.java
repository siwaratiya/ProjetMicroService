package tn.esprit.stockservice.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.stockservice.Entity.Product;
import tn.esprit.stockservice.Interface.IProductService;
import tn.esprit.stockservice.Mapper.ProductMapper;
import tn.esprit.stockservice.dto.ProductDto;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    IProductService productService;
    @PostMapping()
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        final Product product = ProductMapper.mapToEntity(productDto);
        return ProductMapper.mapToDto(productService.addProduct(product));
    }
    @GetMapping()
    public Set<ProductDto> getAllProduct(){
        final Set<Product> productSet = productService.getAllProduct();
        return productSet.stream().map(p -> ProductMapper.mapToDto(p)).collect(Collectors.toSet());
    }

    @PutMapping()
    public ProductDto modifyProduct(@RequestBody ProductDto productDto) {
        final Product product = ProductMapper.mapToEntity(productDto);
        return ProductMapper.mapToDto(productService.modifyProduct(product));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long productId) {
        return productService.getProductById(productId);
    }

}
