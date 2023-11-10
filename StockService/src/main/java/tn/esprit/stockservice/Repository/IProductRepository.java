package tn.esprit.stockservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.stockservice.Entity.Product;

@Repository
public interface IProductRepository  extends JpaRepository<Product, Long> {
}
