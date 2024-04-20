package com.example.dominantsoftdevelopment.service.order;

import com.example.dominantsoftdevelopment.dto.AddOrderItemDTO;
import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.dto.OrderDTO;
import com.example.dominantsoftdevelopment.dto.OrderItemDTO;
import com.example.dominantsoftdevelopment.exceptions.RestException;
import com.example.dominantsoftdevelopment.model.OrderItem;
import com.example.dominantsoftdevelopment.model.Orders;
import com.example.dominantsoftdevelopment.model.Product;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.repository.OrderItemRepository;
import com.example.dominantsoftdevelopment.repository.OrderRepository;
import com.example.dominantsoftdevelopment.repository.UserRepository;
import com.example.dominantsoftdevelopment.repository.product.ProductRepository;
import com.example.dominantsoftdevelopment.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ApiResult<Boolean> makeOrder(List<AddOrderItemDTO> orderItems) {
        Orders order = new Orders();
        orderRepository.save(order);
        OrderItem orderItem = new OrderItem();
        Long productId = 1L;
        double totalPrice = 0;

        for (AddOrderItemDTO itemDTO : orderItems) {
            productId = itemDTO.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> RestException.restThrow("Product not found"));

            orderItem.setOrderId(order);
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());
            orderItem.setProductCount(itemDTO.getProductCount());

            orderItemRepository.save(orderItem);
            totalPrice += (product.getPrice()*orderItem.getProductCount());
        }
        order.setTotalPrice(totalPrice);
        order.setCustomer(CommonUtils.getCurrentUserFromContext());
        orderRepository.save(order);

        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<List<OrderDTO>> getOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> RestException.restThrow("User not found"));
        List<Orders> orders = orderRepository
                .findAllByCustomerId(userId).orElse(List.of());

        if (orders.isEmpty()) {
            return ApiResult.successResponse(List.of());
        }
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Orders order : orders) {
            List<OrderItem> items = orderItemRepository.findByOrderId(order);
            List<OrderItemDTO> list = items.stream()
                    .map(orderItem -> modelMapper.map(orderItem, OrderItemDTO.class))
                    .toList();
            OrderDTO orderDTO = OrderDTO.builder()
                    .createdAt(order.getCreatedAt())
                    .id(order.getId())
                    .orderItems(list)
                    .totalPrice(order.getTotalPrice())
                    .build();
            orderDTOS.add(orderDTO);

        }

        return ApiResult.successResponse(orderDTOS);
    }

}
