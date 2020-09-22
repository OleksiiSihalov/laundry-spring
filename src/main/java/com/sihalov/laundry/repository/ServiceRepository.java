package com.sihalov.laundry.repository;

import com.sihalov.laundry.data.entity.Service;
import com.sihalov.laundry.data.enums.ClothesTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
     List<Service> getAllByClothesType(ClothesTypes type);
}
