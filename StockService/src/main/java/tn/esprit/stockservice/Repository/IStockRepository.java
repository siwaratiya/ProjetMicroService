package tn.esprit.stockservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.stockservice.Entity.Product;
import tn.esprit.stockservice.Entity.Stock;

import java.util.Set;

@Repository
public interface IStockRepository extends JpaRepository<Stock,Long> {

    @Query("select st.products from Stock st join st.products where st.id=:id")
    Set<Product> getStockProduct(@Param("id") Long id);

    @Query("select count(pr) from Stock st join st.products pr where st.id = :id")
    Long NbProductsInStock(@Param("id") Long id);

    @Query("select pr.quantity from Stock st join st.products pr where st.id = :id")
    Double getTotalQantity(@Param("id") Long id);
}
