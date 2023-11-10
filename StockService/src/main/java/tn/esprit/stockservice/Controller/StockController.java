package tn.esprit.stockservice.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.stockservice.Entity.Product;
import tn.esprit.stockservice.Entity.Stock;
import tn.esprit.stockservice.Interface.IStockService;
import tn.esprit.stockservice.Mapper.ProductMapper;
import tn.esprit.stockservice.Mapper.StockMapper;
import tn.esprit.stockservice.dto.ProductDto;
import tn.esprit.stockservice.dto.StockDto;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/stock")
public class StockController {

    IStockService stockService;


    @PostMapping()
    public StockDto addStock(@RequestBody StockDto stockDto){
        final Stock stock = StockMapper.mapToEntity(stockDto);
        return StockMapper.mapToDto(stockService.addStock(stock));
    }

    @GetMapping()
    public Set<StockDto> getAllStocks(){
        final Set<Stock> stockSet = stockService.getAllStock();
        return stockSet.stream().map(p -> StockMapper.mapToDto(p)).collect(Collectors.toSet());
    }

    @PutMapping()
    public StockDto modifyStock(@RequestBody StockDto stockDto){
        final Stock stock = StockMapper.mapToEntity(stockDto);
        return StockMapper.mapToDto(stockService.modifyStock(stock));
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable("id") Long stockId) {
        stockService.deleteStock(stockId);
    }

    @GetMapping("/{id}")
    public StockDto getStock(@PathVariable("id") Long stockId) {
        return StockMapper.mapToDto(stockService.getStockById(stockId));
    }

    @PutMapping("/affectToStock/{id}/{idP}/{quantity}")
    public StockDto affectProductToStock(@PathVariable("id") Long id ,@PathVariable("idP") Long idP,@PathVariable("quantity") Double quantity){
        return StockMapper.mapToDto(stockService.addProductToStock(id,idP,quantity));
    }

    @GetMapping("/getProductStock/{id}")
    public Set<ProductDto> getStockProduct(@PathVariable("id") Long id){
        final Set<Product> productSet = stockService.getStockProduct(id);
        return productSet.stream().map(p -> ProductMapper.mapToDto(p)).collect(Collectors.toSet());
    }

    @PutMapping("/withdraw/{id}")
    public StockDto withDraw(@RequestBody Double quantity,@PathVariable("id") Long id){
        return StockMapper.mapToDto(stockService.withdrawStock(quantity,id));
    }
}
