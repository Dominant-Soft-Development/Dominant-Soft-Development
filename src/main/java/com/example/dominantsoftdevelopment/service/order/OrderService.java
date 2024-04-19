package com.example.dominantsoftdevelopment.service.order;

import com.example.dominantsoftdevelopment.dto.AddOrderItemDTO;
import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    ApiResult<Boolean> makeOrder(List<AddOrderItemDTO> orderItems);

    ApiResult<List<OrderDTO>> getOrders(Long userId);

//    ApiResult<List<OrderItemDTO>> getOrderItems(Long orderId);
}
