package com.sihalov.laundry.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sihalov.laundry.data.enums.ClothesTypes;
import com.sihalov.laundry.data.enums.ServiceTypes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="SERVICES")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private ServiceTypes serviceType;
    private ClothesTypes clothesType;
    private Boolean unit;
    private Integer quantity;
    private Integer price;
    private Integer minutesTime;
    @ManyToOne
    @JoinColumn(name = "laundry_id")
    @JsonBackReference
    private Laundry laundry;
    @JsonBackReference
    @ManyToMany(mappedBy = "services", fetch = FetchType.EAGER)
    private List<Order> orders;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceTypes getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypes serviceType) {
        this.serviceType = serviceType;
    }

    public ClothesTypes getClothesType() {
        return clothesType;
    }

    public void setClothesType(ClothesTypes clothesType) {
        this.clothesType = clothesType;
    }

    public Boolean getUnit() {
        return unit;
    }

    public void setUnit(Boolean unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMinutesTime() {
        return minutesTime;
    }

    public void setMinutesTime(Integer minutesTime) {
        this.minutesTime = minutesTime;
    }

    public Laundry getLaundry() {
        return laundry;
    }

    public void setLaundry(Laundry laundry) {
        this.laundry = laundry;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
