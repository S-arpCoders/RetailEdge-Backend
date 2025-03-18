package com.coders.RetailEdge.services.Sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @PostMapping("/transaction")
    public Mono<ResponseEntity<SaleDTO>> createTransaction(@RequestBody SaleDTO saleDTO) {
        return salesService.createSale(saleDTO)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/history/one")
    public Mono<ResponseEntity<SaleDTO>> getSale(@RequestParam int saleId) {
        return salesService.getSaleById(saleId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/history/list")
    public Mono<ResponseEntity<SaleDTO[]>> getAllSales() {
        return salesService.getAllSales()
                .map(ResponseEntity::ok);
    }
}