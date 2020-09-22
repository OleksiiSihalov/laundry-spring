package com.sihalov.laundry.service;

import com.sihalov.laundry.data.entity.Order;
import com.sihalov.laundry.data.enums.OrderStatuses;
import com.sihalov.laundry.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.getOne(id);
    }

    public OrderStatuses getOrderStatus(Long id) {
        Order order = getOrder(id);
        return order.getOrderStatus();
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public void changeStatus(Long id, OrderStatuses status) {
        Order order = getOrder(id);
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
