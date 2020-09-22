package com.sihalov.laundry.web.controller;

import com.sihalov.laundry.data.entity.Laundry;
import com.sihalov.laundry.data.entity.Order;
import com.sihalov.laundry.data.entity.Service;
import com.sihalov.laundry.data.enums.ClothesTypes;
import com.sihalov.laundry.data.enums.OrderStatuses;
import com.sihalov.laundry.data.enums.ServiceTypes;
import com.sihalov.laundry.service.LaundryService;
import com.sihalov.laundry.service.OrderService;
import com.sihalov.laundry.service.ServiceService;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AdminController {
    private final LaundryService laundryService;
    private final OrderService orderService;
    private final ServiceService serviceService;

    public AdminController(LaundryService laundryService, OrderService orderService, ServiceService serviceService) {
        this.laundryService = laundryService;
        this.orderService = orderService;
        this.serviceService = serviceService;
    }

    @GetMapping
    public String checkAdmin() {
        return "ADMIN";
    }

    @PostMapping("/create/laundry")
    public ResponseEntity<Laundry> createLaundry(@RequestBody @Valid JSONObject json) {
        Laundry laundry = new Laundry();
        laundry.setName((String) json.get("name"));
        laundry.setDescription((String) json.get("description"));
        laundry.setPhoneNumber((String) json.get("phoneNumber"));
        laundry.setEmail((String) json.get("email"));
        laundry.setAddress((String) json.get("address"));
        laundry.setLatitude(Double.valueOf((String) json.get("latitude")));
        laundry.setLongitude(Double.valueOf((String) json.get("longitude")));
        laundryService.createLaundry(laundry);
        return ResponseEntity.ok(laundry);
    }

    @PutMapping("/edit/laundry/{id}")
    public ResponseEntity<Laundry> editLaundry(@PathVariable Long id, @RequestBody @Valid JSONObject json) {
        Laundry laundry = laundryService.getLaundry(id);
        laundry.setName((String) json.get("name"));
        laundry.setDescription((String) json.get("description"));
        laundry.setAddress((String) json.get("address"));
        laundry.setPhoneNumber((String) json.get("phoneNumber"));
        laundry.setEmail((String) json.get("email"));
        laundry.setLatitude((Double) json.get("latitude"));
        laundry.setLongitude((Double) json.get("longitude"));
        laundryService.editLaundry(id, laundry);
        return ResponseEntity.ok(laundry);
    }

    @PostMapping("/create/service")
    public ResponseEntity<Service> createService(@RequestBody @Valid JSONObject json) {
        Laundry laundry = laundryService.getLaundry(Long.valueOf(json.get("laundryId").toString()));
        Service service = new Service();
        service.setName((String) json.get("name"));
        service.setDescription((String) json.get("description"));
        service.setServiceType(ServiceTypes.valueOf((String) json.get("serviceType")));
        service.setClothesType(ClothesTypes.valueOf((String) json.get("clothesType")));
        service.setUnit((Boolean.valueOf((String) json.get("unit"))));
        service.setQuantity(Integer.valueOf((String) json.get("quantity")));
        service.setPrice(Integer.valueOf((String) json.get("price")));
        service.setMinutesTime(Integer.valueOf((String) json.get("minutesTime")));
        service.setLaundry(laundry);
        serviceService.createService(service);
        return ResponseEntity.ok(service);
    }

    @PutMapping("/edit/service/{id}")
    public ResponseEntity<Service> editService(@PathVariable Long id, @RequestBody @Valid JSONObject json) {
        Service service = serviceService.getService(id);
        service.setName((String) json.get("name"));
        service.setDescription((String) json.get("description"));
        service.setUnit((Boolean.valueOf((String) json.get("unit"))));
        service.setServiceType(ServiceTypes.valueOf((String) json.get("serviceType")));
        service.setClothesType(ClothesTypes.valueOf((String) json.get("clothesType")));
        service.setQuantity(Integer.valueOf((String)json.get("quantity")));
        service.setPrice(Integer.valueOf((String)json.get("price")));
        service.setMinutesTime(Integer.valueOf((String)json.get("minutesTime")));
        serviceService.editService(id, service);
        return ResponseEntity.ok(service);
    }

    @PutMapping("/order/{id}/{status}")
    public String changeOrderStatus(@PathVariable Long id, @PathVariable String status) {
        orderService.changeStatus(id, OrderStatuses.valueOf(status));
        return status;
    }

    @DeleteMapping("/delete/laundry/{id}")
    public ResponseEntity<Long> deleteLaundry(@PathVariable Long id) {
        laundryService.deleteLaundry(id);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/delete/service/{id}")
    public ResponseEntity<Long> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/delete/order/{id}")
    public ResponseEntity<Long> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(id);
    }
}
