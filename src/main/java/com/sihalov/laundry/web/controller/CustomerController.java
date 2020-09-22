package com.sihalov.laundry.web.controller;

import com.sihalov.laundry.data.entity.Laundry;
import com.sihalov.laundry.data.entity.Order;
import com.sihalov.laundry.data.entity.Service;
import com.sihalov.laundry.data.enums.ClothesTypes;
import com.sihalov.laundry.data.enums.OrderStatuses;
import com.sihalov.laundry.repository.LaundryRepository;
import com.sihalov.laundry.service.LaundryService;
import com.sihalov.laundry.service.OrderService;
import com.sihalov.laundry.service.ServiceService;
import net.minidev.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    private final LaundryService laundryService;
    private final OrderService orderService;
    private final ServiceService serviceService;

    public CustomerController(LaundryService laundryService, OrderService orderService, ServiceService serviceService) {
        this.laundryService = laundryService;
        this.orderService = orderService;
        this.serviceService = serviceService;
    }

    @GetMapping("/laundries")
    public ResponseEntity<List<Laundry>> getLaundries() {
        List<Laundry> laundries = laundryService.getAll();
        return ResponseEntity.ok(laundries);
    }

    @GetMapping("/laundries/{id}")
    @ResponseBody
    public ResponseEntity<Laundry> getLaundry(@PathVariable Long id) {
        Laundry laundry;
        try {
            laundry = laundryService.getLaundry(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Laundry());
        }
        return ResponseEntity.ok(laundry);
    }

    @GetMapping("/order/{id}")
    @ResponseBody
    public ResponseEntity<Order> getOrderInfo(@PathVariable Long id, @RequestParam String phone) {
        Order order = orderService.getOrder(id);
        if (phone.equals(order.getCustomerPhone())) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Order());
        }
    }

    @GetMapping("/order/{id}/status")
    @ResponseBody
    public ResponseEntity<String> getOrderStatus(@PathVariable Long id, @RequestParam String phone) {
        Order order = orderService.getOrder(id);
        OrderStatuses status = orderService.getOrderStatus(id);
        if (phone.equals(order.getCustomerPhone())) {
            return ResponseEntity.ok(status.toString());
        } else {
            return new ResponseEntity<>("WRONG PHONE NUMBER", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/order/calculate")
    @ResponseBody
    public ResponseEntity<List<Service>> calculateOrder(@RequestBody @Valid JSONObject json) {
        Laundry laundry = laundryService.getLaundry(Long.valueOf(json.get("laundryId").toString()));
        List<Service> services = new ArrayList<>();
        List<Service> laundryServices = laundry.getServicesList();
        String serviceTypes = (String) json.get("serviceTypes");
        StringBuilder usedServices = new StringBuilder();
        for (String key : json.keySet()) {
            if (key.equals("serviceTypes") || key.equals("laundryId")) {
                continue;
            }

            Integer quantity = Integer.valueOf(json.get(key).toString());

            if (quantity > 0) {
                for (Service s : laundryServices) {
                    if (serviceTypes.contains(s.getServiceType().toString()) &&
                            s.getClothesType() == ClothesTypes.valueOf(key) &&
                            !usedServices.toString()
                                    .contains(s.getClothesType().toString()+s.getServiceType().toString())) {
                        if (s.getQuantity() >= quantity) {
                            services.add(s);
                        } else {
                            int count = (quantity % s.getQuantity());
                            for (int i = 0; i < (count == 0 ? quantity / s.getQuantity() : quantity / s.getQuantity()+1); i++) {
                                services.add(s);
                            }
                        }
                        usedServices.append(s.getClothesType());
                        usedServices.append(s.getServiceType());
                    }
                }
            }
        }

        return ResponseEntity.ok(services);
    }

    @PostMapping("/order/create")
    @ResponseBody
    public ResponseEntity<Order> createOrder(@RequestBody @Valid JSONObject json) {
        Laundry laundry = laundryService.getLaundry(Long.valueOf(json.get("laundryId").toString()));
        Order order = new Order();
        order.setCustomerName((String) json.get("customerName"));
        order.setCustomerPhone((String) json.get("customerPhone"));
        order.setCustomerEmail((String) json.get("customerEmail"));
        order.setDescription((String) json.get("description"));
        order.setDate(new Date());
        order.setTotalPrice((Integer) json.get("totalPrice"));
        order.setPaid(false);
        order.setOrderStatus(OrderStatuses.RECEIVED);
        order.setLaundry(laundry);
        order.setServices(getServicesList(json.get("servicesId")));
        orderService.createOrder(order);
        return ResponseEntity.ok(order);
    }

    private List<Service> getServicesList(Object servicesId) {
        List<Service> services = new ArrayList<>();
        ArrayList<Integer> ids = (ArrayList<Integer>) servicesId;
        for (Integer id : ids) {
            services.add(serviceService.getService(Long.valueOf(id)));
        }
        return services;
    }
}
