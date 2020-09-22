package com.sihalov.laundry.service;

import com.sihalov.laundry.data.entity.Service;
import com.sihalov.laundry.data.enums.ClothesTypes;
import com.sihalov.laundry.repository.ServiceRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Service> getAllById(List<Long> ids) {
        return serviceRepository.findAllById(ids);
    }


    public List<Service> getAllByClothesTypes(ClothesTypes type) {
        return serviceRepository.getAllByClothesType(type);
    }

    public Service getService(Long id) {
        return serviceRepository.getOne(id);
    }

    public void createService(Service service) {
        serviceRepository.save(service);
    }

    public void editService(Long id, Service changedService) {
        Service service = getService(id);
        service.setName(changedService.getName());
        service.setDescription(changedService.getDescription());
        service.setServiceType(changedService.getServiceType());
        service.setClothesType(changedService.getClothesType());
        service.setUnit(changedService.getUnit());
        service.setQuantity(changedService.getQuantity());
        service.setPrice(changedService.getPrice());
        service.setMinutesTime(changedService.getMinutesTime());
        serviceRepository.save(service);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
