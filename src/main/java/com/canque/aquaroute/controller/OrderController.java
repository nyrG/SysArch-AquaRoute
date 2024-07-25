package com.canque.aquaroute.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.canque.aquaroute.model.Order;
import com.canque.aquaroute.repository.OrderRepository;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/findOrders")
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/findOrder/{id}")
    public ResponseEntity<?> findOrderById(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Optional<Order> order = orderRepository.findById(objectId);
            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format");
        }
    }

    @GetMapping("/findOrderByStation")
    public ResponseEntity<?> findOrderByStationName(@RequestParam String stationName) {
        List<Order> orders = orderRepository.findByStationName(stationName);
        if (!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for station name: " + stationName);
        }
    }
    
    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Order order) {
        try {
            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order successfully created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving order");
        }
    }

}
