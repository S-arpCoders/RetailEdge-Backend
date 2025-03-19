package com.coders.RetailEdge.services.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final RestTemplate restTemplate;
    private final String BASE_URL = System.getenv("database-address") + "/reports";

    public ReportService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getSalesReport() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/sales", List.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getInventoryReport() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/inventory", List.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getStockMovements() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/stock-movements", List.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getSupplierReport() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + "/suppliers", List.class);
        return response.getBody();
    }
}
