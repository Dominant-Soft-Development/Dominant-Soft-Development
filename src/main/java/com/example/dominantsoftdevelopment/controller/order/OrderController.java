package com.example.dominantsoftdevelopment.controller.order;

import com.example.dominantsoftdevelopment.dto.AddOrderItemDTO;
import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.dto.OrderDTO;
import com.example.dominantsoftdevelopment.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "This API is used for making orders")
    public HttpEntity<ApiResult<Boolean>> makeOrders(@RequestBody List<AddOrderItemDTO> orderItems) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.makeOrder(orderItems));
    }

    @GetMapping("/list/{userId}")
    @Operation(summary = "This API is used for getting orders by userID")
    public ResponseEntity<ApiResult<List<OrderDTO>>> getOrders(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.getOrders(userId));
    }

    /* @GetMapping("/item/{orderId}")
     @Operation(summary = "This API is used for getting order-items by orderID")
    public ResponseEntity<ApiResult<List<OrderItemDTO>>> getOrderItems(@PathVariable Long orderId) {
         return ResponseEntity.ok(orderService.getOrderItems(orderId));
    }*/
}
