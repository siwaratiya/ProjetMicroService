package tn.esprit.stockservice.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.stockservice.Entity.Product;
import tn.esprit.stockservice.Entity.State;
import tn.esprit.stockservice.Entity.Stock;
import tn.esprit.stockservice.Entity.Type_product;
import tn.esprit.stockservice.Exception.ElementNotFoundException;
import tn.esprit.stockservice.Exception.NoProductException;
import tn.esprit.stockservice.Exception.OutOfStockException;
import tn.esprit.stockservice.Interface.IProductService;
import tn.esprit.stockservice.Interface.IStockService;
import tn.esprit.stockservice.Repository.IProductRepository;
import tn.esprit.stockservice.Repository.IStockRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StockService implements IStockService {
    IStockRepository stockRepository;
    IProductRepository productRepository;

    @Override
    public Stock addStock(Stock stock) {
        stock.setDate(LocalDate.now());
        stockRepository.save(stock);
        if(stock.getFree_storage()==null && stock.getUsed_storage()==null){
            stock.setFree_storage(stock.getStorage());
            stock.setUsed_storage(0.0);
            stockRepository.save(stock);
        }
        return stock;
    }

    @Override
    public Stock modifyStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    @Override
    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Stock> getAllStock() {
        Set<Stock> stockList = new HashSet<>();
        stockRepository.findAll().forEach(stockList::add);
        return stockList;
    }

    @Override
    public Stock withdrawStock(Double quantity, Long idstock) {
        Stock stock = stockRepository.findById(idstock).orElseThrow(() -> new ElementNotFoundException("Stock with id " + idstock + " not found : "));
        Double newQuantity = stock.getTotal_quantity() - quantity;
        Double newFreeQuantity = stock.getFree_storage() + quantity;
        Double newUsedQuantity = stock.getUsed_storage() - quantity;
        stock.setTotal_quantity(newQuantity);
        stock.setFree_storage(newFreeQuantity);
        stock.setUsed_storage(newUsedQuantity);
        for (Product product : stock.getProducts()) {
            if (product.getQuantity() < 0 || stock.getFree_storage() < 0 || stock.getUsed_storage() < 0 || stock.getTotal_quantity() < 0) {
                throw new NoProductException("quantity and Storage cannot be negative");
            }
            if (stock.getFree_storage() >= stock.getStorage() || stock.getUsed_storage() >= stock.getStorage()) {
                throw new OutOfStockException("Free and used storage can't over pass total storage ");
            }
        }
        stockRepository.save(stock);
        return stock;
    }

    @Override
    public Set<Product> getStockProduct(Long id) {
        return stockRepository.getStockProduct(id);
    }


    @Override
    @Transactional
    public Stock addProductToStock(Long id, Long idProduct,Double quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Stock with id " + id + " not found : "));
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new ElementNotFoundException("Product with id " + idProduct + " not found : "));
        stockRepository.save(stock);
        if(stock.getUsed_storage()==null){
            stock.setUsed_storage(quantity);
            stock.setFree_storage(stock.getStorage() - quantity);
            Double oldQuantity = product.getQuantity();
            product.setQuantity(oldQuantity - quantity);
            stockRepository.save(stock);
        }
        if (stock.getProducts() == null) {
            stock.setTotal_quantity(quantity);
            stock.setUsed_storage(quantity);
            stock.setFree_storage(stock.getStorage() - quantity);
            Double oldQuantity = product.getQuantity();
            product.setQuantity(oldQuantity - quantity);
            Set<Product> productList = new HashSet<>();
            productList.add(product);
            stock.setProducts(productList);
        } else {
            stock.setTotal_quantity(quantity);
            stock.setUsed_storage(quantity);
            stock.setFree_storage(stock.getStorage() - quantity);
            Double oldQuantity = product.getQuantity();
            product.setQuantity(oldQuantity - quantity);

            if (product.getType_product().equals(Type_product.REAGENT) && stock.getFree_storage() <= stock.getStorage()){
                System.out.println("hello3");
                Double quantityWithSize = quantity * product.getSize_product();
                Double newQuantity = product.getQuantity() - quantityWithSize;
                Long nbProducts = stockRepository.NbProductsInStock(id);
                Double totQantity = stock.getTotal_quantity() + quantityWithSize;
                /************** storage ******************/
                Double updatedFreeStorage = stock.getStorage() - totQantity;
                /************** storage ******************/
                stock.setNbProduct(nbProducts);
                stock.setTotal_quantity(totQantity);
                product.setQuantity(newQuantity);
                stock.setState(State.AVAILABLE);
                stock.setUsed_storage(totQantity);
                stock.setFree_storage(updatedFreeStorage);
            } else if (product.getType_product().equals(Type_product.EQUIPMENT) && stock.getFree_storage() <= stock.getStorage()) {
                System.out.println("hello4");
                Double quantityWithSize = quantity * product.getSize_product();
                Double newQuantity = product.getQuantity() - quantityWithSize;
                Long nbProducts = stockRepository.NbProductsInStock(id);
                Double totQantity = stock.getTotal_quantity() +quantityWithSize;
                /************** storage ******************/
                Double updatedFreeStorage = stock.getStorage() - totQantity;
                /************** storage ******************/
                stock.setNbProduct(nbProducts);
                stock.setTotal_quantity(totQantity);
                product.setQuantity(newQuantity);
                stock.setState(State.AVAILABLE);
                stock.setUsed_storage(totQantity);
                stock.setFree_storage(updatedFreeStorage);
                stock.setNbProduct(nbProducts);
            }
            stock.getProducts().add(product);
        }
        if (product.getQuantity() <0 || stock.getUsed_storage()<0  ){
            throw new NoProductException("Product quantity or stock quantity or storage can't be negative");
        }
        if (stock.getFree_storage() == stock.getStorage() ) {
            stock.setState(State.OUT_OF_STOCK);
            throw new OutOfStockException("Product is out of stock ");
        }


        return stock;
    }
}
