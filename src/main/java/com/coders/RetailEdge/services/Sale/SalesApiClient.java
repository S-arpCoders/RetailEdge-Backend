package com.coders.RetailEdge.services.Sale;

import com.coders.RetailEdge.services.Sale.SaleDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Component
public class SalesApiClient {

    private final WebClient webClient;

    public SalesApiClient(WebClient.Builder webClientBuilder) {
        // Base URL of the external data service
        this.webClient = webClientBuilder.baseUrl("https://external-data-service.com/api").build();
    }

    public Mono<SaleDTO> fetchSaleById(int saleId) {
        return webClient.get()
                .uri("/sales/{id}", saleId)
                .retrieve()
                .bodyToMono(SaleDTO.class);
    }

    public Mono<SaleDTO[]> fetchAllSales() {
        return webClient.get()
                .uri("/sales/list")
                .retrieve()
                .bodyToMono(SaleDTO[].class);
    }

    public Mono<SaleDTO> createSale(SaleDTO saleDTO) {
        return webClient.post()
                .uri("/sales/transaction")
                .bodyValue(saleDTO)
                .retrieve()
                .bodyToMono(SaleDTO.class);
    }
}