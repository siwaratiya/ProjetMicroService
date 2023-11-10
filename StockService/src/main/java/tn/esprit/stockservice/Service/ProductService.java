package tn.esprit.stockservice.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.stockservice.Entity.Product;
import tn.esprit.stockservice.Exception.ElementNotFoundException;
import tn.esprit.stockservice.Interface.IProductService;
import tn.esprit.stockservice.Repository.IProductRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {
    IProductRepository productRepository;
    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product modifyProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Product> getAllProduct() {
        Set<Product> productList = new HashSet<>();
        productRepository.findAll().forEach(productList::add);
        return productList;
    }


}
