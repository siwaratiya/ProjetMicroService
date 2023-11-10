package tn.esprit.stockservice.Mapper;

import tn.esprit.stockservice.Entity.Stock;
import tn.esprit.stockservice.dto.StockDto;

public class StockMapper {
    public static Stock mapToEntity(StockDto stockDto){
        return Stock.builder()
                .id(stockDto.getId())
                .nbProduct(stockDto.getNbProduct())
                .unit(stockDto.getUnit())
                .total_quantity(stockDto.getTotal_quantity())
                .storage(stockDto.getStorage())
                .free_storage(stockDto.getFree_storage())
                .used_storage(stockDto.getUsed_storage())
                .date(stockDto.getDate())
                .location(stockDto.getLocation())
                .type_product(stockDto.getType_product())
                .state(stockDto.getState())
                .products(stockDto.getProducts())
                .build();
    }

    public static StockDto mapToDto(Stock stock){
        return StockDto.builder()
                .id(stock.getId())
                .nbProduct(stock.getNbProduct())
                .unit(stock.getUnit())
                .total_quantity(stock.getTotal_quantity())
                .storage(stock.getStorage())
                .free_storage(stock.getFree_storage())
                .used_storage(stock.getUsed_storage())
                .date(stock.getDate())
                .location(stock.getLocation())
                .type_product(stock.getType_product())
                .state(stock.getState())
                .products(stock.getProducts())
                .build();
    }
}
