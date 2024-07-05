package com.canque.aquaroute.controller;

import java.util.List;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.canque.aquaroute.model.Order;
import com.canque.aquaroute.repository.OrderRepository;


@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // Find all orders
    @GetMapping("/findAll")
    public ResponseEntity<List<Order>> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        // Perform any necessary validations or business logic
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString().trim());
        }

        order.setTotalAmount(order.getPrice() * order.getQuantity());

        Order savedOrder = orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    // Update an existing order by ID
    @PutMapping("/update/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable("orderId") String orderId, @Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString().trim());
        }

        ObjectId id;
        try {
            id = new ObjectId(orderId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid order ID format");
        }

        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }

        existingOrder.setStationId(order.getStationId());
        existingOrder.setPrice(order.getPrice());
        existingOrder.setQuantity(order.getQuantity());
        existingOrder.setTotalAmount(order.getPrice() * order.getQuantity());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setDeliveryDate(order.getDeliveryDate());
        existingOrder.setDeliveryAddress(order.getDeliveryAddress());

        orderRepository.save(existingOrder);

        return ResponseEntity.ok("Order updated successfully");
    }
}
