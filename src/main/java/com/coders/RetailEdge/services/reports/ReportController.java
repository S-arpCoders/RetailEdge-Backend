package com.coders.RetailEdge.services.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/internal/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportServiceClient) {
        this.reportService = reportServiceClient;
    }

    @GetMapping("/sales")
    public ResponseEntity<List<Map<String, Object>>> getSalesReport() {
        return ResponseEntity.ok(reportService.getSalesReport());
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<Map<String, Object>>> getInventoryReport() {
        return ResponseEntity.ok(reportService.getInventoryReport());
    }

    @GetMapping("/stock-movements")
    public ResponseEntity<List<Map<String, Object>>> getStockMovements() {
        return ResponseEntity.ok(reportService.getStockMovements());
    }

    @GetMapping("/suppliers")
    public ResponseEntity<List<Map<String, Object>>> getSupplierReport() {
        return ResponseEntity.ok(reportService.getSupplierReport());
    }
}
