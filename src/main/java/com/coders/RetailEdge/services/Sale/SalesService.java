package com.coders.RetailEdge.services.Sale;

//import com.example.sales.client.SalesApiClient;
//import com.example.sales.dto.SaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SalesService {

    @Autowired
    private SalesApiClient salesApiClient;

    public Mono<SaleDTO> getSaleById(int saleId) {
        return salesApiClient.fetchSaleById(saleId);
    }

    public Mono<SaleDTO[]> getAllSales() {
        return salesApiClient.fetchAllSales();
    }

    public Mono<SaleDTO> createSale(SaleDTO saleDTO) {
        return salesApiClient.createSale(saleDTO);
    }
}