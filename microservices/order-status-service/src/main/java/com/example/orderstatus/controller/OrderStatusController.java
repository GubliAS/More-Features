package com.example.orderstatus.controller;

import com.example.orderstatus.dto.OrderStatusDTO;
import com.example.orderstatus.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-statuses")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    @PostMapping
    public ResponseEntity<OrderStatusDTO> addOrderStatus(@RequestBody OrderStatusDTO dto) {
        return ResponseEntity.ok(orderStatusService.addOrderStatus(dto));
    }

    @PutMapping
    public ResponseEntity<OrderStatusDTO> updateOrderStatus(@RequestBody OrderStatusDTO dto) {
        return ResponseEntity.ok(orderStatusService.updateOrderStatus(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable Long id) {
        orderStatusService.deleteOrderStatus(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<OrderStatusDTO> getAllOrderStatuses() {
        return orderStatusService.getAllOrderStatuses();
    }
} 