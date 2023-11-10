package tn.esprit.stockservice.Interface;

import tn.esprit.stockservice.Entity.Product;

import java.util.Set;

public interface IProductService {
    Product addProduct(Product product);
    Product modifyProduct(Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    Set<Product> getAllProduct();
}
